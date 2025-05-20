package com.daitoj.tkms.modules.common.repository.mapper;

import com.daitoj.tkms.domain.MCert;
import com.daitoj.tkms.modules.common.service.dto.CertSearchDto;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

/** 資格マッパー */
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface MCertMapper {
  /**
   * 資格リストから資格Dtoリストに変換する.
   *
   * @param certList 資格リスト
   * @return 資格toリスト
   */
  List<CertSearchDto> toCertSearchList(List<MCert> certList);
}
