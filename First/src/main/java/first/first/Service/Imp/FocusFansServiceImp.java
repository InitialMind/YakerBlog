package first.first.Service.Imp;

import first.first.Conver.FocusFans2UserConver;
import first.first.Entity.FocusFans;
import first.first.Entity.User;
import first.first.Enum.FocusFansEnum;
import first.first.Enum.UserEnum;
import first.first.Exception.UserException;
import first.first.Repository.FocusFansRepository;
import first.first.Repository.UserRepository;
import first.first.Service.FocusFansService;
import first.first.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.transaction.Transactional;
import java.util.List;

/**
 * @创建人 weizc
 * @创建时间 2018/8/12 18:49
 */
@Service
public class FocusFansServiceImp implements FocusFansService {
    @Autowired
    private FocusFansRepository focusFansRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;

    @Override
    public List<User> findByFansId(Integer fansId) {
        List<FocusFans> focusFansList = focusFansRepository.findByFansIdAndFocusStatus(fansId, FocusFansEnum.YES.getStatus());
        return FocusFans2UserConver.Focus2User(focusFansList);
    }

    @Override
    public List<User> findByFocusId(Integer focusId) {
        List<FocusFans> focusFansList = focusFansRepository.findByFocusIdAndFocusStatus(focusId, FocusFansEnum.YES.getStatus());
        return FocusFans2UserConver.Fans2User(focusFansList);
    }

    @Override
    @Transactional
    public void cancelFocus(Integer focusId, Integer fansId) {
        if (ObjectUtils.isEmpty(userRepository.findById(focusId)) || ObjectUtils.isEmpty(userRepository.findById(fansId))) {
            throw new UserException(UserEnum.USER_NOT_EXIST);
        }
        User focus = userRepository.findById(focusId).get();
        User fans = userRepository.findById(fansId).get();
        FocusFans focusFans = focusFansRepository.findByFocusIdAndFansId(focusId, fansId);
        if (focusFans.getFocusStatus() == FocusFansEnum.NO.getStatus()) {
            throw new UserException(UserEnum.FOCUS_STATUS_ERROR);
        }
        focusFans.setFocusStatus(FocusFansEnum.NO.getStatus());
        focusFansRepository.save(focusFans);
        fans.setFocusCount(fans.getFocusCount() - 1);
        focus.setFansCount(focus.getFansCount() - 1);
        userRepository.save(fans);
        userRepository.save(focus);
    }

    @Override
    @Transactional
    public void save(Integer focusId, Integer fansId) {

        if (ObjectUtils.isEmpty(userRepository.findById(focusId)) || ObjectUtils.isEmpty(userRepository.findById(fansId))) {
            throw new UserException(UserEnum.USER_NOT_EXIST);
        }
        FocusFans focusFans = focusFansRepository.findByFocusIdAndFansId(focusId, fansId);
        if (ObjectUtils.isEmpty(focusFans)) {
            FocusFans nwfocus = new FocusFans();
            nwfocus.setFocusId(focusId);
            nwfocus.setFansId(fansId);
            focusFansRepository.save(nwfocus);

        } else if (focusFans.getFocusStatus() == FocusFansEnum.YES.getStatus()) {
            throw new UserException(UserEnum.FOCUS_STATUS_ERROR);
        } else {
            focusFans.setFocusStatus(FocusFansEnum.YES.getStatus());
            focusFansRepository.save(focusFans);
        }
        User focus = userRepository.findById(focusId).get();
        User fans = userRepository.findById(fansId).get();
        fans.setFocusCount(fans.getFocusCount() + 1);
        focus.setFansCount(focus.getFansCount() + 1);

        userRepository.save(fans);
        userRepository.save(focus);

    }

    @Override
    public FocusFans findByFansIdAndFocusId(Integer fansId, Integer focusId) {
        return focusFansRepository.findByFocusIdAndFansId(focusId, fansId);
    }

    @Override
    public Page<FocusFans> findByFocusIdAndFocusStatus(Integer focusId, Integer status, Pageable pageable) {
        return focusFansRepository.findByFocusIdAndFocusStatus(focusId, status, pageable);
    }

    @Override
    public Page<FocusFans> findByFansIdAndFocusStatus(Integer fansId, Integer status, Pageable pageable) {
        return focusFansRepository.findByFansIdAndFocusStatus(fansId, status, pageable);
    }

    @Override
    public FocusFans findByFocusIdAndFansIdAndFocusStatus(Integer focusId, Integer fansId, Integer focusStatus) {
        return focusFansRepository.findByFocusIdAndFansIdAndFocusStatus(focusId, fansId, focusStatus);
    }
}
