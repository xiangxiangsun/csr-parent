package css.security.service;

import css.security.entity.Menu;
import css.security.entity.TreeSelect;

import java.util.List;

public interface MenuService {
    // 查询所有菜单
    List<Menu> findTree();

    // 添加子菜单
    int insertMenu(Menu menu);

    // 通过id查找菜单
    Menu findMenuById(Long id);

    // 编辑菜单
    int updateMenu(Menu menu);

    // 删除
    int deleteMenuById(Integer id);

    // 通过用户名获取对应菜单
    List<Menu> getMenuList(String username);

    //构建前端所需要下拉树结构
    public List<TreeSelect> buildMenuTreeSelect(List<Menu> menus);

    String checkMenuNameUnique(Menu menu);

    List<Menu> getMenuList2(String username);
}
