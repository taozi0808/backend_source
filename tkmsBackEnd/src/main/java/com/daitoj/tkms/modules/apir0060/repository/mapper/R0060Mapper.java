package com.daitoj.tkms.modules.apir0060.repository.mapper;

import com.daitoj.tkms.domain.MCustomer;

import com.daitoj.tkms.modules.apic0030.service.dto.*;
import com.daitoj.tkms.modules.apir0060.service.dto.CustomerBankBankBanchInfoDto;
import org.mapstruct.*;

@Mapper(
    componentModel = MappingConstants.ComponentModel.SPRING,
    unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface R0060Mapper {

  MCustomer toMCustomerEntity(CustomerBankBankBanchInfoDto tCustomerBankBankBanchInfoDto);
}
