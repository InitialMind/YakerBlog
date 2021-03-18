package first.first.Service.Imp;

import first.first.Entity.Chat;
import first.first.Entity.ChatBox;
import first.first.Enum.ChatBoxExistEnum;
import first.first.Enum.ChatTypeEnum;
import first.first.Repository.ChatBoxRepository;
import first.first.Service.ChatBoxService;
import first.first.Service.ChatService;
import first.first.VO.ChatVO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit4.SpringRunner;

import javax.xml.ws.Action;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

/**
 * @创建人 weizc
 * @创建时间 2018/10/30 9:10
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class ChatBoxServiceImpTest {
    @Autowired
    ChatBoxRepository chatBoxRepository;
    @Autowired
    ChatBoxService chatBoxService;
    @Autowired
    ChatService chatService;

    @Test
    public void findByMasterIdAndExistStatus() {
        List<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(2);
        PageRequest request = PageRequest.of(0, 10, new Sort(Sort.Direction.DESC, "createTime"));
        Page<Chat> page = chatService.findBySendIdInAndReceiveIdInAndChatType(list, list, request, ChatTypeEnum.PERSONAL_CHAT.getType());

    }
}