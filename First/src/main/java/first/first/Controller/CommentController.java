package first.first.Controller;

import first.first.Entity.Article;
import first.first.Entity.Comment;
import first.first.Entity.User;
import first.first.Enum.CommentStatusEnum;
import first.first.Enum.UserEnum;
import first.first.Exception.UserException;
import first.first.Form.CommentForm;
import first.first.Repository.ArticleRepository;
import first.first.Repository.CommentRepository;
import first.first.Service.ArticleService;
import first.first.Service.CommentService;
import first.first.Service.UserService;
import first.first.VO.CommentVO;
import jdk.nashorn.internal.objects.annotations.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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
 * @创建时间 2018/8/14 13:08
 */
@Controller
@RequestMapping("comment")
public class CommentController {
    @Autowired
    CommentRepository commentRepository;
    @Autowired
    CommentService commentService;
    @Autowired
    UserService userService;
    @Autowired
    ArticleService articleService;
    @Autowired
    ArticleRepository articleRepository;

    @GetMapping("/index")
    public String index() {

        return "thymeleaf/comment/index";
    }

    @RequestMapping("/data")
    @ResponseBody
    public String data(HttpServletRequest httpServletRequest) {
        Integer size = Integer.parseInt(httpServletRequest.getParameter("limit"));
        Integer pageNum = Integer.parseInt(httpServletRequest.getParameter("page"));
        PageRequest request = PageRequest.of(pageNum - 1, size);
        return CommentVO.getResult(commentService.findAllByCommentStatus(CommentStatusEnum.ON.getStatus(), request));
    }

    @RequestMapping("/edit")
    public String edit(@RequestParam("id") Integer id, Model model) {
        Comment comment = commentService.findByCommenId(id);
        if (!ObjectUtils.isEmpty(comment)) {
            User user = userService.findOne(comment.getUserId());
            model.addAttribute("master", user.getNickName());
            model.addAttribute("comment", comment);
        }
        return "thymeleaf/comment/edit";
    }

    @RequestMapping("/edit_main")
    @ResponseBody
    public String edit_main(@RequestParam("id") Integer id, @RequestParam("content") String content) {
        Comment comment = commentService.findByCommenId(id);
        if (!ObjectUtils.isEmpty(comment)) {
            comment.setContent(content);
            commentRepository.save(comment);
        }
        return content;
    }

    @RequestMapping("/del")
    @ResponseBody
    @Transactional
    public boolean del(@RequestParam("id") Integer id) {
        Comment comment = commentService.findByCommenId(id);
        if (!ObjectUtils.isEmpty(comment)) {
            commentService.deleteComment(id);
        }
        return true;
    }

    @RequestMapping("/choose")
    @ResponseBody
    @Transactional
    public boolean del(@RequestParam("idList") String idlist) {
        String s = idlist.substring(1, idlist.length() - 1);
        for (String id : s.split(",")) {
            Comment comment = commentService.findByCommenId(Integer.parseInt(id));
            if (!ObjectUtils.isEmpty(comment)) {
                commentService.deleteComment(Integer.parseInt(id));
            }
        }
        return true;
    }

}
