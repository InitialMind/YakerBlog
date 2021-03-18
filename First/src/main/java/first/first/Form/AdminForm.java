package first.first.Form;

import lombok.Data;

/**
 * @创建人 weizc
 * @创建时间 2018/9/17 15:21
 */
@Data
public class AdminForm {
    private Integer adminId;

    private String userName;
    private String userPwd;
    private String nickName;
    private Integer noticeCount;
    private String role;
}
