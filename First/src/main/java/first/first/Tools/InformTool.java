package first.first.Tools;

import first.first.Entity.*;
import first.first.Enum.AdmireStatusEnum;
import first.first.Enum.InformStatusEnum;
import first.first.Enum.InformTypeEnum;
import first.first.Service.*;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
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
 * @创建时间 2018/9/25 9:20
 */
@Component
public class InformTool {
    @Autowired
    ArticleService articleService;
    @Autowired
    CommentService commentService;
    @Autowired
    ReplyService replyService;
    @Autowired
    InformService informService;
    @Autowired
    AdmireService admireService;
    @Autowired
    UserService userService;

    private static InformTool informTool;

    @PostConstruct
    public void init() {
        informTool = this;
        informTool.admireService = this.admireService;
        informTool.commentService = this.commentService;
        informTool.articleService = this.articleService;
        informTool.informService = this.informService;
        informTool.replyService = this.replyService;
        informTool.userService = this.userService;
    }

    public static List<Map<String, Object>> getResult(Integer userId) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:MM:SS");

        List<Map<String, Object>> mapList = new ArrayList<>();
        List<Inform> informList = informTool.informService.findByAuthorIdAndStatus(userId, InformStatusEnum.UNREAD.getStatus());
        if (!ObjectUtils.isEmpty(informList)) {


            for (Inform inform : informList) {
                Map<String, Object> map = new HashMap<>();
                // 1.获取昵称
                User user = informTool.userService.findOne(inform.getUserId());
                if (!ObjectUtils.isEmpty(user)) {
                    map.put("nickName", user.getNickName());
                } else {
                    map.put("nickName", "");
                }
                // 2.获取动作
                if (inform.getInformType().equals(InformTypeEnum.COMMENT_ARTICLE.getType())) {
                    map.put("action", "评论");
                    map.put("type", "文章");
                    Article article = informTool.articleService.findByArticleId(inform.getAimId());
                    if (!ObjectUtils.isEmpty(article)) {
                        map.put("title", article.getTitle());
                    } else {
                        map.put("title", "");
                    }
                } else if (inform.getInformType().equals(InformTypeEnum.REPLY_COMMENT.getType())) {
                    map.put("action", "回复");
                    map.put("type", "评论");
                    Comment comment = informTool.commentService.findByCommenId(inform.getAimId());
                    if (!ObjectUtils.isEmpty(comment)) {
                        map.put("title", comment.getContent());
                    } else {
                        map.put("title", "");
                    }
                } else if (inform.getInformType().equals(InformTypeEnum.REPLY_REPLY.getType())) {
                    map.put("action", "回复");
                    map.put("type", "回复");
                    Reply reply = informTool.replyService.findByReplyId(inform.getAimId());
                    if (!ObjectUtils.isEmpty(reply)) {
                        map.put("title", reply.getContent());
                    } else {
                        map.put("title", "");
                    }
                } else if (inform.getInformType().equals(InformTypeEnum.ADMIRE_ARTICLE.getType())) {
                    map.put("action", "点赞");
                    map.put("type", "文章");
                    Article article = informTool.articleService.findByArticleId(inform.getAimId());
                    if (!ObjectUtils.isEmpty(article)) {
                        map.put("title", article.getTitle());
                    } else {
                        map.put("title", "");
                    }
                } else if (inform.getInformType().equals(InformTypeEnum.ADMIRE_COMMENT.getType())) {
                    map.put("action", "点赞");
                    map.put("type", "评论");
                    Comment comment = informTool.commentService.findByCommenId(inform.getAimId());
                    if (!ObjectUtils.isEmpty(comment)) {
                        map.put("title", comment.getContent());
                    } else {
                        map.put("title", "");
                    }
                } else if (inform.getInformType().equals(InformTypeEnum.ADMIRE_REPLY.getType())) {
                    map.put("action", "点赞");
                    map.put("type", "评论");
                    Reply reply = informTool.replyService.findByReplyId(inform.getAimId());
                    if (!ObjectUtils.isEmpty(reply)) {
                        map.put("title", reply.getContent());
                    } else {
                        map.put("title", "");
                    }
                }
                // 3.获取内容和时间
                map.put("content", inform.getContent());
                map.put("time", format.format(inform.getCreateTime()));
                mapList.add(map);

            }

        } else {
            return null;
        }
        return mapList;
    }
}
