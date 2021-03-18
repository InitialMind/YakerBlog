package first.first.Service;

import first.first.Entity.ActionExperience;

import java.util.List;

/**
 * @创建人 weizc
 * @创建时间 2018/9/17 8:31
 */
public interface ActionExperienceService {
    ActionExperience findByAction(String action);

    Long getAdmireExperience();

    Long getCommentExperience();

    List<ActionExperience> findAll();
}
