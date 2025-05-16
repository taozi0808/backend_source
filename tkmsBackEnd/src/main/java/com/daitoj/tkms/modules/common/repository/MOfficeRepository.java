package com.daitoj.tkms.modules.common.repository;

import com.daitoj.tkms.domain.MOffice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/** 事業所情報リポジトリ */
@Repository
public interface MOfficeRepository extends JpaRepository<MOffice, String> {

}
