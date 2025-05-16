package com.daitoj.tkms.modules.common.service.dto;

import io.swagger.v3.oas.annotations.media.Schema;

/** 作業員情報 */
@lombok.Getter
@lombok.Setter
@Schema(name = "common.WorkerDto", description = "作業員情報")
public class WorkerDto {

  /** 作業員ID */
  @Schema(description = "作業員ID")
  private Long id;

  /** 業者ID */
  @Schema(description = "業者ID")
  private VendorDto vendor;

  /** メールアドレス */
  @Schema(description = "メールアドレス")
  private String mailAddress;

  /** 作業員コード */
  @Schema(description = "作業員コード")
  private String workerCd;

  /** 歴番（01から連番） */
  @Schema(description = "歴番")
  private String hisNo;

  /** 作業員氏名 */
  @Schema(description = "作業員氏名")
  private String workerNm;

  /** 作業員カナ氏名 */
  @Schema(description = "作業員カナ氏名")
  private String workerKnNm;

  /** 雇入年月日 */
  @Schema(description = "雇入年月日")
  private String employYmd;

  /** 職種コード */
  @Schema(description = "職種コード")
  private String jobTypeCd;

  /** 生年月日 */
  @Schema(description = "生年月日")
  private String birthDate;

  /** 性別 */
  @Schema(description = "性別")
  private String sex;

  /** 血液型区分（1：A型（Rh+）） */
  @Schema(description = "血液型区分")
  private String bloodTypeK;

  /** CCUS認定番号 */
  @Schema(description = "CCUS認定番号")
  private String ccusCertNo;

  /** 外国籍区分（1：なし） */
  @Schema(description = "外国籍区分")
  private String nonJapaneseK;

  /** 国籍 */
  @Schema(description = "国籍")
  private String nationality;

  /** 作業員電話番号 */
  @Schema(description = "作業員電話番号")
  private String workerTelNo;

  /** 作業員郵便番号 */
  @Schema(description = "作業員郵便番号")
  private String workerPostNo;

  /** 作業員住所1 */
  @Schema(description = "作業員住所1")
  private String workerAddr1;

  /** 作業員住所2 */
  @Schema(description = "作業員住所2")
  private String workerAddr2;

  /** 被災時連絡先氏名 */
  @Schema(description = "被災時連絡先氏名")
  private String ecpNm;

  /** 被災時連絡先続柄 */
  @Schema(description = "被災時連絡先続柄")
  private String ecpRel;

  /** 被災時連絡先住所 */
  @Schema(description = "被災時連絡先住所")
  private String ecpAddr;

  /** 被災時連絡先電話番号 */
  @Schema(description = "被災時連絡先電話番号")
  private String ecpTelNo;

  /** 健康診断日 */
  @Schema(description = "健康診断日")
  private String healthCheckupDay;

  /** 最低血圧 */
  @Schema(description = "最低血圧")
  private Integer dbp;

  /** 最高血圧 */
  @Schema(description = "最高血圧")
  private Integer sbp;

  /** 特殊健康診断日 */
  @Schema(description = "特殊健康診断日")
  private String spHealthCheckupDay;

  /** 特殊診断種類 */
  @Schema(description = "特殊診断種類")
  private String spHealthCheckupType;

  /** 特別教育コード */
  @Schema(description = "特別教育コード")
  private String sneCd;

  /** 技能講習コード */
  @Schema(description = "技能講習コード")
  private String sktCd;

  /** 資格免許コード */
  @Schema(description = "資格免許コード")
  private String certCd;

  /** 雇用区分コード */
  @Schema(description = "雇用区分コード")
  private String employK;

  /** 雇入教育実施日 */
  @Schema(description = "雇入教育実施日")
  private String employEduYmd;

  /** 職長教育実施年月日 */
  @Schema(description = "職長教育実施年月日")
  private String foremanEduYmd;

  /** 主任技術者コード */
  @Schema(description = "主任技術者コード")
  private String chiefEngineerCd;

  /** 労災特別番号 */
  @Schema(description = "労災特別番号")
  private String wciSpecNo;

  /** 健康保険種類区分（1：国民健康保険） */
  @Schema(description = "健康保険種類区分")
  private String ehiTypeK;

  /** 健康保険番号 */
  @Schema(description = "健康保険番号")
  private String ehiNo;

  /** 健康保険除外理由区分（1：個人事業主） */
  @Schema(description = "健康保険除外理由区分")
  private String ehiDeselectReasonK;

  /** 厚生年金種類区分（1：厚生年金基金） */
  @Schema(description = "厚生年金種類区分")
  private String wpiTypeK;

  /** 厚生年金番号 */
  @Schema(description = "厚生年金番号")
  private String wpiNo;

  /** 厚生年金除外理由コード（1：個人事業主） */
  @Schema(description = "厚生年金除外理由コード")
  private String wpiDeselectReasonK;

  /** 雇用保険種類区分（1：加入） */
  @Schema(description = "雇用保険種類区分")
  private String eisTypeK;

  /** 雇用保険番号 */
  @Schema(description = "雇用保険番号")
  private String eisNo;

  /** 雇用保険除外理由区分（1：事業主） */
  @Schema(description = "雇用保険除外理由区分")
  private String eisDeselectReasonK;
}
