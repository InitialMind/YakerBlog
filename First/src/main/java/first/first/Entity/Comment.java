package first.first.Entity;

import first.first.Enum.CommentStatusEnum;
import lombok.Data;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

/**
 * @创建人 weizc
 * @创建时间 2018/8/13 20:52
 */
@Data
@Entity
@DynamicInsert
public class Comment {
    @Id
    @GeneratedValue
    /** 评论id*/
    private Integer commentId;
    /**
     * 评论的文章id
     */
    private Integer articleId;
    /**
     * 评论人的id
     */
    private Integer userId;
    /**
     * 评论的内容
     */
    private String content;
    /**
     * 评论时间
     */
    private Date createTime;
    /**
     * 点赞数
     */
    private Integer admireCount = 0;
    /**
     * 回复数
     */
    private Integer replyCount = 0;
    /**
     * 评论的状态
     **/
    private Integer commentStatus = CommentStatusEnum.ON.getStatus();
}
