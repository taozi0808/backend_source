package com.daitoj.tkms.modules.apia0030.repository;

import com.daitoj.tkms.domain.MLogin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/** 個人設定のリポジトリ */
@Repository
public interface A0030Repository extends JpaRepository<MLogin, Long> {

  /**
   * ログイン情報TBLから情報を取得する
   *
   * @param loginId ログインID
   * @param delFlg 除フラグ削除フラグ
   * @return ログイン情報
   */
  MLogin findByLoginIdAndDelFlg(String loginId, String delFlg);
}
