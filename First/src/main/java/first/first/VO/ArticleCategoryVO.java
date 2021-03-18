package first.first.VO;

import first.first.Entity.Article;
import first.first.Entity.ArticleCategory;
import first.first.Service.ArticleService;
import first.first.Service.UserService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @创建人 weizc
 * @创建时间 2018/9/16 8:04
 */
@Component
public class ArticleCategoryVO {
    @Autowired
    ArticleService articleService;
    private static ArticleCategoryVO articleCategoryVO;

    @PostConstruct
    public void init() {
        articleCategoryVO = this;
        articleCategoryVO.articleService = this.articleService;
    }

    public static String getArticleCagetgory(Page<ArticleCategory> page) {
        List<Map<String, Object>> mapList = new ArrayList<>();
        PageRequest request = PageRequest.of(0, 10);
        for (ArticleCategory category : page.getContent()) {
            Map<String, Object> map = new HashMap<>();
            Page<Article> articles = articleCategoryVO.articleService.findByCategory(category.getCategoryName(), request);
            map.put("ID", category.getCategoryId());
            map.put("name", category.getCategoryName());
            map.put("articles", articles.getTotalElements());
            mapList.add(map);
        }
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("code", 0);
        resultMap.put("msg", "");
        resultMap.put("count", page.getTotalElements());
        resultMap.put("data", mapList);
        return new JSONObject(resultMap).toString();
    }
}
