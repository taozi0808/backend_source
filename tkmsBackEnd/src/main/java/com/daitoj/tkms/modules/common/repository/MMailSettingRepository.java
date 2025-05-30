package com.daitoj.tkms.modules.common.repository;

import com.daitoj.tkms.domain.MMailSetting;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/** メール送信設定リポジトリ */
@Repository
public interface MMailSettingRepository extends JpaRepository<MMailSetting, Long> {

  /**
   * メール送信設定を取得
   *
   * @param mailFunction メール送信機能
   * @return メール送信設定
   */
  Optional<MMailSetting> findByMailFunction(String mailFunction);
}
