package com.daitoj.tkms.modules.apio0010.service;

import com.daitoj.tkms.domain.MItemListSetting;
import com.daitoj.tkms.modules.apio0010.repository.O0010Repository;
import com.daitoj.tkms.modules.apio0010.repository.mapper.O0010Mapper;
import com.daitoj.tkms.modules.apio0010.service.dto.O0010ReturnData;
import com.daitoj.tkms.modules.apio0010.service.dto.O0010S01Dto;
import com.daitoj.tkms.modules.apio0010.service.dto.O0010S02Dto;
import com.daitoj.tkms.modules.apio0010.service.dto.O0010S03Dto;
import com.daitoj.tkms.modules.apio0010.service.dto.VendorInfoDto;
import com.daitoj.tkms.modules.apio0010.service.dto.VendorResultDto;
import com.daitoj.tkms.modules.common.constants.CommonConstants;
import com.daitoj.tkms.modules.common.constants.MasterData;
import com.daitoj.tkms.modules.common.constants.Message;
import com.daitoj.tkms.modules.common.repository.MItemListSettingRepository;
import com.daitoj.tkms.modules.common.service.ReportService;
import com.daitoj.tkms.modules.common.service.SystemException;
import com.daitoj.tkms.modules.common.service.dto.ApiResult;
import com.daitoj.tkms.modules.common.utils.DateUtils;
import com.daitoj.tkms.modules.common.utils.EnhancedFullWidthConverterUtils;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletResponse;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.apache.commons.collections.CollectionUtils;
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

/** 業者一覧ビジネスロジック. */
@Service
public class O0010Service {
  private static final Logger LOG = LoggerFactory.getLogger(O0010Service.class);

  /* CSVヘッダ */
  private static final String[] CSV_HEADER = {
    "業者コード", "業者名", "業者支店コード", "業者支店名", "住所", "代表者名", "電話番号", "業種", "解体登録", "警備認定", "産廃許可", "許可制限"
  };

  /* CSVファイル名 */
  public static final String APP_NAME = "業者一覧";

  /* 帳票日付フォーマット */
  private static final String PDF_DATE_FORMAT = "yyyy年MM月dd日HH:mm:ss";

  /* フォーマット: yyyy年MM月dd日 */
  private static final String DATE_FORAMT = "yyyy年MM月dd日";

  /* フォーマット: yyyyMMdd */
  private static final String DATE_FORMAT_YYYYMMDD = "yyyyMMdd";

  /* メッセージ. */
  private final MessageSource messageSource;

  /* 業者一覧のリポジトリ */
  private final O0010Repository o0010Repository;

  /* 項目リストのリポジトリ */
  private final MItemListSettingRepository mItemListSettingRepository;

  /* fasterxml.jackson@ObjectMapper */
  private final ObjectMapper objectMapper;

  /* 業者マッピング */
  private final O0010Mapper o0010Mapper;

  /** レポートサービス. */
  private final ReportService reportService;

  /** レポートファイル名. */
  public static final String REPORT_FILE_NAME = "WebO0020.jasper";

  /** コンストラクタ. */
  public O0010Service(
      MessageSource messageSource,
      O0010Repository o0010Repository,
      MItemListSettingRepository mItemListSettingRepository,
      ObjectMapper objectMapper,
      O0010Mapper o0010Mapper,
      ReportService reportService) {
    this.messageSource = messageSource;
    this.o0010Repository = o0010Repository;
    this.mItemListSettingRepository = mItemListSettingRepository;
    this.objectMapper = objectMapper;
    this.o0010Mapper = o0010Mapper;
    this.reportService = reportService;
  }

  /**
   * 初期表示.
   *
   * @return 業者一覧
   */
  public ApiResult<O0010ReturnData> getInitInfo() {
    try {
      // 業者情報を取得
      List<VendorInfoDto> vendorList = o0010Repository.findInitInfo();

      // 業者結果情報変換
      List<VendorResultDto> vendorResultList = o0010Mapper.toVendorResultDto(vendorList);

      // 業種リストを取得
      List<MItemListSetting> listSettings =
          mItemListSettingRepository
              .findById_ItemClassCdAndId_EffectiveStartDtLessThanEqualOrderByDisplayOrder(
                  MasterData.ITEM_CLASS_CD_D0019, DateUtils.formatNow(DateUtils.DATE_FORMAT));

      // 戻り値
      O0010ReturnData returnData = new O0010ReturnData();
      // 業者情報リスト
      returnData.setListVendorInfo(vendorResultList);
      // 業種リスト
      returnData.setListJobTypeInfo(listSettings);
      // 成功の場合
      return ApiResult.success(returnData);

    } catch (Exception ex) {
      LOG.error(ex.toString(), ex);
      throw new SystemException(
          messageSource.getMessage(Message.MSGID_S0000, null, LocaleContextHolder.getLocale()));
    }
  }

  /**
   * 業者検索処理.
   *
   * @param inDto 検索パラメータ
   * @return 業者情報
   */
  public ApiResult<O0010ReturnData> getVendorInfo(O0010S01Dto inDto) {
    try {

      // 業者情報を取得
      List<VendorInfoDto> vendorList =
          o0010Repository.findVendorInfo(
              inDto.getVendorCd(),
              inDto.getBranchCd(),
              inDto.getJobType(),
              inDto.getDemolPerm(),
              inDto.getSecurityCert(),
              inDto.getIwPerm());
      // 業者結果情報に変換する
      List<VendorResultDto> vendorResult = filterByCondition(inDto, vendorList);

      // 0件を取得する場合
      if (CollectionUtils.isEmpty(vendorResult)) {
        // メッセージ
        String msg =
            messageSource.getMessage(Message.MSGID_K00001, null, LocaleContextHolder.getLocale());
        LOG.info(msg);
        // 結果情報
        return ApiResult.error(Message.MSGID_K00001, msg);
      }

      // 戻り値
      O0010ReturnData returnData = new O0010ReturnData();
      // 業者情報リスト
      returnData.setListVendorInfo(vendorResult);

      return ApiResult.success(returnData);
    } catch (Exception ex) {
      LOG.error(ex.toString(), ex);
      throw new SystemException(
          messageSource.getMessage(Message.MSGID_S0000, null, LocaleContextHolder.getLocale()));
    }
  }

  /**
   * CSV出力.
   *
   * @param inDto 業者情報取得パラメータ
   * @param encodedFileName ファイル名
   * @param response response
   */
  public ApiResult<?> downloadCsv(
      O0010S02Dto inDto, String encodedFileName, HttpServletResponse response) {

    try {

      // ヘッダ設定（カンマ区切り）
      CSVFormat csvFormat =
          CSVFormat.DEFAULT
              .builder()
              .setDelimiter(',')
              .setQuoteMode(QuoteMode.ALL)
              .setHeader(CSV_HEADER)
              .get();

      // 業者情報を取得
      List<VendorInfoDto> vendorList =
          o0010Repository.findVendorInfo(
              inDto.getVendorCd(),
              inDto.getBranchCd(),
              inDto.getJobType(),
              inDto.getDemolPerm(),
              inDto.getSecurityCert(),
              inDto.getIwPerm());
      List<VendorResultDto> vendorResultList = filterByCondition(inDto, vendorList);

      // 最大件数を超える場合
      if (!CollectionUtils.isEmpty(vendorList)
          && vendorList.size() > CommonConstants.SEARCH_MAX_COUNT) {
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
        if (!CollectionUtils.isEmpty(vendorList)) {
          for (VendorResultDto vendorInfo : vendorResultList) {
            // 明細行設定
            printer.printRecord(
                vendorInfo.getVendorCd(),
                vendorInfo.getCompNm(),
                vendorInfo.getBranchCd(),
                vendorInfo.getBranchNm(),
                vendorInfo.getVendorAddr(),
                vendorInfo.getCeoNm(),
                vendorInfo.getVendorTelNoFormat(),
                vendorInfo.getJobTypeList(),
                vendorInfo.getDemolPermStr(),
                vendorInfo.getSecurityCertStr(),
                vendorInfo.getIwPermStr(),
                vendorInfo.getIwPermDt() != null
                    ? DateUtils.formatDateFromYYYYMMDD(vendorInfo.getIwPermDt(), DATE_FORAMT)
                    : "");
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
   * 印刷処理.
   *
   * @param inDto 業者情報印刷パラメータ
   * @return pdf
   */
  public ApiResult<?> exportReportToPdf(O0010S02Dto inDto) {

    try {

      // 業者情報を取得
      List<VendorInfoDto> vendorList =
          o0010Repository.findVendorInfo(
              inDto.getVendorCd(),
              inDto.getBranchCd(),
              inDto.getJobType(),
              inDto.getDemolPerm(),
              inDto.getSecurityCert(),
              inDto.getIwPerm());

      // 最大件数を超えた場合
      if (!CollectionUtils.isEmpty(vendorList)
          && vendorList.size() > CommonConstants.SEARCH_MAX_COUNT) {
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

      List<VendorResultDto> vendorResultList = filterByCondition(inDto, vendorList);

      // 印刷パラメータ
      O0010S03Dto printDto = new O0010S03Dto();
      // 利用PCのシステム日付
      printDto.setSysDate(inDto.getSysDate().format(DateTimeFormatter.ofPattern(PDF_DATE_FORMAT)));
      // 業者コード
      printDto.setVendorCd(inDto.getVendorCd());
      // 業者名
      printDto.setCompNm(inDto.getCompNm());
      // 産廃許可期限
      printDto.setIwPermDt(inDto.getIwPermDt());
      // 業種
      printDto.setJobType(inDto.getJobTypeNm());
      // 解体登録
      printDto.setDemolPerm(inDto.getDemolPerm());
      // 警備認定
      printDto.setSecurityCert(inDto.getSecurityCert());
      // 産廃許可
      printDto.setIwPerm(inDto.getIwPerm());
      // レポートの渡すパラメータ
      Map<String, Object> paramsMap =
          objectMapper.convertValue(printDto, new TypeReference<Map<String, Object>>() {});

      // データソースを生成する
      JRDataSource dataSource = new JRBeanCollectionDataSource(vendorResultList);

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
   * 業者検索処理.
   *
   * @param inDto パラメータ
   * @param dtoList 業者情報リスト
   * @return 業者情報検索結果
   */
  private List<VendorResultDto> filterByCondition(O0010S01Dto inDto, List<VendorInfoDto> dtoList) {

    // 業者結果情報に転換する
    List<VendorResultDto> resultDtoList = o0010Mapper.toVendorResultDto(dtoList);

    // 業者名
    if (StringUtils.isNotEmpty(inDto.getCompNm())) {
      resultDtoList =
          resultDtoList.stream()
              .filter(
                  vendor ->
                      vendor
                              .getFullWidthCompNm()
                              .contains(EnhancedFullWidthConverterUtils.convert(inDto.getCompNm()))
                          || vendor
                              .getFullWidthCompKnNm()
                              .contains(EnhancedFullWidthConverterUtils.convert(inDto.getCompNm())))
              .collect(Collectors.toList());
    }

    // 産廃許可期限2ヶ月以内
    if ("1".equals(inDto.getIwPermDt())) {
      resultDtoList =
          resultDtoList.stream()
              .filter(
                  vendor -> {
                    if (StringUtils.isEmpty(vendor.getIwPermDt())) {
                      return false;
                    }

                    LocalDate iwPermDt =
                        LocalDate.parse(
                            vendor.getIwPermDt(),
                            DateTimeFormatter.ofPattern(DATE_FORMAT_YYYYMMDD));
                    LocalDate currentDate = LocalDate.now();
                    LocalDate twoMonthsAfter = currentDate.plusMonths(2);
                    return iwPermDt.isBefore(twoMonthsAfter) && iwPermDt.isAfter(currentDate);
                  })
              .collect(Collectors.toList());
    }

    return resultDtoList;
  }

}
