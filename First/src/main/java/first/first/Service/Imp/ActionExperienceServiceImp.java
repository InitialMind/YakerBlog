package first.first.Service.Imp;

import first.first.Entity.ActionExperience;
import first.first.Enum.ExperienceEnum;
import first.first.Repository.ActionRepository;
import first.first.Service.ActionExperienceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @创建人 weizc
 * @创建时间 2018/9/17 8:32
 */
@Service
public class ActionExperienceServiceImp implements ActionExperienceService {
    @Autowired
    ActionRepository actionRepository;

    @Override
    public ActionExperience findByAction(String action) {
        return actionRepository.findByAction(action);
    }

    @Override
    public List<ActionExperience> findAll() {
        return actionRepository.findAll();
    }

    @Override
    public Long getAdmireExperience() {
        ActionExperience actionExperience = actionRepository.findByAction(ExperienceEnum.ADMIRE.getMessage());
        return actionExperience.getExperience();
    }

    @Override
    public Long getCommentExperience() {
        ActionExperience actionExperience = actionRepository.findByAction(ExperienceEnum.COMMENT.getMessage());
        return actionExperience.getExperience();
    }
}
