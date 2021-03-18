package first.first.Service;

import first.first.Entity.ArticleCategory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * @创建人 weizc
 * @创建时间 2018/9/14 20:15
 */
public interface ArticleCategoryService {
    void deleteByCategoryName(String name);

    void deleteByCategoryId(Integer id);

    ArticleCategory findByCategoryName(String categoryName);

    ArticleCategory findByArticleId(Integer id);

    List<ArticleCategory> findAll();

    Page<ArticleCategory> findAll(Pageable pageable);

}
