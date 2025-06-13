package com.daitoj.tkms.modules.apin0010.service.dto;

import com.daitoj.tkms.modules.common.service.dto.BasePageRequest;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

/** 工事予実一覧パラメータ. */
@lombok.Getter
@lombok.Setter
@Schema(name = "N0010S01Dto", description = "工事予実一覧パラメータ")
public class N0010S01Dto extends BasePageRequest {

  /** 所属事業所コード. */
  @NotNull
  @Schema(name = "belongOfficeCd", description = "所属事業所コード")
  private String belongOfficeCd;

  /** 現場コード. */
  @Schema(name = "constrSiteCd", description = "現場コード")
  private String constrSiteCd;

  /** 現場名. */
  @Schema(name = "constrSiteNm", description = "現場名")
  private String constrSiteNm;

  /** 現場名カナ. */
  @Schema(name = "constrSiteKnNm", description = "現場名カナ")
  private String constrSiteKnNm;

  /** 業者名. */
  @Schema(name = "compNm", description = "業者名")
  private String compNm;

  /** 業者名カナ. */
  @Schema(name = "compKnNm", description = "業者名カナ")
  private String compKnNm;

  /** 予実作成日FROM. */
  @Schema(name = "wbsCreateDtFrom", description = "予実作成日FROM")
  private String wbsCreateDtFrom;

  /** 予実作成日To. */
  @Schema(name = "wbsCreateDtTo", description = "予実作成日To")
  private String wbsCreateDtTo;

  /** 現場着手日FROM. */
  @Schema(name = "constrSiteStartYmdFrom", description = "現場着手日FROM")
  private String constrSiteStartYmdFrom;

  /** 現場着手日TO. */
  @Schema(name = "constrSiteStartYmdTo", description = "現場着手日TO")
  private String constrSiteStartYmdTo;

  /** 現場引渡日FROM. */
  @Schema(name = "constrSiteDeliveryYmdFrom", description = "現場引渡日FROM")
  private String constrSiteDeliveryYmdFrom;

  /** 現場引渡日TO. */
  @Schema(name = "constrSiteDeliveryYmdTo", description = "現場引渡日TO")
  private String constrSiteDeliveryYmdTo;

  /** 入力担当者. */
  @Schema(name = "empNm", description = "入力担当者")
  private String empNm;

}
