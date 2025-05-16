package com.daitoj.tkms.modules.common.repository;

import com.daitoj.tkms.domain.MNotice;
import com.daitoj.tkms.domain.MNoticeId;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/** お知らせ情報リポジトリ */
@Repository
public interface MNoticeRepository extends JpaRepository<MNotice, MNoticeId> {

  /**
   * お知らせ情報を取得
   *
   * @param pgId 機能ID
   * @param sysdate 適用開始日
   * @return お知らせ情報
   */
  List<MNotice> findByIdPgIdAndIdEffectiveStartDtLessThanEqualOrderByIdEffectiveStartDtDesc(
      String pgId, String sysdate);
}
