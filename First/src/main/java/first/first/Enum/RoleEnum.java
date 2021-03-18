package first.first.Enum;

import lombok.Data;
import lombok.Getter;

/**
 * @创建人 weizc
 * @创建时间 2018/9/10 9:08
 */
@Getter
public enum RoleEnum {
    ADMIN(1, "管理员"),
    USER(2, "用户"),
    SUPER_ADMIN(3, "超级管理员"),;
    private Integer code;
    private String roleName;

    RoleEnum(Integer code, String roleName) {
        this.code = code;
        this.roleName = roleName;
    }
}
