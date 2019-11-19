package csr.security.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import csr.security.dao.MenuDao;
import csr.security.entity.Menu;
import csr.security.service.MenuService;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.transaction.Transactional;
import java.util.*;

@Component
@Transactional
public class MenuServiceImpl implements MenuService {

    @Resource
    private MenuDao menuDao;

    @Override
    public List<Menu> findTree() {
        Map<String,Object> data = new HashMap<String,Object>();
        try {//查询所有菜单
            List<Menu> allMenu = menuDao.findTree();
            //根节点
            List<Menu> rootMenu = new ArrayList<Menu>();
            for (Menu nav : allMenu) {
                if(nav.getParentMenuId() == null || nav.getParentMenuId().equals("")){//父节点是NULL的，为根节点。
                    rootMenu.add(nav);
                }
            }
            /* 根据Menu类的order排序 */
            Collections.sort(rootMenu, order());
            //为根菜单设置子菜单，getClild是递归调用的
            for (Menu nav : rootMenu) {
                /* 获取根节点下的所有子节点 使用getChild方法*/
                List<Menu> childList = getChild(nav.getId(), allMenu);
                nav.setChildren(childList);//给根节点设置子节点
            }
            /**
             * 输出构建好的菜单数据。
             *
             */
            return rootMenu;
        } catch (Exception e) {
            return null;
        }
    }

    // 添加子菜单
    @Override
    public void add(Menu menu) {
        menuDao.add(menu);
    }

    // 通过id查找菜单
    @Override
    public Menu findMenuById(Integer id) {
        return menuDao.findMenuById(id);
    }

    // 编辑菜单
    @Override
    public void update(Menu menu) {
        menuDao.update(menu);
    }

    // 删除
    @Override
    public void remove(Integer id) {
        // 先查询是否有约束
        Integer count = menuDao.findRelationByMenuId(id);
        if (count > 0){
            System.out.println("存在约束联系，无法删除");
        } else {
            menuDao.remove(id);
        }
    }

    // 通过用户名获取对应菜单
    @Override
    public List<Menu> getMenuList(String username) {
        // 通过用户名查询对应的menu_id
        List<Integer> menuIds = menuDao.findMenuIdByUsername(username);
        // 通过menu_id查询对应菜单数据
        List<Menu> menuListFirst = new ArrayList<>();
        if (menuIds != null && menuIds.size() > 0){
            // 获取一级菜单
            menuListFirst =  menuDao.getMenuListFirst(menuIds);
            if (CollectionUtil.isNotEmpty(menuListFirst)){
                for (Menu menu : menuListFirst) {
                    // 获取二级菜单
                    List<Menu> menuListSecond = menuDao.findSecondMenu(menu.getId());
                    menu.setChildren(menuListSecond);
                }
            }
        }
        return menuListFirst;
    }

    // 查询子菜单
    public List<Menu> getChild(String id,List<Menu> allMenu){
        //子菜单
        List<Menu> childList = new ArrayList<Menu>();
        for (Menu nav : allMenu) {
            // 遍历所有节点，将所有菜单的父id与传过来的根节点的id比较
            //相等说明：为该根节点的子节点。
            if(nav.getParentMenuId() != null && nav.getParentMenuId().equals(id)){
                childList.add(nav);
            }
        }
        //递归
        for (Menu nav : childList) {
            nav.setChildren(getChild(nav.getId(), allMenu));
        }
        Collections.sort(childList,order());//排序
        //如果节点下没有子节点，返回一个空List（递归退出）
        if(childList.size() == 0){
            return new ArrayList<Menu>();
        }
        return childList;
    }

    public Comparator<Menu> order(){
        Comparator<Menu> comparator = new Comparator<Menu>() {
            @Override
            public int compare(Menu o1, Menu o2) {
                if(o1.getId().equals(o2.getId())){
                    Integer id1 = Integer.parseInt(o1.getId());
                    Integer id2 = Integer.parseInt(o2.getId());
                    return id1 - id2;
                }
                return 0;
            }
        };
        return comparator;
    }
}
