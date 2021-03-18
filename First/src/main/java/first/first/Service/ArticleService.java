package first.first.Service;

import first.first.Entity.Article;
import first.first.Form.ArticleForm;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * @创建人 weizc
 * @创建时间 2018/8/12 21:03
 */
public interface ArticleService {
    List<Article> findByAuthor(Integer authorId);

    Article findByArticleId(Integer articleid);

    Article publish(ArticleForm form);

    void deleteByAuthorId(Integer authorId);

    void delArticle(ArticleForm form);

    Page<Article> findByCategory(String categoryName, Pageable pageable);

    Page<Article> findAll(Pageable pageable);

    List<Article> findByCategory(String categoryName);

    Page<Article> findByAuthorIdIn(List<Integer> list, Pageable pageable);

    Page<Article> findByArticleIdIn(List<Integer> list, Pageable pageable);

    Page<Article> findByAuthorId(Integer authorId, Pageable pageable);

    Page<Article> findByAuthorIdAndArticleCategory(Integer authorId, String category, Pageable pageable);

    Page<Article> findByAuthorIdAndTitleLike(Integer authorId, String titleWord, Pageable pageable);
}
