package first.first.Enum;

import lombok.Getter;

/**
 * @创建人 weizc
 * @创建时间 2018/8/13 9:34
 */
@Getter
public enum UserLevel {
    ONE(1, 100L),
    TWO(2, 300L),
    THREE(3, 600L),
    FOUR(4, 1000L),
    FIVE(5, 1500L),
    SIX(6, 2100L),
    SEVEN(7, 2800L),
    EIGHT(8, 3600L),
    NINE(9, 4500L),
    TEN(10, 5500L),;

    private Integer level;
    private Long experience;

    UserLevel(Integer level, Long experience) {
        this.experience = experience;
        this.level = level;
    }
}
