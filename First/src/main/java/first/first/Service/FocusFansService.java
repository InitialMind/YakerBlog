package first.first.Service;

import first.first.Entity.FocusFans;
import first.first.Entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * @创建人 weizc
 * @创建时间 2018/8/12 18:47
 */
public interface FocusFansService {
    List<User> findByFansId(Integer fansId);

    List<User> findByFocusId(Integer focusId);

    void cancelFocus(Integer focusId, Integer fansId);

    void save(Integer focusId, Integer fansId);

    FocusFans findByFansIdAndFocusId(Integer fansId, Integer focusId);

    FocusFans findByFocusIdAndFansIdAndFocusStatus(Integer focusId, Integer fansId, Integer focusStatus);

    Page<FocusFans> findByFocusIdAndFocusStatus(Integer focusId, Integer status, Pageable pageable);

    Page<FocusFans> findByFansIdAndFocusStatus(Integer fansId, Integer status, Pageable pageable);
}
