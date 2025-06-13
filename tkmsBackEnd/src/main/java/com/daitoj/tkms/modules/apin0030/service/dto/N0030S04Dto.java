package com.daitoj.tkms.modules.apin0030.service.dto;

import com.daitoj.tkms.domain.TConstrWbsDtl;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.Instant;
import java.util.List;

/** 工事予実印刷パラメータ. */
@lombok.Getter
@lombok.Setter
@Schema(name = "N0030S04Dto", description = "工事予実保存パラメータ")
public class N0030S04Dto extends N0030S02ReturnData {
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
  public N0030S04Dto(
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
}
