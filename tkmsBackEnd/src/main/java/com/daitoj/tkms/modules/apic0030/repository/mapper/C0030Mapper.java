package com.daitoj.tkms.modules.apic0030.repository.mapper;

import com.daitoj.tkms.domain.TEvSim;
import com.daitoj.tkms.domain.TEvSimBgt;
import com.daitoj.tkms.domain.TRoughEstFileDtl;
import com.daitoj.tkms.domain.TRoughEstHdr;
import com.daitoj.tkms.domain.TRoughEstDtl;

import com.daitoj.tkms.modules.apic0030.service.dto.*;
import org.mapstruct.*;

import java.util.List;

/** 社員登録マッパー */
@Mapper(
    componentModel = MappingConstants.ComponentModel.SPRING,
    unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface C0030Mapper {

  /**
   * 削除フラグのデフォルト変換処理
   *
   * @param value 削除フラグ
   * @return 変換した削除フラグ
   */
  @Named("nullToZero")
  static String nullToZero(String value) {
    return value == null ? "0" : value;
  }

  /**
   * 概算ヘッダDtoから概算ヘッダエンティティに変換する
   *
   * @param tRoughEstHdrDto 概算ヘッダDto
   * @return 概算ヘッダエンティティ
   */
  @Mapping(target = "priceRegion.id", source = "priceRegionId")
  @Mapping(target = "delFlg", source = "delFlg", qualifiedByName = "nullToZero")
  TRoughEstHdr toTRoughEstHdrEntity(C0030S01Dto tRoughEstHdrDto);

  /**
   * 概算明細Dtoから概算明細エンティティに変換する
   *
   * @param tRoughEstDtlDto 概算明細Dto
   * @return 概算ヘッダエンティティ
   */
  @Mapping(target = "roughEstHid.id", expression = "java(id)")
  @Mapping(target = "delFlg", source = "delFlg", qualifiedByName = "nullToZero")
  @Mapping(target = "unitK", source = "unit")
  TRoughEstDtl toTRoughEstDtlEntity(C0030S02Dto tRoughEstDtlDto, @Context Long id);

  List<TRoughEstDtl> toTRoughEstDtlEntityList(
      List<C0030S02Dto> tRoughEstDtlDtoList, @Context Long id);

  //  /**
  //   * 概算添付ファイルDtoから概算明細エンティティに変換する
  //   *
  //   * @param tRoughEstFileDtlDto 概算添付ファイル明細Dto
  //   * @return 概算ヘッダエンティティ
  //   */
  //  @Mapping(target = "roughEstHid.id", expression = "java(id)")
  //  @Mapping(target = "delFlg", source = "delFlg", qualifiedByName = "nullToZero")
  //  @Mapping(target = "id", source = "fileId")
  //  TRoughEstFileDtl toTRoughEstFileDtlEntity(C0030S03Dto tRoughEstFileDtlDto, @Context Long id);
  //
  //  List<TRoughEstFileDtl> toTRoughEstFileDtlEntityList(
  //      List<C0030S03Dto> tRoughEstFileDtlDtoList, @Context Long id);

  //  /**
  //   * 出来高シュミレーション変換する
  //   *
  //   * @param id 概算ヘッダID
  //   * @param id 概算コード
  //   * @return 来高シュミレーション
  //   */
  //  @Mapping(target = "roughEstHid.id", expression = "java(id)")
  //  @Mapping(target = "roughEstCd", source = "java(roughEstCd)")
  //  TEvSim toTEvSimEntity(Long id, String roughEstCd);

  List<TEvSimBgt> toTEvSimBgtEntityList(List<C0030S02Dto> tEvSimBgtDtoList, @Context Long id);

  /**
   * 出来高シュミレーション変換する
   *
   * @param tEvSimBgtDto 概算ヘッダID
   * @param id 概算コード
   * @return 来高シュミレーション
   */
  @Mapping(target = "evSim.id", expression = "java(id)")
  @Mapping(target = "delFlg", source = "delFlg", qualifiedByName = "nullToZero")
  TEvSimBgt toTEvSimBgtEntity(C0030S02Dto tEvSimBgtDto, @Context Long id);

    @Mapping(target = "sysDate", expression = "java(sysDate)")
  C0030S05Dto toRoughInfo(RoughInfoDto toRoughInfoDto, @Context String sysDate);
}
