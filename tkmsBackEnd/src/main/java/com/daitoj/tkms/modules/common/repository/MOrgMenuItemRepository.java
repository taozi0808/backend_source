package com.daitoj.tkms.modules.common.repository;

import com.daitoj.tkms.domain.MOrgMenuItem;
import com.daitoj.tkms.domain.MOrgMenuItemId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/** 組織・メニュー項目・対照表情報リポジトリ */
@Repository
public interface MOrgMenuItemRepository extends JpaRepository<MOrgMenuItem, MOrgMenuItemId> {

}
