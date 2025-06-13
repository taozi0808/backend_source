package com.daitoj.tkms.modules.apin0030.service.dto;

import com.daitoj.tkms.domain.TConstrWbsDtl;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.format.annotation.DateTimeFormat;

/** 工事予実印刷パラメータ */
@lombok.Getter
@lombok.Setter
@Schema(name = "N0030S0Dto", description = "工事予実印刷パラメータ")
public class N0030S03Dto extends N0030S02ReturnData {

  /** 利用PCのシステム日付 */
  @NotNull
  @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  @Schema(
      name = "sysDate",
      description = "利用PCのシステム日付(yyyy-MM-dd HH:mm:ss)",
      example = "2025-01-02 13:14:15",
      type = "string",
      requiredMode = Schema.RequiredMode.REQUIRED)
  private LocalDateTime sysDate;

  /** 入力担当者 */
  @NotNull
  @Schema(
      name = "empNm",
      description = "入力担当者",
      type = "string",
      requiredMode = Schema.RequiredMode.REQUIRED)
  private String empNm;

  /**
   * コンストラクタ.
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
  public N0030S03Dto(
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
    super(
        id,
        constrSiteCd,
        createPicCd,
        wbsCreateDt,
        constrSiteNm,
        constrSiteKnNm,
        constrSiteStartYmd,
        constrSiteDeliveryYmd,
        hisNo,
        updTs,
        constrWbsDtls);
  }

  // TODO 削除
  //    /** 現場コード */
  //    @NotNull
  //    @Size(max = 30)
  //    @Schema(name = "constrSiteCd", description = "現場コード")
  //    protected String constrSiteCd;
  //
  //    /** 現場名 */
  //    @NotNull
  //    @Size(max = 60)
  //    @Schema(name = "constrSiteNm", description = "現場名")
  //    protected String constrSiteNm;
  //
  //    /** 現場カナ名 */
  //    @NotNull
  //    @Size(max = 30)
  //    @Schema(name = "constr_site_kn_nm", description = "現場名")
  //    protected String constrSiteKnNm;
  //
  //    /** 現場着手日 */
  //    @NotNull
  //    @Size(max = 8)
  //    @Schema(name = "constrSiteStartYmd", description = "現場着手日")
  //    protected String constrSiteStartYmd;
  //
  //    /** 現場引渡日 */
  //    @NotNull
  //    @Size(max = 8)
  //    @Schema(name = "constrSiteDeliveryYmd", description = "現場引渡日")
  //    protected String constrSiteDeliveryYmd;
  //
  //    /** 入力担当者 */
  //    @NotNull
  //    @Size(max = 11)
  //    @Schema(name = "empNm", description = "入力担当者")
  //    protected String empNm;
  //
  //    /** 予実作成日 */
  //    @NotNull
  //    @Size(max = 8)
  //    @Schema(name = "wbsCreateDt", description = "予実作成日")
  //    protected String wbsCreateDt;
  //
  //    /** 明細リスト */
  //    @Valid
  //    @NotNull
  //    @Size(min = 1)
  //    @Schema(
  //        name = "listKoujiYojitsu",
  //        description = "工事予実明細リスト",
  //        requiredMode = Schema.RequiredMode.REQUIRED)
  //    private List<ConstrWbsDtlDto> listKoujiYojitsu;
}
