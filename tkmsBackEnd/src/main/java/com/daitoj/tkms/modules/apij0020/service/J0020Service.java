package com.daitoj.tkms.modules.apij0020.service;

import com.daitoj.tkms.domain.TConstrSiteExpDtl;
import com.daitoj.tkms.modules.apij0020.repository.J0020Repository;
import com.daitoj.tkms.modules.apij0020.service.dto.J0020S01Dto;
import com.daitoj.tkms.modules.apij0020.service.dto.J0020S02Dto;
import com.daitoj.tkms.modules.apij0020.service.dto.KeihiShiharaiInfoDto;
import com.daitoj.tkms.modules.apij0020.service.dto.J0020ReturnData;
import com.daitoj.tkms.modules.common.constants.CommonConstants;
import com.daitoj.tkms.modules.common.constants.Message;
import com.daitoj.tkms.modules.common.service.dto.ApiResult;
import com.daitoj.tkms.modules.common.service.SystemException;
import com.daitoj.tkms.modules.common.utils.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import jakarta.servlet.http.HttpServletResponse;

import java.util.ArrayList;
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
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

/** 連携用支払データ作成ビジネスロジック */
@Service
@Transactional
public class J0020Service {

  private static final Logger LOG = LoggerFactory.getLogger(J0020Service.class);

  /** CSV日付フォーマット */
  private static final String CSV_DATE_FORMAT = "yyyy年MM年dd日";

  /** 帳票日付フォーマット */
  private static final String PDF_DATE_FORMAT = "yyyy年MM年dd日HH:mm:ss";

  /** 処理区分：新規 */
  public static final String SHORIKUBUN_SINNKI = "1";

  /** 連携用支払データ作成のクエリ */
  private final J0020Repository j0020Repository;

  /** メッセージ */
  private final MessageSource messageSource;

  /** CSVヘッダ */
  private static final String[] CSV_HEADER = {
    "協力業者コード", "協力業者名", "現場コード", "現場名", "大工事コード", "大工事名", "小工事コード", "小工事名", "注文書No", "発注金額",
    "前月迄支払金額", "当月支払金額", "支払日", "変更支払金額", "支払合計金額", "未払金額"
  };

  /** CSVファイル名 */
  public static final String APP_NAME = "支払データ（経費）";

  /** コンストラクタ */
  public J0020Service(J0020Repository j0010Repository, MessageSource messageSource) {
    this.j0020Repository = j0010Repository;
    this.messageSource = messageSource;
  }

  /**
   * 初期表示
   *
   * @param inDto 査定支払情報取得パラメータ
   * @return 査定支払情報取得結果
   */
  public ApiResult<J0020ReturnData> getKeihiShiharai(J0020S01Dto inDto) {
    try {
      // 査定支払情報を取得
      List<KeihiShiharaiInfoDto> ankenList =
          SHORIKUBUN_SINNKI.equals(inDto.getShorikubun())
              ? j0020Repository.getKeihiShiharai(
                  inDto.getConstrSiteCd(),
                  inDto.getConstrSiteNm(),
                  inDto.getAssessPaymentDStart(),
                  inDto.getAssessPaymentDEnd(),
                  inDto.getPaymentDataCreateFlg(),
                  DateUtils.formatNow(DateUtils.DATE_FORMAT))
              : j0020Repository.getKeihiShiharai(
                  null, null, null, null, "0", DateUtils.formatNow(DateUtils.DATE_FORMAT));

      // 戻り値
      J0020ReturnData returnData = new J0020ReturnData();
      // 連携用支払データリスト
      returnData.setListKeihiShiharaiInfoDto(ankenList);

      return ApiResult.success(returnData);

    } catch (Exception ex) {
      LOG.error(ex.toString(), ex);

      throw ex;
    }
  }

  /**
   * CSV出力
   *
   * @param inDto 経費情報取得パラメータ
   * @param encodedFileName Stream
   * @param response response
   */
  public ApiResult<?> downLoadCsv(
      J0020S02Dto inDto, String encodedFileName, HttpServletResponse response) {
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
      List<KeihiShiharaiInfoDto> ankenList = inDto.getListKeihiShiharaiInfoDto();

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
        List<Long> idList = new ArrayList<>();
            inDto.getListKeihiShiharaiInfoDto().forEach(entity ->
                idList.add(entity.getId()));
        List<TConstrSiteExpDtl> constrSiteExpDtlList=  j0020Repository.findAllById(idList);
        constrSiteExpDtlList.forEach(entity -> entity.setPaymentDataCreateFlg(CommonConstants.HAS_CREATE_FLG));
        j0020Repository.saveAll(constrSiteExpDtlList);

      response.setHeader(
          HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + encodedFileName);
      response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);
      Charset charset = Charset.forName(CommonConstants.CSV_CHARSET_SHIFT_JIS);
      try (CSVPrinter printer =
          new CSVPrinter(new OutputStreamWriter(response.getOutputStream(), charset), csvFormat)) {
        // データがある場合
        if (!CollectionUtils.isEmpty(ankenList)) {
          for (KeihiShiharaiInfoDto ankenInfo : ankenList) {
            // 明細行設定
            printer.printRecord(
                ankenInfo.getPartnerVendorCd(),
                ankenInfo.getCompNm(),
                ankenInfo.getConstrSiteCd(),
                ankenInfo.getConstrSiteNm(),
                ankenInfo.getMajorWorkCd(),
                ankenInfo.getMajorWorkNm(),
                ankenInfo.getMinorWorkCd(),
                ankenInfo.getMinorWorkNm());
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
