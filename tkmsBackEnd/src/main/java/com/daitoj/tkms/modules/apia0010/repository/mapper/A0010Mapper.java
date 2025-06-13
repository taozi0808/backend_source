package com.daitoj.tkms.modules.apia0010.repository.mapper;

import com.daitoj.tkms.domain.MEmp;
import com.daitoj.tkms.domain.MEmpOrg;
import com.daitoj.tkms.domain.MMenuItem;
import com.daitoj.tkms.domain.MVendor;
import com.daitoj.tkms.domain.MWorker;
import com.daitoj.tkms.modules.common.service.dto.EmpDto;
import com.daitoj.tkms.modules.common.service.dto.EmpOrgDto;
import com.daitoj.tkms.modules.common.service.dto.MenuItemDto;
import com.daitoj.tkms.modules.common.service.dto.VendorDto;
import com.daitoj.tkms.modules.common.service.dto.WorkerDto;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

/** ログインマッパー */
@Mapper(
    componentModel = MappingConstants.ComponentModel.SPRING,
    unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface A0010Mapper {

  /**
   * メニュー情報リストからメニューDtoリストに変換する.
   *
   * @param menuItems メニュー情報リスト
   * @return メニューDtoリスト
   */
  List<MenuItemDto> toMenuItemDtoList(List<MMenuItem> menuItems);

  /**
   * 従業員情報から従業員Dtoに変換する.
   *
   * @param emp 従業員
   * @return 従業員Dto
   */
  @Mapping(source = "belongOfficeCd.officeCd", target = "belongOfficeCd")
  EmpDto toEmpDto(MEmp emp);

  /**
   * 業者情報情報から業者情報Dtoに変換する.
   *
   * @param vhd 業者情報
   * @return 業者情報Dto
   */
  VendorDto toVendorDto(MVendor vhd);

  /**
   * 作業員情報から作業員Dtoに変換する.
   *
   * @param worker 作業員情報
   * @return 作業員Dto
   */
  WorkerDto toWorkerDto(MWorker worker);

  /**
   * 従業員・組織・対照情報リストから従業員・組織・対照情報Dtoリストに変換する.
   *
   * @param empOrgs 従業員・組織・対照情報リスト
   * @return 従業員・組織・対照情報リスト
   */
  List<EmpOrgDto> toEmpOrgDtoList(List<MEmpOrg> empOrgs);
}
