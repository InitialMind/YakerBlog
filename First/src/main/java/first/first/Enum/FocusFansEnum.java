package first.first.Enum;

import lombok.Getter;

/**
 * @创建人 weizc
 * @创建时间 2018/8/14 14:26
 */
@Getter
public enum FocusFansEnum {
    YES(0, "已关注"),
    NO(1, "未关注"),;
    private Integer status;
    private String message;

    FocusFansEnum(Integer status, String message) {
        this.status = status;
        this.message = message;
    }
}
