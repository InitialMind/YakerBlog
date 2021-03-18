package first.first.Service.Imp;

import first.first.Conver.Experience2Level;
import first.first.Entity.*;
import first.first.Enum.*;
import first.first.Exception.UserException;
import first.first.Form.AdmireForm;
import first.first.Repository.*;
import first.first.Service.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.transaction.Transactional;
import java.util.List;

/**
 * @创建人 weizc
 * @创建时间 2018/8/13 11:12
 */
@Service
public class AdmireServiceImp implements AdmireService {
    @Autowired
    AdmireRepository admireRepository;
    @Autowired
    ArticleService articleService;
    @Autowired
    ArticleRepository articleRepository;
    @Autowired
    CommentService commentService;
    @Autowired
    CommentRepository commentRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    UserService userService;
    @Autowired
    ReplyService replyService;
    @Autowired
    ReplyRepository replyRepository;
    @Autowired
    InformService informService;
    @Autowired
    InformRepository informRepository;
    @Autowired
    ActionExperienceService actionExperienceService;

    @Override
    @Transactional
    public Admire admire(Integer aimId, Integer userId, Integer type) {

        User user = new User();
        // 创建一个通知，第一次点赞才有效
        Inform inform = new Inform();
        inform.setContent("");
        inform.setUserId(userId);
        inform.setAimId(aimId);
        // 1.判断点赞的对象是否存在 存在进行点赞数的加减
        if (type == AdmireTypeEnum.ARTICLE.getType()) {
            Article article = articleService.findByArticleId(aimId);
            if (!ObjectUtils.isEmpty(article)) {
                inform.setInformType(InformTypeEnum.ADMIRE_ARTICLE.getType());
                inform.setAuthorId(article.getAuthorId());
                user = userService.findOne(article.getAuthorId());
                article.setAdmireCount(article.getAdmireCount() + 1);
                articleRepository.save(article);
            }

        } else if (type == AdmireTypeEnum.COMMENT.getType()) {
            Comment comment = commentService.findByCommenId(aimId);
            if (!ObjectUtils.isEmpty(comment)) {
                inform.setInformType(InformTypeEnum.ADMIRE_COMMENT.getType());
                inform.setAuthorId(comment.getUserId());
                user = userService.findOne(comment.getUserId());
                comment.setAdmireCount(comment.getAdmireCount() + 1);
                commentRepository.save(comment);
            }

        } else if (type == AdmireTypeEnum.REPLY.getType()) {
            Reply reply = replyService.findByReplyId(aimId);
            if (!ObjectUtils.isEmpty(reply)) {
                inform.setInformType(InformTypeEnum.ADMIRE_REPLY.getType());
                inform.setAuthorId(reply.getUserId());
                user = userService.findOne(reply.getUserId());

                reply.setAdmireCount(reply.getAdmireCount() + 1);
                replyRepository.save(reply);
            }

        } else {
            throw new UserException(UserEnum.ADMIRE_TYPE_ERROR);
        }
        // 2.第一次点赞增加经验
        Admire admire = admireRepository.findByAimIdAndUserIdAndType(aimId, userId, type);
        if (ObjectUtils.isEmpty(admire)) {
            Admire nwadmire = new Admire();
            nwadmire.setAimId(aimId);
            nwadmire.setUserId(userId);
            nwadmire.setType(type);
            user.setExperience(user.getExperience() + actionExperienceService.getAdmireExperience());
            user.setUserLevel(Experience2Level.experience2level(user.getExperience()));
            userRepository.save(user);
            informRepository.save(inform);
            return admireRepository.save(nwadmire);
        } else {
            admire.setAdmireStatus(AdmireStatusEnum.YES.getStatus());
        }
        return admireRepository.save(admire);
    }

    @Override
    public Page<Admire> findByTypeAndStatus(Integer type, Integer status, Pageable pageable) {
        return admireRepository.findByTypeAndAdmireStatus(type, status, pageable);
    }

    @Override
    public void deleteByAdmireId(Integer userId, Integer aimId, Integer type) {
        Admire admire = admireRepository.findByAimIdAndUserIdAndType(aimId, userId, type);
        if (!ObjectUtils.isEmpty(admire)) {
            admire.setAdmireStatus(AdmireStatusEnum.NO.getStatus());
            admireRepository.save(admire);
        }
        if (type.equals(AdmireTypeEnum.ARTICLE.getType())) {
            Article article = articleService.findByArticleId(aimId);
            if (!ObjectUtils.isEmpty(article)) {
                article.setAdmireCount(article.getAdmireCount() - 1);
                articleRepository.save(article);
            }
        } else if (type.equals(AdmireTypeEnum.COMMENT.getType())) {
            Comment comment = commentService.findByCommenId(aimId);
            if (!ObjectUtils.isEmpty(comment)) {
                comment.setAdmireCount(comment.getAdmireCount() - 1);
                commentRepository.save(comment);
            }
        } else if (type.equals(AdmireTypeEnum.REPLY.getType())) {
            Reply reply = replyService.findByReplyId(aimId);
            if (!ObjectUtils.isEmpty(reply)) {
                reply.setAdmireCount(reply.getAdmireCount() - 1);
                replyRepository.save(reply);
            }
        }
    }

    @Override
    public Admire findByTypeAndAimIdAndUserId(Integer type, Integer aimId, Integer userId) {
        return admireRepository.findByAimIdAndUserIdAndType(aimId, userId, type);
    }

    @Override
    public void deleteByAdmireId(Integer id) {
        Admire admire = admireRepository.findByAdmireId(id);
        if (!ObjectUtils.isEmpty(admire)) {
            Integer aimId = admire.getAimId();
            if (admire.getType().equals(AdmireTypeEnum.ARTICLE.getType())) {
                Article article = articleService.findByArticleId(aimId);
                if (!ObjectUtils.isEmpty(article)) {
                    article.setAdmireCount(article.getAdmireCount() - 1);
                    articleRepository.save(article);
                }
            } else if (admire.getType().equals(AdmireTypeEnum.COMMENT.getType())) {
                Comment comment = commentService.findByCommenId(aimId);
                if (!ObjectUtils.isEmpty(comment)) {
                    comment.setAdmireCount(comment.getAdmireCount() - 1);
                    commentRepository.save(comment);
                }
            } else if (admire.getType().equals(AdmireTypeEnum.REPLY.getType())) {
                Reply reply = replyService.findByReplyId(aimId);
                if (!ObjectUtils.isEmpty(reply)) {
                    reply.setAdmireCount(reply.getAdmireCount() - 1);
                    replyRepository.save(reply);
                }
            }
            admire.setAdmireStatus(AdmireStatusEnum.NO.getStatus());
            admireRepository.save(admire);
        }
    }

    @Override
    public List<Admire> findByTypeAndUserId(Integer type, Integer userId) {
        return admireRepository.findByTypeAndUserId(type, userId);
    }
}
