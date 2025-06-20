package com.daitoj.tkms.modules.apif0010.service;

import com.daitoj.tkms.modules.apif0010.repository.F0010Repository;
import com.daitoj.tkms.modules.apif0010.service.dto.F0010ReturnData;
import com.daitoj.tkms.modules.apif0010.service.dto.F0010S01Dto;
import com.daitoj.tkms.modules.apif0010.service.dto.F0010S02Dto;
import com.daitoj.tkms.modules.apif0010.service.dto.ProjectSiteInfoDto;
import com.daitoj.tkms.modules.common.constants.CommonConstants;
import com.daitoj.tkms.modules.common.constants.Message;
import com.daitoj.tkms.modules.common.service.SystemException;
import com.daitoj.tkms.modules.common.service.dto.ApiResult;
import com.daitoj.tkms.modules.common.utils.DateUtils;
import com.daitoj.tkms.modules.common.utils.TextUtils;
import jakarta.servlet.http.HttpServletResponse;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;
import java.text.DecimalFormat;
import java.util.List;
import java.util.stream.Collectors;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.QuoteMode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

/** 物件一覧ビジネスロジック. */
@Service
public class F0010Service {

  private static final Logger LOG = LoggerFactory.getLogger(F0010Service.class);

  /* CSVヘッダ */
  private static final String[] CSV_HEADER = {
    "物件コード",
    "物件名",
    "案件コード",
    "案件名",
    "担当事業所コード",
    "担当事業所",
    "顧客コード",
    "顧客名",
    "受注年月日",
    "請負金額",
    "着工日",
    "完工日",
    "工事部門コード",
    "工事部門",
    "工事担当者コード",
    "工事担当者",
    "専任技術者コード",
    "専任技術者",
    "施工担当者コード",
    "施工担当者"
  };

  /* 物件一覧のクエリ */
  private final F0010Repository f0010Repository;

  /* メッセージ */
  private final MessageSource messageSource;

  /* CSVファイル名 */
  public static final String APP_NAME = "物件一覧";

  /* 金額フォーマット */
  public static final String MONEY_FORMAT = "￥#,##0";

  /* 日付フォーマット yyyy年MM月dd日 */
  public static final String CSV_DATE_FORMAT = "yyyy年MM月dd日";

  /* 本社コード */
  private static final String OFFICE_HONSHA_CODE = "008";

  /**
   * コンストラクタ.
   *
   * @param f0010Repository 物件一覧のクエリ
   * @param messageSource メッセージ
   */
  public F0010Service(F0010Repository f0010Repository, MessageSource messageSource) {
    this.f0010Repository = f0010Repository;
    this.messageSource = messageSource;
  }

  /**
   * 初期表示.
   *
   * @return 物件情報取得結果
   */
  public ApiResult<F0010ReturnData> getInitInfo(String belongOfficeCd) {
    try {

      // 物件情報を取得
      List<ProjectSiteInfoDto> projectSiteList =
          f0010Repository.findInitInfo(
              OFFICE_HONSHA_CODE.equals(belongOfficeCd) ? null : belongOfficeCd);

      // 戻り値
      F0010ReturnData returnData = new F0010ReturnData();
      // 物件情報リスト
      returnData.setListProjectSiteInfo(projectSiteList);

      // 成功の場合
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
   * @return 物件情報取得結果
   */
  public ApiResult<F0010ReturnData> getProjectSiteInfo(F0010S01Dto inDto) {
    try {

      // 物件情報を取得
      List<ProjectSiteInfoDto> projectSiteInfoList =
          f0010Repository.findProjectSiteInfo(
              inDto.getProjectSiteCd(),
              inDto.getCustomerCd(),
              inDto.getOrderYmdFrom(),
              inDto.getOrderYmdTo(),
              inDto.getIcOfficeNm(),
              inDto.getConstrStartYmdFrom(),
              inDto.getConstrStartYmdTo(),
              inDto.getConstrCompYmdFrom(),
              inDto.getConstrCompYmdTo(),
              inDto.getConstrOrgNm(),
              inDto.getConstrMgrNm(),
              inDto.getFtEngineerNm(),
              inDto.getConstrPicNm(),
              inDto.getBelongOfficeCd());
      // 物件名と顧客名の検索
      projectSiteInfoList =
          compareKanaItem(projectSiteInfoList, inDto.getProjectSiteNm(), inDto.getCustomerNm());

      // 0件を取得した場合
      if (CollectionUtils.isEmpty(projectSiteInfoList)) {
        // メッセージ
        String msg =
            messageSource.getMessage(Message.MSGID_K00001, null, LocaleContextHolder.getLocale());
        LOG.info(msg);
        // 結果情報
        return ApiResult.error(Message.MSGID_K00001, msg);
      }

      // 戻り値
      F0010ReturnData returnData = new F0010ReturnData();
      // 物件情報結果リスト
      returnData.setListProjectSiteInfo(projectSiteInfoList);

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
   * @param inDto 物件情報取得パラメータ
   * @param response response
   */
  public ApiResult<?> downLoadCsv(
      F0010S02Dto inDto, String encodedFileName, HttpServletResponse response) {

    try {

      // CSVフォーマットを構築する
      CSVFormat csvFormat =
          CSVFormat.DEFAULT
              .builder()
              .setDelimiter(',') // フィールド区切り文字をカンマに設定
              .setQuoteMode(QuoteMode.ALL) // 全フィールドをダブルクォーテーションで囲む
              .setHeader(CSV_HEADER) // カラムヘッダーを設定
              .get();

      // 物件情報を取得
      List<ProjectSiteInfoDto> projectSiteInfoList =
          f0010Repository.findProjectSiteInfo(
              inDto.getProjectSiteCd(),
              inDto.getCustomerCd(),
              inDto.getOrderYmdFrom(),
              inDto.getOrderYmdTo(),
              inDto.getIcOfficeNm(),
              inDto.getConstrStartYmdFrom(),
              inDto.getConstrStartYmdTo(),
              inDto.getConstrCompYmdFrom(),
              inDto.getConstrCompYmdTo(),
              inDto.getConstrOrgNm(),
              inDto.getConstrMgrNm(),
              inDto.getFtEngineerNm(),
              inDto.getConstrPicNm(),
              inDto.getBelongOfficeCd());
      projectSiteInfoList =
          compareKanaItem(projectSiteInfoList, inDto.getProjectSiteNm(), inDto.getCustomerNm());

      // 最大件数を超える場合
      if (!CollectionUtils.isEmpty(projectSiteInfoList)
          && projectSiteInfoList.size() > CommonConstants.SEARCH_MAX_COUNT) {
        // メッセージ取得
        String msg =
            messageSource.getMessage(
                Message.MSGID_K00001,
                new Object[] {CommonConstants.SEARCH_MAX_COUNT},
                LocaleContextHolder.getLocale());
        // 情報ログに出力
        LOG.info(msg);
        // APIエラー応答を返却
        return ApiResult.error(Message.MSGID_00000, msg);
      }

      // ファイルダウンロード用のHTTPヘッダー設定
      response.setHeader(
          "Content-Disposition",
          "attachment; filename=\"" + encodedFileName + "\"; filename*=utf-8''" + encodedFileName);
      response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);
      Charset charset = Charset.forName(CommonConstants.CSV_CHARSET_SHIFT_JIS);

      try (CSVPrinter print =
          new CSVPrinter(new OutputStreamWriter(response.getOutputStream(), charset), csvFormat)) {

        // 物件情報リストが空ではない場合
        if (!CollectionUtils.isEmpty(projectSiteInfoList)) {
          /* CSVレコード構成
           * 1.  物件コード
           * 2.  物件名
           * 3.  案件コード
           * 4.  案件名
           * 5.  担当事業所コード
           * 6.  担当事業所
           * 7.  顧客コード
           * 8.  顧客名
           * 9.  受注年月日
           * 10. 請負金額
           * 11. 着工日
           * 12. 完工日
           * 13. 工事部門コード
           * 14. 工事部門
           * 15. 工事担当者コード
           * 16. 工事担当者
           * 17. 専任技術者コード
           * 18. 専任技術者
           * 19. 施工担当者コード
           * 20. 施工担当者
           */
          for (ProjectSiteInfoDto projectSite : projectSiteInfoList) {
            print.printRecord(
                projectSite.getProjectSiteCd(),
                projectSite.getProjectSiteNm(),
                projectSite.getProjectCd(),
                projectSite.getProjectNm(),
                projectSite.getIcOfficeCd(),
                projectSite.getIcOfficeNm(),
                projectSite.getCustomerCd(),
                projectSite.getCustomerNm(),
                DateUtils.formatDateFromYYYYMMDD(projectSite.getOrderYmd(), CSV_DATE_FORMAT),
                new DecimalFormat(MONEY_FORMAT).format(projectSite.getInclTaxCoTotalAmt()),
                DateUtils.formatDateFromYYYYMMDD(projectSite.getConstrStartYmd(), CSV_DATE_FORMAT),
                DateUtils.formatDateFromYYYYMMDD(projectSite.getConstrCompYmd(), CSV_DATE_FORMAT),
                projectSite.getConstrOrgCd(),
                projectSite.getConstrOrgNm(),
                projectSite.getConstrMgrCd(),
                projectSite.getConstMgrNm(),
                projectSite.getFtEngineerCd(),
                projectSite.getFtEngineerNm(),
                projectSite.getConstrPicCd(),
                projectSite.getConstrPicNm());
          }
        }

        // API応答を返却
        return ApiResult.success(null);
      }

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
   * @param inDto 物件情報印刷パラメータ
   * @return pdf
   */
//  public ApiResult<?> exportExcel(F0010S02Dto inDto) {
//
//    try {
//      // 物件情報を取得
//      List<ProjectSiteInfoDto> projectSiteInfoList =
//          f0010Repository.findProjectSiteInfo(
//              inDto.getProjectSiteCd(),
//              inDto.getCustomerCd(),
//              inDto.getOrderYmdFrom(),
//              inDto.getOrderYmdTo(),
//              inDto.getIcOfficeNm(),
//              inDto.getConstrStartYmdFrom(),
//              inDto.getConstrStartYmdTo(),
//              inDto.getConstrCompYmdFrom(),
//              inDto.getConstrCompYmdTo(),
//              inDto.getConstrOrgNm(),
//              inDto.getConstrMgrNm(),
//              inDto.getFtEngineerNm(),
//              inDto.getConstrPicNm(),
//              inDto.getBelongOfficeCd());
//      projectSiteInfoList =
//          compareKanaItem(projectSiteInfoList, inDto.getProjectSiteNm(), inDto.getCustomerNm());
//
//      // 最大件数を超えた場合
//      if (!CollectionUtils.isEmpty(projectSiteInfoList)
//          && projectSiteInfoList.size() > CommonConstants.SEARCH_MAX_COUNT) {
//        // メッセージ
//        String msg =
//            messageSource.getMessage(
//                Message.MSGID_00000,
//                new Object[] {CommonConstants.SEARCH_MAX_COUNT},
//                LocaleContextHolder.getLocale());
//        // 情報ログに出力
//        LOG.info(msg);
//        // APIエラー応答を返却
//        return ApiResult.error(Message.MSGID_00000, msg);
//      }
//
//      // Excelファイルを作成
//      try (XSSFWorkbook workbook = ExcelExporter.export(projectSiteInfoList);
//          ByteArrayOutputStream out = new ByteArrayOutputStream()) {
//        workbook.write(out);
//        return ApiResult.success(out.toByteArray());
//      }
//
//    } catch (Exception ex) {
//      // エラーログ出力とシステム例外のスロー
//      LOG.error(ex.toString(), ex);
//      throw new SystemException(
//          messageSource.getMessage(Message.MSGID_S0000, null, LocaleContextHolder.getLocale()));
//    }
//  }

  /**
   * 物件名と顧客名の検索.
   *
   * @param list 物件情報リスト
   * @param projectSiteNm 物件名
   * @param customerNm 顧客名
   * @return 検索した物件リスト
   */
  private List<ProjectSiteInfoDto> compareKanaItem(
      List<ProjectSiteInfoDto> list, String projectSiteNm, String customerNm) {
    if (CollectionUtils.isEmpty(list)) {
      return list;
    }
    return list.stream()
        .filter(
            projectSite ->
                (TextUtils.matchesIgnoringKanaWidth(projectSite.getProjectSiteNm(), projectSiteNm)
                        || TextUtils.matchesIgnoringKanaWidth(
                            projectSite.getProjectSiteKanaNm(), projectSiteNm))
                    && (TextUtils.matchesIgnoringKanaWidth(
                            projectSite.getCustomerKanaNm(), customerNm)
                        || TextUtils.matchesIgnoringKanaWidth(
                            projectSite.getCustomerNm(), customerNm)))
        .collect(Collectors.toList());
  }

}
