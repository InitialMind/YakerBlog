package first.first.Entity;

import first.first.Enum.ChatBoxExistEnum;
import first.first.Enum.ChatBoxFocuStatusEnum;
import first.first.Enum.ChatTypeEnum;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Date;

/**
 * @创建人 weizc
 * @创建时间 2018/10/30 8:24
 */
@Entity
@Data
public class ChatBox {
    @GeneratedValue
    @Id
    private Integer boxId;
    /**
     * 会话框归属Id
     */
    private Integer masterId;
    /**
     * 会话框的两个用户
     */
    private Integer aId;
    private Integer bId;
    /**
     * 会话框的存在状态
     */
    private Integer existStatus = ChatBoxExistEnum.YES.getCode();
    /**
     * 未读信息数量
     */
    private Integer unReadCount = 0;
    /**
     * 会话框的类型
     */
    private Integer chatBoxType;

}
