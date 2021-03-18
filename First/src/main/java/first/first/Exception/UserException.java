package first.first.Exception;

import first.first.Enum.UserEnum;

/**
 * @创建人 weizc
 * @创建时间 2018/8/12 16:01
 */
public class UserException extends RuntimeException {
    private Integer code;

    public UserException(UserEnum userEnum) {
        super(userEnum.getMessage());
        this.code = userEnum.getCode();
    }
}
