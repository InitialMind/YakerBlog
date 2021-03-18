package first.first.VO;

import first.first.Entity.*;
import first.first.Enum.InformStatusEnum;
import first.first.Enum.InformTypeEnum;
import first.first.Service.*;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
 * @创建时间 2018/11/8 11:30
 */
@Component
public class DetailInformVO {
    @Autowired
    UserService userService;
    @Autowired
    InformService informService;
    @Autowired
    ArticleService articleService;
    @Autowired
    CommentService commentService;
    @Autowired
    ReplyService replyService;
    @Autowired
    AdmireService admireService;

    private static DetailInformVO detailInformVO;

    @PostConstruct
    private void init() {
        detailInformVO = this;
        detailInformVO.informService = this.informService;
        detailInformVO.userService = this.userService;
        detailInformVO.articleService = this.articleService;
        detailInformVO.commentService = this.commentService;
        detailInformVO.replyService = this.replyService;
        detailInformVO.admireService = this.admireService;
    }

    public static String getResult(String action, User user, Pageable pageable) {
        Map<String, Object> res = new HashMap<>();
        SimpleDateFormat format = new SimpleDateFormat();
        List<Map<String, Object>> list = new ArrayList<>();
        if (action.equals("comment")) {
            Page<Inform> page = detailInformVO.informService.findByAuthorIdAndInformType(user.getUserId(), InformTypeEnum.COMMENT_ARTICLE.getType(), pageable);
            if (page.getContent().size() > 0) {
                for (Inform inform : page.getContent()) {
                    inform.setInformStatus(InformStatusEnum.READ.getStatus());
                    detailInformVO.informService.save(inform);
                    Map<String, Object> childMap = new HashMap<>();
                    childMap.put("action", "comment");
                    childMap.put("time", format.format(inform.getCreateTime()));
                    childMap.put("content", inform.getContent());
                    Article article = detailInformVO.articleService.findByArticleId(inform.getAimId());
                    if (!ObjectUtils.isEmpty(article)) {
                        childMap.put("aimId", inform.getAimId());
                        childMap.put("aimTitle", article.getTitle());
                    }
                    User master = detailInformVO.userService.findOne(inform.getUserId());
                    if (!ObjectUtils.isEmpty(master)) {
                        childMap.put("nickName", master.getNickName());
                        childMap.put("icon", master.getIcon());
                        childMap.put("userId", master.getUserId());
                    }
                    list.add(childMap);
                }
            }
            res.put("data", list);
            res.put("pages", page.getTotalPages());
            return new JSONObject(res).toString();
        } else if (action.equals("reply")) {
            List<Integer> types = new ArrayList<>();
            types.add(InformTypeEnum.REPLY_COMMENT.getType());
            types.add(InformTypeEnum.REPLY_REPLY.getType());
            Page<Inform> page = detailInformVO.informService.findByAuthorIdAndInformTypeIn(user.getUserId(), types, pageable);
            if (page.getContent().size() > 0) {
                for (Inform inform : page.getContent()) {
                    inform.setInformStatus(InformStatusEnum.READ.getStatus());
                    detailInformVO.informService.save(inform);
                    Map<String, Object> childMap = new HashMap<>();
                    childMap.put("time", format.format(inform.getCreateTime()));
                    childMap.put("content", inform.getContent());
                    if (inform.getInformType().equals(InformTypeEnum.REPLY_COMMENT.getType())) {
                        childMap.put("action", "comment");
                        Comment comment = detailInformVO.commentService.findByCommenId(inform.getAimId());
                        if (!ObjectUtils.isEmpty(comment)) {
                            childMap.put("aimId", inform.getAimId());
                            childMap.put("aimTitle", comment.getContent());
                        }

                    }
                    if (inform.getInformType().equals(InformTypeEnum.REPLY_REPLY.getType())) {
                        childMap.put("action", "reply");
                        Reply reply = detailInformVO.replyService.findByReplyId(inform.getAimId());
                        if (!ObjectUtils.isEmpty(reply)) {
                            childMap.put("aimId", inform.getAimId());
                            childMap.put("aimTitle", reply.getContent());
                        }
                    }
                    User master = detailInformVO.userService.findOne(inform.getUserId());
                    if (!ObjectUtils.isEmpty(master)) {
                        childMap.put("nickName", master.getNickName());
                        childMap.put("icon", master.getIcon());
                        childMap.put("userId", master.getUserId());
                    }
                    list.add(childMap);
                }
            }
            res.put("data", list);
            res.put("pages", page.getTotalPages());
            return new JSONObject(res).toString();
        } else if (action.equals("admire")) {
            List<Integer> types = new ArrayList<>();
            types.add(InformTypeEnum.ADMIRE_REPLY.getType());
            types.add(InformTypeEnum.ADMIRE_COMMENT.getType());
            types.add(InformTypeEnum.ADMIRE_ARTICLE.getType());
            Page<Inform> page = detailInformVO.informService.findByAuthorIdAndInformTypeIn(user.getUserId(), types, pageable);
            if (page.getContent().size() > 0) {
                for (Inform inform : page.getContent()) {
                    inform.setInformStatus(InformStatusEnum.READ.getStatus());
                    detailInformVO.informService.save(inform);
                    Map<String, Object> childMap = new HashMap<>();
                    childMap.put("time", format.format(inform.getCreateTime()));
                    childMap.put("content", inform.getContent());
                    if (inform.getInformType().equals(InformTypeEnum.ADMIRE_ARTICLE.getType())) {
                        childMap.put("action", "article");
                        Article article = detailInformVO.articleService.findByArticleId(inform.getAimId());
                        if (!ObjectUtils.isEmpty(article)) {
                            childMap.put("aimId", inform.getAimId());
                            childMap.put("aimTitle", article.getTitle());
                        }
                    } else if (inform.getInformType().equals(InformTypeEnum.ADMIRE_COMMENT.getType())) {
                        childMap.put("action", "comment");
                        Comment comment = detailInformVO.commentService.findByCommenId(inform.getAimId());
                        if (!ObjectUtils.isEmpty(comment)) {
                            childMap.put("aimId", inform.getAimId());
                            childMap.put("aimTitle", comment.getContent());
                        }
                    } else if (inform.getInformType().equals(InformTypeEnum.ADMIRE_REPLY.getType())) {
                        childMap.put("action", "reply");
                        Reply reply = detailInformVO.replyService.findByReplyId(inform.getAimId());
                        if (!ObjectUtils.isEmpty(reply)) {
                            childMap.put("aimId", inform.getAimId());
                            childMap.put("aimTitle", reply.getContent());
                        }
                    }
                    User master = detailInformVO.userService.findOne(inform.getUserId());
                    if (!ObjectUtils.isEmpty(master)) {
                        childMap.put("nickName", master.getNickName());
                        childMap.put("icon", master.getIcon());
                        childMap.put("userId", master.getUserId());
                    }
                    list.add(childMap);
                }
            }
            res.put("data", list);
            res.put("pages", page.getTotalPages());
            return new JSONObject(res).toString();
        }
        return null;
    }
}
