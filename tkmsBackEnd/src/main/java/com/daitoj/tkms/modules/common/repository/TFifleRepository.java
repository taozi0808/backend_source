package com.daitoj.tkms.modules.common.repository;

import com.daitoj.tkms.domain.TFifle;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

/** 添付ファイルリポジトリ */
public interface TFifleRepository extends JpaRepository<TFifle, UUID> {}
