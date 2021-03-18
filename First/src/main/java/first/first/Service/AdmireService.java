package first.first.Service;

import first.first.Entity.Admire;
import first.first.Entity.Reply;
import first.first.Form.AdmireForm;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * @创建人 weizc
 * @创建时间 2018/8/13 11:00
 */
public interface AdmireService {
    Admire admire(Integer aimId, Integer userId, Integer type);

    Page<Admire> findByTypeAndStatus(Integer type, Integer status, Pageable pageable);

    void deleteByAdmireId(Integer userId, Integer aimId, Integer type);

    void deleteByAdmireId(Integer id);

    Admire findByTypeAndAimIdAndUserId(Integer type, Integer aimId, Integer userId);

    List<Admire> findByTypeAndUserId(Integer type, Integer userId);
}
