package first.first.Repository;

import first.first.Entity.Article;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import javax.lang.model.element.NestingKind;
import java.util.List;

/**
 * @创建人 weizc
 * @创建时间 2018/8/12 21:01
 */
public interface ArticleRepository extends JpaRepository<Article, Integer> {
    List<Article> findByAuthorId(Integer authorId);

    Page<Article> findByAuthorId(Integer authorId, Pageable pageable);

    Article findByArticleId(Integer articleId);

    List<Article> findByArticleCategory(String category);

    Page<Article> findByArticleCategory(String category, Pageable pageable);

    void deleteByArticleId(Integer articleId);

    @Query("select u from Article u where u.authorId in (?1)")
    Page<Article> findByAuthorIdIn(List<Integer> list, Pageable pageable);

    @Query("select u from  Article u where u.articleId in (?1)")
    Page<Article> findByArticleIdIn(List<Integer> list, Pageable pageable);

    void deleteByAuthorId(Integer authorId);

    Page<Article> findByAuthorIdAndArticleCategory(Integer authorId, String category, Pageable pageable);

    Page<Article> findByAuthorIdAndTitleLike(Integer authorId, String titleWord, Pageable pageable);
}
