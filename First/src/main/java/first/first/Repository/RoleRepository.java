package first.first.Repository;

import first.first.Entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @创建人 weizc
 * @创建时间 2018/8/22 15:33
 */
public interface RoleRepository extends JpaRepository<Role, Integer> {
}
