package first.first.Repository;

import first.first.Entity.ChatBox;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @创建人 weizc
 * @创建时间 2018/10/30 8:56
 */
public interface ChatBoxRepository extends JpaRepository<ChatBox, Integer> {
    ChatBox findByBoxId(Integer boxId);

    List<ChatBox> findByMasterIdAndExistStatus(Integer masterId, Integer status);

    ChatBox findByMasterIdAndAIdAndBId(Integer masterId, Integer AId, Integer BId);

    ChatBox findByMasterIdAndAIdInAndBIdInAndChatBoxType(Integer masterId, List<Integer> list1, List<Integer> list2, Integer type);

    ChatBox findByMasterIdAndChatBoxTypeAndBId(Integer masterId, Integer type, Integer Bid);

    List<ChatBox> findByMasterIdAndChatBoxType(Integer masterId, Integer type);

    List<ChatBox> findByChatBoxTypeAndBId(Integer type, Integer bId);

    void deleteByBoxId(Integer boxId);

    List<ChatBox> findByMasterId(Integer masterId);

}
