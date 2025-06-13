package com.daitoj.tkms.modules.apia0040.service.dto;

import io.swagger.v3.oas.annotations.media.Schema;

/** ダッシュボード情報. */
@lombok.Setter
@lombok.Getter
@Schema(name = "NoticeResultInfoDto", description = "ダッシュボード情報")
public class NoticeResultInfoDto extends NoticeInfoDto {

  /** 区分名. */
  @Schema(name = "kbnNm", description = "区分名")
  public String getKbnNm() {
    String kbnNm = "";
    switch (kbn) {
      case "1" -> kbnNm = "通知";
      case "2" -> kbnNm = "通達";
      case "3" -> kbnNm = "社報";
      case "4", "5" -> kbnNm = "申請";
      case "80" -> kbnNm = "協力会";
      default -> kbnNm = "";
    }
    return kbnNm;
  }

}
