package first.first.Repository;

import first.first.Entity.ActionExperience;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @创建人 weizc
 * @创建时间 2018/9/17 8:30
 */
public interface ActionRepository extends JpaRepository<ActionExperience, Integer> {
    ActionExperience findByAction(String action);

}
