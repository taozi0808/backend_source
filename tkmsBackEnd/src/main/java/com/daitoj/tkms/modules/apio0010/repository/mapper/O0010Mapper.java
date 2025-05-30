package com.daitoj.tkms.modules.apio0010.repository.mapper;

import com.daitoj.tkms.modules.apio0010.service.dto.VendorInfoDto;
import com.daitoj.tkms.modules.apio0010.service.dto.VendorResultDto;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

/** 業者マッピング. */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface O0010Mapper {

  /**
   * 業者情報dtoから業者結果情報dtoに変換する.
   *
   * @param vendor 業者情報
   * @return 業者結果情報
   */
  List<VendorResultDto> toVendorResultDto(List<VendorInfoDto> vendor);

}
