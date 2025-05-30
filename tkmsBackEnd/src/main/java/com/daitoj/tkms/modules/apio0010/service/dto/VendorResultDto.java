package com.daitoj.tkms.modules.apio0010.service.dto;

import com.daitoj.tkms.modules.common.utils.EnhancedFullWidthConverterUtils;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import org.apache.commons.lang3.StringUtils;

import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/** 業者結果情報. */
@lombok.Getter
@lombok.Setter
@Schema(name = "VendorResultDto", description = "業者結果情報")
public class VendorResultDto extends VendorInfoDto {

  /* 業者コード・業者支店コード */
  @JsonProperty("vendorCdAndBranchCd")
  @Schema(description = "業者コード・業者支店コード")
  public String getVendorCdAndBranchCd() {
    return vendorCd + "-" + branchCd;
  }

  /* 業者名・業者支店名 */
  @JsonProperty("compNmAndBranchNm")
  @Schema(description = "業者名・業者支店名")
  public String getCompNmAndBranchNm() {
    return compNm + " " + branchNm;
  }

  /* 住所 */
  @JsonProperty("vendorAddr")
  @Schema(description = "住所")
  public String getVendorAddr() {
    return vendorAddr1 + Optional.ofNullable(vendorAddr2).orElse("");
  }

  /* 業種リスト */
  @JsonProperty("jobTypeList")
  @Schema(description = "業種リスト(表示のみ)")
  public String getJobTypeList() {
    return Stream.of(jobType1, jobType2, jobType3, jobType4, jobType5)
        .filter(Objects::nonNull)
        .filter(s -> !s.isEmpty())
        .collect(Collectors.joining("、"));
  }

  /* 全角業者名 */
  @JsonProperty("fullWidthCompNm")
  @Schema(description = "全角業者名")
  public String getFullWidthCompNm() {
    return EnhancedFullWidthConverterUtils.convert(compNm);
  }

  /* 全角業者カナ名 */
  @JsonProperty("fullWidthCompKnNm")
  @Schema(description = "全角業者カナ名")
  public String getFullWidthCompKnNm() {
    return EnhancedFullWidthConverterUtils.convert(compKnNm);
  }

  /* 解体登録 */
  @JsonProperty("demolPermStr")
  @Schema(description = "解体登録(表示のみ)")
  public String getDemolPermStr() {
    return "1".equals(demolPerm) ? "有" : "無";
  }

  /* 警備認定 */
  @JsonProperty("securityCertStr")
  @Schema(description = "警備認定(表示のみ)")
  public String getSecurityCertStr() {
    return "1".equals(securityCert) ? "有" : "無";
  }

  /* 産廃許可 */
  @JsonProperty("iwPermStr")
  @Schema(description = "産廃許可(表示のみ)")
  public String getIwPermStr() {
    return "1".equals(iwPerm) ? "有" : "無";
  }

  /* 電話番号 */
  @JsonProperty("vendorTelNoFormat")
  @Schema(description = "電話番号(フォマード後)")
  public String getVendorTelNoFormat() {
    if (StringUtils.isEmpty(vendorTelNo) || vendorTelNo.length() != 11) {
      return vendorTelNo;
    }

    return vendorTelNo.replaceAll("(\\d{3})(\\d{4})(\\d{4})", "$1-$2-$3");
  }

}
