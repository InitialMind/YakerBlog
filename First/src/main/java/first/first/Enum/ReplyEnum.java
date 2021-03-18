package first.first.Enum;

import lombok.Getter;

/**
 * @创建人 weizc
 * @创建时间 2018/8/14 14:11
 */
@Getter
public enum ReplyEnum {
    ON(0, "未删除"),
    OFF(1, "已删除"),;
    private Integer status;
    private String message;

    ReplyEnum(Integer status, String message) {
        this.status = status;
        this.message = message;
    }
}
