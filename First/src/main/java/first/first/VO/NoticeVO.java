package first.first.VO;

import first.first.Entity.Admin_User;
import first.first.Entity.Notice;
import first.first.Service.AdminService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @创建人 weizc
 * @创建时间 2018/9/18 9:07
 */
@Component
public class NoticeVO {
    @Autowired
    AdminService adminService;
    private static NoticeVO noticeVO;

    @PostConstruct
    public void init() {
        noticeVO = this;
        noticeVO.adminService = this.adminService;
    }

    public static String getResult(Page<Notice> page) {
        List<Map<String, Object>> mapList = new ArrayList<>();


        for (Notice notice : page.getContent()) {

            Admin_User admin_user = noticeVO.adminService.findByAdminId(notice.getAuthorId());
            Map<String, Object> map = new HashMap<>();
            map.put("ID", notice.getNoticeId());
            map.put("title", notice.getTitle());
            map.put("content", notice.getContent());
            map.put("author", admin_user.getNickName());
            map.put("createTime", notice.getCreateTime().toString());
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
