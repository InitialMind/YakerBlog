package first.first.Repository;

import first.first.Entity.ArticleCategory;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @创建人 weizc
 * @创建时间 2018/9/14 19:54
 */
public interface ArticleCategoryRepository extends JpaRepository<ArticleCategory, Integer> {
    void deleteByCategoryName(String name);

    void deleteByCategoryId(Integer id);

    ArticleCategory findByCategoryName(String name);

    ArticleCategory findByCategoryId(Integer id);
}
