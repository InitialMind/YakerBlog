package first.first.Service.Imp;

import first.first.Entity.Admin_User;
import first.first.Entity.User;
import first.first.Enum.UserEnum;
import first.first.Exception.UserException;
import first.first.Service.AdminService;
import first.first.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;


import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;


/**
 * @创建人 weizc
 * @创建时间 2018/8/21 15:24
 */
@Service
public class CustomService implements UserDetailsService {
    @Autowired
    AdminService adminService;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        Admin_User admin_user = adminService.findByUserName(s);
        if (ObjectUtils.isEmpty(admin_user)) {
            throw new UserException(UserEnum.USER_NOT_EXIST);
        }

        return admin_user;
    }
}
