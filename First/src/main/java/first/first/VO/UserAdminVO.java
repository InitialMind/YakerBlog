package first.first.VO;

import first.first.Entity.Admin_User;
import first.first.Entity.Article;
import first.first.Service.AdminService;
import first.first.Service.UserService;
import first.first.Tools.RoleTool;
import lombok.Data;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import javax.annotation.PostConstruct;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @创建人 weizc
 * @创建时间 2018/9/17 14:32
 */
@Data
public class UserAdminVO {
    @Autowired
    AdminService adminService;
    private static UserAdminVO userAdminVO;

    @PostConstruct
    public void init() {
        userAdminVO = this;
        userAdminVO.adminService = this.adminService;
    }

    public static String getAdminResult(Page<Admin_User> page) {
        List<Map<String, Object>> mapList = new ArrayList<>();

        for (Admin_User admin_user : page.getContent()) {

            Map<String, Object> map = new HashMap<>();
            map.put("ID", admin_user.getAdminId());
            map.put("username", admin_user.getUsername());
            map.put("nickname", admin_user.getNickName());
            map.put("noticecount", admin_user.getNoticeCount());
            map.put("role", admin_user.getRoleName());
            mapList.add(map);
        }
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("code", 0);
        resultMap.put("msg", "");
        resultMap.put("count", page.getTotalElements());
        resultMap.put("data", mapList);
        return new JSONObject(resultMap).toString();
    }
}
