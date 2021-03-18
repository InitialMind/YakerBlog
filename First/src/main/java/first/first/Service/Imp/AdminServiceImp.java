package first.first.Service.Imp;

import first.first.Entity.Admin_User;
import first.first.Entity.Role;
import first.first.Enum.UserEnum;
import first.first.Exception.UserException;
import first.first.Repository.AdminRepository;
import first.first.Service.AdminService;
import first.first.Service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

/**
 * @创建人 weizc
 * @创建时间 2018/9/6 21:26
 */
@Service
public class AdminServiceImp implements AdminService {
    @Autowired
    private AdminRepository adminRepository;
    @Autowired
    RoleService roleService;

    @Override
    public Admin_User findByUserName(String userName) {
        Admin_User admin_user = adminRepository.findByUserName(userName);
        if (ObjectUtils.isEmpty(admin_user)) {

            throw new UserException(UserEnum.USER_NOT_EXIST);
        }

        return admin_user;
    }

    @Override
    public Page<Admin_User> findAll(Pageable pageable) {
        return adminRepository.findAll(pageable);
    }

    @Override
    public Admin_User findByAdminId(Integer id) {
        if (ObjectUtils.isEmpty(adminRepository.findById(id))) {
            throw new UserException(UserEnum.USER_NOT_EXIST);
        }
        return adminRepository.findById(id).get();
    }

    @Transactional
    @Override
    public void deleteByAdminId(Integer id) {
        if (ObjectUtils.isEmpty(adminRepository.findById(id))) {
            throw new UserException(UserEnum.USER_NOT_EXIST);
        }
        adminRepository.deleteByAdminId(id);
    }

    @Override
    public Page<Admin_User> findByRole(List<Role> roleList, List<Role> roles, Pageable pageable) {

        /* adminRepository.findAllByRole(roleList, roles, pageable);*/
        return null;
    }

    @Override
    public Admin_User findByNickName(String nickName) {
        return adminRepository.findByNickName(nickName);
    }
}
