package com.daitoj.tkms.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.SqlResultSetMapping;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Immutable;

/** 下請業者関係ビュー. */
@Getter
@Setter
@Entity
@Table(name = "v_subbie_vendor_rel")
public class VSubbieVendorRel {
  @Id
  @Column(name = "subbie_vendor_id")
  private Long subbieVendorId;

  @Size(max = 9)
  @Column(name = "partner_vendor_cd", length = 9)
  private String partnerVendorCd;

  @Column(name = "parent_subbie_vendor_id")
  private Long parentSubbieVendorId;

  @Column(name = "parent_partner_vendor_cd", length = Integer.MAX_VALUE)
  private String parentPartnerVendorCd;

  @Column(name = "level")
  private Integer level;
}
