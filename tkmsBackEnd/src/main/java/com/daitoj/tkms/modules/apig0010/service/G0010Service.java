package com.daitoj.tkms.modules.apig0010.service;

import com.daitoj.tkms.modules.apig0010.repository.G0010Repository;
import com.daitoj.tkms.modules.apig0010.service.dto.ExecBgInfoDto;
import com.daitoj.tkms.modules.apig0010.service.dto.G0010ReturnData;
import com.daitoj.tkms.modules.apig0010.service.dto.G0010S01Dto;
import com.daitoj.tkms.modules.apig0010.service.dto.G0010S02Dto;
import com.daitoj.tkms.modules.apig0010.service.dto.G0010S03Dto;
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
import java.util.ArrayList;
import java.util.Comparator;
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

/** 実行予算一覧ビジネスロジック. */
@Service
public class G0010Service {

  /** CSVファイル名. */
  public static final String APP_NAME = "実行予算一覧";

  /** レポートファイル名. */
  public static final String REPORT_FILE_NAME = "WebG0020.jasper";

  private static final Logger LOG = LoggerFactory.getLogger(G0010Service.class);

  /** 帳票日付フォーマット. */
  private static final String PDF_DATE_FORMAT = "yyyy年MM年dd日HH:mm:ss";

  /* CSV日付フォーマット: yyyy年MM月dd日 */
  private static final String DATE_FORAMT = "yyyy年MM月dd日";

  /** CSVヘッダ. */
  private static final String[] CSV_HEADER = {
    "現場コード",
    "現場名",
    "実行予算コード",
    "予算申請日",
    "予算承認日",
    "予算作成部門ID",
    "予算作成部門",
    "予算作成者コード",
    "予算作成者",
    "実行予算金額",
    "発注金額",
    "未発注額",
    "実行予算一覧済金額",
    "未実行予算一覧残"
  };

  /** 実行予算一覧リポジトリ. */
  private final G0010Repository g0010Repository;

  /** メッセージ. */
  private final MessageSource messageSource;

  /** レポートサービス. */
  private final ReportService reportService;

  /** fasterxml.jacksonのObjectMapper. */
  private final ObjectMapper objectMapper;

  /** コンストラクタ. */
  public G0010Service(
      G0010Repository g0010Repository,
      MessageSource messageSource,
      ReportService reportService,
      ObjectMapper objectMapper) {
    this.g0010Repository = g0010Repository;
    this.messageSource = messageSource;
    this.reportService = reportService;
    this.objectMapper = objectMapper;
  }

  /**
   * 初期表示.
   *
   * @param inDto 実行予算情報取得パラメータ
   * @return 実行予算情報取得結果
   */
  public ApiResult<G0010ReturnData> getInitInfo(G0010S01Dto inDto) {
    try {
      // 実行予算情報を取得
      List<ExecBgInfoDto> execBgList = g0010Repository.findInitInfo(inDto.getBelongOfficeCd());

      // 査定情報をマージする
      execBgList = mergeEmpList(execBgList);

      // 戻り値
      G0010ReturnData returnData = new G0010ReturnData();
      for (ExecBgInfoDto finaljikkouyosan : execBgList) {
        if (finaljikkouyosan.getExecBgtTotalAmt() != null
            && finaljikkouyosan.getPoAmt() != null
            && finaljikkouyosan.getAssessTotalAmt() != null) {

          // 未発注金額
          BigDecimal execBgtTotalAmt = finaljikkouyosan.getExecBgtTotalAmt();
          BigDecimal poAmt = finaljikkouyosan.getPoAmt();
          // 未実行予算一覧残
          BigDecimal assessTotalAmt = finaljikkouyosan.getAssessTotalAmt();

          finaljikkouyosan.setUnpaid(execBgtTotalAmt.subtract(poAmt));
          finaljikkouyosan.setUnidentified(poAmt.subtract(assessTotalAmt));
        }
        String paddedCd =
            String.format("%12s", finaljikkouyosan.getConstrSiteCd()).replace(' ', '0');
        String constrSiteCd =
            String.format(
                "%s-%s-%s",
                paddedCd.substring(0, 7), paddedCd.substring(7, 10), paddedCd.substring(10));
        String padded = String.format("%12s", finaljikkouyosan.getExecBgtCd()).replace(' ', '0');
        String execBgtCd =
            String.format(
                "%s-%s-%s", padded.substring(0, 7), padded.substring(7, 10), padded.substring(10));
        finaljikkouyosan.setConstrSiteCd(constrSiteCd);
        finaljikkouyosan.setExecBgtCd(execBgtCd);
      }

      // 実行予算情報リスト
      returnData.setJikkouyosanInfo(execBgList);

      return ApiResult.success(returnData);

    } catch (Exception ex) {
      LOG.error(ex.toString(), ex);

      throw new SystemException(ex.toString(), ex);
    }
  }

  /**
   * 検索処理.
   *
   * @param inDto 実行予算情報取得パラメータ
   * @return 実行予算情報取得結果
   */
  public ApiResult<G0010ReturnData> getJikkouyosanInfo(G0010S01Dto inDto) {
    try {
      // 実行予算情報を取得
      List<ExecBgInfoDto> execBgList =
          g0010Repository.findJikkouyosanInfo(
              inDto.getExecBgtCd(),
              inDto.getConstrSiteCd(),
              inDto.getBgtYmRegDtStart(),
              inDto.getBgtYmRegDtEnd(),
              inDto.getFinalApprDtStart(),
              inDto.getFinalApprDtEnd(),
              inDto.getOrgNm(),
              inDto.getEmpNm(),
              inDto.getBelongOfficeCd(),
              inDto.getBudgetMakingFlg());

      // 査定情報をマージする
      execBgList = mergeEmpList(execBgList);

      // 現場名検索
      List<ExecBgInfoDto> searchedList = compareKanaItem(execBgList, inDto.getConstrSiteNm());
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
      G0010ReturnData returnData = new G0010ReturnData();
      for (ExecBgInfoDto finaljikkouyosan : execBgList) {
        if (finaljikkouyosan.getExecBgtTotalAmt() != null
            && finaljikkouyosan.getPoAmt() != null
            && finaljikkouyosan.getAssessTotalAmt() != null) {

          // 未発注金額
          BigDecimal execBgtTotalAmt = finaljikkouyosan.getExecBgtTotalAmt();
          BigDecimal poAmt = finaljikkouyosan.getPoAmt();
          // 未実行予算一覧残
          BigDecimal assessTotalAmt = finaljikkouyosan.getAssessTotalAmt();

          finaljikkouyosan.setUnpaid(execBgtTotalAmt.subtract(poAmt));
          finaljikkouyosan.setUnidentified(poAmt.subtract(assessTotalAmt));
        }
        String paddedCd =
            String.format("%12s", finaljikkouyosan.getConstrSiteCd()).replace(' ', '0');
        String constrSiteCd =
            String.format(
                "%s-%s-%s",
                paddedCd.substring(0, 7), paddedCd.substring(7, 10), paddedCd.substring(10));
        String padded = String.format("%12s", finaljikkouyosan.getExecBgtCd()).replace(' ', '0');
        String execBgtCd =
            String.format(
                "%s-%s-%s", padded.substring(0, 7), padded.substring(7, 10), padded.substring(10));
        finaljikkouyosan.setConstrSiteCd(constrSiteCd);
        finaljikkouyosan.setExecBgtCd(execBgtCd);
      }

      // 実行予算情報リスト
      returnData.setJikkouyosanInfo(searchedList);

      return ApiResult.success(returnData);

    } catch (Exception ex) {
      LOG.error(ex.toString(), ex);

      throw new SystemException(ex.toString(), ex);
    }
  }

  /**
   * CSV出力.
   *
   * @param inDto 実行予算情報取得パラメータ
   * @param encodedFileName ファイル名
   * @param response response
   */
  public ApiResult<?> downLoadCsv(
      G0010S02Dto inDto, String encodedFileName, HttpServletResponse response) {
    try {
      // ヘッダ設定（カンマ区切り）
      CSVFormat csvFormat =
          CSVFormat.DEFAULT
              .builder()
              .setDelimiter(',')
              .setQuoteMode(QuoteMode.ALL)
              .setHeader(CSV_HEADER)
              .get();

      // 実行予算情報を取得
      List<ExecBgInfoDto> execBgList =
          g0010Repository.findJikkouyosanInfo(
              inDto.getConstrSiteCd(),
              inDto.getExecBgtCd(),
              inDto.getBgtYmRegDtStart(),
              inDto.getBgtYmRegDtEnd(),
              inDto.getFinalApprDtStart(),
              inDto.getFinalApprDtEnd(),
              inDto.getOrgNm(),
              inDto.getEmpNm(),
              inDto.getBelongOfficeCd(),
              inDto.getBudgetMakingFlg());

      // 査定情報をマージする
      execBgList = mergeEmpList(execBgList);

      // 現場名検索
      List<ExecBgInfoDto> searchedList = compareKanaItem(execBgList, inDto.getConstrSiteNm());

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
      for (ExecBgInfoDto finaljikkouyosan : execBgList) {
        if (finaljikkouyosan.getExecBgtTotalAmt() != null
            && finaljikkouyosan.getPoAmt() != null
            && finaljikkouyosan.getAssessTotalAmt() != null) {

          // 未発注金額
          BigDecimal execBgtTotalAmt = finaljikkouyosan.getExecBgtTotalAmt();
          BigDecimal poAmt = finaljikkouyosan.getPoAmt();
          // 未実行予算一覧残
          BigDecimal assessTotalAmt = finaljikkouyosan.getAssessTotalAmt();

          finaljikkouyosan.setUnpaid(execBgtTotalAmt.subtract(poAmt));
          finaljikkouyosan.setUnidentified(poAmt.subtract(assessTotalAmt));
        }
        String paddedCd =
            String.format("%12s", finaljikkouyosan.getConstrSiteCd()).replace(' ', '0');
        String constrSiteCd =
            String.format(
                "%s-%s-%s",
                paddedCd.substring(0, 7), paddedCd.substring(7, 10), paddedCd.substring(10));
        String padded = String.format("%12s", finaljikkouyosan.getExecBgtCd()).replace(' ', '0');
        String execBgtCd =
            String.format(
                "%s-%s-%s", padded.substring(0, 7), padded.substring(7, 10), padded.substring(10));
        finaljikkouyosan.setConstrSiteCd(constrSiteCd);
        finaljikkouyosan.setExecBgtCd(execBgtCd);
      }

      response.setHeader(
          HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + encodedFileName);
      response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);

      Charset charset = Charset.forName(CommonConstants.CSV_CHARSET_SHIFT_JIS);

      try (CSVPrinter printer =
          new CSVPrinter(new OutputStreamWriter(response.getOutputStream(), charset), csvFormat)) {
        // データがある場合
        if (!CollectionUtils.isEmpty(searchedList)) {
          for (ExecBgInfoDto jikkouyosanInfo : searchedList) {
            // 明細行設定
            printer.printRecord(
                jikkouyosanInfo.getConstrSiteCd(),
                jikkouyosanInfo.getConstrSiteNm(),
                jikkouyosanInfo.getExecBgtCd(),
                jikkouyosanInfo.getBgtYmRegDt() != null
                    ? DateUtils.formatDateFromYYYYMMDD(jikkouyosanInfo.getBgtYmRegDt(), DATE_FORAMT)
                    : "",
                jikkouyosanInfo.getFinalApprDt() != null
                    ? DateUtils.formatDateFromYYYYMMDD(
                        jikkouyosanInfo.getFinalApprDt(), DATE_FORAMT)
                    : "",
                jikkouyosanInfo.getOrgNm(),
                jikkouyosanInfo.getBgtCreatePicCd(),
                jikkouyosanInfo.getEmpNm(),
                jikkouyosanInfo.getExecBgtTotalAmt(),
                jikkouyosanInfo.getPoAmt(),
                jikkouyosanInfo.getUnpaid(),
                jikkouyosanInfo.getAssessTotalAmt(),
                jikkouyosanInfo.getUnidentified());
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
   * @param inDto 実行予算情報印刷パラメータ
   * @return pdf
   */
  public ApiResult<?> exportReportToPdf(G0010S02Dto inDto) {
    try {
      // 実行予算情報を取得
      List<ExecBgInfoDto> execBgList =
          g0010Repository.findJikkouyosanInfo(
              inDto.getExecBgtCd(),
              inDto.getConstrSiteCd(),
              inDto.getBgtYmRegDtStart(),
              inDto.getBgtYmRegDtEnd(),
              inDto.getFinalApprDtStart(),
              inDto.getFinalApprDtEnd(),
              inDto.getOrgNm(),
              inDto.getEmpNm(),
              inDto.getBelongOfficeCd(),
              inDto.getBudgetMakingFlg());

      // 査定情報をマージする
      execBgList = mergeEmpList(execBgList);

      // 現場名検索
      List<ExecBgInfoDto> searchedList = compareKanaItem(execBgList, inDto.getConstrSiteNm());

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
      for (ExecBgInfoDto finaljikkouyosan : searchedList) {
        if (finaljikkouyosan.getExecBgtTotalAmt() != null
            && finaljikkouyosan.getPoAmt() != null
            && finaljikkouyosan.getAssessTotalAmt() != null) {

          // 未発注金額
          BigDecimal execBgtTotalAmt = finaljikkouyosan.getExecBgtTotalAmt();
          BigDecimal poAmt = finaljikkouyosan.getPoAmt();
          // 未実行予算一覧残
          BigDecimal assessTotalAmt = finaljikkouyosan.getAssessTotalAmt();

          finaljikkouyosan.setUnpaid(execBgtTotalAmt.subtract(poAmt));
          finaljikkouyosan.setUnidentified(poAmt.subtract(assessTotalAmt));
        }
        String paddedCd =
            String.format("%12s", finaljikkouyosan.getConstrSiteCd()).replace(' ', '0');
        String constrSiteCd =
            String.format(
                "%s-%s-%s",
                paddedCd.substring(0, 7), paddedCd.substring(7, 10), paddedCd.substring(10));
        String padded = String.format("%12s", finaljikkouyosan.getExecBgtCd()).replace(' ', '0');
        String execBgtCd =
            String.format(
                "%s-%s-%s", padded.substring(0, 7), padded.substring(7, 10), padded.substring(10));
        finaljikkouyosan.setConstrSiteCd(constrSiteCd);
        finaljikkouyosan.setExecBgtCd(execBgtCd);
      }

      // 印刷パラメータ
      G0010S03Dto printDto = new G0010S03Dto();
      // 利用PCのシステム日付
      printDto.setSysDate(inDto.getSysDate().format(DateTimeFormatter.ofPattern(PDF_DATE_FORMAT)));
      printDto.setConstrSiteCd(inDto.getConstrSiteCd());
      printDto.setConstrSiteNm(inDto.getConstrSiteNm());
      printDto.setExecBgtCd(inDto.getExecBgtCd());
      printDto.setBgtYmRegDtStart(inDto.getBgtYmRegDtStart());
      printDto.setBgtYmRegDtEnd(inDto.getBgtYmRegDtEnd());
      printDto.setFinalApprDtStart(inDto.getFinalApprDtStart());
      printDto.setFinalApprDtEnd(inDto.getFinalApprDtEnd());
      printDto.setOrgNm(inDto.getOrgNm());
      printDto.setEmpNm(inDto.getEmpNm());
      printDto.setExecBgtTotalAmt(inDto.getExecBgtTotalAmt());
      printDto.setPoAmt(inDto.getPoAmt());
      printDto.setUnpaid(inDto.getUnpaid());
      printDto.setAssessTotalAmt(inDto.getAssessTotalAmt());
      printDto.setUnidentified(inDto.getUnidentified());
      // レポートに渡すパラメータ
      Map<String, Object> paramsMap =
          objectMapper.convertValue(printDto, new TypeReference<Map<String, Object>>() {});

      // データソースを生成する
      JRDataSource dataSource = new JRBeanCollectionDataSource(searchedList);

      // レポートを生成する
      byte[] datas = reportService.exportReportToPdf(REPORT_FILE_NAME, paramsMap, dataSource);

      return ApiResult.success(datas);
    } catch (Exception ex) {
      LOG.error(ex.toString(), ex);

      throw new SystemException(ex.toString(), ex);
    }
  }

  /**
   * 実行予算一覧リストマージする.
   *
   * @param empList 実行予算一覧リスト
   * @return マージ後リスト
   */
  private List<ExecBgInfoDto> mergeEmpList(List<ExecBgInfoDto> empList) {
    return new ArrayList<>(
        empList.stream()
            .collect(
                Collectors.groupingBy(
                    ExecBgInfoDto::getId,
                    Collectors.collectingAndThen(
                        Collectors.toList(),
                        list -> {
                          list.sort(Comparator.comparing(ExecBgInfoDto::getConstrSiteCd));
                          ExecBgInfoDto first = list.get(0);
                          ExecBgInfoDto merged = new ExecBgInfoDto();
                          merged.setId(first.getId());
                          merged.setConstrSiteCd(first.getConstrSiteCd());
                          merged.setConstrSiteNm(first.getConstrSiteNm());
                          merged.setExecBgtCd(first.getExecBgtCd());
                          merged.setBgtYmRegDt(first.getBgtYmRegDt());
                          merged.setFinalApprDt(first.getFinalApprDt());
                          merged.setBgtCreateOrgId(first.getBgtCreateOrgId());
                          merged.setOrgNm(first.getOrgNm());
                          merged.setBgtCreatePicCd(first.getBgtCreatePicCd());
                          merged.setEmpNm(first.getEmpNm());
                          merged.setExecBgtTotalAmt(first.getExecBgtTotalAmt());
                          merged.setPoAmt(first.getPoAmt());
                          merged.setUnpaid(first.getUnpaid());
                          merged.setAssessTotalAmt(first.getAssessTotalAmt());
                          merged.setUnidentified(first.getUnidentified());

                          return merged;
                        })))
            .values()
            .stream()
            .sorted(Comparator.comparing(ExecBgInfoDto::getConstrSiteCd))
            .collect(Collectors.toList()));
  }

  /**
   * 現場名検索.
   *
   * @param list 実行予算リスト
   * @param constrSiteNm 現場名
   * @return 検索した実行予算リスト
   */
  private List<ExecBgInfoDto> compareKanaItem(List<ExecBgInfoDto> list, String constrSiteNm) {
    if (list == null) {
      return null;
    }
    return list.stream()
        .filter(
            execbg ->
                (TextUtils.matchesIgnoringKanaWidth(execbg.getConstrSiteNm(), constrSiteNm)
                    || TextUtils.matchesIgnoringKanaWidth(
                        execbg.getConstrSiteKnNm(), constrSiteNm)))
        .collect(Collectors.toList());
  }
}
