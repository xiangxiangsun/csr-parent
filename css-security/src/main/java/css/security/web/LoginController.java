package css.security.web;

import css.security.security.LoginUser;
import css.security.utils.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
public class LoginController {
    private Logger logger = LoggerFactory.getLogger(LoginController.class);

    @RequestMapping("/")
    public String showHome(){
//        String name = SecurityContextHolder.getContext().getAuthentication().getName();
//        logger.info("当前登陆用户："+name);
        if (SecurityUtils.getUsername().equals("liyue")){
            return "dist/helloLy.html";
        }
        if (SecurityUtils.getUsername().equals("dongzhouzhou")){
            return "dist/helloDzz.html";
        }
        if (SecurityUtils.getUsername().equals("dzz")){
            return "dist/helloDzz.html";
        }
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

    @RequestMapping("/admin")
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
    }

}
