package first.first.Service;

import first.first.Entity.Notice;
import org.aspectj.weaver.ast.Not;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * @创建人 weizc
 * @创建时间 2018/9/18 8:39
 */
public interface NoticeService {
    Notice findByNoticeId(Integer id);

    List<Notice> findAll();

    List<Notice> findByAuthor(Integer id);

    void deleteByNoticeId(Integer id);

    Page<Notice> findAll(Pageable pageable);
}
