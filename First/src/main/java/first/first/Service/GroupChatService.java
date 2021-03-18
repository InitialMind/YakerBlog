package first.first.Service;

import first.first.Entity.GroupChat;

/**
 * @创建人 weizc
 * @创建时间 2018/10/28 9:02
 */
public interface GroupChatService {
    GroupChat findByGroupId(Integer groupId);

    GroupChat findByMasterId(Integer masterId);

    void deleteByGroupId(Integer groupId);

    GroupChat save(GroupChat groupChat);

    boolean inGroup(Integer groupId, Integer userId);

    GroupChat findByNickNameAndExistStatus(String nickName, Integer status);
}
