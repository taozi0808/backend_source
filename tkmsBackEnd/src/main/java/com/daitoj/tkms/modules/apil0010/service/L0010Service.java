package com.daitoj.tkms.modules.apil0010.service;

import com.daitoj.tkms.modules.apil0010.repository.L0010Repository;
import com.daitoj.tkms.modules.apil0010.repository.mapper.L0010Mapper;
import com.daitoj.tkms.modules.apil0010.service.dto.CustomerInvoiceHdrInfoDto;
import com.daitoj.tkms.modules.apil0010.service.dto.CustomerInvoiceResultInfoDto;
import com.daitoj.tkms.modules.apil0010.service.dto.L0010ReturnData;
import com.daitoj.tkms.modules.apil0010.service.dto.L0010S01Dto;
import com.daitoj.tkms.modules.apil0010.service.dto.L0010S02Dto;
import com.daitoj.tkms.modules.apil0010.service.dto.L0010S03Dto;
import com.daitoj.tkms.modules.common.constants.CommonConstants;
import com.daitoj.tkms.modules.common.constants.Message;
import com.daitoj.tkms.modules.common.service.ReportService;
import com.daitoj.tkms.modules.common.service.SystemException;
import com.daitoj.tkms.modules.common.service.dto.ApiResult;
import com.daitoj.tkms.modules.common.utils.DateUtils;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletResponse;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;
import java.text.DecimalFormat;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.QuoteMode;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

/** 請求一覧のサービス. */
@Service
public class L0010Service {

  private static final Logger LOG = LoggerFactory.getLogger(L0010Service.class);

  /* CSVヘッダ */
  private static final String[] CSV_HEADER = {
    "顧客コード", "顧客名", "物件コード", "物件名", "物件着手日", "物件引渡日", "請求書No", "支払条件", "請求年月", "請負金額", "請求済金額",
    "今回請求金額", "未請求金額"
  };

  /* CSVファイル名 */
  public static final String APP_NAME = "請求一覧";

  /* 帳票日付フォーマット */
  private static final String PDF_DATE_FORMAT = "yyyy年MM月dd日HH:mm:ss";

  /* CSV日付フォーマット */
  private static final String CSV_DATE_FORMAT = "yyyy年MM月dd日";

  /*　年月フォーマット　*/
  private static final String YEAR_MONTH_FORMAT = "yyyy年MM月";

  /* 金額フォマード */
  private static final String MONEY_FORMAT = "￥#,##0";

  /* 請求一覧のリポジトリ */
  private final L0010Repository l0010Repository;

  /* メッセージ */
  private final MessageSource messageSource;

  /* 顧客請求マッピング */
  private final L0010Mapper l0010Mapper;

  /* fasterxml.jackson@ObjectMapper */
  private final ObjectMapper objectMapper;

  /* レポートファイル名 */
  private final ReportService reportService;

  /* レポートファイル名 */
  public static final String REPORT_FILE_NAME = "WebL0020.jasper";

  /**
   * コンストラクタ.
   *
   * @param l0010Repository 請求一覧のリポジトリ
   * @param messageSource メッセージ
   * @param l0010Mapper 顧客請求マッピング
   * @param objectMapper fasterxml.jackson@ObjectMapper
   */
  public L0010Service(
      L0010Repository l0010Repository,
      MessageSource messageSource,
      L0010Mapper l0010Mapper,
      ObjectMapper objectMapper,
      ReportService reportService) {
    this.l0010Repository = l0010Repository;
    this.messageSource = messageSource;
    this.l0010Mapper = l0010Mapper;
    this.objectMapper = objectMapper;
    this.reportService = reportService;
  }

  /**
   * 初期処理.
   *
   * @param belongOfficeCd 所属事務所コード
   * @return 請求一覧
   */
  public ApiResult<L0010ReturnData> getInitInfo(String belongOfficeCd) {

    try {
      // 請求情報を取得
      List<CustomerInvoiceHdrInfoDto> customerInvoiceHdrList =
          l0010Repository.findInitInfo(
              CommonConstants.OFFICE_HONSHA_CODE.equals(belongOfficeCd) ? null : belongOfficeCd);
      // 請求結果情報に変換する
      List<CustomerInvoiceResultInfoDto> customerInvoiceResultList =
          l0010Mapper.toCustomerInvoiceResultInfo(customerInvoiceHdrList);
      // 次回請求のみ
      customerInvoiceResultList = customerInvoiceFilter(customerInvoiceResultList);

      // 戻り値
      L0010ReturnData returnData = new L0010ReturnData();
      // 請求情報リスト
      returnData.setListCustomerInvoiceResultInfo(customerInvoiceResultList);

      // 成功の場合
      return ApiResult.success(returnData);

    } catch (Exception ex) {
      // エラーログ出力とシステム例外のスロー
      LOG.error(ex.toString(), ex);
      throw new SystemException(
          messageSource.getMessage(Message.MSGID_S0000, null, LocaleContextHolder.getLocale()));
    }
  }

  /**
   * 請求書検索処理.
   *
   * @param inDto 検索パラメータ
   * @return 請求書情報
   */
  public ApiResult<L0010ReturnData> getCustomerInvoiceInfo(L0010S01Dto inDto) {

    try {

      // 請求書情報を取得
      List<CustomerInvoiceHdrInfoDto> customerInvoiceList =
          l0010Repository.findCustomerInvoiceInfo(
              CommonConstants.OFFICE_HONSHA_CODE.equals(inDto.getBelongOfficeCd())
                  ? null
                  : inDto.getBelongOfficeCd(),
              inDto.getCustomerCd(),
              inDto.getCustomerNm(),
              inDto.getProjectSiteCd(),
              inDto.getProjectSiteNm(),
              inDto.getInvoiceNo(),
              inDto.getConstrStartYmdFrom(),
              inDto.getConstrCompYmdTo(),
              inDto.getConstrCompYmdFrom(),
              inDto.getConstrCompYmdTo(),
              inDto.getDisplayItem1(),
              inDto.getDisplayItem2(),
              inDto.getDisplayItem3());
      // 請求結果情報に変換する
      List<CustomerInvoiceResultInfoDto> customerInvoiceResultList =
          l0010Mapper.toCustomerInvoiceResultInfo(customerInvoiceList);

      // 表示履歴を選択された場合
      if ("0".equals(inDto.getDisplayHistory())) {
        // 最大の歴番のレコードのみを表示する
        customerInvoiceResultList = customerInvoiceFilter(customerInvoiceResultList);
      }

      // 0件を取得する場合
      if (CollectionUtils.isEmpty(customerInvoiceResultList)) {
        // メッセージ
        String msg =
            messageSource.getMessage(Message.MSGID_K00001, null, LocaleContextHolder.getLocale());
        LOG.info(msg);
        // 結果情報
        return ApiResult.error(Message.MSGID_K00001, msg);
      }

      // 戻り値
      L0010ReturnData returnData = new L0010ReturnData();
      // 請求書情報リスト
      returnData.setListCustomerInvoiceResultInfo(customerInvoiceResultList);
      // APIの応答を返却する
      return ApiResult.success(returnData);

    } catch (Exception ex) {
      // エラーログ出力とシステム例外のスロー
      LOG.error(ex.toString(), ex);
      throw new SystemException(
          messageSource.getMessage(Message.MSGID_S0000, null, LocaleContextHolder.getLocale()));
    }
  }

  /**
   * CSV出力処理.
   *
   * @param inDto パラメータ
   * @param encodedFileName ファイル名
   * @param response レスポンス
   * @return 無し
   */
  public ApiResult<?> downloadCsv(
      L0010S02Dto inDto, String encodedFileName, HttpServletResponse response) {

    try {

      // CSVフォーマットを構築する
      CSVFormat csvFormat =
          CSVFormat.DEFAULT
              .builder()
              .setDelimiter(',') // フィールド区切り文字をカンマに設定
              .setQuoteMode(QuoteMode.ALL) // 全フィールドをダブルクォーテーションで囲む
              .setHeader(CSV_HEADER) // カラムヘッダーを設定
              .get();

      // 請求情報を取得
      List<CustomerInvoiceHdrInfoDto> customerInvoiceList =
          l0010Repository.findCustomerInvoiceInfo(
              CommonConstants.OFFICE_HONSHA_CODE.equals(inDto.getBelongOfficeCd())
                  ? null
                  : inDto.getBelongOfficeCd(),
              inDto.getCustomerCd(),
              inDto.getCustomerNm(),
              inDto.getProjectSiteCd(),
              inDto.getProjectSiteNm(),
              inDto.getInvoiceNo(),
              inDto.getConstrStartYmdFrom(),
              inDto.getConstrStartYmdTo(),
              inDto.getConstrCompYmdFrom(),
              inDto.getConstrCompYmdTo(),
              inDto.getDisplayItem1(),
              inDto.getDisplayItem2(),
              inDto.getDisplayItem3());
      // 請求結果情報に変換する
      List<CustomerInvoiceResultInfoDto> customerInvoiceResultList =
          l0010Mapper.toCustomerInvoiceResultInfo(customerInvoiceList);
      // 画面の表示歴番を選択する場合
      if ("0".equals(inDto.getDisplayHistory())) {
        customerInvoiceResultList = customerInvoiceFilter(customerInvoiceResultList);
      }

      // 最大件数を超える場合
      if (!CollectionUtils.isEmpty(customerInvoiceResultList)
          && customerInvoiceResultList.size() > CommonConstants.SEARCH_MAX_COUNT) {
        // メッセージ取得
        String msg =
            messageSource.getMessage(
                Message.MSGID_K00001,
                new Object[] {CommonConstants.SEARCH_MAX_COUNT},
                LocaleContextHolder.getLocale());
        // 情報ログに出力
        LOG.info(msg);
        // APIエラー応答を返却
        return ApiResult.error(Message.MSGID_00000, msg);
      }

      // ファイルダウンロード用のHTTPヘッダー設定
      response.setHeader(
          "Content-Disposition",
          "attachment; filename=\"" + encodedFileName + "\"; filename*=utf-8''" + encodedFileName);
      response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);
      Charset charset = Charset.forName(CommonConstants.CSV_CHARSET_SHIFT_JIS);

      try (CSVPrinter print =
          new CSVPrinter(new OutputStreamWriter(response.getOutputStream(), charset), csvFormat)) {

        // 請求結果リストが空ではない場合処理
        if (!CollectionUtils.isEmpty(customerInvoiceResultList)) {

          // 請求結果レコードをCSVに書き出し
          for (CustomerInvoiceResultInfoDto customerInvoiceResultInfo : customerInvoiceResultList) {

            /* CSVレコード構成
             * 1.  顧客コード
             * 2.  顧客名
             * 3.  物件コード
             * 4.  物件名
             * 5.  物件着手日
             * 6.  物件引渡日
             * 7.  請求書No
             * 8.  支払条件
             * 9.  請求年月
             * 10. 請負金額
             * 11. 請求済金額
             * 12. 今回請求金額
             * 13. 未請求残
             */
            print.printRecord(
                customerInvoiceResultInfo.getCustomerCd(),
                customerInvoiceResultInfo.getCustomerNm(),
                customerInvoiceResultInfo.getProjectSiteCd(),
                customerInvoiceResultInfo.getProjectSiteNm(),
                DateUtils.formatDateFromYYYYMMDD(
                    customerInvoiceResultInfo.getConstrStartYmd(), CSV_DATE_FORMAT),
                DateUtils.formatDateFromYYYYMMDD(
                    customerInvoiceResultInfo.getConstrCompYmd(), CSV_DATE_FORMAT),
                customerInvoiceResultInfo.getInvoiceNoWithHisno(),
                customerInvoiceResultInfo.getPaymentTermsNm(),
                DateUtils.formatDateFromYYYYMMDD(
                    customerInvoiceResultInfo.getInvoiceDt(), YEAR_MONTH_FORMAT),
                customerInvoiceResultInfo.getInclTaxCoTotalAmt() != null
                    ? new DecimalFormat(MONEY_FORMAT)
                        .format(customerInvoiceResultInfo.getInclTaxCoTotalAmt())
                    : "",
                customerInvoiceResultInfo.getPaidAmt() != null
                    ? new DecimalFormat(MONEY_FORMAT).format(customerInvoiceResultInfo.getPaidAmt())
                    : "",
                customerInvoiceResultInfo.getPaymentAmt() != null
                    ? new DecimalFormat(MONEY_FORMAT)
                        .format(customerInvoiceResultInfo.getPaymentAmt())
                    : "",
                customerInvoiceResultInfo.getOutstandingAmt() != null
                    ? new DecimalFormat(MONEY_FORMAT)
                        .format(customerInvoiceResultInfo.getOutstandingAmt())
                    : "");
          }
        }
      }

      // API応答を返却
      return ApiResult.success(null);

    } catch (Exception ex) {
      // エラーログ出力とシステム例外のスロー
      LOG.error(ex.toString(), ex);
      throw new SystemException(
          messageSource.getMessage(Message.MSGID_S0000, null, LocaleContextHolder.getLocale()));
    }
  }

  /**
   * 印刷処理.
   *
   * @param inDto 請求情報印刷パラメータ
   * @return pdf
   */
  public ApiResult<?> exportReportToPdf(L0010S02Dto inDto) {

    try {

      // 請求情報を取得
      List<CustomerInvoiceHdrInfoDto> customerInvoiceList =
          l0010Repository.findCustomerInvoiceInfo(
              CommonConstants.OFFICE_HONSHA_CODE.equals(inDto.getBelongOfficeCd())
                  ? null
                  : inDto.getBelongOfficeCd(),
              inDto.getCustomerCd(),
              inDto.getCustomerNm(),
              inDto.getProjectSiteCd(),
              inDto.getProjectSiteNm(),
              inDto.getInvoiceNo(),
              inDto.getConstrStartYmdFrom(),
              inDto.getConstrCompYmdTo(),
              inDto.getConstrCompYmdFrom(),
              inDto.getConstrCompYmdTo(),
              inDto.getDisplayItem1(),
              inDto.getDisplayItem2(),
              inDto.getDisplayItem3());
      // 請求結果情報に変換する
      List<CustomerInvoiceResultInfoDto> customerInvoiceResultList =
          l0010Mapper.toCustomerInvoiceResultInfo(customerInvoiceList);

      // 表示履歴を選択された場合
      if ("0".equals(inDto.getDisplayHistory())) {
        // 最大の歴番のレコードのみを表示する
        customerInvoiceResultList = customerInvoiceFilter(customerInvoiceResultList);
      }

      // 最大件数を超えた場合
      if (!CollectionUtils.isEmpty(customerInvoiceResultList)
          && customerInvoiceResultList.size() > CommonConstants.SEARCH_MAX_COUNT) {
        // メッセージ
        String msg =
            messageSource.getMessage(
                Message.MSGID_00000,
                new Object[] {CommonConstants.SEARCH_MAX_COUNT},
                LocaleContextHolder.getLocale());
        // 情報ログに出力
        LOG.info(msg);
        // APIエラー応答を返却
        return ApiResult.error(Message.MSGID_00000, msg);
      }

      // 印刷パラメータ
      L0010S03Dto printDto = new L0010S03Dto();
      // 利用PCのシステム日付
      printDto.setSysDate(inDto.getSysDate().format(DateTimeFormatter.ofPattern(PDF_DATE_FORMAT)));
      // 顧客コード
      printDto.setCustomerCd(inDto.getCustomerCd());
      // 顧客名
      printDto.setCustomerNm(inDto.getCustomerNm());
      // 物件コード
      printDto.setProjectSiteCd(inDto.getProjectSiteCd());
      // 物件名
      printDto.setProjectSiteNm(inDto.getProjectSiteNm());
      // 請求書No
      printDto.setInvoiceNo(inDto.getInvoiceNo());
      // 物件着手日
      printDto.setConstrStartYmd(
          buildDateRange(inDto.getConstrStartYmdFrom(), inDto.getConstrStartYmdTo()));
      // 物件引渡日
      printDto.setConstrCompYmd(
          buildDateRange(inDto.getConstrCompYmdFrom(), inDto.getConstrCompYmdTo()));
      // 表示対象(未請求)
      printDto.setDisplayItem1(inDto.getDisplayItem1());
      // 表示対象(請求残有り)
      printDto.setDisplayItem2(inDto.getDisplayItem2());
      // 表示対象(請求残無し)
      printDto.setDisplayItem3(inDto.getDisplayItem3());
      // 表示履歴
      printDto.setDisplayHistory(nullConverter(inDto.getDisplayHistory()));
      // レポートの渡すパラメータ
      Map<String, Object> paramsMap =
          objectMapper.convertValue(printDto, new TypeReference<Map<String, Object>>() {});

      // データソースを生成する
      JRDataSource dataSource = new JRBeanCollectionDataSource(customerInvoiceResultList);

      // レポートの生成する
      byte[] datas = reportService.exportReportToPdf(REPORT_FILE_NAME, paramsMap, dataSource);

      return ApiResult.success(datas);

    } catch (Exception ex) {
      // エラーログ出力とシステム例外のスロー
      LOG.error(ex.toString(), ex);
      throw new SystemException(
          messageSource.getMessage(Message.MSGID_00000, null, LocaleContextHolder.getLocale()));
    }
  }

  /**
   * 転換処理.
   *
   * @param input input
   * @return 転換後文字
   */
  private String nullConverter(String input) {
    return input == null ? "" : input;
  }

  /**
   * 顧客請求のフィルター: 同一の請求Noの最大歴番のみを抽出する.
   *
   * @param customerInvoices 請求リスト
   * @return フィルター後の請求リスト
   */
  private List<CustomerInvoiceResultInfoDto> customerInvoiceFilter(
      List<CustomerInvoiceResultInfoDto> customerInvoices) {

    return new ArrayList<>(
        customerInvoices.stream()
            .collect(
                Collectors.toMap(
                    CustomerInvoiceResultInfoDto::getInvoiceNo,
                    Function.identity(),
                    (c1, c2) -> c1.getHisNo() > c2.getHisNo() ? c1 : c2))
            .values()
            .stream()
            .sorted(
                Comparator.comparing(CustomerInvoiceResultInfoDto::getCustomerCd)
                    .thenComparing(CustomerInvoiceResultInfoDto::getProjectSiteCd))
            .toList());
  }

  /**
   * 期間条件の変換.
   *
   * @param dateFrom 開始時間
   * @param dateTo 終了時間
   * @return 期間文字列
   */
  private String buildDateRange(String dateFrom, String dateTo) {
    boolean isFromEmpty = StringUtils.isEmpty(dateFrom);
    boolean isToEmpty = StringUtils.isEmpty(dateTo);

    if (isFromEmpty && isToEmpty) {
      return "";
    } else if (isFromEmpty) {
      return "~ " + DateUtils.formatDateFromYYYYMMDD(dateFrom, CSV_DATE_FORMAT);
    } else if (isToEmpty) {
      return DateUtils.formatDateFromYYYYMMDD(dateTo, CSV_DATE_FORMAT) + " ~";
    } else {
      return DateUtils.formatDateFromYYYYMMDD(dateFrom, CSV_DATE_FORMAT)
          + " ~ "
          + DateUtils.formatDateFromYYYYMMDD(dateTo, CSV_DATE_FORMAT);
    }
  }
}
