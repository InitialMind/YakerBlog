package first.first.Service;

import first.first.Entity.UserExperience;

import java.util.List;

/**
 * @创建人 weizc
 * @创建时间 2018/9/16 16:13
 */
public interface UserExperienceService {
    List<UserExperience> findAll();

    UserExperience findByLevel(Integer level);
}
