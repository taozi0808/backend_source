package com.daitoj.tkms.modules.apij0020.service.dto;

import com.daitoj.tkms.modules.common.service.dto.BasePageRequest;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

/** 連携用支払データ作成（査定）パラメータ */
@lombok.Getter
@lombok.Setter
@Schema(name = "J0010S01Dto", description = "連携用支払データ作成（査定）パラメータ")
public class J0020S01Dto {

  @Schema(name = "constrSiteCd", description = "現場コード")
  private String constrSiteCd;

  @Schema(name = "constrSiteNm", description = "現場名")
  private String constrSiteNm;

  @Schema(name = "assessPaymentDStart", description = "支払日（開始）")
  private String assessPaymentDStart;

  @Schema(name = "assessPaymentDEnd", description = "支払日（終了）")
  private String assessPaymentDEnd;

  /** 支払データ作成フラグ */
  @Schema(name = "paymentDataCreateFlg", description = "支払データ作成フラグ")
  private String paymentDataCreateFlg;

  /** 処理区分 */
  @NotNull
  @Size(max = 1)
  @Schema(description = "処理区分", requiredMode = Schema.RequiredMode.REQUIRED, maxLength = 1)
  private String shorikubun;
}
