package first.first.Service.Imp;

import first.first.Entity.Chat;
import first.first.Repository.ChatRepository;
import first.first.Service.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

/**
 * @创建人 weizc
 * @创建时间 2018/10/28 9:33
 */
@Service
public class ChatServiceImp implements ChatService {
    @Autowired
    ChatRepository chatRepository;

    @Override
    public Chat findByChatId(Integer chatId) {
        return chatRepository.findByChatId(chatId);
    }

    @Override
    public Page<Chat> findBySendIdAndReceiveId(Integer sendId, Integer receiveId, Pageable pageable) {
        return chatRepository.findBySendIdAndReceiveId(sendId, receiveId, pageable);
    }

    @Override
    public List<Chat> findBySendIdInAndReceiveIdIn(List<Integer> list1, List<Integer> list2, Sort sort) {
        return chatRepository.findBySendIdInAndReceiveIdIn(list1, list2, sort);
    }

    @Override
    public Page<Chat> findBySendIdInAndReceiveIdInAndChatType(List<Integer> list1, List<Integer> list2, Pageable pageable, Integer type) {
        return chatRepository.findBySendIdInAndReceiveIdInAndChatType(list1, list2, pageable, type);
    }

    @Override
    public Page<Chat> findByReceiveIdAndChatType(Integer receiveId, Integer type, Pageable pageable) {
        return chatRepository.findByReceiveIdAndChatType(receiveId, type, pageable);
    }

    @Override
    public Chat save(Chat chat) {
        return chatRepository.save(chat);
    }

    @Override
    public Page<Chat> findAll(Pageable pageable) {
        return chatRepository.findAll(pageable);
    }

    @Transactional
    @Override
    public void deleteByChatId(Integer chatId) {
        chatRepository.deleteByChatId(chatId);
    }
}
