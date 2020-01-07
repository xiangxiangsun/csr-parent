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
    @RequestMapping("/add")
    public Result add(@RequestBody Menu menu){
        try {
            menuService.add(menu);
            return Result.success("添加菜单成功");
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error("添加菜单失败");
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

    //构建菜单页以外的树结构
    @RequestMapping("/treeSelect")
    public Result treeSelect(){
        List<Menu> menus = menuService.findTree();
        return new Result(true,MessageConstant.GET_DEPT_SUCCESS,menuService.buildDeptTreeSelect(menus));
    }

    // 获取所有菜单
    @RequestMapping("/getAll")
    public Result getAll(){
        List<Menu> menus = menuService.findTree();
        return Result.success(MessageConstant.GET_MENU_SUCCESS,menus);
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
            menuService.deleteMenuById(id);
            return Result.success(MessageConstant.DELETE_MENU_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error(MessageConstant.DELETE_MENU_ERROR);
        }
    }


}
