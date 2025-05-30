package com.daitoj.tkms.modules.apio0010.service.dto;

import com.daitoj.tkms.modules.common.service.dto.BasePageRequest;
import io.swagger.v3.oas.annotations.media.Schema;

/** 業者一覧パラメータ. */
@lombok.Getter
@lombok.Setter
@Schema(name = "O0010S01Dto", description = "業者一覧パラメータ")
public class O0010S01Dto extends BasePageRequest {

  /* 業者コード */
  @Schema(name = "vendorCd", description = "業者コード")
  private String vendorCd;

  /* 業者名 */
  @Schema(name = "compNm", description = "業者名")
  private String compNm;

  /* 支店コード */
  @Schema(name = "branchCd", description = "支店コード")
  private String branchCd;

  /* 産廃許可期限 */
  @Schema(name = "iwPermDt", description = "産廃許可期限")
  private String iwPermDt;

  /* 業種 */
  @Schema(name = "jobType", description = "業種")
  private String jobType;

  /* 業種名 */
  @Schema(name = "jobTypeNm", description = "業種名")
  private String jobTypeNm;

  /* 解体登録 */
  @Schema(name = "demolPerm", description = "解体登録")
  private String demolPerm;

  /* 警備認定 */
  @Schema(name = "securityCert", description = "警備認定")
  private String securityCert;

  /* 産廃許可 */
  @Schema(name = "iwPerm", description = "産廃許可")
  private String iwPerm;

}
