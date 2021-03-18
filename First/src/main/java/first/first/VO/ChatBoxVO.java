package first.first.VO;


import lombok.Data;

import java.util.Date;

/**
 * @创建人 weizc
 * @创建时间 2018/10/30 9:28
 */

@Data
public class ChatBoxVO {
    private Integer boxId;

    private String icon;

    private String nickName;

    private String time;

    private String message;

    private String type;

    private Integer unReadCount = 0;
}
