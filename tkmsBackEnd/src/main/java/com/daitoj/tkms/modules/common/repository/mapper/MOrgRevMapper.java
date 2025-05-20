package com.daitoj.tkms.modules.common.repository.mapper;

import com.daitoj.tkms.domain.MOrgRev;
import com.daitoj.tkms.modules.common.service.dto.OrgRevSearchDto;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

/** 組織改定マッパー */
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface MOrgRevMapper {
  /**
   * 組織改定リストから組織改定Dtoリストに変換する.
   *
   * @param morgRevList 組織改定リスト
   * @return 組織改定Dtoリスト
   */
  List<OrgRevSearchDto> toOrgRevSearchDtoList(List<MOrgRev> morgRevList);
}
