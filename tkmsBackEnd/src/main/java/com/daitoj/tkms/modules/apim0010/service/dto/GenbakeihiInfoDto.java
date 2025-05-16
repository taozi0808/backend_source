package com.daitoj.tkms.modules.apim0010.service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.math.BigDecimal;

/** 現場経費情報 */
@lombok.Getter
@lombok.Setter
@Schema(name = "GenbakeihiInfoDto", description = "検索結果")
public class GenbakeihiInfoDto {

  /** 現場コード */
  @Schema(name = "genbaCode", description = "現場コード")
  private String genbaCode;

  /** 現場名 */
  @Schema(name = "genbaName", description = "現場名")
  private String genbaName;

  /** 現場着手日 */
  @Schema(name = "genbaCyakusyuYmd", description = "現場着手日")
  private String genbaCyakusyuYmd;

  /** 現場引渡日 */
  @Schema(name = "genbaHikiwatashiYmd", description = "現場引渡日")
  private String genbaHikiwatashiYmd;

  /** 申請者 */
  @Schema(name = "shinseisya", description = "申請者")
  private String shinseisya;

  /** 申請者コード */
  @Schema(name = "shinseisyaCode", description = "申請者コード")
  private String shinseisyaCode;

  /** 経費申請日 */
  @Schema(name = "keihiShinseiYmd", description = "経費申請日")
  private String keihiShinseiYmd;

  /** 承認者 */
  @Schema(name = "syouninsya", description = "承認者")
  private String syouninsya;

  /** 承認者コード */
  @Schema(name = "syouninsyaCode", description = "承認者コード")
  private String syouninsyaCode;

  /** 経費承認日 */
  @Schema(name = "keihiSyouninYmd", description = "経費承認日")
  private String keihiSyouninYmd;

  /** 経費金額合計 */
  @Schema(name = "keihiKingakuGoukei", description = "経費金額合計")
  private BigDecimal keihiKingakuGoukei;

  /** コンストラクタ */
  public GenbakeihiInfoDto() {}

  /**
   * コンストラクタ
   *
   * @param genbaCode 現場コード
   * @param genbaName 現場名
   * @param genbaCyakusyuYmd 現場着手日
   * @param genbaHikiwatashiYmd 現場引渡日
   * @param shinseisya 申請者
   * @param shinseisyaCode 申請者コード
   * @param keihiShinseiYmd 経費申請日
   * @param syouninsya 承認者
   * @param syouninsyaCode 承認者コード
   * @param keihiSyouninYmd 経費承認日
   * @param keihiKingakuGoukei 経費金額合計
   */
  public GenbakeihiInfoDto(
      String genbaCode,
      String genbaName,
      String genbaCyakusyuYmd,
      String genbaHikiwatashiYmd,
      String shinseisya,
      String shinseisyaCode,
      String keihiShinseiYmd,
      String syouninsya,
      String syouninsyaCode,
      String keihiSyouninYmd,
      BigDecimal keihiKingakuGoukei) {
    this.genbaCode = genbaCode;
    this.genbaName = genbaName;
    this.genbaCyakusyuYmd = genbaCyakusyuYmd;
    this.genbaHikiwatashiYmd = genbaHikiwatashiYmd;
    this.shinseisya = shinseisya;
    this.shinseisyaCode = shinseisyaCode;
    this.keihiShinseiYmd = keihiShinseiYmd;
    this.syouninsya = syouninsya;
    this.syouninsyaCode = syouninsyaCode;
    this.keihiSyouninYmd = keihiSyouninYmd;
    this.keihiKingakuGoukei = keihiKingakuGoukei;
  }
}
