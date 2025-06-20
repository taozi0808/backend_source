package com.daitoj.tkms.modules.apig0030.repository.mapper;

import com.daitoj.tkms.domain.TExecBgtDtl;
import com.daitoj.tkms.domain.TExecBgtDtlPo;
import com.daitoj.tkms.domain.TExecBgtPubDtl;
import com.daitoj.tkms.modules.apig0030.service.dto.ExecBgtDtlDto;
import com.daitoj.tkms.modules.apig0030.service.dto.ExecBgtDtlPoDto;
import java.util.List;

import com.daitoj.tkms.modules.apig0030.service.dto.ExecBgtPubDtlDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

/** 実行予算作成情報マッパー. */
@Mapper(
    unmappedTargetPolicy = ReportingPolicy.IGNORE,
    componentModel = MappingConstants.ComponentModel.SPRING)
public interface G0030Mapper {
  /**
   * 実行予算明細リストから実行予算明細Dtoリストに変換する.
   *
   * @param texecBgtDtlList 実行予算明細リスト
   * @return 実行予算明細Dtoリスト
   */
  List<ExecBgtDtlDto> toExecBgtDtlDtoList(List<TExecBgtDtl> texecBgtDtlList);

  /**
   * 実行予算明公共細リストから実行予算公共明細Dtoリストに変換する.
   *
   * @param texecBgtPubDtlList 実行予算公共明細リスト
   * @return 実行予算公共明細Dtoリスト
   */
  List<ExecBgtPubDtlDto> toExecBgtPubDtlDtoList(List<TExecBgtPubDtl> texecBgtPubDtlList);
}
