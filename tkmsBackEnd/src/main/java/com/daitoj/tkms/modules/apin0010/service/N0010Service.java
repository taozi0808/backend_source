//package com.daitoj.tkms.modules.apin0010.service;
//
//import com.daitoj.tkms.modules.apin0010.repository.N0010Repository;
//import com.daitoj.tkms.modules.apin0010.service.dto.ConstrWbsInfoDto;
//import com.daitoj.tkms.modules.apin0010.service.dto.N0010ReturnData;
//import com.daitoj.tkms.modules.apin0010.service.dto.N0010S01Dto;
//import com.daitoj.tkms.modules.apin0010.service.dto.N0010S02Dto;
//import com.daitoj.tkms.modules.apin0010.service.dto.N0010S03Dto;
//import com.daitoj.tkms.modules.common.constants.CommonConstants;
//import com.daitoj.tkms.modules.common.constants.Message;
//import com.daitoj.tkms.modules.common.service.ReportService;
//import com.daitoj.tkms.modules.common.service.SystemException;
//import com.daitoj.tkms.modules.common.service.dto.ApiResult;
//import com.daitoj.tkms.modules.common.utils.TextUtils;
//import com.fasterxml.jackson.core.type.TypeReference;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import jakarta.servlet.http.HttpServletResponse;
//import java.io.OutputStreamWriter;
//import java.nio.charset.Charset;
//import java.time.LocalDate;
//import java.time.format.DateTimeFormatter;
//import java.time.format.DateTimeParseException;
//import java.util.List;
//import java.util.Map;
//import java.util.stream.Collectors;
//import net.sf.jasperreports.engine.JRDataSource;
//import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
//import org.apache.commons.csv.CSVFormat;
//import org.apache.commons.csv.CSVPrinter;
//import org.apache.commons.csv.QuoteMode;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.context.MessageSource;
//import org.springframework.context.i18n.LocaleContextHolder;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.MediaType;
//import org.springframework.stereotype.Service;
//import org.springframework.util.CollectionUtils;
//
//
///** 工事予実一覧ビジネスロジック. */
//@Service
//public class N0010Service {
//  /** CSVファイル名. */
//  public static final String APP_NAME = "工事予実一覧";
//
//  /** レポートファイル名. */
//  public static final String REPORT_FILE_NAME = "WebN0020.jasper";
//
//  private static final Logger LOG = LoggerFactory.getLogger(N0010Service.class);
//
//  /** 帳票日付フォーマット. */
//  private static final String PDF_DATE_FORMAT = "yyyy年MM年dd日HH:mm:ss";
//
//  /** CSVヘッダ. */
//  private static final String[] CSV_HEADER = {
//    "現場コード", "現場名", "予実作成日", "入力担当者コード", "入力担当者", "現場着手日", "現場引渡日"
//  };
//
//  /** 工事予実一覧のクエリ. */
//  private final N0010Repository n0010Repository;
//
//  /** メッセージ. */
//  private final MessageSource messageSource;
//
//  /** レポートサービス. */
//  private final ReportService reportService;
//
//  /** fasterxml.jacksonのObjectMapper. */
//  private final ObjectMapper objectMapper;
//
//  /** コンストラクタ. */
//  public N0010Service(
//      N0010Repository n0010Repository,
//      MessageSource messageSource,
//      ReportService reportService,
//      ObjectMapper objectMapper) {
//    this.n0010Repository = n0010Repository;
//    this.messageSource = messageSource;
//    this.reportService = reportService;
//    this.objectMapper = objectMapper;
//  }
//
//  /**
//   * 初期表示.
//   *
//   * @param inDto 工事予実情報取得パラメータ
//   * @return 工事予実情報取得結果
//   */
//  public ApiResult<N0010ReturnData> getInitInfo(N0010S01Dto inDto) {
//    try {
//      // 工事予実情報を取得
//      List<ConstrWbsInfoDto> detailedEstList =
//          n0010Repository.findInitInfo(
//              inDto.getBelongOfficeCd().equals(CommonConstants.OFFICE_HONSHA_CODE)
//                  ? null
//                  : inDto.getBelongOfficeCd());
//
//      // 戻り値
//      N0010ReturnData returnData = new N0010ReturnData();
//      // 工事予実情報リスト
//      returnData.setListConstrWbsInfo(detailedEstList);
//
//      return ApiResult.success(returnData);
//
//    } catch (Exception ex) {
//      LOG.error(ex.toString(), ex);
//
//      throw new SystemException(ex.toString(), ex);
//    }
//  }
//
//  /**
//   * 検索処理.
//   *
//   * @param inDto 工事予実情報取得パラメータ
//   * @return 案工事予実情報取得結果
//   */
//  public ApiResult<N0010ReturnData> getConstrWbsInfo(N0010S01Dto inDto) {
//    try {
//      // 工事予実情報を取得
//      List<ConstrWbsInfoDto> constrWbsInfoList =
//          n0010Repository.findConstrWbsInfo(
//              inDto.getBelongOfficeCd().equals(CommonConstants.OFFICE_HONSHA_CODE)
//                  ? null
//                  : inDto.getBelongOfficeCd(),
//              inDto.getConstrSiteCd(),
//              inDto.getWbsCreateDtFrom(),
//              inDto.getWbsCreateDtTo(),
//              inDto.getConstrSiteStartYmdFrom(),
//              inDto.getConstrSiteStartYmdTo(),
//              inDto.getConstrSiteDeliveryYmdFrom(),
//              inDto.getConstrSiteDeliveryYmdTo(),
//              inDto.getEmpNm());
//      // 現場名検索
//      List<ConstrWbsInfoDto> searchedList =
//          compareKanaItem(constrWbsInfoList, inDto.getConstrSiteNm());
//
//      // 取得件数が０件だった場合
//      if (CollectionUtils.isEmpty(searchedList)) {
//        // メッセージ
//        String msg =
//            messageSource.getMessage(Message.MSGID_K00001, null, LocaleContextHolder.getLocale());
//
//        LOG.info(msg);
//
//        // 結果情報
//        return ApiResult.error(Message.MSGID_K00001, msg);
//      }
//
//      // 戻り値
//      N0010ReturnData returnData = new N0010ReturnData();
//      // 工事予実情報リスト
//      returnData.setListConstrWbsInfo(searchedList);
//      return ApiResult.success(returnData);
//
//    } catch (Exception ex) {
//      LOG.error(ex.toString(), ex);
//
//      throw new SystemException(ex.toString(), ex);
//    }
//  }
//
//  /**
//   * CSV出力.
//   *
//   * @param inDto 工事予実情報取得パラメータ
//   * @param encodedFileName ファイル名
//   * @param response response
//   */
//  public ApiResult<?> downLoadCsv(
//      N0010S02Dto inDto, String encodedFileName, HttpServletResponse response) {
//    try {
//      // ヘッダ設定（カンマ区切り）
//      CSVFormat csvFormat =
//          CSVFormat.DEFAULT
//              .builder()
//              .setDelimiter(',')
//              .setQuoteMode(QuoteMode.ALL)
//              .setHeader(CSV_HEADER)
//              .get();
//
//      // 工事予実情報を取得
//      List<ConstrWbsInfoDto> constrWbsInfoList =
//          n0010Repository.findConstrWbsInfo(
//              inDto.getBelongOfficeCd().equals(CommonConstants.OFFICE_HONSHA_CODE)
//                  ? null
//                  : inDto.getBelongOfficeCd(),
//              inDto.getConstrSiteCd(),
//              inDto.getWbsCreateDtFrom(),
//              inDto.getWbsCreateDtTo(),
//              inDto.getConstrSiteStartYmdFrom(),
//              inDto.getConstrSiteStartYmdTo(),
//              inDto.getConstrSiteDeliveryYmdFrom(),
//              inDto.getConstrSiteDeliveryYmdTo(),
//              inDto.getEmpNm());
//      // 現場名検索
//      List<ConstrWbsInfoDto> searchedList =
//          compareKanaItem(constrWbsInfoList, inDto.getConstrSiteNm());
//
//      // 最大件数を超える場合
//      if (!CollectionUtils.isEmpty(searchedList)
//          && searchedList.size() > CommonConstants.SEARCH_MAX_COUNT) {
//        // メッセージ
//        String msg =
//            messageSource.getMessage(
//                Message.MSGID_00000,
//                new Object[] {CommonConstants.SEARCH_MAX_COUNT},
//                LocaleContextHolder.getLocale());
//
//        LOG.info(msg);
//
//        // 結果情報
//        return ApiResult.error(Message.MSGID_00000, msg);
//      }
//      response.setHeader(
//          HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + encodedFileName);
//      response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);
//
//      Charset charset = Charset.forName(CommonConstants.CSV_CHARSET_SHIFT_JIS);
//
//      try (CSVPrinter printer =
//          new CSVPrinter(new OutputStreamWriter(response.getOutputStream(), charset), csvFormat)) {
//        // データがある場合
//        if (!CollectionUtils.isEmpty(searchedList)) {
//          for (ConstrWbsInfoDto constrWbs : searchedList) {
//            // 明細行設定
//            printer.printRecord(
//                // 現場コード
//                constrWbs.getConstrSiteCd(),
//                // 現場名
//                constrWbs.getConstrSiteNm(),
//                // 予実作成日
//                constrWbs.getWbsCreateDt(),
//                // 入力担当者コード
//                constrWbs.getCreatePicCd(),
//                // 入力担当者
//                constrWbs.getEmpNm(),
//                // 現場着手日
//                constrWbs.getConstrSiteStartYmd(),
//                // 現場引渡日
//                constrWbs.getConstrSiteDeliveryYmd());
//          }
//        }
//      }
//      return ApiResult.success(null);
//    } catch (Exception ex) {
//      LOG.error(ex.toString(), ex);
//
//      throw new SystemException(ex.toString(), ex);
//    }
//  }
//
//  /**
//   * 印刷処理.
//   *
//   * @param inDto 工事予実情報印刷パラメータ
//   * @return pdf
//   */
//  public ApiResult<?> exportReportToPdf(N0010S02Dto inDto) {
//    try {
//      // 工事予実情報を取得
//      List<ConstrWbsInfoDto> constrWbsInfoList =
//          n0010Repository.findConstrWbsInfo(
//              inDto.getBelongOfficeCd().equals(CommonConstants.OFFICE_HONSHA_CODE)
//                  ? null
//                  : inDto.getBelongOfficeCd(),
//              inDto.getConstrSiteCd(),
//              inDto.getWbsCreateDtFrom(),
//              inDto.getWbsCreateDtTo(),
//              inDto.getConstrSiteStartYmdFrom(),
//              inDto.getConstrSiteStartYmdTo(),
//              inDto.getConstrSiteDeliveryYmdFrom(),
//              inDto.getConstrSiteDeliveryYmdTo(),
//              inDto.getEmpNm());
//      // 現場名検索
//      List<ConstrWbsInfoDto> searchedList =
//          compareKanaItem(constrWbsInfoList, inDto.getConstrSiteNm());
//
//      // 最大件数を超えた場合
//      if (!CollectionUtils.isEmpty(searchedList)
//          && searchedList.size() > CommonConstants.SEARCH_MAX_COUNT) {
//        // メッセージ
//        String msg =
//            messageSource.getMessage(
//                Message.MSGID_00000,
//                new Object[] {CommonConstants.SEARCH_MAX_COUNT},
//                LocaleContextHolder.getLocale());
//
//        LOG.info(msg);
//
//        // 結果情報
//        return ApiResult.error(Message.MSGID_00000, msg);
//      }
//
//      // 印刷パラメータ
//      N0010S03Dto printDto = new N0010S03Dto();
//
//      // 利用PCのシステム日付
//      printDto.setSysDate(inDto.getSysDate().format(DateTimeFormatter.ofPattern(PDF_DATE_FORMAT)));
//      // 現場コード
//      printDto.setConstrSiteCd(inDto.getConstrSiteCd());
//      // 現場名
//      printDto.setConstrSiteNm(inDto.getConstrSiteNm());
//      // 予実作成日
//      printDto.setWbsCreateDt(
//          buildDateRangeStr(inDto.getWbsCreateDtFrom(), inDto.getWbsCreateDtTo()));
//      // 現場着手日
//      printDto.setConstrSiteStartYmd(
//          buildDateRangeStr(inDto.getConstrSiteStartYmdFrom(), inDto.getConstrSiteStartYmdTo()));
//      // 現場引渡日
//      printDto.setConstrSiteDeliveryYmd(
//          buildDateRangeStr(
//              inDto.getConstrSiteDeliveryYmdFrom(), inDto.getConstrSiteDeliveryYmdTo()));
//      // 入力担当者
//      printDto.setEmpNm(inDto.getEmpNm());
//
//      // レポートに渡すパラメータ
//      Map<String, Object> paramsMap =
//          objectMapper.convertValue(printDto, new TypeReference<Map<String, Object>>() {});
//
//      // データソースを生成する
//      JRDataSource dataSource = new JRBeanCollectionDataSource(searchedList);
//
//      // レポートを生成する
//      byte[] datas = reportService.exportReportToPdf(REPORT_FILE_NAME, paramsMap, dataSource);
//
//      return ApiResult.success(datas);
//    } catch (Exception ex) {
//      LOG.error(ex.toString(), ex);
//
//      throw new SystemException(ex.toString(), ex);
//    }
//  }
//
//  /**
//   * 日付範囲文字列を構築する.
//   *
//   * @param dateFrom 開始時間
//   * @param dateTo 完了時間
//   * @return フォーマットされた日付範囲文字列
//   */
//  public String buildDateRangeStr(String dateFrom, String dateTo) {
//
//    if ((dateFrom == null || dateFrom.isEmpty()) && (dateTo == null || dateTo.isEmpty())) {
//      return "";
//    }
//    DateTimeFormatter inputFormatter = DateTimeFormatter.BASIC_ISO_DATE;
//    DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("yyyy年MM月dd日");
//
//    String formattedFrom = formatDate(dateFrom, inputFormatter, outputFormatter);
//    String formattedTo = formatDate(dateTo, inputFormatter, outputFormatter);
//
//    if (!formattedFrom.isEmpty() && !formattedTo.isEmpty()) {
//      return formattedFrom + " ～ " + formattedTo;
//    } else if (!formattedFrom.isEmpty()) {
//      return formattedFrom + " ～";
//    } else {
//      return "～ " + formattedTo;
//    }
//  }
//
//  private String formatDate(String dateStr, DateTimeFormatter input, DateTimeFormatter output) {
//    if (dateStr == null || dateStr.isEmpty()) {
//      return "";
//    }
//    try {
//      LocalDate date = LocalDate.parse(dateStr, input);
//      return date.format(output);
//    } catch (DateTimeParseException e) {
//      return "";
//    }
//  }
//
//  /**
//   * 現場名検索.
//   *
//   * @param list 工事予実情報リスト
//   * @param constrSiteNm 現場名
//   * @return 検索した工事予実情報リスト
//   */
//  private List<ConstrWbsInfoDto> compareKanaItem(List<ConstrWbsInfoDto> list, String constrSiteNm) {
//    if (list == null) {
//      return null;
//    }
//    return list.stream()
//        .filter(
//            constrSite ->
//                (TextUtils.matchesIgnoringKanaWidth(constrSite.getConstrSiteNm(), constrSiteNm)
//                    || TextUtils.matchesIgnoringKanaWidth(
//                        constrSite.getConstrSiteKnNm(), constrSiteNm)))
//        .collect(Collectors.toList());
//  }
//}
