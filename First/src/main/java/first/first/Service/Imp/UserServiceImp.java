package first.first.Service.Imp;

import first.first.Entity.User;
import first.first.Enum.UserEnum;
import first.first.Exception.UserException;
import first.first.Repository.UserRepository;
import first.first.Service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.transaction.Transactional;

/**
 * @创建人 weizc
 * @创建时间 2018/8/12 15:58
 */
@Service
public class UserServiceImp implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CommentService commentService;
    @Autowired
    private ArticleService articleService;
    @Autowired
    private ReplyService replyService;
    @Autowired
    private AdmireService admireService;

    @Override
    public User findOne(Integer userId) {
        if (ObjectUtils.isEmpty(userRepository.findById(userId))) {
            throw new UserException(UserEnum.USER_NOT_EXIST);
        }
        return userRepository.findById(userId).get();
    }

    @Override
    public Page<User> findAll(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    @Override
    public User findByUserName(String userName) {
        return userRepository.findByUserName(userName);
    }

    @Transactional
    @Override
    public void deleteByUserId(Integer userId) {

        if (!ObjectUtils.isEmpty(userId)) {
            userRepository.deleteByUserId(userId);


        }
    }

    @Override
    public User findByNickName(String nickName) {
        if (ObjectUtils.isEmpty(userRepository.findByNickName(nickName))) {
            throw new UserException(UserEnum.USER_NOT_EXIST);
        }
        return userRepository.findByNickName(nickName);
    }

    @Override
    public User findByUserNameAndPassWord(String username, String password) {
        return userRepository.findByUserNameAndUserPwd(username, password);
    }
}
