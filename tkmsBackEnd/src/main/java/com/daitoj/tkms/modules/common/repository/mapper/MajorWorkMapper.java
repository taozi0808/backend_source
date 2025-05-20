package com.daitoj.tkms.modules.common.repository.mapper;

import com.daitoj.tkms.domain.MMajorWork;
import com.daitoj.tkms.modules.common.service.dto.MajorWorkSearchDto;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

/** 大工事マッパー */
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface MajorWorkMapper {
  /**
   * 大工事リストから大工事Dtoリストに変換する.
   *
   * @param majorWorkList 大工事リスト
   * @return 大工事Dtoリスト
   */
  List<MajorWorkSearchDto> toMajorWorkSearchList(List<MMajorWork> majorWorkList);
}
