package first.first.Service.Imp;

import first.first.Entity.Article;
import first.first.Form.ArticleForm;
import first.first.Repository.ArticleRepository;
import first.first.Service.ArticleService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

/**
 * @创建人 weizc
 * @创建时间 2018/8/12 21:12
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class ArticleServiceImpTest {
    @Autowired
    private ArticleService articleService;
    @Autowired
    private ArticleRepository articleRepository;

    @Test
    public void save() {
        Article article = new Article();

        article.setAuthorId(1);
        article.setContent("第二篇文章");
        article.setTitle("测试");
        Article res = articleRepository.save(article);
        Assert.assertNotNull(res);
    }

    @Test
    public void mod() {
        PageRequest request = PageRequest.of(0, 5);

        System.out.println(articleService.findByAuthorIdAndTitleLike(1, "第一", request).getContent());
    }

    @Test
    @Transactional
    public void del() {
        ArticleForm form = new ArticleForm();
        form.setArticleId(14);
        form.setAuthorId(1);
        articleService.delArticle(form);
    }

    @Test
    public void findByAuthor() {
    }
}