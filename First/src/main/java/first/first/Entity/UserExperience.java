package first.first.Entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * @创建人 weizc
 * @创建时间 2018/9/16 15:26
 */
@Entity
@Data
public class UserExperience {
    @Id
    @GeneratedValue
    private Integer id;

    private Integer level;
    private Long experience;
}
