package com.daitoj.tkms.modules.apir0040.service.dto;

import com.daitoj.tkms.modules.common.service.dto.BasePageRequest;
import io.swagger.v3.oas.annotations.media.Schema;

/** 社員一覧パラメータ. */
@lombok.Getter
@lombok.Setter
@Schema(name = "R0040S01Dto", description = "社員一覧パラメータ")
public class R0040S01Dto {

  /* 社員コード */
  @Schema(name = "empCd", description = "社員コード")
  private String empCd;

  /* 氏名 */
  @Schema(name = "empNm", description = "氏名")
  private String empNm;

  /* 性別 */
  @Schema(name = "sex", description = "性別")
  private String sex;

  /* 性別文字列 */
  @Schema(name = "sexNm", description = "性別文字列")
  private String sexNm;

  /* 職種コード */
  @Schema(name = "jobTypeNm", description = "職種名")
  private String jobTypeNm;

  /* 所属部 */
  @Schema(name = "belongOrgNm", description = "所属部")
  private String belongOrgNm;

  /* 所属課 */
  @Schema(name = "belongSectionNm", description = "所属課")
  private String belongSectionNm;

  /* 役職コード */
  @Schema(name = "positionCd", description = "役職コード")
  private String positionCd;

  /* 役職名 */
  @Schema(name = "positionNm", description = "役職名")
  private String positionNm;

  /* 退職者検索区分 */
  @Schema(name = "showResign", description = "退職者検索区分")
  private String showResign;

}
