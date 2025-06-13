package com.daitoj.tkms.modules.apih0010.service;

import com.daitoj.tkms.modules.apih0010.repository.H0010Repository;
import com.daitoj.tkms.modules.apih0010.service.dto.AssessInfoDto;
import com.daitoj.tkms.modules.apih0010.service.dto.AssessmentInfoDto;
import com.daitoj.tkms.modules.apih0010.service.dto.H0010ReturnData;
import com.daitoj.tkms.modules.apih0010.service.dto.H0010S01Dto;
import com.daitoj.tkms.modules.apih0010.service.dto.H0010S02Dto;
import com.daitoj.tkms.modules.apih0010.service.dto.H0010S03Dto;
import com.daitoj.tkms.modules.common.constants.CommonConstants;
import com.daitoj.tkms.modules.common.constants.Message;
import com.daitoj.tkms.modules.common.service.ReportService;
import com.daitoj.tkms.modules.common.service.SystemException;
import com.daitoj.tkms.modules.common.service.dto.ApiResult;
import com.daitoj.tkms.modules.common.utils.DateUtils;
import com.daitoj.tkms.modules.common.utils.TextUtils;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletResponse;
import java.io.OutputStreamWriter;
import java.math.BigDecimal;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

/** 査定一覧ビジネスロジック. */
@Service
public class H0010Service {

  /** CSVファイル名. */
  public static final String APP_NAME = "査定一覧";

  /** レポートファイル名. */
  public static final String REPORT_FILE_NAME = "WebH0020.jasper";

  private static final Logger LOG = LoggerFactory.getLogger(H0010Service.class);

  /** 帳票日付フォーマット. */
  private static final String PDF_DATE_FORMAT = "yyyy年MM年dd日HH:mm:ss";

  /* フォーマット: yyyy年MM月dd日 */
  private static final String DATE_FORAMT = "yyyy年MM月dd日";

  /** CSVヘッダ. */
  private static final String[] CSV_HEADER = {
    "現場コード",
    "現場名",
    "工事部門コード",
    "工事部門",
    "現場所長コード",
    "現場所長",
    "専任技術者コード",
    "専任技術者",
    "工事工程",
    "査定承認日",
    "実行予算金額",
    "発注金額",
    "前月迄査定済金額",
    "当月査定金額",
    "査定済合計金額",
    "未査定残",
  };

  /** 査定一覧のクエリ. */
  private final H0010Repository h0010Repository;

  /** メッセージ. */
  private final MessageSource messageSource;

  /** レポートサービス. */
  private final ReportService reportService;

  /** fasterxml.jacksonのObjectMapper. */
  private final ObjectMapper objectMapper;

  /** コンストラクタ. */
  public H0010Service(
      H0010Repository h0010Repository,
      MessageSource messageSource,
      ReportService reportService,
      ObjectMapper objectMapper) {
    this.h0010Repository = h0010Repository;
    this.messageSource = messageSource;
    this.reportService = reportService;
    this.objectMapper = objectMapper;
  }

  /**
   * 初期表示.
   *
   * @param inDto 査定情報取得パラメータ
   * @return 査定情報取得結果
   */
  public ApiResult<H0010ReturnData> getInitInfo(H0010S01Dto inDto) {
    try {
      List<AssessmentInfoDto> constrSiteCdList =
          h0010Repository.findConstrSiteInfo(inDto.getDisplayobject());
      List<String> consiteCd =
          constrSiteCdList.stream()
              .map(AssessmentInfoDto::getConstrSiteCd)
              .collect(Collectors.toList());
      List<AssessInfoDto> assessList =
          h0010Repository.findInitInfo(inDto.getBelongOfficeCd(), consiteCd);

      // 戻り値
      H0010ReturnData returnData = new H0010ReturnData();
      for (AssessInfoDto finalassess : assessList) {
        if (finalassess.getPoTotalAmt() != null) {

          // 発注金額
          BigDecimal assessTotalAmt = finalassess.getPoTotalAmt();
          // 査定済合計金額
          BigDecimal mtdAssessTotalAmt = finalassess.getAssessTotalAmt();
          // 未査定残 = 発注金額 - 査定済合計金額
          finalassess.setUnidentified(assessTotalAmt.subtract(mtdAssessTotalAmt));
        }
        String padded = String.format("%12s", finalassess.getConstrSiteCd()).replace(' ', '0');
        String constrSiteCd =
            String.format(
                "%s-%s-%s", padded.substring(0, 7), padded.substring(7, 10), padded.substring(10));
        finalassess.setConstrSiteCd(constrSiteCd);
      }
      // 査定情報リスト
      returnData.setListAssessInfo(assessList);
      return ApiResult.success(returnData);

    } catch (Exception ex) {
      LOG.error(ex.toString(), ex);

      throw new SystemException(ex.toString(), ex);
    }
  }

  /**
   * 検索処理.
   *
   * @param inDto 査定情報取得パラメータ
   * @return 査定情報取得結果
   */
  public ApiResult<H0010ReturnData> getassessInfo(H0010S01Dto inDto) {
    try {
      List<AssessmentInfoDto> constrSiteCdList =
          h0010Repository.findConstrSiteInfo(inDto.getDisplayobject());
      List<String> consiteCd =
          constrSiteCdList.stream()
              .map(AssessmentInfoDto::getConstrSiteCd)
              .collect(Collectors.toList());
      List<AssessInfoDto> assessList =
          h0010Repository.findSateiInfo(
              inDto.getBelongOfficeCd(),
              inDto.getDisplayobject(),
              inDto.getConstrSiteCd(),
              inDto.getExecBgtCd(),
              inDto.getOrgNm(),
              inDto.getEmpNm(),
              consiteCd);
      // 現場名検索
      List<AssessInfoDto> searchedList = compareKanaItem(assessList, inDto.getConstrSiteNm());
      for (AssessInfoDto finalassess : assessList) {
        if (finalassess.getPoTotalAmt() != null) {

          // 発注金額
          BigDecimal assessTotalAmt = finalassess.getPoTotalAmt();
          // 査定済合計金額
          BigDecimal mtdAssessTotalAmt = finalassess.getAssessTotalAmt();
          // 未査定残 = 発注金額 - 査定済合計金額
          finalassess.setUnidentified(assessTotalAmt.subtract(mtdAssessTotalAmt));
        }
        String padded = String.format("%12s", finalassess.getConstrSiteCd()).replace(' ', '0');
        String constrSiteCd =
            String.format(
                "%s-%s-%s", padded.substring(0, 7), padded.substring(7, 10), padded.substring(10));
        finalassess.setConstrSiteCd(constrSiteCd);
      }

      // 取得件数が０件だった場合
      if (CollectionUtils.isEmpty(searchedList)) {
        // メッセージ
        String msg =
            messageSource.getMessage(Message.MSGID_K00001, null, LocaleContextHolder.getLocale());

        LOG.info(msg);

        // 結果情報
        return ApiResult.error(Message.MSGID_K00001, msg);
      }

      // 戻り値
      H0010ReturnData returnData = new H0010ReturnData();
      // 査定情報リスト
      returnData.setListAssessInfo(searchedList);

      return ApiResult.success(returnData);

    } catch (Exception ex) {
      LOG.error(ex.toString(), ex);

      throw new SystemException(ex.toString(), ex);
    }
  }

  /**
   * CSV出力.
   *
   * @param inDto 査定情報取得パラメータ
   * @param encodedFileName ファイル名
   * @param response response
   */
  public ApiResult<?> downLoadCsv(
      H0010S02Dto inDto, String encodedFileName, HttpServletResponse response) {
    try {
      // ヘッダ設定（カンマ区切り）
      CSVFormat csvFormat =
          CSVFormat.DEFAULT
              .builder()
              .setDelimiter(',')
              .setQuoteMode(QuoteMode.ALL)
              .setHeader(CSV_HEADER)
              .get();

      // 査定情報を取得
      List<AssessmentInfoDto> constrSiteCdList =
          h0010Repository.findConstrSiteInfo(inDto.getDisplayobject());
      List<String> consiteCd =
          constrSiteCdList.stream()
              .map(AssessmentInfoDto::getConstrSiteCd)
              .collect(Collectors.toList());
      List<AssessInfoDto> assessList =
          h0010Repository.findSateiInfo(
              inDto.getBelongOfficeCd(),
              inDto.getDisplayobject(),
              inDto.getConstrSiteCd(),
              inDto.getExecBgtCd(),
              inDto.getOrgNm(),
              inDto.getEmpNm(),
              consiteCd);
      // 現場名検索
      List<AssessInfoDto> searchedList = compareKanaItem(assessList, inDto.getConstrSiteNm());
      for (AssessInfoDto finalassess : assessList) {
        if (finalassess.getPoTotalAmt() != null) {

          // 発注金額
          BigDecimal assessTotalAmt = finalassess.getPoTotalAmt();
          // 査定済合計金額
          BigDecimal mtdAssessTotalAmt = finalassess.getAssessTotalAmt();
          // 未査定残 = 発注金額 - 査定済合計金額
          finalassess.setUnidentified(assessTotalAmt.subtract(mtdAssessTotalAmt));
        }
        String padded = String.format("%12s", finalassess.getConstrSiteCd()).replace(' ', '0');
        String constrSiteCd =
            String.format(
                "%s-%s-%s", padded.substring(0, 7), padded.substring(7, 10), padded.substring(10));
        finalassess.setConstrSiteCd(constrSiteCd);
      }

      if (!CollectionUtils.isEmpty(searchedList)
          && searchedList.size() > CommonConstants.SEARCH_MAX_COUNT) {
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

      response.setHeader(
          HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + encodedFileName);
      response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);

      Charset charset = Charset.forName(CommonConstants.CSV_CHARSET_SHIFT_JIS);

      try (CSVPrinter printer =
          new CSVPrinter(new OutputStreamWriter(response.getOutputStream(), charset), csvFormat)) {
        // データがある場合
        if (!CollectionUtils.isEmpty(searchedList)) {
          for (AssessInfoDto assessInfo : searchedList) {
            // 明細行設定
            printer.printRecord(
                assessInfo.getConstrSiteCd(),
                assessInfo.getConstrSiteNm(),
                assessInfo.getOrgCd(),
                assessInfo.getOrgNm(),
                assessInfo.getConstrSiteDirectorCd(),
                assessInfo.getConstrSiteEmpNm(),
                assessInfo.getPicCd(),
                assessInfo.getEmpNm(),
                assessInfo.getConstrProcessNm(),
                assessInfo.getFinalApprDt() != null
                    ? DateUtils.formatDateFromYYYYMMDD(assessInfo.getFinalApprDt(), DATE_FORAMT)
                    : "",
                assessInfo.getExecBgtTotalAmt(),
                assessInfo.getPoTotalAmt(),
                assessInfo.getBefAssessTotalAmt(),
                assessInfo.getMtdAssessTotalAmt(),
                assessInfo.getAssessTotalAmt(),
                assessInfo.getUnidentified());
          }
        }
      }

      return ApiResult.success(null);
    } catch (Exception ex) {
      LOG.error(ex.toString(), ex);

      throw new SystemException(ex.toString(), ex);
    }
  }

  /**
   * 印刷処理.
   *
   * @param inDto 査定情報印刷パラメータ
   * @return pdf
   */
  public ApiResult<?> exportReportToPdf(H0010S02Dto inDto) {
    try {
      List<AssessmentInfoDto> constrSiteCdList =
          h0010Repository.findConstrSiteInfo(inDto.getDisplayobject());
      List<String> consiteCd =
          constrSiteCdList.stream()
              .map(AssessmentInfoDto::getConstrSiteCd)
              .collect(Collectors.toList());
      List<AssessInfoDto> assessList =
          h0010Repository.findSateiInfo(
              inDto.getBelongOfficeCd(),
              inDto.getDisplayobject(),
              inDto.getConstrSiteCd(),
              inDto.getExecBgtCd(),
              inDto.getOrgNm(),
              inDto.getEmpNm(),
              consiteCd);
      // 現場名検索
      List<AssessInfoDto> searchedList = compareKanaItem(assessList, inDto.getConstrSiteNm());
      for (AssessInfoDto finalassess : assessList) {
        if (finalassess.getPoTotalAmt() != null) {

          // 発注金額
          BigDecimal assessTotalAmt = finalassess.getPoTotalAmt();
          // 査定済合計金額
          BigDecimal mtdAssessTotalAmt = finalassess.getAssessTotalAmt();
          // 未査定残 = 発注金額 - 査定済合計金額
          finalassess.setUnidentified(assessTotalAmt.subtract(mtdAssessTotalAmt));
        }
        String padded = String.format("%12s", finalassess.getConstrSiteCd()).replace(' ', '0');
        String constrSiteCd =
            String.format(
                "%s-%s-%s", padded.substring(0, 7), padded.substring(7, 10), padded.substring(10));
        finalassess.setConstrSiteCd(constrSiteCd);
      }

      if (!CollectionUtils.isEmpty(searchedList)
          && searchedList.size() > CommonConstants.SEARCH_MAX_COUNT) {
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
      H0010S03Dto printDto = new H0010S03Dto();
      // 利用PCのシステム日付
      printDto.setSysDate(inDto.getSysDate().format(DateTimeFormatter.ofPattern(PDF_DATE_FORMAT)));
      printDto.setConstrSiteCd(inDto.getConstrSiteCd());
      printDto.setConstrSiteNm(inDto.getConstrSiteNm());
      printDto.setConstrOrgId(inDto.getConstrOrgId());
      printDto.setConstrSiteDirectorNm(inDto.getConstrSiteDirectorNm());
      printDto.setEmpNm(inDto.getEmpNm());
      printDto.setConstrProcessNm(inDto.getConstrProcessNm());
      printDto.setFinalApprDt(inDto.getFinalApprDt());
      printDto.setExecBgtTotalAmt(inDto.getExecBgtTotalAmt());
      printDto.setPoTotalAmt(inDto.getPoTotalAmt());
      printDto.setAssessTotalAmt(inDto.getAssessTotalAmt());
      printDto.setMtdAssessTotalAmt(inDto.getMtdAssessTotalAmt());
      printDto.setAssessmentTotalAmount(inDto.getAssessmentTotalAmount());
      printDto.setUnidentified(inDto.getUnidentified());
      printDto.setDisplayobject(inDto.getDisplayobject());
      // レポートに渡すパラメータ
      Map<String, Object> paramsMap = objectMapper.convertValue(printDto, new TypeReference<>() {});

      // データソースを生成する
      JRDataSource dataSource = new JRBeanCollectionDataSource(assessList);

      // レポートを生成する
      byte[] datas = reportService.exportReportToPdf(REPORT_FILE_NAME, paramsMap, dataSource);

      return ApiResult.success(datas);
    } catch (Exception ex) {
      LOG.error(ex.toString(), ex);

      throw new SystemException(ex.toString(), ex);
    }
  }

  /**
   * 現場名検索.
   *
   * @param list 実行予算リスト
   * @param constrSiteNm 現場名
   * @return 検索した実行予算リスト
   */
  private List<AssessInfoDto> compareKanaItem(List<AssessInfoDto> list, String constrSiteNm) {
    if (list == null) {
      return null;
    }
    return list.stream()
        .filter(
            assess ->
                (TextUtils.matchesIgnoringKanaWidth(assess.getConstrSiteNm(), constrSiteNm)
                    || TextUtils.matchesIgnoringKanaWidth(
                        assess.getConstrSiteKnNm(), constrSiteNm)))
        .collect(Collectors.toList());
  }
}
