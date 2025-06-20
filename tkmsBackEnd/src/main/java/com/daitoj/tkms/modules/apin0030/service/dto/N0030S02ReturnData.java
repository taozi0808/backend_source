package com.daitoj.tkms.modules.apin0030.service.dto;

import com.daitoj.tkms.domain.TConstrWbsDtl;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.time.Instant;
import java.util.List;

/** 工事予実入力情報結果. */
@lombok.Getter
@lombok.Setter
@Schema(name = "N0030S02ReturnData", description = "工事予実入力情報結果")
public class N0030S02ReturnData {
  /** 工事予実ヘッダ. */
  @Schema(name = "constrWbsHid", description = "工事予実ヘッダID")
  protected Long id;

  /** 現場コード. */
  @NotEmpty(message = "現場コードが入力されていません。")
  @Schema(name = "constrSiteCd", description = "現場コード", requiredMode = Schema.RequiredMode.REQUIRED)
  protected String constrSiteCd;

  /** 入力担当者コード. */
  @NotEmpty(message = "入力担当者コードが入力されていません。")
  @Schema(
      name = "createPicCd",
      description = "入力担当者コード",
      requiredMode = Schema.RequiredMode.REQUIRED)
  protected String createPicCd;

  /** 入力担当者名. */
  @Schema(name = "emp_nm", description = "入力担当者名")
  protected String empNm;

  /** 予実作成日. */
  @NotNull(message = "予実作成日が入力されていません。")
  @Schema(name = "wbsCreateTs", description = "予実作成日", requiredMode = Schema.RequiredMode.REQUIRED)
  private Instant wbsCreateTs;

  /** 現場名. */
  @Schema(name = "constrSiteNm", description = "現場名")
  protected String constrSiteNm;

  /** 現場カナ名. */
  @Schema(name = "constr_site_kn_nm", description = "現場カナ名")
  protected String constrSiteKnNm;

  /** 現場着手日. */
  @Schema(name = "constrSiteStartYmd", description = "現場着手日")
  protected String constrSiteStartYmd;

  /** 現場引渡日. */
  @Schema(name = "constrSiteDeliveryYmd", description = "現場引渡日")
  protected String constrSiteDeliveryYmd;

  /** 歴番. */
  @Schema(name = "his_no", description = "歴番")
  protected Integer hisNo;

  /** 更新日時. */
  @Column(name = "upd_ts")
  protected Instant updTs;

  /** 工事予実明細リスト. */
  @Schema(description = "工事予実明細リスト")
  protected List<TConstrWbsDtl> constrWbsDtls;

  /** コンストラクタ. */
  public N0030S02ReturnData() {}

  /**
   * コンストラクタ.
   *
   * @param id 工事予実ヘッダID
   * @param constrSiteCd 現場コード
   * @param createPicCd 入力担当者コード
   * @param empNm 入力担当者名
   * @param wbsCreateTs 予実作成日
   * @param constrSiteNm 現場名
   * @param constrSiteKnNm 現場カナ名
   * @param constrSiteStartYmd 現場着手日
   * @param constrSiteDeliveryYmd 現場引渡日
   * @param hisNo 歴番
   * @param updTs 更新日時
   * @param constrWbsDtls 工事予実明細
   */
  public N0030S02ReturnData(
      Long id,
      String constrSiteCd,
      String createPicCd,
      String empNm,
      Instant wbsCreateTs,
      String constrSiteNm,
      String constrSiteKnNm,
      String constrSiteStartYmd,
      String constrSiteDeliveryYmd,
      Integer hisNo,
      Instant updTs,
      List<TConstrWbsDtl> constrWbsDtls) {
    this.id = id;
    this.constrSiteCd = constrSiteCd;
    this.createPicCd = createPicCd;
    this.empNm = empNm;
    this.wbsCreateTs = wbsCreateTs;
    this.constrSiteNm = constrSiteNm;
    this.constrSiteKnNm = constrSiteKnNm;
    this.constrSiteStartYmd = constrSiteStartYmd;
    this.constrSiteDeliveryYmd = constrSiteDeliveryYmd;
    this.hisNo = hisNo;
    this.updTs = updTs;
    this.constrWbsDtls = constrWbsDtls;
  }
}
