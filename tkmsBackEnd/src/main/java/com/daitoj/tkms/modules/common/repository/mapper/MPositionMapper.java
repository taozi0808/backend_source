package com.daitoj.tkms.modules.common.repository.mapper;

import com.daitoj.tkms.domain.MPosition;
import com.daitoj.tkms.modules.common.service.dto.PositionSearchDto;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

/** 役職マッパー */
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface MPositionMapper {
  /**
   * 役職リストから役職Dtoリストに変換する.
   *
   * @param mpositionList 役職リスト
   * @return 役職Dtoリスト
   */
  List<PositionSearchDto> toPositionSearchDtoList(List<MPosition> mpositionList);
}
