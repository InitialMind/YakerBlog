package first.first.CustomerController;

import first.first.Entity.*;
import first.first.Enum.FocusFansEnum;
import first.first.Enum.InformStatusEnum;
import first.first.Repository.UserRepository;
import first.first.Service.*;
import first.first.VO.DetailArticleVO;
import first.first.VO.IndexVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @创建人 weizc
 * @创建时间 2018/10/12 14:35
 */
@Controller
@RequestMapping("/customer/detail/personal")
public class DetailPersonalController {
    @Autowired
    UserService userService;
    @Autowired
    InformService informService;
    @Autowired
    FocusFansService focusFansService;
    @Autowired
    NoticeService noticeService;
    @Autowired
    ArticleCategoryService articleCategoryService;
    @Autowired
    ArticleService articleService;
    @Autowired
    UserExperienceService userExperienceService;
    @Autowired
    UserRepository userRepository;
    @Autowired
    ChatBoxService chatBoxService;

    @GetMapping("")
    public String index(@RequestParam("id") Integer id, HttpSession session, Model model) {
        User master = userService.findOne(id);
        Integer count = 0;
        if (!ObjectUtils.isEmpty(master)) {
            model.addAttribute("master", master);
            User user = new User();
            if (!ObjectUtils.isEmpty(session.getAttribute("username"))) {
                user = userService.findByUserName(session.getAttribute("username").toString());
                for (ChatBox chatBox : chatBoxService.findByMasterId(user.getUserId())) {
                    count += chatBox.getUnReadCount();
                }
                if (user.getUserId().equals(id)) {
                    model.addAttribute("same", "Y");
                    UserExperience experience = userExperienceService.findByLevel(master.getUserLevel() + 1);
                    if (!ObjectUtils.isEmpty(experience)) {
                        model.addAttribute("nextLevel", experience.getExperience() - master.getExperience());
                    } else {
                        model.addAttribute("nextLevel", 0);
                    }
                } else {
                    model.addAttribute("same", "N");
                }
                model.addAttribute("user", user);
                model.addAttribute("informs", informService.findByAuthorIdAndStatus(user.getUserId(), InformStatusEnum.UNREAD.getStatus()).size() + count);
                FocusFans focusFans = focusFansService.findByFocusIdAndFansIdAndFocusStatus(master.getUserId(), user.getUserId(), FocusFansEnum.YES.getStatus());
                if (!ObjectUtils.isEmpty(focusFans)) {
                    model.addAttribute("focus", "Y");
                } else {
                    model.addAttribute("focus", "N");
                }
            }
            PageRequest request = PageRequest.of(0, 5);
        }
        List<ArticleCategory> articleCategories = articleCategoryService.findAll();
        model.addAttribute("articleCategories", articleCategories);
        return "thymeleaf/customer/detail/personal";
    }

    @GetMapping("/getUser")
    @ResponseBody
    public User getUser() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        HttpSession session = request.getSession();
        if (!ObjectUtils.isEmpty(session.getAttribute("username"))) {
            return userService.findByUserName(session.getAttribute("username").toString());
        }
        return null;
    }

    @GetMapping("/api/list")
    @ResponseBody
    public String api(@RequestParam("page") Integer page,
                      @RequestParam(value = "id", required = false) Integer userId,
                      @RequestParam(value = "category", required = false) String category,
                      @RequestParam(value = "search", required = false) String search) {
        User user = getUser();
        if (!ObjectUtils.isEmpty(user)) {
            PageRequest request = PageRequest.of(page - 1, 10, new Sort(Sort.Direction.DESC, "createTime"));
            if (!ObjectUtils.isEmpty(category)) {
                return IndexVO.getIndexApi(articleService.findByAuthorIdAndArticleCategory(userId, category, request));
            }
            if (!ObjectUtils.isEmpty(search)) {
                return IndexVO.getIndexApi(articleService.findByAuthorIdAndTitleLike(userId, search, request));
            }
            return IndexVO.getIndexApi(articleService.findByAuthorId(userId, request));
        }
        return null;
    }

    @PostMapping("/update")
    @ResponseBody
    public boolean update(@RequestParam("id") Integer userId,
                          @RequestParam("nickName") String nickName,
                          @RequestParam("sex") String sex,
                          @RequestParam("birthday") String birthday,
                          @RequestParam("province") String province,
                          @RequestParam("city") String city,
                          @RequestParam("statement") String statement) {
        User user = userService.findOne(userId);
        if (!ObjectUtils.isEmpty(user)) {
            user.setNickName(nickName);
            user.setUserSex(sex);
            user.setProvince(province);
            user.setCity(city);
            user.setStatement(statement);
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            try {
                Date date = format.parse(birthday);
                user.setBirthday(date);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            userRepository.save(user);
            return true;
        }
        return false;
    }

    @GetMapping("/pwd")
    public String pwd(@RequestParam("id") Integer id, Model model) {
        User user = userService.findOne(id);
        model.addAttribute("user", user);
        return "thymeleaf/customer/detail/personal_pwd";
    }

    @PostMapping("/pwd_check")
    @ResponseBody
    public boolean pwd_check(@RequestParam("id") Integer id,
                             @RequestParam("pwd") String pwd) {
        User user = userService.findOne(id);
        if (!ObjectUtils.isEmpty(user)) {
            if (user.getPassword().equals(pwd)) {
                return true;
            }
            return false;
        }
        return false;
    }

    @PostMapping("/pwd_save")
    @ResponseBody
    public boolean pwd_save(@RequestParam("id") Integer id,
                            @RequestParam("pwd") String pwd) {
        User user = userService.findOne(id);
        if (!ObjectUtils.isEmpty(user)) {
            user.setUserPwd(pwd);
            userRepository.save(user);
            return true;
        }
        return false;
    }
}
