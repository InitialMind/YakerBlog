package first.first.Entity;

import first.first.Enum.AdmireStatusEnum;
import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

/**
 * @创建人 weizc
 * @创建时间 2018/8/13 10:15
 */
@Data
@Entity
@DynamicInsert
@DynamicUpdate
public class Admire {
    @Id
    @GeneratedValue
    private Integer admireId;
    private Integer aimId;
    private Integer type;
    private Integer userId;
    private Date createTime;
    private Date updateTime;
    private Integer admireStatus = AdmireStatusEnum.YES.getStatus();

}
