package com.daitoj.tkms.modules.apir0055.repository.mapper;

import com.daitoj.tkms.modules.apir0055.service.dto.CustomerInfoDto;
import com.daitoj.tkms.modules.apir0055.service.dto.CustomerResultDto;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

/** 顧客マッピング. */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface R0055Mapper {

  /**
   * 顧客情報dtoから顧客結果情報dtoに変換する.
   *
   * @param customerList 顧客情報
   * @return 顧客結果情報
   */
  List<CustomerResultDto> toCustomerResultDto(List<CustomerInfoDto> customerList);

}
