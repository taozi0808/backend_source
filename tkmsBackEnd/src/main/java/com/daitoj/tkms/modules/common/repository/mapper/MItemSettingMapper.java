package com.daitoj.tkms.modules.common.repository.mapper;

import com.daitoj.tkms.domain.MItemListSetting;
import com.daitoj.tkms.modules.common.service.dto.MitemSettingDto;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

/** マスタデータマッパー */
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface MItemSettingMapper {

  /**
   * マスタデータからマスタデータDtoに変換する.
   *
   * @param mitemListSetting マスタデータ
   * @return マスタデータDto
   */
  @Mapping(source = "id.itemCd", target = "itemCd")
  @Mapping(source = "itemValue", target = "itemValue")
  MitemSettingDto toDto(MItemListSetting mitemListSetting);

  /**
   * マスタデータリストからマスタデータDtoリストに変換する.
   *
   * @param itemListSettings マスタデータリスト
   * @return マスタデータDtoリスト
   */
  List<MitemSettingDto> toDto(List<MItemListSetting> itemListSettings);
}
