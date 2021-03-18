package first.first.Controller;

import first.first.Entity.Admin_User;
import first.first.Repository.AdminRepository;
import first.first.Repository.UserRepository;
import first.first.Service.AdminService;
import first.first.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

/**
 * @创建人 weizc
 * @创建时间 2018/9/23 18:12
 */
@Controller
@RequestMapping("personal")
public class PersonalController {
    @Autowired
    AdminService adminService;
    @Autowired
    AdminRepository adminRepository;

    @GetMapping("/index")
    public String index(Model model) {
        Admin_User admin_user = (Admin_User) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();

        Admin_User admin = adminService.findByUserName(admin_user.getUsername());
        model.addAttribute("admin", admin);
        return "thymeleaf/personal/index";
    }

    @PostMapping("/edit")
    @ResponseBody
    public boolean edit(@RequestParam("id") Integer id, @RequestParam("nickName") String nickName,
                        @RequestParam("password") String password) {

        Admin_User admin_user = adminService.findByAdminId(id);
        if (!ObjectUtils.isEmpty(admin_user)) {
            Admin_User admin_user1 = adminService.findByNickName(nickName);
            if (ObjectUtils.isEmpty(admin_user1)) {
                admin_user.setNickName(nickName);
                admin_user.setUserPwd(password);
                adminRepository.save(admin_user);
                return true;
            } else if (admin_user1.getAdminId() == admin_user.getAdminId()) {
                admin_user.setUserPwd(password);
                adminRepository.save(admin_user);
                return true;
            } else {
                return false;
            }
        }
        return false;
    }
}
