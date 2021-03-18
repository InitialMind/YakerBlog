package first.first.Service.Imp;

import first.first.Enum.ReplyEnum;
import first.first.Enum.ReplyTypeEnum;
import first.first.Form.ReplyForm;
import first.first.Service.ArticleService;
import first.first.Service.ReplyService;
import first.first.VO.DetailArticleVO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

/**
 * @创建人 weizc
 * @创建时间 2018/8/14 15:19
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class ReplyServiceImpTest {
    @Autowired
    ReplyService replyService;
    @Autowired
    ArticleService articleService;

    @Test
    public void createReply() {


    }

    @Test
    public void deleteReply() {
        PageRequest request = PageRequest.of(0, 10);


    }

    @Test
    public void findByReplyId() {
    }
}