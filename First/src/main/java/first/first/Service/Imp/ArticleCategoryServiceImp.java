package first.first.Service.Imp;

import first.first.Entity.ArticleCategory;
import first.first.Enum.UserEnum;
import first.first.Exception.UserException;
import first.first.Repository.ArticleCategoryRepository;
import first.first.Service.ArticleCategoryService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;


import javax.naming.Name;
import javax.transaction.Transactional;
import java.util.List;

/**
 * @创建人 weizc
 * @创建时间 2018/9/14 20:20
 */
@Service
public class ArticleCategoryServiceImp implements ArticleCategoryService {
    @Autowired
    ArticleCategoryRepository articleCategoryRepository;

    @Transactional
    @Override
    public void deleteByCategoryName(String name) {
        articleCategoryRepository.deleteByCategoryName(name);
    }

    @Override
    public Page<ArticleCategory> findAll(Pageable pageable) {
        return articleCategoryRepository.findAll(pageable);
    }

    @Override
    public List<ArticleCategory> findAll() {
        return articleCategoryRepository.findAll();
    }

    @Override
    public ArticleCategory findByArticleId(Integer id) {
        ArticleCategory category = articleCategoryRepository.findByCategoryId(id);
        if (ObjectUtils.isEmpty(category)) {
            throw new UserException(UserEnum.ARTICLECATEGORY_NOT_EXIST);
        }
        return category;
    }

    @Override
    public ArticleCategory findByCategoryName(String categoryName) {

        return articleCategoryRepository.findByCategoryName(categoryName);
    }

    @Transactional
    @Override
    public void deleteByCategoryId(Integer id) {
        articleCategoryRepository.deleteByCategoryId(id);

    }
}
