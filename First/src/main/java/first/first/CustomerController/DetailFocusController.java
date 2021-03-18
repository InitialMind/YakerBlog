package first.first.CustomerController;

import first.first.Entity.ArticleCategory;
import first.first.Entity.ChatBox;
import first.first.Entity.FocusFans;
import first.first.Entity.User;
import first.first.Enum.FocusFansEnum;
import first.first.Enum.InformStatusEnum;
import first.first.Service.ChatBoxService;
import first.first.Service.FocusFansService;
import first.first.Service.InformService;
import first.first.Service.UserService;
import first.first.VO.FocusFansVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import sun.security.util.Length;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @创建人 weizc
 * @创建时间 2018/10/22 13:18
 */
@Controller
@RequestMapping("/customer/detail/focus")
public class DetailFocusController {
    @Autowired
    UserService userService;
    @Autowired
    InformService informService;
    @Autowired
    FocusFansService focusFansService;
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
                if (user.getUserId().equals(id)) {
                    model.addAttribute("same", "Y");
                } else {
                    model.addAttribute("same", "N");
                    List<Integer> list1 = focusFansService.findByFansId(user.getUserId()).stream().map(e -> e.getUserId()).collect(Collectors.toList());
                    List<Integer> list3 = focusFansService.findByFansId(user.getUserId()).stream().map(e -> e.getUserId()).collect(Collectors.toList());
                    ;
                    List<Integer> list2 = focusFansService.findByFansId(id).stream().map(e -> e.getUserId()).collect(Collectors.toList());
                    List<Integer> list4 = focusFansService.findByFocusId(id).stream().map(e -> e.getUserId()).collect(Collectors.toList());
                    list1.retainAll(list2);
                    list3.retainAll(list4);
                    model.addAttribute("commonFocus", list1.size());
                    model.addAttribute("focusFocus", list3.size());
                }
                model.addAttribute("user", user);
                for (ChatBox chatBox : chatBoxService.findByMasterId(user.getUserId())) {
                    count += chatBox.getUnReadCount();
                }
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
        return "thymeleaf/customer/detail/focus";
    }

    @GetMapping("/api/list")
    @ResponseBody
    public String api(@RequestParam("page") Integer page,
                      @RequestParam(value = "id", required = false) Integer userId,
                      @RequestParam("action") String action) {
        PageRequest request = PageRequest.of(page - 1, 10);
        User user = getUser();
        if (action.equals("focus")) {
            return FocusFansVO.getResult(focusFansService.findByFansIdAndFocusStatus(userId, FocusFansEnum.YES.getStatus(), request), "focus", user.getUserId());
        } else if (action.equals("fans")) {
            return FocusFansVO.getResult(focusFansService.findByFocusIdAndFocusStatus(userId, FocusFansEnum.YES.getStatus(), request), "fans", user.getUserId());
        }
        return null;
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

    @PostMapping("/focusBody")
    @ResponseBody
    public boolean focusBody(@RequestParam("id") Integer id,
                             @RequestParam("type") String type) {
        User user = getUser();
        User master = userService.findOne(id);
        if (!ObjectUtils.isEmpty(user) && !ObjectUtils.isEmpty(master)) {
            FocusFans focusFans = focusFansService.findByFansIdAndFocusId(user.getUserId(), master.getUserId());
            if (type.equals("Y")) {
                if (!ObjectUtils.isEmpty(focusFans) && focusFans.getFocusStatus().equals(FocusFansEnum.YES.getStatus())) {
                    focusFansService.cancelFocus(master.getUserId(), user.getUserId());
                    return true;
                }
            } else if (type.equals("N")) {
                if (ObjectUtils.isEmpty(focusFans) || focusFans.getFocusStatus().equals(FocusFansEnum.NO.getStatus())) {
                    focusFansService.save(master.getUserId(), user.getUserId());
                    return true;
                }
            }
        }
        return false;
    }
}
