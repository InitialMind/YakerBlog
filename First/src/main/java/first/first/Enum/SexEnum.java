package first.first.Enum;

import lombok.Getter;

/**
 * @创建人 weizc
 * @创建时间 2018/8/12 18:11
 */
@Getter
public enum SexEnum {
    MAN(0, "男"),
    WOMAN(1, "女"),;
    private Integer code;
    private String sex;

    SexEnum(Integer code, String sex) {
        this.code = code;
        this.sex = sex;
    }
}
