package first.first.Controller;


import first.first.Entity.Admin_User;
import first.first.Entity.Role;
import first.first.Entity.User;

import first.first.Enum.RoleEnum;
import first.first.Enum.UserEnum;
import first.first.Exception.UserException;
import first.first.Form.AdminForm;
import first.first.Form.UserForm;
import first.first.Repository.AdminRepository;
import first.first.Repository.UserRepository;
import first.first.Service.AdminService;
import first.first.Service.RoleService;
import first.first.Service.UserService;
import first.first.Tools.RoleTool;
import first.first.VO.UserAdminVO;
import first.first.VO.UserCommonVO;
import org.json.JSONObject;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import sun.security.util.Length;

import javax.persistence.Id;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import javax.validation.Valid;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @创建人 weizc
 * @创建时间 2018/8/15 19:31
 */
@Controller
@RequestMapping("user")
public class UserController {
    @Autowired
    UserService userService;
    @Autowired
    AdminRepository adminRepository;
    @Autowired
    AdminService adminService;
    @Autowired
    UserRepository userRepository;
    @Autowired
    RoleService roleService;


    @GetMapping("/admin")
    public String admin() {

        return "thymeleaf/user/admin";
    }

    @GetMapping("/common")
    public String common() {

        return "thymeleaf/user/common";
    }

    @RequestMapping("/admin/data")
    @ResponseBody
    public String admin_data(HttpServletRequest httpServletRequest) {
        Integer size = Integer.parseInt(httpServletRequest.getParameter("limit"));
        Integer pageNum = Integer.parseInt(httpServletRequest.getParameter("page"));
        PageRequest request = PageRequest.of(pageNum - 1, size);
        return UserAdminVO.getAdminResult(adminService.findAll(request));
    }

    @RequestMapping("/common/data")
    @ResponseBody
    public String data(HttpServletRequest httpServletRequest) {
        Integer size = Integer.parseInt(httpServletRequest.getParameter("limit"));
        Integer pageNum = Integer.parseInt(httpServletRequest.getParameter("page"));
        PageRequest request = PageRequest.of(pageNum - 1, size);
        return UserCommonVO.UserCommonData(userService.findAll(request));

    }

    @GetMapping("/admin/edit")
    public String admin_edit(@RequestParam(value = "id", required = false) Integer id,
                             @RequestParam("action") String action, Model model) {
        if (!ObjectUtils.isEmpty(id)) {
            Admin_User admin_user = adminService.findByAdminId(id);
            model.addAttribute("user", admin_user);

        }
        List<Role> roles = roleService.findAll();
        for (Role role : roles) {
            if (role.getRoleName().equals("ROLE_USER")) {
                roles.remove(role);
            }
        }
        model.addAttribute("roles", RoleTool.getRoleName(roles));


        model.addAttribute("action", action);
        return "thymeleaf/user/admin_edit";
    }

    @GetMapping("/common/edit")
    public String common_edit(@RequestParam(value = "id", required = false) Integer id,
                              @RequestParam("action") String action, Model model) {
        if (!ObjectUtils.isEmpty(id)) {
            User user = userService.findOne(id);
            model.addAttribute("user", user);

        }
        model.addAttribute("action", action);
        return "thymeleaf/user/common_edit";
    }

    @PostMapping("/common/name")
    @ResponseBody
    public boolean common_name(@RequestParam("name") String username,
                               @RequestParam(value = "id", required = false) String adminId,
                               @RequestParam("nickName") String nickName) {
        User user = userRepository.findByUserName(username);
        User user1 = userRepository.findByNickName(nickName);
        if (ObjectUtils.isEmpty(user) && ObjectUtils.isEmpty(user1)) {
            return true;
        } else if (!StringUtils.isEmpty(adminId)) {
            if (user.getUserId() == Integer.parseInt(adminId) && !ObjectUtils.isEmpty(user) && ObjectUtils.isEmpty(user1)) {
                return true;
            } else if (user1.getUserId() == Integer.parseInt(adminId) && ObjectUtils.isEmpty(user) && !ObjectUtils.isEmpty(user1)) {
                return true;
            } else if (user1.getUserId() == user.getUserId() && user1.getUserId() == Integer.parseInt(adminId) && !ObjectUtils.isEmpty(user) && !ObjectUtils.isEmpty(user1)) {
                return true;
            }

        }
        return false;
    }

    @PostMapping("/admin/name")
    @ResponseBody
    public boolean admin_name(@RequestParam("name") String username,
                              @RequestParam(value = "id", required = false) String adminId,
                              @RequestParam("nickName") String nickName) {
        Admin_User admin_user1 = adminRepository.findByUserName(username);
        Admin_User admin_user2 = adminService.findByNickName(nickName);
        if (ObjectUtils.isEmpty(admin_user1) && ObjectUtils.isEmpty(admin_user2)) {
            return true;
        } else if (!StringUtils.isEmpty(adminId)) {
            if (admin_user1.getAdminId() == Integer.parseInt(adminId) && !ObjectUtils.isEmpty(admin_user1) && ObjectUtils.isEmpty(admin_user2)) {
                return true;
            } else if (admin_user2.getAdminId() == Integer.parseInt(adminId) && ObjectUtils.isEmpty(admin_user1) && !ObjectUtils.isEmpty(admin_user2)) {
                return true;
            } else if (admin_user2.getAdminId() == admin_user1.getAdminId() && admin_user2.getAdminId() == Integer.parseInt(adminId) && !ObjectUtils.isEmpty(admin_user1) && !ObjectUtils.isEmpty(admin_user2)) {
                return true;
            }

        }
        return false;
    }


    @PostMapping("/admin/mod")
    @ResponseBody
    public String admin_edit_add(@Valid AdminForm form,
                                 @RequestParam("action") String action,
                                 BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new UserException(UserEnum.FORM_ERROR);
        }
        if (action.equals("edit")) {
            Admin_User admin_user = adminService.findByAdminId(form.getAdminId());
            BeanUtils.copyProperties(form, admin_user);
            admin_user.setRole(RoleTool.setRole(form.getRole()));
            adminRepository.save(admin_user);

        } else {
            Admin_User admin_user = new Admin_User();
            BeanUtils.copyProperties(form, admin_user);
            admin_user.setRole(RoleTool.setRole(form.getRole()));
            adminRepository.save(admin_user);
        }
        return new JSONObject(form).toString();
    }

    @PostMapping("/common/mod")
    @ResponseBody
    public String edit_add(@Valid UserForm form, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new UserException(UserEnum.FORM_ERROR);
        }
        if (form.getAction().equals("edit")) {
            User user = userService.findOne(form.getUserId());
            BeanUtils.copyProperties(form, user);
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            try {
                user.setBirthday(format.parse(form.getBirthday()));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            if (form.getUserSex().equals("男") && user.getIcon().equals("/images/default_woman.png")) {
                user.setIcon("/images/default_man.png");
            } else if (form.getUserSex().equals("女") && user.getIcon().equals("/images/default_man.png")) {
                user.setIcon("/images/default_woman.png");
            }

            userRepository.save(user);

        } else {
            User user = new User();
            BeanUtils.copyProperties(form, user);
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            try {
                user.setBirthday(format.parse(form.getBirthday()));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            if (form.getUserSex().equals("男")) {
                user.setIcon("/images/default_man.png");
            } else {
                user.setIcon("/images/default_woman.png");
            }
            userRepository.save(user);
        }
        return new JSONObject(form).toString();
    }

    @Transactional
    @PostMapping("/admin/choose")
    @ResponseBody
    public boolean del_choose(@RequestParam("idList") String idlist) {
        String ids = idlist.substring(1, idlist.length() - 1);

        for (String id : ids.split(",")) {
            Admin_User admin_user = adminService.findByAdminId(Integer.parseInt(id));
            if (ObjectUtils.isEmpty(admin_user)) {

            } else {
                adminRepository.deleteByAdminId(Integer.parseInt(id));
            }

        }
        return true;
    }

    @Transactional
    @PostMapping("/common/choose")
    @ResponseBody
    public boolean common_choose(@RequestParam("idList") String idlist) {
        String ids = idlist.substring(1, idlist.length() - 1);

        for (String id : ids.split(",")) {
            User user = userService.findOne(Integer.parseInt(id));
            if (ObjectUtils.isEmpty(user)) {
                throw new UserException(UserEnum.USER_NOT_EXIST);
            }
            userService.deleteByUserId(Integer.parseInt(id));
        }

        return true;
    }


    @Transactional
    @PostMapping("/admin/del")
    @ResponseBody
    public boolean admin_del(@RequestParam("id") Integer id) {
        if (!ObjectUtils.isEmpty(id)) {
            adminService.deleteByAdminId(id);
        }
        return true;
    }


    @Transactional
    @PostMapping("/common/del")
    @ResponseBody
    public boolean common_del(@RequestParam("id") Integer id) {

        User user = userService.findOne(id);
        if (ObjectUtils.isEmpty(user)) {
            throw new UserException(UserEnum.USER_NOT_EXIST);
        }
        userService.deleteByUserId(id);
        return true;
    }




}
