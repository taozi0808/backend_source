package com.daitoj.tkms.modules.apib0030.service;

import com.daitoj.tkms.domain.MCustomer;
import com.daitoj.tkms.domain.MEmp;
import com.daitoj.tkms.domain.MMajorWork;
import com.daitoj.tkms.domain.MMinorWork;
import com.daitoj.tkms.domain.MOffice;
import com.daitoj.tkms.domain.MOrg;
import com.daitoj.tkms.domain.TConstrSite;
import com.daitoj.tkms.domain.TDetailedEstHdr;
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
import com.daitoj.tkms.modules.apib0030.repository.mapper.B0030Mapper;
import com.daitoj.tkms.modules.apib0030.service.dto.B0030Dto;
import com.daitoj.tkms.modules.apib0030.service.dto.B0030S01ReturnData;
import com.daitoj.tkms.modules.apib0030.service.dto.B0030S02ReturnData;
import com.daitoj.tkms.modules.apib0030.service.dto.B0030S03ReturnData;
import com.daitoj.tkms.modules.apib0030.service.dto.B0030S04ReturnData;
import com.daitoj.tkms.modules.apib0030.service.dto.ProjectBuildingDtlDto;
import com.daitoj.tkms.modules.apib0030.service.dto.ProjectDto;
import com.daitoj.tkms.modules.apib0030.service.dto.ProjectPaymentTermsDto;
import com.daitoj.tkms.modules.apib0030.service.dto.ProjectPreWorkDtlDto;
import com.daitoj.tkms.modules.apib0030.service.dto.ProjectRequestDtlDto;
import com.daitoj.tkms.modules.apib0030.service.dto.ProjectResultDto;
import com.daitoj.tkms.modules.apib0030.service.dto.ProjectSiteDto;
import com.daitoj.tkms.modules.common.constants.CommonConstants;
import com.daitoj.tkms.modules.common.constants.MasterData;
import com.daitoj.tkms.modules.common.constants.Message;
import com.daitoj.tkms.modules.common.repository.MCustomerRepository;
import com.daitoj.tkms.modules.common.repository.MEmpRepository;
import com.daitoj.tkms.modules.common.repository.MItemListSettingRepository;
import com.daitoj.tkms.modules.common.repository.MMajorWorkRepository;
import com.daitoj.tkms.modules.common.repository.MMinorWorkRepository;
import com.daitoj.tkms.modules.common.repository.MOrgRepository;
import com.daitoj.tkms.modules.common.repository.mapper.MCustomerMapper;
import com.daitoj.tkms.modules.common.repository.mapper.MEmpMapper;
import com.daitoj.tkms.modules.common.repository.mapper.MMinorWorkMapper;
import com.daitoj.tkms.modules.common.repository.mapper.MOrgMapper;
import com.daitoj.tkms.modules.common.repository.mapper.MajorWorkMapper;
import com.daitoj.tkms.modules.common.service.ConflictException;
import com.daitoj.tkms.modules.common.service.ItemListSettingService;
import com.daitoj.tkms.modules.common.service.NumberService;
import com.daitoj.tkms.modules.common.service.SystemException;
import com.daitoj.tkms.modules.common.service.dto.ApiResult;
import com.daitoj.tkms.modules.common.service.dto.MItemListSettingDto;
import com.daitoj.tkms.modules.common.utils.DateUtils;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

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

  /** 精積算情報リポジトリ */
  private final B0030S08Repository b0030S08Repository;

  /** 現場情報のリポジトリ */
  private final B0030S09Repository b0030S09Repository;

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

  /** 大工事情報のリポジトリ */
  private final MajorWorkMapper majorWorkMapper;

  /** 小工事情報のリポジトリ */
  private final MMinorWorkMapper minorWorkMapper;

  /** マスタデータリポジトリ */
  private final MItemListSettingRepository mitemListSettingRepository;

  /** 従業員情報のリポジトリ */
  private final MEmpRepository mempRepository;

  /** 組織情報のリポジトリ */
  private final MOrgRepository morgRepository;

  /** 大工事情報のリポジトリ */
  private final MMajorWorkRepository mmajorWorkRepository;

  /** 小工事情報のリポジトリ */
  private final MMinorWorkRepository minorWorkRepository;

  /** マスタデータサービス */
  private final ItemListSettingService itemListSettingService;

  /** 採番サービス */
  private final NumberService numberRuleService;

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
      MCustomerRepository mcustomerRepository,
      MEmpRepository mempRepository,
      MOrgRepository morgRepository,
      MMajorWorkRepository mmajorWorkRepository,
      MMinorWorkRepository minorWorkRepository,
      MItemListSettingRepository mitemListSettingRepository,
      B0030Mapper b0030Mapper,
      MCustomerMapper mcustomerMapper,
      MEmpMapper mempMapper,
      MOrgMapper morgMapper,
      MajorWorkMapper majorWorkMapper,
      MMinorWorkMapper minorWorkMapper,
      ItemListSettingService itemListSettingService,
      NumberService numberRuleService,
      MessageSource messageSource) {
    this.b0030S01Repository = b0030S01Repository;
    this.b0030S02Repository = b0030S02Repository;
    this.b0030S03Repository = b0030S03Repository;
    this.b0030S04Repository = b0030S04Repository;
    this.b0030S05Repository = b0030S05Repository;
    this.b0030S06Repository = b0030S06Repository;
    this.b0030S07Repository = b0030S07Repository;
    this.b0030S08Repository = b0030S08Repository;
    this.b0030S09Repository = b0030S09Repository;
    this.mcustomerRepository = mcustomerRepository;
    this.mempRepository = mempRepository;
    this.morgRepository = morgRepository;
    this.mmajorWorkRepository = mmajorWorkRepository;
    this.minorWorkRepository = minorWorkRepository;
    this.mitemListSettingRepository = mitemListSettingRepository;
    this.b0030Mapper = b0030Mapper;
    this.mcustomerMapper = mcustomerMapper;
    this.mempMapper = mempMapper;
    this.morgMapper = morgMapper;
    this.majorWorkMapper = majorWorkMapper;
    this.minorWorkMapper = minorWorkMapper;
    this.itemListSettingService = itemListSettingService;
    this.numberRuleService = numberRuleService;
    this.messageSource = messageSource;
  }

  /**
   * 選択項目取得
   *
   * @return 選択項目
   */
  public ApiResult<B0030S01ReturnData> getSentakuKoumoku() {
    try {
      // マスタデータリストを取得
      ApiResult<List<MItemListSettingDto>> result =
          itemListSettingService.getDataList(
              new String[] {
                MasterData.ITEM_CLASS_CD_D0002,
                MasterData.ITEM_CLASS_CD_D0001,
                MasterData.ITEM_CLASS_CD_D0006,
                MasterData.ITEM_CLASS_CD_D0003,
                MasterData.ITEM_CLASS_CD_D0010,
                MasterData.ITEM_CLASS_CD_D0004,
                MasterData.ITEM_CLASS_CD_D0007,
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

      // 大工事リストを取得
      List<MMajorWork> majorWorkList =
          mmajorWorkRepository.findAll(Sort.by(Sort.Order.asc("displayOrder")));
      // 大工事リストを設定
      ret.setMajorWorkList(majorWorkMapper.toMajorWorkSearchList(majorWorkList));

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
  public ApiResult<B0030S04ReturnData> getMinorWork(String majorWorkCd) {
    try {
      // 結果情報
      B0030S04ReturnData ret = new B0030S04ReturnData();

      // 小工事リストを取得
      List<MMinorWork> minorWorkList =
          minorWorkRepository.findById_MajorWorkCdOrderById_MinorWorkCd(majorWorkCd);
      // 小工事リストを設定
      ret.setMinorWorkList(minorWorkMapper.toMinorWorkSearchList(minorWorkList));

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
                MasterData.ITEM_CLASS_CD_D0001,
                projectInfo.getOrderStCd(),
                DateUtils.formatNow(DateUtils.DATE_FORMAT))
            .ifPresent(item -> projectInfo.setOrderStNm(item.getItemValue()));
      }

      if (projectInfo.getProjectK() != null) {
        // 案件区分を取得
        mitemListSettingRepository
            .findById_ItemClassCdAndId_ItemCdAndId_EffectiveStartDtLessThanEqual(
                MasterData.ITEM_CLASS_CD_D0002,
                projectInfo.getProjectK(),
                DateUtils.formatNow(DateUtils.DATE_FORMAT))
            .ifPresent(item -> projectInfo.setProjectKNm(item.getItemValue()));
      }

      if (projectInfo.getProgressCd() != null) {
        // 進捗度を取得
        mitemListSettingRepository
            .findById_ItemClassCdAndId_ItemCdAndId_EffectiveStartDtLessThanEqual(
                MasterData.ITEM_CLASS_CD_D0003,
                projectInfo.getProgressCd(),
                DateUtils.formatNow(DateUtils.DATE_FORMAT))
            .ifPresent(item -> projectInfo.setProgressNm(item.getItemValue()));
      }

      if (projectInfo.getGovPeoK() != null) {
        // 官民区分を取得
        mitemListSettingRepository
            .findById_ItemClassCdAndId_ItemCdAndId_EffectiveStartDtLessThanEqual(
                MasterData.ITEM_CLASS_CD_D0006,
                projectInfo.getGovPeoK(),
                DateUtils.formatNow(DateUtils.DATE_FORMAT))
            .ifPresent(item -> projectInfo.setGovPeoKNm(item.getItemValue()));
      }

      if (projectInfo.getPaymentK() != null) {
        // 支払日区分を取得
        mitemListSettingRepository
            .findById_ItemClassCdAndId_ItemCdAndId_EffectiveStartDtLessThanEqual(
                MasterData.ITEM_CLASS_CD_D0007,
                projectInfo.getPaymentK(),
                DateUtils.formatNow(DateUtils.DATE_FORMAT))
            .ifPresent(item -> projectInfo.setPaymentKNm(item.getItemValue()));
      }

      // 物件、概算情報を取得
      List<ProjectSiteDto> projectSites = b0030S02Repository.findByProjectCd(projectCd);
      returnData.setProjectSites(projectSites);

      // 案件請求条件情報を取得
      List<ProjectPaymentTermsDto> projectPaymentTerms =
          b0030S03Repository.findByProjectId(projectInfo.getId());

      if (!CollectionUtils.isEmpty(projectPaymentTerms)) {
        for (ProjectPaymentTermsDto paymentTerm : projectPaymentTerms) {
          if (paymentTerm.getPaymentTermsK() != null) {
            // 請求条件区分を取得
            mitemListSettingRepository
                .findById_ItemClassCdAndId_ItemCdAndId_EffectiveStartDtLessThanEqual(
                    MasterData.ITEM_CLASS_CD_D0010,
                    paymentTerm.getPaymentTermsK(),
                    DateUtils.formatNow(DateUtils.DATE_FORMAT))
                .ifPresent(dataItem -> paymentTerm.setPaymentTermsKNm(dataItem.getItemValue()));
          }

          if (paymentTerm.getTaxRateId() != null) {
            // 消費税区分を取得 // TODO 確認要
            mitemListSettingRepository
                .findById_ItemClassCdAndId_ItemCdAndId_EffectiveStartDtLessThanEqual(
                    MasterData.ITEM_CLASS_CD_D0004,
                    String.valueOf(paymentTerm.getTaxRateId()),
                    DateUtils.formatNow(DateUtils.DATE_FORMAT))
                .ifPresent(dataItem -> paymentTerm.setTaxRate(dataItem.getItemValue()));
          }
        }
        // 案件請求条件情報を設定
        returnData.setProjectPaymentTerms(projectPaymentTerms);
      }

      // 現場棟明細情報
      List<ProjectBuildingDtlDto> projectBuildingDtls =
          b0030S04Repository.findByProjectId(projectInfo.getId(), projectCd);
      returnData.setProjectBuildingDtls(projectBuildingDtls);

      // 案件要望明細情報
      List<ProjectRequestDtlDto> projectRequestDtls =
          b0030S05Repository.findByProjectId(projectInfo.getId());
      returnData.setProjectRequestDtls(projectRequestDtls);

      // 先行作業明細を取得
      List<TProjectPreWorkDtl> projectPreWorkDtls =
          b0030S06Repository.findByProjectId(projectInfo.getId());

      if (!CollectionUtils.isEmpty(projectPreWorkDtls)) {
        List<ProjectPreWorkDtlDto> projectPreWorkDtlDtos =
            b0030Mapper.toDtoList(projectPreWorkDtls);
        // 先行作業明細を設定
        returnData.setProjectPreWorkDtls(projectPreWorkDtlDtos);
      }

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
  public ApiResult<B0030S03ReturnData> saveAnkenInfo(B0030Dto inDto) {
    try {
      // 案件情報
      ProjectDto projectInfo = inDto.getProjectInfo();
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
        TProject newProjectEntity = insertProjectInfo(projectInfo, projectCd);

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

        // 案件情報を更新
        TProject newProjectEntity = updateProjectInfo(projectInfo, tproject);

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
        saveTRoughEstHdr(projectCd, inDto);

        // 精積算情報データを作成する
        saveTDetailedEstHdr(projectCd, inDto);
      }

      // 受注状態:成約
      if (MasterData.ITEM_CLASS_CD_D0001_ITEM_CD_2.equals(projectInfo.getOrderStCd())) {
        // 物件情報を作成する
        TProjectSite projectSite = saveProjectSite(projectCd, inDto);

        // 戻り値を設定(物件ID)
        ret.setProjectSiteId(projectSite.getId());
        // 戻り値を設定(物件コード)
        ret.setProjectSiteCd(projectSite.getProjectSiteCd());

        // 現場情報を作成する
        saveTConstrSite(projectSite, inDto);
      }

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
   * @param projectDto 案件情報
   * @param dbProjectEntity DBの案件情報
   * @return 更新した案件情報
   */
  private TProject updateProjectInfo(ProjectDto projectDto, TProject dbProjectEntity) {
    // DBの案件情報を過去に更新
    dbProjectEntity.setNewestFlg(CommonConstants.NEWEST_FLAG_HIS);
    b0030S01Repository.save(dbProjectEntity);

    // 案件エンティティに変換
    TProject projectEntity = b0030Mapper.toProjectEntity(projectDto);
    // ID
    projectEntity.setId(null);
    // 最新フラグ;1：最新
    projectEntity.setNewestFlg(CommonConstants.NEWEST_FLAG_NEW);
    // 歴番 + 1
    projectEntity.setHisNo(String.format("%02d", Integer.parseInt(dbProjectEntity.getHisNo()) + 1));
    // 最新データとして登録
    return b0030S01Repository.save(projectEntity);
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
  private void saveTRoughEstHdr(String projectCd, B0030Dto inDto) {

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
        entity.setRoughEstYmd("N");
        // 案件コード
        entity.setProjectCd(projectCd);
        // 棟コード
        entity.setBuildingCd(projectBuildingDtl.getBuildingCd());
        // 概算合計金額
        entity.setRoughEstTotalAmt(BigDecimal.ZERO);
        // 概算部門ID
        entity.setRoughEstOrgId(0L);
        // 概算担当者コード
        entity.setRoughEstPicCd("N");
        // 担当事業所コード
        MOffice office = new MOffice();
        office.setOfficeCd("10");
        entity.setTantouOfficeCd(office);
        // 地域コード
        entity.setRegionCd("N");
        // 現場区分
        entity.setConstrSiteK("N");
        // 概算作成フラグ
        entity.setRoughEstCreateFlg(CommonConstants.ROUGH_EST_CREATE_FLG_UNCREATED);

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
  private void saveTDetailedEstHdr(String projectCd, B0030Dto inDto) {
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
        // 精積算日付
        entity.setDetailedEstYmd("N");
        // 精積算担当者コード
        entity.setDetailedEstPicCd("N");
        // 地域コード
        entity.setRegionCd("N");
        // 現場区分
        entity.setConstrSiteK("N");

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

    // 物件コード TODO 案件番号がK始まりの場合は手動
    entity.setProjectSiteCd(
        numberRuleService.getNextNumberByFieldId(FIELD_ID_PROJECT_SITE_CD, null, null));
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
    // 着工日
    entity.setConstrStartYmd("N");
    // 完工日
    entity.setConstrCompYmd("N");
    // 担当事業所コード
    MOffice office = new MOffice();
    office.setOfficeCd("10");
    entity.setIcOfficeCd(office);
    // 工事部門適用開始日
    entity.setConstrDeptStartDt("N");
    // 工事部門ID
    entity.setConstrOrgId(0L);
    // 工事管理職コード
    entity.setConstMgrCd("N");
    // 専任技術者コード
    entity.setFtEngineerCd("N");
    // 施工担当者コード
    entity.setConstrPicCd("N");
    // 物件郵便番号
    entity.setProjectSitePostNo(projectInfo.getPostNo());
    // 物件住所１
    entity.setProjectSiteAddr1(projectInfo.getConstrSiteAddr1());
    // 物件住所２
    entity.setProjectSiteAddr2(projectInfo.getConstrSiteAddr2());
    // 現場所長コード
    entity.setConstrSiteDirectorCd("N");
    // 所長代理コード
    entity.setDeputyDirectorCd("N");
    // 副所長コード
    entity.setViceDirectorCd("N");
    // 現場主任コード
    entity.setConstrSiteSupvCd("N");
    // 現場担当者コード１
    entity.setConstrSitePicCd1("N");
    // 現場担当者コード２
    entity.setConstrSitePicCd2("N");
    // 現場担当者コード３
    entity.setConstrSitePicCd3("N");
    // 現場担当者コード４
    entity.setConstrSitePicCd4("N");
    // 現場担当者コード５
    entity.setConstrSitePicCd5("N");
    // 設計業者名
    entity.setDesignVenderNm("N");
    // 設計担当者
    entity.setDesignPicNm("N");
    // 用途地域区分
    entity.setZoningK("N");
    // 防火地域区分
    entity.setFireAreaK("N");

    return b0030S02Repository.save(entity);
  }

  /**
   * 現場情報を作成する
   *
   * @param projectSite 物件情報
   * @param inDto 案件登録保存パラメータ
   */
  private void saveTConstrSite(TProjectSite projectSite, B0030Dto inDto) {
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
        // 現場コード
        entity.setConstrSiteCd(
            projectSite.getProjectSiteCd() + "000" + projectBuildingDtl.getBuildingCd());
        // 現場名
        entity.setConstrSiteNm(projectBuildingDtl.getBuildingWorkNm());
        // 現場カナ名
        entity.setConstrSiteKnNm("N");
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
        // 構造区分 TODO
        entity.setStructureK("N");
        // 現場着手日
        entity.setConstrSiteStartYmd("N");
        // 現場引渡日
        entity.setConstrSiteDeliveryYmd("N");
        // 税抜請負金額
        entity.setExclTaxCoAmt(BigDecimal.ZERO);
        // 税込請負金額
        entity.setInclTaxCoAmt(BigDecimal.ZERO);
        // 請負消費税金額
        entity.setCoSalesTaxAmt(BigDecimal.ZERO);
        // 粗利益額
        entity.setGrossProfitAmt("N");
        // 粗利益率
        entity.setGrossProfitRate("N");
        // メモ
        entity.setMemo("N");

        entityList.add(entity);
      }
      // 現場情報を作成する
      b0030S09Repository.saveAll(entityList);
    }
  }
}
