package com.daitoj.tkms.modules.common.service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import org.springframework.data.domain.Page;

/** ページネーション情報 */
@lombok.Getter
@lombok.Setter
@Schema(name = "PaginationMeta", description = "ページネーション情報")
public class PaginationMeta {
  /** 全件数 */
  @Schema(name = "totalItemCounts", description = "全件数")
  private long totalItemCounts;

  /** 1ページあたりの件数 */
  @Schema(name = "pageSize", description = "1ページあたりの件数")
  private int pageSize;

  /** 現在のページ（1-index） */
  @Schema(name = "currentPage", description = "現在のページ（1-index）")
  private int currentPage;

  /** 総ページ数 */
  @Schema(name = "totalPages", description = "総ページ数")
  private int totalPages;

  /** ソート項目 */
  @Schema(name = "sortItems", description = "ソート項目")
  private List<SortItem> sortItems;

  /**
   * コンストラクタ
   *
   * @param page ページ情報
   */
  public PaginationMeta(Page<?> page) {
    this.totalItemCounts = page.getTotalElements();
    this.pageSize = page.getSize();
    this.totalPages = page.getTotalPages();

    if (totalPages == 0) {
      this.currentPage = 0;
    } else {
      this.currentPage = page.getNumber() + 1;
    }
  }

  /**
   * コンストラクタ
   *
   * @param page ページ情報
   * @param sortItems ソート情報
   */
  public PaginationMeta(Page<?> page, List<SortItem> sortItems) {
    this.totalItemCounts = page.getTotalElements();
    this.pageSize = page.getSize();

    this.totalPages = page.getTotalPages();
    this.sortItems = sortItems;

    if (totalPages == 0) {
      this.currentPage = 0;
    } else {
      this.currentPage = page.getNumber() + 1;
    }
  }
}
