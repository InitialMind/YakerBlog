package first.first.CustomerController;

import first.first.Entity.*;
import first.first.Enum.AdmireTypeEnum;
import first.first.Enum.InformStatusEnum;
import first.first.Enum.ReplyEnum;
import first.first.Repository.ArticleRepository;
import first.first.Service.*;
import first.first.Tools.InformTool;
import first.first.VO.IndexVO;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import sun.security.util.Length;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @创建人 weizc
 * @创建时间 2018/9/19 13:16
 */
@Controller
@RequestMapping("customer")
public class CustomerController {
    @Autowired
    UserService userService;
    @Autowired
    ArticleService articleService;
    @Autowired
    ArticleCategoryService articleCategoryService;
    @Autowired
    NoticeService noticeService;
    @Autowired
    InformService informService;
    @Autowired
    ArticleRepository articleRepository;
    @Autowired
    FocusFansService focusFansService;
    @Autowired
    AdmireService admireService;
    @Autowired
    CommentService commentService;
    @Autowired
    ReplyService replyService;
    @Autowired
    ChatBoxService chatBoxService;

    @GetMapping("/index")
    public String index(Model model, HttpSession session) {
        Integer count = 0;
        if (!ObjectUtils.isEmpty(session.getAttribute("username"))) {

            User user = userService.findByUserName(session.getAttribute("username").toString());
            for (ChatBox chatBox : chatBoxService.findByMasterId(user.getUserId())) {
                count += chatBox.getUnReadCount();
            }
            model.addAttribute("user", user);
            model.addAttribute("informs", informService.findByAuthorIdAndStatus(user.getUserId(), InformStatusEnum.UNREAD.getStatus()).size() + count);
        }
        PageRequest request = PageRequest.of(0, 5);
        model.addAttribute("notices", noticeService.findAll(request).getContent());
        model.addAttribute("categorys", articleCategoryService.findAll());
        return "thymeleaf/customer/index/index";
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

    @GetMapping("/index/api/list")
    @ResponseBody
    public String indexApiList(@RequestParam("page") Integer page,
                               @RequestParam(value = "categoryId", required = false) Integer categoryId,
                               @RequestParam(value = "focusArticle", required = false) Integer focus,
                               @RequestParam(value = "admireArticle", required = false) Integer admire,
                               @RequestParam(value = "commentArticle", required = false) Integer comment) {
        User user = getUser();
        PageRequest request = PageRequest.of(page - 1, 10, new Sort(Sort.Direction.DESC, "createTime"));
        if (!ObjectUtils.isEmpty(categoryId)) {
            ArticleCategory articleCategory = articleCategoryService.findByArticleId(categoryId);
            if (!ObjectUtils.isEmpty(articleCategory)) {
                return IndexVO.getIndexApi(articleService.findByCategory(articleCategory.getCategoryName(), request));
            }
            return null;
        } else if (!ObjectUtils.isEmpty(focus)) {
            List<Integer> list = focusFansService.findByFansId(user.getUserId()).stream().map(e -> e.getUserId()).collect(Collectors.toList());
            return IndexVO.getIndexApi(articleService.findByAuthorIdIn(list, request));
        } else if (!ObjectUtils.isEmpty(admire)) {
            List<Integer> list = admireService.findByTypeAndUserId(AdmireTypeEnum.ARTICLE.getType(), user.getUserId()).stream().map(e -> e.getAimId()).collect(Collectors.toList());
            return IndexVO.getIndexApi(articleService.findByArticleIdIn(list, request));
        } else if (!ObjectUtils.isEmpty(comment)) {
            List<Integer> list1 = commentService.findByUserId(user.getUserId()).stream().map(e -> e.getArticleId()).collect(Collectors.toList());
            List<Integer> list2 = replyService.findByReplyStatusAndUserId(ReplyEnum.ON.getStatus(), user.getUserId()).stream().map(e -> e.getCommentId()).collect(Collectors.toList());
            list1.addAll(commentService.findByCommentIdIn(list2).stream().map(e -> e.getArticleId()).collect(Collectors.toList()));
            return IndexVO.getIndexApi(articleService.findByArticleIdIn(list1, request));
        } else {
            return IndexVO.getIndexApi(articleService.findAll(request));
        }

    }


}
