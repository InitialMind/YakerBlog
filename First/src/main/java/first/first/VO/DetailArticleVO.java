package first.first.VO;

import first.first.Entity.*;
import first.first.Enum.*;
import first.first.Service.*;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import javax.annotation.PostConstruct;
import javax.xml.ws.Action;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @创建人 weizc
 * @创建时间 2018/9/26 15:02
 */
@Component
public class DetailArticleVO {
    @Autowired
    CommentService commentService;
    @Autowired
    ReplyService replyService;
    @Autowired
    ArticleService articleService;
    @Autowired
    UserService userService;
    @Autowired
    AdmireService admireService;
    private static DetailArticleVO detailArticleVO;

    @PostConstruct
    public void init() {
        detailArticleVO = this;
        detailArticleVO.commentService = this.commentService;
        detailArticleVO.replyService = this.replyService;
        detailArticleVO.userService = this.userService;
        detailArticleVO.articleService = this.articleService;
        detailArticleVO.admireService = this.admireService;
    }

    public static String getArticleDetail(Article article, Pageable pageable, User master) {
        Map<String, Object> res = new HashMap<>();
        List<Map<String, Object>> resMap = new ArrayList<>();
        SimpleDateFormat format = new SimpleDateFormat("yy-MM-dd HH:mm");
        List<Comment> commentList = detailArticleVO.commentService.findByArticleIdAndStatus(article.getArticleId(), CommentStatusEnum.ON.getStatus(), pageable).getContent();
        if (!ObjectUtils.isEmpty(commentList)) {
            for (Comment comment : commentList) {
                Map<String, Object> dataMap = new HashMap<>();
                User user = detailArticleVO.userService.findOne(comment.getUserId());
                if (ObjectUtils.isEmpty(user)) {
                    continue;
                } else {
                    dataMap.put("nickName", user.getNickName());
                    dataMap.put("icon", user.getIcon());
                    dataMap.put("userId", user.getUserId());
                }
                dataMap.put("master", comment.getUserId());
                dataMap.put("commentId", comment.getCommentId());
                dataMap.put("content", comment.getContent());
                dataMap.put("admires", comment.getAdmireCount());
                dataMap.put("replys", comment.getReplyCount());
                Admire admire = detailArticleVO.admireService.findByTypeAndAimIdAndUserId(AdmireTypeEnum.COMMENT.getType(), comment.getCommentId(), master.getUserId());
                if (ObjectUtils.isEmpty(admire) || admire.getAdmireStatus().equals(AdmireStatusEnum.NO.getStatus())) {

                    dataMap.put("admireStatus", "N");
                } else {
                    dataMap.put("admireStatus", "Y");
                }
                dataMap.put("time", format.format(comment.getCreateTime()));
                List<Map<String, Object>> replyMapList = new ArrayList<>();
                List<Reply> replyList = detailArticleVO.replyService.findByReplyStatusAndCommentId(ReplyEnum.ON.getStatus(), comment.getCommentId());
                if (!ObjectUtils.isEmpty(replyList)) {
                    for (Reply reply : replyList) {
                        Map<String, Object> replyMap = new HashMap<>();
                        User replyUser = detailArticleVO.userService.findOne(reply.getUserId());
                        if (ObjectUtils.isEmpty(replyUser)) {
                            continue;
                        } else {
                            replyMap.put("master", reply.getUserId());
                            replyMap.put("nickName", replyUser.getNickName());
                            replyMap.put("replyUserId", replyUser.getUserId());
                            replyMap.put("content", reply.getContent());
                            replyMap.put("time", format.format(reply.getCreateTime()));
                            replyMap.put("admires", reply.getAdmireCount());
                            replyMap.put("replyId", reply.getReplyId());
                            Admire replyAdmire = detailArticleVO.admireService.findByTypeAndAimIdAndUserId(AdmireTypeEnum.REPLY.getType(), reply.getReplyId(), master.getUserId());
                            if (ObjectUtils.isEmpty(replyAdmire) || replyAdmire.getAdmireStatus().equals(AdmireStatusEnum.NO.getStatus())) {

                                replyMap.put("admireStatus", "N");
                            } else {
                                replyMap.put("admireStatus", "Y");
                            }
                        }
                        if (reply.getReplyType().equals(ReplyTypeEnum.COMMENT.getType())) {
                            User user2 = detailArticleVO.userService.findOne(comment.getUserId());
                            if (!ObjectUtils.isEmpty(user2)) {
                                replyMap.put("toUser", user2.getNickName());
                                replyMap.put("toUserId", user2.getUserId());

                            } else {
                                continue;
                            }

                        } else if (reply.getReplyType().equals(ReplyTypeEnum.REPLY.getType())) {
                            Reply reply1 = detailArticleVO.replyService.findByReplyId(reply.getAimId());
                            if (!ObjectUtils.isEmpty(reply1)) {
                                User user1 = detailArticleVO.userService.findOne(reply1.getUserId());
                                if (!ObjectUtils.isEmpty(user1)) {
                                    replyMap.put("toUser", user1.getNickName());
                                    replyMap.put("toUserId", user1.getUserId());
                                } else {
                                    continue;
                                }
                            } else {
                                continue;
                            }
                        }
                        replyMapList.add(replyMap);
                    }
                    dataMap.put("replys", replyMapList);
                } else {
                    dataMap.put("replys", "");
                }

                resMap.add(dataMap);
            }
            res.put("data", resMap);
        } else {
            //没人评论
            res.put("data", "还没有人评论");
        }
        return new JSONObject(res).toString();
    }
}
