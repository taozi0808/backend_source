package com.daitoj.tkms.modules.apid0010.repository.mapper;

import com.daitoj.tkms.modules.apid0010.service.dto.DetailedEstInfoDto;
import com.daitoj.tkms.modules.apid0010.service.dto.DetailedEstResultDto;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

/** 精積算マッピング. */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface D0010Mapper {

  /**
   * 精積算情報dtoから精積算結果dtoに変換する.
   *
   * @param detailedEstInfoDto 精積算情報
   * @return 精積算情報
   */
  List<DetailedEstResultDto> toDetailedEstResultDto(List<DetailedEstInfoDto> detailedEstInfoDto);

}
