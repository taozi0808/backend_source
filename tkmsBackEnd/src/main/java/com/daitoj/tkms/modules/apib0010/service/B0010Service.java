package com.daitoj.tkms.modules.apib0010.service;

import com.daitoj.tkms.domain.MItemListSetting;
import com.daitoj.tkms.modules.apib0010.repository.B0010Repository;
import com.daitoj.tkms.modules.apib0010.service.dto.AnkenInfoDto;
import com.daitoj.tkms.modules.apib0010.service.dto.B0010ReturnData;
import com.daitoj.tkms.modules.apib0010.service.dto.B0010S01Dto;
import com.daitoj.tkms.modules.apib0010.service.dto.B0010S02Dto;
import com.daitoj.tkms.modules.apib0010.service.dto.B0010S03Dto;
import com.daitoj.tkms.modules.apib0010.service.dto.B0010S04Dto;
import com.daitoj.tkms.modules.common.constants.CommonConstants;
import com.daitoj.tkms.modules.common.constants.MasterData;
import com.daitoj.tkms.modules.common.constants.Message;
import com.daitoj.tkms.modules.common.repository.MItemListSettingRepository;
import com.daitoj.tkms.modules.common.repository.mapper.MItemSettingMapper;
import com.daitoj.tkms.modules.common.service.ReportService;
import com.daitoj.tkms.modules.common.service.SystemException;
import com.daitoj.tkms.modules.common.service.dto.ApiResult;
import com.daitoj.tkms.modules.common.service.dto.MitemSettingDto;
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

/** 案件一覧ビジネスロジック */
@Service
public class B0010Service {

  private static final Logger LOG = LoggerFactory.getLogger(B0010Service.class);

  /** 帳票日付フォーマット */
  private static final String PDF_DATE_FORMAT = "yyyy年MM年dd日HH:mm:ss";

  /** CSV日付フォーマット */
  private static final String CSV_DATE_FORMAT = "yyyy年MM年dd日";

    /** 検索日付フォーマット */
    private static final String SELECT_DATE_FORMAT = "yyyyMMddyyyyMMdd";

  /** CSVヘッダ */
  private static final String[] CSV_HEADER = {
    "案件コード",
    "案件枝コード",
    "案件名",
    "顧客コード",
    "顧客名",
    "現場住所",
    "想定金額",
    "受注見込日",
    "着工希望日",
    "営業担当部門コード",
    "営業担当部門",
    "営業担当者コード",
    "営業担当者",
    "進捗度コード",
    "進捗度"
  };

  /** マスタデータリポジトリ */
  private final MItemListSettingRepository mitemListSettingRepository;

  /** 案件一覧リポジトリ */
  private final B0010Repository b0010Repository;

  /** メッセージ */
  private final MessageSource messageSource;

  /** レポートサービス */
  private final ReportService reportService;

  /** fasterxml.jacksonのObjectMapper */
  private final ObjectMapper objectMapper;

  /** マスタデータのDTOマッパー */
  private final MItemSettingMapper itemSettingMapper;

  /** CSVファイル名 */
  public static final String APP_NAME = "案件一覧";

  /** レポートファイル名 */
  public static final String REPORT_FILE_NAME = "WebB0020.jasper";

  /** コンストラクタ */
  public B0010Service(
      MItemListSettingRepository mitemListSettingRepository,
      B0010Repository b0010Repository,
      MessageSource messageSource,
      ReportService reportService,
      MItemSettingMapper itemSettingMapper,
      ObjectMapper objectMapper) {
    this.mitemListSettingRepository = mitemListSettingRepository;
    this.b0010Repository = b0010Repository;
    this.messageSource = messageSource;
    this.reportService = reportService;
    this.itemSettingMapper = itemSettingMapper;
    this.objectMapper = objectMapper;
  }

  /**
   * 初期表示
   *
   * @param inDto 案件情報取得パラメータ
   * @return 案件情報取得結果
   */
  public ApiResult<B0010ReturnData> getInitInfo(B0010S01Dto inDto, Pageable pageable) {
    try {
      // 案件情報を取得
      Page<AnkenInfoDto> ankenList =
          b0010Repository.findInitInfo(
              MasterData.ITEM_CLASS_CD_D0003, DateUtils.formatNow(DateUtils.DATE_FORMAT), pageable);

      // マスタデータを取得
      List<MItemListSetting> mitemList =
          mitemListSettingRepository
              .findById_ItemClassCdAndId_EffectiveStartDtLessThanEqualOrderByDisplayOrder(
                  MasterData.ITEM_CLASS_CD_D0001, DateUtils.formatNow(DateUtils.DATE_FORMAT));

      // マスタデータDtoに変換する
      List<MitemSettingDto> dtoList = itemSettingMapper.toDto(mitemList);

      // 戻り値
      B0010ReturnData returnData = new B0010ReturnData();
      // 案件情報リスト
      returnData.setListAnkenInfo(ankenList.getContent());
      // マスタデータ(受注状態)
      returnData.setMitemSettingD0001(dtoList);

      // ページ情報
      PaginationMeta meta = new PaginationMeta(ankenList);

      return ApiResult.success(returnData, meta);

    } catch (Exception ex) {

      LOG.error(ex.toString(), ex);

      throw new SystemException(
          messageSource.getMessage(Message.MSGID_S0000, null, LocaleContextHolder.getLocale()));
    }
  }

  /**
   * 検索処理
   *
   * @param inDto 案件情報取得パラメータ
   * @return 案件情報取得結果
   */
  public ApiResult<B0010ReturnData> getAnkenInfo(B0010S02Dto inDto, Pageable pageable) {
    try {
      // 案件情報を取得
      Page<AnkenInfoDto> ankenList =
          b0010Repository.findAnkenInfo(
              inDto.getListJyucyuuJyoutai(),
              MasterData.ITEM_CLASS_CD_D0003,
              inDto.getAnkenCode(),
              inDto.getJmYmdStart(),
              inDto.getJmYmdEnd(),
              inDto.getEigyouBumon(),
              inDto.getEigyouTantousya(),
              DateUtils.formatNow(DateUtils.DATE_FORMAT),
              pageable);

      // 顧客名、顧客名検索
      List<AnkenInfoDto> searchedList =
          compareKanaItem(ankenList.getContent(), inDto.getAnkenName(), inDto.getKokyakuName());

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
      B0010ReturnData returnData = new B0010ReturnData();
      // 案件情報リスト
      returnData.setListAnkenInfo(searchedList);

      // ページ情報
      PaginationMeta meta = new PaginationMeta(ankenList);

      return ApiResult.success(returnData, meta);

    } catch (Exception ex) {
      LOG.error(ex.toString(), ex);

      throw new SystemException(
          messageSource.getMessage(Message.MSGID_S0000, null, LocaleContextHolder.getLocale()));
    }
  }

  /**
   * CSV出力
   *
   * @param inDto 案件情報取得パラメータ
   * @param encodedFileName ファイル名
   * @param response response
   */
  public ApiResult<?> downLoadCsv(
      B0010S03Dto inDto, String encodedFileName, HttpServletResponse response) {
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
      Page<AnkenInfoDto> ankenList =
          b0010Repository.findAnkenInfo(
              inDto.getListJyucyuuJyoutai(),
              MasterData.ITEM_CLASS_CD_D0003,
              inDto.getAnkenCode(),
              inDto.getJmYmdStart(),
              inDto.getJmYmdEnd(),
              inDto.getEigyouBumon(),
              inDto.getEigyouTantousya(),
              DateUtils.formatNow(DateUtils.DATE_FORMAT),
              PageUtils.createMaxPageable());

      // 顧客名、顧客名検索
      List<AnkenInfoDto> searchedList =
          compareKanaItem(ankenList.getContent(), inDto.getAnkenName(), inDto.getKokyakuName());

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
          for (AnkenInfoDto ankenInfo : searchedList) {
            // 明細行設定
            printer.printRecord(
                ankenInfo.getAnkenCode(),
                ankenInfo.getHisNo().toString(),
                ankenInfo.getAnkenName(),
                ankenInfo.getKokyakuCode(),
                ankenInfo.getKokyakuName(),
                String.join("", ankenInfo.getGenbaJyuusyo1(), ankenInfo.getGenbaJyuusyo2()),
                ankenInfo.getSouteiKingaku(),
                DateUtils.formatDateFromYYYYMMDD(ankenInfo.getJycyuuMikomiYmd(), CSV_DATE_FORMAT),
                DateUtils.formatDateFromYYYYMMDD(ankenInfo.getCyakkouKibouYmd(), CSV_DATE_FORMAT),
                ankenInfo.getEigyouBumonCode(),
                ankenInfo.getEigyouBumonName(),
                ankenInfo.getEigyouTantousyaCode(),
                ankenInfo.getEigyouTantousyaShiMei(),
                ankenInfo.getShincyokudoCode(),
                ankenInfo.getShincyokudo());
          }
        }
      }

      return ApiResult.success(null);
    } catch (Exception ex) {
      LOG.error(ex.toString(), ex);

      throw new SystemException(
          messageSource.getMessage(Message.MSGID_S0000, null, LocaleContextHolder.getLocale()));
    }
  }

  /**
   * 印刷処理
   *
   * @param inDto 案件情報印刷パラメータ
   * @return pdf
   */
  public ApiResult<?> exportReportToPdf(B0010S03Dto inDto) {
    try {
      // 案件情報を取得
      Page<AnkenInfoDto> ankenList =
          b0010Repository.findAnkenInfo(
              inDto.getListJyucyuuJyoutai(),
              MasterData.ITEM_CLASS_CD_D0003,
              inDto.getAnkenCode(),
              inDto.getJmYmdStart(),
              inDto.getJmYmdEnd(),
              inDto.getEigyouBumon(),
              inDto.getEigyouTantousya(),
              DateUtils.formatNow(DateUtils.DATE_FORMAT),
              PageUtils.createMaxPageable());

      // 顧客名、顧客名検索
      List<AnkenInfoDto> searchedList =
          compareKanaItem(ankenList.getContent(), inDto.getAnkenName(), inDto.getKokyakuName());

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
      B0010S04Dto printDto = new B0010S04Dto();
      // 利用PCのシステム日付
      printDto.setSysDate(inDto.getSysDate().format(DateTimeFormatter.ofPattern(PDF_DATE_FORMAT)));

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

      throw new SystemException(
          messageSource.getMessage(Message.MSGID_S0000, null, LocaleContextHolder.getLocale()));
    }
  }

  /**
   * 顧客名、顧客名検索
   *
   * @param list 案件リスト
   * @param ankenName 案件名
   * @param kokyakuName 顧客名
   * @return 検索した案件リスト
   */
  private List<AnkenInfoDto> compareKanaItem(
      List<AnkenInfoDto> list, String ankenName, String kokyakuName) {
    if (list == null) {
      return null;
    }
    return list.stream()
        .filter(
            anken ->
                (TextUtils.matchesIgnoringKanaWidth(anken.getAnkenName(), ankenName)
                        || TextUtils.matchesIgnoringKanaWidth(anken.getAnkenKnName(), ankenName))
                    && (TextUtils.matchesIgnoringKanaWidth(anken.getKokyakuName(), kokyakuName)
                        || TextUtils.matchesIgnoringKanaWidth(
                            anken.getKokyakuKnName(), kokyakuName)))
        .collect(Collectors.toList());
  }
}
