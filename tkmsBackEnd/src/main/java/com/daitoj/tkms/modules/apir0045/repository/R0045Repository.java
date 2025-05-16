package com.daitoj.tkms.modules.apir0045.repository;

import com.daitoj.tkms.domain.MEmp;
import java.util.Optional;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/** 社員登録のリポジトリ */
@Repository
public interface R0045Repository extends JpaRepository<MEmp, Long> {

  /**
   * 社員情報を取得
   *
   * @param empCd 社員コード
   * @return 社員情報
   */
  @EntityGraph(
      attributePaths = {
        "positionCd",
        "belongOfficeCd",
        "empCertList.certCd",
        "empPhotoList",
        "empOrgList.org"
      })
  Optional<MEmp> findByEmpCd(String empCd);
}
