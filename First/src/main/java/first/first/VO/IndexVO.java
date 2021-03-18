package first.first.VO;

import first.first.Entity.Article;
import first.first.Entity.User;
import first.first.Service.UserService;
import lombok.Data;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @创建人 weizc
 * @创建时间 2018/9/19 20:46
 */
@Component
public class IndexVO {
    @Autowired
    UserService userService;
    private static IndexVO indexVO;

    @PostConstruct
    public void init() {
        indexVO = this;
        indexVO.userService = this.userService;
    }

    public static String getIndexApi(Page<Article> page) {
        SimpleDateFormat format = new SimpleDateFormat();
        Map<String, Object> resultMap = new HashMap<>();
        List<Map<String, Object>> mapList = new ArrayList<>();
        List<Article> list = page.getContent();

        for (Article article : list) {
            Map<String, Object> dataMap = new HashMap<>();
            User user = indexVO.userService.findOne(article.getAuthorId());
            dataMap.put("ID", article.getArticleId());
            dataMap.put("icon", user.getIcon());
            dataMap.put("nickName", user.getNickName());
            dataMap.put("userId", user.getUserId());
            dataMap.put("title", article.getTitle());
            dataMap.put("createTime", format.format(article.getCreateTime()));
            dataMap.put("category", article.getArticleCategory());
            mapList.add(dataMap);
        }
        resultMap.put("data", mapList);
        resultMap.put("pages", page.getTotalPages());
        return new JSONObject(resultMap).toString();
    }
}
