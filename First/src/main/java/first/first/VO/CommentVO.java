package first.first.VO;

import first.first.Entity.Article;
import first.first.Entity.Comment;
import first.first.Entity.User;
import first.first.Service.ArticleService;
import first.first.Service.UserService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import javax.annotation.PostConstruct;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @创建人 weizc
 * @创建时间 2018/9/22 14:02
 */
@Component
public class CommentVO {
    @Autowired
    ArticleService articleService;
    @Autowired
    UserService userService;
    private static CommentVO commentVO;

    @PostConstruct
    public void init() {
        commentVO = this;
        commentVO.articleService = this.articleService;
        commentVO.userService = this.userService;
    }

    public static String getResult(Page<Comment> page) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:MM:SS");
        List<Map<String, Object>> mapList = new ArrayList<>();
        for (Comment comment : page.getContent()) {
            Map<String, Object> dataMap = new HashMap<>();
            dataMap.put("ID", comment.getCommentId());
            Article article = commentVO.articleService.findByArticleId(comment.getArticleId());
            if (!ObjectUtils.isEmpty(article)) {
                dataMap.put("title", article.getTitle());
            } else {
                dataMap.put("title", "");
            }
            dataMap.put("content", comment.getContent());
            User user = commentVO.userService.findOne(comment.getUserId());
            if (!ObjectUtils.isEmpty(user)) {

                dataMap.put("master", user.getNickName());
            } else {
                dataMap.put("master", "");
            }
            dataMap.put("createTime", format.format(comment.getCreateTime()));
            dataMap.put("admires", comment.getAdmireCount());
            dataMap.put("replys", comment.getReplyCount());
            mapList.add(dataMap);
        }
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("code", 0);
        resultMap.put("msg", "");
        resultMap.put("count", page.getTotalElements());
        resultMap.put("data", mapList);
        return new JSONObject(resultMap).toString();
    }
}
