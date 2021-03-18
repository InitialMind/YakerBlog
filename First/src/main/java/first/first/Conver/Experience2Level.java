package first.first.Conver;

import first.first.Entity.UserExperience;
import first.first.Enum.ExperienceEnum;
import first.first.Enum.UserLevel;
import first.first.Service.UserExperienceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;

/**
 * @创建人 weizc
 * @创建时间 2018/8/14 20:39
 */
@Component
public class Experience2Level {
    @Autowired
    UserExperienceService userExperienceService;
    private static Experience2Level experience2Level;

    @PostConstruct
    public void init() {
        experience2Level = this;
        experience2Level.userExperienceService = this.userExperienceService;
    }

    public static Integer experience2level(Long experience) {
        Integer level = 1;
        List<UserExperience> userExperiences = experience2Level.userExperienceService.findAll();
        for (UserExperience userExperience : userExperiences) {
            if (experience >= userExperience.getExperience()) {
                level = userExperience.getLevel();
            }
        }
        return level;
    }
}
