package first.first.VO;

import first.first.Entity.Chat;
import first.first.Entity.GroupChat;
import first.first.Entity.User;
import first.first.Service.ChatBoxService;
import first.first.Service.ChatService;
import first.first.Service.GroupChatService;
import first.first.Service.UserService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import javax.annotation.PostConstruct;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @创建人 weizc
 * @创建时间 2018/10/30 13:12
 */
@Component
public class ChatVO {
    @Autowired
    ChatService chatService;
    @Autowired
    ChatBoxService chatBoxService;
    @Autowired
    UserService userService;
    @Autowired
    GroupChatService groupChatService;
    private static ChatVO chatVO;

    @PostConstruct
    public void init() {
        chatVO = this;
        chatVO.chatBoxService = this.chatBoxService;
        chatVO.chatService = this.chatService;
        chatVO.userService = this.userService;
        chatVO.groupChatService = this.groupChatService;
    }

    public static String getResult(Page<Chat> page, Integer aimId, String type) {
        Map<String, Object> resMap = new HashMap<>();
        List<Chat> chats = page.getContent();
        List<Map<String, Object>> resList = new ArrayList<>();
        SimpleDateFormat format = new SimpleDateFormat("yy-MM-dd");
        SimpleDateFormat format1 = new SimpleDateFormat("HH:mm");

        List<String> timeList = chats.stream().map(e -> format.format(e.getCreateTime())).distinct().collect(Collectors.toList());
        for (String time : timeList) {
            List<Map<String, Object>> parList = new ArrayList<>();
            for (Chat chat : chats) {
                if (format.format(chat.getCreateTime()).equals(time)) {
                    Map<String, Object> childMap = new HashMap<>();
                    childMap.put("content", chat.getContent());
                    childMap.put("masterId", chat.getSendId());
                    childMap.put("time", format1.format(chat.getCreateTime()));
                    User user1 = chatVO.userService.findOne(chat.getSendId());
                    if (!ObjectUtils.isEmpty(user1)) {
                        childMap.put("icon", user1.getIcon());
                    }
                    parList.add(childMap);
                }
            }
            Map<String, Object> parMap = new HashMap<>();
            parMap.put("time", time);
            parMap.put("data", parList);
            resList.add(parMap);
        }
        resMap.put("data", resList);
        if (type.equals("chat")) {
            User user = chatVO.userService.findOne(aimId);
            if (!ObjectUtils.isEmpty(user)) {
                resMap.put("nickName", user.getNickName());
                resMap.put("type", "chat");
            }
        } else {
            GroupChat groupChat = chatVO.groupChatService.findByGroupId(aimId);
            if (!ObjectUtils.isEmpty(groupChat)) {
                resMap.put("nickName", groupChat.getNickName());
                resMap.put("type", "groupChat");
            }
        }

        return new JSONObject(resMap).toString();

    }


}
