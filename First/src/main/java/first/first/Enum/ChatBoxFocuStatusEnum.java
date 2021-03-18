package first.first.Enum;

import lombok.Getter;

/**
 * @创建人 weizc
 * @创建时间 2018/10/30 8:39
 */
@Getter
public enum ChatBoxFocuStatusEnum {
    YES(0, "得到焦点"),
    NO(1, "失去焦点"),;
    private Integer code;
    private String msg;

    ChatBoxFocuStatusEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
