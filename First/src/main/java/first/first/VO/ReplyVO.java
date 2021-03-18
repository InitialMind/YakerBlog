package first.first.VO;

import first.first.Entity.Article;
import first.first.Entity.Comment;
import first.first.Entity.Reply;
import first.first.Entity.User;
import first.first.Enum.ReplyTypeEnum;
import first.first.Service.CommentService;
import first.first.Service.ReplyService;
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
 * @创建时间 2018/9/22 16:01
 */
@Component
public class ReplyVO {
    @Autowired
    CommentService commentService;
    @Autowired
    ReplyService replyService;
    @Autowired
    UserService userService;
    private static ReplyVO replyVO;

    @PostConstruct
    public void init() {
        replyVO = this;
        replyVO.commentService = this.commentService;
        replyVO.replyService = this.replyService;
        replyVO.userService = this.userService;
    }

    public static String setResult(Page<Reply> page) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:MM:SS");
        List<Map<String, Object>> mapList = new ArrayList<>();
        for (Reply reply : page.getContent()) {
            Map<String, Object> dataMap = new HashMap<>();
            dataMap.put("ID", reply.getReplyId());
            if (reply.getReplyType().equals(ReplyTypeEnum.COMMENT.getType())) {
                Comment comment = replyVO.commentService.findByCommenId(reply.getAimId());
                if (!ObjectUtils.isEmpty(comment)) {
                    dataMap.put("title", comment.getContent());
                } else {
                    dataMap.put("title", "");
                }
            } else {
                Reply reply1 = replyVO.replyService.findByReplyId(reply.getAimId());
                if (!ObjectUtils.isEmpty(reply1)) {
                    dataMap.put("title", reply1.getContent());
                } else {
                    dataMap.put("title", "");
                }
            }
            dataMap.put("content", reply.getContent());
            User user = replyVO.userService.findOne(reply.getUserId());
            if (!ObjectUtils.isEmpty(user)) {

                dataMap.put("master", user.getNickName());
            } else {
                dataMap.put("master", "");
            }
            dataMap.put("createTime", format.format(reply.getCreateTime()));
            dataMap.put("admires", reply.getAdmireCount());
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

