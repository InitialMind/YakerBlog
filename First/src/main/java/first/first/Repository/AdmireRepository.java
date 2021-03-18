package first.first.Repository;

import first.first.Entity.Admire;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @创建人 weizc
 * @创建时间 2018/8/13 10:56
 */
public interface AdmireRepository extends JpaRepository<Admire, Integer> {
    Admire findByAimIdAndUserIdAndType(Integer aimId, Integer userId, Integer type);

    Page<Admire> findByTypeAndAdmireStatus(Integer type, Integer status, Pageable pageable);

    Admire findByAdmireId(Integer id);

    List<Admire> findByTypeAndUserId(Integer type, Integer userId);


}
