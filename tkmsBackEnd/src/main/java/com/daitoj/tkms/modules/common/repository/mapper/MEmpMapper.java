package com.daitoj.tkms.modules.common.repository.mapper;

import com.daitoj.tkms.domain.MEmp;
import com.daitoj.tkms.modules.common.service.dto.EmpSearchDto;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

/** 従業員マッパー */
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface MEmpMapper {
  /**
   * 従業員リストから従業員Dtoリストに変換する.
   *
   * @param mempList 従業員リスト
   * @return 従業員Dtoリスト
   */
  List<EmpSearchDto> toEmpSearchDtoList(List<MEmp> mempList);
}
