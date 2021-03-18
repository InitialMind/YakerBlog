package first.first.Repository;

import first.first.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * @创建人 weizc
 * @创建时间 2018/8/12 15:55
 */
public interface UserRepository extends JpaRepository<User, Integer> {
    User findByUserName(String userName);

    void deleteByUserId(Integer userId);

    User findByUserNameAndUserPwd(String username, String userPwd);

    User findByNickName(String nickName);
}
