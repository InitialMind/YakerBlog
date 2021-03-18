package first.first.CustomerController;

import first.first.Entity.*;
import first.first.Enum.AdmireStatusEnum;
import first.first.Enum.AdmireTypeEnum;
import first.first.Enum.InformStatusEnum;
import first.first.Enum.ReplyTypeEnum;
import first.first.Form.ArticleForm;
import first.first.Form.CommentForm;
import first.first.Form.ReplyForm;
import first.first.Repository.CommentRepository;
import first.first.Repository.ReplyRepository;
import first.first.Service.*;
import first.first.VO.DetailArticleVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.server.Session;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @创建人 weizc
 * @创建时间 2018/9/26 8:39
 */
@Controller
@RequestMapping("/customer/detail")
public class DetailArticleController {
    @Autowired
    ArticleService articleService;
    @Autowired
    UserService userService;
    @Autowired
    ArticleCategoryService articleCategoryService;
    @Autowired
    InformService informService;
    @Autowired
    NoticeService noticeService;
    @Autowired
    AdmireService admireService;
    @Autowired
    CommentRepository commentRepository;
    @Autowired
    CommentService commentService;
    @Autowired
    ReplyRepository replyRepository;
    @Autowired
    ReplyService replyService;
    @Autowired
    ChatBoxService chatBoxService;

    @GetMapping("/article")
    public String article(@RequestParam("id") Integer id, HttpSession session, Model model) {
        User master = new User();
        Integer count = 0;
        if (!ObjectUtils.isEmpty(session.getAttribute("username"))) {
            master = userService.findByUserName(session.getAttribute("username").toString());
            for (ChatBox chatBox : chatBoxService.findByMasterId(master.getUserId())) {
                count += chatBox.getUnReadCount();
            }
            model.addAttribute("user", master);
            model.addAttribute("informs", informService.findByAuthorIdAndStatus(master.getUserId(), InformStatusEnum.UNREAD.getStatus()).size() + count);
        }
        Article article = articleService.findByArticleId(id);
        if (!ObjectUtils.isEmpty(article)) {
            model.addAttribute("article", article);
            User user = userService.findOne(article.getAuthorId());
            if (!ObjectUtils.isEmpty(user)) {
                model.addAttribute("author", user.getNickName());
            } else {
                model.addAttribute("author", "");
            }

        }
        Admire admire = admireService.findByTypeAndAimIdAndUserId(AdmireTypeEnum.ARTICLE.getType(), article.getArticleId(), master.getUserId());
        if (ObjectUtils.isEmpty(admire) || admire.getAdmireStatus().equals(AdmireStatusEnum.NO.getStatus())) {
            model.addAttribute("admireStatus", "N");
        } else {
            model.addAttribute("admireStatus", "Y");
        }
        PageRequest request = PageRequest.of(0, 5);
        model.addAttribute("notices", noticeService.findAll(request).getContent());
        model.addAttribute("categorys", articleCategoryService.findAll());
        return "thymeleaf/customer/detail/article";
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

    @GetMapping("/article/api/list")
    @ResponseBody
    public String article_api(@RequestParam(value = "page") Integer page, @RequestParam("id") Integer id,
                              HttpSession session) {

        User user = userService.findByUserName(session.getAttribute("username").toString());

        PageRequest request = PageRequest.of(page - 1, 10, new Sort(Sort.Direction.DESC, "admireCount"));
        return DetailArticleVO.getArticleDetail(articleService.findByArticleId(id), request, user);
    }

    @PostMapping("/article/admire")
    @ResponseBody
    public boolean article_admire(@RequestParam("aimId") Integer id, @RequestParam("action") String action,
                                  @RequestParam("type") String type) {
        User user = getUser();
        if (!ObjectUtils.isEmpty(user)) {
            if (action.equals("cancel")) {
                if (type.equals("article")) {
                    Admire admire = admireService.findByTypeAndAimIdAndUserId(AdmireTypeEnum.ARTICLE.getType(), id, user.getUserId());
                    if (!ObjectUtils.isEmpty(admire) && admire.getAdmireStatus().equals(AdmireStatusEnum.YES.getStatus())) {
                        admireService.deleteByAdmireId(user.getUserId(), id, AdmireTypeEnum.ARTICLE.getType());
                        return true;
                    }
                } else if (type.equals("comment")) {
                    Admire admire = admireService.findByTypeAndAimIdAndUserId(AdmireTypeEnum.COMMENT.getType(), id, user.getUserId());
                    if (!ObjectUtils.isEmpty(admire) && admire.getAdmireStatus().equals(AdmireStatusEnum.YES.getStatus())) {
                        admireService.deleteByAdmireId(user.getUserId(), id, AdmireTypeEnum.COMMENT.getType());
                        return true;
                    }
                } else if (type.equals("reply")) {
                    Admire admire = admireService.findByTypeAndAimIdAndUserId(AdmireTypeEnum.REPLY.getType(), id, user.getUserId());
                    if (!ObjectUtils.isEmpty(admire) && admire.getAdmireStatus().equals(AdmireStatusEnum.YES.getStatus())) {
                        admireService.deleteByAdmireId(user.getUserId(), id, AdmireTypeEnum.REPLY.getType());
                        return true;
                    }
                }

            } else if (action.equals("admire")) {
                if (type.equals("article")) {
                    admireService.admire(id, user.getUserId(), AdmireTypeEnum.ARTICLE.getType());
                    return true;
                } else if (type.equals("comment")) {
                    admireService.admire(id, user.getUserId(), AdmireTypeEnum.COMMENT.getType());
                    return true;
                } else if (type.equals("reply")) {
                    admireService.admire(id, user.getUserId(), AdmireTypeEnum.REPLY.getType());
                    return true;
                }

            }
        }

        return false;
    }

    @PostMapping("/article/comment")
    @ResponseBody
    public boolean article_comment(@RequestParam("articleId") Integer id, @RequestParam("content") String content) {
        User user = getUser();
        if (!ObjectUtils.isEmpty(user)) {
            CommentForm form = new CommentForm();
            form.setArticleId(id);
            form.setContent(content);
            form.setUserId(user.getUserId());
            commentService.createComment(form);
            return true;
        }
        return false;
    }

    @PostMapping("/article/reply")
    @ResponseBody
    public boolean comment_reply(@RequestParam("content") String content, @RequestParam("id") Integer id,
                                 @RequestParam("commentId") Integer commentId, @RequestParam("type") String type) {
        User user = getUser();
        if (!ObjectUtils.isEmpty(user)) {
            if (type.equals(ReplyTypeEnum.COMMENT.getMessage())) {
                Comment comment = commentService.findByCommenId(id);
                if (ObjectUtils.isEmpty(comment)) {
                    return false;
                }
            }
            if (type.equals(ReplyTypeEnum.REPLY.getMessage())) {
                Reply reply = replyService.findByReplyId(id);
                if (ObjectUtils.isEmpty(reply)) {
                    return false;
                }
            }
            ReplyForm form = new ReplyForm();
            form.setReplyType(type);
            form.setAimId(id);
            form.setUserId(user.getUserId());
            form.setContent(content);
            form.setCommentId(commentId);
            replyService.createReply(form);
            return true;
        }
        return false;
    }

    @PostMapping("/article/delete")
    @ResponseBody
    public boolean delete_reply(@RequestParam("id") Integer id, @RequestParam("type") String type) {
        if (!ObjectUtils.isEmpty(getUser())) {

            if (type.equals("comment")) {
                commentService.deleteComment(id);
                return true;
            } else if (type.equals("reply")) {
                replyService.deleteReply(id);
                return true;
            }
        }
        return false;
    }

    @GetMapping("/article/pub")
    public String pub_article(Model model) {
        User master = getUser();
        if (!ObjectUtils.isEmpty(master)) {
            model.addAttribute("user", master);
            model.addAttribute("informs", informService.findByAuthorIdAndStatus(master.getUserId(), InformStatusEnum.UNREAD.getStatus()).size());
            List<ArticleCategory> list = articleCategoryService.findAll();
            model.addAttribute("categorys", list);
        }
        return "thymeleaf/customer/detail/pub_article";
    }

    @PostMapping("/article/pub_main")
    @ResponseBody
    public String pub_main(@RequestParam("title") String title,
                           @RequestParam("content") String content,
                           @RequestParam("category") String category) {
        User user = getUser();
        if (!ObjectUtils.isEmpty(user)) {
            ArticleForm form = new ArticleForm();
            form.setAuthorId(user.getUserId());
            form.setTitle(title);
            form.setContent(content);
            form.setArticleCategory(category);

            return articleService.publish(form).getArticleId().toString();
        }
        return null;
    }

}
