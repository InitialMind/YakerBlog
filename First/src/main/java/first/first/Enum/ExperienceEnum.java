package first.first.Enum;

import lombok.Getter;

/**
 * @创建人 weizc
 * @创建时间 2018/8/13 10:02
 */
@Getter
public enum ExperienceEnum {
    ADMIRE(1L, "admire"),
    COMMENT(1L, "comment");
    private Long experience;
    private String message;

    ExperienceEnum(Long experience, String message) {
        this.experience = experience;
        this.message = message;
    }
}
