package com.daitoj.tkms.modules.apir0010.service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.Instant;
import java.util.List;

/** 削除対象のデータが従業員情報で使用されているか確認 */
@lombok.Getter
@lombok.Setter
@Schema(name = "R0010S04Dto", description = "削除対象のデータが従業員情報で使用されているか確認")
public class R0010S04Dto {

  /** 役職コード */
  @Schema(name = "positionCd", description = "役職コード")
  private String positionCd;

  /** 役職名 */
  @Schema(name = "positionNm", description = "役職名(表示のみ)")
  private String positionNm;

  /** 使用中フラグ */
  @Schema(name = "flg", description = "使用中フラグ")
  private String flg;

  @Schema(name = "regTs")
  protected Instant regTs;

  @Schema(name = "regUserId")
  protected String regUserId;

  /** コンストラクタ */
  public R0010S04Dto() {}

  /**
   * コンストラクタ
   *
   * @param positionCd 役職コード
   * @param positionNm 役職名
   * @param flg 使用中フラグ
   */
  public R0010S04Dto(
      String positionCd, String positionNm, String flg, Instant regTs, String regUserId) {
    this.positionCd = positionCd;
    this.positionNm = positionNm;
    this.flg = flg;
    this.regTs = regTs;
    this.regUserId = regUserId;
  }
}
