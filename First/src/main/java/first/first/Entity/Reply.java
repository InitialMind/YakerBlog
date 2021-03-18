package first.first.Entity;

import first.first.Enum.ReplyEnum;
import lombok.Data;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

/**
 * @创建人 weizc
 * @创建时间 2018/8/14 14:06
 */
@Data
@Entity
@DynamicInsert
public class Reply {
    @Id
    @GeneratedValue
    /** 回复id*/
    private Integer replyId;
    /**
     * 回复的评论id
     */
    private Integer aimId;
    /**
     * 回复人的id
     */
    private Integer userId;
    /**
     * 回复的内容
     */
    private String content;
    /**
     * 回复时间
     */
    private Date createTime;
    /**
     * 点赞数
     */
    private Integer admireCount = 0;
    /**
     * 回复类型
     */
    private Integer replyType;
    /**
     * 回复的评论
     */
    private Integer commentId;
    /**
     * 回复的状态
     */
    private Integer replyStatus = ReplyEnum.ON.getStatus();
}
