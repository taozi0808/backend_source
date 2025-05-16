package com.daitoj.tkms.modules.apir0045.service;

import com.daitoj.tkms.domain.*;
import com.daitoj.tkms.modules.apir0045.repository.R0045Repository;
import com.daitoj.tkms.modules.apir0045.repository.mapper.R0045Mapper;
import com.daitoj.tkms.modules.apir0045.service.dto.*;
import com.daitoj.tkms.modules.common.constants.CommonConstants;
import com.daitoj.tkms.modules.common.constants.KeyConstants;
import com.daitoj.tkms.modules.common.constants.MasterData;
import com.daitoj.tkms.modules.common.constants.Message;
import com.daitoj.tkms.modules.common.constants.TemplateName;
import com.daitoj.tkms.modules.common.repository.*;
import com.daitoj.tkms.modules.common.repository.mapper.MItemSettingMapper;
import com.daitoj.tkms.modules.common.service.*;
import com.daitoj.tkms.modules.common.service.dto.ApiResult;
import com.daitoj.tkms.modules.common.service.dto.MItemListSettingDto;
import com.daitoj.tkms.modules.common.service.dto.MitemSettingDto;
import com.daitoj.tkms.modules.common.utils.DateUtils;
import com.daitoj.tkms.modules.common.utils.PasswordUtils;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.mail.MessagingException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import net.sf.jasperreports.engine.JREmptyDataSource;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.mail.MailSendException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

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

  /** レポートサービス */
  private final ReportService reportService;

  /** 社員登録情報リポジトリ */
  private final R0045Mapper r0045Mapper;

  /** マスタデータサービス */
  private final ItemListSettingService itemListSettingService;

  /** メールサービス */
  private final CustomMailService customMailService;

  /** メッセージ */
  private final MessageSource messageSource;

  /** fasterxml.jackson@ObjectMapper */
  private final ObjectMapper objectMapper;

  /** マスタデータのDTOマッパー */
  private final MItemSettingMapper itemSettingMapper;

  private final MCertRepository mCertRepository;

  /** ストレージサービス */
  private final CloudStorageService cloudStorageService;

  /** 処理区分：新規 */
  public static final String SHORIKUBUN_SINNKI = "1";

  /** 歴番:01から連番 */
  private static final String HIS_NO_START = "01";

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
      R0045Mapper r0045Mapper,
      CustomMailService customMailService,
      ItemListSettingService itemListSettingService,
      ObjectMapper objectMapper,
      ReportService reportService,
      MessageSource messageSource,
      CloudStorageService cloudStorageService,
      MItemSettingMapper itemSettingMapper,
      MCertRepository mCertRepository) {
    this.r0045Repository = r0045Repository;
    this.loginRepository = loginRepository;
    this.mitemListSettingRepository = mitemListSettingRepository;
    this.mempRepository = mempRepository;
    this.mempOrgRepository = mempOrgRepository;
    this.mempCertRepository = mempCertRepository;
    this.mempPhotoRepository = mempPhotoRepository;
    this.r0045Mapper = r0045Mapper;
    this.customMailService = customMailService;
    this.itemListSettingService = itemListSettingService;
    this.objectMapper = objectMapper;
    this.reportService = reportService;
    this.messageSource = messageSource;
    this.cloudStorageService = cloudStorageService;
    this.itemSettingMapper = itemSettingMapper;
    this.mCertRepository = mCertRepository;
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
      List<MCert> certList = mCertRepository.findByOrderByDisplayOrder();
      // 結果情報
      R0045S01ReturnData ret = new R0045S01ReturnData();
      // 選択項目情報
      ret.setSettingList(result.getData());
      ret.setCertList(certList);

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
      // 社員情報を取得
      MEmp empEntity = r0045Repository.findByEmpCd(empCd).orElseThrow(ConflictException::new);
      EmpResultDto empDto = r0045Mapper.toEmpResultDto(empEntity);

      if (empEntity.getEmpJobTypeCd() != null) {
        // 職種マスタデータを取得
        mitemListSettingRepository
            .findById_ItemClassCdAndId_ItemCdAndId_EffectiveStartDtLessThanEqual(
                MasterData.ITEM_CLASS_CD_D0014,
                empEntity.getEmpJobTypeCd(),
                DateUtils.formatNow(DateUtils.DATE_FORMAT))
            .ifPresent(item -> empDto.setEmpJobTypeName(item.getItemValue()));
      }

      if (empEntity.getSex() != null) {
        // 性別マスタデータを取得
        mitemListSettingRepository
            .findById_ItemClassCdAndId_ItemCdAndId_EffectiveStartDtLessThanEqual(
                MasterData.ITEM_CLASS_CD_D0013,
                empEntity.getSex(),
                DateUtils.formatNow(DateUtils.DATE_FORMAT))
            .ifPresent(item -> empDto.setSexName(item.getItemValue()));
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
  public ApiResult<R0045S03ReturnData> savesyainkanritourokuInfo(R0045Dto inDto) {
    try {
      // 社員コード
      String empCd;
      // 社員情報
      EmpDto empDto = inDto.getEmp();

      // 処理区分が新規
      if (SHORIKUBUN_SINNKI.equals(inDto.getShorikubun())) {
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
          // 社員コードを採番
          empCd = mempRepository.getNextValue();
        }

        // パスワードを生成
        String passwordString = PasswordUtils.createPassword();
        // 社員情報を登録
        MEmp newEmpEntity = insertEmpInfoWithLogin(empDto, empCd, passwordString);

        // m_emp以外のデータを保存
        saveOtherInfo(newEmpEntity.getId(), empDto);

        // メールの変数
        Map<String, Object> varMap = new HashMap<>();
        varMap.put(KeyConstants.A0020_VAR_PASSWORD, passwordString);

        // メールを送信する
        customMailService.sendEmailFromTemplate(
            TemplateName.A0020_TEMPLATE,
            empDto.getMailAddress(),
            Message.A0020_MAIL_TITLE,
            varMap,
            false,
            false);

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
        saveOtherInfo(newEmpEntity.getId(), empDto);
      }

      return ApiResult.success();
    } catch (MessagingException | MailSendException e) {
      // メッセージ：メール送信に失敗しました。
      String msg =
          messageSource.getMessage(Message.MSGID_A00007, null, LocaleContextHolder.getLocale());

      LOG.warn(msg);

      throw new SystemException(msg);
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
   * @param mailAddress メールアドレス
   * @return エラー情報
   */
  public ApiResult<?> syainkanritourokupasswordalert(String loginId, String mailAddress) {
    try {
      // パスワードを生成
      String passwordString = PasswordUtils.createPassword();

      // パスワード暗号化処理
      BCryptPasswordEncoder bcryptPasswordEncoder = new BCryptPasswordEncoder();
      String encodePassword = bcryptPasswordEncoder.encode(passwordString);

      // ログイン情報を取得する
      MLogin loginInfo = loginRepository.findById(loginId).orElseThrow(ConflictException::new);

      // パスワードを設定
      loginInfo.setPassword(encodePassword);
      // ログイン情報の更新処理
      loginRepository.save(loginInfo);

      // メール変数
      Map<String, Object> varMap = new HashMap<>();
      varMap.put(KeyConstants.A0020_VAR_PASSWORD, passwordString);

      // メールを送信する
      customMailService.sendEmailFromTemplate(
          TemplateName.A0020_TEMPLATE, mailAddress, Message.A0020_MAIL_TITLE, varMap, false, false);

      return ApiResult.success();

    } catch (MessagingException e) {
      // メッセージ：メール送信に失敗しました。
      String msg =
          messageSource.getMessage(Message.MSGID_A00007, null, LocaleContextHolder.getLocale());

      LOG.warn(msg);

      // 結果情報
      return ApiResult.error(Message.MSGID_A00007, msg);

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
      // "1"（削除済）
      empEntity.setDelFlg(CommonConstants.DELETE_FLAG_DELETE);
      // 社員情報を削除
      mempRepository.saveAndFlush(empEntity);

      // ログイン情報を取得する
      Optional<MLogin> loginInfo = loginRepository.findById(empEntity.getLogin().getLoginId());
      loginInfo.ifPresent(
          mlogin -> {
            // "1"（削除済）
            mlogin.setDelFlg(CommonConstants.DELETE_FLAG_DELETE);
            // ログイン情報を削除
            loginRepository.save(mlogin);
          });

      if (!CollectionUtils.isEmpty(empEntity.getEmpOrgList())) {
        // "1"（削除済）
        empEntity
            .getEmpOrgList()
            .forEach(entity -> entity.setDelFlg(CommonConstants.DELETE_FLAG_DELETE));
        // 従業員・組織・対照情報を削除
        mempOrgRepository.saveAllAndFlush(empEntity.getEmpOrgList());
      }

      if (!CollectionUtils.isEmpty(empEntity.getEmpCertList())) {
        // "1"（削除済）
        empEntity
            .getEmpCertList()
            .forEach(entity -> entity.setDelFlg(CommonConstants.DELETE_FLAG_DELETE));
        // 従業員資格情報を削除
        mempCertRepository.saveAllAndFlush(empEntity.getEmpCertList());
      }

      if (!CollectionUtils.isEmpty(empEntity.getEmpPhotoList())) {
        // "1"（削除済）
        empEntity
            .getEmpPhotoList()
            .forEach(entity -> entity.setDelFlg(CommonConstants.DELETE_FLAG_DELETE));
        // 従業員顔写真を削除
        mempPhotoRepository.saveAllAndFlush(empEntity.getEmpPhotoList());
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
    empEntity.setHisNo(HIS_NO_START);
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
    // ID TODO
    // 最新フラグ;1：最新
    empEntity.setNewestFlg(CommonConstants.NEWEST_FLAG_NEW);
    // 歴番+1
    empEntity.setHisNo(String.format("%02d", Integer.parseInt(dbEmpEntity.getHisNo()) + 1));
    // 最新データとして登録
    return mempRepository.save(empEntity);
  }

  /**
   * m_emp以外のデータを保存
   *
   * @param empId 社員ID
   * @param empDto 社員情報
   */
  private void saveOtherInfo(Long empId, EmpDto empDto) {
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

      // 顔認証用写真を取得
      R0045PrintDto printDto = new R0045PrintDto();
      List<EmpPhotoDto> photoList =
          Optional.ofNullable(empDto.getEmpPhotoList()).orElse(Collections.emptyList());

      for (int i = 0; i < photoList.size(); i++) {
        EmpPhotoDto photo = photoList.get(i);
        String url = null;
        if (StringUtils.isNotEmpty(photo.getPhotoUrl().trim())) {
          url = cloudStorageService.createUrl(photo.getPhotoUrl());
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
      // 資格情報
      for (int i = 0; i < 5; i++) {
        String certNm = "";
        String certExpirationDt = "";
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
          }
          case 1 -> {
            printDto.setEmpCertField3(certNm);
            printDto.setEmpCertField4(certExpirationDt);
          }
          case 3 -> {
            printDto.setEmpCertField5(certNm);
            printDto.setEmpCertField6(certExpirationDt);
          }
          case 4 -> {
            printDto.setEmpCertField7(certNm);
            printDto.setEmpCertField8(certExpirationDt);
          }
          case 5 -> {
            printDto.setEmpCertField9(certNm);
            printDto.setEmpCertField10(certExpirationDt);
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
