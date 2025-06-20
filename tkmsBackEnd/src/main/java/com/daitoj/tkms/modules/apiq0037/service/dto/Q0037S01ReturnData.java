package com.daitoj.tkms.modules.apiq0037.service.dto;

import com.daitoj.tkms.domain.VSubbieVendorRel;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;

/** 作業員名簿登録選択項目取得結果. */
@lombok.Getter
@lombok.Setter
@Schema(name = "Q0037S01ReturnData", description = "作業員名簿登録選択項目取得結果")
public class Q0037S01ReturnData {
  /** 業者リスト情報リスト情報. */
  @Schema(description = "業者リスト情報")
  List<VSubbieVendorRel> subbieVendorList;
}
