package com.daitoj.tkms.modules.apig0030.service;

import com.daitoj.tkms.domain.TExecBgtDtl;
import com.daitoj.tkms.domain.TExecBgtHdr;
import com.daitoj.tkms.domain.TExecBgtPubDtl;
import com.daitoj.tkms.modules.apig0030.repository.G0030S01Repository;
import com.daitoj.tkms.modules.apig0030.repository.G0030S02Repository;
import com.daitoj.tkms.modules.apig0030.repository.G0030S03Repository;
import com.daitoj.tkms.modules.apig0030.repository.mapper.G0030Mapper;
import com.daitoj.tkms.modules.apig0030.service.dto.ExecBgtDtlDto;
import com.daitoj.tkms.modules.apig0030.service.dto.ExecBgtDtlPoDto;
import com.daitoj.tkms.modules.apig0030.service.dto.ExecBgtPubDtlDto;
import com.daitoj.tkms.modules.apig0030.service.dto.ExecBgtPubDtlPoDto;
import com.daitoj.tkms.modules.apig0030.service.dto.G0030S02ReturnData;
import com.daitoj.tkms.modules.apin0030.service.dto.N0030S03Dto;
import com.daitoj.tkms.modules.apin0030.service.dto.N0030S04Dto;
import com.daitoj.tkms.modules.apiq0037.service.dto.Q0037S01ReturnData;
import com.daitoj.tkms.modules.common.constants.Message;
import com.daitoj.tkms.modules.common.repository.MEmpRepository;
import com.daitoj.tkms.modules.common.repository.TExecBgtDtlRepository;
import com.daitoj.tkms.modules.common.repository.TExecBgtPubDtlRepository;
import com.daitoj.tkms.modules.common.repository.mapper.MEmpMapper;
import com.daitoj.tkms.modules.common.service.ConflictException;
import com.daitoj.tkms.modules.common.service.ReportService;
import com.daitoj.tkms.modules.common.service.SystemException;
import com.daitoj.tkms.modules.common.service.dto.ApiResult;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Objects;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/** 実行予算作成ビジネスロジック. */
@Service
@Transactional(rollbackFor = Throwable.class)
public class G0030Service {
  private static final Logger LOG = LoggerFactory.getLogger(G0030Service.class);

  /** レポートファイル名. */
  public static final String REPORT_FILE_NAME = "WebN0030.jasper";

  /** 文字日付フォーマット. */
  private static final String PDF_STRING_DATE_FORMAT = "yyyyMMdd";

  /** 帳票日月ヘッダフォーマット. */
  private static final String PDF_MONTH_HEADER_FORMAT = "yyyy年M月";

  /** 帳票日月ヘッダ比較用フォーマット. */
  private static final String PDF_MONTH_HEADER_COMPARE = "yyyyMM";

  /** 実行予算作成マッパー. */
  private final G0030Mapper g0030Mapper;

  /** 実行予算ヘッダリポジトリ. */
  private final G0030S01Repository g0030S01Repository;

  /** 実行予算明細発注情報のリポジトリ. */
  private final G0030S02Repository g0030S02Repository;

  /** 実行予算公共明細発注情報のリポジトリ. */
  private final G0030S03Repository g0030S03Repository;

  /** 実行予算明細リポジトリ. */
  private final TExecBgtDtlRepository texecBgtDtlRepository;

  /** 実行予算公共明細リポジトリ. */
  private final TExecBgtPubDtlRepository texecBgtPubDtlRepository;

  /** 従業員情報のリポジトリ. */
  private final MEmpRepository mempRepository;

  /** 従業員マッパー. */
  private final MEmpMapper mempMapper;

  /** レポートサービス. */
  private final ReportService reportService;

  /** fasterxml.jacksonのObjectMapper. */
  private final ObjectMapper objectMapper;

  /** メッセージ . */
  private final MessageSource messageSource;

  /** コンストラクタ. */
  public G0030Service(
      G0030Mapper g0030Mapper,
      G0030S01Repository g0030S01Repository,
      G0030S02Repository g0030S02Repository,
      G0030S03Repository g0030S03Repository,
      TExecBgtDtlRepository texecBgtDtlRepository,
      TExecBgtPubDtlRepository texecBgtPubDtlRepository,
      MEmpRepository mempRepository,
      MEmpMapper mempMapper,
      ReportService reportService,
      ObjectMapper objectMapper,
      MessageSource messageSource) {
    this.g0030Mapper = g0030Mapper;
    this.g0030S01Repository = g0030S01Repository;
    this.g0030S02Repository = g0030S02Repository;
    this.g0030S03Repository = g0030S03Repository;
    this.texecBgtDtlRepository = texecBgtDtlRepository;
    this.texecBgtPubDtlRepository = texecBgtPubDtlRepository;
    this.mempRepository = mempRepository;
    this.mempMapper = mempMapper;
    this.reportService = reportService;
    this.objectMapper = objectMapper;
    this.messageSource = messageSource;
  }

  /**
   * 業者選択項目取得.
   *
   * @param parentPartnerVendorCd 上位業者コード
   * @return 業者選択項目
   */
  public ApiResult<Q0037S01ReturnData> getSagyouinMeiboSK(String parentPartnerVendorCd) {
    try {
      //      // 結果情報
      //      Q0037S01ReturnData ret = new Q0037S01ReturnData();
      //
      //      // 入力担当社リストを取得
      //      List<VSubbieVendorRel> subbieVendorList =
      //
      // vsubbieVendorRelRepository.findAllByParentPartnerVendorCdOrderByPartnerVendorCd(parentPartnerVendorCd);
      //
      //      // 入力担当社リストを設定
      //      ret.setSubbieVendorList(subbieVendorList);

      return ApiResult.success(null);

    } catch (Exception ex) {
      LOG.error(ex.toString(), ex);
      throw new SystemException(
          messageSource.getMessage(Message.MSGID_S0000, null, LocaleContextHolder.getLocale()));
    }
  }

  /**
   * 実行予算作成情報取得.
   *
   * @param execBgtCd 実行予算コード
   * @return 工事予実情報
   */
  public ApiResult<G0030S02ReturnData> getJikkouYosanInfo(String execBgtCd) {
    try {

      // 実行予算作成情報(戻り値)取得する
      G0030S02ReturnData returnData = g0030S01Repository.findJikkouYosanHdrInfo(execBgtCd);
      if (null == returnData) {
        return null;
      }
      TExecBgtHdr texecBgtHdr = new TExecBgtHdr();
      texecBgtHdr.setId(returnData.getId());

      // 実行予算明細リストを取得する
      List<TExecBgtDtl> execBgtDtlList = texecBgtDtlRepository.findAllByExecBgtHid(texecBgtHdr);
      if (execBgtDtlList.isEmpty()) {
        return ApiResult.success(returnData);
      }
      List<ExecBgtDtlDto> execBgtDtlDtoList = g0030Mapper.toExecBgtDtlDtoList(execBgtDtlList);

      // 実行予算明細発注リストを取得する
      List<ExecBgtDtlPoDto> texecBgtDtlPoDtoList =
          g0030S02Repository.findAllByExecBgtHid(returnData.getId());

      // 実行予算明細発注情報リストを実行予算明細にマッピングする
      execBgtDtlDtoList.forEach(
          item -> {
            item.getExecBgtDtlPoList()
                .addAll(
                    texecBgtDtlPoDtoList.stream()
                        .filter(itemPo -> Objects.equals(itemPo.getExecBgtDid(), item.getId()))
                        .toList());
          });
      returnData.setExecBgtDtlList(execBgtDtlDtoList);

      // 実行予算公共明細情報リストを取得する
      List<TExecBgtPubDtl> execBgtPubDtlList =
          texecBgtPubDtlRepository.findAllByExecBgtHid(texecBgtHdr);
      if (execBgtPubDtlList.isEmpty()) {
        return ApiResult.success(returnData);
      }
      List<ExecBgtPubDtlDto> execBgtPubDtlDtoList =
          g0030Mapper.toExecBgtPubDtlDtoList(execBgtPubDtlList);

      // 実行予算公共明細発注リストを取得する
      List<ExecBgtPubDtlPoDto> texecBgtPubDtlPoDtoList =
          g0030S03Repository.findAllByExecBgtHid(returnData.getId());

      // 実行予算公共明細発注情報リストを実行予算公共明細にマッピングする
      execBgtPubDtlDtoList.forEach(
          item -> {
            item.getExecBgtPubDtlPoList()
                .addAll(
                    texecBgtPubDtlPoDtoList.stream()
                        .filter(itemPo -> Objects.equals(itemPo.getExecBgtDid(), item.getId()))
                        .toList());
          });
      returnData.setExecBgtPubDtlList(execBgtPubDtlDtoList);

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
      SimpleDateFormat dateFormatFrom = new SimpleDateFormat(PDF_STRING_DATE_FORMAT);
      SimpleDateFormat dateFormatTo = new SimpleDateFormat(PDF_MONTH_HEADER_FORMAT);
      SimpleDateFormat dateFormatCompare = new SimpleDateFormat(PDF_MONTH_HEADER_COMPARE);

      //      N0030S02ReturnData printData =
      //          n0030S01Repository.findKoujiYojitsuInfo(inDto.getConstrSiteCd());
      //      printData.setConstrWbsDtls(
      //          tConstrWbsDltRepository.findAllByConstrWbsHidIdOrderBySeqNo(printData.getId()));
      //
      //      int reportMaxRowCount = (Math.min(printData.getConstrWbsDtls().size(),
      // REPORT_ROW_COUNT));
      //
      //      // 明細テーブルデータヘッダを作成する
      //      List<TConstrWbsDtl> newConstrWbsDtls = new ArrayList<>();
      //      List<List<String>> dateColumns = new ArrayList<>();
      //      List<List<String>> dateColumnsCompare = new ArrayList<>();
      //      for (int i = 0; i < printData.getConstrWbsDtls().size(); i++) {
      //        if (i % reportMaxRowCount == 0) {
      //          List<TConstrWbsDtl> onePageRows =
      //              printData
      //                  .getConstrWbsDtls()
      //                  .subList(i, Math.min(i + reportMaxRowCount,
      // printData.getConstrWbsDtls().size()));
      //          newConstrWbsDtls.addAll(
      //              this.createReportRow(
      //                  onePageRows,
      //                  dateFormatFrom,
      //                  dateColumns,
      //                  dateFormatTo,
      //                  dateColumnsCompare,
      //                  dateFormatCompare));
      //        }
      //      }
      //
      //      // レポートに渡すパラメータを作成する
      //      Map<String, Object> paramsMap =
      //          objectMapper.convertValue(printData, new TypeReference<>() {});
      //      paramsMap.put("wbsCreateTs", printData.getWbsCreateTs());
      //      paramsMap.put("dateColumns", dateColumns);
      //      paramsMap.put("dateColumnsCompare", dateColumnsCompare);
      //      paramsMap.put("reportMaxRowCount", reportMaxRowCount);
      //
      //      // データソースを生成する
      //      JRDataSource dataSource = new JRBeanCollectionDataSource(newConstrWbsDtls);
      //
      //      // レポートを生成する
      //      byte[] datas = reportService.exportReportToPdf(REPORT_FILE_NAME, paramsMap,
      // dataSource);
      //
      //      return ApiResult.success(datas);
      return null;
    } catch (Exception ex) {
      LOG.error(ex.toString(), ex);

      throw new SystemException(
          messageSource.getMessage(Message.MSGID_S0000, null, LocaleContextHolder.getLocale()));
    }
  }

  /**
   * 保存処理.
   *
   * @param inDto 工事予実保存パラメータ
   * @return メッセージ
   */
  public ApiResult<?> saveKoujiYojitsuInfo(N0030S04Dto inDto) {
    //    TConstrWbsHdr dbConstWbsHdrEntity =
    //        tConstrWbsHdrRepository.findById(inDto.getId()).orElseThrow(ConflictException::new);
    //
    //    // 排他チェック
    //    if ((inDto.getUpdTs() == null && dbConstWbsHdrEntity.getUpdTs() != null)
    //        || (inDto.getUpdTs() != null &&
    // !inDto.getUpdTs().equals(dbConstWbsHdrEntity.getUpdTs()))) {
    //      throw new ConflictException();
    //    }
    //
    //    // 工事予実入力画面の情報を工事予実情報ヘッダTBLに保存する
    //    dbConstWbsHdrEntity.setConstrSiteCd(inDto.getConstrSiteCd());
    //    dbConstWbsHdrEntity.setCreatePicCd(inDto.getCreatePicCd());
    //    dbConstWbsHdrEntity.setWbsCreateTs(inDto.getWbsCreateTs());
    //    dbConstWbsHdrEntity.setHisNo(inDto.getHisNo());
    //    tConstrWbsHdrRepository.saveAndFlush(dbConstWbsHdrEntity);
    //
    //    // 工事予実入力画面の情報を工事予実情報明細TBLに保存する
    //    tConstrWbsDltRepository.saveAllAndFlush(inDto.getConstrWbsDtls());

    // 保存が正常に完了しました。
    String msg =
        messageSource.getMessage(Message.MSGID_K00003, null, LocaleContextHolder.getLocale());

    // 保存処理が成功した場合は、戻り値を返して処理を終了する。
    return ApiResult.success(msg);
  }
}
