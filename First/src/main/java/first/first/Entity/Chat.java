package first.first.Entity;

import lombok.Data;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Date;

/**
 * @创建人 weizc
 * @创建时间 2018/10/27 9:12
 */
@Data
@Entity
@DynamicInsert
public class Chat {
    @Id
    @GeneratedValue
    /** 私信Id*/
    private Integer chatId;
    /**
     * 发送人的Id
     */
    private Integer sendId;
    /**
     * 接受人的Id
     */
    private Integer receiveId;
    /**
     * 发送的内容
     */
    private String content;
    /**
     * 发送的时间
     */
    private Date createTime;
    /**
     * 聊天的类型
     */
    private Integer chatType;
}
