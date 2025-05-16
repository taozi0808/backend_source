package com.daitoj.tkms.modules.common.repository;

import com.daitoj.tkms.domain.MNumber;
import com.daitoj.tkms.domain.MNumberId;
import jakarta.persistence.LockModeType;
import jakarta.persistence.QueryHint;
import java.time.Instant;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.stereotype.Repository;

/** 採番リポジトリ */
@Repository
public interface MNumberRepository extends JpaRepository<MNumber, MNumberId> {
  /**
   * 採番情報を取得
   *
   * @param fieldId 項目ID
   * @param pkNo 主番
   * @return 採番情報
   */
  @Lock(LockModeType.PESSIMISTIC_WRITE)
  @QueryHints({@QueryHint(name = "jakarta.persistence.lock.timeout", value = "0")})
  @Query("SELECT m FROM MNumber m WHERE m.id.fieldId = :fieldId AND m.id.pkNo = :pkNo")
  Optional<MNumber> findById_FieldIdAndId_PkNo(String fieldId, String pkNo);

  /**
   * システム日付を取得
   *
   * @return システム日付
   */
  @Query(value = "SELECT CURRENT_TIMESTAMP", nativeQuery = true)
  Instant getCurrentDbTime();
}
