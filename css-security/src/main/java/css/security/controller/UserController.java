package css.security.controller;

import css.security.common.MessageConstant;
import css.security.dto.SysUserDTO;
import css.security.entity.*;
import css.security.security.LoginUser;
import css.security.service.UserService;
import css.security.entity.PageResult;
import css.security.entity.Result;
import css.security.utils.JuheSmsUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.annotation.Resource;
import java.util.List;


@RestController
@RequestMapping("/user")

public class UserController {

    @Resource
    private UserService userService;

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequestMapping("/getUserName")
    public Result getUsername(){
//        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        LoginUser user = (LoginUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return new Result(true, MessageConstant.GET_USERNAME_SUCCESS,user.getUsername());
    }

    //分页查询
//    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
/*    @RequestMapping("/findPage")
    public ResponseEntity findPage(@RequestBody SysUserDTO userDTO){
        PageResult pageResult = userService.findPage(userDTO);{
        return ResponseEntity.ok(pageResult);
    }}*/

    @RequestMapping("/findPage")
    public ResponseEntity findPage(@RequestBody SysUserDTO userDTO){
        PageResult pageResult = userService.findPage(userDTO);{
            return ResponseEntity.ok(pageResult);
        }}

    @RequestMapping("/add")
    public Result add(@RequestBody UserTable user){
        try {
            userService.add(user);
            return new Result(true, "用户新建成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "用户新建失败");
        }
    }

    @RequestMapping("/findById")
    public Result findById(Integer id){
        try {
            SysUser user = userService.findById(id);
            return new Result(true, "用户信息查询成功",user);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "用户信息查询失败");
        }
    }

    @RequestMapping("/findRoleIdsByUserId")
    public List<Integer> findRoleIdsByUserId(Integer id){
        return userService.findRoleIdsByUserId(id);
    }

    @RequestMapping("/findDeptIdsByUserId")
    public List<Integer> findDeptIdsByUserId(Integer id){
        return userService.findDeptIdsByUserId(id);
    }

    @RequestMapping("/edit")
    public Result edit(@RequestBody UserTable user){
        try {
            userService.edit(user);
            return new Result(true,"用户信息修改成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,"用户信息修改失败");
        }
    }

    @RequestMapping("/deleteById")
    public Result deleteById(Integer id){
        try {
            userService.deleteById(id);
            return new Result(true,"数据删除成功");
        } catch (Exception e) {
            return new Result(false,"数据删除失败");
        }
    }

    @GetMapping("/SmsTest")
    public Result SmsTest(String tel){
        try {
            JuheSmsUtils.sendTz(tel);
            return new Result(true,"查询成功");
        } catch (Exception e) {
            return new Result(false,"查询失败");
        }
    }
}
