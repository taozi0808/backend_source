package com.daitoj.tkms.modules.apia0010.service;

import com.daitoj.tkms.domain.MEmp;
import com.daitoj.tkms.domain.MEmpOrg;
import com.daitoj.tkms.domain.MMenuItem;
import com.daitoj.tkms.domain.MNotice;
import com.daitoj.tkms.domain.MSystemConfig;
import com.daitoj.tkms.domain.MVendor;
import com.daitoj.tkms.domain.MWorker;
import com.daitoj.tkms.modules.apia0010.repository.A0010S01Repository;
import com.daitoj.tkms.modules.apia0010.repository.A0010S02Repository;
import com.daitoj.tkms.modules.apia0010.repository.mapper.A0010Mapper;
import com.daitoj.tkms.modules.apia0010.service.dto.A0010S01ReturnData;
import com.daitoj.tkms.modules.apia0010.service.dto.A0010S02ReturnData;
import com.daitoj.tkms.modules.common.constants.CommonConstants;
import com.daitoj.tkms.modules.common.constants.Message;
import com.daitoj.tkms.modules.common.constants.SystemConfig;
import com.daitoj.tkms.modules.common.repository.MEmpOrgRepository;
import com.daitoj.tkms.modules.common.repository.MEmpRepository;
import com.daitoj.tkms.modules.common.repository.MMenuItemRepository;
import com.daitoj.tkms.modules.common.repository.MNoticeRepository;
import com.daitoj.tkms.modules.common.repository.MSystemConfigRepository;
import com.daitoj.tkms.modules.common.repository.MVendorRepository;
import com.daitoj.tkms.modules.common.service.InvalidUserException;
import com.daitoj.tkms.modules.common.service.NumberService;
import com.daitoj.tkms.modules.common.service.SystemException;
import com.daitoj.tkms.modules.common.service.dto.ApiResult;
import com.daitoj.tkms.modules.common.service.dto.CustomUserDetails;
import com.daitoj.tkms.modules.common.service.dto.EmpDto;
import com.daitoj.tkms.modules.common.service.dto.EmpOrgDto;
import com.daitoj.tkms.modules.common.service.dto.MenuItemDto;
import com.daitoj.tkms.modules.common.service.dto.VendorDto;
import com.daitoj.tkms.modules.common.service.dto.WorkerDto;
import com.daitoj.tkms.modules.common.utils.DateUtils;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

/** 案件一覧ビジネスロジック */
@Service
@Transactional
public class A0010Service {
  private static final Logger LOG = LoggerFactory.getLogger(A0010Service.class);

  /** メニュー項目情報リポジトリ(従業員) */
  private final A0010S01Repository a0010S01Repository;

  /** 作業員情報リポジトリ */
  private final A0010S02Repository a0010S02Repository;

  /** システム設定情報リポジトリ */
  private final MSystemConfigRepository msystemConfigRepository;

  private final NumberService numberRuleService;

  /** 従業員情報リポジトリ */
  private final MEmpRepository mempRepository;

  /** 業者情報リポジトリ */
  private final MVendorRepository vendorRepository;

  /** メニュー項目情報リポジトリ */
  private final MMenuItemRepository menuItemRepository;

  /** お知らせ情報リポジトリ */
  private final MNoticeRepository noticeRepository;

  /** 役職情報リポジトリ */
  private final MEmpOrgRepository empOrgRepository;

  /** 業者情報マッパー */
  private final A0010Mapper a0010Mapper;

  /** メッセージ */
  private final MessageSource messageSource;

  /** コンストラクタ */
  public A0010Service(
      A0010S01Repository a0010S01Repository,
      A0010S02Repository a0010S02Repository,
      MSystemConfigRepository msystemConfigRepository,
      NumberService numberRuleService,
      MEmpRepository mempRepository,
      MVendorRepository vendorRepository,
      MMenuItemRepository menuItemRepository,
      MNoticeRepository noticeRepository,
      MEmpOrgRepository empOrgRepository,
      A0010Mapper a0010Mapper,
      MessageSource messageSource) {
    this.a0010S01Repository = a0010S01Repository;
    this.a0010S02Repository = a0010S02Repository;
    this.msystemConfigRepository = msystemConfigRepository;
    this.numberRuleService = numberRuleService;
    this.mempRepository = mempRepository;
    this.vendorRepository = vendorRepository;
    this.menuItemRepository = menuItemRepository;
    this.noticeRepository = noticeRepository;
    this.empOrgRepository = empOrgRepository;
    this.a0010Mapper = a0010Mapper;
    this.messageSource = messageSource;
  }

  /**
   * ログイン情報取得
   *
   * @param userDetails ユーザ情報
   * @return ログイン情報
   */
  public ApiResult<A0010S01ReturnData> getLoginInfo(CustomUserDetails userDetails) {
    try {
      // メニュー情報
      List<MMenuItem> menuItemList = null;

      // 戻り値
      A0010S01ReturnData returnData = new A0010S01ReturnData();
      // アカウント区分
      returnData.setAccountKubun(userDetails.getAccountKubun());

      // アカウント区分が"1（社員）の場合
      if (CommonConstants.ACCOUNT_K_EMP.equals(userDetails.getAccountKubun())) {
        // 社員情報
        MEmp empInfo = mempRepository.findBylogin_LoginId(userDetails.getUsername());

        // 社員情報が取得できない
        if (empInfo == null || empInfo.getPositionCd() == null) {
          // メッセージ
          String msg =
              messageSource.getMessage(Message.MSGID_A0003, null, LocaleContextHolder.getLocale());

          LOG.warn(msg);

          // 結果情報
          throw new InvalidUserException(Message.MSGID_A0003, msg);
        }
        // システム利用可能時間
        List<MSystemConfig> configList =
            msystemConfigRepository.findById_SysCd(SystemConfig.SYS_CD_USETM);

        if (!CollectionUtils.isEmpty(configList)
            && !CommonConstants.USE_TIME_CONTROL_FLG.equals(empInfo.getUseTimeControlFlg())) {
          // システム利用可能時間-開始時間
          MSystemConfig startTime =
              configList.stream()
                  .filter(
                      item ->
                          item.getId().getConfigKey().equals(SystemConfig.SYS_USABLE_START_TIME))
                  .findFirst()
                  .orElse(null);

          // システム利用可能時間-終了時間
          MSystemConfig endTime =
              configList.stream()
                  .filter(
                      item -> item.getId().getConfigKey().equals(SystemConfig.SYS_USABLE_END_TIME))
                  .findFirst()
                  .orElse(null);

          // 利用時間チェック
          if (!checkWorkTime(startTime, endTime)) {
            // メッセージ
            String msg =
                messageSource.getMessage(
                    Message.MSGID_A0004, null, LocaleContextHolder.getLocale());

            LOG.warn(msg);

            // 結果情報
            return ApiResult.error(Message.MSGID_A0004, msg);
          }
        }

        // 参照権限がない場合
        if (!CommonConstants.HAS_PERMISSION.equals(empInfo.getPositionCd().getReferPerm())) {
          // メッセージ
          String msg =
              messageSource.getMessage(Message.MSGID_K00006, null, LocaleContextHolder.getLocale());

          LOG.warn(msg);

          // 結果情報
          throw new InvalidUserException(Message.MSGID_K00006, msg);
        }

        // 社員情報Dtoに変換する
        EmpDto empDto = a0010Mapper.toEmpDto(empInfo);
        // 社員情報を設定
        returnData.setEmpInfo(empDto);

        // 従業員・組織・対照表情報
        List<MEmpOrg> orgList = empOrgRepository.findByEmpId(empInfo.getId());

        // 組織情報が取得できない
        if (CollectionUtils.isEmpty(orgList)) {
          // メッセージ
          String msg =
              messageSource.getMessage(Message.MSGID_A0003, null, LocaleContextHolder.getLocale());

          LOG.warn(msg);

          // 結果情報
          throw new InvalidUserException(Message.MSGID_A0003, msg);
        }

        // 組織情報Dtoに変換する
        List<EmpOrgDto> empOrgDtoList = a0010Mapper.toEmpOrgDtoList(orgList);
        // 組織情報を設定
        returnData.setEmpOrgList(empOrgDtoList);

        // メニュー情報
        menuItemList =
            a0010S01Repository.findEmpMenuItems(
                userDetails.getUsername(),
                CommonConstants.HAS_PERMISSION,
                Integer.valueOf(CommonConstants.ACCOUNT_K_EMP));

        // アカウント区分が"2"（協力業者）の場合
      } else if (CommonConstants.ACCOUNT_K_VENDOR.equals(userDetails.getAccountKubun())) {
        // 業者情報
        MVendor vendorHdr = vendorRepository.findByLogin_loginId(userDetails.getUsername());

        // 業者情報が取得できない
        if (vendorHdr == null) {
          // メッセージ
          String msg =
              messageSource.getMessage(Message.MSGID_A0003, null, LocaleContextHolder.getLocale());

          LOG.warn(msg);

          // 結果情報
          throw new InvalidUserException(Message.MSGID_A0003, msg);
        }

        // 業者情報Dtoに変換する
        VendorDto vhdDto = a0010Mapper.toVendorDto(vendorHdr);
        // 業者情報を設定
        returnData.setVendorHdrInfo(vhdDto);

        // アカウント区分が"3"（協力業者社員）の場合
      } else if (CommonConstants.ACCOUNT_K_VENDOR_WORKER.equals(userDetails.getAccountKubun())) {
        // 作業員
        MWorker worderInfo = a0010S02Repository.findWorkerInfo(userDetails.getUsername());

        // 作業員情報が取得できない
        if (worderInfo == null) {
          // メッセージ
          String msg =
              messageSource.getMessage(Message.MSGID_A0003, null, LocaleContextHolder.getLocale());

          LOG.warn(msg);

          // 結果情報
          throw new InvalidUserException(Message.MSGID_A0003, msg);
        }

        // 作業員情報Dtoに変換する
        WorkerDto workerDto = a0010Mapper.toWorkerDto(worderInfo);
        // 作業員情報を設定
        returnData.setWorkerInfo(workerDto);
      }

      // アカウント区分が"2"（協力業者）の場合
      // アカウント区分が"3"（協力業者社員）の場合
      if (CommonConstants.ACCOUNT_K_VENDOR.equals(userDetails.getAccountKubun())
          || CommonConstants.ACCOUNT_K_VENDOR_WORKER.equals(userDetails.getAccountKubun())) {
        // メニュー情報
        menuItemList =
            menuItemRepository.findMenuItems(Integer.valueOf(userDetails.getAccountKubun()));
      }

      // メニュー情報がある場合
      if (!CollectionUtils.isEmpty(menuItemList)) {
        // Dtoに変換する
        List<MenuItemDto> menuList = a0010Mapper.toMenuItemDtoList(menuItemList);

        // ツリー構造
        List<MenuItemDto> list = buildMenuTree(menuList);

        // メニュー情報を設定
        returnData.setMenuItemList(list);
      }

      return ApiResult.success(returnData);
    } catch (InvalidUserException ex) {
      throw ex;
    } catch (Exception ex) {
      LOG.error(ex.toString(), ex);

      throw new SystemException(
          messageSource.getMessage(Message.MSGID_S0000, null, LocaleContextHolder.getLocale()));
    }
  }

  /**
   * お知らせ情報取得
   *
   * @param pgId 機能ID
   * @return お知らせ情報
   */
  public ApiResult<A0010S02ReturnData> getNoticeList(String pgId) {
    try {
      // 戻り値
      A0010S02ReturnData returnData = new A0010S02ReturnData();

      // お知らせ情報
      List<MNotice> noticeList =
          noticeRepository
              .findByIdPgIdAndIdEffectiveStartDtLessThanEqualOrderByIdEffectiveStartDtDesc(
                  pgId, DateUtils.formatNow(DateUtils.DATE_FORMAT));

      // お知らせ情報がある場合
      if (!CollectionUtils.isEmpty(noticeList)) {
        // 1件目のお知らせ情報を設定
        returnData.setNoticeContentHtml(noticeList.get(0).getNoticeContentHtml());
      }

      return ApiResult.success(returnData);

    } catch (Exception ex) {
      LOG.error(ex.toString(), ex);

      throw new SystemException(
          messageSource.getMessage(Message.MSGID_S0000, null, LocaleContextHolder.getLocale()));
    }
  }

  /**
   * メニューツリーを構築
   *
   * @param flatList メニュー項目リスト
   * @return 変換されたメニュー項目リスト
   */
  private List<MenuItemDto> buildMenuTree(List<MenuItemDto> flatList) {
    // メニュー項目IDマップ
    Map<Integer, MenuItemDto> map = new HashMap<>();
    // メニュー項目のリスト
    List<MenuItemDto> rootItems = new ArrayList<>();

    // マップに変換
    for (MenuItemDto item : flatList) {
      map.put(item.getId(), item);
    }

    // 親メニュー項目に追加
    for (MenuItemDto item : flatList) {
      Integer parentId = item.getParentMenuItemId();
      if (parentId == null) {
        // ルートアイテムに追加
        rootItems.add(item);
      } else {
        // 親IDが存在する場合
        MenuItemDto parent = map.get(parentId);
        if (parent != null) {
          // 親メニュー項目に子を追加
          parent.getChildren().add(item);
        }
      }
    }

    // ソート
    sortChildren(rootItems);
    return rootItems;
  }

  /**
   * ソート処理
   *
   * @param items メニュー項目リスト
   */
  private void sortChildren(List<MenuItemDto> items) {
    for (MenuItemDto item : items) {
      // 表示順でソート
      item.getChildren().sort(Comparator.comparingInt(MenuItemDto::getDisplayOrder));
      // 再帰的に子メニュー項目のソート
      sortChildren(item.getChildren());
    }
  }

  /**
   * 利用時間チェック
   *
   * @param startTime 開始時間
   * @param endTime 終了時間
   * @return 利用可能
   */
  private boolean checkWorkTime(MSystemConfig startTime, MSystemConfig endTime) {
    // フォーマット
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("H:mm");
    // システム時間
    LocalTime now = LocalTime.ofInstant(numberRuleService.getSystemDate(), ZoneId.systemDefault());

    if (startTime != null && StringUtils.isNotBlank(startTime.getConfigValue())) {
      LocalTime start = LocalTime.parse(startTime.getConfigValue(), formatter);
      // 時間チェック
      if (now.isBefore(start)) {
        return false;
      }
    }

    if (endTime != null && StringUtils.isNotBlank(endTime.getConfigValue())) {
      LocalTime end = LocalTime.parse(endTime.getConfigValue(), formatter);

      // 時間チェック
      if (now.isAfter(end)) {
        return false;
      }
    }

    return true;
  }
}
