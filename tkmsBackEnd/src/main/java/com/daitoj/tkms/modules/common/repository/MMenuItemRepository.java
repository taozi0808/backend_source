package com.daitoj.tkms.modules.common.repository;

import com.daitoj.tkms.domain.MMenuItem;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/** メニュー項目情報リポジトリ */
@Repository
public interface MMenuItemRepository extends JpaRepository<MMenuItem, Integer> {
  /**
   * メニュー項目情報取得
   *
   * @param menuId メニューID
   * @return メニュー項目情報
   */
  @Query(
      """
                SELECT mi
                  FROM MMenuItem mi
            INNER JOIN MMenu menu       ON mi.menu = menu
                 WHERE mi.menu.id  = :menuId
              ORDER BY mi.displayOrder
            """)
  List<MMenuItem> findMenuItems(Integer menuId);
}
