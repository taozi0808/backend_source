package com.daitoj.tkms.modules.apil0010.repository.mapper;

import com.daitoj.tkms.modules.apil0010.service.dto.CustomerInvoiceHdrInfoDto;
import com.daitoj.tkms.modules.apil0010.service.dto.CustomerInvoiceResultInfoDto;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

/** 顧客請求マッピング. */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface L0010Mapper {

  /**
   * 請求dtoから請求結果dtoに変換する.
   *
   * @param customerInvoiceHdrInfo 請求dto
   * @return 請求結果dto
   */
  List<CustomerInvoiceResultInfoDto> toCustomerInvoiceResultInfo(
      List<CustomerInvoiceHdrInfoDto> customerInvoiceHdrInfo);

}
