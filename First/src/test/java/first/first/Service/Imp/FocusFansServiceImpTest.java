package first.first.Service.Imp;

import first.first.Entity.FocusFans;
import first.first.Entity.User;
import first.first.Repository.FocusFansRepository;
import first.first.Repository.UserRepository;
import first.first.Service.FocusFansService;
import first.first.Service.UserService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

/**
 * @创建人 weizc
 * @创建时间 2018/8/12 18:50
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class FocusFansServiceImpTest {
    @Autowired
    private FocusFansRepository focusFansRepository;
    @Autowired
    private FocusFansService focusFansService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserService userService;

    @Test

    public void save() {

        focusFansService.save(2, 1);
    }

    @Test

    public void findByFansId() {


        List<User> users = focusFansService.findByFansId(1);
        System.out.println(users);
        Assert.assertNotNull(users);


    }

    @Test
    public void cancel() {
        focusFansService.cancelFocus(1, 2);

    }

    @Test
    public void findByFocusId() {
        List<User> users = focusFansService.findByFocusId(2);
        System.out.println(users);
        Assert.assertNotNull(users);
    }
}