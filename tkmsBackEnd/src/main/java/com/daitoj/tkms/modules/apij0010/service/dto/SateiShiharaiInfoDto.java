package com.daitoj.tkms.modules.apij0010.service.dto;

import io.swagger.v3.oas.annotations.media.Schema;

/** 査定経費情報リスト */
@lombok.Getter
@lombok.Setter
@Schema(name = "SateiShiharaiInfoDto", description = "検索結果")
public class SateiShiharaiInfoDto {
  /** 査定明細ID */
  @Schema(name = "id", description = "査定明細ID")
  private Long id;

  @Schema(name = "partnerVendorCd", description = "協力業者コード")
  private String partnerVendorCd;

  @Schema(name = "compNm", description = "協力業者名")
  private String compNm;

  @Schema(name = "constrSiteCd", description = "現場コード")
  private String constrSiteCd;

  @Schema(name = "constrSiteNm", description = "現場名")
  private String constrSiteNm;

  @Schema(name = "majorWorkCd", description = "大工事コード")
  private String majorWorkCd;

  @Schema(name = "majorWorkNm", description = "大工事名")
  private String majorWorkNm;

  @Schema(name = "minorWorkCd", description = "小工事コード")
  private String minorWorkCd;

  @Schema(name = "minorWorkNm", description = "小工事名")
  private String minorWorkNm;

  @Schema(name = "poNo", description = "注文書No")
  private String poNo;

  /** コンストラクタ */
  public SateiShiharaiInfoDto() {}

  /**
   * コンストラクタ
   *
   * @param id 査定明細ID
   * @param partnerVendorCd 協力業者コード
   * @param compNm 協力業者名
   * @param constrSiteCd 現場コード
   * @param constrSiteNm 現場名
   * @param majorWorkCd 大工事コード
   * @param majorWorkNm 大工事名
   * @param minorWorkCd 小工事コード
   * @param minorWorkNm 小工事名
   * @param poNo 注文書No
   */
  public SateiShiharaiInfoDto(
      Long id,
      String partnerVendorCd,
      String compNm,
      String constrSiteCd,
      String constrSiteNm,
      String majorWorkCd,
      String majorWorkNm,
      String minorWorkCd,
      String minorWorkNm,
      String poNo) {
    this.id = id;
    this.partnerVendorCd = partnerVendorCd;
    this.compNm = compNm;
    this.constrSiteCd = constrSiteCd;
    this.constrSiteNm = constrSiteNm;
    this.majorWorkCd = majorWorkCd;
    this.majorWorkNm = majorWorkNm;
    this.minorWorkCd = minorWorkCd;
    this.minorWorkNm = minorWorkNm;
    this.poNo = poNo;
  }
}
