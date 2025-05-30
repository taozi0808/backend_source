package com.daitoj.tkms.modules.apir0045.repository.mapper;

import com.daitoj.tkms.domain.MEmp;
import com.daitoj.tkms.domain.MEmpCert;
import com.daitoj.tkms.domain.MEmpOrg;
import com.daitoj.tkms.domain.MEmpPhoto;
import com.daitoj.tkms.domain.MEmpTransferDtl;
import com.daitoj.tkms.domain.MEmpTransferHdr;
import com.daitoj.tkms.modules.apir0045.service.dto.EmpCertDto;
import com.daitoj.tkms.modules.apir0045.service.dto.EmpDto;
import com.daitoj.tkms.modules.apir0045.service.dto.EmpOrgDto;
import com.daitoj.tkms.modules.apir0045.service.dto.EmpPhotoDto;
import com.daitoj.tkms.modules.apir0045.service.dto.EmpResultDto;
import com.daitoj.tkms.modules.common.service.dto.EmpTransferDtlDto;
import com.daitoj.tkms.modules.common.service.dto.EmpTransferHdrDto;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

/** 社員登録マッパー */
@Mapper(
    componentModel = MappingConstants.ComponentModel.SPRING,
    unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface R0045Mapper {

  /**
   * 社員登録情報から社員登録Dtoに変換する.
   *
   * @param emp 従業員
   * @return 社員登録Dto
   */
  EmpDto toEmpDto(MEmp emp);

  /**
   * 社員登録情報から社員登録Dtoに変換する.
   *
   * @param emp 従業員
   * @return 社員登録Dto
   */
  EmpResultDto toEmpResultDto(MEmp emp);

  /**
   * 従業員顔写真情報をソート
   *
   * @param empPhotoSet 従業員顔写真情報
   * @return ソートした従業員顔写真情報
   */
  default List<EmpPhotoDto> mapEmpPhotoList(Set<MEmpPhoto> empPhotoSet) {
    if (empPhotoSet == null) {
      return null;
    }
    // ソート
    // 社員写真Dtoに変換する
    return empPhotoSet.stream()
        .sorted(Comparator.comparing(MEmpPhoto::getSeqNo))
        .map(this::mapEmpPhotoToDto)
        .collect(Collectors.toList());
  }

  /**
   * 社員写真情報から社員写真Dtoに変換する.
   *
   * @param empPhoto 社員写真情報
   * @return 社員写真Dto
   */
  EmpPhotoDto mapEmpPhotoToDto(MEmpPhoto empPhoto);

  /**
   * 従業員・組織・対照表情報をソート
   *
   * @param empOrgSet 従業員・組織・対照表情報
   * @return ソートした従業員・組織・対照表情報
   */
  default List<EmpOrgDto> mapEmpOrgList(Set<MEmpOrg> empOrgSet) {
    if (empOrgSet == null) {
      return null;
    }
    // ソート
    // 従業員・組織・対照表Dtoに変換する
    return empOrgSet.stream()
        .sorted(Comparator.comparing(MEmpOrg::getSeqNo))
        .map(this::mapEmpOrgToDto)
        .collect(Collectors.toList());
  }

  /**
   * 従業員・組織・対照表情報から従業員・組織・対照表Dtoに変換する.
   *
   * @param empOrg 従業員・組織・対照表情報
   * @return 従業員・組織・対照表Dto
   */
  EmpOrgDto mapEmpOrgToDto(MEmpOrg empOrg);

  /**
   * 資格情報をソート`
   *
   * @param empCertSet 資格情報
   * @return ソートした資格情報
   */
  default List<EmpCertDto> mapEmpCertList(Set<MEmpCert> empCertSet) {
    if (empCertSet == null) {
      return null;
    }
    // ソート
    // 資格Dtoに変換する
    return empCertSet.stream()
        .sorted(Comparator.comparing(MEmpCert::getSeqNo))
        .map(this::mapEmpCertToDto)
        .collect(Collectors.toList());
  }

  /**
   * 資格情報から資格Dtoに変換する.
   *
   * @param empCert 資格情報
   * @return 資格Dto
   */
  EmpCertDto mapEmpCertToDto(MEmpCert empCert);

  /**
   * 従業員Dtoから従業員エンティティに変換する
   *
   * @param empDto 従業員Dto
   * @return 従業員エンティティ
   */
  MEmp toEmpEntity(EmpDto empDto);

  /**
   * 従業員・組織・対照Dtoから従業員・組織・対照エンティティに変換する
   *
   * @param empOrgDto 従業員・組織・対照Dto
   * @param empId 従業員ID
   * @return 従業員・組織・対照エンティティ
   */
  @Mapping(target = "emp.id", expression = "java(empId)")
  @Mapping(target = "id", source = "id", ignore = true)
  MEmpOrg toEmpOrgEntity(EmpOrgDto empOrgDto, @Context Long empId);

  /**
   * 従業員・組織・対照Dtoリストから従業員・組織・対照エンティティリストに変換する
   *
   * @param empOrgDtoList 従業員・組織・対照Dtoリスト
   * @param empId 従業員ID
   * @return 従業員・組織・対照エンティティリスト
   */
  List<MEmpOrg> toEmpOrgEntityList(List<EmpOrgDto> empOrgDtoList, @Context Long empId);

  /**
   * 従業員資格Dtoから従業員資格エンティティに変換する
   *
   * @param empCertDto 従業員資格Dto
   * @param empId 従業員ID
   * @return 従業員資格エンティティ
   */
  @Mapping(target = "emp.id", expression = "java(empId)")
  @Mapping(target = "id", source = "id", ignore = true)
  MEmpCert toEmpCertEntity(EmpCertDto empCertDto, @Context Long empId);

  /**
   * 従業員資格Dtoリストから従業員資格エンティティリストに変換する
   *
   * @param empCertDtoList 従業員資格Dtoリスト
   * @param empId 従業員ID
   * @return 従業員資格エンティティリスト
   */
  List<MEmpCert> toEmpCertEntityList(List<EmpCertDto> empCertDtoList, @Context Long empId);

  /**
   * 従業員顔写真Dtoから従業員顔写真エンティティに変換する
   *
   * @param empPhotoDto 従業員顔写真Dto
   * @param empId 従業員ID
   * @return 従業員顔写真エンティティ
   */
  @Mapping(target = "emp.id", expression = "java(empId)")
  @Mapping(target = "id", source = "id", ignore = true)
  MEmpPhoto toEmpPhotoEntity(EmpPhotoDto empPhotoDto, @Context Long empId);

  /**
   * 従業員顔写真Dtoリストから従業員顔写真エンティティリストに変換する
   *
   * @param empPhotoDtoList 従業員顔写真Dtoリスト
   * @param empId 従業員ID
   * @return 従業員顔写真エンティティリスト
   */
  List<MEmpPhoto> toEmpPhotoEntityList(List<EmpPhotoDto> empPhotoDtoList, @Context Long empId);

  /**
   * 従業員異動情報から従業員異動Dtoに変換する.
   *
   * @param empTransfer 従業員
   * @return 従業員異動Dto
   */
  EmpTransferHdrDto toEmpTransferHdrDto(MEmpTransferHdr empTransfer);

  /**
   * 従業員異動明細情報から従業員異動明細情報Dtoに変換する.
   *
   * @param empTransferDtl 従業員異動明細情報
   * @return 従業員異動明細情報Dto
   */
  @Mapping(source = "org.id", target = "orgId")
  @Mapping(source = "org.orgCd", target = "orgCd")
  EmpTransferDtlDto mapTransferDtlDto(MEmpTransferDtl empTransferDtl);

  /**
   * 従業員異動明細情報をソート
   *
   * @param empTransferDtl 従業員異動明細情報
   * @return ソートした従業員異動明細情報
   */
  default List<EmpTransferDtlDto> mapEmpTransferDtlList(Set<MEmpTransferDtl> empTransferDtl) {
    if (empTransferDtl == null) {
      return null;
    }
    // ソート
    // 社員写真Dtoに変換する
    return empTransferDtl.stream()
        .sorted(
            Comparator.comparing(MEmpTransferDtl::getSeqNo).thenComparing(MEmpTransferDtl::getOrgK))
        .map(this::mapTransferDtlDto)
        .collect(Collectors.toList());
  }
}
