package com.daitoj.tkms.modules.common.repository;

import com.daitoj.tkms.domain.TWfApprStep;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/** WF承認ステップ情報リポジトリ */
@Repository
public interface TWfApprStepRepository extends JpaRepository<TWfApprStep, Long> {

  /**
   * WF承認ステップ情報を取得
   *
   * @param id 申請ID
   * @param apprStepOrder 承認順序
   * @return WF承認ステップ情報
   */
  Optional<TWfApprStep> findByWfRequest_IdAndApprStepOrder(Long id, Integer apprStepOrder);

  /**
   * WF承認ステップ情報を取得
   *
   * @param id 申請ID
   * @return WF承認ステップ情報
   */
  List<TWfApprStep> findByWfRequest_Id(Long id);
}
