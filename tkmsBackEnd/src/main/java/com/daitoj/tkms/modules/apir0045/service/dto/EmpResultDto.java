package com.daitoj.tkms.modules.apir0045.service.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;

/** 従業員結果情報 */
@lombok.Getter
@lombok.Setter
@Schema(name = "EmpResultDto", description = "従業員結果情報")
public class EmpResultDto extends EmpDto {

  /** 生年月日(YYYY)" */
  @JsonProperty("birthYear")
  @Schema(description = "生年月日(YYYY)(表示のみ)")
  public String getBirthYear() {
    return birthDate != null && birthDate.length() >= 4 ? birthDate.substring(0, 4) : null;
  }

  /** 生年月日(MM) */
  @JsonProperty("birthMonth")
  @Schema(description = "生年月日(MM)(表示のみ)")
  public String getBirthMonth() {
    return birthDate != null && birthDate.length() >= 6 ? birthDate.substring(4, 6) : null;
  }

  /** 生年月日(DD) */
  @JsonProperty("birthDay")
  @Schema(description = "生年月日(DD)(表示のみ)")
  public String getBirthDay() {
    return birthDate != null && birthDate.length() >= 8 ? birthDate.substring(6, 8) : null;
  }

  /** 入社年月日(YYYY) */
  @JsonProperty("employmentYear")
  @Schema(description = "入社年月日(YYYY)(表示のみ)")
  public String getemploymentYear() {
    return employmentYmd != null && employmentYmd.length() >= 4
        ? employmentYmd.substring(0, 4)
        : null;
  }

  /** 入社年月日(MM) */
  @JsonProperty("employmentMonth")
  @Schema(description = "入社年月日(MM)(表示のみ)")
  public String getemploymentMonth() {
    return employmentYmd != null && employmentYmd.length() >= 6
        ? employmentYmd.substring(4, 6)
        : null;
  }

  /** 入社年月日(DD) */
  @JsonProperty("employmentDay")
  @Schema(description = "入社年月日(DD)(表示のみ)")
  public String getemploymentDay() {
    return employmentYmd != null && employmentYmd.length() >= 8
        ? employmentYmd.substring(6, 8)
        : null;
  }

  /** 退職年月日(YYYY) */
  @JsonProperty("terminationYear")
  @Schema(description = "退職年月日(YYYY)(表示のみ)")
  public String getterminationYear() {
    return terminationYmd != null && terminationYmd.length() >= 4
        ? terminationYmd.substring(0, 4)
        : null;
  }

  /** 退職年月日(MM) */
  @JsonProperty("terminationMonth")
  @Schema(description = "退職年月日(MM)(表示のみ)")
  public String getterminationMonth() {
    return terminationYmd != null && terminationYmd.length() >= 6
        ? terminationYmd.substring(4, 6)
        : null;
  }

  /** 退職年月日(DD) */
  @JsonProperty("terminationDay")
  @Schema(description = "退職年月日(DD)(表示のみ)")
  public String getterminationDay() {
    return terminationYmd != null && terminationYmd.length() >= 8
        ? terminationYmd.substring(6, 8)
        : null;
  }

  /** 会社電話番号1 */
  @Schema(description = "会社電話番号1(表示のみ)")
  @JsonProperty("compPhoneNo1")
  public String getCompanyMobileNumber1() {
    return compPhoneNo != null && compPhoneNo.length() >= 3 ? compPhoneNo.substring(0, 3) : null;
  }

  /** 会社電話番号2 */
  @Schema(description = "会社電話番号2(表示のみ)")
  @JsonProperty("compPhoneNo2")
  public String getCompanyMobileNumber2() {
    return compPhoneNo != null && compPhoneNo.length() >= 7 ? compPhoneNo.substring(3, 7) : null;
  }

  /** 会社電話番号3 */
  @Schema(description = "会社電話番号3(表示のみ)")
  @JsonProperty("compPhoneNo3")
  public String getCompanyMobileNumber3() {
    return compPhoneNo != null && compPhoneNo.length() >= 11 ? compPhoneNo.substring(7, 11) : null;
  }

  /** 郵便番号1 */
  @Schema(description = "郵便番号1(表示のみ)")
  @JsonProperty("empPostNo1")
  public String getEmpPostNo1() {
    return empPostNo != null && empPostNo.length() >= 3 ? empPostNo.substring(0, 3) : null;
  }

  /** 郵便番号2 */
  @Schema(description = "郵便番号2(表示のみ)")
  @JsonProperty("empPostNo2")
  public String getEmpPostNo2() {
    return empPostNo != null && empPostNo.length() >= 7 ? empPostNo.substring(3, 7) : null;
  }

  /** 個人携帯番号1 */
  @Schema(description = "個人携帯番号1(表示のみ)")
  @JsonProperty("idlPhoneNo1")
  public String getIdlPhoneNo1() {
    return idlPhoneNo != null && idlPhoneNo.length() >= 3 ? idlPhoneNo.substring(0, 3) : null;
  }

  /** 個人携帯番号2 */
  @Schema(description = "個人携帯番号2(表示のみ)")
  @JsonProperty("idlPhoneNo2")
  public String getIdlPhoneNo2() {
    return idlPhoneNo != null && idlPhoneNo.length() >= 7 ? idlPhoneNo.substring(3, 7) : null;
  }

  /** 個人携帯番号3 */
  @Schema(description = "個人携帯番号3(表示のみ)")
  @JsonProperty("idlPhoneNo3")
  public String getIdlPhoneNo3() {
    return idlPhoneNo != null && idlPhoneNo.length() >= 11 ? idlPhoneNo.substring(7, 11) : null;
  }

  /** 自宅電話番号1 */
  @Schema(description = "自宅電話番号1(表示のみ)")
  @JsonProperty("homeTelNo1")
  public String getHomeTelNo1() {
    return homeTelNo != null && homeTelNo.length() >= 3 ? homeTelNo.substring(0, 3) : null;
  }

  /** 自宅電話番号2 */
  @Schema(description = "自宅電話番号2(表示のみ)")
  @JsonProperty("homeTelNo2")
  public String getHomeTelNo2() {
    return homeTelNo != null && homeTelNo.length() >= 7 ? homeTelNo.substring(3, 7) : null;
  }

  /** 自宅電話番号3 */
  @Schema(description = "自宅電話番号3(表示のみ)")
  @JsonProperty("homeTelNo3")
  public String getHomeTelNo3() {
    return homeTelNo != null && homeTelNo.length() >= 11 ? homeTelNo.substring(7, 11) : null;
  }

  /** 緊急連絡先電話番号1 */
  @Schema(description = "緊急連絡先電話番号1(表示のみ)")
  @JsonProperty("ecnTelNo1")
  public String getEcnTelNo1() {
    return ecnTelNo != null && ecnTelNo.length() >= 3 ? ecnTelNo.substring(0, 3) : null;
  }

  /** 緊急連絡先電話番号2 */
  @Schema(description = "緊急連絡先電話番号2(表示のみ)")
  @JsonProperty("ecnTelNo2")
  public String getEcnTelNo2() {
    return ecnTelNo != null && ecnTelNo.length() >= 7 ? ecnTelNo.substring(3, 7) : null;
  }

  /** 緊急連絡先電話番号3 */
  @Schema(description = "緊急連絡先電話番号3(表示のみ)")
  @JsonProperty("ecnTelNo3")
  public String getEcnTelNo3() {
    return ecnTelNo != null && ecnTelNo.length() >= 11 ? ecnTelNo.substring(7, 11) : null;
  }
}
