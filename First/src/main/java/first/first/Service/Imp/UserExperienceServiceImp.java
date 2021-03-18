package first.first.Service.Imp;

import first.first.Entity.UserExperience;
import first.first.Repository.UserExperienceRepository;
import first.first.Service.UserExperienceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @创建人 weizc
 * @创建时间 2018/9/16 16:15
 */
@Service
public class UserExperienceServiceImp implements UserExperienceService {
    @Autowired
    UserExperienceRepository userExperienceRepository;

    @Override
    public List<UserExperience> findAll() {
        return userExperienceRepository.findAll();
    }

    @Override
    public UserExperience findByLevel(Integer level) {
        return userExperienceRepository.findByLevel(level);
    }
}
