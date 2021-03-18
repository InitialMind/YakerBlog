package first.first.Controller;

import com.fasterxml.jackson.core.JsonParser;
import com.sun.org.apache.regexp.internal.RE;
import first.first.Entity.ActionExperience;
import first.first.Entity.UserExperience;
import first.first.Repository.ActionRepository;
import first.first.Repository.UserExperienceRepository;
import first.first.Service.ActionExperienceService;
import first.first.Service.UserExperienceService;
import jdk.nashorn.internal.parser.JSONParser;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @创建人 weizc
 * @创建时间 2018/9/16 16:11
 */
@Controller
@RequestMapping("/experience")
public class ExperienceController {
    @Autowired
    UserExperienceService userExperienceService;
    @Autowired
    UserExperienceRepository userExperienceRepository;
    @Autowired
    ActionExperienceService actionExperienceService;
    @Autowired
    ActionRepository actionRepository;

    @GetMapping("/index")
    public String index(Model model) {
        List<UserExperience> list = userExperienceService.findAll();
        list.remove(0);
        model.addAttribute("list", list);
        Map<Object, Object> map = new HashMap<>();
        for (UserExperience experience : list) {
            if (experience.getLevel() != 1) {
                map.put("Lv" + experience.getLevel().toString(), experience.getExperience());
            }

        }
        Map<Object, Object> map1 = new HashMap<>();
        List<ActionExperience> actionExperiences = actionExperienceService.findAll();
        for (ActionExperience actionExperience : actionExperiences) {
            map1.put(actionExperience.getAction(), actionExperience.getExperience());
        }
        model.addAttribute("data", new JSONObject(map).toString());
        model.addAttribute("action", new JSONObject(map1).toString());
        return "thymeleaf/experience/index";
    }

    @PostMapping("/level")
    @ResponseBody
    public boolean level(Model model, @RequestParam("data") String data) {
        JSONObject jsonObject = new JSONObject(data);
        List<UserExperience> list = userExperienceService.findAll();
        list.remove(0);
        for (UserExperience experience : list) {
            String lv = "Lv" + experience.getLevel().toString();
            experience.setExperience(Long.parseLong(jsonObject.get(lv).toString()));
            userExperienceRepository.save(experience);
        }
        return true;
    }

    @PostMapping("/action")
    @ResponseBody
    public boolean action(@RequestParam("data") String data) {
        JSONObject jsonObject = new JSONObject(data);
        List<ActionExperience> actionExperiences = actionExperienceService.findAll();
        for (ActionExperience actionExperience : actionExperiences) {
            actionExperience.setExperience(Long.parseLong(jsonObject.get(actionExperience.getAction()).toString()));
            actionRepository.save(actionExperience);
        }
        return true;
    }


}
