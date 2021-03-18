package first.first.Form;

import first.first.Enum.CommentStatusEnum;
import lombok.Data;

import java.util.Date;

/**
 * @创建人 weizc
 * @创建时间 2018/8/14 12:58
 */
@Data
public class CommentForm {
    private Integer articleId;
    private Integer userId;
    private String content;

}
