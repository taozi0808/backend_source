package com.daitoj.tkms.modules.apib0030.service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.Objects;

/** 精積算明細 */
@lombok.Getter
@lombok.Setter
@Schema(name = "DetailedEstDtlDto", description = "精積算明細")
public class DetailedEstDtlDto {

  /** 精積算明細ID */
  @Schema(description = "精積算明細ID")
  private Long id;

  /** 大工事コード */
  @Schema(description = "大工事コード")
  private String majorWorkCd;

  /** 大工事名 */
  @Schema(description = "大工事名")
  private String majorWorkNm;

  /** 小工事コード */
  @Schema(description = "小工事コード")
  private String minorWorkCd;

  /** 小工事名 */
  @Schema(description = "小工事名")
  private String minorWorkNm;

  /** 連番 */
  @Schema(description = "連番")
  private String workSeqNo;

  /** 規格 */
  @Schema(description = "規格")
  private String spec;

  /**
   * コンストラクタ
   *
   * @param id 精積算明細ID
   * @param majorWorkCd 大工事コード
   * @param majorWorkNm 大工事名
   * @param minorWorkCd 小工事コード
   * @param minorWorkNm 小工事名
   * @param workSeqNo 連番
   * @param spec 規格
   */
  public DetailedEstDtlDto(
      Long id,
      String majorWorkCd,
      String majorWorkNm,
      String minorWorkCd,
      String minorWorkNm,
      String workSeqNo,
      String spec) {
    this.id = id;
    this.majorWorkCd = majorWorkCd;
    this.majorWorkNm = majorWorkNm;
    this.minorWorkCd = minorWorkCd;
    this.minorWorkNm = minorWorkNm;
    this.workSeqNo = workSeqNo;
    this.spec = spec;
  }

  @Override
  public boolean equals(Object o) {
    if (o == null || getClass() != o.getClass()) return false;
    DetailedEstDtlDto that = (DetailedEstDtlDto) o;
    return Objects.equals(id, that.id)
        && Objects.equals(majorWorkCd, that.majorWorkCd)
        && Objects.equals(majorWorkNm, that.majorWorkNm)
        && Objects.equals(minorWorkCd, that.minorWorkCd)
        && Objects.equals(minorWorkNm, that.minorWorkNm)
        && Objects.equals(workSeqNo, that.workSeqNo)
        && Objects.equals(spec, that.spec);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, majorWorkCd, majorWorkNm, minorWorkCd, minorWorkNm, workSeqNo, spec);
  }
}
