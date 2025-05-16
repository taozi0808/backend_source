package com.daitoj.tkms.modules.apia0010.repository;

import com.daitoj.tkms.domain.MMenuItem;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/** メニュー項目情報リポジトリ */
@Repository
public interface A0010S01Repository extends JpaRepository<MMenuItem, Integer> {
  /**
   * メニュー項目情報を取得する
   *
   * @param loginId ユーザーID
   * @param referPerm 権限あり
   * @param accountKbn アカウント区分
   * @return メニュー項目情報
   */
  @Query(
      """
                   SELECT mi
                     FROM MEmp emp
               INNER JOIN MEmpOrg eo       ON emp = eo.emp
               INNER JOIN MOrgMenuItem omi ON eo.org = omi.mOrg
               INNER JOIN MMenuItem mi     ON omi.menuItem = mi
               INNER JOIN MMenu menu       ON mi.menu = menu
                    WHERE emp.login.loginId   = :loginId
                      AND omi.referPerm       = :referPerm
                      AND menu.id             = :accountKbn
                 ORDER BY mi.displayOrder
          """)
  List<MMenuItem> findEmpMenuItems(String loginId, String referPerm, Integer accountKbn);
}
