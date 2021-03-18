package first.first.Service.Imp;

import first.first.Entity.Article;
import first.first.Entity.Comment;
import first.first.Entity.Reply;
import first.first.Enum.ReplyEnum;
import first.first.Enum.ReplyTypeEnum;
import first.first.Enum.UserEnum;
import first.first.Exception.UserException;
import first.first.Form.ReplyForm;
import first.first.Repository.ArticleRepository;
import first.first.Repository.CommentRepository;
import first.first.Repository.ReplyRepository;
import first.first.Service.ArticleService;
import first.first.Service.CommentService;
import first.first.Service.ReplyService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.transaction.Transactional;
import java.util.List;

/**
 * @创建人 weizc
 * @创建时间 2018/8/14 14:22
 */
@Service
public class ReplyServiceImp implements ReplyService {
    @Autowired
    ReplyRepository replyRepository;

    @Autowired
    CommentService commentService;
    @Autowired
    CommentRepository commentRepository;
    @Autowired
    ArticleService articleService;
    @Autowired
    ArticleRepository articleRepository;

    @Override
    @Transactional
    public Reply createReply(ReplyForm form) {
        if (form.getReplyType().equals(ReplyTypeEnum.COMMENT.getMessage())) {
            Comment comment = commentService.findByCommenId(form.getAimId());
            if (!ObjectUtils.isEmpty(comment)) {
                Reply reply = new Reply();
                comment.setReplyCount(comment.getReplyCount() + 1);
                commentRepository.save(comment);
                BeanUtils.copyProperties(form, reply);
                reply.setReplyType(ReplyTypeEnum.COMMENT.getType());
                Article article = articleService.findByArticleId(comment.getArticleId());
                if (!ObjectUtils.isEmpty(article)) {
                    article.setCommentCount(article.getCommentCount() + 1);
                    articleRepository.save(article);
                }
                return replyRepository.save(reply);
            }
        } else if (form.getReplyType().equals(ReplyTypeEnum.REPLY.getMessage())) {
            Comment comment = commentService.findByCommenId(form.getCommentId());
            Reply reply = replyRepository.findByReplyId(form.getAimId());
            if (!ObjectUtils.isEmpty(reply)) {
                Reply reply1 = new Reply();
                comment.setReplyCount(comment.getReplyCount() + 1);
                commentRepository.save(comment);
                BeanUtils.copyProperties(form, reply1);
                reply1.setReplyType(ReplyTypeEnum.REPLY.getType());
                Article article = articleService.findByArticleId(comment.getArticleId());
                if (!ObjectUtils.isEmpty(article)) {
                    article.setCommentCount(article.getCommentCount() + 1);
                    articleRepository.save(article);
                }
                return replyRepository.save(reply1);
            }

        }
        return null;

    }

    @Override
    @Transactional
    public void deleteReply(Integer replyId) {
        Reply reply = replyRepository.findByReplyId(replyId);
        if (!ObjectUtils.isEmpty(reply) && reply.getReplyStatus() == ReplyEnum.ON.getStatus()) {
            Comment comment = commentService.findByCommenId(reply.getCommentId());
            comment.setReplyCount(comment.getReplyCount() - 1);
            commentRepository.save(comment);
            Article article = articleService.findByArticleId(comment.getArticleId());
            if (!ObjectUtils.isEmpty(article)) {
                article.setCommentCount(article.getCommentCount() - 1);
                articleRepository.save(article);
            }
            reply.setReplyStatus(ReplyEnum.OFF.getStatus());
            replyRepository.save(reply);
        }


    }

    @Override
    public Reply findByReplyId(Integer replyId) {
        return replyRepository.findByReplyId(replyId);
    }

    @Override
    public Page<Reply> findByTypeAndStatus(Integer type, Integer status, Pageable pageable) {
        return replyRepository.findByReplyTypeAndReplyStatus(type, status, pageable);
    }

    @Override
    public List<Reply> findByReplyStatusAndCommentId(Integer status, Integer commentId) {
        return replyRepository.findByReplyStatusAndCommentId(status, commentId);
    }

    @Override
    public List<Reply> findByReplyStatusAndUserId(Integer status, Integer userId) {
        return replyRepository.findByUserIdAndReplyStatus(userId, status);
    }
}
