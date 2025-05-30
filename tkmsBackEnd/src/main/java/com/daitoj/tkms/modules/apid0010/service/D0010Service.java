package com.daitoj.tkms.modules.apid0010.service;

import com.daitoj.tkms.modules.apid0010.repository.D0010Repository;
import com.daitoj.tkms.modules.apid0010.repository.mapper.D0010Mapper;
import com.daitoj.tkms.modules.apid0010.service.dto.D0010ReturnData;
import com.daitoj.tkms.modules.apid0010.service.dto.D0010S01Dto;
import com.daitoj.tkms.modules.apid0010.service.dto.D0010S02Dto;
import com.daitoj.tkms.modules.apid0010.service.dto.D0010S03Dto;
import com.daitoj.tkms.modules.apid0010.service.dto.DetailedEstInfoDto;
import com.daitoj.tkms.modules.apid0010.service.dto.DetailedEstResultDto;
import com.daitoj.tkms.modules.common.constants.CommonConstants;
import com.daitoj.tkms.modules.common.constants.Message;
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
import java.text.DecimalFormat;
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
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

/** 精積算一覧ビジネスロジック. */
@Service
public class D0010Service {

  private static final Logger LOG = LoggerFactory.getLogger(D0010Service.class);

  /* 帳票日付フォーマット */
  private static final String PDF_DATE_FORMAT = "yyyy年MM月dd日HH:mm:ss";

  /* 日付フォーマット */
  private static final String DATE_FORMAT = "yyyy年MM月dd日";

  /* 金額フォマード */
  private static final String MONEY_FORMAT = "￥#,##0";

  /* CSVヘッダ */
  private static final String[] CSV_HEADER = {
    "精積算コード",
    "概算コード",
    "案件コード",
    "案件名",
    "顧客コード",
    "顧客名",
    "概算金額",
    "精積算金額",
    "作成日/修正日",
    "承認日",
    "精積算担当部門コード",
    "精積算担当部門",
    "精積算担当者コード",
    "精積算担当者"
  };

  /* 精積算一覧のクエリ */
  private final D0010Repository d0010Repository;

  /* メッセージ */
  private final MessageSource messageSource;

  /* レポートサービス */
  private final ReportService reportService;

  /* fasterxml.jacksonのObjectMapper */
  private final ObjectMapper objectMapper;

  /* 精積算マッピング */
  private final D0010Mapper d0010Mapper;

  /* CSVファイル名 */
  public static final String APP_NAME = "精積算一覧";

  /* レポートファイル名 */
  public static final String REPORT_FILE_NAME = "WebD0020.jasper";

  /**
   * コンストラクタ.
   *
   * @param d0010Repository 精積算一覧のクエリ
   * @param messageSource メッセージ
   * @param reportService レポートサービス
   * @param objectMapper fasterxml.jacksonのObjectMapper
   * @param d0010Mapper 精積算マッピング
   */
  public D0010Service(
      D0010Repository d0010Repository,
      MessageSource messageSource,
      ReportService reportService,
      ObjectMapper objectMapper,
      D0010Mapper d0010Mapper) {
    this.d0010Repository = d0010Repository;
    this.messageSource = messageSource;
    this.reportService = reportService;
    this.objectMapper = objectMapper;
    this.d0010Mapper = d0010Mapper;
  }

  /**
   * 初期表示.
   *
   * @param detailedEstCreateFlg 精積算作成済フラグ
   * @return 精積算情報
   */
  public ApiResult<D0010ReturnData> getInitInfo(String detailedEstCreateFlg) {

    try {
      // 精積算情報を取得
      List<DetailedEstInfoDto> detailedEstList = d0010Repository.findInitInfo(detailedEstCreateFlg);
      // 精積算結果情報に変換する
      List<DetailedEstResultDto> detailedEstResultList =
          d0010Mapper.toDetailedEstResultDto(detailedEstList);

      // 戻り値
      D0010ReturnData returnData = new D0010ReturnData();
      // 精積算結果情報リスト
      returnData.setListDetailedEstInfo(detailedEstResultList);

      // API応答を返却
      return ApiResult.success(returnData);

    } catch (Exception ex) {
      // エラーログ出力とシステム例外のスロー
      LOG.error(ex.toString(), ex);
      throw new SystemException(
          messageSource.getMessage(Message.MSGID_S0000, null, LocaleContextHolder.getLocale()));
    }
  }

  /**
   * 検索処理.
   *
   * @param inDto パラメータ
   * @return 精積算情報
   */
  public ApiResult<D0010ReturnData> getDetailedEstInfo(D0010S01Dto inDto) {

    try {

      // 精積算情報を取得
      List<DetailedEstInfoDto> detailedEstInfoList =
          d0010Repository.findDetailedEstInfo(
              inDto.getProjectCd(),
              inDto.getCustomerCd(),
              inDto.getRoughEstCd(),
              inDto.getDetailedEstCd(),
              inDto.getDetailedEstOrgNm(),
              inDto.getDetailedEstPicNm(),
              inDto.getDetailedEstCreateFlg());
      // 　案件名と顧客名を条件として検索
      List<DetailedEstResultDto> detailedEstResultList =
          filterByCondition(inDto, detailedEstInfoList);

      // 取得件数が０件だった場合
      if (CollectionUtils.isEmpty(detailedEstResultList)) {
        // メッセージ
        String msg =
            messageSource.getMessage(Message.MSGID_K00001, null, LocaleContextHolder.getLocale());
        LOG.info(msg);
        // 結果情報
        return ApiResult.error(Message.MSGID_K00001, msg);
      }

      // 戻り値
      D0010ReturnData returnData = new D0010ReturnData();
      // 精積算情報リスト
      returnData.setListDetailedEstInfo(detailedEstResultList);

      // API応答を返却する
      return ApiResult.success(returnData);

    } catch (Exception ex) {
      // エラーログ出力とシステム例外のスロー
      LOG.error(ex.toString(), ex);
      throw new SystemException(
          messageSource.getMessage(Message.MSGID_S0000, null, LocaleContextHolder.getLocale()));
    }
  }

  /**
   * CSV出力.
   *
   * @param inDto パラメータ
   * @param encodedFileName ファイル名
   * @param response レスポンス
   */
  public ApiResult<?> downLoadCsv(
      D0010S02Dto inDto, String encodedFileName, HttpServletResponse response) {
    try {
      // ヘッダ設定（カンマ区切り）
      CSVFormat csvFormat =
          CSVFormat.DEFAULT
              .builder()
              .setDelimiter(',')
              .setQuoteMode(QuoteMode.ALL)
              .setHeader(CSV_HEADER)
              .get();

      // 精積算情報を取得
      List<DetailedEstInfoDto> detailedEstInfo =
          d0010Repository.findDetailedEstInfo(
              inDto.getProjectCd(),
              inDto.getCustomerCd(),
              inDto.getRoughEstCd(),
              inDto.getDetailedEstCd(),
              inDto.getDetailedEstOrgNm(),
              inDto.getDetailedEstPicNm(),
              inDto.getDetailedEstCreateFlg());
      // 案件名と顧客名を条件として検索する
      List<DetailedEstResultDto> detailedEstResultDtoList =
          filterByCondition(inDto, detailedEstInfo);

      // 0件の場合
      if (!CollectionUtils.isEmpty(detailedEstResultDtoList)
          && detailedEstResultDtoList.size() > CommonConstants.SEARCH_MAX_COUNT) {
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

      // ファイルダウンロード用のHTTPヘッダー設定
      response.setHeader(
          HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + encodedFileName);
      response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);
      Charset charset = Charset.forName(CommonConstants.CSV_CHARSET_SHIFT_JIS);

      // CSVファイル出力処理
      try (CSVPrinter printer =
          new CSVPrinter(new OutputStreamWriter(response.getOutputStream(), charset), csvFormat)) {

        // データがある場合
        if (!CollectionUtils.isEmpty(detailedEstResultDtoList)) {
          /* CSVレコード構成
           * 1. 精積算コード
           * 2. 概算コード
           * 3. 案件コード
           * 4. 案件名
           * 5. 顧客コード
           * 6. 顧客名
           * 7. 概算金額
           * 8. 精積算金額
           * 9. 作成日/修正日
           * 10.承認日
           * 11.精積算担当部門コード
           * 12.精積算担当部門
           * 13.精積算担当者コード
           * 14.精積算担当者
           */
          for (DetailedEstResultDto detailedEst : detailedEstResultDtoList) {
            // 明細行設定
            printer.printRecord(
                detailedEst.getDetailedEstCd(),
                detailedEst.getRoughEstCd(),
                detailedEst.getProjectCd(),
                detailedEst.getProjectNm(),
                detailedEst.getCustomerCd(),
                detailedEst.getCustomerNm(),
                detailedEst.getRoughEstTotalAmt() != null
                    ? new DecimalFormat(MONEY_FORMAT).format(detailedEst.getRoughEstTotalAmt())
                    : "",
                detailedEst.getDetailedEstTotalAmt() != null
                    ? new DecimalFormat(MONEY_FORMAT).format(detailedEst.getDetailedEstTotalAmt())
                    : "",
                detailedEst.getDetailedEstYmd() != null
                    ? DateUtils.formatDateFromYYYYMMDD(detailedEst.getDetailedEstYmd(), DATE_FORMAT)
                    : "",
                detailedEst.getFinalApprDt() != null
                    ? DateUtils.formatDateFromYYYYMMDD(detailedEst.getFinalApprDt(), DATE_FORMAT)
                    : "",
                detailedEst.getDetailedEstOrgCd(),
                detailedEst.getDetailedEstOrgNm(),
                detailedEst.getDetailedEstPicCd(),
                detailedEst.getDetailedEstPicNm());
          }
        }
      }

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
   * @param inDto パラメータ
   * @return pdf
   */
  public ApiResult<?> exportReportToPdf(D0010S02Dto inDto) {
    try {
      // 精積算情報を取得
      List<DetailedEstInfoDto> detailedEstInfo =
          d0010Repository.findDetailedEstInfo(
              inDto.getProjectCd(),
              inDto.getCustomerCd(),
              inDto.getRoughEstCd(),
              inDto.getDetailedEstCd(),
              inDto.getDetailedEstOrgNm(),
              inDto.getDetailedEstPicNm(),
              inDto.getDetailedEstCreateFlg());
      // 精積算結果情報に変換する
      List<DetailedEstResultDto> detailedEstResult = filterByCondition(inDto, detailedEstInfo);

      // 0件の場合
      if (!CollectionUtils.isEmpty(detailedEstResult)
          && detailedEstResult.size() > CommonConstants.SEARCH_MAX_COUNT) {
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
      D0010S03Dto printDto = new D0010S03Dto();

      // 利用PCのシステム日付
      printDto.setSysDate(inDto.getSysDate().format(DateTimeFormatter.ofPattern(PDF_DATE_FORMAT)));
      // 案件コード
      printDto.setProjectCd(inDto.getProjectCd());
      // 案件名
      printDto.setProjectNm(inDto.getProjectNm());
      // 概算コード
      printDto.setRoughEstCd(inDto.getRoughEstCd());
      // 精積算コード
      printDto.setDetailedEstCd(inDto.getDetailedEstCd());
      // 顧客コード
      printDto.setCustomerCd(inDto.getCustomerCd());
      // 顧客名
      printDto.setCustomerNm(inDto.getCustomerNm());
      // 精積算担当部門
      printDto.setDetailedEstOrgNm(inDto.getDetailedEstOrgNm());
      // 精積算担当者
      printDto.setDetailedEstPicNm(inDto.getDetailedEstPicNm());
      // 精積算作成済区分
      printDto.setDetailedEstCreateFlg(inDto.getDetailedEstCreateFlg());
      // レポートに渡すパラメータ
      Map<String, Object> paramsMap =
          objectMapper.convertValue(printDto, new TypeReference<Map<String, Object>>() {});

      // データソースを生成する
      JRDataSource dataSource = new JRBeanCollectionDataSource(detailedEstResult);

      // レポートを生成する
      byte[] datas = reportService.exportReportToPdf(REPORT_FILE_NAME, paramsMap, dataSource);

      // API応答を返却
      return ApiResult.success(datas);
    } catch (Exception ex) {
      // エラーログ出力とシステム例外のスロー
      LOG.error(ex.toString(), ex);
      throw new SystemException(
        messageSource.getMessage(Message.MSGID_S0000, null, LocaleContextHolder.getLocale()));
    }
  }

  /**
   * 検索処理.
   *
   * @param inDto パラメータ
   * @param dtoList 精積算情報リスト
   * @return 精積算結果情報リスト
   */
  private List<DetailedEstResultDto> filterByCondition(
      D0010S01Dto inDto, List<DetailedEstInfoDto> dtoList) {

    // 精積算結果リストに変換する
    List<DetailedEstResultDto> resultList = d0010Mapper.toDetailedEstResultDto(dtoList);

    // 案件名と顧客名
    resultList =
        resultList.stream()
            .filter(
                item ->
                    StringUtils.isEmpty(inDto.getProjectNm())
                        || item.getFullWidthProjectNm()
                            .contains(
                                EnhancedFullWidthConverterUtils.convert(inDto.getProjectNm())))
            .filter(
                item ->
                    StringUtils.isEmpty(inDto.getCustomerNm())
                        || item.getFullWidthCustomerNm().contains(inDto.getCustomerNm()))
            .collect(Collectors.toList());

    return resultList;
  }

}
