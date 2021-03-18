package first.first.Tools;

import first.first.Entity.Role;
import first.first.Enum.RoleEnum;

import java.util.ArrayList;
import java.util.List;

/**
 * @创建人 weizc
 * @创建时间 2018/9/10 9:07
 */
public class RoleTool {
    public static List<String> getRoleName(List<Role> roleList) {
        List<String> strings = new ArrayList<>();
        for (Role role : roleList) {
            for (RoleEnum roleEnum : RoleEnum.values()) {
                if (roleEnum.getCode() == role.getId()) {
                    strings.add(roleEnum.getRoleName());
                }
            }
        }
        return strings;
    }

    public static List<Role> setRole(String role) {
        List<Role> roleList = new ArrayList<>();
        Role role1 = new Role();
        for (RoleEnum roleEnum : RoleEnum.values()) {
            if (roleEnum.getRoleName().equals(role)) {
                role1.setRoleName(role);
                role1.setId(roleEnum.getCode());
                roleList.add(role1);
                return roleList;
            }
        }
        return null;
    }

    public static String getAdminRoleName(List<Role> roleList) {
        for (RoleEnum roleEnum : RoleEnum.values()) {
            if (roleEnum.getCode() == roleList.get(0).getId()) {
                return roleEnum.getRoleName();
            }
        }
        return null;
    }

}
