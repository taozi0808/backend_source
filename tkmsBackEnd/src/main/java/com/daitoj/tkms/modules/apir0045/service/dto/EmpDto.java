package com.daitoj.tkms.modules.apir0045.service.dto;

import com.daitoj.tkms.modules.common.service.dto.BaseDto;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.List;

/** 従業員情報 */
@lombok.Getter
@lombok.Setter
@Schema(name = "EmpDto", description = "従業員情報")
public class EmpDto extends BaseDto {

  /** 従業員ID(登録の場合、nullを渡す) */
  @Schema(description = "従業員ID(登録の場合、nullを渡す)")
  protected Long id;

  /** 従業員コード */
  @Size(max = 6)
  @Schema(description = "従業員コード", maxLength = 6)
  protected String empCd;

  /** 従業員氏 */
  @NotNull
  @Size(max = Integer.MAX_VALUE)
  @Schema(
      description = "従業員氏",
      maxLength = Integer.MAX_VALUE,
      requiredMode = Schema.RequiredMode.REQUIRED)
  protected String empFamNm;

  /** 従業員名 */
  @NotNull
  @Size(max = Integer.MAX_VALUE)
  @Schema(
      description = "従業員名",
      maxLength = Integer.MAX_VALUE,
      requiredMode = Schema.RequiredMode.REQUIRED)
  protected String empPerNm;

  /** 従業員氏名 */
  @NotNull
  @Size(max = Integer.MAX_VALUE)
  @Schema(
      description = "従業員氏名",
      maxLength = Integer.MAX_VALUE,
      requiredMode = Schema.RequiredMode.REQUIRED)
  protected String empNm;

  /** 従業員カナ氏名 */
  @NotNull
  @Size(max = Integer.MAX_VALUE)
  @Schema(
      description = "従業員カナ氏名",
      maxLength = Integer.MAX_VALUE,
      requiredMode = Schema.RequiredMode.REQUIRED)
  protected String empKnNm;

  /** 職種 */
  @Size(max = 2)
  @NotNull
  @Schema(description = "職種", maxLength = 2, requiredMode = Schema.RequiredMode.REQUIRED)
  protected String empJobTypeCd;

  /** 職種名 */
  @Schema(description = "職種名(表示のみ)")
  protected String empJobTypeName;

  /** 生年月日 */
  @Size(max = 8)
  @NotNull
  @Schema(description = "生年月日", maxLength = 8, requiredMode = Schema.RequiredMode.REQUIRED)
  protected String birthDate;

  /** 性別 */
  @NotNull
  @Schema(description = "性別", requiredMode = Schema.RequiredMode.REQUIRED)
  protected String sex;

  /** 性別文字 */
  @Schema(description = "性別文字(表示のみ)")
  protected String sexName;

  /** 入社年月日 */
  @Size(max = 8)
  @NotNull
  @Schema(description = "入社年月日", maxLength = 8, requiredMode = Schema.RequiredMode.REQUIRED)
  protected String employmentYmd;

  /** 退職年月日 */
  @Size(max = 8)
  @Schema(description = "退職年月日", maxLength = 8)
  protected String terminationYmd;

  /** 資格情報 */
  @Valid
  @Schema(description = "資格情報")
  protected List<EmpCertDto> empCertList;

  /** メールアドレス */
  @Size(max = 255)
  @NotNull
  @Schema(description = "メールアドレス", maxLength = 255, requiredMode = Schema.RequiredMode.REQUIRED)
  protected String mailAddress;

  /** 会社電話番号 */
  @Size(max = Integer.MAX_VALUE)
  @Schema(description = "会社電話番号", maxLength = Integer.MAX_VALUE)
  protected String compPhoneNo;

  @Valid
  @Schema(description = "ログインID", requiredMode = Schema.RequiredMode.REQUIRED)
  protected LoginDto login;

  /** 所属事業所コード */
  @Valid
  @NotNull
  @Schema(description = "所属事業所コード", requiredMode = Schema.RequiredMode.REQUIRED)
  protected OfficeDto belongOfficeCd;

  /** 所属部署役職コード */
  @Valid
  @NotNull
  @Schema(description = "所属部署役職コード", requiredMode = Schema.RequiredMode.REQUIRED)
  protected PositionDto positionCd;

  /** 従業員顔写真情報 */
  @Valid
  @Schema(description = "従業員顔写真情報")
  protected List<EmpPhotoDto> empPhotoList;

  /** 従業員・組織・対照情報 */
  @Valid
  @NotNull
  @Size(min = 1)
  @Schema(description = "従業員・組織・対照情報", requiredMode = Schema.RequiredMode.REQUIRED)
  protected List<EmpOrgDto> empOrgList;

  /** 適用開始日付 */
  @Size(max = 8)
  @Schema(description = "適用開始日付", maxLength = 8)
  protected String effectiveStartDt;

  /** 部署異動役職コード */
  @Schema(description = "部署異動役職コード")
  protected String transgerPositionCd;

  /** 部署異動情報 */
  @Valid
  @Schema(description = "部署異動情報")
  protected List<EmpOrgDto> transferEmpOrgList;

  /** 郵便番号 */
  @Size(max = 7)
  @Schema(description = "郵便番号", maxLength = 7)
  protected String empPostNo;

  /** 住所1 */
  @NotNull
  @Size(max = Integer.MAX_VALUE)
  @Schema(
      description = "住所1",
      maxLength = Integer.MAX_VALUE,
      requiredMode = Schema.RequiredMode.REQUIRED)
  protected String empAddr1;

  /** 住所2 */
  @Size(max = Integer.MAX_VALUE)
  @Schema(description = "住所2", maxLength = Integer.MAX_VALUE)
  protected String empAddr2;

  /** 個人携帯番号 */
  @Size(max = Integer.MAX_VALUE)
  @Schema(description = "個人携帯番号", maxLength = Integer.MAX_VALUE)
  protected String idlPhoneNo;

  /** 自宅電話番号 */
  @Size(max = Integer.MAX_VALUE)
  @Schema(description = "自宅電話番号", maxLength = Integer.MAX_VALUE)
  protected String homeTelNo;

  /** 緊急連絡先氏名 */
  @Size(max = Integer.MAX_VALUE)
  @Schema(description = "緊急連絡先氏名", maxLength = Integer.MAX_VALUE)
  protected String ecnNm;

  /** 緊急連絡先続柄 */
  @Size(max = Integer.MAX_VALUE)
  @Schema(description = "緊急連絡先続柄", maxLength = Integer.MAX_VALUE)
  protected String ecnRel;

  /** 緊急連絡先住所 */
  @Size(max = Integer.MAX_VALUE)
  @Schema(description = "緊急連絡先住所", maxLength = Integer.MAX_VALUE)
  protected String ecnAddr;

  /** 緊急連絡先電話番号 */
  @Size(max = Integer.MAX_VALUE)
  @Schema(description = "緊急連絡先電話番号", maxLength = Integer.MAX_VALUE)
  protected String ecnTelNo;
}
