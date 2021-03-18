package first.first.Service.Imp;

import first.first.Entity.Admin_User;
import first.first.Entity.Role;
import first.first.Repository.AdminRepository;
import first.first.Service.AdminService;
import first.first.Service.AdmireService;
import first.first.Service.RoleService;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.parameters.P;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.security.RolesAllowed;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @创建人 weizc
 * @创建时间 2018/8/14 19:45
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class AdmireServiceImpTest {
    @Autowired
    AdmireService admireService;
    @Autowired
    AdminRepository adminRepository;
    @Autowired
    AdminService adminService;
    @Autowired
    RoleService roleService;

    @Test
    public void admire() {

    }

    @Test
    public void tool() {
        Admin_User admin_user = adminService.findByAdminId(1);
        System.out.println(admin_user);

    }
}