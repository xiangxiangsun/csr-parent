package css.security.service;

import css.security.entity.Menu;

import java.util.List;

public interface MenuService {
    // 查询所有菜单
    List<Menu> findTree();

    // 添加子菜单
    void add(Menu menu);

    // 通过id查找菜单
    Menu findMenuById(Integer id);

    // 编辑菜单
    void update(Menu menu);

    // 删除
    void remove(Integer id);

    // 通过用户名获取对应菜单
    List<Menu> getMenuList(String username);
}
