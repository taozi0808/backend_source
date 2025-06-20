package com.daitoj.tkms.modules.apir0045.service.dto;

import com.daitoj.tkms.domain.MOffice;
import com.daitoj.tkms.modules.common.service.dto.CertSearchDto;
import com.daitoj.tkms.modules.common.service.dto.MItemListSettingDto;
import com.daitoj.tkms.modules.common.service.dto.OrgRevSearchDto;
import com.daitoj.tkms.modules.common.service.dto.OrgSearchDto;
import com.daitoj.tkms.modules.common.service.dto.PositionSearchDto;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;

/** 社員管理選択項目取得結果 */
@lombok.Getter
@lombok.Setter
@Schema(name = "R0045S01ReturnData", description = "社員管理選択項目取得結果")
public class R0045S01ReturnData {

  /** マスタデータリスト情報 */
  @Schema(description = "マスタデータリスト情報")
  List<MItemListSettingDto> settingList;

  /** 資格リスト情報 */
  @Schema(description = "資格リスト情報")
  List<CertSearchDto> certList;

  /** 役職リスト情報 */
  @Schema(description = "役職リスト情報")
  List<PositionSearchDto> positionList;

  /** 部署リスト情報 */
  @Schema(description = "部署リスト情報")
  List<OrgSearchDto> busyoList;

  /** 部署課リスト情報 */
  @Schema(description = "部署課リスト情報")
  List<OrgSearchDto> busyokaList;

  /** 異動部署リスト情報 */
  @Schema(description = "異動部署リスト情報")
  List<OrgSearchDto> transferBusyoList;

  /** 異動部署課リスト情報 */
  @Schema(description = "異動部署課リスト情報")
  List<OrgSearchDto> transferBusyokaList;

  /** 適用開始日 */
  @Schema(description = "適用開始日")
  List<OrgRevSearchDto> startDtList;

  /** 事業所情報 */
  @Schema(description = "事業所情報")
  List<MOffice> officeList;
}
