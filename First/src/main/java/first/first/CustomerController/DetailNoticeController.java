package first.first.CustomerController;

import first.first.Entity.ChatBox;
import first.first.Entity.Inform;
import first.first.Entity.Notice;
import first.first.Entity.User;
import first.first.Enum.InformStatusEnum;
import first.first.Service.ChatBoxService;
import first.first.Service.InformService;
import first.first.Service.NoticeService;
import first.first.Service.UserService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @创建人 weizc
 * @创建时间 2018/11/10 16:59
 */
@Controller
@RequestMapping("/customer/detail/notice")
public class DetailNoticeController {
    @Autowired
    NoticeService noticeService;
    @Autowired
    UserService userService;
    @Autowired
    InformService informService;
    @Autowired
    ChatBoxService chatBoxService;

    @GetMapping("/index")
    public String index(Model model) {
        Integer count = 0;
        User master = getUser();
        if (!ObjectUtils.isEmpty(master)) {
            for (ChatBox chatBox : chatBoxService.findByMasterId(master.getUserId())) {
                count += chatBox.getUnReadCount();
            }
            model.addAttribute("user", master);
            model.addAttribute("informs", informService.findByAuthorIdAndStatus(master.getUserId(), InformStatusEnum.UNREAD.getStatus()).size() + count);
        }

        return "thymeleaf/customer/detail/notice_index";
    }

    @RequestMapping("/data")
    @ResponseBody
    public String list(@RequestParam("page") Integer pageNum) {
        SimpleDateFormat format = new SimpleDateFormat();
        PageRequest request = PageRequest.of(pageNum - 1, 10, new Sort(Sort.Direction.DESC, "createTime"));
        Page<Notice> page = noticeService.findAll(request);
        List<Map<String, Object>> list = new ArrayList<>();
        if (page.getContent().size() > 0) {
            for (Notice notice : page.getContent()) {
                Map<String, Object> childMap = new HashMap<>();
                childMap.put("title", notice.getTitle());
                childMap.put("content", notice.getContent());
                childMap.put("time", format.format(notice.getCreateTime()));
                childMap.put("id", notice.getNoticeId());
                list.add(childMap);
            }
        }
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("pages", page.getTotalPages());
        resultMap.put("data", list);
        return new JSONObject(resultMap).toString();
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

    @GetMapping("/main")
    public String main(@RequestParam(value = "id", required = false) Integer id, Model model) {
        Integer count = 0;
        User master = getUser();
        if (!ObjectUtils.isEmpty(master)) {
            for (ChatBox chatBox : chatBoxService.findByMasterId(master.getUserId())) {
                count += chatBox.getUnReadCount();
            }
            model.addAttribute("user", master);
            model.addAttribute("informs", informService.findByAuthorIdAndStatus(master.getUserId(), InformStatusEnum.UNREAD.getStatus()).size() + count);
        }
        Notice notice = noticeService.findByNoticeId(id);
        model.addAttribute("notice", notice);
        return "thymeleaf/customer/detail/notice_main";
    }

}
