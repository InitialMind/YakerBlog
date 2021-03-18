package first.first.Controller;

import first.first.Entity.User;
import first.first.Repository.UserRepository;
import first.first.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.persistence.Id;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;
import java.util.Random;

/**
 * @创建人 weizc
 * @创建时间 2018/10/24 9:13
 */
@Controller
public class IconController {
    @Autowired
    UserService userService;
    @Autowired
    UserRepository userRepository;

    @GetMapping("/changeIcon")
    public String ww(@RequestParam(value = "id", required = false) Integer userId, Model model) {
        model.addAttribute("icon", userService.findOne(userId).getIcon());
        return "thymeleaf/icon/changeIcon";
    }

    @PostMapping("/changeIcon/img")
    @ResponseBody
    public String img(@RequestParam("imgData") String img) {
        User user = getUser();
        String files = new SimpleDateFormat("yyyyMMddHHmmssSSS")
                .format(new Date())
                + (new Random().nextInt(9000) % (9000 - 1000 + 1) + 1000)
                + ".png";
        String filename = "E:\\intellij idea\\project\\First\\src\\main\\resources\\images\\" + files;
        String base64 = img.substring(img.indexOf(",") + 1);
        try {
            FileOutputStream write = new FileOutputStream(new File(filename));
            byte[] decoderBytes = Base64.getDecoder().decode(base64);
            write.write(decoderBytes);
            user.setIcon("/images/" + files);
            userRepository.save(user);
            write.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return filename;
    }

    @GetMapping("/changeIcon/getUser")
    @ResponseBody
    public User getUser() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        HttpSession session = request.getSession();
        if (!ObjectUtils.isEmpty(session.getAttribute("username"))) {
            return userService.findByUserName(session.getAttribute("username").toString());
        }
        return null;
    }
}
