package first.first.Enum;

import lombok.Getter;

/**
 * @创建人 weizc
 * @创建时间 2018/10/30 8:37
 */
@Getter
public enum ChatBoxExistEnum {
    YES(0, "存在"),
    NO(1, "已删除"),;
    private Integer code;
    private String msg;

    ChatBoxExistEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
