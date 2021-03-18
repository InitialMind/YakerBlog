package first.first.Controller;

import first.first.Entity.Reply;
import first.first.Entity.User;
import first.first.Enum.ReplyEnum;
import first.first.Enum.ReplyTypeEnum;
import first.first.Enum.UserEnum;
import first.first.Exception.UserException;
import first.first.Form.ReplyForm;
import first.first.Repository.ReplyRepository;
import first.first.Service.ReplyService;
import first.first.Service.UserService;
import first.first.VO.ReplyVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
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
 * @创建时间 2018/8/14 15:10
 */
@Controller
@RequestMapping("reply")
public class ReplyController {
    @Autowired
    ReplyService replyService;
    @Autowired
    ReplyRepository replyRepository;
    @Autowired
    UserService userService;

    @GetMapping("/comment/index")
    public String comment_index() {
        return "thymeleaf/reply/comment_index";
    }

    @RequestMapping("/comment/data")
    @ResponseBody
    public String comment_data(HttpServletRequest httpServletRequest) {
        Integer size = Integer.parseInt(httpServletRequest.getParameter("limit"));
        Integer pageNum = Integer.parseInt(httpServletRequest.getParameter("page"));
        PageRequest request = PageRequest.of(pageNum - 1, size);
        return ReplyVO.setResult(replyService.findByTypeAndStatus(ReplyTypeEnum.COMMENT.getType(), ReplyEnum.ON.getStatus(), request));
    }

    @GetMapping("/reply/index")
    public String reply_index() {
        return "thymeleaf/reply/reply_index";
    }

    @RequestMapping("/reply/data")
    @ResponseBody
    public String reply_data(HttpServletRequest httpServletRequest) {
        Integer size = Integer.parseInt(httpServletRequest.getParameter("limit"));
        Integer pageNum = Integer.parseInt(httpServletRequest.getParameter("page"));
        PageRequest request = PageRequest.of(pageNum - 1, size);
        return ReplyVO.setResult(replyService.findByTypeAndStatus(ReplyTypeEnum.REPLY.getType(), ReplyEnum.ON.getStatus(), request));

    }

    @GetMapping("/edit")
    public String edit(@RequestParam("id") Integer id, Model model) {
        Reply reply = replyService.findByReplyId(id);
        if (!ObjectUtils.isEmpty(reply)) {
            User user = userService.findOne(reply.getUserId());
            if (!ObjectUtils.isEmpty(user)) {
                model.addAttribute("master", user.getNickName());
            } else {
                model.addAttribute("master", "");
            }
        }
        model.addAttribute("reply", reply);
        return "thymeleaf/reply/edit";
    }

    @RequestMapping("/edit_main")
    @ResponseBody
    public String edit_main(@RequestParam("id") Integer id, @RequestParam("content") String content) {
        Reply reply = replyService.findByReplyId(id);
        if (!ObjectUtils.isEmpty(reply)) {
            reply.setContent(content);
            replyRepository.save(reply);
        }
        return content;
    }

    @Transactional
    @RequestMapping("/del")
    @ResponseBody
    public boolean del(@RequestParam("id") Integer id) {
        replyService.deleteReply(id);
        return true;
    }

    @Transactional
    @RequestMapping("/choose")
    @ResponseBody
    public boolean choose(@RequestParam("idList") String idlist) {
        String ids = idlist.substring(1, idlist.length() - 1);
        for (String id : ids.split(",")) {
            replyService.deleteReply(Integer.parseInt(id));
        }
        return true;
    }

}
