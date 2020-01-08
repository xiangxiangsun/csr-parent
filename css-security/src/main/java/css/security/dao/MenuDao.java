package css.security.dao;

import css.security.entity.Menu;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface MenuDao {
    // 添加子菜单
    int insertMenu(Menu menu);

    List<Menu> findFirstMenu();

    List<Menu> findSecondMenu(@Param("id") Integer id);
//    Menu findSecondMenu(@Param("id") Integer id);

    Menu findMenuById(@Param("id") long id);

    int updateMenu(Menu menu);

    Integer findRelationByMenuId(@Param("id") Integer id);

    int deleteMenuById(@Param("id") Integer id);

    List<Integer> findMenuIdByUsername(@Param("username") String username);

    List<Menu> getMenuListFirst(@Param("menuIds")List<Integer> menuIds);

    List<Menu> findTree();

    Integer findParentMenuId(@Param("menuId")Integer menuId);

    Menu checkDeptNameUnique(@Param("name") String name,@Param("parentmenuid") Long parentmenuid);

    List<Menu> findMenuListByUsername(@Param("username") String username);
}
