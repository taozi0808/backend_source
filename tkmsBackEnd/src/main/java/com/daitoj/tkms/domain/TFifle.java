package com.daitoj.tkms.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.UuidGenerator;
import org.hibernate.annotations.Where;

@Getter
@Setter
@Entity
@Table(name = "t_fifle")
@Where(clause = "del_flg = '0'")
public class TFifle extends BaseEntity {

  @Id
  @GeneratedValue
  @UuidGenerator
  @Column(name = "file_id", nullable = false, updatable = false)
  private UUID id;

  @NotNull
  @Column(name = "file_path", nullable = false, length = Integer.MAX_VALUE)
  private String filePath;

  @NotNull
  @Column(name = "file_nm", nullable = false, length = Integer.MAX_VALUE)
  private String fileNm;

  @Size(max = 50)
  @NotNull
  @Column(name = "file_ext", nullable = false, length = 50)
  private String fileExt;

  @NotNull
  @Column(name = "file_size", nullable = false)
  private Integer fileSize;
}
