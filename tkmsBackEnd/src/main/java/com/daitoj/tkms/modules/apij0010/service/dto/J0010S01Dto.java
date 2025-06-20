package com.daitoj.tkms.modules.apij0010.service.dto;

import com.daitoj.tkms.modules.common.service.dto.BasePageRequest;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

/** 連携用支払データ作成（査定）パラメータ */
@lombok.Getter
@lombok.Setter
@Schema(name = "C0010S01Dto", description = "連携用支払データ作成（査定）パラメータ")
public class J0010S01Dto {

  /** 査定年月 */
  @Schema(name = "assessYm", description = "査定年月")
  private String assessYm;

  @Schema(name = "partnerVendorCd", description = "協力業者コード")
  private String partnerVendorCd;

  @Schema(name = "compNm", description = "協力業者名")
  private String compNm;

  @Schema(name = "constrSiteCd", description = "現場コード")
  private String constrSiteCd;

  @Schema(name = "constrSiteNm", description = "現場名")
  private String constrSiteNm;

  @Schema(name = "assessPaymentDStart", description = "支払日")
  private String assessPaymentDStart;

  @Schema(name = "assessPaymentDStartEnd", description = "支払日")
  private String assessPaymentDStartEnd;

  /** 支払データ作成フラグ */
  @Schema(name = "paymentDataCreateFlg", description = "支払データ作成フラグ")
  private String paymentDataCreateFlg;

  /** 処理区分 */
  @NotNull
  @Size(max = 1)
  @Schema(description = "処理区分", requiredMode = Schema.RequiredMode.REQUIRED, maxLength = 1)
  private String shorikubun;
}
