package first.first.Entity;

import first.first.Enum.FocusFansEnum;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * @创建人 weizc
 * @创建时间 2018/8/12 18:43
 */
@Entity
@Data
public class FocusFans {
    @Id
    @GeneratedValue
    private Integer id;
    /**
     * 被关注人的id
     */
    private Integer focusId;
    /**
     * 粉丝id
     */
    private Integer fansId;
    /**
     * 关注状态
     */
    private Integer focusStatus = FocusFansEnum.YES.getStatus();
}
