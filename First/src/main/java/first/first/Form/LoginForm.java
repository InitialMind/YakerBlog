package first.first.Form;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @创建人 weizc
 * @创建时间 2018/8/18 14:14
 */
@Data
public class LoginForm {
    @NotNull
    private String loginName;
    @NotNull
    private String loginPwd;
}
