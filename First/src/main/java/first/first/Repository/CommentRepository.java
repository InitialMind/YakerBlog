package first.first.Repository;

import first.first.Entity.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @创建人 weizc
 * @创建时间 2018/8/14 12:52
 */
public interface CommentRepository extends JpaRepository<Comment, Integer> {
    Comment findByCommentId(Integer commentId);

    Page<Comment> findByCommentStatus(Integer status, Pageable pageable);

    Page<Comment> findByArticleIdAndCommentStatus(Integer articleId, Integer status, Pageable pageable);

    List<Comment> findByUserId(Integer userId);

    @Query("select u from Comment u where u.commentId in (?1)")
    List<Comment> findByCommentIdIn(List<Integer> list);
}
