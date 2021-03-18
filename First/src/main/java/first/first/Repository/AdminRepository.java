package first.first.Repository;

import first.first.Entity.Admin_User;
import first.first.Entity.Role;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @创建人 weizc
 * @创建时间 2018/9/6 21:13
 */
public interface AdminRepository extends JpaRepository<Admin_User, Integer> {
    @Query(value = "select u from Admin_User u where u.userPwd=?1 or u.userPwd=?2 ")
    Page<Admin_User> findByUserPwd(String u1, String u2, Pageable pageable);

    Admin_User findByUserName(String name);

    void deleteByAdminId(Integer adminId);

    //     @Query(value = "select u from Admin_User u where u.roleName =?1 or u.roleName =?2 ")
//    Page<Admin_User> findByRole(List<Role> roleList,List<Role> roles, Pageable pageable);
    @Query(value = "select u from Admin_User u where u.role =?1 ")
    Page<Admin_User> findByRole(List<Role> roles, Pageable pageable);

    Admin_User findByNickName(String nickName);
}
