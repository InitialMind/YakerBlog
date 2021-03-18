package first.first.Service.Imp;

import first.first.Entity.Role;
import first.first.Enum.UserEnum;
import first.first.Exception.UserException;
import first.first.Repository.RoleRepository;
import first.first.Service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.List;

/**
 * @创建人 weizc
 * @创建时间 2018/9/1 10:50
 */
@Service
public class RoleServiceImp implements RoleService {
    @Autowired
    RoleRepository roleRepository;

    @Override
    public Role findById(Integer roleId) {
        if (ObjectUtils.isEmpty(roleRepository.findById(roleId))) {
            throw new UserException(UserEnum.ROLE_ERROR);
        }
        return roleRepository.findById(roleId).get();
    }

    @Override
    public List<Role> findAll() {
        return roleRepository.findAll();
    }
}
