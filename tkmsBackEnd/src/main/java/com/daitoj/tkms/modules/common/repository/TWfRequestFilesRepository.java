package com.daitoj.tkms.modules.common.repository;

import com.daitoj.tkms.domain.TWfRequestFiles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/** WF申請添付ファイル情報リポジトリ */
@Repository
public interface TWfRequestFilesRepository extends JpaRepository<TWfRequestFiles, Long> {}
