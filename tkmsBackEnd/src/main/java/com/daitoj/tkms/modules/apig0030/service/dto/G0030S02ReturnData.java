package com.daitoj.tkms.modules.apig0030.service.dto;

import com.daitoj.tkms.domain.TExecBgtDtl;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

/** 実行予算情報結果. */
@lombok.Getter
@lombok.Setter
@Schema(name = "G0030S02ReturnData", description = "実行予算情報結果")
public class G0030S02ReturnData {

  /** 実行予算ヘッダID. */
  @Schema(name = "id", description = "実行予算ヘッダID")
  protected Long id;

  /** 現場コード. */
  @NotNull
  @Schema(name = "constrSiteCd", description = "現場コード")
  protected String constrSiteCd;

  /** 現場名. */
  @Schema(name = "constrSiteNm", description = "現場名")
  protected String constrSiteNm;

  /** 現場カナ名. */
  @Schema(name = "constrSiteKnNm", description = "現場カナ名")
  protected String constrSiteKnNm;

  /** 実行予算コード. */
  @NotNull
  @Schema(name = "execBgtCd", description = "実行予算コード")
  protected String execBgtCd;

  /** 予算申請日. */
  @Schema(name = "bgtYmReqDt", description = "予算申請日")
  protected String bgtYmReqDt;

  /** 予算承認日. */
  @Schema(name = "bgtApprDt", description = "予算承認日")
  protected String bgtApprDt;

  /** 予算作成部門ID. */
  @Schema(name = "bgtCreateOrgId", description = "予算作成部門ID")
  protected Long bgtCreateOrgId;

  /** 予算作成者コード. */
  @Schema(name = "bgtCreatePicCd", description = "予算作成者コード")
  protected String bgtCreatePicCd;

  /** 予算作成者. */
  @Schema(name = "bgtCreatePicNm", description = "予算作成者")
  protected String bgtCreatePicNm;

  /** 建築面積（㎡）. */
  @Schema(name = "buildingArea", description = "建築面積（㎡）")
  protected BigDecimal buildingArea;

  /** 延床面積（㎡）. */
  @Schema(name = "grossFloorArea", description = "延床面積（㎡）")
  protected BigDecimal grossFloorArea;

  /** 施工床面積（㎡）. */
  @Schema(name = "buildupArea", description = "施工床面積（㎡）")
  protected BigDecimal buildupArea;

  /** 構造区分. */
  @Schema(name = "structureK", description = "構造区分")
  protected String structureK;

  /** 現場着手日. */
  @Schema(name = "constrSiteStartYmd", description = "現場着手日")
  protected String constrSiteStartYmd;

  /** 現場引渡日. */
  @Schema(name = "constrSiteDeliveryYmd", description = "現場引渡日")
  protected String constrSiteDeliveryYmd;

  /** メモ. */
  @Schema(name = "memo", description = "メモ")
  protected String memo;

  /** 更新日時. */
  @Schema(name = "updTs", description = "更新日時")
  protected Instant updTs;

  /** 実行予算明細情報リスト. */
  @Schema(name = "execBgtDtlList", description = "実行予算明細情報リスト")
  protected List<ExecBgtDtlDto> execBgtDtlList;

  /** 実行予算公共明細情報リスト. */
  @Schema(name = "execBgtDtlList", description = "実行予算公共明細情報リスト")
  protected List<ExecBgtPubDtlDto> execBgtPubDtlList;

  /** コンストラクタ. */
  public G0030S02ReturnData() {}

  /**
   * コンストラクタ.
   *
   * @param id 実行予算ヘッダId
   * @param constrSiteCd 現場コード
   * @param constrSiteNm 現場名
   * @param constrSiteKnNm 現場カナ名
   * @param execBgtCd 実行予算コード
   * @param bgtYmReqDt 予算申請日
   * @param bgtApprDt 予算承認日
   * @param bgtCreateOrgId 予算作成部門ID
   * @param bgtCreatePicCd 予算作成者コード
   * @param bgtCreatePicNm 予算作成者
   * @param buildingArea 建築面積（㎡）
   * @param grossFloorArea 延床面積（㎡）
   * @param buildupArea 施工床面積（㎡）
   * @param structureK 構造区分
   * @param constrSiteStartYmd 現場着手日
   * @param constrSiteDeliveryYmd 現場引渡日
   * @param memo メモ
   * @param updTs 更新日時
   */
  public G0030S02ReturnData(
      Long id,
      String constrSiteCd,
      String constrSiteNm,
      String constrSiteKnNm,
      String execBgtCd,
      String bgtYmReqDt,
      String bgtApprDt,
      Long bgtCreateOrgId,
      String bgtCreatePicCd,
      String bgtCreatePicNm,
      BigDecimal buildingArea,
      BigDecimal grossFloorArea,
      BigDecimal buildupArea,
      String structureK,
      String constrSiteStartYmd,
      String constrSiteDeliveryYmd,
      String memo,
      Instant updTs) {
    this.id = id;
    this.constrSiteCd = constrSiteCd;
    this.constrSiteNm = constrSiteNm;
    this.constrSiteKnNm = constrSiteKnNm;
    this.execBgtCd = execBgtCd;
    this.bgtYmReqDt = bgtYmReqDt;
    this.bgtApprDt = bgtApprDt;
    this.bgtCreateOrgId = bgtCreateOrgId;
    this.bgtCreatePicCd = bgtCreatePicCd;
    this.bgtCreatePicNm = bgtCreatePicNm;
    this.buildingArea = buildingArea;
    this.grossFloorArea = grossFloorArea;
    this.buildupArea = buildupArea;
    this.structureK = structureK;
    this.constrSiteStartYmd = constrSiteStartYmd;
    this.constrSiteDeliveryYmd = constrSiteDeliveryYmd;
    this.memo = memo;
    this.updTs = updTs;
  }
}
