package first.first.Controller;

import first.first.Entity.Admin_User;
import first.first.Service.AdminService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;
import java.util.Random;

/**
 * @创建人 weizc
 * @创建时间 2018/8/21 17:43
 */
@Controller
public class LoginController {
    @GetMapping("/login")
    public String login() {
        return "thymeleaf/login/login";
    }

    @GetMapping("/sign")
    public String sign() {
        return "thymeleaf/login/sign";
    }

    @GetMapping("/index")
    public String index(Model model) {

        return "thymeleaf/login/index";
    }


}
