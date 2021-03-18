package first.first.Service;

import first.first.Entity.ChatBox;
import first.first.VO.ChatBoxVO;

import java.util.List;

/**
 * @创建人 weizc
 * @创建时间 2018/10/30 8:57
 */
public interface ChatBoxService {
    List<ChatBox> findByMasterIdAndExistStatus(Integer masterId, Integer status);

    List<ChatBoxVO> getResult(List<ChatBox> list);

    ChatBox findByBoxId(Integer boxId);

    ChatBox findByMasterIdAndAIdAndBId(Integer masterId, Integer AId, Integer BId);

    ChatBox save(ChatBox chatBox);

    ChatBox findByMasterIdAndAIdInAndBIdInAndChatBoxType(Integer masterId, List<Integer> list1, List<Integer> list2, Integer type);

    ChatBox unRead(Integer ChatBoxId);

    ChatBox findByMasterIdAndChatBoxTypeAndBId(Integer masterId, Integer type, Integer Bid);

    List<ChatBox> findByMasterIdAndChatBoxType(Integer masterId, Integer type);

    List<ChatBox> findByChatBoxTypeAndBId(Integer type, Integer bId);

    void deleteByBoxId(Integer boxId);

    List<ChatBox> findByMasterId(Integer masterId);
}
