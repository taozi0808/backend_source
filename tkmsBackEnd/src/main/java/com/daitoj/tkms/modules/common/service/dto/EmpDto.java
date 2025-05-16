package com.daitoj.tkms.modules.common.service.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;

/** 従業員情報 */
@lombok.Getter
@lombok.Setter
@Schema(name = "common.EmpDto", description = "従業員情報")
public class EmpDto {

  /** 従業員コード */
  @Schema(description = "従業員コード")
  private String empCd;

  /** 従業員氏 */
  @Schema(description = "従業員氏")
  private String empFamNm;

  /** 従業員名 */
  @Schema(description = "従業員名")
  private String empPerNm;

  /** 従業員氏名 */
  @Schema(description = "従業員氏名")
  private String empNm;

  /** 職種コード */
  @Schema(description = "職種コード")
  private String jobTypeCd;

  /** 生年月日 */
  @Schema(description = "生年月日")
  private String birthDate;

  /** 性別 */
  @Schema(description = "性別")
  private String sex;

  /** 入社年月日 */
  @Schema(description = "入社年月日")
  private String employmentYmd;

  /** 退職年月日 */
  @Schema(description = "退職年月日")
  private String terminationYmd;

  /** メールアドレス */
  @Schema(description = "メールアドレス")
  private String mailAddress;

  /** 会社電話番号 */
  @Schema(description = "会社電話番号")
  private String compPhoneNo;

  /** 所属事業所コード */
  @Schema(description = "所属事業所コード")
  private String belongOfficeCd;

  /** 役職コード */
  @Schema(description = "役職コード")
  @JsonProperty("positionInfo")
  private PositionDto positionCd;

  /** 郵便番号 */
  @Schema(description = "郵便番号")
  private String empPostNo;

  /** 住所1 */
  @Schema(description = "住所1")
  private String empAddr1;

  /** 住所2 */
  @Schema(description = "住所2")
  private String empAddr2;

  /** 個人携帯番号 */
  @Schema(description = "個人携帯番号")
  private String idlPhoneNo;

  /** 自宅電話番号 */
  @Schema(description = "自宅電話番号")
  private String homeTelNo;

  /** 緊急連絡先氏名 */
  @Schema(description = "緊急連絡先氏名")
  private String ecnNm;

  /** 緊急連絡先続柄 */
  @Schema(description = "緊急連絡先続柄")
  private String ecnRel;

  /** 緊急連絡先住所 */
  @Schema(description = "緊急連絡先住所")
  private String ecnAddr;

  /** 緊急連絡先電話番号 */
  @Schema(description = "緊急連絡先電話番号")
  private String ecnTelNo;
}
