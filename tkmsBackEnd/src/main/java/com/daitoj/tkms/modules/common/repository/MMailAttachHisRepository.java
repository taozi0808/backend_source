package com.daitoj.tkms.modules.common.repository;

import com.daitoj.tkms.domain.MMailAttachHis;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/** メール送信添付ファイル履歴リポジトリ */
@Repository
public interface MMailAttachHisRepository extends JpaRepository<MMailAttachHis, Long> {}
