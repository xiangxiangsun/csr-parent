package css.security.controller;

import css.security.common.MessageConstant;
import css.security.entity.Menu;
import css.security.entity.Result;
import css.security.service.MenuService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    @RequestMapping("/update")
    public Result update(@RequestBody Menu menu){
        try {
            menuService.update(menu);
            return Result.success("编辑菜单成功");
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error("编辑菜单失败");
        }
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
    public Result findMenuById(Integer id){
        try {
            Menu menu = menuService.findMenuById(id);
            return Result.success("",menu);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error("查询菜单详情失败");
        }
    }

    // 删除
    @RequestMapping("/remove")
    public Result remove(Integer id){
        try {
            menuService.remove(id);
            return Result.success("删除成功");
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error("删除失败");
        }
    }


}
