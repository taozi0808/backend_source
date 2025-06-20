package com.daitoj.tkms.modules.apic0030.service;

import com.daitoj.tkms.domain.*;
import com.daitoj.tkms.modules.apic0030.repository.C0030Repository;
import com.daitoj.tkms.modules.apic0030.service.dto.*;
import com.daitoj.tkms.modules.apic0030.repository.mapper.C0030Mapper;
import com.daitoj.tkms.modules.common.constants.CommonConstants;
import com.daitoj.tkms.modules.common.constants.Message;
import com.daitoj.tkms.modules.common.repository.*;
import com.daitoj.tkms.modules.common.repository.mapper.MMinorWorkMapper;
import com.daitoj.tkms.modules.common.repository.mapper.MajorWorkMapper;
import com.daitoj.tkms.modules.common.service.*;
import com.daitoj.tkms.modules.common.service.dto.ApiResult;
import com.daitoj.tkms.modules.common.service.dto.MajorWorkSearchDto;
import com.daitoj.tkms.modules.common.service.dto.MinorWorkSearchDto;
import com.daitoj.tkms.modules.common.utils.DateUtils;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.*;
import java.util.stream.Collectors;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

/** 概算一覧ビジネスロジック */
@Service
@Transactional(rollbackFor = Throwable.class)
public class C0030Service {

  private static final Logger LOG = LoggerFactory.getLogger(C0030Service.class);

  /** マスタデータサービス */
  private final ItemListSettingService itemListSettingService;

  /** 帳票日付フォーマット */
  private static final String PDF_DATE_FORMAT = "yyyy年MM年dd日HH:mm:ss";

  /** 概算情報リポジトリ */
  private final TRoughEstHdrRepository tRoughEstHdrRepository;

  private final TRoughEstFileDtlRepository tRoughEstFileDtlRepository;

  private final TRoughEstDtlRepository tRoughEstDtlRepository;

  private final TEstRepository tEstRepository;

  /** 出来高シュミレーション */
  private final TEvSimRepository tEvSimRepository;

  private final TEvSimBgtRepository tEvSimBgtRepository;

  /** ストレージサービス */
  private final CloudStorageService cloudStorageService;

  /** 組織改定情報のリポジトリ */
  private final MOrgRevRepository morgRevRepository;

  /** 従業員情報リポジトリ */
  private final MEmpRepository mempRepository;

  /** 概算一覧のクエリ */
  private final C0030Repository c0030Repository;

  /** 概算一覧のクエリ */
  private final C0030Mapper c0030Mapper;

  /** 大工事のクエリ */
  private final MMajorWorkRepository mMajorWorkRepository;

  /** 大工事のクエリ */
  private final MajorWorkMapper majorWorkMapper;

  /** 小工事のクエリ */
  private final MMinorWorkRepository minorWorkRepository;

  /** 小工事のクエリ */
  private final MMinorWorkMapper minorWorkMapper;

  /** メッセージ */
  private final MessageSource messageSource;

  /** 業務データ最新承認情報リポジトリ */
  private final TBusinessNewestApprRepository tbusinessNewestApprRepository;

  /** レポートサービス */
  private final ReportService reportService;

  /** fasterxml.jacksonのObjectMapper */
  private final ObjectMapper objectMapper;

  /** レポートファイル名 */
  public static final String REPORT_FILE_NAME = "WebC0040.jasper";

  /** コンストラクタ */
  public C0030Service(
      C0030Repository c0030Repository,
      ItemListSettingService itemListSettingService,
      TRoughEstHdrRepository tRoughEstHdrRepository,
      TRoughEstDtlRepository tRoughEstDtlRepository,
      TEstRepository tEstRepository,
      TRoughEstFileDtlRepository tRoughEstFileDtlRepository,
      TEvSimRepository tEvSimRepository,
      TEvSimBgtRepository tEvSimBgtRepository,
      CloudStorageService cloudStorageService,
      MOrgRevRepository morgRevRepository,
      MEmpRepository mempRepository,
      MessageSource messageSource,
      C0030Mapper c0030Mapper,
      MMajorWorkRepository mMajorWorkRepository,
      MajorWorkMapper majorWorkMapper,
      MMinorWorkRepository minorWorkRepository,
      MMinorWorkMapper minorWorkMapper,
      TBusinessNewestApprRepository tbusinessNewestApprRepository,
      ReportService reportService,
      ObjectMapper objectMapper) {
    this.c0030Repository = c0030Repository;
    this.itemListSettingService = itemListSettingService;
    this.tRoughEstHdrRepository = tRoughEstHdrRepository;
    this.tRoughEstDtlRepository = tRoughEstDtlRepository;
    this.tEstRepository = tEstRepository;
    this.tRoughEstFileDtlRepository = tRoughEstFileDtlRepository;
    this.tEvSimRepository = tEvSimRepository;
    this.tEvSimBgtRepository = tEvSimBgtRepository;
    this.cloudStorageService = cloudStorageService;
    this.morgRevRepository = morgRevRepository;
    this.mempRepository = mempRepository;
    this.messageSource = messageSource;
    this.c0030Mapper = c0030Mapper;
    this.mMajorWorkRepository = mMajorWorkRepository;
    this.majorWorkMapper = majorWorkMapper;
    this.minorWorkRepository = minorWorkRepository;
    this.minorWorkMapper = minorWorkMapper;
    this.tbusinessNewestApprRepository = tbusinessNewestApprRepository;
    this.reportService = reportService;
    this.objectMapper = objectMapper;
  }

  /**
   * 大工事/小工事情報
   *
   * @return 大工事/小工事情報取得結果
   */
  public ApiResult<C0030S03ReturnData> getMajorMinorReport() {
    try {
      C0030S03ReturnData returnData = new C0030S03ReturnData();

      List<MajorWorkSearchDto> listMajorWorkSearchDto =
          majorWorkMapper.toMajorWorkSearchList(mMajorWorkRepository.findAll());
      List<MinorWorkSearchDto> listMinorWorkSearchDto =
          minorWorkMapper.toMinorWorkSearchList(minorWorkRepository.findAll());
      returnData.setListMajorWorkSearchDto(listMajorWorkSearchDto);
      returnData.setListMinorWorkSearchDto(listMinorWorkSearchDto);
      return ApiResult.success(returnData);

    } catch (Exception ex) {
      LOG.error(ex.toString(), ex);

      throw ex;
    }
  }

  /**
   * 社員情報
   *
   * @param empNm 従業員氏名
   * @return 社員情報取得結果
   */
  public List<MEmpInfoDto> getMEmp(String empNm) {
    // 社員情報
    return c0030Repository.findByEmpNm(empNm);
  }

  /**
   * 部門
   *
   * @return 部門取得結果
   */
  public List<OrgRevSearchInfoDto> getOrgRev() {
    // 適用開始日リストを取得
    return c0030Repository.getOrgRevSearchInfoDto(DateUtils.formatNow(DateUtils.DATE_FORMAT));
  }

  /**
   * 単価地域情報取得
   *
   * @return 地域リスト
   */
  public List<RegionInfoDto> getRegionInfo() {
    // 単価地域リストを取得
    return c0030Repository.getRegionInfo("1");
  }

  /**
   * 初期表示
   *
   * @param roughEstCd 概算コード
   * @return 概算情報取得結果
   */
  public ApiResult<C0030S01ReturnData> getInitInfo(String roughEstCd) {
    try {

      // 概算情報を取得
      RoughInfoDto roughInfoDto =
          c0030Repository.getRoughInfoDto(roughEstCd).orElseThrow(ConflictException::new);
      if (roughInfoDto == null) {
        // メッセージ
        String msg =
            messageSource.getMessage(Message.MSGID_A00005, null, LocaleContextHolder.getLocale());

        LOG.info(msg);

        return ApiResult.error(Message.MSGID_A00005, msg);
      }
      // 部門リストを取得
      List<OrgRevSearchInfoDto> orgRevList = getOrgRev();

      // 社員情報
      List<MEmpInfoDto> empInfo = getMEmp(null);

      // 単価地域情報取得
      List<RegionInfoDto> regionInfoDtoList = getRegionInfo();

      // 請求条件リスト
      List<ProjectPaymentTermsInfoDto> projectPaymentTermsInfoDto =
          c0030Repository.getProjectPaymentTermsInfoDto(roughInfoDto.getProjectId());

      // 添付ファイルリスト
      List<RoughEstFileDtlInfoDto> roughEstFileDtlInfoDto =
          c0030Repository.getRoughEstFileDtlInfoDto(roughInfoDto.getRoughEstId());
      // 概算明細リスト
      List<RoughEstDtlInfoDto> roughEstDtlInfoDto =
          c0030Repository.getRoughEstDtlInfoDto(roughInfoDto.getRoughEstId());
      // 戻り値
      C0030S01ReturnData returnData = new C0030S01ReturnData();
      // 概算情報リスト
      returnData.setRoughInfoDto(roughInfoDto);
      returnData.setListProjectPaymentTermsInfo(projectPaymentTermsInfoDto);
      returnData.setListRoughEstFileDtlInfoDto(roughEstFileDtlInfoDto);
      returnData.setOrgRevSearchInfoDto(orgRevList);
      returnData.setListRoughEstDtlInfoDto(roughEstDtlInfoDto);
      returnData.setRegionInfoDto(regionInfoDtoList);
      returnData.setEmpInfo(empInfo);
      returnData.setListConstrSiteInfoDto(
          itemListSettingService.getData("D0005").getData().getItemSettingList());
      return ApiResult.success(returnData);

    } catch (Exception ex) {
      LOG.error(ex.toString(), ex);

      throw ex;
    }
  }

  /**
   * 保存処理
   *
   * @param inDto 概算情報パラメータ
   * @return
   */
  public ApiResult<C0030S01ReturnData> saveRoughInfo(C0030S01Dto inDto) {
    try {
      // 概算コード
      String roughEstCd;
      //   歴番
      Integer hisNo;
      TRoughEstHdr hisNoList = tRoughEstHdrRepository.findByRoughEstCd(inDto.getRoughEstCd());
      if (hisNoList == null) {
        String msg =
            messageSource.getMessage(Message.MSGID_K00011, null, LocaleContextHolder.getLocale());

        LOG.info(msg);

        // 結果情報
        return ApiResult.error(Message.MSGID_K00011, msg);
      }
      // todo 最新フラグ;2：概算エンティティを登録
      hisNoList.setNewestFlg(CommonConstants.NEWEST_FLAG_HIS);
      tRoughEstHdrRepository.saveAndFlush(hisNoList);

      hisNo = hisNoList.getHisNo() + 1;
      // 概算エンティティに変換
      TRoughEstHdr tRoughEstHdr = c0030Mapper.toTRoughEstHdrEntity(inDto);

      // 最新フラグ;1：最新
      tRoughEstHdr.setNewestFlg(CommonConstants.NEWEST_FLAG_NEW);
      // 棟コード
      tRoughEstHdr.setBuildingCd(hisNoList.getBuildingCd());
      // 歴番;01から連番
      tRoughEstHdr.setHisNo(hisNo);
      // 概算エンティティを登録
      TRoughEstHdr newtRoughEstHdrEntity = tRoughEstHdrRepository.saveAndFlush(tRoughEstHdr);
      if (newtRoughEstHdrEntity == null) {
        String msg =
            messageSource.getMessage(Message.MSGID_K00011, null, LocaleContextHolder.getLocale());

        LOG.info(msg);

        // 結果情報
        return ApiResult.error(Message.MSGID_K00011, msg);
      }
      // 概算エンティティに変換
      List<TRoughEstDtl> tRoughEstDtlList =
          c0030Mapper.toTRoughEstDtlEntityList(
              inDto.getListC0030S02DtoInfoDto(), newtRoughEstHdrEntity.getId());
      for (int i = 0; i < tRoughEstDtlList.size(); i++) {
        tRoughEstDtlList.get(i).setSeqNo(i);
        tRoughEstDtlList.get(i).setWorkSeqNo(i + "");
      }
      // 概算明細を登録
      tRoughEstDtlRepository.saveAllAndFlush(tRoughEstDtlList);

      if (!inDto.getListC0030S03DtoInfoDto().isEmpty()
          && inDto.getListC0030S03DtoInfoDto().size() > 0) {

        MultipartFile[] files = new MultipartFile[inDto.getListC0030S03DtoInfoDto().size()];
        for (int i = 0; i < inDto.getListC0030S03DtoInfoDto().size(); i++) {
          files[i] = inDto.getListC0030S03DtoInfoDto().get(i).getFiles();
        }
        // 添付ファイルプロード
        List<UUID> uuids = cloudStorageService.upload(files);

        List<TRoughEstFileDtl> tRoughEstFileDtlList = new ArrayList<>();

        // 従業員顔写真情報を設定
        for (int i = 0; i < inDto.getListC0030S03DtoInfoDto().size(); i++) {
          TRoughEstFileDtl dtlDto = new TRoughEstFileDtl();
          // ファイルのインデックス
          String fileIndex = String.valueOf(i);
          dtlDto.getFile().setId(uuids.get(i));
          dtlDto.setSeqNo(inDto.getListC0030S03DtoInfoDto().get(i).getSeqNo());
          dtlDto.getRoughEstHid().setId(newtRoughEstHdrEntity.getId());

          tRoughEstFileDtlList.add(dtlDto);
        }
        // 概算添付ファイル明細を登録
        tRoughEstFileDtlRepository.saveAllAndFlush(tRoughEstFileDtlList);
      }

      String msg =
          messageSource.getMessage(Message.MSGID_K00003, null, LocaleContextHolder.getLocale());

      LOG.info(msg);
      // 保存が正常に完了しました。
      return ApiResult.error(Message.MSGID_K00003, msg);

    } catch (Exception ex) {
      LOG.error(ex.toString(), ex);

      throw ex;
    }
  }

  /**
   * 概算情報を削除
   *
   * @param roughEstCd 概算コード歴番
   * @param hisNo 歴番
   */
  public ApiResult<?> deleteRoughInfo(String roughEstCd, String hisNo) {
    try {
      TRoughEstHdr tRoughEstHdrEntity = tRoughEstHdrRepository.findByRoughEstCd(roughEstCd);
      if (tRoughEstHdrEntity == null) {
        String msg =
            messageSource.getMessage(Message.MSGID_K00011, null, LocaleContextHolder.getLocale());

        LOG.info(msg);

        // 結果情報
        return ApiResult.error(Message.MSGID_K00011, msg);
      }
      List<TEst> tEstList = tEstRepository.findByroughEstHid_Id(tRoughEstHdrEntity.getId());

      if (!tEstList.isEmpty()) {
        // 見積依頼情報が存在するため、概算情報を削除できません。
        String msg =
            messageSource.getMessage(Message.MSGID_K00011, null, LocaleContextHolder.getLocale());

        LOG.info(msg);

        // 結果情報
        return ApiResult.error(Message.MSGID_K00011, msg);
      }
      // 概算ヘッダ情報削除
      tRoughEstHdrEntity.setDelFlg(CommonConstants.DELETE_FLAG_DELETE);
      tRoughEstHdrRepository.save(tRoughEstHdrEntity);

      // 削除が正常に完了しました。
      return ApiResult.success();
    } catch (ConflictException ex) {
      throw ex;
    } catch (Exception ex) {
      LOG.error(ex.toString(), ex);

      throw new SystemException(
          messageSource.getMessage(Message.MSGID_S0000, null, LocaleContextHolder.getLocale()));
    }
  }

  /**
   * 単価情報取得
   *
   * @param regionCd 地域区分
   * @param constrSiteK 現場区分
   */
  public ApiResult<?> getMPriceInfo(String regionCd, String constrSiteK) {
    try {
      List<MPriceRegionInfoDto> mPriceRegionInfoDtoList =
          c0030Repository.getMPriceInfo("1", regionCd, constrSiteK);
      if (mPriceRegionInfoDtoList.isEmpty() || mPriceRegionInfoDtoList.size() == 0) {
        String msg =
            messageSource.getMessage(Message.MSGID_A00005, null, LocaleContextHolder.getLocale());

        LOG.info(msg);

        return ApiResult.error(Message.MSGID_A00005, msg);
      }
      // 戻り値
      C0030S02ReturnData returnData = new C0030S02ReturnData();
      // 概算情報リスト
      returnData.setMPriceRegionInfoDtoList(mPriceRegionInfoDtoList);
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
   * 出来高シミュレーション処理
   *
   * @param inDto 概算情報パラメータ
   * @return
   */
  public ApiResult<C0030S01ReturnData> createGaisanDekidakaSData(C0030S04Dto inDto) {

    try {
      TRoughEstHdr idList = tRoughEstHdrRepository.findByRoughEstCd(inDto.getRoughEstCd());
      if (idList == null) {
        String msg =
            messageSource.getMessage(Message.MSGID_K00011, null, LocaleContextHolder.getLocale());

        LOG.info(msg);

        // 結果情報
        return ApiResult.error(Message.MSGID_K00011, msg);
      }
      // 業務種類情報.業務テーブルID、引数.業務データIDをキーに、業務データ最新承認情報テーブルを検索
      Optional<TBusinessNewestAppr> apprData =
          tbusinessNewestApprRepository.findByBusinessTblIdAndBusinessDataIdAndBusinessDataSt(
              "T_ROUGH_EST_HDR", idList.getId(), "3");

      if (!apprData.isPresent()) {
        String msg =
            messageSource.getMessage(Message.MSGID_C00007, null, LocaleContextHolder.getLocale());

        LOG.info(msg);
        // 結果情報
        return ApiResult.error(Message.MSGID_C00007, msg);
      }
      // 概算登録画面の情報を出来高シミュレーションTBLに保存する
      // TEvSim tEvSim = c0030Mapper.toTEvSimEntity(idList.getId(), inDto.getRoughEstCd());
      TEvSim tEvSim = new TEvSim();
      tEvSim.setRoughEstHid(idList.getId());
      tEvSim.setRoughEstCd(inDto.getRoughEstCd());
      tEvSim.setDelFlg(CommonConstants.DELETE_FLAG_VALID);
      tEvSim.setEvRegDt(DateUtils.formatNow(DateUtils.DATE_FORMAT));
      TEvSim tEvSims = tEvSimRepository.save(tEvSim);

      // 概算明細リストの情報を出来高シミュレーション予算項目TBLに保存する
      List<TEvSimBgt> tEvSimBgts =
          c0030Mapper.toTEvSimBgtEntityList(inDto.getListC0030S02DtoInfoDto(), tEvSims.getId());
      tEvSimBgts.stream().distinct().collect(Collectors.toList());
      tEvSimBgtRepository.saveAll(tEvSimBgts);
      String msg =
          messageSource.getMessage(Message.MSGID_C00004, null, LocaleContextHolder.getLocale());

      LOG.info(msg);
      // 保存が正常に完了しました。
      return ApiResult.error(Message.MSGID_C00004, msg);

    } catch (Exception ex) {
      LOG.error(ex.toString(), ex);

      throw ex;
    }
  }

  /**
   * 概算見積依頼情報
   *
   * @param roughEstCd 概算コード
   * @return
   */
  public ApiResult<C0030S04ReturnData> getGaisanMitsumoriIraiData(String roughEstCd) {

    try {
      TRoughEstHdr idList = tRoughEstHdrRepository.findByRoughEstCd(roughEstCd);
      if (idList == null) {
        String msg =
            messageSource.getMessage(Message.MSGID_K00011, null, LocaleContextHolder.getLocale());

        LOG.info(msg);
        // 結果情報
        return ApiResult.error(Message.MSGID_K00011, msg);
      }
      // 戻り値
      C0030S04ReturnData returnData = new C0030S04ReturnData();
      // 概算情報リスト
      returnData.setListRoughEstDtlInfoDto(c0030Repository.getRoughEstDtlInfoDto(idList.getId()));
      return ApiResult.success(returnData);

    } catch (Exception ex) {
      LOG.error(ex.toString(), ex);

      throw ex;
    }
  }

  /**
   * 印刷処理
   *
   * @param roughEstCd 概算情報印刷パラメータ
   * @return pdf
   */
  public ApiResult<?> exportReportToPdf(String roughEstCd, String sysDate) {
    try {
      // 概算情報を取得
      RoughInfoDto roughInfoDto =
          c0030Repository.getRoughInfoDto(roughEstCd).orElseThrow(ConflictException::new);
      if (roughInfoDto == null) {
        // メッセージ
        String msg =
            messageSource.getMessage(Message.MSGID_A00005, null, LocaleContextHolder.getLocale());

        LOG.info(msg);

        return ApiResult.error(Message.MSGID_A00005, msg);
      }
      // 概算明細リスト
      List<RoughEstDtlInfoDto> roughEstDtlInfoDto =
          c0030Repository.getRoughEstDtlInfoDto(roughInfoDto.getRoughEstId());
      C0030S05Dto data = c0030Mapper.toRoughInfo(roughInfoDto, sysDate);
      if (!CollectionUtils.isEmpty(roughEstDtlInfoDto)
          && roughEstDtlInfoDto.size() > CommonConstants.SEARCH_MAX_COUNT) {
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
      // レポートに渡すパラメータ
      Map<String, Object> paramsMap =
          objectMapper.convertValue(data, new TypeReference<Map<String, Object>>() {});

      // データソースを生成する
      JRDataSource dataSource = new JRBeanCollectionDataSource(roughEstDtlInfoDto);

      // レポートを生成する
      byte[] datas = reportService.exportReportToPdf(REPORT_FILE_NAME, paramsMap, dataSource);

      return ApiResult.success(datas);
    } catch (Exception ex) {
      LOG.error(ex.toString(), ex);

      throw new SystemException(ex.toString(), ex);
    }
  }
}
