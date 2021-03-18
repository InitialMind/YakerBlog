package first.first.Service;

import first.first.Entity.Inform;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * @创建人 weizc
 * @创建时间 2018/9/24 8:57
 */
public interface InformService {
    Page<Inform> findAll(Pageable pageable);

    Inform findByInformId(Integer id);

    List<Inform> findByAuthorIdAndStatus(Integer authorId, Integer status);

    Page<Inform> findByAuthorIdAndInformType(Integer authorId, Integer type, Pageable pageable);

    Page<Inform> findByAuthorIdAndInformTypeIn(Integer authorId, List<Integer> list, Pageable pageable);

    Inform save(Inform inform);

    List<Inform> findByAuthorIdAndInformTypeInAndInformStatus(Integer authorId, List<Integer> list, Integer status);

    List<Inform> findByAuthorIdAndInformTypeAndInformStatusAndUserId(Integer masterId, Integer type, Integer status, Integer userId);
}
