package first.first.CustomerController;

import first.first.Entity.*;
import first.first.Enum.ChatBoxExistEnum;
import first.first.Enum.ChatTypeEnum;
import first.first.Enum.InformStatusEnum;
import first.first.Enum.InformTypeEnum;
import first.first.Repository.ChatBoxRepository;
import first.first.Repository.GroupChatRepository;
import first.first.Service.*;
import first.first.VO.ChatBoxVO;
import first.first.VO.ChatVO;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @创建人 weizc
 * @创建时间 2018/10/29 8:23
 */
@Controller
@RequestMapping("/customer/chat")
public class DetailChatController {

    @Autowired
    ChatService chatService;
    @Autowired
    UserService userService;
    @Autowired
    FocusFansService focusFansService;
    @Autowired
    ChatBoxService chatBoxService;
    @Autowired
    GroupChatService groupChatService;
    @Autowired
    GroupChatRepository groupChatRepository;
    @Autowired
    ChatBoxRepository chatBoxRepository;
    @Autowired
    InformService informService;

    @GetMapping("/index")
    public String index(Model model, @RequestParam(value = "id", required = false) Integer id) {

        User user = getUser();
        if (!ObjectUtils.isEmpty(user)) {
            if (!ObjectUtils.isEmpty(id)) {
                List<Integer> list = new ArrayList<>();
                list.add(user.getUserId());
                list.add(id);
                ChatBox chatBox1 = chatBoxService.findByMasterIdAndAIdInAndBIdInAndChatBoxType(user.getUserId(), list, list, ChatTypeEnum.PERSONAL_CHAT.getType());
                if (ObjectUtils.isEmpty(chatBox1)) {
                    ChatBox chatBox = new ChatBox();
                    chatBox.setAId(user.getUserId());
                    chatBox.setBId(id);
                    chatBox.setChatBoxType(ChatTypeEnum.PERSONAL_CHAT.getType());
                    chatBox.setMasterId(user.getUserId());
                    chatBoxService.save(chatBox);
                }
                chatBox1.setExistStatus(ChatBoxExistEnum.YES.getCode());
                chatBoxService.save(chatBox1);
            }
            List<ChatBoxVO> chatBoxVOS = chatBoxService.getResult(chatBoxService.findByMasterIdAndExistStatus(user.getUserId(), ChatBoxExistEnum.YES.getCode()));
            model.addAttribute("Boxs", chatBoxVOS);
            model.addAttribute("user", user);
            List<User> users = focusFansService.findByFansId(user.getUserId());
            List<Map<String, Object>> list = new ArrayList<>();
            List<Map<String, Object>> list1 = new ArrayList<>();
            for (User friend : users) {
                Map<String, Object> map = new HashMap<>();
                map.put("icon", friend.getIcon());
                map.put("nickName", friend.getNickName());
                map.put("id", friend.getUserId());
                list.add(map);
            }
            Map<String, Object> map1 = new HashMap<>();
            map1.put("data", list);
            model.addAttribute("friendList", new JSONObject(map1).toString());
            for (ChatBox chatBox : chatBoxService.findByMasterIdAndChatBoxType(user.getUserId(), ChatTypeEnum.GROUP_CHAT.getType())) {
                GroupChat groupChat = groupChatService.findByGroupId(chatBox.getBId());
                if (!ObjectUtils.isEmpty(groupChat) && groupChatService.inGroup(groupChat.getGroupId(), user.getUserId())) {
                    Map<String, Object> map = new HashMap<>();
                    map.put("icon", groupChat.getIcon());
                    map.put("nickName", groupChat.getNickName());
                    map.put("id", groupChat.getGroupId());
                    list1.add(map);
                }
            }
            Map<String, Object> map2 = new HashMap<>();
            map2.put("data", list1);
            model.addAttribute("groupList", new JSONObject(map2).toString());
        }


        return "thymeleaf/customer/chat/chat";
    }

    @GetMapping("/getUser")
    @ResponseBody
    public User getUser() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        HttpSession session = request.getSession();
        if (!ObjectUtils.isEmpty(session.getAttribute("username"))) {
            return userService.findByUserName(session.getAttribute("username").toString());
        }
        return null;
    }

    @PostMapping("/index/with")
    @ResponseBody
    public String chatWith(@RequestParam("id") Integer boxId, @RequestParam("type") String type) {
        User user = getUser();
        ChatBox box = chatBoxService.findByBoxId(boxId);
        if (!ObjectUtils.isEmpty(box)) {
            box.setUnReadCount(0);
            List<Inform> list = new ArrayList<>();
            if (box.getMasterId().equals(box.getAId())) {
                list = informService.findByAuthorIdAndInformTypeAndInformStatusAndUserId(box.getMasterId(), InformTypeEnum.CHAT.getType(), InformStatusEnum.UNREAD.getStatus(), box.getBId());
            } else {
                list = informService.findByAuthorIdAndInformTypeAndInformStatusAndUserId(box.getMasterId(), InformTypeEnum.CHAT.getType(), InformStatusEnum.UNREAD.getStatus(), box.getAId());
            }
            for (Inform inform : list) {
                inform.setInformStatus(InformStatusEnum.READ.getStatus());
                informService.save(inform);
            }
            chatBoxService.save(box);
        } else {
            return null;
        }

        if (type.equals("chat")) {
            List<Integer> list = new ArrayList<>();
            list.add(box.getAId());
            list.add(box.getBId());
            PageRequest request = PageRequest.of(0, 10, new Sort(Sort.Direction.DESC, "createTime"));
            Page<Chat> page = chatService.findBySendIdInAndReceiveIdInAndChatType(list, list, request, ChatTypeEnum.PERSONAL_CHAT.getType());
            if (box.getAId().equals(box.getMasterId())) {
                return ChatVO.getResult(page, box.getBId(), "chat");
            } else {
                return ChatVO.getResult(page, box.getAId(), "chat");
            }
        } else if (type.equals("groupChat")) {
            GroupChat groupChat = groupChatService.findByGroupId(box.getBId());
            if (!ObjectUtils.isEmpty(groupChat)) {
                for (User user1 : groupChat.getGroupUser()) {
                    if (user1.getUserId().equals(user.getUserId())) {
                        PageRequest request = PageRequest.of(0, 10, new Sort(Sort.Direction.DESC, "createTime"));
                        Page<Chat> page = chatService.findByReceiveIdAndChatType(box.getBId(), ChatTypeEnum.GROUP_CHAT.getType(), request);
                        return ChatVO.getResult(page, box.getBId(), "groupChat");
                    }
                }
                return null;
            }

        }
        return null;
    }

    @PostMapping("/inGroup")
    @ResponseBody
    public boolean inGroup(@RequestParam("boxId") Integer boxId) {
        User user = getUser();
        ChatBox chatBox = chatBoxService.findByBoxId(boxId);
        if (!ObjectUtils.isEmpty(chatBox) && groupChatService.inGroup(chatBox.getBId(), user.getUserId())) {
            return true;
        }

        return false;
    }

    @PostMapping("/unRead")
    @ResponseBody
    public String unRead(@RequestParam("boxId") String boxId) {

        return chatBoxService.unRead(Integer.parseInt(boxId)).getUnReadCount().toString();
    }

    @PostMapping("/getMsg")
    @ResponseBody
    public String getMsg(@RequestParam("boxId") Integer boxId) {
        ChatBox chatBox = chatBoxService.findByBoxId(boxId);
        SimpleDateFormat format = new SimpleDateFormat("MM-dd hh:mm");
        String res = "";
        if (!ObjectUtils.isEmpty(chatBox)) {
            chatBox.setUnReadCount(chatBox.getUnReadCount() + 1);
            chatBox.setExistStatus(ChatBoxExistEnum.YES.getCode());
            chatBoxService.save(chatBox);
            res += "\"unRead\":\"" + chatBox.getUnReadCount().toString() + "\"";
            if (chatBox.getChatBoxType().equals(ChatTypeEnum.PERSONAL_CHAT.getType())) {
                User user = userService.findOne(chatBox.getMasterId());
                if (!ObjectUtils.isEmpty(user)) {
                    res += ",\"icon\":\"" + user.getIcon() + "\",\"nickName\":\"" + user.getNickName() + "\"";
                }
                return "{" + res + "}";
            } else {
                GroupChat groupChat = groupChatService.findByGroupId(chatBox.getBId());
                if (!ObjectUtils.isEmpty(groupChat)) {
                    res += ",\"icon\":\"" + groupChat.getIcon() + "\",\"nickName\":\"" + groupChat.getNickName() + "\"";
                }
                return "{" + res + "}";
            }

        }
        return null;
    }

    @PostMapping("/delBox")
    @ResponseBody
    public boolean delBox(@RequestParam("boxId") Integer boxId) {
        ChatBox chatBox = chatBoxService.findByBoxId(boxId);
        if (!ObjectUtils.isEmpty(chatBox) && chatBox.getExistStatus().equals(ChatBoxExistEnum.YES.getCode())) {
            chatBox.setExistStatus(ChatBoxExistEnum.NO.getCode());
            chatBoxService.save(chatBox);
            return true;
        }
        return false;
    }

    @PostMapping("/newBox")
    @ResponseBody
    public String newBox(@RequestParam("aimId") Integer aimId, @RequestParam("type") String type) {
        User user = getUser();
        if (!ObjectUtils.isEmpty(user)) {
            if (type.equals("chat")) {
                List<Integer> list = new ArrayList<>();
                list.add(user.getUserId());
                list.add(aimId);
                ChatBox chatBox = chatBoxService.findByMasterIdAndAIdInAndBIdInAndChatBoxType(user.getUserId(), list, list, ChatTypeEnum.PERSONAL_CHAT.getType());
                if (ObjectUtils.isEmpty(chatBox)) {
                    ChatBox chatBox1 = new ChatBox();
                    chatBox1.setMasterId(user.getUserId());
                    chatBox1.setChatBoxType(ChatTypeEnum.PERSONAL_CHAT.getType());
                    chatBox1.setAId(user.getUserId());
                    chatBox1.setBId(aimId);
                    return chatBoxService.save(chatBox1).getBoxId().toString();
                } else {
                    chatBox.setExistStatus(ChatBoxExistEnum.YES.getCode());
                    return chatBoxService.save(chatBox).getBoxId().toString();
                }
            } else {
                ChatBox chatBox = chatBoxService.findByMasterIdAndChatBoxTypeAndBId(user.getUserId(), ChatTypeEnum.GROUP_CHAT.getType(), aimId);
                if (!ObjectUtils.isEmpty(chatBox) && groupChatService.inGroup(chatBox.getBId(), user.getUserId())) {
                    chatBox.setExistStatus(ChatBoxExistEnum.YES.getCode());
                    return chatBoxService.save(chatBox).getBoxId().toString();
                }
                return null;
            }
        }
        return null;
    }

    @GetMapping("/group_manager")
    public String group_manager(@RequestParam("id") Integer boxId, Model model) {
        User user = getUser();
        if (!ObjectUtils.isEmpty(user)) {
            ChatBox chatBox = chatBoxService.findByBoxId(boxId);
            if (!ObjectUtils.isEmpty(chatBox)) {
                GroupChat groupChat = groupChatService.findByGroupId(chatBox.getBId());
                if (!ObjectUtils.isEmpty(groupChat)) {
                    model.addAttribute("group", groupChat);
                    User master = userService.findOne(groupChat.getMasterId());
                    if (!ObjectUtils.isEmpty(master)) {
                        model.addAttribute("master", master);
                        if (user.getUserId().equals(master.getUserId())) {
                            model.addAttribute("same", "Y");
                        } else {
                            model.addAttribute("same", "N");
                        }
                    }
                }
            }
            model.addAttribute("fans", focusFansService.findByFocusId(user.getUserId()));

        }
        return "thymeleaf/customer/chat/group_manager";
    }

    @Transactional
    @PostMapping("/group_manager/del_member")
    @ResponseBody
    public boolean del_member(@RequestParam("userId") Integer id, @RequestParam("groupId") Integer groupId) {
        GroupChat groupChat = groupChatService.findByGroupId(groupId);
        if (!ObjectUtils.isEmpty(groupChat)) {
            if (groupChat.getMasterId().equals(id)) {
                groupChat.getGroupUser().clear();
                groupChat.setExistStatus(ChatBoxExistEnum.NO.getCode());
                groupChatService.save(groupChat);
                List<ChatBox> list = chatBoxService.findByChatBoxTypeAndBId(ChatTypeEnum.GROUP_CHAT.getType(), groupChat.getGroupId());
                for (ChatBox chatBox : list) {
                    chatBoxService.deleteByBoxId(chatBox.getBoxId());
                }
            } else {
                for (User user : groupChat.getGroupUser()) {
                    if (user.getUserId().equals(id)) {
                        groupChat.getGroupUser().remove(user);
                        groupChatService.save(groupChat);
                        break;
                    }
                }
            }
            ChatBox chatBox = chatBoxService.findByMasterIdAndChatBoxTypeAndBId(id, ChatTypeEnum.GROUP_CHAT.getType(), groupChat.getGroupId());
            if (!ObjectUtils.isEmpty(chatBox)) {
                chatBox.setExistStatus(ChatBoxExistEnum.NO.getCode());
                chatBoxService.save(chatBox);
            }
            return true;
        }
        return false;
    }

    @PostMapping("/group_manager/add")
    @ResponseBody
    public boolean group_add(@RequestParam("groupId") Integer groupId,
                             @RequestParam("list") String members) {
        String[] list = members.split(" ");
        GroupChat groupChat = groupChatService.findByGroupId(groupId);
        if (!ObjectUtils.isEmpty(groupChat)) {
            out:
            for (String s : list) {
                User user = userService.findOne(Integer.parseInt(s));
                if (!ObjectUtils.isEmpty(user)) {
                    for (User user1 : groupChat.getGroupUser()) {
                        if (user.getUserId().equals(user1.getUserId())) {
                            break out;
                        }
                    }
                    groupChat.getGroupUser().add(user);
                    ChatBox chatBox = chatBoxService.findByMasterIdAndChatBoxTypeAndBId(user.getUserId(), ChatTypeEnum.GROUP_CHAT.getType(), groupId);
                    if (ObjectUtils.isEmpty(chatBox)) {
                        ChatBox chatBox1 = new ChatBox();
                        chatBox1.setBId(groupId);
                        chatBox1.setMasterId(user.getUserId());
                        chatBox1.setAId(groupChat.getMasterId());
                        chatBox1.setChatBoxType(ChatTypeEnum.GROUP_CHAT.getType());
                        chatBoxService.save(chatBox1);
                    } else {
                        chatBox.setExistStatus(ChatBoxExistEnum.YES.getCode());
                        chatBoxService.save(chatBox);
                    }

                }
            }
            groupChatService.save(groupChat);
            return true;
        }
        return false;
    }

    @Transactional
    @PostMapping("/group_manager/exit")
    @ResponseBody
    public boolean exit(@RequestParam("groupId") Integer groupId) {
        GroupChat groupChat = groupChatService.findByGroupId(groupId);
        User user = getUser();
        if (!ObjectUtils.isEmpty(groupChat) && !ObjectUtils.isEmpty(user)) {
            if (user.getUserId() != groupChat.getMasterId()) {
                for (User user1 : groupChat.getGroupUser()) {
                    if (user.getUserId().equals(user1.getUserId())) {
                        groupChat.getGroupUser().remove(user1);
                        break;
                    }
                }
                ChatBox chatBox = chatBoxService.findByMasterIdAndChatBoxTypeAndBId(user.getUserId(), ChatTypeEnum.GROUP_CHAT.getType(), groupId);
                if (!ObjectUtils.isEmpty(chatBox)) {
                    chatBoxService.deleteByBoxId(chatBox.getBoxId());
                }
                groupChat.setExistStatus(ChatBoxExistEnum.NO.getCode());
                groupChatService.save(groupChat);
            } else {
                groupChat.getGroupUser().clear();
                List<ChatBox> list = chatBoxService.findByChatBoxTypeAndBId(ChatTypeEnum.GROUP_CHAT.getType(), groupId);
                for (ChatBox chatBox : list) {
                    chatBoxService.deleteByBoxId(chatBox.getBoxId());
                }
                groupChat.setExistStatus(ChatBoxExistEnum.NO.getCode());
                groupChatService.save(groupChat);
            }
            return true;
        }
        return false;
    }

    @PostMapping("/group_manager/change_name")
    @ResponseBody
    public boolean change_name(@RequestParam("groupId") Integer groupId,
                               @RequestParam("name") String name) {
        GroupChat groupChat = groupChatService.findByGroupId(groupId);
        if (!ObjectUtils.isEmpty(groupChat)) {
            groupChat.setNickName(name);
            groupChatService.save(groupChat);
            return true;
        }
        return false;
    }

    @RequestMapping("/new_group")
    public String new_group(Model model) {
        model.addAttribute("action", "create");
        User master = getUser();
        if (!ObjectUtils.isEmpty(master)) {
            model.addAttribute("master", master);
            model.addAttribute("fans", focusFansService.findByFocusId(master.getUserId()));
        }

        return "thymeleaf/customer/chat/new_group";
    }

    @PostMapping("/new_group/main")
    @ResponseBody
    public String new_group_main(@RequestParam("name") String name,
                                 @RequestParam("list") String users) {
        User user = getUser();
        GroupChat groupChat = groupChatService.findByNickNameAndExistStatus(name, ChatBoxExistEnum.YES.getCode());
        if (ObjectUtils.isEmpty(groupChat)) {
            GroupChat groupChat1 = new GroupChat();
            groupChat1.setNickName(name);
            groupChat1.setMasterId(user.getUserId());
            groupChat1.setIcon("/images/groupChat_icon.jpg");
            List<User> lis = new ArrayList<>();
            lis.add(user);
            groupChat1.setGroupUser(lis);
            ChatBox chatBox1 = new ChatBox();
            GroupChat res = groupChatRepository.save(groupChat1);
            chatBox1.setMasterId(user.getUserId());
            chatBox1.setAId(user.getUserId());
            chatBox1.setBId(res.getGroupId());
            chatBox1.setChatBoxType(ChatTypeEnum.GROUP_CHAT.getType());
            Map<String, Object> map = new HashMap<>();
            map.put("boxId", chatBoxRepository.save(chatBox1).getBoxId());
            map.put("icon", res.getIcon());
            String nickName = "";
            for (Integer i = 0; i < res.getNickName().length(); i++) {
                nickName += (int) res.getNickName().charAt(i) + ",";
            }
            map.put("nickName", nickName);
            System.out.println(nickName);
            if (!users.equals("")) {
                String[] list = users.split(",");
                for (String s : list) {
                    User member = userService.findOne(Integer.parseInt(s));
                    if (!ObjectUtils.isEmpty(member)) {
                        res.getGroupUser().add(member);
                        ChatBox chatBox = new ChatBox();
                        chatBox.setChatBoxType(ChatTypeEnum.GROUP_CHAT.getType());
                        chatBox.setBId(res.getGroupId());
                        chatBox.setAId(user.getUserId());
                        chatBox.setMasterId(member.getUserId());
                        chatBoxRepository.save(chatBox);
                    }
                }
            }
            groupChatRepository.save(res);
            return new JSONObject(map).toString();

        } else {
            return null;
        }
    }

    @PostMapping("/chat_record")
    @ResponseBody
    public String chat_record(@RequestParam("boxId") Integer boxId,
                              @RequestParam("page") Integer page) {
        User user = getUser();
        ChatBox chatBox = chatBoxService.findByBoxId(boxId);
        if (!ObjectUtils.isEmpty(chatBox)) {
            PageRequest request = PageRequest.of(page + 1, 10, new Sort(Sort.Direction.DESC, "createTime"));
            if (chatBox.getChatBoxType().equals(ChatTypeEnum.PERSONAL_CHAT.getType())) {
                List<Integer> list = new ArrayList<>();
                list.add(chatBox.getAId());
                list.add(chatBox.getBId());
                Page<Chat> chatPage = chatService.findBySendIdInAndReceiveIdInAndChatType(list, list, request, ChatTypeEnum.PERSONAL_CHAT.getType());
                if (user.equals(chatBox.getBId())) {
                    return ChatVO.getResult(chatPage, chatBox.getAId(), "chat");
                } else {
                    return ChatVO.getResult(chatPage, chatBox.getBId(), "chat");
                }

            } else {
                Page<Chat> chatPage = chatService.findByReceiveIdAndChatType(chatBox.getBId(), ChatTypeEnum.GROUP_CHAT.getType(), request);
                return ChatVO.getResult(chatPage, chatBox.getBId(), "groupChat");
            }


        }
        return null;

    }
}
