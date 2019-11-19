package csr.security.dao;

import csr.security.entity.Menu;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface MenuDao {
    // 添加子菜单
    void add(Menu menu);

    List<Menu> findFirstMenu();

    List<Menu> findSecondMenu(@Param("id") Integer id);

    Menu findMenuById(@Param("id") Integer id);

    void update(Menu menu);

    Integer findRelationByMenuId(@Param("id") Integer id);

    void remove(@Param("id") Integer id);

    List<Integer> findMenuIdByUsername(@Param("username") String username);

    List<Menu> getMenuListFirst(@Param("menuIds")List<Integer> menuIds);

    List<Menu> findTree();
}
