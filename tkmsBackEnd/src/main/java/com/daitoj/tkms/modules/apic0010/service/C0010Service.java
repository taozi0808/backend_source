package com.daitoj.tkms.modules.apic0010.service;

import com.daitoj.tkms.modules.apic0010.repository.C0010Repository;
import com.daitoj.tkms.modules.apic0010.service.dto.C0010ReturnData;
import com.daitoj.tkms.modules.apic0010.service.dto.C0010S01Dto;
import com.daitoj.tkms.modules.apic0010.service.dto.C0010S02Dto;
import com.daitoj.tkms.modules.apic0010.service.dto.C0010S03Dto;
import com.daitoj.tkms.modules.apic0010.service.dto.GaisanInfoDto;
import com.daitoj.tkms.modules.common.constants.CommonConstants;
import com.daitoj.tkms.modules.common.constants.Message;
import com.daitoj.tkms.modules.common.service.ReportService;
import com.daitoj.tkms.modules.common.service.SystemException;
import com.daitoj.tkms.modules.common.service.dto.ApiResult;
import com.daitoj.tkms.modules.common.service.dto.PaginationMeta;
import com.daitoj.tkms.modules.common.utils.DateUtils;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletResponse;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;
import java.text.DecimalFormat;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.QuoteMode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

/** 概算一覧ビジネスロジック */
@Service
public class C0010Service {

  private static final Logger LOG = LoggerFactory.getLogger(C0010Service.class);

  /** CSV日付フォーマット */
  private static final String CSV_DATE_FORMAT = "yyyy年MM年dd日";

  /** 帳票日付フォーマット */
  private static final String PDF_DATE_FORMAT = "yyyy年MM年dd日HH:mm:ss";

  /* 金額フォマード */
  private static final String MONEY_FORMAT = "￥#,##0";

  /** CSVヘッダ */
  private static final String[] CSV_HEADER = {
    "案件コード",
    "案件枝コード",
    "案件名",
    "概算コード",
    "顧客コード",
    "顧客名",
    "見積提出期限",
    "概算金額",
    "着工希望時期",
    "完工希望時期",
    "概算担当部門コード",
    "概算部門",
    "概算担当者コード",
    "概算担当者"
  };

  /** 概算一覧のクエリ */
  private final C0010Repository c0010Repository;

  /** メッセージ */
  private final MessageSource messageSource;

  /** レポートサービス */
  private final ReportService reportService;

  /** fasterxml.jacksonのObjectMapper */
  private final ObjectMapper objectMapper;

  /** CSVファイル名 */
  public static final String APP_NAME = "概算一覧";

  /** レポートファイル名 */
  public static final String REPORT_FILE_NAME = "WebC0020.jasper";

  /** コンストラクタ */
  public C0010Service(
      C0010Repository c0010Repository,
      MessageSource messageSource,
      ReportService reportService,
      ObjectMapper objectMapper) {
    this.c0010Repository = c0010Repository;
    this.messageSource = messageSource;
    this.reportService = reportService;
    this.objectMapper = objectMapper;
  }

  /**
   * 初期表示
   *
   * @param inDto 概算情報取得パラメータ
   * @return 概算情報取得結果
   */
  public ApiResult<C0010ReturnData> getInitInfo(C0010S01Dto inDto, Pageable pageable) {
    try {
      // 概算情報を取得
      Page<GaisanInfoDto> ankenList = c0010Repository.getGaisanInfo(pageable);

      // 戻り値
      C0010ReturnData returnData = new C0010ReturnData();
      // 概算情報リスト
      returnData.setListGaisanInfo(ankenList.getContent());

      // ページ情報
      PaginationMeta meta = new PaginationMeta(ankenList);

      return ApiResult.success(returnData, meta);

    } catch (Exception ex) {
      LOG.error(ex.toString(), ex);

      throw ex;
    }
  }

  /**
   * 検索処理
   *
   * @param inDto 概算情報取得パラメータ
   * @return 概算情報取得結果
   */
  public ApiResult<C0010ReturnData> getAnkenInfo(C0010S01Dto inDto, Pageable pageable) {
    try {
      // 概算情報を取得
      Page<GaisanInfoDto> ankenList =
          c0010Repository.searchGaisanInfo(
              inDto.getProjectCd(),
              inDto.getProjectNm(),
              inDto.getRoughEstCd(),
              inDto.getCustomerCd(),
              inDto.getCustomerName(),
              inDto.getEstSubmitDueDtStart() == null
                  ? null
                  : inDto.getEstSubmitDueDtStart().replace("-", ""),
              inDto.getEstSubmitDueDtEnd() == null
                  ? null
                  : inDto.getEstSubmitDueDtEnd().replace("-", ""),
              inDto.getOrgNm(),
              inDto.getEmpNm(),
              inDto.getGaisanSakusei().equals("true") ? "1" : "0",
              Pageable.unpaged());

      // 取得件数が０件だった場合
      if (CollectionUtils.isEmpty(ankenList.getContent())) {
        // メッセージ
        String msg =
            messageSource.getMessage(Message.MSGID_K00001, null, LocaleContextHolder.getLocale());

        LOG.info(msg);

        // 結果情報
        return ApiResult.error(Message.MSGID_K00001, msg);
      }

      // 戻り値
      C0010ReturnData returnData = new C0010ReturnData();
      // 概算情報リスト
      returnData.setListGaisanInfo(ankenList.getContent());

      // ページ情報
      PaginationMeta meta = new PaginationMeta(ankenList);

      return ApiResult.success(returnData, meta);

    } catch (Exception ex) {
      LOG.error(ex.toString(), ex);

      throw ex;
    }
  }

  /**
   * CSV出力
   *
   * @param inDto 概算情報取得パラメータ
   * @param encodedFileName Stream
   * @param response response
   */
  public ApiResult<?> downLoadCsv(
      C0010S02Dto inDto, String encodedFileName, HttpServletResponse response) {
    try {
      // ヘッダ設定（カンマ区切り）
      CSVFormat csvFormat =
          CSVFormat.DEFAULT
              .builder()
              .setDelimiter(',')
              .setQuoteMode(QuoteMode.ALL)
              .setHeader(CSV_HEADER)
              .get();

      // 概算情報を取得
      Page<GaisanInfoDto> ankenList =
          c0010Repository.searchGaisanInfo(
              inDto.getProjectCd(),
              inDto.getProjectNm(),
              inDto.getRoughEstCd(),
              inDto.getCustomerCd(),
              inDto.getCustomerName(),
              inDto.getEstSubmitDueDtStart() == null
                  ? null
                  : inDto.getEstSubmitDueDtStart().replace("-", ""),
              inDto.getEstSubmitDueDtEnd() == null
                  ? null
                  : inDto.getEstSubmitDueDtEnd().replace("-", ""),
              inDto.getOrgNm(),
              inDto.getEmpNm(),
              inDto.getEstSubmitDueDtStart() == null
                  ? "0"
                  : inDto.getGaisanSakusei().equals("true") ? "1" : "0",
              Pageable.unpaged());

      if (!CollectionUtils.isEmpty(ankenList.getContent())
          && ankenList.getTotalElements() > CommonConstants.SEARCH_MAX_COUNT) {
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
        if (!CollectionUtils.isEmpty(ankenList.getContent())) {
          for (GaisanInfoDto ankenInfo : ankenList) {
            // 明細行設定
            printer.printRecord(
                ankenInfo.getProjectCd(),
                ankenInfo.getHisNo(),
                ankenInfo.getProjectNm(),
                ankenInfo.getRoughEstCd(),
                ankenInfo.getCustomerCd(),
                ankenInfo.getCustomerName(),
                ankenInfo.getEstSubmitDueDt(),
                ankenInfo.getRoughEstTotalAmt() != null
                    ? new DecimalFormat(MONEY_FORMAT).format(ankenInfo.getRoughEstTotalAmt())
                    : "",
                ankenInfo.getStartHopeYmd(),
                ankenInfo.getCompHopeYmd(),
                ankenInfo.getOrgCd(),
                ankenInfo.getOrgNm(),
                ankenInfo.getRoughEstPicCd(),
                ankenInfo.getEmpNm());
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
   * 印刷処理
   *
   * @param inDto 概算情報印刷パラメータ
   * @return pdf
   */
  public ApiResult<?> exportReportToPdf(C0010S02Dto inDto) {
    try {
      // 概算情報を取得
      Page<GaisanInfoDto> ankenList =
          c0010Repository.searchGaisanInfo(
              inDto.getProjectCd(),
              inDto.getProjectNm(),
              inDto.getRoughEstCd(),
              inDto.getCustomerCd(),
              inDto.getCustomerName(),
              inDto.getEstSubmitDueDtStart() == null
                  ? null
                  : inDto.getEstSubmitDueDtStart().replace("-", ""),
              inDto.getEstSubmitDueDtEnd() == null
                  ? null
                  : inDto.getEstSubmitDueDtEnd().replace("-", ""),
              inDto.getOrgNm(),
              inDto.getEmpNm(),
              inDto.getEstSubmitDueDtStart() == null
                  ? "0"
                  : inDto.getGaisanSakusei().equals("true") ? "1" : "0",
              Pageable.unpaged());
      if (!CollectionUtils.isEmpty(ankenList.getContent())
          && ankenList.getTotalElements() > CommonConstants.SEARCH_MAX_COUNT) {
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
      C0010S03Dto printDto = new C0010S03Dto();
      // 利用PCのシステム日付
      printDto.setSysDate(inDto.getSysDate().format(DateTimeFormatter.ofPattern(PDF_DATE_FORMAT)));
      printDto.setProjectCd(inDto.getProjectCd());
      printDto.setProjectNm(inDto.getProjectNm());
      printDto.setRoughEstCd(inDto.getRoughEstCd());
      printDto.setCustomerCd(inDto.getCustomerCd());
      printDto.setCustomerName(inDto.getCustomerName());
      printDto.setOrgNm(inDto.getOrgNm());
      printDto.setEmpNm(inDto.getEmpNm());
      printDto.setEstSubmitDueDtStart(
          inDto.getEstSubmitDueDtStart() == null
              ? ""
              : DateUtils.formatDateFromYYYYMMDD(inDto.getEstSubmitDueDtStart(), CSV_DATE_FORMAT));
      printDto.setEstSubmitDueDtEnd(
          inDto.getEstSubmitDueDtEnd() == null
              ? ""
              : DateUtils.formatDateFromYYYYMMDD(inDto.getEstSubmitDueDtEnd(), CSV_DATE_FORMAT));
      printDto.setGaisanSakusei(
          inDto.getEstSubmitDueDtStart() == null
              ? "0"
              : inDto.getGaisanSakusei().equals("true") ? "1" : "0");
      // レポートに渡すパラメータ
      Map<String, Object> paramsMap =
          objectMapper.convertValue(printDto, new TypeReference<Map<String, Object>>() {});

      // データソースを生成する
      JRDataSource dataSource = new JRBeanCollectionDataSource(ankenList.getContent());

      // レポートを生成する
      byte[] datas = reportService.exportReportToPdf(REPORT_FILE_NAME, paramsMap, dataSource);

      return ApiResult.success(datas);
    } catch (Exception ex) {
      LOG.error(ex.toString(), ex);

      throw new SystemException(ex.toString(), ex);
    }
  }
}
