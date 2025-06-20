package com.daitoj.tkms.modules.apig0030.service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

/** 実行予算公共明細情報. */
@lombok.Getter
@lombok.Setter
@Schema(name = "ExecBgtPubDtlDto", description = "実行予算公共明細情報")
public class ExecBgtPubDtlDto {

  /** 実行予算公共明細ID. */
  @Schema(name = "id", description = "実行予算公共明細ID")
  private Long id;

  /** 大工事コード. */
  @Schema(name = "majorWorkCd", description = "大工事コード")
  private String majorWorkCd;

  /** 小工事コード. */
  @Schema(name = "minorWorkCd", description = "小工事コード")
  private String minorWorkCd;

  /** 規格. */
  @Schema(name = "spec", description = "規格")
  private String spec;

  /** 数量. */
  @Schema(name = "qty", description = "数量")
  private BigDecimal qty;

  /** 単価. */
  @Schema(name = "price", description = "単価")
  private BigDecimal price;

  /** 単位. */
  @Schema(name = "unitK", description = "単位")
  private String unitK;

  /** 実行予算金額. */
  @Schema(name = "targetBgtAmt", description = "実行予算金額")
  private BigDecimal targetBgtAmt;

  /** 更新日時. */
  @Schema(name = "updTs", description = "更新日時")
  private Instant updTs;

  /** 実行予算公共明細発注情報リスト. */
  @Schema(name = "execBgtDtlPoList", description = "実行予算明細発注情報リスト")
  private List<ExecBgtPubDtlPoDto> execBgtPubDtlPoList = new ArrayList<>();
}
