package com.daitoj.tkms.modules.apir0010.repository.mapper;

import com.daitoj.tkms.domain.MOrgMenuItem;
import com.daitoj.tkms.domain.MPosition;
import com.daitoj.tkms.modules.apir0010.service.dto.R0010S01Dto;
import com.daitoj.tkms.modules.apir0010.service.dto.R0010S02Dto;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.*;
import java.util.List;

/** 役職マッパー */
@Mapper(
    componentModel = MappingConstants.ComponentModel.SPRING,
    unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface R0010Mapper {
    /**
     * 役職リストから役職Dtoリストに変換する.
     *
     * @param mpositionList 役職リスト
     * @return 役職Dtoリスト
     */
    List<R0010S02Dto> toR0010S02DtoList(List<MPosition> mpositionList);

    /**
     * 役職リストから役職Dtoリストに変換する.
     *
     * @param mpositionList 役職リスト
     * @return 役職Dtoリスト
     */
    List<MPosition> toMPositionList(List<R0010S02Dto> mpositionList);

  @Mapping(target = "id.MOrgId", source = "orgId")
  @Mapping(target = "id.menuItemId", source = "menuItemId")
  @Mapping(target = "MOrg.id", source = "orgId")
  @Mapping(target = "menuItem.id", source = "menuItemId")
  MOrgMenuItem toMOrgMenuItem(R0010S01Dto mposition);

    /**
     * 組織・メニュー項目・対照表リストに変換する.
     *
     * @param mpositionList 役職リスト
     * @return 役職Dtoリスト
     */
    List<MOrgMenuItem> toMOrgMenuItemList(List<R0010S01Dto> mpositionList);



}
