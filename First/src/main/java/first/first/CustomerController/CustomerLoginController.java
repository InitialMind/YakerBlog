package first.first.CustomerController;

import first.first.Entity.User;
import first.first.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

/**
 * @创建人 weizc
 * @创建时间 2018/9/21 11:22
 */
@Controller
@RequestMapping("customer")
public class CustomerLoginController {
    @Autowired
    UserService userService;

    @GetMapping("/login")
    public String login() {
        return "thymeleaf/customer/login/login";
    }

    @PostMapping("/login/getIcon")
    @ResponseBody
    public String getIcon(@RequestParam("username") String username) {
        User user = userService.findByUserName(username);
        if (!ObjectUtils.isEmpty(user)) {
            return user.getIcon();
        }
        return null;
    }

    @RequestMapping("/login/index")
    public String verify(@RequestParam("username") String username,
                         @RequestParam("password") String password,
                         Model model, HttpSession session) {
        User user = userService.findByUserNameAndPassWord(username, password);
        if (ObjectUtils.isEmpty(user)) {

            return "redirect:/customer/login?error";
        }
        session.setAttribute("username", username);
        session.setAttribute("userId", user.getUserId());
        return "redirect:/customer/index";
    }
}
