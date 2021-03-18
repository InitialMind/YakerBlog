package first.first.Enum;

import lombok.Data;
import lombok.Getter;

/**
 * @创建人 weizc
 * @创建时间 2018/8/12 16:02
 */
@Getter
public enum UserEnum {
    USER_NOT_EXIST(104, "用户不存在！"),
    FORM_ERROR(10, "表单验证错误！"),
    ADMIRE_ERROR(20, "点赞记录异常!"),
    ADMIRE_STATUS_ERROR(21, "点赞状态异常！"),
    ADMIRE_TYPE_ERROR(22, "点赞类型异常!"),
    ARTICLE_NOT_EXIST(30, "文章不存在!"),
    ARTICLECATEGORY_NOT_EXIST(31, "文章分类不存在!"),
    COMMENT_NOT_EXIST(40, "评论不存在!"),
    COMMENT_ALREADY_DELETE(41, "评论已被删除！"),
    FOCUS_STATUS_ERROR(50, "关注状态异常！"),
    REPLY_NOT_EXIST(60, "回复不存在!"),
    REPLY_STATUS_ERROR(61, "回复状态异常！"),
    ROLE_ERROR(70, "权限错误！");
    private Integer code;
    private String message;

    UserEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
