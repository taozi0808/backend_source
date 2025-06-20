package com.daitoj.tkms.modules.apib0030.service;

import com.daitoj.tkms.domain.MCustomer;
import com.daitoj.tkms.domain.MEmp;
import com.daitoj.tkms.domain.MOffice;
import com.daitoj.tkms.domain.MOrg;
import com.daitoj.tkms.domain.MPaymentTerm;
import com.daitoj.tkms.domain.MTaxRate;
import com.daitoj.tkms.domain.TConstrSite;
import com.daitoj.tkms.domain.TDetailedEstDtl;
import com.daitoj.tkms.domain.TDetailedEstHdr;
import com.daitoj.tkms.domain.TEvSim;
import com.daitoj.tkms.domain.TEvSimBgt;
import com.daitoj.tkms.domain.TExecBgtDtl;
import com.daitoj.tkms.domain.TExecBgtHdr;
import com.daitoj.tkms.domain.TProject;
import com.daitoj.tkms.domain.TProjectBuildingDtl;
import com.daitoj.tkms.domain.TProjectPaymentTerms;
import com.daitoj.tkms.domain.TProjectPreWorkDtl;
import com.daitoj.tkms.domain.TProjectRequestDtl;
import com.daitoj.tkms.domain.TProjectSite;
import com.daitoj.tkms.domain.TRoughEstHdr;
import com.daitoj.tkms.modules.apib0030.repository.B0030S01Repository;
import com.daitoj.tkms.modules.apib0030.repository.B0030S02Repository;
import com.daitoj.tkms.modules.apib0030.repository.B0030S03Repository;
import com.daitoj.tkms.modules.apib0030.repository.B0030S04Repository;
import com.daitoj.tkms.modules.apib0030.repository.B0030S05Repository;
import com.daitoj.tkms.modules.apib0030.repository.B0030S06Repository;
import com.daitoj.tkms.modules.apib0030.repository.B0030S07Repository;
import com.daitoj.tkms.modules.apib0030.repository.B0030S08Repository;
import com.daitoj.tkms.modules.apib0030.repository.B0030S09Repository;
import com.daitoj.tkms.modules.apib0030.repository.B0030S10Repository;
import com.daitoj.tkms.modules.apib0030.repository.mapper.B0030Mapper;
import com.daitoj.tkms.modules.apib0030.service.dto.B0030Dto;
import com.daitoj.tkms.modules.apib0030.service.dto.B0030PreWorkPrintDto;
import com.daitoj.tkms.modules.apib0030.service.dto.B0030PrintDto;
import com.daitoj.tkms.modules.apib0030.service.dto.B0030S01ReturnData;
import com.daitoj.tkms.modules.apib0030.service.dto.B0030S02ReturnData;
import com.daitoj.tkms.modules.apib0030.service.dto.B0030S03ReturnData;
import com.daitoj.tkms.modules.apib0030.service.dto.B0030S04ReturnData;
import com.daitoj.tkms.modules.apib0030.service.dto.DetailedEstDtlDto;
import com.daitoj.tkms.modules.apib0030.service.dto.ProjectBuildingDtlDto;
import com.daitoj.tkms.modules.apib0030.service.dto.ProjectDto;
import com.daitoj.tkms.modules.apib0030.service.dto.ProjectPaymentTermsDto;
import com.daitoj.tkms.modules.apib0030.service.dto.ProjectPreWorkDtlDto;
import com.daitoj.tkms.modules.apib0030.service.dto.ProjectRequestDtlDto;
import com.daitoj.tkms.modules.apib0030.service.dto.ProjectRequestPrintDto;
import com.daitoj.tkms.modules.apib0030.service.dto.ProjectResultDto;
import com.daitoj.tkms.modules.apib0030.service.dto.ProjectSiteDto;
import com.daitoj.tkms.modules.common.constants.CommonConstants;
import com.daitoj.tkms.modules.common.constants.MasterData;
import com.daitoj.tkms.modules.common.constants.Message;
import com.daitoj.tkms.modules.common.repository.MCustomerRepository;
import com.daitoj.tkms.modules.common.repository.MEmpRepository;
import com.daitoj.tkms.modules.common.repository.MItemListSettingRepository;
import com.daitoj.tkms.modules.common.repository.MOrgRepository;
import com.daitoj.tkms.modules.common.repository.MPaymentTermRepository;
import com.daitoj.tkms.modules.common.repository.MTaxRateRepository;
import com.daitoj.tkms.modules.common.repository.TEvSimBgtRepository;
import com.daitoj.tkms.modules.common.repository.TEvSimRepository;
import com.daitoj.tkms.modules.common.repository.TExecBgtDtlRepository;
import com.daitoj.tkms.modules.common.repository.TExecBgtHdrRepository;
import com.daitoj.tkms.modules.common.repository.mapper.MCustomerMapper;
import com.daitoj.tkms.modules.common.repository.mapper.MEmpMapper;
import com.daitoj.tkms.modules.common.repository.mapper.MOrgMapper;
import com.daitoj.tkms.modules.common.service.CloudStorageService;
import com.daitoj.tkms.modules.common.service.ConflictException;
import com.daitoj.tkms.modules.common.service.ItemListSettingService;
import com.daitoj.tkms.modules.common.service.NumberService;
import com.daitoj.tkms.modules.common.service.ReportService;
import com.daitoj.tkms.modules.common.service.SystemException;
import com.daitoj.tkms.modules.common.service.dto.ApiResult;
import com.daitoj.tkms.modules.common.service.dto.MItemListSettingDto;
import com.daitoj.tkms.modules.common.utils.DateUtils;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import net.sf.jasperreports.engine.JREmptyDataSource;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

/** 案件登録ビジネスロジック */
@Service
@Transactional(rollbackFor = Throwable.class)
public class B0030Service {
  private static final Logger LOG = LoggerFactory.getLogger(B0030Service.class);

  /** 案件登録リポジトリ */
  private final B0030S01Repository b0030S01Repository;

  /** 物件情報リポジトリ */
  private final B0030S02Repository b0030S02Repository;

  /** 案件請求条件情報リポジトリ */
  private final B0030S03Repository b0030S03Repository;

  /** 現場棟明細情報リポジトリ */
  private final B0030S04Repository b0030S04Repository;

  /** 案件要望明細情報リポジトリ */
  private final B0030S05Repository b0030S05Repository;

  /** 先行作業明細リポジトリ */
  private final B0030S06Repository b0030S06Repository;

  /** 概算ヘッダリポジトリ */
  private final B0030S07Repository b0030S07Repository;

  /** 精積算ヘッダ情報リポジトリ */
  private final B0030S08Repository b0030S08Repository;

  /** 精積算明細情報リポジトリ */
  private final B0030S10Repository b0030S10Repository;

  /** 現場情報のリポジトリ */
  private final B0030S09Repository b0030S09Repository;

  /** 出来高シミュレーションのリポジトリ */
  private final TEvSimRepository tevSimRepository;

  /** 消費税率のリポジトリ */
  private final MTaxRateRepository mtaxRateRepository;

  /** 請求条件リポジトリ */
  private final MPaymentTermRepository mpaymentTermRepository;

  /** 実行予算ヘッダのリポジトリ */
  private final TExecBgtHdrRepository texecBgtHdrRepository;

  /** 実行予算明細のリポジトリ */
  private final TExecBgtDtlRepository texecBgtDtlRepository;

  /** 出来高シミュレーション予算項目のリポジトリ */
  private final TEvSimBgtRepository tevSimBgtRepository;

  /** 案件登録情報リポジトリ */
  private final B0030Mapper b0030Mapper;

  /** 案件登録情報リポジトリ */
  private final MCustomerMapper mcustomerMapper;

  /** 従業員情報リポジトリ */
  private final MEmpMapper mempMapper;

  /** 顧客情報のリポジトリ */
  private final MCustomerRepository mcustomerRepository;

  /** 組織情報のリポジトリ */
  private final MOrgMapper morgMapper;

  /** マスタデータリポジトリ */
  private final MItemListSettingRepository mitemListSettingRepository;

  /** 従業員情報のリポジトリ */
  private final MEmpRepository mempRepository;

  /** 組織情報のリポジトリ */
  private final MOrgRepository morgRepository;

  /** マスタデータサービス */
  private final ItemListSettingService itemListSettingService;

  /** 採番サービス */
  private final NumberService numberRuleService;

  /** ストレージサービス */
  private final CloudStorageService cloudStorageService;

  /** メッセージ */
  private final MessageSource messageSource;

  /** 採番項目ID(案件コード) */
  private static final String FIELD_ID_PROJECT_CD = "PROJECT_CD";

  /** 採番項目ID(物件コード) */
  private static final String FIELD_ID_PROJECT_SITE_CD = "PROJECT_SITE_CD";

  /** 概算コード */
  private static final String ROUGH_EST_CD = "G";

  /** 精積算コード */
  private static final String DETAILED_EST_CD = "S";

  /** 付帯工事コード */
  private static final String RELATED_WORK_CD = "000";

  /** fasterxml.jacksonのObjectMapper */
  private final ObjectMapper objectMapper;

  /** レポートサービス */
  private final ReportService reportService;

  /** レポートファイル名 */
  public static final String REPORT_PRE_WORK_FILE_NAME = "WebB0030PreWork.jasper";

  /** レポートファイル名 */
  public static final String REPORT_FILE_NAME = "WebB0030.jasper";

  /** 帳票日付フォーマット */
  private static final String PDF_DATE_FORMAT = "yyyy年MM年dd日HH:mm:ss";

  /** コンストラクタ */
  public B0030Service(
    B0030S01Repository b0030S01Repository,
    B0030S02Repository b0030S02Repository,
    B0030S03Repository b0030S03Repository,
    B0030S04Repository b0030S04Repository,
    B0030S05Repository b0030S05Repository,
    B0030S06Repository b0030S06Repository,
    B0030S07Repository b0030S07Repository,
    B0030S08Repository b0030S08Repository,
    B0030S09Repository b0030S09Repository,
    B0030S10Repository b0030S10Repository,
    MCustomerRepository mcustomerRepository,
    TExecBgtHdrRepository texecBgtHdrRepository,
    TExecBgtDtlRepository texecBgtDtlRepository,
    TEvSimBgtRepository tevSimBgtRepository,
    TEvSimRepository tevSimRepository,
    MEmpRepository mempRepository,
    MOrgRepository morgRepository,
    MTaxRateRepository mtaxRateRepository,
    MPaymentTermRepository mpaymentTermRepository,
    MItemListSettingRepository mitemListSettingRepository,
    B0030Mapper b0030Mapper,
    MCustomerMapper mcustomerMapper,
    MEmpMapper mempMapper,
    MOrgMapper morgMapper,
    ItemListSettingService itemListSettingService,
    NumberService numberRuleService,
    CloudStorageService cloudStorageService,
    MessageSource messageSource,
    ObjectMapper objectMapper,
    ReportService reportService) {
    this.b0030S01Repository = b0030S01Repository;
    this.b0030S02Repository = b0030S02Repository;
    this.b0030S03Repository = b0030S03Repository;
    this.b0030S04Repository = b0030S04Repository;
    this.b0030S05Repository = b0030S05Repository;
    this.b0030S06Repository = b0030S06Repository;
    this.b0030S07Repository = b0030S07Repository;
    this.b0030S08Repository = b0030S08Repository;
    this.b0030S09Repository = b0030S09Repository;
    this.b0030S10Repository = b0030S10Repository;
    this.mcustomerRepository = mcustomerRepository;
    this.mtaxRateRepository = mtaxRateRepository;
    this.texecBgtHdrRepository = texecBgtHdrRepository;
    this.texecBgtDtlRepository = texecBgtDtlRepository;
    this.tevSimRepository = tevSimRepository;
    this.tevSimBgtRepository = tevSimBgtRepository;
    this.mempRepository = mempRepository;
    this.morgRepository = morgRepository;
    this.mpaymentTermRepository = mpaymentTermRepository;
    this.mitemListSettingRepository = mitemListSettingRepository;
    this.b0030Mapper = b0030Mapper;
    this.mcustomerMapper = mcustomerMapper;
    this.mempMapper = mempMapper;
    this.morgMapper = morgMapper;
    this.itemListSettingService = itemListSettingService;
    this.numberRuleService = numberRuleService;
    this.cloudStorageService = cloudStorageService;
    this.messageSource = messageSource;
    this.objectMapper = objectMapper;
    this.reportService = reportService;
  }

  /**
   * 選択項目取得
   *
   * @return 選択項目
   */
  public ApiResult<B0030S01ReturnData> getSentakuKoumoku() {
    try {
      // システム日付
      String sysDate = DateUtils.formatNow(DateUtils.DATE_FORMAT);

      // マスタデータリストを取得
      ApiResult<List<MItemListSettingDto>> result =
          itemListSettingService.getDataList(
              new String[] {
                MasterData.ITEM_CLASS_CD_D0002,
                MasterData.ITEM_CLASS_CD_D0001,
                MasterData.ITEM_CLASS_CD_D0006,
                MasterData.ITEM_CLASS_CD_D0003,
                MasterData.ITEM_CLASS_CD_D0007,
                MasterData.ITEM_CLASS_CD_D0030,
              });

      // 結果情報
      B0030S01ReturnData ret = new B0030S01ReturnData();
      // マスタデータリスト情報
      ret.setSettingList(result.getData());

      // 顧客名リストを取得
      List<MCustomer> customerList =
          mcustomerRepository.findAll(Sort.by(Sort.Order.asc("customerCd")));
      // 顧客名リストを設定
      ret.setCustomerList(mcustomerMapper.toCustomerSearchList(customerList));

      // 営業部門リストを取得
      List<MOrg> orgList = morgRepository.findBusyos(DateUtils.formatNow(DateUtils.DATE_FORMAT));
      // 営業部門リストを設定
      ret.setOrgList(morgMapper.toOrgSearchDtoList(orgList));

      // 営業管理職を取得
      List<MEmp> mgrList = mempRepository.findByPositionNm(CommonConstants.POSITION_MGR_NAME);
      // 営業管理職リストを設定
      ret.setMgrList(mempMapper.toEmpSearchDtoList(mgrList));

      // 営業担当者リストを取得
      List<MEmp> empList = mempRepository.findAll(Sort.by(Sort.Order.asc("empCd")));
      // 営業担当者リストを設定
      ret.setPicList(mempMapper.toEmpSearchDtoList(empList));

      // 消費税率を取得
      List<MTaxRate> taxRateList = mtaxRateRepository.findByTaxRateList(sysDate);
      // 消費税率を設定
      ret.setTaxRateList(b0030Mapper.toTaxRateList(taxRateList));
      // 請求条件を取得
      List<MPaymentTerm> paymentTermList =
          mpaymentTermRepository.findAll(Sort.by(Sort.Order.asc("paymentTermsCd")));
      // 請求条件を設定
      ret.setPaymentTermList(b0030Mapper.toPaymentTermList(paymentTermList));

      return ApiResult.success(ret);
    } catch (Exception ex) {
      LOG.error(ex.toString(), ex);

      throw new SystemException(
          messageSource.getMessage(Message.MSGID_S0000, null, LocaleContextHolder.getLocale()));
    }
  }

  /**
   * 小工事リスト項目取得
   *
   * @return 小工事リスト項目
   */
  public ApiResult<B0030S04ReturnData> getMinorWork(String projectCd, String majorWorkCd) {
    try {
      // 結果情報
      B0030S04ReturnData ret = new B0030S04ReturnData();

      // 小工事リストを取得
      List<DetailedEstDtlDto> minorWorkList =
          b0030S06Repository.findMinorWorkByProjectCd(projectCd, majorWorkCd);
      // 小工事リストを設定
      ret.setMinorWorkList(minorWorkList);

      return ApiResult.success(ret);
    } catch (Exception ex) {
      LOG.error(ex.toString(), ex);

      throw new SystemException(
          messageSource.getMessage(Message.MSGID_S0000, null, LocaleContextHolder.getLocale()));
    }
  }

  /**
   * 案件情報取得
   *
   * @param projectCd 案件コード
   * @return 案件情報
   */
  public ApiResult<B0030S02ReturnData> getAnkenInfo(String projectCd) {
    try {
      // システム日付
      String sysDate = DateUtils.formatNow(DateUtils.DATE_FORMAT);

      // 戻り値
      B0030S02ReturnData returnData = new B0030S02ReturnData();

      // 案件情報を取得
      ProjectResultDto projectInfo =
          b0030S01Repository.findAnkenInfo(projectCd).orElseThrow(ConflictException::new);

      // 案件情報を設定
      returnData.setProjectInfo(projectInfo);

      if (projectInfo.getOrderStCd() != null) {
        // 受注状態を取得
        mitemListSettingRepository
            .findById_ItemClassCdAndId_ItemCdAndId_EffectiveStartDtLessThanEqual(
                MasterData.ITEM_CLASS_CD_D0001, projectInfo.getOrderStCd(), sysDate)
            .ifPresent(item -> projectInfo.setOrderStNm(item.getItemValue()));
      }

      if (projectInfo.getProjectK() != null) {
        // 案件区分を取得
        mitemListSettingRepository
            .findById_ItemClassCdAndId_ItemCdAndId_EffectiveStartDtLessThanEqual(
                MasterData.ITEM_CLASS_CD_D0002, projectInfo.getProjectK(), sysDate)
            .ifPresent(item -> projectInfo.setProjectKNm(item.getItemValue()));
      }

      if (projectInfo.getProgressCd() != null) {
        // 進捗度を取得
        mitemListSettingRepository
            .findById_ItemClassCdAndId_ItemCdAndId_EffectiveStartDtLessThanEqual(
                MasterData.ITEM_CLASS_CD_D0003, projectInfo.getProgressCd(), sysDate)
            .ifPresent(item -> projectInfo.setProgressNm(item.getItemValue()));
      }

      if (projectInfo.getGovPeoK() != null) {
        // 官民区分を取得
        mitemListSettingRepository
            .findById_ItemClassCdAndId_ItemCdAndId_EffectiveStartDtLessThanEqual(
                MasterData.ITEM_CLASS_CD_D0006, projectInfo.getGovPeoK(), sysDate)
            .ifPresent(item -> projectInfo.setGovPeoKNm(item.getItemValue()));
      }

      if (projectInfo.getPaymentK() != null) {
        // 支払日区分を取得
        mitemListSettingRepository
            .findById_ItemClassCdAndId_ItemCdAndId_EffectiveStartDtLessThanEqual(
                MasterData.ITEM_CLASS_CD_D0007, projectInfo.getPaymentK(), sysDate)
            .ifPresent(item -> projectInfo.setPaymentKNm(item.getItemValue()));
      }

      // 現場棟明細情報
      List<ProjectBuildingDtlDto> projectBuildingDtls =
          b0030S04Repository.findByProjectId(projectInfo.getId(), projectCd);
      returnData.setProjectBuildingDtls(projectBuildingDtls);

      if (!CollectionUtils.isEmpty(projectBuildingDtls)) {
        // 棟番号が１番小さい番号
        String buildingCd =
            projectBuildingDtls.stream()
                .min(Comparator.comparing(ProjectBuildingDtlDto::getBuildingCd))
                .get()
                .getBuildingCd();

        // 物件、概算情報を取得
        List<ProjectSiteDto> projectSites =
            b0030S02Repository.findByProjectCd(projectCd, buildingCd);
        returnData.setProjectSites(projectSites);
      }

      // 案件請求条件情報を取得
      List<ProjectPaymentTermsDto> projectPaymentTerms =
          b0030S03Repository.findByProjectId(projectInfo.getId(), sysDate);
      if (!CollectionUtils.isEmpty(projectPaymentTerms)) {
        returnData.setProjectPaymentTerms(projectPaymentTerms);
      }

      // 案件要望明細情報を取得
      List<ProjectRequestDtlDto> projectRequestDtls =
          b0030S05Repository.findByProjectId(projectInfo.getId());
      if (!CollectionUtils.isEmpty(projectRequestDtls)) {
        returnData.setProjectRequestDtls(projectRequestDtls);
      }

      // 先行作業明細を取得
      List<ProjectPreWorkDtlDto> projectPreWorkDtls =
          b0030S06Repository.findByProjectId(projectInfo.getId());
      if (!CollectionUtils.isEmpty(projectPreWorkDtls)) {
        returnData.setProjectPreWorkDtls(projectPreWorkDtls);
      }

      // 大工事リストを取得
      List<DetailedEstDtlDto> majorWorkList =
          b0030S06Repository.findMajorWorkByProjectCd(projectCd);

      if (!CollectionUtils.isEmpty(majorWorkList)) {
        // 大工事リストを設定
        returnData.setMajorWorkList(majorWorkList.stream().distinct().collect(Collectors.toList()));
      }

      // 発注情報明細 TODO 未確定

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
   * 案件登録保存
   *
   * @param inDto 案件登録保存パラメータ
   * @return 案件ID、歴番
   */
  public ApiResult<B0030S03ReturnData> saveAnkenInfo(B0030Dto inDto, MultipartFile[] files) {
    try {
      // 先行作業内諾書明細ファイル
      if (files != null && files.length > 0) {
        // 従業員顔写真アップロード
        List<UUID> uuids = cloudStorageService.upload(files);

        // 従業員顔写真情報を設定
        for (int i = 0; i < uuids.size(); i++) {
          if (!CollectionUtils.isEmpty(inDto.getProjectPreWorkDtls())) {
            // ファイルのインデックス
            String fileIndex = String.valueOf(i);
            // ファイルのDto
            Optional<ProjectPreWorkDtlDto> dtlDto =
                inDto.getProjectPreWorkDtls().stream()
                    .filter(item -> fileIndex.equals(item.getFileIndex()))
                    .findAny();
            if (dtlDto.isPresent()) {
              // S3パスを設定
              dtlDto.get().setFileId(uuids.get(i));
            }
          }
        }
      }

      // 案件情報
      ProjectDto projectInfo = inDto.getProjectInfo();
      // 案件情報を更新
      TProject newProjectEntity = null;
      // 案件コード
      String projectCd = null;
      // 戻り値
      B0030S03ReturnData ret = new B0030S03ReturnData();

      // 処理区分が新規
      if (CommonConstants.SHORIKUBUN_SINNKI.equals(inDto.getShorikubun())) {
        // 案件コードを採番
        projectCd =
            numberRuleService.getNextNumberByFieldId(
                FIELD_ID_PROJECT_CD, projectInfo.getProjectK(), null);

        // 案件を登録
        newProjectEntity = insertProjectInfo(projectInfo, projectCd);

        // 戻り値を設定(案件ID)
        ret.setProjectId(newProjectEntity.getId());
        // 戻り値を設定(歴番)
        ret.setHisNo(newProjectEntity.getHisNo());
        // 編集の場合
      } else {
        // 案件コード
        projectCd = projectInfo.getProjectCd();

        // 最新案件情報を取得
        TProject tproject =
            b0030S01Repository.findById(projectInfo.getId()).orElseThrow(ConflictException::new);

        // 排他チェック
        if ((projectInfo.getUpdTs() == null && tproject.getUpdTs() != null)
            || (projectInfo.getUpdTs() != null
                && !projectInfo.getUpdTs().equals(tproject.getUpdTs()))) {
          throw new ConflictException();
        }

        // 案件エンティティに変換
        TProject projectEntity = b0030Mapper.toProjectEntity(projectInfo);

        // 案件情報を更新
        newProjectEntity = updateProjectInfo(tproject, projectEntity);

        // 戻り値を設定(案件ID)
        ret.setProjectId(newProjectEntity.getId());
        // 戻り値を設定(歴番)
        ret.setHisNo(newProjectEntity.getHisNo());
      }

      // t_project以外の明細データを保存
      saveDtlInfo(ret.getProjectId(), inDto);

      // 処理区分が新規
      if (CommonConstants.SHORIKUBUN_SINNKI.equals(inDto.getShorikubun())) {
        // 概算情報データを作成する
        saveRoughEstHdr(projectCd, inDto);

        // 精積算情報データを作成する
        saveDetailedEstHdr(projectCd, inDto);
      }

      // 現場情報
      List<TConstrSite> constrSiteList = null;

      // 受注状態:成約
      if (MasterData.ITEM_CLASS_CD_D0001_ITEM_CD_2.equals(projectInfo.getOrderStCd())) {
        // 物件情報を作成する
        TProjectSite projectSite = saveProjectSite(projectCd, inDto);

        // 戻り値を設定(物件ID)
        ret.setProjectSiteId(projectSite.getId());
        // 戻り値を設定(物件コード)
        ret.setProjectSiteCd(projectSite.getProjectSiteCd());

        // 現場情報を作成する
        constrSiteList = saveConstrSite(projectSite, inDto);
      }

      // 実行予算、出来高を保存
      saveExecBgt(newProjectEntity, constrSiteList);

      return ApiResult.success(ret);
    } catch (ConflictException ex) {
      throw ex;
    } catch (Exception ex) {
      LOG.error(ex.toString(), ex);

      throw new SystemException(
          messageSource.getMessage(Message.MSGID_S0000, null, LocaleContextHolder.getLocale()));
    }
  }

  /**
   * 案件情報を登録
   *
   * @param projectDto 案件情報
   * @param projectCd 案件コード
   * @return 登録した案件情報
   */
  private TProject insertProjectInfo(ProjectDto projectDto, String projectCd) {
    // 案件エンティティに変換
    TProject tproject = b0030Mapper.toProjectEntity(projectDto);
    // 案件コード
    tproject.setProjectCd(projectCd);
    // 最新フラグ;1：最新
    tproject.setNewestFlg(CommonConstants.NEWEST_FLAG_NEW);
    // 歴番;01から連番
    tproject.setHisNo(CommonConstants.HIS_NO_START);
    // 案件エンティティを登録
    return b0030S01Repository.save(tproject);
  }

  /**
   * 案件情報を更新
   *
   * @param newProjectEntity 案件情報
   * @param dbProjectEntity DBの案件情報
   * @return 更新した案件情報
   */
  public TProject updateProjectInfo(TProject dbProjectEntity, TProject newProjectEntity) {
    // DBの案件情報を過去に更新
    dbProjectEntity.setNewestFlg(CommonConstants.NEWEST_FLAG_HIS);
    b0030S01Repository.save(dbProjectEntity);

    // ID
    newProjectEntity.setId(null);
    // 最新フラグ;1：最新
    newProjectEntity.setNewestFlg(CommonConstants.NEWEST_FLAG_NEW);
    // 歴番 + 1
    newProjectEntity.setHisNo(dbProjectEntity.getHisNo() + 1);
    // 最新データとして登録
    return b0030S01Repository.save(newProjectEntity);
  }

  /**
   * t_project以外明細のデータを保存
   *
   * @param projectId 案件ID
   * @param inDto 案件登録保存パラメータ
   */
  private void saveDtlInfo(Long projectId, B0030Dto inDto) {
    // 請求条件明細
    List<ProjectPaymentTermsDto> projectPaymentTerms = inDto.getProjectPaymentTerms();

    if (!CollectionUtils.isEmpty(projectPaymentTerms)) {
      // 請求条件明細エンティティリスト
      List<TProjectPaymentTerms> tprojectPaymentTerms =
          b0030Mapper.toProjectPaymentTermEntityList(projectPaymentTerms, projectId);

      // 請求条件明細を登録
      b0030S03Repository.saveAll(tprojectPaymentTerms);
    }

    // 現場棟明細情報
    List<ProjectBuildingDtlDto> projectBuildingDtls = inDto.getProjectBuildingDtls();

    if (!CollectionUtils.isEmpty(projectBuildingDtls)) {
      // 現場棟明細エンティティリスト
      List<TProjectBuildingDtl> tprojectBuildingDtls =
          b0030Mapper.toProjectBuildingDtlEntityList(projectBuildingDtls, projectId);

      // 現場棟明細を登録
      b0030S04Repository.saveAll(tprojectBuildingDtls);
    }

    // 案件要望明細
    List<ProjectRequestDtlDto> projectRequestDtls = inDto.getProjectRequestDtls();

    if (!CollectionUtils.isEmpty(projectRequestDtls)) {
      // 案件要望明細エンティティリスト
      List<TProjectRequestDtl> tprojectRequestDtls =
          b0030Mapper.toTProjectRequestDtlEntityList(projectRequestDtls, projectId);
      // 案件要望明細を登録
      b0030S05Repository.saveAll(tprojectRequestDtls);
    }

    // 先行作業明細
    List<ProjectPreWorkDtlDto> projectPreWorkDtls = inDto.getProjectPreWorkDtls();

    if (!CollectionUtils.isEmpty(projectPreWorkDtls)) {
      // 先行作業明細エンティティリスト
      List<TProjectPreWorkDtl> tprojectPreWorkDtls =
          b0030Mapper.toTProjectPreWorkDtlEntityList(projectPreWorkDtls, projectId);
      // 先行作業明細を登録
      b0030S06Repository.saveAll(tprojectPreWorkDtls);
    }
  }

  /**
   * 概算情報を作成する
   *
   * @param projectCd 案件コード
   * @param inDto 案件登録保存パラメータ
   */
  private void saveRoughEstHdr(String projectCd, B0030Dto inDto) {

    // 現場棟明細情報
    List<ProjectBuildingDtlDto> projectBuildingDtls = inDto.getProjectBuildingDtls();

    if (!CollectionUtils.isEmpty(projectBuildingDtls)) {
      List<TRoughEstHdr> entityList = new ArrayList<>();

      // 棟情報リストのレコード分だけループ
      for (ProjectBuildingDtlDto projectBuildingDtl : projectBuildingDtls) {
        // 概算ヘッダ
        TRoughEstHdr entity = new TRoughEstHdr();
        // 概算コード
        entity.setRoughEstCd(ROUGH_EST_CD + projectCd + projectBuildingDtl.getBuildingCd());
        // 歴番
        entity.setHisNo(CommonConstants.HIS_NO_START);
        // 最新フラグ;1：最新
        entity.setNewestFlg(CommonConstants.NEWEST_FLAG_NEW);
        // 概算日付
        entity.setRoughEstYmd(DateUtils.formatNow(DateUtils.DATE_FORMAT));
        // 案件コード
        entity.setProjectCd(projectCd);
        // 棟コード
        entity.setBuildingCd(projectBuildingDtl.getBuildingCd());

        entityList.add(entity);
      }
      // 概算情報を作成する
      b0030S07Repository.saveAll(entityList);
    }
  }

  /**
   * 精積算情報を作成する
   *
   * @param projectCd 案件コード
   * @param inDto 案件登録保存パラメータ
   */
  private void saveDetailedEstHdr(String projectCd, B0030Dto inDto) {
    // 現場棟明細情報
    List<ProjectBuildingDtlDto> projectBuildingDtls = inDto.getProjectBuildingDtls();

    if (!CollectionUtils.isEmpty(projectBuildingDtls)) {
      List<TDetailedEstHdr> entityList = new ArrayList<>();

      // 棟情報リストのレコード分だけループ
      for (ProjectBuildingDtlDto projectBuildingDtl : projectBuildingDtls) {
        // 精積算情報
        TDetailedEstHdr entity = new TDetailedEstHdr();
        // 精積算コード
        entity.setDetailedEstCd(DETAILED_EST_CD + projectCd + projectBuildingDtl.getBuildingCd());
        // 歴番
        entity.setHisNo(CommonConstants.HIS_NO_START);
        // 最新フラグ;1：最新
        entity.setNewestFlg(CommonConstants.NEWEST_FLAG_NEW);
        // 案件コード
        entity.setProjectCd(projectCd);
        // 顧客支店コード
        entity.setCustomerBranchCd(inDto.getProjectInfo().getCustomerBranchCd());
        // 棟コード
        entity.setBuildingCd(projectBuildingDtl.getBuildingCd());
        // 精積算合計金額
        entity.setDetailedEstTotalAmt(BigDecimal.ZERO);

        entityList.add(entity);
      }
      // 精積算情報を作成する
      b0030S08Repository.saveAll(entityList);
    }
  }

  /**
   * 物件情報を作成する
   *
   * @param projectCd 案件コード
   * @param inDto 案件登録保存パラメータ
   * @return 登録した物件情報
   */
  private TProjectSite saveProjectSite(String projectCd, B0030Dto inDto) {
    // 案件情報
    ProjectDto projectInfo = inDto.getProjectInfo();
    // 物件情報
    TProjectSite entity = new TProjectSite();

    // 物件コード
    if (StringUtils.isNotBlank(projectInfo.getProjectSiteCd())) {
      entity.setProjectSiteCd(projectInfo.getProjectSiteCd());
    } else {
      entity.setProjectSiteCd(
          numberRuleService.getNextNumberByFieldId(FIELD_ID_PROJECT_SITE_CD, null, null));
    }

    // 歴番
    entity.setHisNo(CommonConstants.HIS_NO_START);
    // 最新フラグ;1：最新
    entity.setNewestFlg(CommonConstants.NEWEST_FLAG_NEW);
    // 物件名
    entity.setProjectSiteNm(projectInfo.getProjectNm());
    // 物件カナ名
    entity.setProjectSiteKnNm(projectInfo.getProjectKnNm());
    // 案件コード
    entity.setProjectCd(projectCd);
    // 顧客支店コード
    entity.setCustomerBranchCd(projectInfo.getCustomerBranchCd());
    // 受注年月日
    entity.setOrderYmd(
        DateUtils.formatDateTime(numberRuleService.getSystemDate(), DateUtils.DATE_FORMAT));
    // 税抜請負合計金額
    entity.setExclTaxCoTotalAmt(BigDecimal.ZERO);
    // 税込請負合計金額
    entity.setInclTaxCoTotalAmt(BigDecimal.ZERO);
    // 消費税合計金額
    entity.setSalesTaxTotalAmt(BigDecimal.ZERO);

    // TODO QA117
    // 着工日
    if (StringUtils.isBlank(projectInfo.getStartHopeYmd())) {
      entity.setConstrStartYmd(
          DateUtils.formatDateTime(numberRuleService.getSystemDate(), DateUtils.DATE_FORMAT));
    } else {
      entity.setConstrStartYmd(projectInfo.getStartHopeYmd());
    }

    // 完工日
    if (StringUtils.isBlank(projectInfo.getCompHopeYmd())) {
      entity.setConstrCompYmd("20991231");
    } else {
      entity.setConstrCompYmd(projectInfo.getCompHopeYmd());
    }

    // 担当事業所コード
    MOffice office = new MOffice();
    office.setOfficeCd(CommonConstants.OFFICE_HONSHA_CODE);
    entity.setIcOfficeCd(office);

    // 物件郵便番号
    entity.setProjectSitePostNo(projectInfo.getPostNo());
    // 物件住所１
    entity.setProjectSiteAddr1(projectInfo.getConstrSiteAddr1());
    // 物件住所２
    entity.setProjectSiteAddr2(projectInfo.getConstrSiteAddr2());

    return b0030S02Repository.save(entity);
  }

  /**
   * 現場情報を作成する
   *
   * @param projectSite 物件情報
   * @param inDto 案件登録保存パラメータ
   */
  private List<TConstrSite> saveConstrSite(TProjectSite projectSite, B0030Dto inDto) {
    List<TConstrSite> retList = null;
    // 案件情報
    ProjectDto projectInfo = inDto.getProjectInfo();

    // 現場棟明細情報
    List<ProjectBuildingDtlDto> projectBuildingDtls = inDto.getProjectBuildingDtls();

    if (!CollectionUtils.isEmpty(projectBuildingDtls)) {
      List<TConstrSite> entityList = new ArrayList<>();

      // 棟情報リストのレコード分だけループ
      for (ProjectBuildingDtlDto projectBuildingDtl : projectBuildingDtls) {
        // 現場情報
        TConstrSite entity = new TConstrSite();
        // 物件ID
        entity.setProjectSite(projectSite);
        // 棟コード
        entity.setBuildingCd(projectBuildingDtl.getBuildingCd());
        // 現場コード
        entity.setConstrSiteCd(
            projectSite.getProjectSiteCd() + "000" + projectBuildingDtl.getBuildingCd());
        // 現場名
        entity.setConstrSiteNm(projectBuildingDtl.getBuildingWorkNm());
        // 付帯工事コード
        entity.setRelatedWorkCd(RELATED_WORK_CD);
        // 建築面積
        entity.setBuildingArea(projectInfo.getBuildingArea());
        // 延床面積
        entity.setGrossFloorArea(projectInfo.getGrossFloorArea());
        // 施工床面積
        entity.setBuildupArea(projectInfo.getBuildupArea());
        // 階数（地上）
        entity.setFloorCnt(projectInfo.getFloorCnt());
        // 階数（地下）
        entity.setBasementCnt(projectInfo.getBasementCnt());

        entityList.add(entity);
      }
      // 現場情報を作成する
      retList = b0030S09Repository.saveAll(entityList);
    }

    return retList;
  }

  /**
   * 実行予算、出来高を保存
   *
   * @param projectEntity 案件情報
   * @param constrSiteList 現場情報
   */
  private void saveExecBgt(TProject projectEntity, List<TConstrSite> constrSiteList) {
    // 実行予算ヘッダ
    TExecBgtHdr newBgtHdr = null;
    // 出来高シミュレーション
    TEvSim newEvSim = null;

    if (!CollectionUtils.isEmpty(constrSiteList)) {
      // 精積算ヘッダを取得
      List<TDetailedEstHdr> detailedEstHdrList =
          b0030S08Repository.findByProjectCd(projectEntity.getProjectCd());

      for (TConstrSite constrSite : constrSiteList) {
        // 実行予算明細の大工事コードリスト
        List<String> majorWorkList = new ArrayList<>();

        // 精積算ヘッダ
        Optional<TDetailedEstHdr> estHdr =
            detailedEstHdrList.stream()
                .filter(
                    item ->
                        item.getProjectCd().equals(projectEntity.getProjectCd())
                            && item.getBuildingCd().equals(constrSite.getBuildingCd()))
                .findAny();


        if (estHdr.isPresent()) {
          TExecBgtHdr bgtHdr = new TExecBgtHdr();
          // 実行予算コード
          bgtHdr.setExecBgtCd(constrSite.getConstrSiteCd());
          // 歴番
          bgtHdr.setHisNo(CommonConstants.HIS_NO_START);
          // 最新フラグ
          bgtHdr.setNewestFlg(CommonConstants.NEWEST_FLAG_NEW);
          // 現場コード
          bgtHdr.setConstrSiteCd(constrSite.getConstrSiteCd());
          // 精積算コード
          bgtHdr.setDetailedEstCd(estHdr.get().getDetailedEstCd());
          // 実行予算合計金額
          bgtHdr.setExecBgtTotalAmt(estHdr.get().getDetailedEstTotalAmt());

          // 実行予算ヘッダを保存
          newBgtHdr = texecBgtHdrRepository.save(bgtHdr);

          // 精積算明細を取得
          List<TDetailedEstDtl> detailedEstDtlList =
              b0030S10Repository.findByDetailedEstHid_Id(estHdr.get().getId());
          if (!CollectionUtils.isEmpty(detailedEstDtlList)) {
            List<TExecBgtDtl> bgtDtlList = new ArrayList<>();

            for (TDetailedEstDtl detailedEstDtl : detailedEstDtlList) {
              TExecBgtDtl bgtDtl = new TExecBgtDtl();
              // 実行予算ヘッダID
              bgtDtl.setExecBgtHid(newBgtHdr);
              // 連番
              bgtDtl.setSeqNo(detailedEstDtl.getSeqNo());
              // 大工事コード
              bgtDtl.setMajorWorkCd(detailedEstDtl.getMajorWorkCd());
              // 小工事コード
              bgtDtl.setMinorWorkCd(detailedEstDtl.getMinorWorkCd());
              // 工事連番
              bgtDtl.setWorkSeqNo(detailedEstDtl.getWorkSeqNo());
              // 規格
              bgtDtl.setSpec(detailedEstDtl.getSpec());
              // 単価
              bgtDtl.setPrice(detailedEstDtl.getPrice());
              // 数量
              bgtDtl.setQty(detailedEstDtl.getQty());
              // 単位区分
              bgtDtl.setUnitK(detailedEstDtl.getUnitK());
              // 目標予算金額
              bgtDtl.setTargetBgtAmt(detailedEstDtl.getDetailedEstAmt());
              // 増減合計金額
              bgtDtl.setChgTotalAmt(BigDecimal.ZERO);
              // 備考
              bgtDtl.setRemarks(detailedEstDtl.getRemarks());

              bgtDtlList.add(bgtDtl);

              // 大工事コードリスト
              if (!majorWorkList.contains(detailedEstDtl.getMajorWorkCd())) {
                majorWorkList.add(detailedEstDtl.getMajorWorkCd());
              }
            }
            // 実行予算明細を保存
            texecBgtDtlRepository.saveAll(bgtDtlList);
          }
        }

        // 概算ヘッダ
        TRoughEstHdr roughEstHdr =
            b0030S07Repository.findByProjectCdAndBuildingCd(
                projectEntity.getProjectCd(), constrSite.getBuildingCd());

        // 概算コードに紐づく出来高シミュレーションデータ
        List<TEvSim> evSims = tevSimRepository.findByRoughEstHid(roughEstHdr.getId());
        if (!CollectionUtils.isEmpty(evSims)) {
          for (TEvSim evSim : evSims) {
            // 削除フラグ＝”1”（取消）
            evSim.setDelFlg(CommonConstants.DELETE_FLAG_DELETE);
          }
        }

        TEvSim evSim = new TEvSim();
        // 現場コード
        evSim.setConstrSiteCd(constrSite.getConstrSiteCd());
        // 出来高登録日
        evSim.setEvRegDt(
            DateUtils.formatDateTime(numberRuleService.getSystemDate(), DateUtils.DATE_FORMAT));
        // 出来高シミュレーションデータを作成
        newEvSim = tevSimRepository.save(evSim);

        // 実行予算明細の大工事コードリスト
        if (!CollectionUtils.isEmpty(majorWorkList)) {
          List<TEvSimBgt> evSimBgtList = new ArrayList<>();

          for (String majorWork : majorWorkList) {
            TEvSimBgt evSimBgt = new TEvSimBgt();
            // 出来高シミュレーションID
            evSimBgt.setEvSim(newEvSim);
            // 大工事コード
            evSimBgt.setMajorWorkCd(majorWork);

            evSimBgtList.add(evSimBgt);
          }

          tevSimBgtRepository.saveAll(evSimBgtList);
        }
      }
    }
  }

  /**
   * 案件情報先行作業明細印刷処理
   *
   * @param inDto 案件情報先行作業明細印刷パラメータ
   * @return pdf
   */
  public ApiResult<?> exportReportPreWorkToPdf(B0030PreWorkPrintDto inDto) {
    try {

      // レポートに渡すパラメータ
      Map<String, Object> paramsMap =
        objectMapper.convertValue(inDto, new TypeReference<Map<String, Object>>() {});

      // レポートを生成する
      byte[] datas = reportService.exportReportToPdf(REPORT_PRE_WORK_FILE_NAME, paramsMap, new JREmptyDataSource());

      return ApiResult.success(datas);
    } catch (Exception ex) {
      LOG.error(ex.toString(), ex);

      throw new SystemException(
        messageSource.getMessage(Message.MSGID_S0000, null, LocaleContextHolder.getLocale()));
    }
  }

  /**
   * 印刷処理
   *
   * @param projectCd 案件情報印刷パラメータ
   * @return pdf
   */
  public ApiResult<?> exportReportToPdf(String projectCd, LocalDateTime sysDate) {
    try {
      // 案件登録印刷DTO
      B0030PrintDto printDto = new B0030PrintDto();
      // 案件情報を取得
      ProjectResultDto projectInfo =
        b0030S01Repository.findAnkenInfo(projectCd).orElseThrow(ConflictException::new);

      if (projectInfo.getProjectK() != null) {
        // 案件区分を取得
        mitemListSettingRepository
          .findById_ItemClassCdAndId_ItemCdAndId_EffectiveStartDtLessThanEqual(
            MasterData.ITEM_CLASS_CD_D0002, projectInfo.getProjectK(), sysDate.format(DateTimeFormatter.ofPattern(PDF_DATE_FORMAT)))
          .ifPresent(item -> projectInfo.setProjectKNm(item.getItemValue()));
      }

      if (projectInfo.getProgressCd() != null) {
        // 進捗度を取得
        mitemListSettingRepository
          .findById_ItemClassCdAndId_ItemCdAndId_EffectiveStartDtLessThanEqual(
            MasterData.ITEM_CLASS_CD_D0003, projectInfo.getProgressCd(), sysDate.format(DateTimeFormatter.ofPattern(PDF_DATE_FORMAT)))
          .ifPresent(item -> projectInfo.setProgressNm(item.getItemValue()));
      }

      if (projectInfo.getGovPeoK() != null) {
        // 官民区分を取得
        mitemListSettingRepository
          .findById_ItemClassCdAndId_ItemCdAndId_EffectiveStartDtLessThanEqual(
            MasterData.ITEM_CLASS_CD_D0006, projectInfo.getGovPeoK(), sysDate.format(DateTimeFormatter.ofPattern(PDF_DATE_FORMAT)))
          .ifPresent(item -> projectInfo.setGovPeoKNm(item.getItemValue()));
      }

      if (projectInfo.getPaymentK() != null) {
        // 支払日区分を取得
        mitemListSettingRepository
          .findById_ItemClassCdAndId_ItemCdAndId_EffectiveStartDtLessThanEqual(
            MasterData.ITEM_CLASS_CD_D0007, projectInfo.getPaymentK(), sysDate.format(DateTimeFormatter.ofPattern(PDF_DATE_FORMAT)))
          .ifPresent(item -> projectInfo.setPaymentKNm(item.getItemValue()));
      }

      if (projectInfo.getClosingDay() != null) {
        // 締日区分項目内容を取得
        mitemListSettingRepository
          .findById_ItemClassCdAndId_ItemCdAndId_EffectiveStartDtLessThanEqual(
            MasterData.ITEM_CLASS_CD_D0030, projectInfo.getClosingDay(), sysDate.format(DateTimeFormatter.ofPattern(PDF_DATE_FORMAT)))
          .ifPresent(item -> printDto.setClosingDayNm(item.getItemValue()));
      }

      if (projectInfo.getPaymentD() != null) {
        // 支払日区分項目内容を取得
        mitemListSettingRepository
          .findById_ItemClassCdAndId_ItemCdAndId_EffectiveStartDtLessThanEqual(
            MasterData.ITEM_CLASS_CD_D0030, projectInfo.getPaymentD(), sysDate.format(DateTimeFormatter.ofPattern(PDF_DATE_FORMAT)))
          .ifPresent(item -> printDto.setPaymentDNm(item.getItemValue()));
      }

      // 現場棟明細情報
      List<ProjectBuildingDtlDto> projectBuildingDtls =
        b0030S04Repository.findByProjectId(projectInfo.getId(), projectCd);

      // 案件請求条件情報を取得
      List<ProjectPaymentTermsDto> projectPaymentTerms =
        b0030S03Repository.findByProjectId(projectInfo.getId(), DateUtils.formatNow(DateUtils.DATE_FORMAT));
      // 案件要望明細情報を取得
      List<ProjectRequestDtlDto> projectRequestDtls =
        b0030S05Repository.findByProjectId(projectInfo.getId());

      List<ProjectSiteDto> projectSites = new ArrayList<>();
      if (!CollectionUtils.isEmpty(projectBuildingDtls)) {
        // 棟番号が１番小さい番号
        String buildingCd =
          projectBuildingDtls.stream()
            .min(Comparator.comparing(ProjectBuildingDtlDto::getBuildingCd))
            .get()
            .getBuildingCd();

        // 物件、概算情報を取得
       projectSites =
          b0030S02Repository.findByProjectCd(projectCd, buildingCd);
      }

      // 利用PCのシステム日付
      printDto.setSysDate(sysDate.format(DateTimeFormatter.ofPattern(PDF_DATE_FORMAT)));
      // 案件コード
      printDto.setProjectCd(projectInfo.getProjectCd());
      // 案件区分
      printDto.setProjectKNm(projectInfo.getProjectKNm());
      // 案件名
      printDto.setProjectNm(projectInfo.getProjectNm());
      // 案件カナ名
      printDto.setProjectKnNm(projectInfo.getProjectKnNm());

      if (projectSites.size() != 0) {
        // 現場コード
        printDto.setConstrSiteCd(projectSites.get(0).getConstrSiteCd());
        // 現場名
        printDto.setConstrSiteNm(projectSites.get(0).getConstrSiteNm());
        // 物件コード
        printDto.setProjectSiteCd(projectSites.get(0).getProjectSiteCd());
        // 物件名
        printDto.setProjectSiteNm(projectSites.get(0).getProjectSiteNm());
        // 物件カナ名
        printDto.setProjectSiteKnNm(projectSites.get(0).getProjectSiteKnNm());
        // 概算コード
        printDto.setRoughEstCd(projectSites.get(0).getRoughEstCd());
      }
      // 郵便番号
      printDto.setPostNo(projectInfo.getPostNo());
      // 現場住所１
      printDto.setConstrSiteAddr1(projectInfo.getConstrSiteAddr1());
      // 現場住所2
      printDto.setConstrSiteAddr2(projectInfo.getConstrSiteAddr2());
      // 顧客名
      printDto.setCustomerNm(projectInfo.getCustomerNm());
      // 郵便番号（顧客）
      printDto.setCustPostNo(projectInfo.getCustPostNo());
      // 顧客住所１
      printDto.setCustAddr1(projectInfo.getCustAddr1());
      // 顧客住所2
      printDto.setCustAddr2(projectInfo.getCustAddr2());

      // 想定金額
      printDto.setExpectAmt(projectInfo.getExpectAmt());
      // 官民区分項目内容
      printDto.setGovPeoKNm(projectInfo.getGovPeoKNm());
      // 見積提出期限
      printDto.setEstSubmitDueTs(projectInfo.getEstSubmitDueTs());
      BigDecimal param = new BigDecimal("3.305");
      // 敷地面積
      printDto.setSiteArea(projectInfo.getSiteArea());
      printDto.setSiteAreaTsu(projectInfo.getSiteArea().divide(param, 2, RoundingMode.HALF_UP));
      // 建築面積
      printDto.setBuildingArea(projectInfo.getBuildingArea());
      printDto.setBuildingAreaTsu(projectInfo.getBuildingArea().divide(param, 2, RoundingMode.HALF_UP));
      // 延床面積
      printDto.setGrossFloorArea(projectInfo.getGrossFloorArea());
      printDto.setGrossFloorAreaTsu(projectInfo.getGrossFloorArea().divide(param, 2, RoundingMode.HALF_UP));
      // 施工床面積
      printDto.setBuildupArea(projectInfo.getBuildupArea());
      printDto.setBuildupAreaTsu(projectInfo.getBuildupArea().divide(param, 2, RoundingMode.HALF_UP));
      // 専有面積
      printDto.setOccupiedArea(projectInfo.getOccupiedArea());
      printDto.setOccupiedAreaTsu(projectInfo.getOccupiedArea().divide(param, 2, RoundingMode.HALF_UP));
      // 施工面積
      printDto.setConstrArea(projectInfo.getConstrArea());
      printDto.setConstrAreaTsu(projectInfo.getConstrArea().divide(param, 2, RoundingMode.HALF_UP));

      // 戸数
      printDto.setHouseholds(projectInfo.getHouseholds());
      // 階数（地上）
      printDto.setFloorCnt(projectInfo.getFloorCnt());
      // 階数（地下）
      printDto.setBasementCnt(projectInfo.getBasementCnt());

      // 受注見込日
      printDto.setOrderExpectedYmd(projectInfo.getOrderExpectedYmd());
      // 着工希望日
      printDto.setStartHopeYmd(projectInfo.getStartHopeYmd());
      // 完了希望日
      printDto.setCompHopeYmd(projectInfo.getCompHopeYmd());
      // 営業部門名
      printDto.setSalesOrgNm(projectInfo.getSalesOrgNm());
      // 営業管理職名
      printDto.setSalesMgrNm(projectInfo.getSalesMgrNm());
      // 営業担当者名
      printDto.setSalesPicNm(projectInfo.getSalesPicNm());
      // 進捗度項目内容
      printDto.setProgressNm(projectInfo.getProgressNm());
      // 不成約理由
      printDto.setRejectionReason(projectInfo.getRejectionReason());
      // 支払区分
      printDto.setPaymentKNm(projectInfo.getPaymentKNm());
      // 工事経費率
      printDto.setConstrExpRate(projectInfo.getConstrExpRate());
      // 工事経費金額
      printDto.setConstrExpAmt(projectInfo.getConstrExpAmt());
      // 販売管理費率
      printDto.setSaleMgrRate(projectInfo.getSaleMgrRate());
      // 販売管理費金額
      printDto.setSaleMgrAmt(projectInfo.getSaleMgrAmt());
      // 調整金額
      printDto.setTyouseiAmt(projectInfo.getTyouseiAmt());
      // 雇用保険率
      printDto.setEisRate(projectInfo.getEisRate());
      // 健康保険率
      printDto.setEhiRate(projectInfo.getEhiRate());
      // 介護保険率
      printDto.setLtcRate(projectInfo.getLtcRate());
      // 厚生年金率
      printDto.setWpiRate(projectInfo.getWpiRate());
      // 完工希望日
      printDto.setCompHopeYmd(projectInfo.getCompHopeYmd());

      // 現場棟明細情報
      printDto.setProjectBuildingDtls(projectBuildingDtls);
      // 案件請求条件情報
      printDto.setProjectPaymentTerms(projectPaymentTerms);
      // 案件要望明細情報
      List<ProjectRequestPrintDto> projectRequests = IntStream.range(0, projectRequestDtls.size())
        .mapToObj(index -> {
          ProjectRequestDtlDto obj = projectRequestDtls.get(index);
          ProjectRequestPrintDto objContent = new ProjectRequestPrintDto(
            obj.getRequestYmd(),
            obj.getRequestContent(),
            index
          );

          if (obj.getRequestAnswer() != null && !obj.getRequestAnswer().isEmpty()) {
            ProjectRequestPrintDto objAnswer = new ProjectRequestPrintDto(
              obj.getRequestYmd(),
              obj.getRequestAnswer(),
              index
            );
            return Arrays.asList(objContent, objAnswer);
          } else {
            return Collections.singletonList(objContent);
          }
        })
        .flatMap(List::stream)
        .collect(Collectors.toList());
      printDto.setProjectRequestDtls(projectRequests);

      // 割合合計
      BigDecimal paymentRatioSum = projectPaymentTerms.stream()
        .map(item-> new BigDecimal(item.getPaymentRatio()))
        .reduce(BigDecimal.ZERO, BigDecimal::add);
      // 請求金額合計
      BigDecimal exclTaxPaymentAmtSum =
          projectPaymentTerms.stream()
              .map(ProjectPaymentTermsDto::getExclTaxPaymentAmt)
              .reduce(BigDecimal.ZERO, BigDecimal::add);
      // 消費税金額合計
      BigDecimal inclTaxPaymentAmtSum =
        projectPaymentTerms.stream()
          .map(ProjectPaymentTermsDto::getInclTaxPaymentAmt)
          .reduce(BigDecimal.ZERO, BigDecimal::add);
      // 合計金額合計
      BigDecimal salesTaxAmtSum =
        projectPaymentTerms.stream()
          .map(ProjectPaymentTermsDto::getSalesTaxAmt)
          .reduce(BigDecimal.ZERO, BigDecimal::add);
      // 案件請求条件情報合計パラメータ
      Map<String, BigDecimal> params = new HashMap<>();
      params.put("paymentRatioSum", paymentRatioSum);
      params.put("exclTaxPaymentAmtSum", exclTaxPaymentAmtSum);
      params.put("inclTaxPaymentAmtSum", inclTaxPaymentAmtSum);
      params.put("salesTaxAmtSum", salesTaxAmtSum);
      printDto.setParam(params);
      // レポートに渡すパラメータ
      Map<String, Object> paramsMap =
        objectMapper.convertValue(printDto, new TypeReference<Map<String, Object>>() {});

      // レポートを生成する
      byte[] datas = reportService.exportReportToPdf(REPORT_FILE_NAME, paramsMap, new JREmptyDataSource());

      return ApiResult.success(datas);
    } catch (Exception ex) {
      LOG.error(ex.toString(), ex);

      throw new SystemException(
        messageSource.getMessage(Message.MSGID_S0000, null, LocaleContextHolder.getLocale()));
    }
  }
}
