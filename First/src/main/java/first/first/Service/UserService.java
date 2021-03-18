package first.first.Service;

import first.first.Entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

/**
 * @创建人 weizc
 * @创建时间 2018/8/12 15:57
 */
public interface UserService {
    User findOne(Integer userId);

    Page<User> findAll(Pageable pageable);

    void deleteByUserId(Integer userId);

    User findByUserName(String userName);

    User findByUserNameAndPassWord(String username, String password);

    User findByNickName(String nickName);
}
