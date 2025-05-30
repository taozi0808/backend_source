package com.daitoj.tkms.modules.apir0055.service;

import com.daitoj.tkms.domain.MItemListSetting;
import com.daitoj.tkms.modules.apir0055.repository.R0055Repository;
import com.daitoj.tkms.modules.apir0055.repository.mapper.R0055Mapper;
import com.daitoj.tkms.modules.apir0055.service.dto.CustomerInfoDto;
import com.daitoj.tkms.modules.apir0055.service.dto.CustomerResultDto;
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
import com.daitoj.tkms.modules.common.utils.EnhancedFullWidthConverterUtils;
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
    "顧客コード", "顧客名", "取引先区分", "取引先区分名", "業種・業態コード", "業種・業態", "住所", "電話番号", "代表者名"
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

  /* 顧客情報マッピング */
  private final R0055Mapper r0055Mapper;

  /* レポートサービス */
  private final ReportService reportService;

  /* レポートファイル名 */
  public static final String REPORT_FILE_NAME = "WebR0055.jasper";

  /**
   * コンストラクタ.
   *
   * @param r0055Repository 顧客情報一覧リポジトリ
   * @param messageSource メッセージ
   * @param objectMapper jackson mapper
   * @param reportService レポートサービス
   * @param r0055Mapper 顧客情報マッピング
   * @param mitemListSettingRepository スタデータリポジトリ
   */
  public R0055Service(
      R0055Repository r0055Repository,
      MItemListSettingRepository mitemListSettingRepository,
      MessageSource messageSource,
      ObjectMapper objectMapper,
      R0055Mapper r0055Mapper,
      ReportService reportService) {
    this.r0055Repository = r0055Repository;
    this.mitemListSettingRepository = mitemListSettingRepository;
    this.messageSource = messageSource;
    this.objectMapper = objectMapper;
    this.r0055Mapper = r0055Mapper;
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
      // 顧客結果情報
      List<CustomerResultDto> customerResultList = r0055Mapper.toCustomerResultDto(customerList);

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
      returnData.setListCustomerResultInfo(customerResultList);
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
            inDto.getCustomerCd(), inDto.getTradingK(), inDto.getGyousyuGyoutai());

    // 顧客結果情報
    List<CustomerResultDto> customerResultList = filterByCondition(inDto, customerList);

    // 戻り値
    R0055ReturnData returnData = new R0055ReturnData();
    returnData.setListCustomerResultInfo(customerResultList);

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
              inDto.getCustomerCd(), inDto.getTradingK(), inDto.getGyousyuGyoutai());
      // 顧客結果情報
      List<CustomerResultDto> customerResultList = filterByCondition(inDto, customerList);

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
          for (CustomerResultDto customerResult : customerResultList) {

            /* CSVレコード構成
             * 1. 顧客コード
             * 2. 顧客名
             * 3. 取引先区分
             * 4. 取引先区分名
             * 5. 業種・業態コード
             * 6. 業種・業態
             * 7. 住所
             * 8. 電話番号
             * 9. 代表者名
             */
            printer.printRecord(
                customerResult.getCustomerCd(),
                customerResult.getCustomerNm(),
                customerResult.getTradingK(),
                customerResult.getTradingKNm(),
                customerResult.getGyousyuGyoutai(),
                customerResult.getGyousyuGyoutaiNm(),
                customerResult.getCustomerAddr(),
                customerResult.getCustomerTelNo(),
                customerResult.getCeoNm());
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
              inDto.getCustomerCd(), inDto.getTradingK(), inDto.getGyousyuGyoutai());
      // 顧客結果情報
      List<CustomerResultDto> customerResultList = filterByCondition(inDto, customerList);

      // 最大件数を超えた場合
      if (!CollectionUtils.isEmpty(customerResultList)
          && customerResultList.size() > CommonConstants.SEARCH_MAX_COUNT) {
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
      // 取引先区分
      printDto.setTradingKNm(inDto.getTradingKNm());
      // 業種・業態
      printDto.setGyousyuGyoutaiNm(inDto.getGyousyuGyoutaiNm());
      // レポートの渡すパラメータ
      Map<String, Object> paramsMap =
          objectMapper.convertValue(printDto, new TypeReference<Map<String, Object>>() {});

      // データソースを生成する
      JRDataSource dataSource = new JRBeanCollectionDataSource(customerResultList);

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
  private List<CustomerResultDto> filterByCondition(
      R0055S01Dto inDto, List<CustomerInfoDto> customerList) {

    // 顧客結果情報に変換する
    List<CustomerResultDto> customerResultList = r0055Mapper.toCustomerResultDto(customerList);

    // 半角全角を区別しない
    if (StringUtils.isNotEmpty(inDto.getCustomerNm())) {
      customerResultList =
          customerResultList.stream()
              .filter(
                  customer -> {
                    if (StringUtils.isEmpty(customer.getCustomerNm())) {
                      return false;
                    }

                    String inputCustomerNm =
                        EnhancedFullWidthConverterUtils.convert(inDto.getCustomerNm());

                    return customer.getFullWidthCustomerNm().contains(inputCustomerNm)
                        || customer.getFullWidthCustomerRyakusyou().contains(inputCustomerNm)
                        || customer.getFullWidthCustomerKnNm().contains(inputCustomerNm);
                  })
              .collect(Collectors.toList());
    }

    return customerResultList;
  }

}
