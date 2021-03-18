package first.first.Service;

import first.first.Entity.Chat;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;

/**
 * @创建人 weizc
 * @创建时间 2018/10/28 9:32
 */
public interface ChatService {
    Chat findByChatId(Integer chatId);

    Page<Chat> findBySendIdAndReceiveId(Integer sendId, Integer receiveId, Pageable pageable);

    List<Chat> findBySendIdInAndReceiveIdIn(List<Integer> list1, List<Integer> list2, Sort sort);

    Page<Chat> findBySendIdInAndReceiveIdInAndChatType(List<Integer> list1, List<Integer> list2, Pageable pageable, Integer type);

    Page<Chat> findByReceiveIdAndChatType(Integer receiveId, Integer type, Pageable pageable);

    Chat save(Chat chat);

    Page<Chat> findAll(Pageable pageable);

    void deleteByChatId(Integer chatId);
}
