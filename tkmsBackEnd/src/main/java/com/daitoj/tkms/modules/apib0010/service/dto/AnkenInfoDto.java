package com.daitoj.tkms.modules.apib0010.service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.math.BigDecimal;

/** 案件情報 */
@lombok.Getter
@lombok.Setter
@Schema(name = "AnkenInfoDto", description = "検索結果")
public class AnkenInfoDto {

  /** 案件ID */
  @Schema(name = "projectId", description = "案件ID")
  private Long projectId;

  /** 案件コード */
  @Schema(name = "ankenCode", description = "案件コード")
  private String ankenCode;

  /** 歴番 */
  @Schema(name = "ankenEdaCode", description = "歴番")
  private Integer hisNo;

  /** 案件名 */
  @Schema(name = "ankenName", description = "案件名")
  private String ankenName;

  /** 案件カナ名 */
  @Schema(name = "ankenKnName", description = "案件カナ名")
  private String ankenKnName;

  /** 顧客コード */
  @Schema(name = "kokyakuCode", description = "顧客コード")
  private String kokyakuCode;

  /** 顧客名 */
  @Schema(name = "kokyakuName", description = "顧客名")
  private String kokyakuName;

  /** 顧客カナ名 */
  @Schema(name = "kokyakuKnName", description = "顧客カナ名")
  private String kokyakuKnName;

  /** 想定金額 */
  @Schema(name = "souteiKingaku", description = "想定金額")
  private BigDecimal souteiKingaku;

  /** 現場住所１ */
  @Schema(name = "genbaJyuusyo1", description = "現場住所１")
  private String genbaJyuusyo1;

  /** 現場住所２ */
  @Schema(name = "genbaJyuusyo2", description = "現場住所２")
  private String genbaJyuusyo2;

  /** 受注見込日 */
  @Schema(name = "jycyuuMikomiYmd", description = "受注見込日")
  private String jycyuuMikomiYmd;

  /** 着工希望日 */
  @Schema(name = "cyakkouKibouYmd", description = "着工希望日")
  private String cyakkouKibouYmd;

  /** 営業部門コード */
  @Schema(name = "eigyouBumonCode", description = "営業部門コード")
  private String eigyouBumonCode;

  /** 営業部門名 */
  @Schema(name = "eigyouBumonName", description = "営業部門名")
  private String eigyouBumonName;

  /** 営業担当者コード */
  @Schema(name = "eigyouTantousyaCode", description = "営業担当者コード")
  private String eigyouTantousyaCode;

  /** 営業担当者_氏名 */
  @Schema(name = "eigyouTantousyaShiMei", description = "営業担当者_氏名")
  private String eigyouTantousyaShiMei;

  /** 進捗度コード */
  @Schema(name = "shincyokudoCode", description = "進捗度コード")
  private String shincyokudoCode;

  /** 進捗度 */
  @Schema(name = "shincyokudo", description = "進捗度")
  private String shincyokudo;

  /** コンストラクタ */
  public AnkenInfoDto() {}

  /**
   * コンストラクタ
   *
   * @param projectId 案件ID
   * @param ankenCode 案件コード
   * @param hisNo 歴番
   * @param ankenName 案件名
   * @param ankenKnName 案件カナ名
   * @param kokyakuCode 顧客コード
   * @param kokyakuName 顧客名
   * @param kokyakuKnName 顧客カナ名
   * @param souteiKingaku 想定金額
   * @param genbaJyuusyo1 現場住所１
   * @param genbaJyuusyo2 現場住所２
   * @param jycyuuMikomiYmd 受注見込日
   * @param cyakkouKibouYmd 着工希望日
   * @param eigyouBumonCode 営業部門コード
   * @param eigyouBumonName 営業部門名
   * @param eigyouTantousyaCode 営業担当者コード
   * @param jyuugyouinShimei 営業担当者_氏名
   * @param shincyokudoCode 進捗度コード
   * @param shincyokudo 進捗度
   */
  public AnkenInfoDto(
      Long projectId,
      String ankenCode,
      Integer hisNo,
      String ankenName,
      String ankenKnName,
      String kokyakuCode,
      String kokyakuName,
      String kokyakuKnName,
      BigDecimal souteiKingaku,
      String genbaJyuusyo1,
      String genbaJyuusyo2,
      String jycyuuMikomiYmd,
      String cyakkouKibouYmd,
      String eigyouBumonCode,
      String eigyouBumonName,
      String eigyouTantousyaCode,
      String jyuugyouinShimei,
      String shincyokudoCode,
      String shincyokudo) {
    this.projectId = projectId;
    this.ankenCode = ankenCode;
    this.hisNo = hisNo;
    this.ankenName = ankenName;
    this.ankenKnName = ankenKnName;
    this.kokyakuCode = kokyakuCode;
    this.kokyakuName = kokyakuName;
    this.kokyakuKnName = kokyakuKnName;
    this.souteiKingaku = souteiKingaku;
    this.genbaJyuusyo1 = genbaJyuusyo1;
    this.genbaJyuusyo2 = genbaJyuusyo2;
    this.jycyuuMikomiYmd = jycyuuMikomiYmd;
    this.cyakkouKibouYmd = cyakkouKibouYmd;
    this.eigyouBumonCode = eigyouBumonCode;
    this.eigyouBumonName = eigyouBumonName;
    this.eigyouTantousyaCode = eigyouTantousyaCode;
    this.eigyouTantousyaShiMei = jyuugyouinShimei;
    this.shincyokudoCode = shincyokudoCode;
    this.shincyokudo = shincyokudo;
  }

  /**
   * 現場住所１を取得
   *
   * @return 現場住所１
   */
  public String getGenbaJyuusyo1() {
    return genbaJyuusyo1 != null ? genbaJyuusyo1 : "";
  }

  /**
   * 現場住所２を取得
   *
   * @return 現場住所２
   */
  public String getGenbaJyuusyo2() {
    return genbaJyuusyo2 != null ? genbaJyuusyo2 : "";
  }
}
