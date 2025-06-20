package com.daitoj.tkms.modules.apic0030.service.dto;

import com.daitoj.tkms.domain.TRoughEstHdr;
import com.daitoj.tkms.modules.common.service.dto.BaseDto;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.validation.constraints.Size;
import org.springframework.format.annotation.DateTimeFormat;

/** 概算明細 */
@lombok.Getter
@lombok.Setter
@Schema(name = "C0030S02Dto", description = "概算明細")
public class C0030S02Dto extends BaseDto {

  @Size(max = 3)
  @Schema(description = "大工事コード", requiredMode = Schema.RequiredMode.REQUIRED, maxLength = 3)
  private String majorWorkCd;

  @Size(max = 4)
  @Schema(description = "小工事コード", requiredMode = Schema.RequiredMode.REQUIRED, maxLength = 4)
  private String minorWorkCd;

  @Schema(description = "規格", requiredMode = Schema.RequiredMode.REQUIRED)
  private String spec;

  @Size(max = 10)
  @Schema(description = "数量", requiredMode = Schema.RequiredMode.REQUIRED, maxLength = 10)
  private BigDecimal qty;

  @Size(max = 10)
  @Schema(description = "単価", requiredMode = Schema.RequiredMode.REQUIRED, maxLength = 10)
  private BigDecimal price;

  @Schema(description = "単位", requiredMode = Schema.RequiredMode.REQUIRED)
  private String unit;

  @Size(max = 11)
  @Schema(description = "概算金額", requiredMode = Schema.RequiredMode.REQUIRED, maxLength = 11)
  private BigDecimal roughEstAmt;

  @Schema(description = "備考", requiredMode = Schema.RequiredMode.REQUIRED)
  private String remarks;
}
