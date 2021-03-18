package first.first.Entity;

import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Date;

/**
 * @创建人 weizc
 * @创建时间 2018/9/18 8:22
 */
@Entity
@Data
@DynamicUpdate
@DynamicInsert
public class Notice {
    @Id
    @GeneratedValue
    private Integer noticeId;
    private String title;
    private String content;
    private Integer authorId;
    /**
     * 点赞数
     */
    private Integer admireCount = 0;
    private Date createTime;

    private Date updateTime;

}
