package first.first.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @创建人 weizc
 * @创建时间 2018/9/7 12:46
 */
@Controller
@RequestMapping("error")
public class ErrorPageController {
    @GetMapping("/401")
    public String error_401() {
        return "thymeleaf/common/401";
    }

    @GetMapping("/403")
    public String error_403() {
        return "thymeleaf/common/403";
    }

    @GetMapping("/404")
    public String error_404() {
        return "thymeleaf/common/404";
    }
}
