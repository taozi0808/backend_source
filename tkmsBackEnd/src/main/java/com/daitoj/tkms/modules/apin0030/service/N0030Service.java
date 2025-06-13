package com.daitoj.tkms.modules.apin0030.service;

import com.daitoj.tkms.domain.MEmp;
import com.daitoj.tkms.domain.TConstrWbsDtl;
import com.daitoj.tkms.domain.TConstrWbsHdr;
import com.daitoj.tkms.modules.apin0030.repository.N0030S01Repository;
import com.daitoj.tkms.modules.apin0030.repository.N0030S02Repository;
import com.daitoj.tkms.modules.apin0030.service.dto.N0030S01ReturnData;
import com.daitoj.tkms.modules.apin0030.service.dto.N0030S02ReturnData;
import com.daitoj.tkms.modules.apin0030.service.dto.N0030S03Dto;
import com.daitoj.tkms.modules.apin0030.service.dto.N0030S04Dto;
import com.daitoj.tkms.modules.apin0030.service.dto.N0030S04ReturnData;
import com.daitoj.tkms.modules.common.constants.Message;
import com.daitoj.tkms.modules.common.repository.MEmpRepository;
import com.daitoj.tkms.modules.common.repository.TConstrWbsDltRepository;
import com.daitoj.tkms.modules.common.repository.TConstrWbsHdrRepository;
import com.daitoj.tkms.modules.common.repository.mapper.MEmpMapper;
import com.daitoj.tkms.modules.common.service.ConflictException;
import com.daitoj.tkms.modules.common.service.ReportService;
import com.daitoj.tkms.modules.common.service.SystemException;
import com.daitoj.tkms.modules.common.service.dto.ApiResult;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

/** 工事予実入力ビジネスロジック. */
@Service
@Transactional(rollbackFor = Throwable.class)
public class N0030Service {
  private static final Logger LOG = LoggerFactory.getLogger(N0030Service.class);

  /** 帳票日月ヘッダフォーマット. */
  private static final String PDF_MONTH_HEADER_FORMAT = "yyyy年M月";

  /** 文字日付フォーマット. */
  private static final String PDF_STRING_DATE_FORMAT = "yyyyMMdd";

  /** レポートファイル名. */
  public static final String REPORT_FILE_NAME = "WebN0030.jasper";

  /** 工事予実入力リポジトリ. */
  private final N0030S01Repository n0030S01Repository;

  /** 工事予実入力リポジトリ. */
  private final N0030S02Repository n0030S02Repository;

  /** 従業員情報のリポジトリ. */
  private final MEmpRepository mempRepository;

  /** 従業員マッパー. */
  private final MEmpMapper mempMapper;

  /** 工事予実情報リポジトリ. */
  private final TConstrWbsHdrRepository tConstrWbsHdrRepository;

  /** 工事予実リポジトリ. */
  private final TConstrWbsDltRepository tConstrWbsDltRepository;

  /** レポートサービス. */
  private final ReportService reportService;

  /** fasterxml.jacksonのObjectMapper. */
  private final ObjectMapper objectMapper;

  /** メッセージ . */
  private final MessageSource messageSource;

  /** コンストラクタ. */
  public N0030Service(
      N0030S01Repository n0030S01Repository,
      N0030S02Repository n0030S02Repository,
      MEmpRepository mempRepository,
      MEmpMapper mempMapper,
      TConstrWbsHdrRepository tConstrWbsHdrRepository,
      TConstrWbsDltRepository tConstrWbsDltRepository,
      ReportService reportService,
      ObjectMapper objectMapper,
      MessageSource messageSource) {
    this.n0030S01Repository = n0030S01Repository;
    this.n0030S02Repository = n0030S02Repository;
    this.mempRepository = mempRepository;
    this.mempMapper = mempMapper;
    this.tConstrWbsHdrRepository = tConstrWbsHdrRepository;
    this.tConstrWbsDltRepository = tConstrWbsDltRepository;
    this.reportService = reportService;
    this.objectMapper = objectMapper;
    this.messageSource = messageSource;
  }

  /**
   * 選択項目取得.
   *
   * @return 選択項目
   */
  public ApiResult<N0030S01ReturnData> getSentakuKoumoku() {
    try {
      // 結果情報
      N0030S01ReturnData ret = new N0030S01ReturnData();
      // 入力担当社リストを取得
      List<MEmp> empList = mempRepository.findAll(Sort.by(Sort.Order.asc("empCd")));

      // 入力担当社リストを設定
      ret.setPicList(mempMapper.toEmpSearchDtoList(empList));

      return ApiResult.success(ret);

    } catch (Exception ex) {
      LOG.error(ex.toString(), ex);
      throw new SystemException(
          messageSource.getMessage(Message.MSGID_S0000, null, LocaleContextHolder.getLocale()));
    }
  }

  /**
   * 工事予実情報取得.
   *
   * @param constrSiteCd 現場コード
   * @return 工事予実情報
   */
  public ApiResult<N0030S02ReturnData> getKoujiYojitsuInfo(String constrSiteCd) {
    try {

      // 戻り値
      N0030S02ReturnData returnData = n0030S01Repository.findKoujiYojitsuInfo(constrSiteCd);

      // TODO return 201
      if (null == returnData) {
        return ApiResult.success(returnData);
      }

      // 工事予実明細情報取得する
      List<TConstrWbsDtl> constrWbsDtlList =
          tConstrWbsDltRepository.findAllByConstrWbsHidIdOrderBySeqNo(returnData.getId());

      // TODO return 201
      if (CollectionUtils.isEmpty(constrWbsDtlList)) {
        return ApiResult.success(returnData);
      }
      returnData.setConstrWbsDtls(constrWbsDtlList);

      return ApiResult.success(returnData);
    } catch (ConflictException ex) {
      throw ex;
    } catch (Exception ex) {
      LOG.error(ex.toString(), ex);

      throw new SystemException(
          messageSource.getMessage(Message.MSGID_S0000, null, LocaleContextHolder.getLocale()));
    }
  }

  /**
   * 印刷処理.
   *
   * @param inDto 工事予実印刷パラメータ
   * @return pdf
   */
  public ApiResult<?> exportReportToPdf(N0030S03Dto inDto) {
    try {
      // 明細テーブルデータヘッダを作成する
      SimpleDateFormat dateFormatFrom = new SimpleDateFormat(PDF_STRING_DATE_FORMAT);
      List<Date> allDates = new ArrayList<>();
      for (TConstrWbsDtl tarConstrWbsDtl : inDto.getConstrWbsDtls()) {
        allDates.add(dateFormatFrom.parse(tarConstrWbsDtl.getPlanStartDt()));
        allDates.add(dateFormatFrom.parse(tarConstrWbsDtl.getPlanEndDt()));
        allDates.add(dateFormatFrom.parse(tarConstrWbsDtl.getActStartDt()));
        allDates.add(dateFormatFrom.parse(tarConstrWbsDtl.getActEndDt()));
      }
      allDates.sort(Date::compareTo);
      SimpleDateFormat dateFormatTo = new SimpleDateFormat(PDF_MONTH_HEADER_FORMAT);
      Calendar calendar = Calendar.getInstance();
      List<String> dateColumns = new ArrayList<>();
      dateColumns.add(dateFormatTo.format(allDates.getFirst()));
      while (dateColumns.getLast().compareTo(dateFormatTo.format(allDates.getLast())) < 0
          && dateColumns.size() % 12 != 0) {
        calendar.setTime(dateFormatTo.parse(dateColumns.getLast()));
        calendar.add(Calendar.MONTH, 1);
        dateColumns.add(dateFormatTo.format(calendar.getTime()));
      }

      // レポートに渡すパラメータを作成する
      Map<String, Object> paramsMap = objectMapper.convertValue(inDto, new TypeReference<>() {});
      paramsMap.put("dateColumns", dateColumns);

      // データソースを生成する
      JRDataSource dataSource = new JRBeanCollectionDataSource(inDto.getConstrWbsDtls());

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
   * 保存処理.
   *
   * @param inDto　工事予実保存パラメータ.
   * @return 現場コード
   */
  public ApiResult<?> saveKoujiYojitsuInfo(N0030S04Dto inDto) {
    N0030S04ReturnData n0030S04ReturnData = new N0030S04ReturnData();

    TConstrWbsHdr dbConstWbsHdrEntity =
        tConstrWbsHdrRepository.findById(inDto.getId()).orElseThrow(ConflictException::new);

    // 排他チェック
    if ((inDto.getUpdTs() == null && dbConstWbsHdrEntity.getUpdTs() != null)
        || (inDto.getUpdTs() != null && !inDto.getUpdTs().equals(dbConstWbsHdrEntity.getUpdTs()))) {
      throw new ConflictException();
    }

    // 工事予実入力画面の情報を工事予実情報ヘッダTBLに保存する
    dbConstWbsHdrEntity.setConstrSiteCd(inDto.getConstrSiteCd());
    dbConstWbsHdrEntity.setCreatePicCd(inDto.getCreatePicCd());
    dbConstWbsHdrEntity.setWbsCreateDt(inDto.getWbsCreateDt());
    dbConstWbsHdrEntity.setHisNo(inDto.getHisNo());
    tConstrWbsHdrRepository.saveAndFlush(dbConstWbsHdrEntity);

    // 工事予実入力画面の情報を工事予実情報明細TBLに保存する
    tConstrWbsDltRepository.saveAllAndFlush(inDto.getConstrWbsDtls());

    // 保存が正常に完了しました。
    String msg =
        messageSource.getMessage(Message.MSGID_K00003, null, LocaleContextHolder.getLocale());

    // 保存処理が成功した場合は、戻り値を返して処理を終了する。
    return ApiResult.success(msg);
  }
}
