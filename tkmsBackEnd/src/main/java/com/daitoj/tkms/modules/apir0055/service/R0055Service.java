package com.daitoj.tkms.modules.apir0055.service;

import com.daitoj.tkms.domain.MItemListSetting;
import com.daitoj.tkms.modules.apir0055.repository.R0055Repository;
import com.daitoj.tkms.modules.apir0055.service.dto.CustomerInfoDto;
import com.daitoj.tkms.modules.apir0055.service.dto.R0055ReturnData;
import com.daitoj.tkms.modules.apir0055.service.dto.R0055S01Dto;
import com.daitoj.tkms.modules.apir0055.service.dto.R0055S02Dto;
import com.daitoj.tkms.modules.apir0055.service.dto.R0055S03Dto;
import com.daitoj.tkms.modules.common.constants.CommonConstants;
import com.daitoj.tkms.modules.common.constants.MasterData;
import com.daitoj.tkms.modules.common.constants.Message;
import com.daitoj.tkms.modules.common.repository.MItemListSettingRepository;
import com.daitoj.tkms.modules.common.service.ReportService;
import com.daitoj.tkms.modules.common.service.SystemException;
import com.daitoj.tkms.modules.common.service.dto.ApiResult;
import com.daitoj.tkms.modules.common.utils.DateUtils;
import com.daitoj.tkms.modules.common.utils.TextUtils;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletResponse;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
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

/** 顧客情報一覧サービス. */
@Service
public class R0055Service {

  private static final Logger LOG = LoggerFactory.getLogger(R0055Service.class);

  /* CSVヘッダ */
  private static final String[] CSV_HEADER = {
    "顧客コード", "顧客名", "取引区分", "取引区分名", "業種・業態", "住所", "電話番号", "代表者名"
  };

  /* 帳票日付フォーマット */
  private static final String PDF_DATE_FORMAT = "yyyy年MM月dd日HH:mm:ss";

  /* CSVファイル名 */
  public static final String APP_NAME = "顧客一覧";

  /* 顧客情報一覧リポジトリ */
  private final R0055Repository r0055Repository;

  /* スタデータリポジトリ */
  private final MItemListSettingRepository mitemListSettingRepository;

  /* メッセージ */
  private final MessageSource messageSource;

  /* fasterxml.jackson@ObjectMapper */
  private final ObjectMapper objectMapper;

  /* レポートサービス */
  private final ReportService reportService;

  /* レポートファイル名 */
  public static final String REPORT_FILE_NAME = "WebR0070.jasper";

  /**
   * コンストラクタ.
   *
   * @param r0055Repository 顧客情報一覧リポジトリ
   * @param messageSource メッセージ
   * @param objectMapper jackson mapper
   * @param reportService レポートサービス
   * @param mitemListSettingRepository スタデータリポジトリ
   */
  public R0055Service(
      R0055Repository r0055Repository,
      MItemListSettingRepository mitemListSettingRepository,
      MessageSource messageSource,
      ObjectMapper objectMapper,
      ReportService reportService) {
    this.r0055Repository = r0055Repository;
    this.mitemListSettingRepository = mitemListSettingRepository;
    this.messageSource = messageSource;
    this.objectMapper = objectMapper;
    this.reportService = reportService;
  }

  /**
   * 初期処理.
   *
   * @return 顧客情報一覧
   */
  public ApiResult<R0055ReturnData> getInitInfo() {

    try {

      // 顧客情報を取得
      List<CustomerInfoDto> customerList = r0055Repository.findInitInfo();

      // 取引先区分リスト取得
      List<MItemListSetting> tradingkList =
          mitemListSettingRepository
              .findById_ItemClassCdAndId_EffectiveStartDtLessThanEqualOrderByDisplayOrder(
                  MasterData.ITEM_CLASS_CD_D0018, DateUtils.formatNow(DateUtils.DATE_FORMAT));

      // 業種・業態リスト取得
      List<MItemListSetting> gyousyuGyoutaiList =
          mitemListSettingRepository
              .findById_ItemClassCdAndId_EffectiveStartDtLessThanEqualOrderByDisplayOrder(
                  MasterData.ITEM_CLASS_CD_D0019, DateUtils.formatNow(DateUtils.DATE_FORMAT));

      // 戻り値
      R0055ReturnData returnData = new R0055ReturnData();
      // 顧客情報リスト
      returnData.setListCustomerResultInfo(customerList);
      // 取引先区分リスト
      returnData.setTradingKList(tradingkList);
      // 業種・業態リスト
      returnData.setGyousyuGyoutaiList(gyousyuGyoutaiList);

      // 成功の場合
      return ApiResult.success(returnData);

    } catch (Exception ex) {
      // 例外処理
      LOG.error(ex.toString(), ex);
      throw new SystemException(
          messageSource.getMessage(Message.MSGID_S0000, null, LocaleContextHolder.getLocale()));
    }
  }

  /**
   * 顧客情報検索.
   *
   * @param inDto 検索条件
   * @return 顧客情報一覧
   */
  public ApiResult<R0055ReturnData> getCustomerInfo(R0055S01Dto inDto) {

    // 顧客情報を取得
    List<CustomerInfoDto> customerList =
        r0055Repository.findCustomerInfo(
            inDto.getCustomerCd(),
            inDto.getTradingNormal(),
            inDto.getTradingCorporation(),
            inDto.getGyousyuGyoutai());
    customerList = filterByCondition(inDto, customerList);

    // 戻り値
    R0055ReturnData returnData = new R0055ReturnData();
    returnData.setListCustomerResultInfo(customerList);

    // API応答を返却
    return ApiResult.success(returnData);
  }

  /**
   * CSV出力処理.
   *
   * @param inDto パラメータ
   * @param encodedFileName ファイル名
   * @param response レスポンス
   * @return なし
   */
  public ApiResult<?> downLoadCsv(
      R0055S02Dto inDto, String encodedFileName, HttpServletResponse response) {

    try {
      // ヘッダ設定(カンマ区切り)
      CSVFormat csvFormat =
          CSVFormat.DEFAULT
              .builder()
              .setDelimiter(',')
              .setQuoteMode(QuoteMode.ALL)
              .setHeader(CSV_HEADER)
              .get();

      // 顧客情報を取得
      List<CustomerInfoDto> customerList =
          r0055Repository.findCustomerInfo(
              inDto.getCustomerCd(),
              inDto.getTradingNormal(),
              inDto.getTradingCorporation(),
              inDto.getGyousyuGyoutai());
      // 顧客結果情報
      List<CustomerInfoDto> customerResultList = filterByCondition(inDto, customerList);

      // 最大件数を超えた場合
      if (!CollectionUtils.isEmpty(customerResultList)
          && customerResultList.size() > CommonConstants.SEARCH_MAX_COUNT) {
        String msg =
            messageSource.getMessage(
                Message.MSGID_00000,
                new Object[] {CommonConstants.SEARCH_MAX_COUNT},
                LocaleContextHolder.getLocale());
        LOG.info(msg);

        // 結果情報
        return ApiResult.error(Message.MSGID_00000, msg);
      }

      // ファイルダウンロード用のHTTPヘッダー設定
      response.setHeader(
          "Content-Disposition",
          "attachment; filename=\"" + encodedFileName + "\"; filename*=utf-8''" + encodedFileName);
      response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);
      Charset charset = Charset.forName(CommonConstants.CSV_CHARSET_SHIFT_JIS);

      try (CSVPrinter printer =
          new CSVPrinter(new OutputStreamWriter(response.getOutputStream(), charset), csvFormat)) {

        // データがある場合
        if (!CollectionUtils.isEmpty(customerResultList)) {

          // 顧客結果レコードをCSVに書き出し
          for (CustomerInfoDto customer : customerResultList) {

            /* CSVレコード構成
             * 1. 顧客コード
             * 2. 顧客名
             * 3. 取引先区分
             * 4. 取引先区分名
             * 5. 業種・業態
             * 6. 住所
             * 7. 電話番号
             * 8. 代表者名
             */
            printer.printRecord(
                customer.getCustomerCd(),
                customer.getCustomerNm(),
                customer.getTradingK(),
                customer.getTradingKNm(),
                customer.getGyousyuGyoutaiNm(),
                customer.getCustomerAddr(),
                customer.getCustomerTelNo(),
                customer.getCeoNm());
          }
        }
      }

      // API応答を返却
      return ApiResult.success(null);

    } catch (Exception ex) {
      LOG.error(ex.toString(), ex);
      throw new SystemException(
          messageSource.getMessage(Message.MSGID_S0000, null, LocaleContextHolder.getLocale()));
    }
  }

  /**
   * 印刷処理.
   *
   * @param inDto パラメータ
   * @return なし
   */
  public ApiResult<?> exportReportToPdf(R0055S02Dto inDto) {
    try {
      // 顧客情報を取得
      List<CustomerInfoDto> customerList =
          r0055Repository.findCustomerInfo(
              inDto.getCustomerCd(),
              inDto.getTradingNormal(),
              inDto.getTradingCorporation(),
              inDto.getGyousyuGyoutai());
      customerList = filterByCondition(inDto, customerList);

      // 最大件数を超えた場合
      if (!CollectionUtils.isEmpty(customerList)
          && customerList.size() > CommonConstants.SEARCH_MAX_COUNT) {
        // メッセージ
        String msg =
            messageSource.getMessage(
                Message.MSGID_00000,
                new Object[] {CommonConstants.SEARCH_MAX_COUNT},
                LocaleContextHolder.getLocale());
        LOG.info(msg);
        // 結果情報
        return ApiResult.error(Message.MSGID_00000, msg);
      }

      // 印刷パラメータ
      R0055S03Dto printDto = new R0055S03Dto();
      // 利用PCのシステム
      printDto.setSysDate(inDto.getSysDate().format(DateTimeFormatter.ofPattern(PDF_DATE_FORMAT)));
      // 顧客コード
      printDto.setCustomerCd(inDto.getCustomerCd());
      // 顧客名
      printDto.setCustomerNm(inDto.getCustomerNm());
      // 取引先区分_一般
      printDto.setTradingNormal(inDto.getTradingNormal());
      // 取引先区分_法人
      printDto.setTradingCorporation(inDto.getTradingCorporation());
      // 業種・業態
      printDto.setGyousyuGyoutai(inDto.getGyousyuGyoutai());
      // レポートの渡すパラメータ
      Map<String, Object> paramsMap =
          objectMapper.convertValue(printDto, new TypeReference<Map<String, Object>>() {});

      // データソースを生成する
      JRDataSource dataSource = new JRBeanCollectionDataSource(customerList);

      // レポートを生成する
      byte[] datas = reportService.exportReportToPdf(REPORT_FILE_NAME, paramsMap, dataSource);

      // API応答を返却
      return ApiResult.success(datas);

    } catch (Exception ex) {
      LOG.error(ex.toString(), ex);
      throw new SystemException(
          messageSource.getMessage(Message.MSGID_S0000, null, LocaleContextHolder.getLocale()));
    }
  }

  /**
   * 検索処理.
   *
   * @param inDto パラメータ
   * @param customerList 顧客情報リスト
   * @return 顧客結果情報
   */
  private List<CustomerInfoDto> filterByCondition(
      R0055S01Dto inDto, List<CustomerInfoDto> customerList) {

    // 半角全角を区別しない
    if (StringUtils.isNotEmpty(inDto.getCustomerNm())) {
      customerList =
        customerList.stream()
              .filter(
                  customer ->
                      (TextUtils.matchesIgnoringKanaWidth(
                              customer.getCustomerNm(), inDto.getCustomerNm())
                          || TextUtils.matchesIgnoringKanaWidth(
                              customer.getCustomerKnNm(), inDto.getCustomerNm())))
              .collect(Collectors.toList());
    }

    return customerList;
  }

}
