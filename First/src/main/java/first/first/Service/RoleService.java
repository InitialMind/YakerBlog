package first.first.Service;

import first.first.Entity.Role;

import java.util.List;

/**
 * @创建人 weizc
 * @创建时间 2018/9/1 10:49
 */
public interface RoleService {
    Role findById(Integer roleId);

    List<Role> findAll();
}
