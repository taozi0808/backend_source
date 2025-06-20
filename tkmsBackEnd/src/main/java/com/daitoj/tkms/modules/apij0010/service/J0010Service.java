package com.daitoj.tkms.modules.apij0010.service;

import com.daitoj.tkms.modules.apij0010.repository.J0010Repository;
import com.daitoj.tkms.modules.apij0010.service.dto.J0010S01Dto;
import com.daitoj.tkms.modules.apij0010.service.dto.J0010S02Dto;
import com.daitoj.tkms.modules.apij0010.service.dto.SateiShiharaiInfoDto;
import com.daitoj.tkms.modules.apij0010.service.dto.J0010ReturnData;
import com.daitoj.tkms.modules.common.constants.CommonConstants;
import com.daitoj.tkms.modules.common.constants.Message;
import com.daitoj.tkms.modules.common.service.dto.ApiResult;
import com.daitoj.tkms.modules.common.service.SystemException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;
import org.springframework.http.MediaType;
import org.springframework.http.HttpHeaders;
import org.springframework.context.i18n.LocaleContextHolder;
import java.nio.charset.Charset;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.QuoteMode;
import java.io.OutputStreamWriter;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

/** 連携用支払データ作成ビジネスロジック */
@Service
public class J0010Service {

  private static final Logger LOG = LoggerFactory.getLogger(J0010Service.class);

  /** CSV日付フォーマット */
  private static final String CSV_DATE_FORMAT = "yyyy年MM年dd日";

  /** 帳票日付フォーマット */
  private static final String PDF_DATE_FORMAT = "yyyy年MM年dd日HH:mm:ss";

  /** 連携用支払データ作成のクエリ */
  private final J0010Repository j0010Repository;

  /** メッセージ */
  private final MessageSource messageSource;

  /** 処理区分：新規 */
  public static final String SHORIKUBUN_SINNKI = "1";

  /** CSVヘッダ */
  private static final String[] CSV_HEADER = {
    "協力業者コード", "協力業者名", "現場コード", "現場名", "大工事コード", "大工事名", "小工事コード", "小工事名", "注文書No", "発注金額",
    "前月迄支払金額", "当月支払金額", "支払日", "変更支払金額", "支払合計金額", "未払金額"
  };

  /** CSVファイル名 */
  public static final String APP_NAME = "支払データ（査定）";

  /** コンストラクタ */
  public J0010Service(J0010Repository j0010Repository, MessageSource messageSource) {
    this.j0010Repository = j0010Repository;
    this.messageSource = messageSource;
  }

  /**
   * 初期表示
   *
   * @param inDto 査定支払情報取得パラメータ
   * @return 査定支払情報取得結果
   */
  public ApiResult<J0010ReturnData> getSateiShiharai(J0010S01Dto inDto) {
    try {
      // 査定支払情報を取得
      List<SateiShiharaiInfoDto> ankenList =
          SHORIKUBUN_SINNKI.equals(inDto.getShorikubun())
              ? j0010Repository.getSateiShiharai(
                  inDto.getAssessYm(),
                  inDto.getPartnerVendorCd(),
                  inDto.getCompNm(),
                  inDto.getConstrSiteCd(),
                  inDto.getConstrSiteNm(),
                  inDto.getAssessPaymentDStart(),
                  inDto.getAssessPaymentDStartEnd(),
                  inDto.getPaymentDataCreateFlg())
              : j0010Repository.getSateiShiharai(null, null, null, null, null, null, null, "0");

      // 戻り値
      J0010ReturnData returnData = new J0010ReturnData();
      // 連携用支払データリスト
      returnData.setListSateiShiharaiInfoDto(ankenList);

      return ApiResult.success(returnData);

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
      J0010S02Dto inDto, String encodedFileName, HttpServletResponse response) {
    try {
      // ヘッダ設定（カンマ区切り）
      CSVFormat csvFormat =
          CSVFormat.DEFAULT
              .builder()
              .setDelimiter(',')
              .setQuoteMode(QuoteMode.ALL)
              .setHeader(CSV_HEADER)
              .get();

      // 査定支払情報を取得
      List<SateiShiharaiInfoDto> ankenList = inDto.getListSateiShiharaiInfoDto();

      if (!CollectionUtils.isEmpty(ankenList)
          && ankenList.size() > CommonConstants.SEARCH_MAX_COUNT) {
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
      // 査定支払情報更新API １．支払データ作成フラグを更新する
      //        j0010Repository.save();

      response.setHeader(
          HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + encodedFileName);
      response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);
      Charset charset = Charset.forName(CommonConstants.CSV_CHARSET_SHIFT_JIS);
      try (CSVPrinter printer =
          new CSVPrinter(new OutputStreamWriter(response.getOutputStream(), charset), csvFormat)) {
        // データがある場合
        if (!CollectionUtils.isEmpty(ankenList)) {
          for (SateiShiharaiInfoDto ankenInfo : ankenList) {
            // 明細行設定
            printer.printRecord(
                ankenInfo.getPartnerVendorCd(),
                ankenInfo.getCompNm(),
                ankenInfo.getConstrSiteCd(),
                ankenInfo.getConstrSiteNm(),
                ankenInfo.getMajorWorkCd(),
                ankenInfo.getMajorWorkNm(),
                ankenInfo.getMinorWorkCd(),
                ankenInfo.getMinorWorkNm(),
                ankenInfo.getPoNo());
          }
        }
      }
      return ApiResult.success(null);
    } catch (Exception ex) {
      LOG.error(ex.toString(), ex);

      throw new SystemException(ex.toString(), ex);
    }
  }
}
