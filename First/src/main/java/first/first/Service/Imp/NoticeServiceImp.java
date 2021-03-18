package first.first.Service.Imp;

import first.first.Entity.Notice;
import first.first.Repository.NoticeRepository;
import first.first.Service.NoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.List;

/**
 * @创建人 weizc
 * @创建时间 2018/9/18 8:42
 */
@Service
public class NoticeServiceImp implements NoticeService {
    @Autowired
    NoticeRepository noticeRepository;

    @Override
    public Notice findByNoticeId(Integer id) {
        return noticeRepository.findByNoticeId(id);
    }

    @Override
    public List<Notice> findAll() {
        return noticeRepository.findAll();
    }

    @Override
    public List<Notice> findByAuthor(Integer id) {
        return noticeRepository.findByAuthorId(id);
    }

    @Override
    public void deleteByNoticeId(Integer id) {
        if (!ObjectUtils.isEmpty(noticeRepository.findByNoticeId(id))) {

            noticeRepository.deleteByNoticeId(id);
        }
    }

    @Override
    public Page<Notice> findAll(Pageable pageable) {
        return noticeRepository.findAll(pageable);
    }
}
