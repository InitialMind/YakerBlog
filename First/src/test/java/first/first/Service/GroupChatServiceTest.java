package first.first.Service;

import first.first.Entity.Chat;
import first.first.Entity.GroupChat;
import first.first.Entity.User;
import first.first.Enum.ChatTypeEnum;
import first.first.Repository.ChatRepository;
import first.first.Repository.GroupChatRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * @创建人 weizc
 * @创建时间 2018/10/28 9:03
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class GroupChatServiceTest {
    @Autowired
    ChatRepository chatRepository;
    @Autowired
    GroupChatRepository groupChatRepository;
    @Autowired
    UserService userService;

    @Test
    public void findByGroupId() {
//        GroupChat groupChat=new GroupChat();
//        List<Chat> chats =chatRepository.findAll();
//
//        PageRequest request = PageRequest.of(0, 10);
//        List<User> users=userService.findAll(request).getContent();
//        groupChat.setContent(chats);
//        groupChat.setGroupUser(users);
//        groupChat.setMasterId(1);
//        groupChat.setIcon("/images/201810250853454732244.png");
//        groupChat.setNickName("群聊");
//        GroupChat chat=  groupChatRepository.save(groupChat);
//        System.out.println(chat);
        System.out.println(groupChatRepository.findByGroupId(174));
//          groupChatRepository.deleteByGroupId(170);
//        List<Integer> list1 = new ArrayList<>();
//        list1.add(1);
//        list1.add(2);
//        List<Integer> list2 = new ArrayList<>();
//        list2.add(1);
//        list2.add(2);
//
//        List<Chat> chats = chatRepository.findBySendIdInAndReceiveIdIn(list1, list2, new Sort(Sort.Direction.DESC, "createTime"));
//        System.out.println(chats);

    }
}