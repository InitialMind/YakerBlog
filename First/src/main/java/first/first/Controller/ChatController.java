package first.first.Controller;

import first.first.Entity.Chat;
import first.first.Entity.GroupChat;
import first.first.Entity.User;
import first.first.Enum.ChatTypeEnum;
import first.first.Service.ChatService;
import first.first.Service.GroupChatService;
import first.first.Service.UserService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @创建人 weizc
 * @创建时间 2018/11/10 9:50
 */
@Controller
@RequestMapping("/chat")
public class ChatController {
    @Autowired
    ChatService chatService;
    @Autowired
    UserService userService;
    @Autowired
    GroupChatService groupChatService;

    @GetMapping("/index")
    public String index() {

        return "thymeleaf/chat/index";
    }

    @RequestMapping("/data")
    @ResponseBody
    public String data(HttpServletRequest httpServletRequest) {
        Integer size = Integer.parseInt(httpServletRequest.getParameter("limit"));
        Integer pageNum = Integer.parseInt(httpServletRequest.getParameter("page"));
        PageRequest request = PageRequest.of(pageNum - 1, size);
        Page<Chat> page = chatService.findAll(request);
        SimpleDateFormat format = new SimpleDateFormat();
        List<Map<String, Object>> list = new ArrayList<>();
        if (page.getContent().size() > 0) {
            for (Chat chat : page.getContent()) {
                Map<String, Object> map = new HashMap<>();
                map.put("ID", chat.getChatId());
                map.put("content", chat.getContent());
                map.put("time", format.format(chat.getCreateTime()));
                if (chat.getChatType().equals(ChatTypeEnum.PERSONAL_CHAT.getType())) {
                    map.put("type", "私聊");
                    User receive = userService.findOne(chat.getReceiveId());
                    if (!ObjectUtils.isEmpty(receive)) {
                        map.put("receive", receive.getNickName());
                    }
                } else {
                    map.put("type", "群聊");
                    GroupChat groupChat = groupChatService.findByGroupId(chat.getReceiveId());
                    if (!ObjectUtils.isEmpty(groupChat)) {
                        map.put("receive", groupChat.getNickName());
                    }
                }
                User send = userService.findOne(chat.getSendId());
                if (!ObjectUtils.isEmpty(send)) {
                    map.put("send", send.getNickName());
                }

                list.add(map);
            }
        }
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("code", 0);
        resultMap.put("msg", "");
        resultMap.put("count", page.getTotalElements());
        resultMap.put("data", list);
        return new JSONObject(resultMap).toString();
    }

    @Transactional
    @PostMapping("/del")
    @ResponseBody
    public boolean del(@RequestParam("id") Integer chatId) {
        Chat chat = chatService.findByChatId(chatId);
        if (!ObjectUtils.isEmpty(chat)) {
            chatService.deleteByChatId(chatId);
            return true;
        }
        return false;
    }

    @Transactional
    @RequestMapping("/choose")
    @ResponseBody
    public boolean choose(@RequestParam("idList") String ids) {
        String[] list = ids.substring(1, ids.length() - 1).split(",");

        for (String s : list) {
            chatService.deleteByChatId(Integer.parseInt(s));
        }
        return true;
    }
}
