package com.daitoj.tkms.modules.common.service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.math.BigDecimal;

/** 業者情報情報 */
@lombok.Getter
@lombok.Setter
@Schema(name = "common.VendorDto", description = "業者情報情報")
public class VendorDto {

  /** 業者ID */
  @Schema(description = "業者ID")
  private Long id;

  /** 業者コード */
  @Schema(description = "業者コード")
  private String vendorCd;

  /** 履番 */
  @Schema(description = "履番")
  private String hisNo;

  /** 取引停止区分 */
  @Schema(description = "取引停止区分")
  private String tradingStopK;

  /** 会社名 */
  @Schema(description = "会社名")
  private String compNm;

  /** 会社カナ名 */
  @Schema(description = "会社カナ名")
  private String compKnNm;

  /** 支店コード */
  @Schema(description = "支店コード")
  private String branchCd;

  /** 支店名 */
  @Schema(description = "支店名")
  private String branchNm;

  /** 支店カナ名 */
  @Schema(description = "支店カナ名")
  private String branchKnNm;

  /** 代表者名 */
  @Schema(description = "代表者名")
  private String ceoNm;

  /** 代表者カナ名 */
  @Schema(description = "代表者カナ名")
  private String ceoKnNm;

  /** メールアドレス */
  @Schema(description = "メールアドレス")
  private String mailAddress;

  /** 郵便番号 */
  @Schema(description = "郵便番号")
  private String postNo;

  /** 初回登録者コード */
  @Schema(description = "初回登録者コード")
  private String initialRegUserCd;

  /** 業者住所1 */
  @Schema(description = "業者住所1")
  private String vendorAddr1;

  /** 業者住所2 */
  @Schema(description = "業者住所2")
  private String vendorAddr2;

  /** 業者電話番号 */
  @Schema(description = "業者電話番号")
  private String vendorTelNo;

  /** 業者FAX番号 */
  @Schema(description = "業者FAX番号")
  private String vendorFaxNo;

  /** 銀行コード */
  @Schema(description = "銀行コード")
  private String bankCd;

  /** 銀行支店名 */
  @Schema(description = "銀行支店名")
  private String bankBranchNm;

  /** 預金種別;1：普通預金 */
  @Schema(description = "預金種別")
  private String depositType;

  /** 口座番号 */
  @Schema(description = "口座番号")
  private String bankAccountNo;

  /** 口座名義 */
  @Schema(description = "口座名義")
  private String bankAccountHolderNm;

  /** 口座名義カナ */
  @Schema(description = "口座名義カナ")
  private String bankAccountHolderKnNm;

  /** 会社設立日 */
  @Schema(description = "会社設立日")
  private String compFoundDt;

  /** 企業区分 */
  @Schema(description = "企業区分")
  private String compK;

  /** 資本金 */
  @Schema(description = "資本金")
  private String capital;

  /** インボイス認定番号 */
  @Schema(description = "インボイス認定番号")
  private String invoiceRegNo;

  /** 事務所番号 */
  @Schema(description = "事務所番号")
  private String officeNo;

  /** 保険率 */
  @Schema(description = "保険率")
  private BigDecimal insuranceRate;

  /** 従業員数 */
  @Schema(description = "従業員数")
  private String employeeNumber;

  /** 管理職人数 */
  @Schema(description = "管理職人数")
  private BigDecimal mgrNop;

  /** 技術職人数 */
  @Schema(description = "技術職人数")
  private BigDecimal engineerNop;

  /** 事務職人数 */
  @Schema(description = "事務職人数")
  private BigDecimal clerkNop;

  /** 沿革 */
  @Schema(description = "沿革")
  private String compHis;

  /** 解体登録フラグ */
  @Schema(description = "解体登録フラグ")
  private String demolPermFlg;

  /** 解体登録許可都道府県 */
  @Schema(description = "解体登録許可都道府県")
  private String demolPermPref;

  /** 解体登録許可番号1 */
  @Schema(description = "解体登録許可番号1")
  private String demolPermNo1;

  /** 解体登録許可番号2 */
  @Schema(description = "解体登録許可番号2")
  private String demolPermNo2;

  /** 解体登録許可日・認定日 */
  @Schema(description = "解体登録許可日・認定日")
  private String demolPermDt;

  /** 警備認定フラグ */
  @Schema(description = "警備認定フラグ")
  private String securityCertFlg;

  /** 警備認定都道府県 */
  @Schema(description = "警備認定都道府県")
  private String securityCertPref;

  /** 警備認定番号 */
  @Schema(description = "警備認定番号")
  private String securityCertNo;

  /** 警備認定許可日・認定日 */
  @Schema(description = "警備認定許可日・認定日")
  private String securityCertDt;

  /** CCUS事業所ID */
  @Schema(description = "CCUS事業所ID")
  private String ccusOfficeId;
}
