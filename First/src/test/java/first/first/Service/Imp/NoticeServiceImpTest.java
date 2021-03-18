package first.first.Service.Imp;

import first.first.Entity.Notice;
import first.first.Repository.NoticeRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

/**
 * @创建人 weizc
 * @创建时间 2018/9/18 8:53
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class NoticeServiceImpTest {
    @Autowired
    NoticeRepository noticeRepository;

    @Test
    public void findByNoticeId() {
        Notice notice = new Notice();
        notice.setAuthorId(1);
        notice.setContent("测试公告");
        notice.setTitle("第一篇公告");
        noticeRepository.save(notice);
    }

    @Test
    public void findAll() {
    }

    @Test
    public void findByAuthor() {
    }

    @Test
    public void deleteByNoticeId() {
    }
}