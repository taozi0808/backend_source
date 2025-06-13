package com.daitoj.tkms.modules.apiq0035.service;

import com.daitoj.tkms.modules.apiq0035.repository.Q0035Repository;
import com.daitoj.tkms.modules.apiq0035.service.dto.GenbaInfoDto;
import com.daitoj.tkms.modules.apiq0035.service.dto.Q0035ReturnData;
import com.daitoj.tkms.modules.apiq0035.service.dto.Q0035S01Dto;
import com.daitoj.tkms.modules.apiq0035.service.dto.Q0035S02Dto;
import com.daitoj.tkms.modules.apiq0035.service.dto.Q0035S03Dto;
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
import java.nio.charset.Charset;
import java.time.format.DateTimeFormatter;
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

/**
 * 作業員名簿物件情報ビジネスロジック.
 */
@Service
public class Q0035Service {

  private static final Logger LOG = LoggerFactory.getLogger(Q0035Service.class);

  /**
   * DB日付フォーマット.
   */
  private static final String DATE_FORMAT = "yyyyMMdd";

  /**
   * 帳票日付フォーマットYYYYMMDDHHMMSS.
   */
  private static final String PDF_DATE_FORMAT_YYYYMMDDHHMMSS = "yyyy年MM年dd日HH:mm:ss";

  /**
   * 帳票日付フォーマットYYYYMMDD.
   */
  private static final String PDF_DATE_FORMAT_YYYYMMDD = "yyyy年MM年dd日";

  /**
   * CSV日付フォーマット.
   */
  private static final String CSV_DATE_FORMAT = "yyyy年MM年dd日";

  /**
   * CSVヘッダ.
   */
  private static final String[] CSV_HEADER = {
      "No",
      "物件コード",
      "物件名",
      "専任技術者コード",
      "専任技術者",
      "現場着手日",
      "現場引渡日"
  };

  /**
   * 現場情報のクエリ.
   */
  private final Q0035Repository q0035Repository;

  /**
   * メッセージ.
   */
  private final MessageSource messageSource;

  /**
   * レポートサービス.
   */
  private final ReportService reportService;

  /**
   * fasterxml.jacksonのObjectMapper.
   */
  private final ObjectMapper objectMapper;

  /**
   * CSVファイル名.
   */
  public static final String APP_NAME = "現場一覧";

  /**
   * レポートファイル名.
   */
  public static final String REPORT_FILE_NAME = "WebQ0041.jasper";

  /**
   * コンストラクタ.
   */
  public Q0035Service(
      Q0035Repository q0035Repository,
      MessageSource messageSource,
      ReportService reportService,
      ObjectMapper objectMapper) {
    this.q0035Repository = q0035Repository;
    this.messageSource = messageSource;
    this.reportService = reportService;
    this.objectMapper = objectMapper;
  }

  /**
   * 初期表示.
   *
   * @param inDto 現場情報取得パラメータ
   * @return 現場情報取得結果
   */
  public ApiResult<Q0035ReturnData> getInitInfo(Q0035S01Dto inDto) {
    try {

      List<GenbaInfoDto> sateiList = q0035Repository.findInitInfo();
      // 戻り値
      Q0035ReturnData returnData = new Q0035ReturnData();
      // 現場情報リスト
      returnData.setGenbaInfo(sateiList);
      return ApiResult.success(returnData);

    } catch (Exception ex) {
      LOG.error(ex.toString(), ex);

      throw new SystemException(ex.toString(), ex);
    }
  }

  /**
   * 検索処理.
   *
   * @param inDto 作業員名簿物件一覧情報取得パラメータ
   * @return 作業員名簿物件一覧情報取得結果
   */
  public ApiResult<Q0035ReturnData> getSateiInfo(Q0035S01Dto inDto) {
    try {
      // 作業員名簿物件一覧情報を取得
      List<GenbaInfoDto> projectSiteList =
          q0035Repository.findSateiInfo(
              inDto.getProjectSiteCd(),
              inDto.getConstrSiteStartYmdFrom(),
              inDto.getConstrSiteStartYmdTo(),
              inDto.getConstrSiteDeliveryYmdFrom(),
              inDto.getConstrSiteDeliveryYmdTo(),
              inDto.getDocSubmissionStatus());

      projectSiteList = getGenbaInfoDtos(
          projectSiteList,
          inDto.getProjectSiteNm(),
          inDto.getFtEngineerNm());

      // 取得件数が０件だった場合
      if (CollectionUtils.isEmpty(projectSiteList)) {
        // メッセージ
        String msg =
            messageSource.getMessage(Message.MSGID_K00001, null, LocaleContextHolder.getLocale());

        LOG.info(msg);

        // 結果情報
        return ApiResult.error(Message.MSGID_K00001, msg);
      }

      // 戻り値
      Q0035ReturnData returnData = new Q0035ReturnData();
      // 現場情報リスト
      returnData.setGenbaInfo(projectSiteList);

      return ApiResult.success(returnData);

    } catch (Exception ex) {
      LOG.error(ex.toString(), ex);

      throw new SystemException(ex.toString(), ex);
    }
  }

  /**
   * CSV出力.
   *
   * @param inDto           作業員名簿物件情報取得パラメータ
   * @param encodedFileName ファイル名
   * @param response        response
   */
  public ApiResult<?> downLoadCsv(
      Q0035S02Dto inDto, String encodedFileName, HttpServletResponse response) {
    try {
      // ヘッダ設定（カンマ区切り）
      CSVFormat csvFormat =
          CSVFormat.DEFAULT
              .builder()
              .setDelimiter(',')
              .setQuoteMode(QuoteMode.ALL)
              .setHeader(CSV_HEADER)
              .get();

      // 案件情報を取得
      List<GenbaInfoDto> projectSiteList = q0035Repository.findSateiInfo(
          inDto.getProjectSiteCd(),
          inDto.getConstrSiteStartYmdFrom(),
          inDto.getConstrSiteStartYmdTo(),
          inDto.getConstrSiteDeliveryYmdFrom(),
          inDto.getConstrSiteDeliveryYmdTo(),
          inDto.getDocSubmissionStatus());

      projectSiteList = getGenbaInfoDtos(
          projectSiteList,
          inDto.getProjectSiteNm(),
          inDto.getFtEngineerNm());

      if (!CollectionUtils.isEmpty(projectSiteList)
          && projectSiteList.size() > CommonConstants.SEARCH_MAX_COUNT) {
        // メッセージ
        String msg =
            messageSource.getMessage(
                Message.MSGID_00000,
                new Object[]{CommonConstants.SEARCH_MAX_COUNT},
                LocaleContextHolder.getLocale());

        LOG.info(msg);

        // 結果情報
        return ApiResult.error(Message.MSGID_00000, msg);
      }

      if (!CollectionUtils.isEmpty(projectSiteList)
          && projectSiteList.size() > CommonConstants.SEARCH_MAX_COUNT) {
        // メッセージ
        String msg =
            messageSource.getMessage(
                Message.MSGID_00000,
                new Object[]{CommonConstants.SEARCH_MAX_COUNT},
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
               new CSVPrinter(new OutputStreamWriter(response.getOutputStream(), charset),
                   csvFormat)) {
        // データがある場合
        if (!CollectionUtils.isEmpty(projectSiteList)) {
          int no = 1;
          for (GenbaInfoDto projectSiteInfo : projectSiteList) {
            // 明細行設定
            printer.printRecord(
                no++,
                projectSiteInfo.getProjectSiteCd(),
                projectSiteInfo.getProjectSiteNm(),
                projectSiteInfo.getFtEngineerCd(),
                projectSiteInfo.getFtEngineerNm(),
                DateUtils.formatDateFromYYYYMMDD(projectSiteInfo.getConstrSiteStartYmd(),
                    CSV_DATE_FORMAT),
                DateUtils.formatDateFromYYYYMMDD(projectSiteInfo.getConstrSiteDeliveryYmd(),
                    CSV_DATE_FORMAT));
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
   * @param inDto 作業員名簿物件情報印刷パラメータ
   * @return pdf
   */
  public ApiResult<?> exportReportToPdf(Q0035S02Dto inDto) {
    try {
      List<GenbaInfoDto> projectSiteList = q0035Repository.findSateiInfo(
          inDto.getProjectSiteCd(),
          inDto.getConstrSiteStartYmdFrom(),
          inDto.getConstrSiteStartYmdTo(),
          inDto.getConstrSiteDeliveryYmdFrom(),
          inDto.getConstrSiteDeliveryYmdTo(),
          inDto.getDocSubmissionStatus());

      projectSiteList = getGenbaInfoDtos(
          projectSiteList,
          inDto.getProjectSiteNm(),
          inDto.getFtEngineerNm());

      if (!CollectionUtils.isEmpty(projectSiteList)
          && projectSiteList.size() > CommonConstants.SEARCH_MAX_COUNT) {
        // メッセージ
        String msg =
            messageSource.getMessage(
                Message.MSGID_00000,
                new Object[]{CommonConstants.SEARCH_MAX_COUNT},
                LocaleContextHolder.getLocale());

        LOG.info(msg);

        // 結果情報
        return ApiResult.error(Message.MSGID_00000, msg);
      }

      // 印刷パラメータ
      Q0035S03Dto printDto = new Q0035S03Dto();
      // 利用PCのシステム日付
      printDto.setSysDate(
          inDto.getSysDate().format(DateTimeFormatter.ofPattern(PDF_DATE_FORMAT_YYYYMMDDHHMMSS)));
      // 物件コード
      printDto.setProjectSiteCd(inDto.getProjectSiteCd());
      // 物件名
      printDto.setProjectSiteNm(inDto.getProjectSiteNm());
      // 専任技術者
      printDto.setFtEngineerNm(inDto.getFtEngineerNm());
      // 現場着手日From
      printDto.setConstrSiteStartYmdFrom(inDto.getConstrSiteStartYmdFrom() == null
          ? ""
          : DateUtils.formatDateFromYYYYMMDD(
          inDto.getConstrSiteStartYmdFrom(), CSV_DATE_FORMAT));
      // 現場着手日To
      printDto.setConstrSiteStartYmdTo(inDto.getConstrSiteStartYmdTo() == null
          ? ""
          : DateUtils.formatDateFromYYYYMMDD(
          inDto.getConstrSiteStartYmdTo(), CSV_DATE_FORMAT));
      // 現場引渡日From
      printDto.setConstrSiteDeliveryYmdFrom(inDto.getConstrSiteDeliveryYmdFrom() == null
          ? "" : DateUtils.formatDateFromYYYYMMDD(
          inDto.getConstrSiteDeliveryYmdFrom(), CSV_DATE_FORMAT));

      // 現場着手日To
      printDto.setConstrSiteDeliveryYmdTo(inDto.getConstrSiteDeliveryYmdTo() == null
          ? "" : DateUtils.formatDateFromYYYYMMDD(
          inDto.getConstrSiteDeliveryYmdTo(), CSV_DATE_FORMAT));

      // レポートに渡すパラメータ
      Map<String, Object> paramsMap =
          objectMapper.convertValue(printDto, new TypeReference<Map<String, Object>>() {
          });

      // データソースを生成する
      JRDataSource dataSource = new JRBeanCollectionDataSource(projectSiteList);

      // レポートを生成する
      byte[] datas = reportService.exportReportToPdf(REPORT_FILE_NAME, paramsMap, dataSource);

      return ApiResult.success(datas);
    } catch (Exception ex) {
      LOG.error(ex.toString(), ex);

      throw new SystemException(ex.toString(), ex);
    }
  }

  /**
   * 物件名、専任技術者検索.
   *
   * @param projectSiteList 作業員名簿業者リスト
   * @param projectSiteNm   物件名
   * @param ftEngineerNm    専任技術者
   * @return 検索した作業員名簿物件一覧リスト
   */
  private List<GenbaInfoDto> getGenbaInfoDtos(
      List<GenbaInfoDto> projectSiteList,
      String projectSiteNm,
      String ftEngineerNm) {
    projectSiteList = projectSiteList.stream().filter(dto ->
            (TextUtils.matchesIgnoringKanaWidth(dto.getProjectSiteNm(), projectSiteNm)
                || TextUtils.matchesIgnoringKanaWidth(dto.getProjectSiteKnNm(),
                projectSiteNm))
                && (TextUtils.matchesIgnoringKanaWidth(dto.getFtEngineerNm(),
                ftEngineerNm)
                || TextUtils.matchesIgnoringKanaWidth(dto.getFtEngineerKnNm(),
                ftEngineerNm))
        ).sorted(Comparator.comparing(GenbaInfoDto::getProjectSiteCd))
        .collect(Collectors.toList());
    return projectSiteList;
  }
}
