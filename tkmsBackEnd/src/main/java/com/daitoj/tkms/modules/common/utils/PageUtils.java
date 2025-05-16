package com.daitoj.tkms.modules.common.utils;

import com.daitoj.tkms.modules.common.constants.CommonConstants;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

/** ページ処理クラス */
public class PageUtils {
  /**
   * ページング取得
   *
   * @param page ページインデックス
   * @param size ページ数
   * @return Pageable
   */
  public static Pageable createPageable(int page, int size) {
    if (page <= 0 || size <= 0) {
      return Pageable.unpaged();
    }
    return PageRequest.of(page - 1, size);
  }

  /**
   * 最大ページング取得
   *
   * @return 最大件数
   */
  public static Pageable createMaxPageable() {

    return PageRequest.of(0, CommonConstants.SEARCH_MAX_COUNT);
  }
}
