package first.first.Entity;

import first.first.Enum.InformStatusEnum;
import lombok.Data;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Date;

/**
 * @创建人 weizc
 * @创建时间 2018/9/24 8:37
 */
@Data
@Entity
@DynamicInsert
public class Inform {
    @Id
    @GeneratedValue
    private Integer informId;
    private Integer userId;
    private Integer aimId;
    private String content;
    private Date createTime;
    private Integer informType;
    private Integer authorId;
    private Integer informStatus = InformStatusEnum.UNREAD.getStatus();
}
