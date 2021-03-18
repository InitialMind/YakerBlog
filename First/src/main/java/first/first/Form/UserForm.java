package first.first.Form;

import first.first.Enum.SexEnum;
import lombok.Data;

import java.util.Date;

/**
 * @创建人 weizc
 * @创建时间 2018/9/13 10:03
 */
@Data
public class UserForm {
    private Integer userId;
    /**
     * 用户名
     */
    private String userName;
    /**
     * 用户密码
     */
    private String userPwd;
    private String nickName;
    /**
     * 性别
     */
    private String userSex;
    /**
     * 出生日期
     */
    private String birthday;
    /**
     * 户籍
     */
    private String province;
    private String city;
    private String action;
    private Integer pageNum;
    private String statement;
}
