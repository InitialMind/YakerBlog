package first.first.VO;

import first.first.Entity.User;
import org.json.JSONObject;
import org.springframework.data.domain.Page;

import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @创建人 weizc
 * @创建时间 2018/9/12 8:17
 */
public class UserCommonVO {
    public static String UserCommonData(Page<User> userPage) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        List<Map<String, Object>> mapList = new ArrayList<>();
        for (User user : userPage.getContent()) {
            Map<String, Object> map = new HashMap<>();
            map.put("ID", user.getUserId());
            map.put("userName", user.getUsername());
            map.put("sex", user.getUserSex());
            map.put("level", user.getUserLevel());
            map.put("statement", user.getStatement());
            map.put("balance", user.getUserBalance());
            map.put("focus", user.getFocusCount());
            map.put("fans", user.getFansCount());
            map.put("nickName", user.getNickName());
            map.put("articles", user.getArticleCount());
            map.put("birthday", format.format(user.getBirthday()));
            map.put("address", user.getProvince() + user.getCity());
            mapList.add(map);
        }
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("code", 0);
        resultMap.put("msg", "");
        resultMap.put("count", userPage.getTotalElements());
        resultMap.put("data", mapList);
        return new JSONObject(resultMap).toString();
    }
}
