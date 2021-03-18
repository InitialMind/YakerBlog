package first.first.Enum;


import lombok.Getter;


/**
 * @创建人 weizc
 * @创建时间 2018/10/27 9:28
 */
@Getter
public enum ChatTypeEnum {
    PERSONAL_CHAT(0, "私聊"),
    GROUP_CHAT(1, "群聊"),;
    private Integer type;
    private String msg;

    ChatTypeEnum(Integer type, String msg) {
        this.type = type;
        this.msg = msg;
    }
}
