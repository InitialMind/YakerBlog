package first.first.Repository;

import first.first.Entity.Notice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @创建人 weizc
 * @创建时间 2018/9/18 8:31
 */
public interface NoticeRepository extends JpaRepository<Notice, Integer> {

    List<Notice> findByAuthorId(Integer id);

    Notice findByNoticeId(Integer id);

    void deleteByNoticeId(Integer id);

}
