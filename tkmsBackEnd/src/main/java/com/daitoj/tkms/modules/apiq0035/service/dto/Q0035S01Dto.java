package com.daitoj.tkms.modules.apiq0035.service.dto;

import com.daitoj.tkms.modules.common.service.dto.BasePageRequest;
import io.swagger.v3.oas.annotations.media.Schema;

/** 作業員名簿物件一覧パラメータ. */
@lombok.Getter
@lombok.Setter
@Schema(name = "Q0035S01Dto", description = "作業員名簿物件一覧パラメータ")
public class Q0035S01Dto extends BasePageRequest {

  /** 物件コード. */
  @Schema(name = "projectSiteCd", description = "物件コード")
  private String projectSiteCd;

  /** 物件名. */
  @Schema(name = "projectSiteNm", description = "物件名")
  private String projectSiteNm;

  /** 専任技術者. */
  @Schema(name = "ftEngineerNm", description = "専任技術者")
  private String ftEngineerNm;

  /** 現場着手日From. */
  @Schema(name = "constrSiteStartYmdFrom", description = "現場着手日From")
  private String constrSiteStartYmdFrom;

  /** 現場着手日To. */
  @Schema(name = "constrSiteStartYmdTo", description = "現場着手日To")
  private String constrSiteStartYmdTo;

  /** 現場引渡日From. */
  @Schema(name = "constrSiteDeliveryYmdFrom", description = "現場引渡日From")
  private String constrSiteDeliveryYmdFrom;

  /** 現場引渡日To. */
  @Schema(name = "constrSiteDeliveryYmdTo", description = "現場引渡日To")
  private String constrSiteDeliveryYmdTo;

  /** 書類提出状況. */
  @Schema(name = "docSubmissionStatus", description = "書類提出状況")
  private String docSubmissionStatus;
}
