package com.daitoj.tkms.modules.apid0010.service.dto;

import com.daitoj.tkms.modules.common.utils.EnhancedFullWidthConverterUtils;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.Optional;

/** 精積算結果情報. */
@lombok.Getter
@lombok.Setter
@Schema(name = "DetailedEstResultDto", description = "精積算結果情報")
public class DetailedEstResultDto extends DetailedEstInfoDto {

  /* 全角案件名 */
  @Schema(description = "全角案件名")
  public String getFullWidthProjectNm() {
    return EnhancedFullWidthConverterUtils.convert(Optional.ofNullable(projectNm).orElse(""));
  }

  /* 全角顧客名 */
  @Schema(description = "全角顧客名")
  public String getFullWidthCustomerNm() {
    return EnhancedFullWidthConverterUtils.convert(Optional.ofNullable(customerNm).orElse(""));
  }

}
