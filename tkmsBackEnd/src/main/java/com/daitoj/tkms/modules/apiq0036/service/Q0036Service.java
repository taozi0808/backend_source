package com.daitoj.tkms.modules.apiq0036.service;

import com.daitoj.tkms.modules.apiq0036.repository.Q0036Repository;
import com.daitoj.tkms.modules.apiq0036.service.dto.*;
import com.daitoj.tkms.modules.common.constants.CommonConstants;
import com.daitoj.tkms.modules.common.constants.Message;
import com.daitoj.tkms.modules.common.repository.MItemListSettingRepository;
import com.daitoj.tkms.modules.common.repository.mapper.MItemSettingMapper;
import com.daitoj.tkms.modules.common.service.ReportService;
import com.daitoj.tkms.modules.common.service.SystemException;
import com.daitoj.tkms.modules.common.service.dto.ApiResult;
import com.daitoj.tkms.modules.common.service.dto.PaginationMeta;
import com.daitoj.tkms.modules.common.utils.DateUtils;
import com.daitoj.tkms.modules.common.utils.PageUtils;
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

/** 作業員名簿業者一覧ビジネスロジック */
@Service
public class Q0036Service {

  private static final Logger LOG = LoggerFactory.getLogger(Q0036Service.class);

  /** 帳票日付フォーマット */
  private static final String PDF_DATE_FORMAT = "yyyy年MM年dd日HH:mm:ss";

  /** CSV日付フォーマット */
  private static final String CSV_DATE_FORMAT = "yyyy年MM年dd日";

  /** CSVヘッダ */
  private static final String[] CSV_HEADER = {
    "No", "注文書No", "注文書金額", "大工種", "業者コード", "一次業者名", "該当着手日", "該当引渡日"
  };

  /** マスタデータリポジトリ */
  private final MItemListSettingRepository mitemListSettingRepository;

  /** 作業員名簿業者一覧リポジトリ */
  private final Q0036Repository q0036Repository;

  /** メッセージ */
  private final MessageSource messageSource;

  /** レポートサービス */
  private final ReportService reportService;

  /** fasterxml.jacksonのObjectMapper */
  private final ObjectMapper objectMapper;

  /** マスタデータのDTOマッパー */
  private final MItemSettingMapper itemSettingMapper;

  /** CSVファイル名 */
  public static final String APP_NAME = "作業員名簿業者一覧";

  /** レポートファイル名 */
  public static final String REPORT_FILE_NAME = "Q0036.jasper";

  /** コンストラクタ */
  public Q0036Service(
      MItemListSettingRepository mitemListSettingRepository,
      Q0036Repository q0036Repository,
      MessageSource messageSource,
      ReportService reportService,
      MItemSettingMapper itemSettingMapper,
      ObjectMapper objectMapper) {
    this.itemSettingMapper = itemSettingMapper;
    this.mitemListSettingRepository = mitemListSettingRepository;
    this.q0036Repository = q0036Repository;
    this.messageSource = messageSource;
    this.reportService = reportService;
    this.objectMapper = objectMapper;
  }

  /**
   * 初期表示
   *
   * @param inDto 作業員名簿業者情報取得パラメータ
   * @return 作業員名簿業者情報取得結果
   */
  public ApiResult<Q0036ReturnData> getInitInfo(Q0036S01Dto inDto, Pageable pageable) {
    try {
      // 作業員名簿業者情報を取得
      Page<SagyouinInfoDto> sagyouinList =
          q0036Repository.findInitInfo(inDto.getBukkenCode(), pageable);

      // 戻り値
      Q0036ReturnData returnData = new Q0036ReturnData();
      // 作業員名簿業者情報リスト
      returnData.setListSagyouinInfo(sagyouinList.getContent());

      // ページ情報
      PaginationMeta meta = new PaginationMeta(sagyouinList);

      return ApiResult.success(returnData, meta);

    } catch (Exception ex) {
      LOG.error(ex.toString(), ex);

      throw new SystemException(ex.toString(), ex);
    }
  }

  /**
   * 検索処理
   *
   * @param inDto 作業員名簿業者情報取得パラメータ
   * @return 作業員名簿業者情報取得結果
   */
  public ApiResult<Q0036ReturnData> getSagyouinInfo(Q0036S01Dto inDto, Pageable pageable) {
    try {
      // 作業員名簿業者情報を取得
      Page<SagyouinInfoDto> sagyouinList =
          q0036Repository.searchSagyouInfoList(
              inDto.getDaikousyu(),
              inDto.getGyoushaCode(),
              inDto.getChuBunshoNo(),
              inDto.getGaitouChakushuNichiStart(),
              inDto.getGaitouChakushuNichiEnd(),
              inDto.getGaitouBikiWataruNichiStart(),
              inDto.getGaitouBikiWataruNichiEnd(),
              pageable);

      // 一次業者名検索
      List<SagyouinInfoDto> searchedList =
          compareKanaItem(sagyouinList.getContent(), inDto.getGyoushaName());

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
      Q0036ReturnData returnData = new Q0036ReturnData();
      // 作業員名簿業者情報リスト
      returnData.setListSagyouinInfo(searchedList);

      // ページ情報
      PaginationMeta meta = new PaginationMeta(sagyouinList);

      return ApiResult.success(returnData, meta);

    } catch (Exception ex) {
      LOG.error(ex.toString(), ex);

      throw new SystemException(ex.toString(), ex);
    }
  }

  /**
   * CSV出力
   *
   * @param inDto 作業員名簿業者情報取得パラメータ
   * @param encodedFileName ファイル名
   * @param response response
   */
  public ApiResult<?> downLoadCsv(
      Q0036S02Dto inDto, String encodedFileName, HttpServletResponse response) {
    try {
      // ヘッダ設定（カンマ区切り）
      CSVFormat csvFormat =
          CSVFormat.DEFAULT
              .builder()
              .setDelimiter(',')
              .setQuoteMode(QuoteMode.ALL)
              .setHeader(CSV_HEADER)
              .get();

      // 作業員名簿業者情報を取得
      Page<SagyouinInfoDto> sagyouinList =
          q0036Repository.searchSagyouInfoList(
              inDto.getDaikousyu(),
              inDto.getGyoushaCode(),
              inDto.getChuBunshoNo(),
              inDto.getGaitouChakushuNichiStart(),
              inDto.getGaitouChakushuNichiEnd(),
              inDto.getGaitouBikiWataruNichiStart(),
              inDto.getGaitouBikiWataruNichiEnd(),
              PageUtils.createMaxPageable());

      // 一次業者名検索
      List<SagyouinInfoDto> searchedList =
          compareKanaItem(sagyouinList.getContent(), inDto.getGyoushaName());

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
          int no = 1;
          for (SagyouinInfoDto sagyouinInfo : searchedList) {
            // 明細行設定
            printer.printRecord(
                no++,
                sagyouinInfo.getChuBunshoNo(),
                sagyouinInfo.getCyuumonsyoKingaku(),
                sagyouinInfo.getDaikousyuName(),
                sagyouinInfo.getKykGyoushaCode(),
                sagyouinInfo.getIchijiGyoushaName(),
                DateUtils.formatDateFromYYYYMMDD(
                    sagyouinInfo.getGaitouChakusyuYmd(), CSV_DATE_FORMAT),
                DateUtils.formatDateFromYYYYMMDD(
                    sagyouinInfo.getGaitouHikiwatashiYmd(), CSV_DATE_FORMAT));
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
   * @param inDto 作業員名簿業者情報印刷パラメータ
   * @return pdf
   */
  public ApiResult<?> exportReportToPdf(Q0036S02Dto inDto) {

    try {
      // 作業員名簿業者情報を取得
      Page<SagyouinInfoDto> sagyouinList =
          q0036Repository.searchSagyouInfoList(
              inDto.getDaikousyu(),
              inDto.getGyoushaCode(),
              inDto.getChuBunshoNo(),
              inDto.getGaitouChakushuNichiStart(),
              inDto.getGaitouChakushuNichiEnd(),
              inDto.getGaitouBikiWataruNichiStart(),
              inDto.getGaitouBikiWataruNichiEnd(),
              PageUtils.createMaxPageable());
      // 一次業者名検索
      List<SagyouinInfoDto> searchedList =
          compareKanaItem(sagyouinList.getContent(), inDto.getGyoushaName());
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

      // データソースを生成する
      JRDataSource dataSource = new JRBeanCollectionDataSource(searchedList);

      // 印刷パラメータ
      Q0036S03Dto printDto = new Q0036S03Dto();
      // 日付パラメータ
      // 利用PCのシステム日付
      printDto.setSysDate(inDto.getSysDate().format(DateTimeFormatter.ofPattern(PDF_DATE_FORMAT)));
      // todo 物件コード？
      printDto.setBukkenCode(inDto.getBukkenCode());
      // 現場コード
      printDto.setGenbaCode(inDto.getGenbaCode());
      // 現場名
      printDto.setGenbaName(inDto.getGenbaName());
      // 注文書No
      printDto.setChuBunshoNo(inDto.getChuBunshoNo());
      // 大工事
      printDto.setDaikousyu(inDto.getDaikousyu());
      // 業者コード
      printDto.setGyoushaCode(inDto.getGyoushaCode());
      // 業者名
      printDto.setGyoushaName(inDto.getGyoushaName());

      // 該当着手日（開始)
      printDto.setGaitouChakushuNichiStart(
          inDto.getGaitouChakushuNichiStart() == null
              ? ""
              : DateUtils.formatDateFromYYYYMMDD(
                  inDto.getGaitouChakushuNichiStart(), CSV_DATE_FORMAT));

      // 該当着手日（終了)
      printDto.setGaitouChakushuNichiEnd(
          inDto.getGaitouChakushuNichiEnd() == null
              ? ""
              : DateUtils.formatDateFromYYYYMMDD(
                  inDto.getGaitouChakushuNichiEnd(), CSV_DATE_FORMAT));
      // 該当引渡日（開始）
      printDto.setGaitouBikiWataruNichiStart(
          inDto.getGaitouBikiWataruNichiStart() == null
              ? ""
              : DateUtils.formatDateFromYYYYMMDD(
                  inDto.getGaitouBikiWataruNichiStart(), CSV_DATE_FORMAT));
      // 該当引渡日（終了）
      printDto.setGaitouBikiWataruNichiEnd(
          inDto.getGaitouBikiWataruNichiEnd() == null
              ? ""
              : DateUtils.formatDateFromYYYYMMDD(
                  inDto.getGaitouBikiWataruNichiEnd(), CSV_DATE_FORMAT));
      // レポートに渡すパラメ
      Map<String, Object> paramsMap =
          objectMapper.convertValue(printDto, new TypeReference<Map<String, Object>>() {});

      // レポートを生成する
      byte[] datas = reportService.exportReportToPdf(REPORT_FILE_NAME, paramsMap, dataSource);

      return ApiResult.success(datas);
    } catch (Exception ex) {
      LOG.error(ex.toString(), ex);

      throw new SystemException(ex.toString(), ex);
    }
  }

  /**
   * 一次業者名検索
   *
   * @param list 作業員名簿業者リスト
   * @param ichijiGyoushaName 一次業者名
   * @return 検索した作業員名簿業者リスト
   */
  private List<SagyouinInfoDto> compareKanaItem(
      List<SagyouinInfoDto> list, String ichijiGyoushaName) {
    if (list == null) {
      return null;
    }
    return list.stream()
        .filter(
            sagyouin ->
                TextUtils.matchesIgnoringKanaWidth(
                    sagyouin.getIchijiGyoushaName(), ichijiGyoushaName))
        .collect(Collectors.toList());
  }
}
