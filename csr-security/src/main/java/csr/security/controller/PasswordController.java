package csr.security.controller;

import csr.security.entity.Result;
import csr.security.entity.SysUser;
import csr.security.service.UpdatePwdService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/pwd")
public class PasswordController {

    @Resource
    private UpdatePwdService updatePwdService;

    @RequestMapping("/update")
    public Result update(@RequestBody csr.security.entity.SysUser user) {
            try {
                //修改密码
                updatePwdService.updatePwd(user.getUsername(),user.getNewpassword());
                return new Result(true, "密码修改成功");
            } catch (Exception e) {
                e.printStackTrace();
                return new Result(false, "密码修改失败");
            }

    }

    @RequestMapping("/Confirm")
    public Result Confirm(@RequestBody csr.security.entity.SysUser user) {
        try {
            //确认密码
            SysUser sysUser = updatePwdService.ConfirmPassword(user.getUsername(),user.getPassword());
            if (sysUser != null && sysUser.getUsername().length()>0){
            return new Result(true, "");
            }else {
                return new Result(false, "请重新输入正确的密码");
            }
             } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "请重新输入正确的密码");
        }
    }
}
