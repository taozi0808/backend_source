package com.daitoj.tkms.modules.apir0040.service;

import com.daitoj.tkms.domain.MItemListSetting;
import com.daitoj.tkms.domain.MPosition;
import com.daitoj.tkms.modules.apir0040.repository.R0040Repository;
import com.daitoj.tkms.modules.apir0040.service.dto.EmpInfoDto;
import com.daitoj.tkms.modules.apir0040.service.dto.R0040ReturnData;
import com.daitoj.tkms.modules.apir0040.service.dto.R0040S01Dto;
import com.daitoj.tkms.modules.apir0040.service.dto.R0040S02Dto;
import com.daitoj.tkms.modules.apir0040.service.dto.R0040S03Dto;
import com.daitoj.tkms.modules.common.constants.CommonConstants;
import com.daitoj.tkms.modules.common.constants.MasterData;
import com.daitoj.tkms.modules.common.constants.Message;
import com.daitoj.tkms.modules.common.repository.MItemListSettingRepository;
import com.daitoj.tkms.modules.common.repository.MPositionRepository;
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
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
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

/** 社員一覧ビジネスロジック. */
@Service
public class R0040Service {
  private static final Logger LOG = LoggerFactory.getLogger(R0040Service.class);

  /* 帳票日付フォーマット */
  private static final String PDF_DATE_FORMAT = "yyyy年MM年dd日HH:mm:ss";

  /* CSVヘッダ */
  private static final String[] CSV_HEADER = {
    "社員コード", "氏名", "性別", "職種コード", "職種", "所属部コード", "所属部名", "所属課コード", "所属課名", "役職コード", "役職名",
  };

  /* 役職情報リポジトリ */
  private final MPositionRepository mpositionRepository;

  /* マスタデータリポジトリ */
  private final MItemListSettingRepository mitemListSettingRepository;

  /* 社員一覧のクエリ */
  private final R0040Repository r0040Repository;

  /* メッセージ */
  private final MessageSource messageSource;

  /* レポートサービス */
  private final ReportService reportService;

  /* fasterxml.jacksonのObjectMapper */
  private final ObjectMapper objectMapper;

  /* CSVファイル名 */
  public static final String APP_NAME = "社員一覧";

  /* レポートファイル名 */
  public static final String REPORT_FILE_NAME = "WebR0050.jasper";

  /**
   * コンストラクタ.
   *
   * @param mpositionRepository 役職情報リポジトリ
   * @param mitemListSettingRepository マスタデータリポジトリ
   * @param r0040Repository 社員一覧リポジトリ
   * @param messageSource メッセージ
   * @param reportService 印刷サービス
   * @param objectMapper fasterxml.jacksonのObjectMapper
   */
  public R0040Service(
      MPositionRepository mpositionRepository,
      MItemListSettingRepository mitemListSettingRepository,
      R0040Repository r0040Repository,
      MessageSource messageSource,
      ReportService reportService,
      ObjectMapper objectMapper) {
    this.mpositionRepository = mpositionRepository;
    this.mitemListSettingRepository = mitemListSettingRepository;
    this.r0040Repository = r0040Repository;
    this.messageSource = messageSource;
    this.reportService = reportService;
    this.objectMapper = objectMapper;
  }

  /**
   * 初期表示.
   *
   * @return 社員情報取得結果
   */
  public ApiResult<R0040ReturnData> getInitInfo() {

    try {
      // 社員情報を取得
      List<EmpInfoDto> empInfoList = r0040Repository.findInitInfo();

      // 性別情報リストを取得
      List<MItemListSetting> sexInfoList =
          mitemListSettingRepository
              .findById_ItemClassCdAndId_EffectiveStartDtLessThanEqualOrderByDisplayOrder(
                  MasterData.ITEM_CLASS_CD_D0013, DateUtils.formatNow(DateUtils.DATE_FORMAT));
      // 役職情報リストを取得
      List<MPosition> positionInfoList = mpositionRepository.findAll();

      // 戻り値
      R0040ReturnData returnData = new R0040ReturnData();
      // 社員情報リスト
      returnData.setListEmpInfo(empInfoList);
      // 性別情報リスト
      returnData.setListSexInfo(sexInfoList);
      // 役職情報リスト
      returnData.setListPositionInfo(positionInfoList);

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
   * 社員情報の検索処理.
   *
   * @param inDto パラメータ
   * @return 社員情報
   */
  public ApiResult<R0040ReturnData> getEmpInfo(R0040S01Dto inDto) {

    try {
      // 社員情報を取得
      List<EmpInfoDto> empInfoList =
          r0040Repository.findEmpInfo(
              inDto.getEmpCd(),
              inDto.getSex(),
              inDto.getJobTypeNm(),
              inDto.getBelongOrgNm(),
              inDto.getBelongSectionNm(),
              inDto.getPositionCd(),
              inDto.getShowResign());
      // 社員名検索
      empInfoList = compareKanaItem(empInfoList, inDto.getEmpNm());

      // 取得件数が０件だった場合
      if (CollectionUtils.isEmpty(empInfoList)) {
        // メッセージ
        String msg =
            messageSource.getMessage(Message.MSGID_K00001, null, LocaleContextHolder.getLocale());
        LOG.info(msg);
        // 結果情報
        return ApiResult.error(Message.MSGID_K00001, msg);
      }

      // 戻り値
      R0040ReturnData returnData = new R0040ReturnData();
      // 社員情報リスト
      returnData.setListEmpInfo(empInfoList);
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
   * CSV出力処理.
   *
   * @param inDto パラメータ
   * @param encodedFileName ファイル名
   * @param response レスポンス
   * @return 無し
   */
  public ApiResult<?> downLoadCsv(
      R0040S02Dto inDto, String encodedFileName, HttpServletResponse response) {

    try {
      // ヘッダ設定（カンマ区切り）
      CSVFormat csvFormat =
          CSVFormat.DEFAULT
              .builder()
              .setDelimiter(',')
              .setQuoteMode(QuoteMode.ALL)
              .setHeader(CSV_HEADER)
              .get();

      // 社員情報を取得
      List<EmpInfoDto> empInfoList =
          r0040Repository.findEmpInfo(
              inDto.getEmpCd(),
              inDto.getSex(),
              inDto.getJobTypeNm(),
              inDto.getBelongOrgNm(),
              inDto.getBelongSectionNm(),
              inDto.getPositionCd(),
              inDto.getShowResign());
      // 氏名を検索
      empInfoList = compareKanaItem(empInfoList, inDto.getEmpNm());

      // 最大件数を超えた場合
      if (!CollectionUtils.isEmpty(empInfoList)
          && empInfoList.size() > CommonConstants.SEARCH_MAX_COUNT) {
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

      // httpヘッダの編集
      response.setHeader(
          HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + encodedFileName);
      response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);
      Charset charset = Charset.forName(CommonConstants.CSV_CHARSET_SHIFT_JIS);

      // CSVファイル生成
      try (CSVPrinter printer =
          new CSVPrinter(new OutputStreamWriter(response.getOutputStream(), charset), csvFormat)) {

        // データがある場合
        if (!CollectionUtils.isEmpty(empInfoList)) {

          // 社員レコードをCSVに書き出し
          for (EmpInfoDto emp : empInfoList) {

            /* CSVレコード構成
             * 1. 社員コード
             * 2. 社員名
             * 3. 性別
             * 4. 職種コード
             * 5. 職種名
             * 6. 所属部コード
             * 7. 所属部名
             * 8. 所属課コード
             * 9. 所属課名
             * 10.役職コード
             * 11.役職名
             */
            printer.printRecord(
                emp.getEmpCd(),
                emp.getEmpNm(),
                emp.getSex(),
                emp.getJobTypeK(),
                emp.getJobTypeNm(),
                emp.getBelongOrgCd(),
                emp.getBelongOrgNm(),
                emp.getBelongSectionCd(),
                emp.getBelongSectionNm(),
                emp.getPositionCd(),
                emp.getPosition());
          }
        }
      }

      // API応答を返却
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
   * @return 無し
   */
  public ApiResult<?> exportReportToPdf(R0040S02Dto inDto) {

    try {
      // 社員情報を取得
      List<EmpInfoDto> empInfoList =
          r0040Repository.findEmpInfo(
              inDto.getEmpCd(),
              inDto.getSex(),
              inDto.getJobTypeNm(),
              inDto.getBelongOrgNm(),
              inDto.getBelongSectionNm(),
              inDto.getPositionCd(),
              inDto.getShowResign());
      // 社員名検索
      empInfoList = compareKanaItem(empInfoList, inDto.getEmpNm());

      // 最大件数を超えた場合
      if (!CollectionUtils.isEmpty(empInfoList)
          && empInfoList.size() > CommonConstants.SEARCH_MAX_COUNT) {
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
      R0040S03Dto printDto = new R0040S03Dto();
      // 利用PCのシステム日付
      printDto.setSysDate(inDto.getSysDate().format(DateTimeFormatter.ofPattern(PDF_DATE_FORMAT)));
      // 社員コード
      printDto.setEmpCd(inDto.getEmpCd());
      // 氏名
      printDto.setEmpNm(inDto.getEmpNm());
      // 性別
      printDto.setSexNm(inDto.getSexNm());
      // 職種
      printDto.setJobTypeNm(inDto.getJobTypeNm());
      // 所属部
      printDto.setBelongOrgNm(inDto.getBelongOrgNm());
      // 所属課
      printDto.setBelongSectionNm(inDto.getBelongSectionNm());
      // 役職
      printDto.setPositionNm(inDto.getPositionNm());
      // 退職者
      printDto.setShowResign(inDto.getShowResign());

      // レポートに渡すパラメータ
      Map<String, Object> paramsMap =
          objectMapper.convertValue(printDto, new TypeReference<Map<String, Object>>() {});
      // データソースを生成する
      JRDataSource dataSource = new JRBeanCollectionDataSource(empInfoList);

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
   * 社員名検索.
   *
   * @param empList 社員リスト
   * @param empNm 社員名
   * @return 社員情報
   */
  private List<EmpInfoDto> compareKanaItem(List<EmpInfoDto> empList, String empNm) {
    if (StringUtils.isEmpty(empNm) || CollectionUtils.isEmpty(empList)) {
      return empList;
    }
    return empList.stream()
        .filter(empInfo -> (TextUtils.matchesIgnoringKanaWidth(empInfo.getEmpNm(), empNm)))
        .collect(Collectors.toList());
  }

}
