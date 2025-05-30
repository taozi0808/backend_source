package com.daitoj.tkms.modules.common.repository;

import com.daitoj.tkms.domain.TWfApprStepFiles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/** 業務種類添付ファイル情報リポジトリ */
@Repository
public interface TWfApprStepFilesRepository extends JpaRepository<TWfApprStepFiles, Long> {}
