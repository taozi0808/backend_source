package com.daitoj.tkms.modules.apia0010.service.dto;

import com.daitoj.tkms.modules.common.service.dto.EmpDto;
import com.daitoj.tkms.modules.common.service.dto.EmpOrgDto;
import com.daitoj.tkms.modules.common.service.dto.MenuItemDto;
import com.daitoj.tkms.modules.common.service.dto.VendorDto;
import com.daitoj.tkms.modules.common.service.dto.WorkerDto;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;

/** ログイン結果 */
@lombok.Getter
@lombok.Setter
@Schema(name = "A0010S01ReturnData", description = "ログイン結果")
public class A0010S01ReturnData {
  /** アカウント区分 */
  @Schema(description = "アカウント区分")
  private String accountKubun;

  /** メニュー情報 */
  @Schema(description = "メニュー情報")
  private List<MenuItemDto> menuItemList;

  /** 従業員情報 */
  @Schema(description = "従業員情報")
  @JsonInclude(JsonInclude.Include.NON_NULL)
  private EmpDto empInfo;

  /** 組織情報 */
  @Schema(description = "組織情報")
  @JsonInclude(JsonInclude.Include.NON_NULL)
  private List<EmpOrgDto> empOrgList;

  /** 業者情報 */
  @Schema(description = "業者情報")
  @JsonInclude(JsonInclude.Include.NON_NULL)
  private VendorDto vendorHdrInfo;

  /** 作業員情報 */
  @Schema(description = "作業員情報")
  @JsonInclude(JsonInclude.Include.NON_NULL)
  private WorkerDto workerInfo;

  /** Token */
  @Schema(description = "Token")
  @JsonProperty("id_token")
  private String idToken;
}
