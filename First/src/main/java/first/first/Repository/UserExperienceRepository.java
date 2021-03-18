package first.first.Repository;

import first.first.Entity.UserExperience;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @创建人 weizc
 * @创建时间 2018/9/16 16:12
 */
public interface UserExperienceRepository extends JpaRepository<UserExperience, Integer> {
    UserExperience findByLevel(Integer level);
}
