package css.security.controller;

import css.security.common.MessageConstant;
import css.security.entity.Dept;
import css.security.entity.Menu;
import css.security.entity.Result;
import css.security.service.MenuService;
import css.security.utils.SecurityUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/menu")
public class MenuController {

    @Resource
    private MenuService menuService;

    // 添加子菜单
    @RequestMapping("/addMenu")
    public Result insertMenu(@RequestBody Menu menu){
        try {
            if (MessageConstant.NOT_UNIQUE.equals(menuService.checkMenuNameUnique(menu))){
                return Result.error("添加菜单"+menu.getName()+"失败,菜单名称已存在");
            }
            menu.setCreateBy(SecurityUtils.getUsername());
            return new Result(true,MessageConstant.ADD_MENU_SUCCESS,menuService.insertMenu(menu));
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error(MessageConstant.ADD_MENU_ERROR);
        }
    }

    // 编辑菜单
    @PutMapping
    public Result updateMenu(@RequestBody Menu menu){
        if (MessageConstant.NOT_UNIQUE.equals(menuService.checkMenuNameUnique(menu))){
            return Result.error("修改菜单"+menu.getName()+"失败,菜单名称已存在");
        }
        menu.setUpdateBy(SecurityUtils.getUsername());
        return new Result(true,MessageConstant.UPDATE_MENU_SUCCESS,menuService.updateMenu(menu));
    }

    //构建菜单页以外的树结构 "id"和"lable"
    @RequestMapping("/treeSelect")
    public Result treeSelect(){
        List<Menu> menus = menuService.findTree();
        return new Result(true,MessageConstant.GET_DEPT_SUCCESS,menuService.buildMenuTreeSelect(menus));
    }

    // 获取所有菜单
    @RequestMapping("/getAll")
    public Result getAll(){
        List<Menu> menus = menuService.findAll();
        List<Menu> menus1 = menuService.buildMenuTree(menus);
        return Result.success(MessageConstant.GET_MENU_SUCCESS,menus1);
    }

    // 通过用户名获取对应菜单
    @RequestMapping("/getMenuList")
    public Result getMenuList(String username){
        List<Menu> menus = menuService.getMenuList(username);
        return Result.success(MessageConstant.GET_MENU_SUCCESS,menus);
    }


    // 通过id查询菜单
    @RequestMapping("/findMenuById")
    public Result findMenuById(String id){
        try {
            Menu menu = menuService.findMenuById(Long.parseLong(id));
            return Result.success("查询Menu成功",menu);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error("查询菜单详情失败");
        }
    }

    // 删除
    @RequestMapping("/remove")
    public Result deleteMenuById(Integer id){
        try {
            if (menuService.hasChildByMenuId(id))
            {
                return Result.error("存在子菜单,不允许删除");
            }
            if (menuService.checkMenuExistRole(id))
            {
                return Result.error("菜单已分配,不允许删除");
            }
            menuService.deleteMenuById(id);
            return Result.success(MessageConstant.DELETE_MENU_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error(MessageConstant.DELETE_MENU_ERROR);
        }
    }

    @RequestMapping("/queryList")
    public Result list(@RequestBody Menu menu)
    {
        List<Menu> menus = menuService.selectMenuList(menu);
        return new Result(true,MessageConstant.GET_MENU_SUCCESS,menuService.buildMenuTree(menus));
    }


}
