package first.first.Controller;

import first.first.Entity.Article;
import first.first.Entity.ArticleCategory;
import first.first.Entity.User;
import first.first.Enum.UserEnum;
import first.first.Exception.UserException;
import first.first.Form.ArticleForm;
import first.first.Repository.ArticleCategoryRepository;
import first.first.Repository.ArticleRepository;
import first.first.Service.ArticleCategoryService;
import first.first.Service.ArticleService;
import first.first.Service.UserService;
import first.first.VO.ArticleCategoryVO;
import first.first.VO.ArticleVO;
import org.json.JSONObject;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.jws.WebParam;
import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @创建人 weizc
 * @创建时间 2018/9/14 17:40
 */
@Controller
@RequestMapping("article")
public class ArticleController {
    @Autowired
    ArticleService articleService;
    @Autowired
    ArticleRepository articleRepository;
    @Autowired
    UserService userService;
    @Autowired
    ArticleCategoryService articleCategoryService;
    @Autowired
    ArticleCategoryRepository articleCategoryRepository;

    @GetMapping("/manager")
    public String magager() {
        return "thymeleaf/article/manager";
    }

    @RequestMapping("/manager/data")
    @ResponseBody
    public String data(HttpServletRequest httpServletRequest) {
        Integer size = Integer.parseInt(httpServletRequest.getParameter("limit"));
        Integer pageNum = Integer.parseInt(httpServletRequest.getParameter("page"));
        PageRequest request = PageRequest.of(pageNum - 1, size);
        return ArticleVO.articleResult(articleService.findAll(request));
    }

    @RequestMapping("/manager/main")
    public String article_main(@RequestParam(value = "id", required = false) Integer id,
                               @RequestParam("pageNum") Integer pageNum,
                               Model model) {
        if (!ObjectUtils.isEmpty(id)) {
            Article article = articleService.findByArticleId(id);
            User author = userService.findOne(article.getAuthorId());
            model.addAttribute("author", author.getNickName());
            model.addAttribute("article", article);
        }
        List<ArticleCategory> list = articleCategoryService.findAll();
        List<String> categorys = list.stream().map(e -> e.getCategoryName()).collect(Collectors.toList());
        model.addAttribute("categorys", categorys);

        return "thymeleaf/article/manager_main";
    }

    @PostMapping("/manager/edit")
    @ResponseBody
    public boolean manager_edit(@Valid ArticleForm form, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new UserException(UserEnum.FORM_ERROR);
        }
        Article article = articleService.findByArticleId(form.getArticleId());
        if (ObjectUtils.isEmpty(article)) {
            throw new UserException(UserEnum.ARTICLE_NOT_EXIST);
        }
        BeanUtils.copyProperties(form, article);
        articleRepository.save(article);
        return true;

    }

    @Transactional
    @PostMapping("/manager/del")
    @ResponseBody
    public boolean manager_del(@RequestParam("id") Integer id) {
        Article article = articleService.findByArticleId(id);
        if (ObjectUtils.isEmpty(article)) {
            throw new UserException(UserEnum.ARTICLE_NOT_EXIST);
        }
        ArticleForm form = new ArticleForm();
        BeanUtils.copyProperties(article, form);
        articleService.delArticle(form);
        return true;
    }

    @Transactional
    @PostMapping("/manager/choose")
    @ResponseBody
    public boolean manager_choose(@RequestParam("idList") String idlist) {
        String ids = idlist.substring(1, idlist.length() - 1);

        for (String id : ids.split(",")) {
            Article article = articleService.findByArticleId(Integer.parseInt(id));
            if (ObjectUtils.isEmpty(article)) {
                throw new UserException(UserEnum.ARTICLE_NOT_EXIST);
            }
            ArticleForm form = new ArticleForm();
            BeanUtils.copyProperties(article, form);
            articleService.delArticle(form);
        }
        return true;
    }

    @GetMapping("/category")
    public String category() {
        return "thymeleaf/article/category";
    }

    @RequestMapping("/category/data")
    @ResponseBody
    public String category_data(HttpServletRequest httpServletRequest) {
        Integer size = Integer.parseInt(httpServletRequest.getParameter("limit"));
        Integer pageNum = Integer.parseInt(httpServletRequest.getParameter("page"));
        PageRequest request = PageRequest.of(pageNum - 1, size);
        return ArticleCategoryVO.getArticleCagetgory(articleCategoryService.findAll(request));
    }

    @RequestMapping("/category/edit")
    public String category_edit(@RequestParam(value = "id", required = false) Integer id,
                                @RequestParam("action") String action,
                                Model model) {
        if (action.equals("edit")) {
            ArticleCategory category = articleCategoryService.findByArticleId(id);
            model.addAttribute("category", category);
        }
        return "thymeleaf/article/category_main";
    }

    @PostMapping("/category/name")
    @ResponseBody
    public String category_name(@RequestParam("id") Integer id,
                                @RequestParam("name") String name) {
        ArticleCategory category = articleCategoryService.findByCategoryName(name);
        if (ObjectUtils.isEmpty(category) || category.getCategoryId().equals(id)) {
            if (ObjectUtils.isEmpty(id)) {
                ArticleCategory category1 = new ArticleCategory();
                category1.setCategoryName(name);
                articleCategoryRepository.save(category1);
                return new JSONObject(category1).toString();
            } else {
                ArticleCategory category1 = articleCategoryService.findByArticleId(id);
                List<Article> list = articleService.findByCategory(category1.getCategoryName());
                category1.setCategoryName(name);
                for (Article article : list) {
                    article.setArticleCategory(name);
                    articleRepository.save(article);
                }
                articleCategoryRepository.save(category1);
                return new JSONObject(category1).toString();
            }

        }
        return "error";
    }

    @Transactional
    @RequestMapping("/category/del")
    @ResponseBody
    public boolean category_del(@RequestParam("id") Integer id) {
        articleCategoryService.deleteByCategoryId(id);
        return true;
    }
}
