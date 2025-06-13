package com.daitoj.tkms.modules.apin0030.service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.math.BigDecimal;

/** 工事予実明細情報 */
@lombok.Getter
@lombok.Setter
@Schema(name = "ConstrWbsDtlDto", description = "工事予実明細情報")
public class ConstrWbsDtlDto {
  /** 工事予実明細ID */
  @Schema(name = "constrWbsDid", description = "工事予実明細ID")
  protected Long id;

//  /** 工事工程コード */
//  @Schema(name = "constrProcessCd", description = "工事工程コード")
//  protected MConstrProcess constrProcessCd;

  /** 工事工程表示名 */
  @Schema(name = "constrProcessShowNm", description = "工事工程表示名")
  protected String constrProcessShowNm;

  /** 連番 */
  @Schema(name = "seqNo", description = "連番")
  protected Integer seqNo;

  /** 表示フラグ */
  @Schema(name = "showFlg", description = "表示フラグ")
  protected String showFlg;

  /** 協業業者名 */
  @Schema(name = "partnerVendorNm", description = "協業業者名")
  protected String partnerVendorNm;

  /** 階数 */
  @Schema(name = "floor", description = "階数")
  protected BigDecimal floor;

  /** 予定着手日 */
  @Schema(name = "planStartDt", description = "予定着手日")
  protected String planStartDt;

  /** 実績着手日 */
  @Schema(name = "actStartDt", description = "実績着手日")
  protected String actStartDt;

  /** 着手日編集区分 */
  @Schema(name = "startDtEditK", description = "着手日編集区分")
  protected String startDtEditK;

  /** 予定完了日 */
  @Schema(name = "planEndDt", description = "予定完了日")
  protected String planEndDt;

  /** 実績完了日 */
  @Schema(name = "actEndDt", description = "実績完了日")
  protected String actEndDt;

  /** 完了日編集区分 */
  @Schema(name = "end_dt_k", description = "完了日編集区分")
  protected String endDtK;

  /**
   * コンストラクタ
   *
   * @param id 工事予実明細ID
//   * @param constrProcessCd 工事工程コード
   * @param constrProcessShowNm 工事工程表示名
   * @param seqNo 連番
   * @param showFlg 表示フラグ
   * @param partnerVendorNm 協業業者名
   * @param floor 階数
   * @param planStartDt 予定着手日
   * @param actStartDt 実績着手日
   * @param startDtEditK 着手日編集区分
   * @param planEndDt 予定完了日
   * @param actEndDt 実績完了日
   * @param endDtK 完了日編集区分
   */
  public ConstrWbsDtlDto(
      Long id,
      String constrProcessShowNm,
      Integer seqNo,
      String showFlg,
      String partnerVendorNm,
      BigDecimal floor,
      String planStartDt,
      String actStartDt,
      String startDtEditK,
      String planEndDt,
      String actEndDt,
      String endDtK) {
    this.id = id;
//    this.constrProcessCd = constrProcessCd;
    this.constrProcessShowNm = constrProcessShowNm;
    this.seqNo = seqNo;
    this.showFlg = showFlg;
    this.partnerVendorNm = partnerVendorNm;
    this.floor = floor;
    this.planStartDt = planStartDt;
    this.actStartDt = actStartDt;
    this.startDtEditK = startDtEditK;
    this.planEndDt = planEndDt;
    this.actEndDt = actEndDt;
    this.endDtK = endDtK;
  }
}
