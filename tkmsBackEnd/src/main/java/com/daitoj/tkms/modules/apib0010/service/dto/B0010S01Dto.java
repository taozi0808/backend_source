package com.daitoj.tkms.modules.apib0010.service.dto;

import com.daitoj.tkms.modules.common.service.dto.BasePageRequest;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;

/** 案件一覧初期パラメータ */
@lombok.Getter
@lombok.Setter
@Schema(name = "B0010S01Dto", description = "案件一覧初期パラメータ")
public class B0010S01Dto extends BasePageRequest {

  /** 案件コード */
  @Schema(name = "ankenCode", description = "案件コード")
  @Size(max = 9)
  private String ankenCode;

  /** 案件名（ｶﾅ含む） */
  @Schema(name = "ankenName", description = "案件名（ｶﾅ含む）")
  @Size(max = 255)
  private String ankenName;

  /** 顧客名（ｶﾅ含む） */
  @Schema(name = "kokyakuName", description = "顧客名（ｶﾅ含む）")
  @Size(max = 255)
  private String kokyakuName;

  /** 受注見込日（開始） */
  @Schema(name = "jmYmdStart", description = "受注見込日（開始）(yyyyMMdd)", example = "20250102")
  private String jmYmdStart;

  /** 受注見込日（終了） */
  @Schema(name = "jmYmdEnd", description = "受注見込日（終了）(yyyyMMdd)", example = "20250102")
  private String jmYmdEnd;

  /** 営業部門 */
  @Schema(name = "eigyouBumon", description = "営業部門")
  @Size(max = 255)
  private String eigyouBumon;

  /** 営業担当者 */
  @Schema(name = "eigyouTantousya", description = "営業担当者")
  @Size(max = 255)
  private String eigyouTantousya;
}
