package com.daitoj.tkms.modules.common.repository;

import com.daitoj.tkms.domain.MSystemConfig;
import java.util.List;
import java.util.Optional;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/** システム設定リポジトリ */
@Repository
public interface MSystemConfigRepository extends JpaRepository<MSystemConfig, String> {

  // システムコードキー
  String SYSTEMCONFIG_BY_SYSCD_CACHE = "findById_SysCd";
  // システムコード、システム設定キー
  String SYSTEMCONFIG_BY_SYSCD_KEY_CACHE = "findById_SysCdAndId_ConfigKey";

  /**
   * システムコードより、システム設定を取得
   *
   * @param sysCd システムコード
   * @return システム設定
   */
  @Cacheable(value = SYSTEMCONFIG_BY_SYSCD_CACHE, key = "#sysCd", unless = "#result == null")
  List<MSystemConfig> findById_SysCd(String sysCd);

  /**
   * システム設定キーより、システム設定を取得
   *
   * @param sysCd システムコード
   * @param configKey システム設定キー
   * @return システム設定
   */
  @Cacheable(
      value = SYSTEMCONFIG_BY_SYSCD_KEY_CACHE,
      key = "#sysCd + '-' + #configKey",
      unless = "#result == null")
  Optional<MSystemConfig> findById_SysCdAndId_ConfigKey(String sysCd, String configKey);
}
