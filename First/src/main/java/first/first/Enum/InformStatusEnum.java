package first.first.Enum;

import lombok.Getter;

/**
 * @创建人 weizc
 * @创建时间 2018/9/24 8:45
 */
@Getter
public enum InformStatusEnum {
    READ(1, "已读"),
    UNREAD(0, "未读"),;
    private Integer status;
    private String msg;

    InformStatusEnum(Integer status, String msg) {
        this.status = status;
        this.msg = msg;
    }
}
