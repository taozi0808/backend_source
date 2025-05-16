package com.daitoj.tkms.modules.common.repository;

import com.daitoj.tkms.domain.MCert;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/** 資格情報リポジトリ */
public interface MCertRepository extends JpaRepository<MCert, String> {

  /**
   * 資格情報を取得
   *
   * @return
   */
  List<MCert> findByOrderByDisplayOrder();
}
