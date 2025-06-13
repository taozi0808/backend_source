package com.daitoj.tkms.modules.apir0055.service.dto;

import com.daitoj.tkms.modules.common.utils.EnhancedFullWidthConverterUtils;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.Optional;

/** 顧客結果情報. */
@lombok.Getter
@lombok.Setter
@Schema(name = "CustomerResultDto", description = "顧客結果情報")
public class CustomerResultDto extends CustomerInfoDto {

  /* 全角顧客名 */
  @JsonProperty("fullWidthCustomerNm")
  @Schema(description = "全角顧客名")
  public String getFullWidthCustomerNm() {
    return EnhancedFullWidthConverterUtils.convert(Optional.ofNullable(customerNm).orElse(""));
  }

  /* 全角顧客略称 */
  @JsonProperty("fullWidthCustomerRyakusyou")
  @Schema(description = "全角顧客略称")
  public String getFullWidthCustomerRyakusyou() {
    return EnhancedFullWidthConverterUtils.convert(
        Optional.ofNullable(customerRyakusyou).orElse(""));
  }

  /* 全角顧客カナ名 */
  @JsonProperty("fullWidthCustomerKnNm")
  @Schema(description = "全角顧客カナ名")
  public String getFullWidthCustomerKnNm() {
    return EnhancedFullWidthConverterUtils.convert(Optional.ofNullable(customerKnNm).orElse(""));
  }

}
