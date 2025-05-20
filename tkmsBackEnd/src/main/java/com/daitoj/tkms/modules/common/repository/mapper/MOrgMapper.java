package com.daitoj.tkms.modules.common.repository.mapper;

import com.daitoj.tkms.domain.MOrg;
import com.daitoj.tkms.modules.common.service.dto.OrgSearchDto;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

/** 組織マッパー */
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface MOrgMapper {
  /**
   * 組織リストから組織Dtoリストに変換する.
   *
   * @param morgList 組織リスト
   * @return 組織Dtoリスト
   */
  List<OrgSearchDto> toOrgSearchDtoList(List<MOrg> morgList);
}
