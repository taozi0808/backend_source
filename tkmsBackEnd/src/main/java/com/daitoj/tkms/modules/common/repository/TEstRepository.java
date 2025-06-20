package com.daitoj.tkms.modules.common.repository;

import com.daitoj.tkms.domain.TEst;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/** 概算明細リポジトリ */
public interface TEstRepository extends JpaRepository<TEst, String> {

    /**
     * 見積ヘッダを取得
     *
     * @param id 概算ヘッダID
     *
     * @return
     */
    List<TEst> findByroughEstHid_Id(Long id);
}
