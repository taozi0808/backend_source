package com.daitoj.tkms.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.hibernate.annotations.Where;

/** 現場棟明細情報 */
@lombok.Getter
@lombok.Setter
@Entity
@Table(name = "t_project_building_dtl")
@Where(clause = "del_flg = '0'")
public class TProjectBuildingDtl extends BaseEntity {

  @Id
  @GeneratedValue(
      strategy = GenerationType.SEQUENCE,
      generator = "t_project_building_dtl_project_building_did_seq_generator")
  @SequenceGenerator(
      name = "t_project_building_dtl_project_building_did_seq_generator",
      sequenceName = "t_project_building_dtl_project_building_did_seq",
      allocationSize = 1)
  @Column(name = "project_building_did", nullable = false)
  private Long id;

  @NotNull
  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "project_id", nullable = false)
  private TProject project;

  @NotNull
  @Column(name = "seq_no", nullable = false)
  private Integer seqNo;

  @Size(max = 2)
  @NotNull
  @Column(name = "building_cd", nullable = false, length = 2)
  private String buildingCd;

  @NotNull
  @Column(name = "building_work_nm", nullable = false, length = Integer.MAX_VALUE)
  private String buildingWorkNm;
}
