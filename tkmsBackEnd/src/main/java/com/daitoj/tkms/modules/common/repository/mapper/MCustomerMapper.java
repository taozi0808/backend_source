package com.daitoj.tkms.modules.common.repository.mapper;

import com.daitoj.tkms.domain.MCustomer;
import com.daitoj.tkms.modules.common.service.dto.CustomerSearchDto;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

/** 顧客マッパー */
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface MCustomerMapper {
  /**
   * 顧客リストから顧客Dtoリストに変換する.
   *
   * @param mcustomerList 顧客リスト
   * @return 顧客Dtoリスト
   */
  List<CustomerSearchDto> toCustomerSearchList(List<MCustomer> mcustomerList);
}
