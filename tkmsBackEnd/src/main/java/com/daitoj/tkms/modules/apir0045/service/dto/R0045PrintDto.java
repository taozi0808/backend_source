package com.daitoj.tkms.modules.apir0045.service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;
import org.jetbrains.annotations.NotNull;

import java.io.InputStream;
import java.io.Serializable;
import java.util.List;

/**
 * 社員登録印刷パラメータ.
 */
@lombok.Getter
@lombok.Setter
@Schema(name = "R0045PrintDto", description = "社員登録印刷パラメータ")
public class R0045PrintDto extends EmpDto {

  @Schema(
      name = "sysDate",
      description = "利用PCのシステム日付(yyyy年MM月dd日HH:mm:ss)",
      requiredMode = Schema.RequiredMode.REQUIRED)
  @NotNull
  private String sysDate;

  /** 所属部署 役職名 */
  @Schema(description = "所属部署 役職名(表示のみ)")
  private String positionNm1;

  /** 部署移動設定 役職名 */
  @Schema(description = "部署移動設定 役職名(表示のみ)")
  private String positionNm2;

  /** ログインID */
  @Schema(description = "ログインID")
  private String loginId;

  /** 顔認証用写真1 */
  @Schema(description = "顔認証用写真1")
  private String photoUrl1;

  /** 顔認証用写真2 */
  @Schema(description = "顔認証用写真2")
  private String photoUrl2;

  /** 顔認証用写真3 */
  @Schema(description = "顔認証用写真3")
  private String photoUrl3;

  /** 資格テーブルField1 */
  @Schema(description = "資格テーブルField1")
  private String empCertField1;

  /** 資格テーブルField2 */
  @Schema(description = "資格テーブルField2")
  private String empCertField2;

  /** 資格テーブルField3 */
  @Schema(description = "資格テーブルField3")
  private String empCertField3;

  /** 資格テーブルField4 */
  @Schema(description = "資格テーブルField4")
  private String empCertField4;

  /** 資格テーブルField5*/
  @Schema(description = "資格テーブルField5")
  private String empCertField5;

  /** 資格テーブルField6 */
  @Schema(description = "資格テーブルField6")
  private String empCertField6;

  /** 資格テーブルField7 */
  @Schema(description = "資格テーブルField7")
  private String empCertField7;

  /** 資格テーブルField8 */
  @Schema(description = "資格テーブルField8")
  private String empCertField8;

  /** 資格テーブルField9 */
  @Schema(description = "資格テーブルField9")
  private String empCertField9;

  /** 資格テーブルField10 */
  @Schema(description = "資格テーブルField10")
  private String empCertField10;
}
