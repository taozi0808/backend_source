package com.daitoj.tkms.modules.apir0045.service;

import com.daitoj.tkms.domain.MCert;
import com.daitoj.tkms.domain.MEmp;
import com.daitoj.tkms.domain.MEmpCert;
import com.daitoj.tkms.domain.MEmpOrg;
import com.daitoj.tkms.domain.MEmpPhoto;
import com.daitoj.tkms.domain.MEmpTransferDtl;
import com.daitoj.tkms.domain.MEmpTransferHdr;
import com.daitoj.tkms.domain.MItemListSetting;
import com.daitoj.tkms.domain.MLogin;
import com.daitoj.tkms.domain.MOrg;
import com.daitoj.tkms.domain.MOrgRev;
import com.daitoj.tkms.domain.MPosition;
import com.daitoj.tkms.modules.apir0045.repository.R0045Repository;
import com.daitoj.tkms.modules.apir0045.repository.mapper.R0045Mapper;
import com.daitoj.tkms.modules.apir0045.service.dto.EmpCertDto;
import com.daitoj.tkms.modules.apir0045.service.dto.EmpDto;
import com.daitoj.tkms.modules.apir0045.service.dto.EmpOrgDto;
import com.daitoj.tkms.modules.apir0045.service.dto.EmpPhotoDto;
import com.daitoj.tkms.modules.apir0045.service.dto.EmpResultDto;
import com.daitoj.tkms.modules.apir0045.service.dto.LoginDto;
import com.daitoj.tkms.modules.apir0045.service.dto.OrgDto;
import com.daitoj.tkms.modules.apir0045.service.dto.R0045Dto;
import com.daitoj.tkms.modules.apir0045.service.dto.R0045PrintDto;
import com.daitoj.tkms.modules.apir0045.service.dto.R0045S01ReturnData;
import com.daitoj.tkms.modules.apir0045.service.dto.R0045S02ReturnData;
import com.daitoj.tkms.modules.apir0045.service.dto.R0045S03ReturnData;
import com.daitoj.tkms.modules.apir0045.service.dto.R0045S04ReturnData;
import com.daitoj.tkms.modules.common.constants.CommonConstants;
import com.daitoj.tkms.modules.common.constants.KeyConstants;
import com.daitoj.tkms.modules.common.constants.MasterData;
import com.daitoj.tkms.modules.common.constants.Message;
import com.daitoj.tkms.modules.common.constants.TemplateName;
import com.daitoj.tkms.modules.common.repository.MCertRepository;
import com.daitoj.tkms.modules.common.repository.MEmpCertRepository;
import com.daitoj.tkms.modules.common.repository.MEmpOrgRepository;
import com.daitoj.tkms.modules.common.repository.MEmpPhotoRepository;
import com.daitoj.tkms.modules.common.repository.MEmpRepository;
import com.daitoj.tkms.modules.common.repository.MEmpTransferDtlRepository;
import com.daitoj.tkms.modules.common.repository.MEmpTransferHdrRepository;
import com.daitoj.tkms.modules.common.repository.MItemListSettingRepository;
import com.daitoj.tkms.modules.common.repository.MLoginRepository;
import com.daitoj.tkms.modules.common.repository.MOrgRepository;
import com.daitoj.tkms.modules.common.repository.MOrgRevRepository;
import com.daitoj.tkms.modules.common.repository.MPositionRepository;
import com.daitoj.tkms.modules.common.repository.mapper.MCertMapper;
import com.daitoj.tkms.modules.common.repository.mapper.MItemSettingMapper;
import com.daitoj.tkms.modules.common.repository.mapper.MOrgMapper;
import com.daitoj.tkms.modules.common.repository.mapper.MOrgRevMapper;
import com.daitoj.tkms.modules.common.repository.mapper.MPositionMapper;
import com.daitoj.tkms.modules.common.service.CloudStorageService;
import com.daitoj.tkms.modules.common.service.ConflictException;
import com.daitoj.tkms.modules.common.service.CustomMailService;
import com.daitoj.tkms.modules.common.service.ItemListSettingService;
import com.daitoj.tkms.modules.common.service.NumberService;
import com.daitoj.tkms.modules.common.service.ReportService;
import com.daitoj.tkms.modules.common.service.SystemException;
import com.daitoj.tkms.modules.common.service.dto.ApiResult;
import com.daitoj.tkms.modules.common.service.dto.ErrorInfo;
import com.daitoj.tkms.modules.common.service.dto.MItemListSettingDto;
import com.daitoj.tkms.modules.common.service.dto.MitemSettingDto;
import com.daitoj.tkms.modules.common.utils.DateUtils;
import com.daitoj.tkms.modules.common.utils.PasswordUtils;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.mail.MessagingException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import net.sf.jasperreports.engine.JREmptyDataSource;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Sort;
import org.springframework.mail.MailSendException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

/** 社員登録ビジネスロジック */
@Service
@Transactional(rollbackFor = Throwable.class)
public class R0045Service {
  private static final Logger LOG = LoggerFactory.getLogger(R0045Service.class);

  /** 社員情報リポジトリ */
  private final R0045Repository r0045Repository;

  /** ログイン情報リポジトリ */
  private final MLoginRepository loginRepository;

  /** マスタデータリポジトリ */
  private final MItemListSettingRepository mitemListSettingRepository;

  /** 従業員情報リポジトリ */
  private final MEmpRepository mempRepository;

  /** 従業員・組織・対照情報リポジトリ */
  private final MEmpOrgRepository mempOrgRepository;

  /** 従業員資格情報リポジトリ */
  private final MEmpCertRepository mempCertRepository;

  /** 従業員顔写真情報リポジトリ */
  private final MEmpPhotoRepository mempPhotoRepository;

  /** 役職情報リポジトリ */
  private final MPositionRepository mpositionRepository;

  /** 組織情報のリポジトリ */
  private final MOrgRepository morgRepository;

  /** 組織改定情報のリポジトリ */
  private final MOrgRevRepository morgRevRepository;

  /** 従業員異動ヘッダ情報のリポジトリ */
  private final MEmpTransferHdrRepository mempTransferHdrRepository;

  /** 従業員異動ヘッダ明細情報のリポジトリ */
  private final MEmpTransferDtlRepository mempTransferDtlRepository;

  /** レポートサービス */
  private final ReportService reportService;

  /** 社員登録情報リポジトリ */
  private final R0045Mapper r0045Mapper;

  /** マスタデータサービス */
  private final ItemListSettingService itemListSettingService;

  /** メールサービス */
  private final CustomMailService customMailService;

  /** ストレージサービス */
  private final CloudStorageService cloudStorageService;

  /** メッセージ */
  private final MessageSource messageSource;

  /** fasterxml.jackson@ObjectMapper */
  private final ObjectMapper objectMapper;

  /** マスタデータのDTOマッパー */
  private final MItemSettingMapper itemSettingMapper;

  /** 役職のDTOマッパー */
  private final MPositionMapper mpositionMapper;

  /** 資格のDTOマッパー */
  private final MCertMapper mcertMapper;

  /** 組織情報のリポジトリ */
  private final MOrgMapper morgMapper;

  /** 組織改定情報のリポジトリ */
  private final MOrgRevMapper morgRevMapper;

  private final MCertRepository mcertRepository;

  /** 採番サービス */
  private final NumberService numberRuleService;

  /** 採番項目ID(社員コード) */
  private static final String FIELD_ID_EMP_CD = "EMP_CD";

  /** 社員コード先頭文字 */
  private static final String PREFIX_EMP_CD = "j";

  /** 社員コード接尾辞 */
  private static final String SUFFIXEMP_CD = "000001";

  /** 帳票日付フォーマット */
  private static final String PDF_DATE_FORMAT = "yyyy年MM年dd日HH:mm:ss";

  /** レポートファイル名 */
  public static final String REPORT_FILE_NAME = "WebR0045.jasper";

  /** コンストラクタ */
  public R0045Service(
      R0045Repository r0045Repository,
      MLoginRepository loginRepository,
      MItemListSettingRepository mitemListSettingRepository,
      MEmpRepository mempRepository,
      MEmpOrgRepository mempOrgRepository,
      MEmpCertRepository mempCertRepository,
      MEmpPhotoRepository mempPhotoRepository,
      MPositionRepository mpositionRepository,
      MOrgRepository morgRepository,
      MOrgRevRepository morgRevRepository,
      MCertRepository mcertRepository,
      MEmpTransferHdrRepository mempTransferHdrRepository,
      MEmpTransferDtlRepository mempTransferDtlRepository,
      R0045Mapper r0045Mapper,
      CustomMailService customMailService,
      ItemListSettingService itemListSettingService,
      ObjectMapper objectMapper,
      ReportService reportService,
      MessageSource messageSource,
      NumberService numberRuleService,
      CloudStorageService cloudStorageService,
      MItemSettingMapper itemSettingMapper,
      MPositionMapper mpositionMapper,
      MCertMapper mcertMapper,
      MOrgRevMapper morgRevMapper,
      MOrgMapper morgMapper) {
    this.r0045Repository = r0045Repository;
    this.loginRepository = loginRepository;
    this.mitemListSettingRepository = mitemListSettingRepository;
    this.mempRepository = mempRepository;
    this.mempOrgRepository = mempOrgRepository;
    this.mempCertRepository = mempCertRepository;
    this.mempPhotoRepository = mempPhotoRepository;
    this.mpositionRepository = mpositionRepository;
    this.morgRepository = morgRepository;
    this.morgRevRepository = morgRevRepository;
    this.mcertRepository = mcertRepository;
    this.mempTransferHdrRepository = mempTransferHdrRepository;
    this.mempTransferDtlRepository = mempTransferDtlRepository;
    this.r0045Mapper = r0045Mapper;
    this.customMailService = customMailService;
    this.itemListSettingService = itemListSettingService;
    this.objectMapper = objectMapper;
    this.reportService = reportService;
    this.messageSource = messageSource;
    this.numberRuleService = numberRuleService;
    this.cloudStorageService = cloudStorageService;
    this.itemSettingMapper = itemSettingMapper;
    this.mpositionMapper = mpositionMapper;
    this.mcertMapper = mcertMapper;
    this.morgMapper = morgMapper;
    this.morgRevMapper = morgRevMapper;
  }

  /**
   * 選択項目取得
   *
   * @return 選択項目
   */
  public ApiResult<R0045S01ReturnData> getSentakuKoumoku() {
    try {
      // マスタデータリスト
      ApiResult<List<MItemListSettingDto>> result =
          itemListSettingService.getDataList(
              new String[] {MasterData.ITEM_CLASS_CD_D0013, MasterData.ITEM_CLASS_CD_D0014});

      // 結果情報
      R0045S01ReturnData ret = new R0045S01ReturnData();
      // 選択項目情報
      ret.setSettingList(result.getData());

      // 役職リストを取得
      List<MPosition> positionList =
          mpositionRepository.findAll(Sort.by(Sort.Order.asc("positionCd")));
      // 役職リストを設定
      ret.setPositionList(mpositionMapper.toPositionSearchDtoList(positionList));

      // 組織リストを取得
      List<MOrg> orgList = morgRepository.findBusyos(DateUtils.formatNow(DateUtils.DATE_FORMAT));

      if (!CollectionUtils.isEmpty(orgList)) {
        // 部署リスト
        List<MOrg> busyoList =
            orgList.stream()
                .filter(item -> Integer.compare(CommonConstants.ORG_LVL_BU, item.getLvl()) == 0)
                .toList();
        ret.setBusyoList(morgMapper.toOrgSearchDtoList(busyoList));

        // 部署課リスト
        List<MOrg> busyoKaList =
            orgList.stream()
                .filter(item -> Integer.compare(CommonConstants.ORG_LVL_KA, item.getLvl()) == 0)
                .toList();
        ret.setBusyokaList(morgMapper.toOrgSearchDtoList(busyoKaList));
      }

      // 異動組織リストを取得
      List<MOrg> transOrgList =
          morgRepository.findTransferBusyos(DateUtils.formatNow(DateUtils.DATE_FORMAT));

      if (!CollectionUtils.isEmpty(transOrgList)) {
        // 異動部署リスト
        List<MOrg> busyoList =
            transOrgList.stream()
                .filter(item -> Integer.compare(CommonConstants.ORG_LVL_BU, item.getLvl()) == 0)
                .toList();
        ret.setTransferBusyoList(morgMapper.toOrgSearchDtoList(busyoList));

        // 異動部署課リスト
        List<MOrg> busyoKaList =
            transOrgList.stream()
                .filter(item -> Integer.compare(CommonConstants.ORG_LVL_KA, item.getLvl()) == 0)
                .toList();
        ret.setTransferBusyokaList(morgMapper.toOrgSearchDtoList(busyoKaList));
      }

      // 資格情報を取得
      List<MCert> certList = mcertRepository.findByOrderByDisplayOrder();
      // 資格リストを設定
      ret.setCertList(mcertMapper.toCertSearchList(certList));

      // 適用開始日リストを取得
      List<MOrgRev> orgRevList =
          morgRevRepository.findByEffectiveStartDtGreaterThanOrderByEffectiveStartDt(
              DateUtils.formatNow(DateUtils.DATE_FORMAT));
      // 適用開始日リストを設定
      ret.setStartDtList(morgRevMapper.toOrgRevSearchDtoList(orgRevList));

      return ApiResult.success(ret);
    } catch (Exception ex) {
      LOG.error(ex.toString(), ex);

      throw new SystemException(
          messageSource.getMessage(Message.MSGID_S0000, null, LocaleContextHolder.getLocale()));
    }
  }

  /**
   * 社員情報を取得
   *
   * @param empCd 社員コード
   * @return 社員情報
   */
  public ApiResult<R0045S02ReturnData> getsyainkanritourokuInfo(String empCd) {
    try {
      // システム日付
      String sysDate = DateUtils.formatNow(DateUtils.DATE_FORMAT);

      // 社員情報を取得
      MEmp empEntity = r0045Repository.findByEmpCd(empCd).orElseThrow(ConflictException::new);
      EmpResultDto empDto = r0045Mapper.toEmpResultDto(empEntity);

      if (empEntity.getEmpJobTypeCd() != null) {
        // 職種マスタデータを取得
        mitemListSettingRepository
            .findById_ItemClassCdAndId_ItemCdAndId_EffectiveStartDtLessThanEqual(
                MasterData.ITEM_CLASS_CD_D0014, empEntity.getEmpJobTypeCd(), sysDate)
            .ifPresent(item -> empDto.setEmpJobTypeName(item.getItemValue()));
      }

      if (empEntity.getSex() != null) {
        // 性別マスタデータを取得
        mitemListSettingRepository
            .findById_ItemClassCdAndId_ItemCdAndId_EffectiveStartDtLessThanEqual(
                MasterData.ITEM_CLASS_CD_D0013, empEntity.getSex(), sysDate)
            .ifPresent(item -> empDto.setSexName(item.getItemValue()));
      }

      // 従業員の異動情報を取得
      List<MEmpTransferHdr> empTransferHdrList =
          mempTransferHdrRepository.findByEmp_IdOrderByEffectiveStartDt(empEntity.getId());

      if (!CollectionUtils.isEmpty(empTransferHdrList)) {
        // 直近従業員の異動情報
        MEmpTransferHdr transferHdr = null;

        // 適用開始日 > システム日時のデータ
        List<MEmpTransferHdr> aftList =
            empTransferHdrList.stream()
                .filter(item -> item.getEffectiveStartDt().compareTo(sysDate) > 0)
                .toList();

        if (!CollectionUtils.isEmpty(aftList)) {
          transferHdr = aftList.get(0);
        } else {
          // 適用開始日 <= システム日時のデータ
          List<MEmpTransferHdr> befList =
              empTransferHdrList.stream()
                  .filter(item -> item.getEffectiveStartDt().compareTo(sysDate) <= 0)
                  .toList();

          transferHdr = befList.get(befList.size() - 1);
        }

        // 適用開始日を設定
        empDto.setEffectiveStartDt(transferHdr.getEffectiveStartDt());
        // 部署異動役職コードを設定
        empDto.setTransgerPositionCd(transferHdr.getPositionCd());

        // 従業員異動明細
        List<MEmpTransferDtl> transferDtls =
            mempTransferDtlRepository.findByEmpTransferHid_Id(transferHdr.getId());

        if (!CollectionUtils.isEmpty(transferDtls)) {
          // 部署異動情報
          List<EmpOrgDto> transferEmpOrgList = new ArrayList<>();

          for (MEmpTransferDtl dtl : transferDtls) {
            EmpOrgDto empOrgDto = new EmpOrgDto();
            // 連番
            empOrgDto.setSeqNo(dtl.getSeqNo());

            OrgDto orgDto = new OrgDto();
            // 組織ID
            orgDto.setId(dtl.getOrg().getId());
            // 組織コード
            orgDto.setOrgCd(dtl.getOrg().getOrgCd());
            // 組織名
            orgDto.setOrgNm(dtl.getOrg().getOrgNm());
            // 組織情報
            empOrgDto.setOrg(orgDto);

            // 組織区分
            empOrgDto.setOrgK(dtl.getOrgK());

            transferEmpOrgList.add(empOrgDto);
          }
          // 部署異動情報を設定
          empDto.setTransferEmpOrgList(transferEmpOrgList);
        }
      }

      // 結果情報
      R0045S02ReturnData result = new R0045S02ReturnData();
      // 社員情報
      result.setEmp(empDto);

      return ApiResult.success(result);
    } catch (ConflictException ex) {
      throw ex;
    } catch (Exception ex) {
      LOG.error(ex.toString(), ex);

      throw new SystemException(
          messageSource.getMessage(Message.MSGID_S0000, null, LocaleContextHolder.getLocale()));
    }
  }

  /**
   * 社員管理登録情報保存
   *
   * @param inDto 社員管理登録情報保存パラメータ
   * @return 社員コード
   */
  public ApiResult<R0045S03ReturnData> savesyainkanritourokuInfo(
      R0045Dto inDto, MultipartFile[] files) throws MessagingException {
    try {

      if (files != null && files.length > 0) {
        // 従業員顔写真アップロード
        List<UUID> uuids = cloudStorageService.upload(files);

        // 従業員顔写真情報を設定
        for (int i = 0; i < uuids.size(); i++) {
          if (!CollectionUtils.isEmpty(inDto.getEmp().getEmpPhotoList())) {
            // ファイルのインデックス
            String fileIndex = String.valueOf(i);
            // ファイルのDto
            Optional<EmpPhotoDto> photoDto =
                inDto.getEmp().getEmpPhotoList().stream()
                    .filter(item -> fileIndex.equals(item.getFileIndex()))
                    .findAny();
            if (photoDto.isPresent()) {
              // S3パスを設定
              photoDto.get().setPhotoFileId(uuids.get(i));
            }
          }
        }
      }

      // 社員コード
      String empCd = null;
      // 社員情報
      EmpDto empDto = inDto.getEmp();

      // 処理区分が新規
      if (CommonConstants.SHORIKUBUN_SINNKI.equals(inDto.getShorikubun())) {
        // 社員コードがある
        if (StringUtils.isNotBlank(empDto.getEmpCd())) {
          // 社員情報
          Optional<MEmp> memp = mempRepository.findByEmpCd(empDto.getEmpCd());

          // 同じ社員コードが存在する場合
          if (memp.isPresent()) {
            String msg =
                messageSource.getMessage(
                    Message.MSGID_U00015, null, LocaleContextHolder.getLocale());

            LOG.info(msg);

            // 結果情報
            return ApiResult.error(Message.MSGID_U00015, msg);
          }
          // 登録社員コード
          empCd = empDto.getEmpCd();
        } else {
          List<String> empCdList = mempRepository.findAll().stream().map(MEmp::getEmpCd).toList();
          // 社員コードを採番
          for (int i = 0; i < 200; i++) {
            empCd = numberRuleService.getNextNumberByFieldId(FIELD_ID_EMP_CD, null, null);
            if (!empCdList.contains(empCd)) {
              break;
            }
            if (i == 199) {
              throw new SystemException(
                 messageSource.getMessage(Message.MSGID_S0000, null, LocaleContextHolder.getLocale()));
            }
          }
        }

        // ログインIDが設定されない場合
        if (empDto.getLogin() == null || StringUtils.isBlank(empDto.getLogin().getLoginId())) {
          LoginDto loginDto = new LoginDto();
          empDto.setLogin(loginDto);

          // ログインIDを設定:j + 社員コード + 000001
          loginDto.setLoginId(PREFIX_EMP_CD + empCd + SUFFIXEMP_CD);
        }

        // パスワードを生成
        String passwordString = PasswordUtils.createPassword();
        // 社員情報を登録
        MEmp newEmpEntity = insertEmpInfoWithLogin(empDto, empCd, passwordString);

        // m_emp以外のデータを保存
        saveOtherInfo(newEmpEntity.getId(), empDto, newEmpEntity);

        // メールの変数
        Map<String, Object> varMap = new HashMap<>();

        // 社員名
        varMap.put(KeyConstants.R0045_VAR_EMP_NAME, empDto.getEmpNm());
        // ログインID
        varMap.put(KeyConstants.R0045_VAR_LOGIN_ID, empDto.getLogin().getLoginId());
        // パスワード
        varMap.put(KeyConstants.R0045_VAR_PASSWORD, passwordString);

        // メールを送信する
        Long mailResult =
            customMailService.sendMail(
                TemplateName.R0045_INS_TEMPLATE,
                Message.R0045_INS_MAIL_TITLE,
                empDto.getMailAddress(),
                null,
                null,
                varMap,
                null);

        if (mailResult == -1L) {
          throw new SystemException(
              messageSource.getMessage(
                  Message.MSGID_A00007, null, LocaleContextHolder.getLocale()));
        }

        // 戻り値
        R0045S03ReturnData ret = new R0045S03ReturnData();
        // 社員ID
        ret.setEmpId(newEmpEntity.getId());
        // 社員コード
        ret.setEmpCd(newEmpEntity.getEmpCd());

        return ApiResult.success(ret);
        // 編集
      } else {
        // 最新社員情報を取得
        MEmp dbEmpEntity =
            mempRepository.findById(empDto.getId()).orElseThrow(ConflictException::new);

        // 排他チェック
        if ((empDto.getUpdTs() == null && dbEmpEntity.getUpdTs() != null)
            || (empDto.getUpdTs() != null && !empDto.getUpdTs().equals(dbEmpEntity.getUpdTs()))) {
          throw new ConflictException();
        }

        // 社員情報を更新
        MEmp newEmpEntity = updateEmpInfo(empDto, dbEmpEntity);

        // m_emp以外のデータを保存
        saveOtherInfo(newEmpEntity.getId(), empDto, newEmpEntity);
      }

      return ApiResult.success();
    } catch (MessagingException | MailSendException e) {
      // メッセージ：メール送信に失敗しました。
      LOG.error(e.toString(), e);

      throw e;
    } catch (ConflictException ex) {
      throw ex;
    } catch (Exception ex) {
      LOG.error(ex.toString(), ex);

      throw new SystemException(
          messageSource.getMessage(Message.MSGID_S0000, null, LocaleContextHolder.getLocale()));
    }
  }

  /**
   * パスワード通知
   *
   * @param loginId ログインID社員コード
   * @param shimei 氏名
   * @param mailAddress メールアドレス
   * @return エラー情報
   */
  public ApiResult<?> syainkanritourokupasswordalert(
      String loginId, String shimei, String mailAddress) throws MessagingException {
    try {
      // パスワードを生成
      String passwordString = PasswordUtils.createPassword();

      // パスワード暗号化処理
      BCryptPasswordEncoder bcryptPasswordEncoder = new BCryptPasswordEncoder();
      String encodePassword = bcryptPasswordEncoder.encode(passwordString);

      // ログイン情報を取得する
      Optional<MLogin> loginInfo = loginRepository.findById(loginId);

      if (loginInfo.isEmpty()) {
        // 退職者は再通知することが出来ません。
        String msg =
            messageSource.getMessage(Message.MSGID_U00018, null, LocaleContextHolder.getLocale());

        LOG.info(msg);

        // 結果情報
        return ApiResult.error(Message.MSGID_U00018, msg);
      }

      // パスワードを設定
      loginInfo.get().setPassword(encodePassword);
      // ログイン情報の更新処理
      loginRepository.save(loginInfo.get());

      // メール変数
      Map<String, Object> varMap = new HashMap<>();
      // 社員名
      varMap.put(KeyConstants.R0045_VAR_EMP_NAME, shimei);
      // パスワード
      varMap.put(KeyConstants.R0045_VAR_PASSWORD, passwordString);

      // メールを送信する
      Long mailResult =
          customMailService.sendMail(
              TemplateName.R0045_UPD_TEMPLATE,
              Message.R0045_UPD_MAIL_TITLE,
              mailAddress,
              null,
              null,
              varMap,
              null);

      if (mailResult == -1L) {
        throw new SystemException(
            messageSource.getMessage(Message.MSGID_A00007, null, LocaleContextHolder.getLocale()));
      }

      // パスワード再通知致しました。
      String msg =
          messageSource.getMessage(Message.MSGID_U00020, null, LocaleContextHolder.getLocale());

      ErrorInfo msgInfo = new ErrorInfo();
      msgInfo.setCode(Message.MSGID_U00020);
      msgInfo.setMessage(msg);

      return ApiResult.success(msgInfo);

    } catch (MessagingException | MailSendException e) {
      // メッセージ：メール送信に失敗しました。
      LOG.error(e.toString(), e);
      throw e;
    } catch (Exception ex) {
      LOG.error(ex.toString(), ex);

      throw new SystemException(
          messageSource.getMessage(Message.MSGID_S0000, null, LocaleContextHolder.getLocale()));
    }
  }

  /**
   * 社員情報を削除
   *
   * @param empCd 社員コード
   */
  public ApiResult<?> deletesyainkanritourokuInfo(String empCd) {
    try {
      // 最新社員情報を取得
      MEmp empEntity = r0045Repository.findByEmpCd(empCd).orElseThrow(ConflictException::new);

      // 従業員・組織・対照情報を削除
      if (!CollectionUtils.isEmpty(empEntity.getEmpOrgList())) {
        // "1"（削除済）
        empEntity
            .getEmpOrgList()
            .forEach(entity -> entity.setDelFlg(CommonConstants.DELETE_FLAG_DELETE));
      }

      // 従業員資格情報を削除
      if (!CollectionUtils.isEmpty(empEntity.getEmpCertList())) {
        // "1"（削除済）
        empEntity
            .getEmpCertList()
            .forEach(entity -> entity.setDelFlg(CommonConstants.DELETE_FLAG_DELETE));
      }

      // 従業員顔写真を削除
      if (!CollectionUtils.isEmpty(empEntity.getEmpPhotoList())) {
        // "1"（削除済）
        empEntity
            .getEmpPhotoList()
            .forEach(entity -> entity.setDelFlg(CommonConstants.DELETE_FLAG_DELETE));
      }

      // 従業員の異動情報を取得
      List<MEmpTransferHdr> empTransferHdrList =
          mempTransferHdrRepository.findByEmp_IdOrderByEffectiveStartDt(empEntity.getId());
      if (!CollectionUtils.isEmpty(empTransferHdrList)) {

        for (MEmpTransferHdr hdr : empTransferHdrList) {
          // 従業員異動明細
          List<MEmpTransferDtl> transferDtls =
              mempTransferDtlRepository.findByEmpTransferHid_Id(hdr.getId());

          if (!CollectionUtils.isEmpty(transferDtls)) {
            // "1"（削除済）
            transferDtls.forEach(entity -> entity.setDelFlg(CommonConstants.DELETE_FLAG_DELETE));
          }
        }

        // 従業員の異動情報を削除
        empTransferHdrList.forEach(entity -> entity.setDelFlg(CommonConstants.DELETE_FLAG_DELETE));
      }

      // 社員情報を削除
      empEntity.setDelFlg(CommonConstants.DELETE_FLAG_DELETE);

      // ログイン情報を取得する
      Optional<MLogin> loginInfo = loginRepository.findById(empEntity.getLogin().getLoginId());
      if (loginInfo.isPresent()) {

        // 退職日付が入れられた場合
        if (StringUtils.isNotBlank(empEntity.getTerminationYmd())) {
          // 利用終了日付を設定
          loginInfo.get().setEndYmd(empEntity.getTerminationYmd());
        }

        // "1"（削除済）
        loginInfo.get().setDelFlg(CommonConstants.DELETE_FLAG_DELETE);
      }

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
   * 従業員の異動情報を取得
   *
   * @param empId 従業員ID
   * @param effectiveStartDt 適用開始日付
   * @return 従業員の異動情報
   */
  public ApiResult<R0045S04ReturnData> getEmpTransferHdr(Long empId, String effectiveStartDt) {
    try {
      // 戻り値
      R0045S04ReturnData returnData = new R0045S04ReturnData();

      // 従業員の異動情報を設定
      mempTransferHdrRepository
          .findByEmp_IdAndEffectiveStartDt(empId, effectiveStartDt)
          .ifPresent(
              empTransferHdr ->
                  returnData.setEmpTransferHdr(r0045Mapper.toEmpTransferHdrDto(empTransferHdr)));

      return ApiResult.success(returnData);
    } catch (Exception ex) {
      LOG.error(ex.toString(), ex);

      throw new SystemException(
          messageSource.getMessage(Message.MSGID_S0000, null, LocaleContextHolder.getLocale()));
    }
  }

  /**
   * ログイン情報を登録
   *
   * @param empDto 社員情報
   * @param passwordString パスワード
   * @return ログイン情報
   */
  private MLogin insertLoginInfo(EmpDto empDto, String passwordString) {
    // パスワード暗号化処理
    BCryptPasswordEncoder bcryptPasswordEncoder = new BCryptPasswordEncoder();
    String encodePassword = bcryptPasswordEncoder.encode(passwordString);

    // ログイン情報
    MLogin loginInfo = new MLogin();
    // ログインID
    loginInfo.setLoginId(empDto.getLogin().getLoginId());
    // パスワード
    loginInfo.setPassword(encodePassword);
    // アカウント区分:社員
    loginInfo.setAccountK(CommonConstants.ACCOUNT_K_EMP);
    // （未取消）
    loginInfo.setDelFlg(CommonConstants.DELETE_FLAG_VALID);
    // 利用開始日付
    loginInfo.setStartYmd(DateUtils.formatNow(DateUtils.DATE_FORMAT));
    // 利用終了日付
    loginInfo.setEndYmd(null);

    // ログイン情報を登録
    return loginRepository.saveAndFlush(loginInfo);
  }

  /**
   * 社員情報を登録
   *
   * @param empDto 社員情報
   * @param empCd 社員コード
   * @param passwordString パスワード
   * @return 登録した社員情報
   */
  private MEmp insertEmpInfoWithLogin(EmpDto empDto, String empCd, String passwordString) {
    // 社員エンティティに変換
    MEmp empEntity = r0045Mapper.toEmpEntity(empDto);
    // 社員コード
    empEntity.setEmpCd(empCd);
    // ログイン情報
    empEntity.setLogin(insertLoginInfo(empDto, passwordString));
    // 最新フラグ;1：最新
    empEntity.setNewestFlg(CommonConstants.NEWEST_FLAG_NEW);
    // 歴番;01から連番
    empEntity.setHisNo(CommonConstants.HIS_NO_START);
    // 社員エンティティを登録
    return mempRepository.saveAndFlush(empEntity);
  }

  /**
   * 社員情報を更新
   *
   * @param empDto 社員情報
   * @param dbEmpEntity DBの社員情報
   * @return 更新した社員情報
   */
  private MEmp updateEmpInfo(EmpDto empDto, MEmp dbEmpEntity) {
    // DBの社員情報を過去に更新
    dbEmpEntity.setNewestFlg(CommonConstants.NEWEST_FLAG_HIS);
    mempRepository.save(dbEmpEntity);

    // 社員エンティティに変換
    MEmp empEntity = r0045Mapper.toEmpEntity(empDto);
    // ID
    empEntity.setId(null);
    // 最新フラグ;1：最新
    empEntity.setNewestFlg(CommonConstants.NEWEST_FLAG_NEW);
    // 歴番+1
    empEntity.setHisNo(dbEmpEntity.getHisNo() + 1);
    // 利用時間制御フラグ
    if (dbEmpEntity.getUseTimeControlFlg() != null) {
      empEntity.setUseTimeControlFlg(dbEmpEntity.getUseTimeControlFlg());
    }

    // 退職日付が入れられた場合
    if (StringUtils.isNotBlank(empEntity.getTerminationYmd())) {
      // ログイン情報を取得する
      Optional<MLogin> loginInfo = loginRepository.findById(empDto.getLogin().getLoginId());

      // 利用終了日付を設定
      loginInfo.ifPresent(mlogin -> mlogin.setEndYmd(empDto.getTerminationYmd()));
    }

    // 最新データとして登録
    return mempRepository.save(empEntity);
  }

  /**
   * m_emp以外のデータを保存
   *
   * @param empId 社員ID
   * @param empDto 社員情報
   */
  private void saveOtherInfo(Long empId, EmpDto empDto, MEmp emp) {
    // 従業員・組織・対照情報
    List<EmpOrgDto> empOrgDtoList = empDto.getEmpOrgList();
    // 資格情報
    List<EmpCertDto> empCertList = empDto.getEmpCertList();
    // 従業員顔写真情報
    List<EmpPhotoDto> empPhotoList = empDto.getEmpPhotoList();

    if (!CollectionUtils.isEmpty(empOrgDtoList)) {
      // 従業員・組織・対照エンティティリスト
      List<MEmpOrg> mempOrgList = r0045Mapper.toEmpOrgEntityList(empOrgDtoList, empId);
      // 従業員・組織・対照情報を登録
      mempOrgRepository.saveAllAndFlush(mempOrgList);
    }

    if (!CollectionUtils.isEmpty(empCertList)) {
      // 従業員資格エンティティリスト
      List<MEmpCert> mempCertList = r0045Mapper.toEmpCertEntityList(empCertList, empId);
      // 従業員資格情報を登録
      mempCertRepository.saveAllAndFlush(mempCertList);
    }

    if (!CollectionUtils.isEmpty(empPhotoList)) {
      // 従業員顔写真エンティティリスト
      List<MEmpPhoto> mempPhotoList = r0045Mapper.toEmpPhotoEntityList(empPhotoList, empId);
      // 従業員顔写真を登録
      mempPhotoRepository.saveAllAndFlush(mempPhotoList);
    }

    // 部署異動情報
    List<EmpOrgDto> transferEmpOrgList = empDto.getTransferEmpOrgList();
    if (!CollectionUtils.isEmpty(transferEmpOrgList)) {
      // 適用開始日付
      String effectiveStartDt = empDto.getEffectiveStartDt();

      // 従業員異動ヘッダ情報
      MEmpTransferHdr transferHdr = new MEmpTransferHdr();
      // 従業員ID
      transferHdr.setEmp(emp);
      // 適用開始日
      transferHdr.setEffectiveStartDt(effectiveStartDt);
      // 役職コード
      transferHdr.setPositionCd(empDto.getTransgerPositionCd());
      // 従業員異動ヘッダ情報を登録
      MEmpTransferHdr newRransferHdr = mempTransferHdrRepository.save(transferHdr);

      List<MEmpTransferDtl> transferDtls = new ArrayList<>();

      // 部、課でそれぞれ１レコードづつ作成する
      for (EmpOrgDto dto : transferEmpOrgList) {
        MEmpTransferDtl transferDtl = new MEmpTransferDtl();

        // 従業員異動HID
        transferDtl.setEmpTransferHid(newRransferHdr);
        // 連番
        transferDtl.setSeqNo(dto.getSeqNo());
        // 組織ID
        MOrg org = new MOrg();
        org.setId(dto.getOrg().getId());
        transferDtl.setOrg(org);
        // 組織区分
        transferDtl.setOrgK(dto.getOrgK());

        transferDtls.add(transferDtl);
      }

      // 従業員異動明細情報を登録
      mempTransferDtlRepository.saveAll(transferDtls);
    }
  }

  /**
   * 印刷処理.
   *
   * @param empCd 社員コード
   * @return API結果
   */
  public ApiResult<?> exportReportToPdf(String empCd, LocalDateTime sysDate) {
    try {
      // 社員情報を取得
      MEmp empEntity = r0045Repository.findByEmpCd(empCd).orElseThrow(ConflictException::new);
      EmpResultDto empDto = r0045Mapper.toEmpResultDto(empEntity);
      R0045PrintDto printDto = new R0045PrintDto();

      List<MPosition> positionList =
        mpositionRepository.findAll(Sort.by(Sort.Order.asc("positionCd")));
      // 従業員の異動情報を取得
      List<MEmpTransferHdr> empTransferHdrList =
        mempTransferHdrRepository.findByEmp_IdOrderByEffectiveStartDt(empEntity.getId());

      if (!CollectionUtils.isEmpty(empTransferHdrList)) {
        // 直近従業員の異動情報
        MEmpTransferHdr transferHdr = null;

        // 適用開始日 > システム日時のデータ
        List<MEmpTransferHdr> aftList =
          empTransferHdrList.stream()
            .filter(item -> item.getEffectiveStartDt().compareTo(sysDate.format(DateTimeFormatter.ofPattern(PDF_DATE_FORMAT))) > 0)
            .toList();

        if (!CollectionUtils.isEmpty(aftList)) {
          transferHdr = aftList.get(0);
        } else {
          // 適用開始日 <= システム日時のデータ
          List<MEmpTransferHdr> befList =
            empTransferHdrList.stream()
              .filter(item -> item.getEffectiveStartDt().compareTo(sysDate.format(DateTimeFormatter.ofPattern(PDF_DATE_FORMAT))) <= 0)
              .toList();

          transferHdr = befList.get(befList.size() - 1);
        }

        // 適用開始日を設定
        printDto.setStartDate(transferHdr.getEffectiveStartDt());
        MEmpTransferHdr finalTransferHdr = transferHdr;
        String postionNm = positionList.stream()
          .filter(item-> finalTransferHdr.getPositionCd().equals(item.getPositionCd()))
          .map(item-> item.getPositionNm())
          .findFirst()
          .orElse("");
        // 部署異動役職名を設定
        printDto.setPositionNm2(postionNm);

        // 従業員異動明細
        List<MEmpTransferDtl> transferDtls =
          mempTransferDtlRepository.findByEmpTransferHid_Id(transferHdr.getId());

        if (!CollectionUtils.isEmpty(transferDtls)) {
          // 部署異動情報
          List<EmpOrgDto> transferEmpOrgList = new ArrayList<>();

          for (MEmpTransferDtl dtl : transferDtls) {
            EmpOrgDto empOrgDto = new EmpOrgDto();
            // 連番
            empOrgDto.setSeqNo(dtl.getSeqNo());

            OrgDto orgDto = new OrgDto();
            // 組織ID
            orgDto.setId(dtl.getOrg().getId());
            // 組織コード
            orgDto.setOrgCd(dtl.getOrg().getOrgCd());
            // 組織名
            orgDto.setOrgNm(dtl.getOrg().getOrgNm());
            // 組織情報
            empOrgDto.setOrg(orgDto);

            // 組織区分
            empOrgDto.setOrgK(dtl.getOrgK());

            transferEmpOrgList.add(empOrgDto);
          }
          // 部署異動情報を設定
          empDto.setTransferEmpOrgList(transferEmpOrgList);
        }
      }

      // 顔認証用写真を取得
      List<EmpPhotoDto> photoList =
          Optional.ofNullable(empDto.getEmpPhotoList()).orElse(Collections.emptyList());

      for (int i = 0; i < photoList.size(); i++) {
        EmpPhotoDto photo = photoList.get(i);
        String url = null;
        if (StringUtils.isNotEmpty(photo.getPhotoFileId().toString())) {
          url = cloudStorageService.createUrl(photo.getPhotoFileId().toString());
        }
        switch (i) {
          case 0 -> printDto.setPhotoUrl1(url);
          case 1 -> printDto.setPhotoUrl2(url);
          case 2 -> printDto.setPhotoUrl3(url);
        }
      }

      // マスタデータを取得
      List<MItemListSetting> sexItemList =
          mitemListSettingRepository
              .findById_ItemClassCdAndId_EffectiveStartDtLessThanEqualOrderByDisplayOrder(
                  MasterData.ITEM_CLASS_CD_D0013, DateUtils.formatNow(DateUtils.DATE_FORMAT));
      // 性別名を取得
      String sexName =
          itemSettingMapper.toDto(sexItemList).stream()
              .filter(item -> item.getItemCd().equals(empDto.getSex()))
              .map(MitemSettingDto::getItemValue)
              .findFirst()
              .orElse("");
      // 従業員職種名を取得
      List<MItemListSetting> empTypeItemList =
          mitemListSettingRepository
              .findById_ItemClassCdAndId_EffectiveStartDtLessThanEqualOrderByDisplayOrder(
                  MasterData.ITEM_CLASS_CD_D0014, DateUtils.formatNow(DateUtils.DATE_FORMAT));
      // マスタデータDtoに変換する
      String empJobEypeName =
          itemSettingMapper.toDto(empTypeItemList).stream()
              .filter(item -> item.getItemCd().equals(empDto.getEmpJobTypeCd()))
              .map(MitemSettingDto::getItemValue)
              .findFirst()
              .orElse("");
      // 利用PCのシステム日付
      printDto.setSysDate(sysDate.format(DateTimeFormatter.ofPattern(PDF_DATE_FORMAT)));
      // 従業員コード
      printDto.setEmpCd(empDto.getEmpCd());
      // 従業員氏名
      printDto.setEmpNm(empDto.getEmpNm());
      // 職種名(表示のみ)
      printDto.setEmpJobTypeName(empJobEypeName);
      // 生年月日
      printDto.setBirthDate(empDto.getBirthDate());
      // 性別
      printDto.setSexName(sexName);
      // 入社年月日
      printDto.setEmploymentYmd(empDto.getEmploymentYmd());
      // 退職年月日
      printDto.setTerminationYmd(empDto.getTerminationYmd());
      List<String> orgBulist = new ArrayList<>();
      List<String> orgKalist = new ArrayList<>();
      List<String> transFerBulist = new ArrayList<>();
      List<String> transFerKalist = new ArrayList<>();
      // 所属部署（部）
      if (empDto.getEmpOrgList() != null) {
        orgBulist =
            empDto.getEmpOrgList().stream()
                .filter(
                    item ->
                        "1".equals(item.getOrgK())
                            && item.getOrg() != null
                            && item.getOrg().getOrgNm() != null)
                .map(item -> item.getOrg().getOrgNm())
                .collect(Collectors.toList());
        // 所属部署（課）
        orgKalist =
            empDto.getEmpOrgList().stream()
                .filter(
                    item ->
                        "2".equals(item.getOrgK())
                            && item.getOrg() != null
                            && item.getOrg().getOrgNm() != null)
                .map(item -> item.getOrg().getOrgNm())
                .collect(Collectors.toList());
      }
      if (empDto.getTransferEmpOrgList() != null) {
        // 部署異動設定（部）
        transFerBulist =
            empDto.getTransferEmpOrgList().stream()
                .filter(
                    item ->
                        "1".equals(item.getOrgK())
                            && item.getOrg() != null
                            && item.getOrg().getOrgNm() != null)
                .map(item -> item.getOrg().getOrgNm())
                .collect(Collectors.toList());
        // 部署異動設定（部）
        transFerKalist =
            empDto.getTransferEmpOrgList().stream()
                .filter(
                    item ->
                        "2".equals(item.getOrgK())
                            && item.getOrg() != null
                            && item.getOrg().getOrgNm() != null)
                .map(item -> item.getOrg().getOrgNm())
                .collect(Collectors.toList());
      }

      // 資格情報
      for (int i = 0; i < 5; i++) {
        String certNm = "";
        String certExpirationDt = "";
        String empOrgBu = (orgBulist.size() > i) ? orgBulist.get(i) : "";
        String empOrgKa = (orgKalist.size() > i) ? orgKalist.get(i) : "";
        String transEmpOrgBu = (transFerBulist.size() > i) ? transFerBulist.get(i) : "";
        String transEmpOrgKa = (transFerKalist.size() > i) ? transFerKalist.get(i) : "";
        if (i < empDto.getEmpCertList().size()) {
          certNm =
              StringUtils.isEmpty(empDto.getEmpCertList().get(i).getCertCd().getCertNm())
                  ? ""
                  : empDto.getEmpCertList().get(i).getCertCd().getCertNm();
          certExpirationDt =
              StringUtils.isEmpty(empDto.getEmpCertList().get(i).getCertExpirationDt())
                  ? ""
                  : empDto.getEmpCertList().get(i).getCertExpirationDt();
        }
        switch (i) {
          case 0 -> {
            printDto.setEmpCertField1(certNm);
            printDto.setEmpCertField2(certExpirationDt);
            printDto.setEmpOrgBuField1(empOrgBu);
            printDto.setEmpOrgKaField1(empOrgKa);
            printDto.setTransEmpOrgBuField1(transEmpOrgBu);
            printDto.setTransEmpOrgKaField1(transEmpOrgKa);
          }
          case 1 -> {
            printDto.setEmpCertField3(certNm);
            printDto.setEmpCertField4(certExpirationDt);
            printDto.setEmpOrgBuField2(empOrgBu);
            printDto.setEmpOrgKaField2(empOrgKa);
            printDto.setTransEmpOrgBuField2(transEmpOrgBu);
            printDto.setTransEmpOrgKaField2(transEmpOrgKa);
          }
          case 3 -> {
            printDto.setEmpCertField5(certNm);
            printDto.setEmpCertField6(certExpirationDt);
            printDto.setEmpOrgBuField3(empOrgBu);
            printDto.setEmpOrgKaField3(empOrgKa);
            printDto.setTransEmpOrgBuField3(transEmpOrgBu);
            printDto.setTransEmpOrgKaField3(transEmpOrgKa);
          }
          case 4 -> {
            printDto.setEmpCertField7(certNm);
            printDto.setEmpCertField8(certExpirationDt);
            printDto.setEmpOrgBuField4(empOrgBu);
            printDto.setEmpOrgKaField4(empOrgKa);
            printDto.setTransEmpOrgBuField4(transEmpOrgBu);
            printDto.setTransEmpOrgKaField4(transEmpOrgKa);
          }
          case 5 -> {
            printDto.setEmpCertField9(certNm);
            printDto.setEmpCertField10(certExpirationDt);
            printDto.setEmpOrgBuField5(empOrgBu);
            printDto.setEmpOrgKaField5(empOrgKa);
            printDto.setTransEmpOrgBuField5(transEmpOrgBu);
            printDto.setTransEmpOrgKaField5(transEmpOrgKa);
          }
        }
      }
      // メールアドレス
      printDto.setMailAddress(empDto.getMailAddress());
      // 会社電話番号
      printDto.setCompPhoneNo(empDto.getCompPhoneNo());
      // ログインID
      printDto.setLoginId(empDto.getLogin().getLoginId());
      // 所属部署 役職名(表示のみ)
      printDto.setPositionNm1(empDto.getPositionCd().getPositionNm());
      // レポートに渡すパラメータ
      Map<String, Object> paramsMap =
          objectMapper.convertValue(printDto, new TypeReference<Map<String, Object>>() {});
      // レポートを生成する
      byte[] datas =
          reportService.exportReportToPdf(REPORT_FILE_NAME, paramsMap, new JREmptyDataSource());

      return ApiResult.success(datas);
    } catch (Exception ex) {
      LOG.error(ex.toString(), ex);
      throw new SystemException(
          messageSource.getMessage(Message.MSGID_S0000, null, LocaleContextHolder.getLocale()));
    }
  }
}
