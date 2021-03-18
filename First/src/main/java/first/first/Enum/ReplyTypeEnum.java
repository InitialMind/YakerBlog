package first.first.Enum;

import lombok.Data;
import lombok.Getter;

/**
 * @创建人 weizc
 * @创建时间 2018/8/14 21:29
 */
@Getter
public enum ReplyTypeEnum {
    COMMENT(0, "回复评论"),
    REPLY(1, "回复回复"),;
    private Integer type;
    private String message;

    ReplyTypeEnum(Integer type, String message) {
        this.type = type;
        this.message = message;
    }
}
