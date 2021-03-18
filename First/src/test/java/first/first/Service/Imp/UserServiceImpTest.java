package first.first.Service.Imp;

import first.first.Entity.Role;
import first.first.Entity.User;
import first.first.Enum.SexEnum;
import first.first.Enum.UserLevel;
import first.first.Repository.UserRepository;
import first.first.Service.UserService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

/**
 * @创建人 weizc
 * @创建时间 2018/8/12 16:10
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class UserServiceImpTest {
    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;

    @Test
    public void findOne() {
        User res = new User();
        res.setUserName("mage");
        res.setUserPwd("123456");

        res.setProvince("河南");
        res.setCity("洛阳");
        res.setBirthday(new Date());
        List<Role> roleList = new ArrayList<>();
        Role r1 = new Role();
        r1.setId(1);
        r1.setRoleName("admin");
        Role r2 = new Role();
        r2.setId(2);
        r2.setRoleName("user");
        roleList.add(r1);
        roleList.add(r2);
        res.setRoleName(roleList);
        res.setUserSex(SexEnum.MAN.getSex());
        User user = userRepository.save(res);
        Assert.assertNotNull(user);


    }

    @Test
    public void findAll() {
        PageRequest request = PageRequest.of(0, 10);
        Page<User> users = userService.findAll(request);
        System.out.println(users.getContent());
        Assert.assertNotNull(users.getContent());
    }

    @Test
    public void test() {
        User user = userService.findByUserName("weizhichao");
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        user.setUserPwd(encoder.encode(user.getPassword().trim()));
        userRepository.save(user);
    }
}