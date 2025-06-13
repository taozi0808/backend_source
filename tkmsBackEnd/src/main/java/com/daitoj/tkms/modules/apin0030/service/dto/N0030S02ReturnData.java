package com.daitoj.tkms.modules.apin0030.service.dto;

import com.daitoj.tkms.domain.TConstrWbsDtl;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import java.time.Instant;
import java.util.List;

/** 工事予実入力情報結果 */
@lombok.Getter
@lombok.Setter
@Schema(name = "N0030S02ReturnData", description = "工事予実入力情報結果")
public class N0030S02ReturnData {
  /** 工事予実ヘッダ */
  @Schema(name = "constrWbsHid", description = "工事予実ヘッダID")
  protected Long id;

  /** 現場コード */
  @Schema(name = "constrSiteCd", description = "現場コード")
  protected String constrSiteCd;

  /** 入力担当者コード */
  @Schema(name = "createPicCd", description = "入力担当者コード")
  protected String createPicCd;

  /** 予実作成日 */
  @Schema(name = "wbsCreateDt", description = "予実作成日")
  protected String wbsCreateDt;

  /** 現場名 */
  @Schema(name = "constrSiteNm", description = "現場名")
  protected String constrSiteNm;

  /** 現場カナ名 */
  @Schema(name = "constr_site_kn_nm", description = "現場カナ名")
  protected String constrSiteKnNm;

  /** 現場着手日 */
  @Schema(name = "constrSiteStartYmd", description = "現場着手日")
  protected String constrSiteStartYmd;

  /** 現場引渡日 */
  @Schema(name = "constrSiteDeliveryYmd", description = "現場引渡日")
  protected String constrSiteDeliveryYmd;

  /** 歴番 */
  @Schema(name = "his_no", description = "歴番")
  protected Integer hisNo;

  /** 更新日時 */
  @Column(name = "upd_ts")
  protected Instant updTs;

  /** 工事予実明細リスト */
  @Schema(description = "工事予実明細リスト")
  protected List<TConstrWbsDtl> constrWbsDtls;

  /**
   * コンストラクタ
   *
   * @param id 工事予実ヘッダID
   * @param constrSiteCd 現場コード
   * @param createPicCd 入力担当者コード
   * @param wbsCreateDt 予実作成日
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
      String wbsCreateDt,
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
    this.wbsCreateDt = wbsCreateDt;
    this.constrSiteNm = constrSiteNm;
    this.constrSiteKnNm = constrSiteKnNm;
    this.constrSiteStartYmd = constrSiteStartYmd;
    this.constrSiteDeliveryYmd = constrSiteDeliveryYmd;
    this.hisNo = hisNo;
    this.updTs = updTs;
    this.constrWbsDtls = constrWbsDtls;
  }
}
