package first.first.Conver;

import first.first.Entity.FocusFans;
import first.first.Entity.User;
import first.first.Service.UserService;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @创建人 weizc
 * @创建时间 2018/8/12 19:16
 */
@Component
public class FocusFans2UserConver {
    @Autowired
    private UserService userService;
    private static FocusFans2UserConver focusFans2UserConver;

    @PostConstruct
    public void init() {
        focusFans2UserConver = this;
        focusFans2UserConver.userService = this.userService;
    }

    public static List<User> Focus2User(List<FocusFans> focusFansList) {
        List<User> userList = new ArrayList<>();
        for (FocusFans focus : focusFansList) {

            userList.add(focusFans2UserConver.userService.findOne(focus.getFocusId()));

        }
        return userList;
    }

    public static List<User> Fans2User(List<FocusFans> focusFansList) {
        List<User> userList = new ArrayList<>();
        for (FocusFans fans : focusFansList) {

            userList.add(focusFans2UserConver.userService.findOne(fans.getFansId()));

        }
        return userList;
    }
}
