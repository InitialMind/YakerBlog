package first.first.Service;

import first.first.Entity.Comment;
import first.first.Form.CommentForm;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * @创建人 weizc
 * @创建时间 2018/8/14 12:55
 */
public interface CommentService {
    Comment createComment(CommentForm form);


    void deleteComment(Integer commentId);

    Comment findByCommenId(Integer commentId);

    Page<Comment> findAll(Pageable pageable);

    Page<Comment> findAllByCommentStatus(Integer status, Pageable pageable);

    List<Comment> findAll();

    Page<Comment> findByArticleIdAndStatus(Integer articleId, Integer status, Pageable pageable);

    List<Comment> findByUserId(Integer userId);

    List<Comment> findByCommentIdIn(List<Integer> list);
}
