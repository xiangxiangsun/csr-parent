package css.security.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: sunxiangxiang
 * @data: 2020-04-14
 */

@Controller
@RequestMapping("/system")
public class LoginConfigController {

    @GetMapping("/userManage")
    public String user(){
        System.out.println("user");
        return "user.html";
    }

    @RequestMapping(value="/role",method= RequestMethod.GET)
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
