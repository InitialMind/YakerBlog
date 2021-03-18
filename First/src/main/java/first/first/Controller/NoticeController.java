package first.first.Controller;

import first.first.Entity.Admin_User;
import first.first.Entity.Notice;
import first.first.Enum.UserEnum;
import first.first.Exception.UserException;
import first.first.Form.NoticeForm;
import first.first.Repository.AdminRepository;
import first.first.Repository.NoticeRepository;
import first.first.Service.AdminService;
import first.first.Service.NoticeService;
import first.first.VO.NoticeVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import javax.validation.Valid;

/**
 * @创建人 weizc
 * @创建时间 2018/9/18 8:45
 */
@Controller
@RequestMapping("/notice")
public class NoticeController {
    @Autowired
    NoticeService noticeService;
    @Autowired
    NoticeRepository noticeRepository;
    @Autowired
    AdminService adminService;
    @Autowired
    AdminRepository adminRepository;

    @GetMapping("/index")
    public String index() {
        return "thymeleaf/notice/index";
    }

    @RequestMapping("/data")
    @ResponseBody
    public String data(HttpServletRequest httpServletRequest) {
        Integer size = Integer.parseInt(httpServletRequest.getParameter("limit"));
        Integer pageNum = Integer.parseInt(httpServletRequest.getParameter("page"));
        PageRequest request = PageRequest.of(pageNum - 1, size);
        return NoticeVO.getResult(noticeService.findAll(request));

    }

    @RequestMapping("/edit")
    public String edit(@RequestParam(value = "id", required = false) Integer id,
                       Model model) {
        if (!ObjectUtils.isEmpty(id)) {
            Notice notice = noticeService.findByNoticeId(id);
            Admin_User admin_user = adminService.findByAdminId(notice.getAuthorId());
            model.addAttribute("data", notice);
            model.addAttribute("author", admin_user.getNickName());
        }
        return "thymeleaf/notice/edit";
    }

    @PostMapping("/mod")
    @ResponseBody
    public boolean mod(@Valid NoticeForm form, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new UserException(UserEnum.FORM_ERROR);
        } else if (!ObjectUtils.isEmpty(form.getNoticeId())) {
            Notice notice = noticeService.findByNoticeId(form.getNoticeId());
            BeanUtils.copyProperties(form, notice);
            noticeRepository.save(notice);
        }
        return true;
    }

    @RequestMapping("/add")
    public String add() {
        return "thymeleaf/notice/add";
    }

    @PostMapping("/add_main")
    @ResponseBody
    public boolean add_main(@RequestParam("title") String title,
                            @RequestParam("content") String content) {
        Notice notice = new Notice();
        notice.setTitle(title);
        notice.setContent(content);
        Admin_User admin_user = (Admin_User) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();
        notice.setAuthorId(admin_user.getAdminId());
        noticeRepository.save(notice);
        Admin_User author = adminService.findByAdminId(admin_user.getAdminId());
        author.setNoticeCount(author.getNoticeCount() + 1);
        adminRepository.save(author);
        return true;
    }

    @PostMapping("/del")
    @Transactional
    @ResponseBody
    public boolean del(@RequestParam("id") Integer id) {
        Notice notice = noticeService.findByNoticeId(id);
        if (!ObjectUtils.isEmpty(notice)) {
            noticeService.deleteByNoticeId(id);
            Admin_User admin_user = adminService.findByAdminId(notice.getAuthorId());
            admin_user.setNoticeCount(admin_user.getNoticeCount() - 1);
            adminRepository.save(admin_user);
        }
        return true;
    }

    @Transactional
    @PostMapping("/choose")
    @ResponseBody
    public boolean del_choose(@RequestParam("idList") String idlist) {
        String ids = idlist.substring(1, idlist.length() - 1);

        for (String id : ids.split(",")) {
            Notice notice = noticeService.findByNoticeId(Integer.parseInt(id));
            if (!ObjectUtils.isEmpty(notice)) {
                Admin_User admin_user = adminService.findByAdminId(notice.getAuthorId());
                admin_user.setNoticeCount(admin_user.getNoticeCount() - 1);
                noticeService.deleteByNoticeId(Integer.parseInt(id));
                adminRepository.save(admin_user);

            }

        }
        return true;

    }
}
