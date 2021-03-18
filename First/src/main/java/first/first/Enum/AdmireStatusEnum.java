package first.first.Enum;

import lombok.Getter;

/**
 * @创建人 weizc
 * @创建时间 2018/8/13 10:53
 */
@Getter
public enum AdmireStatusEnum {
    YES(1, "已点赞"),
    NO(2, "未点赞"),;
    private Integer status;
    private String message;

    AdmireStatusEnum(Integer status, String message) {
        this.status = status;
        this.message = message;
    }
}
