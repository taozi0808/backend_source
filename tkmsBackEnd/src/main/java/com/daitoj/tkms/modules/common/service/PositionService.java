package com.daitoj.tkms.modules.common.service;

import com.daitoj.tkms.domain.MPosition;
import com.daitoj.tkms.modules.common.constants.CommonConstants;
import com.daitoj.tkms.modules.common.repository.MPositionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/** 役職サービス */
@Service
public class PositionService {
  private static final Logger LOG = LoggerFactory.getLogger(PositionService.class);

  /** 役職情報リポジトリ */
  private final MPositionRepository mpositionRepository;

  /** コンストラクタ */
  public PositionService(MPositionRepository mpositionRepository) {
    this.mpositionRepository = mpositionRepository;
  }

  /**
   * 役職権限チェック
   *
   * @param positionCd 役職コード
   * @param confirmCheckFlag 承認権限チェック要否フラグ
   * @param deleteCheckFlag 削除権限チェック要否フラグ
   * @param editCheckFlag 編集権限チェック要否フラグ
   * @param referCheckFlag 参照権限チェック要否フラグ
   * @param poCheckFlag 注文書権限チェック要否フラグ
   * @return 権限が変更された:false
   */
  public boolean isPermissionValid(
      String positionCd,
      String confirmCheckFlag,
      String deleteCheckFlag,
      String editCheckFlag,
      String referCheckFlag,
      String poCheckFlag) {

    // 役職情報を取得
    MPosition positionInfo = mpositionRepository.findByPositionCd(positionCd);

    // 権限なし場合
    if (positionInfo == null) {
      return false;
    }

    // 承認権限チェック
    if (isPermissionInvalid(confirmCheckFlag, positionInfo.getConfirmPerm())) {
      return false;
    }

    // 削除権限チェック
    if (isPermissionInvalid(deleteCheckFlag, positionInfo.getDeletePerm())) {
      return false;
    }

    // 編集権限チェック
    if (isPermissionInvalid(editCheckFlag, positionInfo.getEditPerm())) {
      return false;
    }

    // 参照権限チェック
    if (isPermissionInvalid(referCheckFlag, positionInfo.getReferPerm())) {
      return false;
    }

    // 注文書権限チェック
    if (isPermissionInvalid(poCheckFlag, positionInfo.getPoPerm())) {
      return false;
    }

    return true;
  }

  /**
   * 権限チエック
   *
   * @param checkFlag 権限チェック
   * @param curPerm DB権限
   * @return true:権限なし
   */
  private boolean isPermissionInvalid(String checkFlag, String curPerm) {
    // チエック要の場合
    if (CommonConstants.PERM_CHECK_FLG.equals(checkFlag)
        && !CommonConstants.HAS_PERMISSION.equals(curPerm)) {
      return true;
    }

    return false;
  }
}
