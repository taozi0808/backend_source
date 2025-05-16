package com.daitoj.tkms.modules.common.service;

import com.daitoj.tkms.domain.MMenuItem;
import com.daitoj.tkms.modules.apia0010.repository.A0010S01Repository;
import com.daitoj.tkms.modules.common.constants.CommonConstants;
import com.daitoj.tkms.modules.common.repository.MMenuItemRepository;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/** メニュー項目情報サービス */
@Service
public class MenuItemService {
  private static final Logger LOG = LoggerFactory.getLogger(MenuItemService.class);

  /** メニュー項目情報リポジトリ(従業員) */
  private final A0010S01Repository a0010S01Repository;

  /** メニュー項目情報リポジトリ */
  private final MMenuItemRepository menuItemRepository;

  /** コンストラクタ */
  public MenuItemService(
      A0010S01Repository a0010S01Repository, MMenuItemRepository menuItemRepository) {
    this.a0010S01Repository = a0010S01Repository;
    this.menuItemRepository = menuItemRepository;
  }

  /**
   * 　メニュー項目情報取得
   *
   * @param username ログインID
   * @param accountK アカウント区分
   * @return メニュー項目情報
   */
  public List<MMenuItem> getMenuItem(String username, String accountK) {
    // メニュー情報
    List<MMenuItem> menuItemList = null;

    // アカウント区分が"1（社員）の場合
    if (CommonConstants.ACCOUNT_K_EMP.equals(accountK)) {
      // メニュー情報
      menuItemList =
          a0010S01Repository.findEmpMenuItems(
              username,
              CommonConstants.HAS_PERMISSION,
              Integer.valueOf(CommonConstants.ACCOUNT_K_EMP));
      // アカウント区分が"2"（協力業者）の場合
      // アカウント区分が"3"（協力業者社員）の場合
    } else if (CommonConstants.ACCOUNT_K_VENDOR.equals(accountK)
        || CommonConstants.ACCOUNT_K_VENDOR_WORKER.equals(accountK)) {
      // メニュー情報
      menuItemList = menuItemRepository.findMenuItems(Integer.valueOf(accountK));
    }

    return menuItemList;
  }
}
