package first.first.Service.Imp;

import first.first.Entity.GroupChat;
import first.first.Entity.User;
import first.first.Repository.GroupChatRepository;
import first.first.Service.GroupChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.transaction.Transactional;

/**
 * @创建人 weizc
 * @创建时间 2018/10/28 9:29
 */
@Service
public class GroupChatServiceImp implements GroupChatService {
    @Autowired
    GroupChatRepository groupChatRepository;

    @Override
    public GroupChat findByGroupId(Integer groupId) {
        return groupChatRepository.findByGroupId(groupId);
    }

    @Override
    public GroupChat findByMasterId(Integer masterId) {
        return groupChatRepository.findByMasterId(masterId);
    }

    @Transactional
    @Override
    public void deleteByGroupId(Integer groupId) {
        groupChatRepository.deleteByGroupId(groupId);
    }

    @Override
    public GroupChat save(GroupChat groupChat) {
        return groupChatRepository.save(groupChat);
    }

    @Override
    public boolean inGroup(Integer groupId, Integer userId) {
        GroupChat groupChat = groupChatRepository.findByGroupId(groupId);
        if (!ObjectUtils.isEmpty(groupChat)) {
            for (User user : groupChat.getGroupUser()) {
                if (user.getUserId().equals(userId)) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public GroupChat findByNickNameAndExistStatus(String nickName, Integer status) {
        return groupChatRepository.findByNickNameAndExistStatus(nickName, status);
    }
}
