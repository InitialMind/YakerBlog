package first.first.Service.Imp;

import first.first.Entity.Article;
import first.first.Entity.Comment;
import first.first.Enum.CommentStatusEnum;
import first.first.Enum.UserEnum;
import first.first.Exception.UserException;
import first.first.Form.CommentForm;
import first.first.Repository.ArticleRepository;
import first.first.Repository.CommentRepository;
import first.first.Service.ArticleService;
import first.first.Service.CommentService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.transaction.Transactional;
import java.util.List;

/**
 * @创建人 weizc
 * @创建时间 2018/8/14 12:59
 */
@Service
public class CommentServiceImp implements CommentService {
    @Autowired
    ArticleService articleService;
    @Autowired
    CommentRepository commentRepository;
    @Autowired
    ArticleRepository articleRepository;

    @Override
    @Transactional
    public Comment createComment(CommentForm form) {
        if (!ObjectUtils.isEmpty(articleService.findByArticleId(form.getArticleId()))) {
            Comment comment = new Comment();
            BeanUtils.copyProperties(form, comment);
            Article article = articleService.findByArticleId(form.getArticleId());
            article.setCommentCount(article.getCommentCount() + 1);
            articleRepository.save(article);
            return commentRepository.save(comment);
        }
        return null;
    }

    @Override
    @Transactional
    public void deleteComment(Integer commentId) {
        Comment comment = commentRepository.findByCommentId(commentId);
        if (!ObjectUtils.isEmpty(comment) && comment.getCommentStatus() != CommentStatusEnum.OFF.getStatus()) {
            Article article = articleService.findByArticleId(comment.getArticleId());
            if (!ObjectUtils.isEmpty(article)) {
                article.setCommentCount(article.getCommentCount() - comment.getReplyCount() - 1);
                articleRepository.save(article);
            }
            comment.setCommentStatus(CommentStatusEnum.OFF.getStatus());
            commentRepository.save(comment);
        }


    }

    @Override
    public Comment findByCommenId(Integer commentId) {
        return commentRepository.findByCommentId(commentId);
    }

    @Override
    public Page<Comment> findAll(Pageable pageable) {
        return commentRepository.findAll(pageable);
    }

    @Override
    public Page<Comment> findAllByCommentStatus(Integer status, Pageable pageable) {
        return commentRepository.findByCommentStatus(status, pageable);
    }

    @Override
    public List<Comment> findAll() {
        return commentRepository.findAll();
    }

    @Override
    public Page<Comment> findByArticleIdAndStatus(Integer articleId, Integer status, Pageable pageable) {
        return commentRepository.findByArticleIdAndCommentStatus(articleId, status, pageable);
    }

    @Override
    public List<Comment> findByUserId(Integer userId) {
        return commentRepository.findByUserId(userId);
    }

    @Override
    public List<Comment> findByCommentIdIn(List<Integer> list) {
        return commentRepository.findByCommentIdIn(list);
    }
}
