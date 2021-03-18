package first.first.Handles;

import first.first.Entity.*;
import first.first.Enum.ChatBoxExistEnum;
import first.first.Enum.ChatTypeEnum;
import first.first.Enum.InformTypeEnum;
import first.first.Repository.ChatBoxRepository;
import first.first.Repository.ChatRepository;
import first.first.Service.ChatBoxService;
import first.first.Service.ChatService;
import first.first.Service.GroupChatService;
import first.first.Service.InformService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @创建人 weizc
 * @创建时间 2018/10/31 12:46
 */
@Service
public class MyHandler extends TextWebSocketHandler {
    @Autowired
    ChatService chatService;
    @Autowired
    GroupChatService groupChatService;
    @Autowired
    ChatBoxService chatBoxService;
    @Autowired
    ChatRepository chatRepository;
    @Autowired
    InformService informService;

    //在线用户列表
    private static final Map<Integer, WebSocketSession> users;
    //用户标识
    private static final String CLIENT_ID = "userId";

    static {
        users = new HashMap<>();
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        System.out.println("成功建立连接");
        Integer userId = getClientId(session);
        System.out.println(userId);
        if (userId != null) {
            users.put(userId, session);
//            session.sendMessage(new TextMessage("成功建立socket连接"));

        }
    }

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) {
        // ...
        System.out.println(message.getPayload());
        JSONObject jsonObject = new JSONObject(message.getPayload());
        Integer boxId = (Integer) jsonObject.get("boxId");
        String content = (String) jsonObject.get("content");
        String[] strings = content.split(" ");
        String contents = "";
        for (String s : strings) {
            contents += (char) Integer.parseInt(s);
        }
        Integer sendId = (Integer) jsonObject.get("sendId");
        String icon = (String) jsonObject.get("icon");
        Date time = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

        ChatBox box = chatBoxService.findByBoxId(boxId);
        if (!ObjectUtils.isEmpty(box)) {
            if (box.getChatBoxType().equals(ChatTypeEnum.PERSONAL_CHAT.getType())) {
                Integer toBoxId = 1;
                String msg = "";
                Chat chat = new Chat();
                chat.setChatType(ChatTypeEnum.PERSONAL_CHAT.getType());
                if (box.getMasterId().equals(box.getAId())) {
                    ChatBox chatBox = chatBoxService.findByMasterIdAndAIdAndBId(box.getBId(), box.getAId(), box.getBId());
                    chat.setSendId(box.getAId());
                    chat.setReceiveId(box.getBId());
                    chat.setContent(contents);
                    chat.setCreateTime(new Date());
                    chatRepository.save(chat);
                    if (ObjectUtils.isEmpty(chatBox)) {
                        ChatBox chatBox1 = new ChatBox();
                        chatBox1.setMasterId(box.getBId());
                        chatBox1.setAId(box.getAId());
                        chatBox1.setBId(box.getBId());
                        chatBox1.setChatBoxType(ChatTypeEnum.PERSONAL_CHAT.getType());
                        toBoxId = chatBoxService.save(chatBox1).getBoxId();
                    } else {
                        toBoxId = chatBox.getBoxId();
                    }
                    msg = "{\"boxId\":\"chat_" + toBoxId.toString() + "\",\"content\":\"" + content + "\",\"icon\":\"" + icon + "\"}";
                    sendMessageToUser(box.getBId(), new TextMessage(msg));

                } else {
                    chat.setSendId(box.getBId());
                    chat.setReceiveId(box.getAId());
                    chat.setContent(contents);
                    chat.setCreateTime(new Date());
                    chatRepository.save(chat);
                    ChatBox chatBox = chatBoxService.findByMasterIdAndAIdAndBId(box.getAId(), box.getAId(), box.getBId());
                    if (ObjectUtils.isEmpty(chatBox)) {
                        ChatBox chatBox1 = new ChatBox();
                        chatBox1.setMasterId(box.getAId());
                        chatBox1.setAId(box.getAId());
                        chatBox1.setBId(box.getBId());
                        chatBox1.setChatBoxType(ChatTypeEnum.PERSONAL_CHAT.getType());
                        toBoxId = chatBoxService.save(chatBox1).getBoxId();
                    } else {
                        toBoxId = chatBox.getBoxId();
                    }
                    msg = "{\"boxId\":\"chat_" + toBoxId.toString() + "\",\"content\":\"" + content + "\",\"icon\":\"" + icon + "\"}";
                    sendMessageToUser(box.getAId(), new TextMessage(msg));
                }

            } else {

                String msg = "";
                Integer toBoxId = 1;
                GroupChat groupChat = groupChatService.findByGroupId(box.getBId());
                List<User> users = groupChat.getGroupUser();
                Chat chat = new Chat();
                chat.setSendId(box.getMasterId());
                chat.setReceiveId(box.getBId());
                chat.setCreateTime(new Date());
                chat.setChatType(ChatTypeEnum.GROUP_CHAT.getType());
                chat.setContent(contents);
                List<Chat> list = groupChat.getContent();
                list.add(chatService.save(chat));
                groupChat.setContent(list);
                groupChatService.save(groupChat);
                for (User user : users) {
                    if (user.getUserId() != sendId) {

                        ChatBox chatBox = chatBoxService.findByMasterIdAndChatBoxTypeAndBId(user.getUserId(), ChatTypeEnum.GROUP_CHAT.getType(), box.getBId());
                        if (ObjectUtils.isEmpty(chatBox)) {
                            ChatBox chatBox1 = new ChatBox();
                            chatBox1.setChatBoxType(ChatTypeEnum.GROUP_CHAT.getType());
                            chatBox1.setBId(box.getBId());
                            chatBox1.setAId(box.getAId());
                            chatBox1.setMasterId(user.getUserId());
                            toBoxId = chatBoxService.save(chatBox1).getBoxId();
                        } else {
                            toBoxId = chatBox.getBoxId();
                        }

                        msg = "{\"boxId\":\"groupChat_" + toBoxId.toString() + "\",\"content\":\"" + content + "\",\"icon\":\"" + icon + "\"}";
                        sendMessageToUser(user.getUserId(), new TextMessage(msg));
                    }
                }
            }
        }

//            WebSocketMessage message1 = new TextMessage("server:" + message);

//        try {
//            session.sendMessage(message1);
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }

    /**
     * 发送信息给指定用户
     *
     * @param clientId
     * @param message
     * @return
     */
    public boolean sendMessageToUser(Integer clientId, TextMessage message) {

        JSONObject jsonObject = new JSONObject(message.getPayload());
        ChatBox chatBox = chatBoxService.findByBoxId(Integer.parseInt(jsonObject.get("boxId").toString().split("_")[1]));
        if (users.get(clientId) == null) {
            chatBox.setUnReadCount(chatBox.getUnReadCount() + 1);
            chatBox.setExistStatus(ChatBoxExistEnum.YES.getCode());
            chatBoxService.save(chatBox);
            Inform inform = new Inform();
            inform.setInformType(InformTypeEnum.CHAT.getType());
            inform.setAuthorId(chatBox.getMasterId());
            inform.setAimId(chatBox.getBoxId());
            if (chatBox.getMasterId().equals(chatBox.getAId())) {
                inform.setUserId(chatBox.getBId());
            } else {
                inform.setUserId(chatBox.getAId());
            }
            informService.save(inform);
            return false;
        }
        WebSocketSession session = users.get(clientId);
//        System.out.println("sendMessage:" + session);
        if (!session.isOpen()) return false;
        try {
            session.sendMessage(message);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * 广播信息
     *
     * @param message
     * @return
     */
    public boolean sendMessageToAllUsers(TextMessage message) {
        boolean allSendSuccess = true;
        Set<Integer> clientIds = users.keySet();
        WebSocketSession session = null;
        for (Integer clientId : clientIds) {
            try {
                session = users.get(clientId);
                if (session.isOpen()) {
                    session.sendMessage(message);
                }
            } catch (IOException e) {
                e.printStackTrace();
                allSendSuccess = false;
            }
        }

        return allSendSuccess;
    }


    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        if (session.isOpen()) {
            session.close();
        }
        System.out.println("连接出错");
        users.remove(getClientId(session));
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        System.out.println("连接已关闭：" + status);
        users.remove(getClientId(session));
    }

    @Override
    public boolean supportsPartialMessages() {
        return false;
    }

    /**
     * 获取用户标识
     *
     * @param session
     * @return
     */
    private Integer getClientId(WebSocketSession session) {
        try {
            Integer clientId = (Integer) session.getAttributes().get(CLIENT_ID);
            return clientId;
        } catch (Exception e) {
            return null;
        }
    }
}


