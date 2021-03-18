package first.first.Enum;

import lombok.Getter;

/**
 * @创建人 weizc
 * @创建时间 2018/9/24 8:40
 */
@Getter
public enum InformTypeEnum {
    COMMENT_ARTICLE(0, "评论文章"),
    REPLY_COMMENT(1, "回复评论"),
    REPLY_REPLY(2, "回复回复"),
    ADMIRE_ARTICLE(3, "点赞文章"),
    ADMIRE_COMMENT(4, "点赞评论"),
    ADMIRE_REPLY(5, "点赞回复"),
    CHAT(6, "私信"),;
    private Integer type;
    private String msg;

    InformTypeEnum(Integer type, String msg) {
        this.type = type;
        this.msg = msg;
    }
}
