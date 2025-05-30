package com.daitoj.tkms.modules.apii0010.service.dto;

import io.swagger.v3.oas.annotations.media.Schema;

/** 出来高シュミレーション情報 */
@lombok.Getter
@lombok.Setter
@Schema(name = "DekidakaShimyurēsyonInfoDto", description = "検索結果")
public class DekidakaShimyurēsyonInfoDto {
  /** 現場コード */
  @Schema(name = "genbaCode", description = "現場コード")
  private String genbaCode;

  /** 現場名 */
  @Schema(name = "genbaName", description = "現場名")
  private String genbaName;

  /** 概算コード */
  @Schema(name = "gaisanCode", description = "概算コード")
  private String gaisanCode;

  /** 案件名 */
  @Schema(name = "ankenName", description = "案件名")
  private String ankenName;

  /** 現場着工日 */
  @Schema(name = "genbaCyakkouYmd", description = "現場着工日")
  private String genbaCyakkouYmd;

  /** 現場完工日 */
  @Schema(name = "genbaKannkouYmd", description = "現場完工日")
  private String genbaKannkouYmd;

  /**
   * コンストラクタ
   *
   * @param genbaCode 現場コード
   * @param genbaName 現場名
   * @param gaisanCode 概算コード
   * @param ankenName 案件名
   * @param genbaCyakkouYmd 現場着工日
   * @param genbaKannkouYmd 現場着工日
   */
  public DekidakaShimyurēsyonInfoDto(
      String genbaCode,
      String genbaName,
      String gaisanCode,
      String ankenName,
      String genbaCyakkouYmd,
      String genbaKannkouYmd) {
    this.genbaCode = genbaCode;
    this.genbaName = genbaName;
    this.gaisanCode = gaisanCode;
    this.ankenName = ankenName;
    this.genbaCyakkouYmd = genbaCyakkouYmd;
    this.genbaKannkouYmd = genbaKannkouYmd;
  }
}
