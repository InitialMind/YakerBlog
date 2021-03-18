package first.first.VO;

import first.first.Entity.*;
import first.first.Enum.AdmireTypeEnum;
import first.first.Service.*;
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
 * @创建时间 2018/9/23 9:16
 */
@Component
public class AdmireVO {
    @Autowired
    AdmireService admireService;
    @Autowired
    UserService userService;
    @Autowired
    CommentService commentService;
    @Autowired
    ArticleService articleService;
    @Autowired
    ReplyService replyService;
    private static AdmireVO admireVO;

    @PostConstruct
    public void init() {
        admireVO = this;
        admireVO.admireService = this.admireService;
        admireVO.articleService = this.articleService;
        admireVO.commentService = this.commentService;
        admireVO.userService = this.userService;
        admireVO.replyService = this.replyService;
    }

    public static String getResult(Page<Admire> page) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:MM:SS");
        List<Map<String, Object>> mapList = new ArrayList<>();
        for (Admire admire : page.getContent()) {
            Map<String, Object> dataMap = new HashMap<>();
            dataMap.put("ID", admire.getAdmireId());
            if (admire.getType().equals(AdmireTypeEnum.ARTICLE.getType())) {
                Article article = admireVO.articleService.findByArticleId(admire.getAimId());
                if (!ObjectUtils.isEmpty(article)) {
                    dataMap.put("title", article.getTitle());
                } else {
                    dataMap.put("title", "");
                }
            } else if (admire.getType().equals(AdmireTypeEnum.COMMENT.getType())) {
                Comment comment = admireVO.commentService.findByCommenId(admire.getAimId());
                if (!ObjectUtils.isEmpty(comment)) {
                    dataMap.put("title", comment.getContent());
                } else {
                    dataMap.put("title", "");
                }
            } else {
                Reply reply = admireVO.replyService.findByReplyId(admire.getAimId());
                if (!ObjectUtils.isEmpty(reply)) {
                    dataMap.put("title", reply.getContent());
                } else {
                    dataMap.put("title", "");
                }

            }
            User user = admireVO.userService.findOne(admire.getUserId());
            if (!ObjectUtils.isEmpty(user)) {
                dataMap.put("master", user.getNickName());
            } else {
                dataMap.put("master", "");
            }
            dataMap.put("updateTime", format.format(admire.getUpdateTime()));
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
