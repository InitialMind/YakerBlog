package first.first.Entity;

import first.first.Enum.ChatBoxExistEnum;
import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.springframework.context.annotation.Lazy;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * @创建人 weizc
 * @创建时间 2018/10/27 9:18
 */
@Data
@Entity
@DynamicInsert
public class GroupChat {

    @Id
    @GeneratedValue
    private Integer groupId;
    /**
     * 群主Id
     */
    private Integer masterId;
    /**
     * 群昵称
     */
    private String nickName;
    /**
     * 群头像
     */
    private String icon;
    /**
     * 群状态
     */
    private Integer existStatus = ChatBoxExistEnum.YES.getCode();
    /**
     * 群消息
     */
    @Fetch(FetchMode.SUBSELECT)
    @ManyToMany(cascade = {CascadeType.REFRESH}, fetch = FetchType.EAGER)
    private List<Chat> content;
    /**
     * 群成员
     */
    @Fetch(FetchMode.SUBSELECT)
    @ManyToMany(cascade = {CascadeType.REFRESH}, fetch = FetchType.EAGER)
    private List<User> groupUser;
    /**
     * 创建时间
     */
    private Date createTime;
}
