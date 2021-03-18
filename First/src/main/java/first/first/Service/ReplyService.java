package first.first.Service;

import com.sun.org.apache.regexp.internal.RE;
import first.first.Entity.Reply;
import first.first.Form.ReplyForm;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * @创建人 weizc
 * @创建时间 2018/8/14 14:15
 */
public interface ReplyService {
    Reply createReply(ReplyForm form);

    void deleteReply(Integer replyId);

    Reply findByReplyId(Integer replyId);

    Page<Reply> findByTypeAndStatus(Integer type, Integer status, Pageable pageable);

    List<Reply> findByReplyStatusAndCommentId(Integer status, Integer commentId);

    List<Reply> findByReplyStatusAndUserId(Integer status, Integer userId);

}
