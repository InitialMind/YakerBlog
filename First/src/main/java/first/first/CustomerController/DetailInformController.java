package first.first.CustomerController;

import first.first.Entity.ChatBox;
import first.first.Entity.Inform;
import first.first.Entity.User;
import first.first.Enum.InformStatusEnum;
import first.first.Enum.InformTypeEnum;
import first.first.Service.ChatBoxService;
import first.first.Service.InformService;
import first.first.Service.UserService;
import first.first.VO.DetailInformVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

/**
 * @创建人 weizc
 * @创建时间 2018/11/8 9:03
 */
@Controller
@RequestMapping("/customer/detail/inform")
public class DetailInformController {
    @Autowired
    UserService userService;
    @Autowired
    InformService informService;
    @Autowired
    ChatBoxService chatBoxService;

    @GetMapping("")
    public String index(@RequestParam("id") Integer id, Model model) {
        User user = getUser();
        Integer count = 0;

        if (!ObjectUtils.isEmpty(user)) {
            for (ChatBox chatBox : chatBoxService.findByMasterId(user.getUserId())) {
                count += chatBox.getUnReadCount();
            }
            model.addAttribute("user", user);
            model.addAttribute("informs", informService.findByAuthorIdAndStatus(user.getUserId(), InformStatusEnum.UNREAD.getStatus()).size() + count);

        }
        List<Integer> list1 = new ArrayList<>();
        list1.add(InformTypeEnum.COMMENT_ARTICLE.getType());
        List<Integer> list2 = new ArrayList<>();
        list2.add(InformTypeEnum.REPLY_COMMENT.getType());
        list2.add(InformTypeEnum.REPLY_REPLY.getType());
        List<Integer> list3 = new ArrayList<>();
        list3.add(InformTypeEnum.ADMIRE_ARTICLE.getType());
        list3.add(InformTypeEnum.ADMIRE_COMMENT.getType());
        list3.add(InformTypeEnum.ADMIRE_REPLY.getType());
        model.addAttribute("comments", informService.findByAuthorIdAndInformTypeInAndInformStatus(user.getUserId(), list1, InformStatusEnum.UNREAD.getStatus()).size());
        model.addAttribute("replys", informService.findByAuthorIdAndInformTypeInAndInformStatus(user.getUserId(), list2, InformStatusEnum.UNREAD.getStatus()).size());
        model.addAttribute("admires", informService.findByAuthorIdAndInformTypeInAndInformStatus(user.getUserId(), list3, InformStatusEnum.UNREAD.getStatus()).size());
        model.addAttribute("chats", count);
        return "thymeleaf/customer/detail/inform";
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

    @RequestMapping("/list")
    @ResponseBody
    public String list(@RequestParam("page") Integer page, @RequestParam("action") String action) {
        User user = getUser();
        if (!ObjectUtils.isEmpty(user)) {
            PageRequest request = PageRequest.of(page - 1, 10, new Sort(Sort.Direction.DESC, "createTime"));
            return DetailInformVO.getResult(action, user, request);
        }
        return null;
    }
}
