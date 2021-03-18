package first.first.Form;

import lombok.Data;

/**
 * @创建人 weizc
 * @创建时间 2018/9/18 15:28
 */
@Data
public class NoticeForm {
    private Integer noticeId;
    private String title;
    private String content;
    private Integer authorId;

}
