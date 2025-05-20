package com.daitoj.tkms.modules.common.repository.mapper;

import com.daitoj.tkms.domain.MMinorWork;
import com.daitoj.tkms.modules.common.service.dto.MinorWorkSearchDto;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

/** 小工事マッパー */
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface MMinorWorkMapper {

  /**
   * 小工事から小工事Dtoに変換する.
   *
   * @param minorWork 小工事
   * @return 小工事Dto
   */
  @Mapping(source = "id.minorWorkCd", target = "minorWorkCd")
  MinorWorkSearchDto toMinorWorkSearchDto(MMinorWork minorWork);

  /**
   * 小工事リストから小工事Dtoリストに変換する.
   *
   * @param minorWorkList 小工事リスト
   * @return 小工事Dtoリスト
   */
  List<MinorWorkSearchDto> toMinorWorkSearchList(List<MMinorWork> minorWorkList);
}
