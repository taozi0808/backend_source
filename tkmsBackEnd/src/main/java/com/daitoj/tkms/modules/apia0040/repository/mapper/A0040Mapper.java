package com.daitoj.tkms.modules.apia0040.repository.mapper;

import com.daitoj.tkms.modules.apia0040.service.dto.NoticeInfoDto;
import com.daitoj.tkms.modules.apia0040.service.dto.NoticeResultInfoDto;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

/** ダッシュボード情報マッピング. */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface A0040Mapper {

  /**
   * ダッシュボード情報に変換する.
   *
   * @param noticeInfoDto ダッシュボード情報
   * @return ダッシュボード情報
   */
  List<NoticeResultInfoDto> toNoticeResultInfo(List<NoticeInfoDto> noticeInfoDto);

}
