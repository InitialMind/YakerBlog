package first.first.Service.Imp;

import first.first.Entity.Article;
import first.first.Entity.User;
import first.first.Enum.UserEnum;
import first.first.Exception.UserException;
import first.first.Form.ArticleForm;
import first.first.Repository.ArticleRepository;
import first.first.Repository.UserRepository;
import first.first.Service.ArticleService;
import first.first.Service.UserService;
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
 * @创建时间 2018/8/12 21:08
 */
@Service
public class ArticleServiceImp implements ArticleService {
    @Autowired
    private ArticleRepository articleRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;

    @Override
    public List<Article> findByAuthor(Integer authorId) {
        List<Article> articleList = articleRepository.findByAuthorId(authorId);
        return articleList;
    }

    @Override
    public Article findByArticleId(Integer articleid) {

        return articleRepository.findByArticleId(articleid);
    }

    @Override
    @Transactional
    public Article publish(ArticleForm form) {
        Article article = new Article();
        BeanUtils.copyProperties(form, article);

        User author = userService.findOne(form.getAuthorId());
        if (!ObjectUtils.isEmpty(author)) {
            author.setArticleCount(author.getArticleCount() + 1);
            userRepository.save(author);
            return articleRepository.save(article);
        }

        return null;
    }

    @Override
    @Transactional
    public void delArticle(ArticleForm form) {
        if (ObjectUtils.isEmpty(articleRepository.findById(form.getArticleId()))) {
            throw new UserException(UserEnum.ARTICLE_NOT_EXIST);
        }
        articleRepository.deleteByArticleId(form.getArticleId());
        User author = userService.findOne(form.getAuthorId());
        author.setArticleCount(author.getArticleCount() - 1);
        userRepository.save(author);
    }

    @Override
    public Page<Article> findAll(Pageable pageable) {
        return articleRepository.findAll(pageable);
    }

    @Override
    public Page<Article> findByCategory(String categoryName, Pageable pageable) {
        return articleRepository.findByArticleCategory(categoryName, pageable);
    }

    @Override
    public List<Article> findByCategory(String categoryName) {
        return articleRepository.findByArticleCategory(categoryName);
    }

    @Override
    public void deleteByAuthorId(Integer authorId) {
        articleRepository.deleteByAuthorId(authorId);
    }

    @Override
    public Page<Article> findByAuthorIdIn(List<Integer> list, Pageable pageable) {
        return articleRepository.findByAuthorIdIn(list, pageable);
    }

    @Override
    public Page<Article> findByArticleIdIn(List<Integer> list, Pageable pageable) {
        return articleRepository.findByArticleIdIn(list, pageable);
    }

    @Override
    public Page<Article> findByAuthorId(Integer authorId, Pageable pageable) {
        return articleRepository.findByAuthorId(authorId, pageable);
    }

    @Override
    public Page<Article> findByAuthorIdAndArticleCategory(Integer authorId, String category, Pageable pageable) {
        return articleRepository.findByAuthorIdAndArticleCategory(authorId, category, pageable);
    }

    @Override
    public Page<Article> findByAuthorIdAndTitleLike(Integer authorId, String titleWord, Pageable pageable) {
        return articleRepository.findByAuthorIdAndTitleLike(authorId, "%" + titleWord + "%", pageable);
    }
}
