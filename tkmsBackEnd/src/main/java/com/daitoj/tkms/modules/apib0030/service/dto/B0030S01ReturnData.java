package com.daitoj.tkms.modules.apib0030.service.dto;

import com.daitoj.tkms.modules.common.service.dto.CustomerSearchDto;
import com.daitoj.tkms.modules.common.service.dto.EmpSearchDto;
import com.daitoj.tkms.modules.common.service.dto.MItemListSettingDto;
import com.daitoj.tkms.modules.common.service.dto.OrgSearchDto;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;

/** 案件管理選択項目取得結果 */
@lombok.Getter
@lombok.Setter
@Schema(name = "B0030S01ReturnData", description = "案件管理選択項目取得結果")
public class B0030S01ReturnData {
  /** マスタデータリスト情報 */
  @Schema(description = "マスタデータリスト情報")
  List<MItemListSettingDto> settingList;

  /** 顧客情報リスト */
  @Schema(description = "顧客情報リスト")
  List<CustomerSearchDto> customerList;

  /** 営業部門情報リスト */
  @Schema(description = "営業部門情報リスト")
  List<OrgSearchDto> orgList;

  /** 営業管理職リスト */
  @Schema(description = "営業管理職リスト")
  List<EmpSearchDto> mgrList;

  /** 営業担当者リスト */
  @Schema(description = "営業担当者リスト")
  List<EmpSearchDto> picList;

  /** 消費税率 */
  @Schema(description = "消費税率")
  List<TaxRateDto> taxRateList;

  /** 請求条件 */
  @Schema(description = "請求条件")
  List<PaymentTermDto> paymentTermList;
}
