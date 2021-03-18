package first.first.Repository;

import first.first.Entity.FocusFans;
import first.first.Entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Arrays;
import java.util.List;

/**
 * @创建人 weizc
 * @创建时间 2018/8/12 18:44
 */
public interface FocusFansRepository extends JpaRepository<FocusFans, Integer> {
    List<FocusFans> findByFansIdAndFocusStatus(Integer fansId, Integer status);

    List<FocusFans> findByFocusIdAndFocusStatus(Integer focusId, Integer status);

    Page<FocusFans> findByFocusIdAndFocusStatus(Integer focusId, Integer status, Pageable pageable);

    Page<FocusFans> findByFansIdAndFocusStatus(Integer fansId, Integer status, Pageable pageable);

    FocusFans findByFocusIdAndFansIdAndFocusStatus(Integer focusId, Integer fansId, Integer focusStatus);

    FocusFans findByFocusIdAndFansId(Integer focusId, Integer fansId);
}
