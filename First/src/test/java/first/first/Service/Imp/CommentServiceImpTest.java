package first.first.Service.Imp;

import first.first.Form.CommentForm;
import first.first.Service.CommentService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

/**
 * @创建人 weizc
 * @创建时间 2018/8/14 13:31
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class CommentServiceImpTest {
    @Autowired
    CommentService commentService;

    @Test
    public void createComment() {

    }

    @Test

    public void deleteComment() {
        commentService.deleteComment(11);

    }
}