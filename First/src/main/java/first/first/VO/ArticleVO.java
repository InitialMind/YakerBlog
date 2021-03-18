package first.first.VO;

import first.first.Conver.FocusFans2UserConver;
import first.first.Entity.Article;
import first.first.Entity.User;
import first.first.Service.UserService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @创建人 weizc
 * @创建时间 2018/9/14 15:27
 */
@Component
public class ArticleVO {
    @Autowired
    UserService userService;
    private static ArticleVO articleVO;

    @PostConstruct
    public void init() {
        articleVO = this;
        articleVO.userService = this.userService;
    }

    public static String articleResult(Page<Article> page) {

        List<Map<String, Object>> mapList = new ArrayList<>();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:MM:SS");
        for (Article article : page.getContent()) {
            User user = articleVO.userService.findOne(article.getAuthorId());
            Map<String, Object> map = new HashMap<>();
            map.put("ID", article.getArticleId());
            map.put("title", article.getTitle());
            map.put("author", user.getNickName());
            map.put("category", article.getArticleCategory());
            map.put("createTime", format.format(article.getCreateTime()));
            map.put("admires", article.getAdmireCount());
            map.put("comments", article.getCommentCount());
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
