package first.first.Repository;

import first.first.Entity.Reply;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @创建人 weizc
 * @创建时间 2018/8/14 14:15
 */
public interface ReplyRepository extends JpaRepository<Reply, Integer> {
    Reply findByReplyId(Integer replyId);

    Page<Reply> findByReplyTypeAndReplyStatus(Integer type, Integer status, Pageable pageable);

    List<Reply> findByReplyStatusAndCommentId(Integer status, Integer commentId);

    List<Reply> findByUserIdAndReplyStatus(Integer userId, Integer status);


}
