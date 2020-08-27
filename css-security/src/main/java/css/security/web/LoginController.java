package css.security.web;

import css.security.utils.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@Controller
public class LoginController {
    private Logger logger = LoggerFactory.getLogger(LoginController.class);

    @RequestMapping("/")
    public String showHome(){
//        String name = SecurityContextHolder.getContext().getAuthentication().getName();
//        logger.info("当前登陆用户："+name);
        if (SecurityUtils.getUsername().equals("wuhuan")){
            return "dist/helloWh.html";
        }else {
            return "index.html";
        }
    }

    @RequestMapping("/login")
    public String showLogin(){
        return "login.html";
    }

/*    @RequestMapping("/admin")
    @ResponseBody
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String printAdmin(){
        return "如果你看见这句话，说明你有ROLE_ADMIN角色";
    }

    @RequestMapping("/user")
    @ResponseBody
    @PreAuthorize("hasRole('ROLE_USER')")
    public String printUser(){
        return "如果你看见这句话，说明你有ROLE_USER角色";
    }*/

    @RequestMapping("/index")
    public String index(){
        return "index.html";
    }

    @RequestMapping("/workOrderPage")
    public String workOrder(){
        return "workOrder.html";
    }

    @RequestMapping("/userManage")
    public String user(){
        return "user.html";
    }

    @RequestMapping("/role")
    public String rolePage(){
        return "role.html";
    }

    @RequestMapping("/auth")
    public String permission(){
        return "permission.html";
    }

    @RequestMapping("/changePwd")
    public String updatePSW(){
        return "updatePSW.html";
    }

    @RequestMapping("/menu")
    public String menu(){
        return "menu.html";
    }

    @RequestMapping("/dept")
    public String dept(){
        return "dept.html";
    }

    @RequestMapping("/ucp")
    public String UCP(){
        return "UCP.html";
    }
}
