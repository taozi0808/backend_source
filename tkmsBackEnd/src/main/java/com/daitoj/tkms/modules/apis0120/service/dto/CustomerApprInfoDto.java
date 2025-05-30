package com.daitoj.tkms.modules.apis0120.service.dto;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * 承認一覧（顧客管理）情報.
 */
@lombok.Getter
@lombok.Setter
@Schema(name = "CustomerApprInfoDto", description = "検索結果")
public class CustomerApprInfoDto {

  /**
   * 顧客コード.
   */
  @Schema(name = "customerCd", description = "顧客コード")
  private String customerCd;

  /**
   * 顧客名.
   */
  @Schema(name = "customerNm", description = "顧客名")
  private String customerNm;

  /**
   *  取引先区分.
   * */
  @Schema(name = "tradingK", description = " 取引先区分")
  private String tradingK;

  /**
   * 業種・業態.
   * */
  @Schema(name = "gyousyuGyoutai", description = "業種・業態")
  private String gyousyuGyoutai;

  /**
   * 代表者名.
   * */
  @Schema(name = "ceoNm", description = "代表者名")
  private String ceoNm;

  /**
   * 申請者.
   */
  @Schema(name = "requestEmpNm", description = "申請者")
  private String requestEmpNm;

  /**
   * 申請日.
   */
  @Schema(name = "requestTs", description = "申請日")
  private String requestTs;

  /**
   * 結果.
   */
  @Schema(name = "itemValue", description = "結果")
  private String itemValue;

  /**
   * コメント.
   */
  @Schema(name = "comment", description = "コメント")
  private String comment;

  /**
   * コンストラクタ.
   */
  public CustomerApprInfoDto() {
  }

  /**
   * コンストラクタ.
   *
   * @param customerCd     顧客コード
   * @param customerNm     顧客名
   * @param tradingK       取引先区分
   * @param gyousyuGyoutai 業種・業態
   * @param ceoNm          代表者名
   * @param requestEmpNm   申請者
   * @param requestTs      申請日
   * @param itemValue      結果
   * @param comment        コメント
   */
  public CustomerApprInfoDto(
      String customerCd,
      String customerNm,
      String tradingK,
      String gyousyuGyoutai,
      String ceoNm,
      String requestEmpNm,
      String requestTs,
      String itemValue,
      String comment) {
    this.customerCd = customerCd;
    this.customerNm = customerNm;
    this.tradingK = tradingK;
    this.gyousyuGyoutai = gyousyuGyoutai;
    this.ceoNm = ceoNm;
    this.requestEmpNm = requestEmpNm;
    this.requestTs = requestTs;
    this.itemValue = itemValue;
    this.comment = comment;
  }

}
