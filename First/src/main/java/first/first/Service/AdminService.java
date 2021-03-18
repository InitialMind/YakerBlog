package first.first.Service;

import first.first.Entity.Admin_User;
import first.first.Entity.Role;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * @创建人 weizc
 * @创建时间 2018/9/6 21:25
 */
public interface AdminService {
    Admin_User findByUserName(String userName);

    Page<Admin_User> findAll(Pageable pageable);

    Admin_User findByAdminId(Integer id);

    void deleteByAdminId(Integer id);

    Page<Admin_User> findByRole(List<Role> roleList, List<Role> roles, Pageable pageable);

    Admin_User findByNickName(String nickName);
}
