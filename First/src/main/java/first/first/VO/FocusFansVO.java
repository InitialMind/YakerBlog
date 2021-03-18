package first.first.VO;

import first.first.Entity.Article;
import first.first.Entity.FocusFans;
import first.first.Entity.User;
import first.first.Enum.FocusFansEnum;
import first.first.Service.FocusFansService;
import first.first.Service.UserService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import javax.annotation.PostConstruct;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @创建人 weizc
 * @创建时间 2018/10/22 19:53
 */
@Component
public class FocusFansVO {
    @Autowired
    FocusFansService focusFansService;
    @Autowired
    UserService userService;
    private static FocusFansVO focusFansVO;

    @PostConstruct
    public void init() {
        focusFansVO = this;
        focusFansVO.focusFansService = this.focusFansService;
        focusFansVO.userService = this.userService;
    }

    public static String getResult(Page<FocusFans> page, String action, Integer masterId) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日");
        Map<String, Object> resultMap = new HashMap<>();
        List<Map<String, Object>> mapList = new ArrayList<>();
        for (FocusFans focus : page.getContent()) {
            User user = new User();
            if (action.equals("fans")) {
                user = focusFansVO.userService.findOne(focus.getFansId());
            } else {
                user = focusFansVO.userService.findOne(focus.getFocusId());
            }
            if (!ObjectUtils.isEmpty(user)) {
                Map<String, Object> dataMap = new HashMap<>();
                if (!ObjectUtils.isEmpty(focusFansVO.focusFansService.findByFocusIdAndFansIdAndFocusStatus(user.getUserId(), masterId, FocusFansEnum.YES.getStatus()))) {
                    dataMap.put("focusStatus", "Y");
                } else {
                    dataMap.put("focusStatus", "N");
                }
                dataMap.put("ID", focus.getId());
                dataMap.put("icon", user.getIcon());
                dataMap.put("nickName", user.getNickName());
                dataMap.put("userId", user.getUserId());
                dataMap.put("sex", user.getUserSex());
                dataMap.put("focus", user.getFocusCount());
                dataMap.put("fans", user.getFansCount());
                dataMap.put("articles", user.getArticleCount());
                dataMap.put("province", user.getProvince());
                dataMap.put("city", user.getCity());
                dataMap.put("statement", user.getStatement());
                dataMap.put("birthday", format.format(user.getBirthday()));
                mapList.add(dataMap);
            }

        }
        resultMap.put("data", mapList);
        resultMap.put("pages", page.getTotalPages());
        return new JSONObject(resultMap).toString();
    }

}
