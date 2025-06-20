package com.daitoj.tkms.modules.common.repository;

import com.daitoj.tkms.domain.TRoughEstFileDtl;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/** 概算添付ファイルリポジトリ */
public interface TRoughEstFileDtlRepository extends JpaRepository<TRoughEstFileDtl, String> {

}
