package first.first.Form;

import first.first.Enum.ReplyEnum;
import lombok.Data;

import java.util.Date;

/**
 * @创建人 weizc
 * @创建时间 2018/8/14 14:20
 */
@Data
public class ReplyForm {

    /**
     * 回复的目标id
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
     * 回复类型
     */
    private String replyType;
    /**
     * 回复的评论id
     */
    private Integer commentId;


}
