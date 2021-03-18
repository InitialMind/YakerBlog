package first.first.Service.Imp;

import first.first.Entity.Chat;
import first.first.Entity.ChatBox;
import first.first.Entity.GroupChat;
import first.first.Entity.User;
import first.first.Enum.ChatTypeEnum;
import first.first.Repository.ChatBoxRepository;
import first.first.Service.ChatBoxService;
import first.first.Service.ChatService;
import first.first.Service.GroupChatService;
import first.first.Service.UserService;
import first.first.VO.ChatBoxVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.transaction.Transactional;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @创建人 weizc
 * @创建时间 2018/10/30 9:09
 */
@Service
public class ChatBoxServiceImp implements ChatBoxService {
    @Autowired
    ChatBoxRepository chatBoxRepository;
    @Autowired
    UserService userService;
    @Autowired
    ChatService chatService;
    @Autowired
    GroupChatService groupChatService;

    @Override
    public List<ChatBox> findByMasterIdAndExistStatus(Integer masterId, Integer status) {
        return chatBoxRepository.findByMasterIdAndExistStatus(masterId, status);
    }

    @Override
    public List<ChatBoxVO> getResult(List<ChatBox> list) {
        SimpleDateFormat format = new SimpleDateFormat("MM-dd HH:mm");
        List<ChatBoxVO> resList = new ArrayList<>();
        for (ChatBox box : list) {
            ChatBoxVO chatBoxVO = new ChatBoxVO();
            chatBoxVO.setUnReadCount(box.getUnReadCount());
            // 私聊会话框
            if (box.getChatBoxType().equals(ChatTypeEnum.PERSONAL_CHAT.getType())) {
                chatBoxVO.setBoxId(box.getBoxId());
                chatBoxVO.setType("chat");
                User user = new User();
                if (box.getAId().equals(box.getMasterId())) {
                    user = userService.findOne(box.getBId());
                } else {
                    user = userService.findOne(box.getAId());
                }
                if (!ObjectUtils.isEmpty(user)) {
                    chatBoxVO.setIcon(user.getIcon());
                    chatBoxVO.setNickName(user.getNickName());
                }
                List<Integer> list1 = new ArrayList<>();
                list1.add(box.getAId());
                list1.add(box.getBId());
                if (chatService.findBySendIdInAndReceiveIdIn(list1, list1, new Sort(Sort.Direction.DESC, "createTime")).size() > 0) {
                    Chat chat = chatService.findBySendIdInAndReceiveIdIn(list1, list1, new Sort(Sort.Direction.DESC, "createTime")).get(0);
                    chatBoxVO.setTime(format.format(chat.getCreateTime()));
                    chatBoxVO.setMessage(chat.getContent());
                }
                resList.add(chatBoxVO);
            }
            // 群聊会话框
            else if (box.getChatBoxType().equals(ChatTypeEnum.GROUP_CHAT.getType())) {
                chatBoxVO.setBoxId(box.getBoxId());
                chatBoxVO.setType("groupChat");
                GroupChat groupChat = groupChatService.findByGroupId(box.getBId());
                if (!ObjectUtils.isEmpty(groupChat)) {
                    if (groupChat.getContent().size() > 0) {
                        Date date = groupChat.getContent().get(0).getCreateTime();
                        Chat lastChat = groupChat.getContent().get(0);
                        for (Chat chat : groupChat.getContent()) {
                            if (chat.getCreateTime().after(date)) {
                                date = chat.getCreateTime();
                                lastChat = chat;
                            }
                        }
                        chatBoxVO.setMessage(lastChat.getContent());
                        chatBoxVO.setTime(format.format(lastChat.getCreateTime()));
                    }
                    chatBoxVO.setNickName(groupChat.getNickName());
                    chatBoxVO.setIcon(groupChat.getIcon());

                }
                resList.add(chatBoxVO);
            }
        }
        return resList;
    }

    @Override
    public ChatBox findByBoxId(Integer boxId) {
        return chatBoxRepository.findByBoxId(boxId);
    }

    @Override
    public ChatBox findByMasterIdAndAIdAndBId(Integer masterId, Integer AId, Integer BId) {
        return chatBoxRepository.findByMasterIdAndAIdAndBId(masterId, AId, BId);
    }

    @Override
    public ChatBox save(ChatBox chatBox) {
        return chatBoxRepository.save(chatBox);
    }

    @Override
    public ChatBox unRead(Integer ChatBoxId) {
        ChatBox box = chatBoxRepository.findByBoxId(ChatBoxId);
        if (!ObjectUtils.isEmpty(box)) {
            box.setUnReadCount(box.getUnReadCount() + 1);
            return chatBoxRepository.save(box);
        }
        return null;
    }

    @Override
    public ChatBox findByMasterIdAndChatBoxTypeAndBId(Integer masterId, Integer type, Integer Bid) {
        return chatBoxRepository.findByMasterIdAndChatBoxTypeAndBId(masterId, type, Bid);
    }

    @Override
    public List<ChatBox> findByMasterIdAndChatBoxType(Integer masterId, Integer type) {
        return chatBoxRepository.findByMasterIdAndChatBoxType(masterId, type);
    }

    @Override
    public ChatBox findByMasterIdAndAIdInAndBIdInAndChatBoxType(Integer masterId, List<Integer> list1, List<Integer> list2, Integer type) {
        return chatBoxRepository.findByMasterIdAndAIdInAndBIdInAndChatBoxType(masterId, list1, list2, type);
    }

    @Override
    public List<ChatBox> findByChatBoxTypeAndBId(Integer type, Integer bId) {
        return chatBoxRepository.findByChatBoxTypeAndBId(type, bId);
    }

    @Transactional
    @Override
    public void deleteByBoxId(Integer boxId) {
        chatBoxRepository.deleteByBoxId(boxId);
    }

    @Override
    public List<ChatBox> findByMasterId(Integer masterId) {
        return chatBoxRepository.findByMasterId(masterId);
    }
}
