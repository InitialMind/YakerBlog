package first.first.Service.Imp;

import first.first.Entity.Inform;
import first.first.Enum.InformStatusEnum;
import first.first.Enum.InformTypeEnum;
import first.first.Repository.InformRepository;
import first.first.Service.InformService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @创建人 weizc
 * @创建时间 2018/9/24 9:02
 */
@Service
public class InfromServiceImp implements InformService {
    @Autowired
    InformRepository informRepository;

    @Override
    public Page<Inform> findAll(Pageable pageable) {
        return informRepository.findAll(pageable);
    }

    @Override
    public Inform findByInformId(Integer id) {
        return informRepository.findByInformId(id);
    }

    @Override
    public List<Inform> findByAuthorIdAndStatus(Integer authorId, Integer status) {
        return informRepository.findByAuthorIdAndInformStatus(authorId, status);
    }

    @Override
    public Page<Inform> findByAuthorIdAndInformType(Integer authorId, Integer type, Pageable pageable) {
        return informRepository.findByAuthorIdAndInformType(authorId, type, pageable);
    }

    @Override
    public Page<Inform> findByAuthorIdAndInformTypeIn(Integer authorId, List<Integer> list, Pageable pageable) {
        return informRepository.findByAuthorIdAndInformTypeIn(authorId, list, pageable);
    }

    @Override
    public Inform save(Inform inform) {
        return informRepository.save(inform);
    }

    @Override
    public List<Inform> findByAuthorIdAndInformTypeInAndInformStatus(Integer authorId, List<Integer> list, Integer status) {
        return informRepository.findByAuthorIdAndInformTypeInAndInformStatus(authorId, list, status);
    }

    @Override
    public List<Inform> findByAuthorIdAndInformTypeAndInformStatusAndUserId(Integer masterId, Integer type, Integer status, Integer userId) {
        return informRepository.findByAuthorIdAndInformTypeAndInformStatusAndUserId(masterId, type, status, userId);
    }
}
