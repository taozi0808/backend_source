package com.daitoj.tkms.modules.apiq0036.service.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;

/** 作業員名簿業者情報 */
@lombok.Getter
@lombok.Setter
@Schema(name = "SagyouinInfoDto", description = "検索結果")
public class SagyouinInfoDto {
  /** 注文書No */
  @Schema(name = "chuBunshoNo", description = "注文書No")
  private String chuBunshoNo;

  /** 注文書合計金額金額 */
  @Schema(name = "cyuumonsyoKingaku", description = "注文書合計金額金額")
  private BigDecimal cyuumonsyoKingaku;

  /** 大工事内容 */
  @Schema(name = "daikousyuName", description = "大工事内容")
  private String daikousyuName;

  /** 協力業者コード */
  @Schema(name = "kykGyoushaCode", description = "協力業者コード")
  private String kykGyoushaCode;

  /** 一次業者名 */
  @Schema(name = "ichijiGyoushaName", description = "一次業者名")
  private String ichijiGyoushaName;

  /** 該当着手日 */
  @Schema(name = "gaitouChakusyuYmd", description = "該当着手日")
  private String gaitouChakusyuYmd;

  /** 該当引渡日 */
  @Schema(name = "gaitouHikiwatashiYmd", description = "該当引渡日")
  private String gaitouHikiwatashiYmd;

  /**
   * コンストラクタ
   *
   * @param chuBunshoNo 注文書No
   * @param cyuumonsyoKingaku 注文書合計金額金額
   * @param daikousyuName 大工事内容
   * @param kykGyoushaCode 協力業者コード
   * @param ichijiGyoushaName 一次業者名
   * @param gaitouChakusyuYmd 該当着手日
   * @param gaitouHikiwatashiYmd 該当引渡日
   */
  public SagyouinInfoDto(
      String chuBunshoNo,
      BigDecimal cyuumonsyoKingaku,
      String daikousyuName,
      String kykGyoushaCode,
      String ichijiGyoushaName,
      String gaitouChakusyuYmd,
      String gaitouHikiwatashiYmd) {
    this.chuBunshoNo = chuBunshoNo;
    this.cyuumonsyoKingaku = cyuumonsyoKingaku;
    this.daikousyuName = daikousyuName;
    this.kykGyoushaCode = kykGyoushaCode;
    this.ichijiGyoushaName = ichijiGyoushaName;
    this.gaitouChakusyuYmd = gaitouChakusyuYmd;
    this.gaitouHikiwatashiYmd = gaitouHikiwatashiYmd;
  }
}
