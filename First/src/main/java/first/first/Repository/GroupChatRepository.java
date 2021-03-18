package first.first.Repository;

import first.first.Entity.GroupChat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * @创建人 weizc
 * @创建时间 2018/10/28 9:02
 */
public interface GroupChatRepository extends JpaRepository<GroupChat, Integer> {

    GroupChat findByGroupId(Integer groupId);

    GroupChat findByMasterId(Integer masterId);

    void deleteByGroupId(Integer groupId);

    GroupChat findByNickNameAndExistStatus(String nickName, Integer status);
}
