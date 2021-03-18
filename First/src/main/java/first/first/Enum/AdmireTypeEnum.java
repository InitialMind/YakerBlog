package first.first.Enum;

import lombok.Getter;

/**
 * @创建人 weizc
 * @创建时间 2018/8/13 10:48
 */
@Getter
public enum AdmireTypeEnum {
    ARTICLE(0, "文章点赞"),
    COMMENT(1, "评论点赞"),
    REPLY(2, "回复点赞");

    private Integer Type;
    private String message;

    AdmireTypeEnum(Integer type, String message) {
        Type = type;
        this.message = message;
    }
}
