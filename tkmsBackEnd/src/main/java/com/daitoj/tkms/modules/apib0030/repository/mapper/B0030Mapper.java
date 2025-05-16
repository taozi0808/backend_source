package com.daitoj.tkms.modules.apib0030.repository.mapper;

import com.daitoj.tkms.domain.TProject;
import com.daitoj.tkms.domain.TProjectBuildingDtl;
import com.daitoj.tkms.domain.TProjectPaymentTerms;
import com.daitoj.tkms.domain.TProjectPreWorkDtl;
import com.daitoj.tkms.domain.TProjectRequestDtl;
import com.daitoj.tkms.modules.apib0030.service.dto.ProjectBuildingDtlDto;
import com.daitoj.tkms.modules.apib0030.service.dto.ProjectDto;
import com.daitoj.tkms.modules.apib0030.service.dto.ProjectPaymentTermsDto;
import com.daitoj.tkms.modules.apib0030.service.dto.ProjectPreWorkDtlDto;
import com.daitoj.tkms.modules.apib0030.service.dto.ProjectRequestDtlDto;
import java.util.List;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

/** 先行作業明細マッパー */
@Mapper(
    unmappedTargetPolicy = ReportingPolicy.IGNORE,
    componentModel = MappingConstants.ComponentModel.SPRING)
public interface B0030Mapper {

  /**
   * 先行作業明細リストから先行作業明細Dtoリストに変換する.
   *
   * @param tprojectPreWorkDtls 先行作業明細リスト
   * @return 先行作業明細DTOリスト
   */
  List<ProjectPreWorkDtlDto> toDtoList(List<TProjectPreWorkDtl> tprojectPreWorkDtls);

  /**
   * 先行作業明細から先行作業明細Dtoに変換する.
   *
   * @param dtl 先行作業明細
   * @return 先行作業明細DTO
   */
  @Mapping(
      target = "priceId",
      expression = "java(dtl.getPriceId() != null ? dtl.getPriceId().getId() : null)")
  @Mapping(
      target = "majorWorkCd",
      expression =
          "java(dtl.getPriceId() != null ? dtl.getPriceId().getMajorWorkCd().getMajorWorkCd() : null)")
  @Mapping(
      target = "majorWorkNm",
      expression =
          "java(dtl.getPriceId() != null ? dtl.getPriceId().getMajorWorkCd().getMajorWorkNm() : null)")
  @Mapping(
      target = "minorWorkCd",
      expression =
          "java(dtl.getPriceId() != null ? dtl.getPriceId().getMinorWorkCd().getId().getMinorWorkCd() : null)")
  @Mapping(
      target = "minorWorkNm",
      expression =
          "java(dtl.getPriceId() != null ? dtl.getPriceId().getMinorWorkCd().getMinorWorkNm() : null)")
  ProjectPreWorkDtlDto toProjectPreWorkDtlDto(TProjectPreWorkDtl dtl);

  /**
   * 案件Dtoから案件エンティティに変換する
   *
   * @param projectDto 案件Dto
   * @return 案件エンティティ
   */
  @Mapping(
      target = "delFlg",
      expression = "java(projectDto.getDelFlg() == null ? \"0\" : projectDto.getDelFlg())")
  @Mapping(source = "customerBranchCd", target = "customerBranchCd")
  TProject toProjectEntity(ProjectDto projectDto);

  /**
   * 請求条件明細Dtoから請求条件明細エンティティに変換する
   *
   * @param projectPaymentTerm 請求条件明細Dto
   * @param projectId 案件ID
   * @return 請求条件明細エンティティ
   */
  @Mapping(target = "projectId", expression = "java(projectId)")
  @Mapping(
      target = "delFlg",
      expression =
          "java(projectPaymentTerm.getDelFlg() == null ? \"0\" : projectPaymentTerm.getDelFlg())")
  TProjectPaymentTerms toProjectPaymentTermEntity(
      ProjectPaymentTermsDto projectPaymentTerm, @Context Long projectId);

  /**
   * 請求条件明細Dtoリストから請求条件明細エンティティリストに変換する
   *
   * @param projectPaymentTerms 請求条件明細Dtoリスト
   * @param projectId 案件ID
   * @return 請求条件明細エンティティリスト
   */
  List<TProjectPaymentTerms> toProjectPaymentTermEntityList(
      List<ProjectPaymentTermsDto> projectPaymentTerms, @Context Long projectId);

  /**
   * 現場棟明細Dtoから現場棟明細エンティティに変換する
   *
   * @param projectBuildingDtlDto 現場棟明細Dto
   * @param projectId 案件ID
   * @return 現場棟明細エンティティ
   */
  @Mapping(target = "projectId", expression = "java(projectId)")
  @Mapping(
      target = "delFlg",
      expression =
          "java(projectBuildingDtlDto.getDelFlg() == null ? \"0\" : projectBuildingDtlDto.getDelFlg())")
  TProjectBuildingDtl toProjectBuildingDtlEntity(
      ProjectBuildingDtlDto projectBuildingDtlDto, @Context Long projectId);

  /**
   * 現場棟明細Dtoリストから現場棟明細エンティティリストに変換する
   *
   * @param projectBuildingDtlDtos 現場棟明細Dtoリスト
   * @param projectId 案件ID
   * @return 現場棟明細エンティティリスト
   */
  List<TProjectBuildingDtl> toProjectBuildingDtlEntityList(
      List<ProjectBuildingDtlDto> projectBuildingDtlDtos, @Context Long projectId);

  /**
   * 案件要望明細Dtoから案件要望明細エンティティに変換する
   *
   * @param projectRequestDtlDto 案件要望明細Dto
   * @param projectId 案件ID
   * @return 案件要望明細エンティティ
   */
  @Mapping(target = "projectId", expression = "java(projectId)")
  @Mapping(
      target = "delFlg",
      expression =
          "java(projectRequestDtlDto.getDelFlg() == null ? \"0\" : projectRequestDtlDto.getDelFlg())")
  TProjectRequestDtl toTProjectRequestDtlEntity(
      ProjectRequestDtlDto projectRequestDtlDto, @Context Long projectId);

  /**
   * 案件要望明細Dtoリストから案件要望明細エンティティリストに変換する
   *
   * @param projectRequestDtlDtos 案件要望明細Dtoリスト
   * @param projectId 案件ID
   * @return 案件要望明細エンティティリスト
   */
  List<TProjectRequestDtl> toTProjectRequestDtlEntityList(
      List<ProjectRequestDtlDto> projectRequestDtlDtos, @Context Long projectId);

  /**
   * 先行作業明細Dtoから先行作業明細エンティティに変換する
   *
   * @param projectPreWorkDtlDto 先行作業明細Dto
   * @param projectId 案件ID
   * @return 先行作業明細エンティティ
   */
  @Mapping(target = "projectId", expression = "java(projectId)")
  @Mapping(
      target = "delFlg",
      expression =
          "java(projectPreWorkDtlDto.getDelFlg() == null ? \"0\" : projectPreWorkDtlDto.getDelFlg())")
  @Mapping(source = "priceId", target = "priceId.id")
  TProjectPreWorkDtl toTProjectPreWorkDtlEntity(
      ProjectPreWorkDtlDto projectPreWorkDtlDto, @Context Long projectId);

  /**
   * 先行作業明細Dtoリストから先行作業明細エンティティリストに変換する
   *
   * @param projectPreWorkDtlDtos 先行作業明細Dtoリスト
   * @param projectId 案件ID
   * @return 先行作業明細エンティティリスト
   */
  List<TProjectPreWorkDtl> toTProjectPreWorkDtlEntityList(
      List<ProjectPreWorkDtlDto> projectPreWorkDtlDtos, @Context Long projectId);
}
