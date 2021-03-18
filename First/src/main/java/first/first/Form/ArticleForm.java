package first.first.Form;

import lombok.Data;

import java.util.Date;

/**
 * @创建人 weizc
 * @创建时间 2018/8/14 18:37
 */
@Data
public class ArticleForm {
    private Integer articleId;
    /**
     * 文章标题
     */
    private String title;
    /**
     * 文章分类
     */
    private String articleCategory;
    /**
     * 文章内容
     */
    private String content;
    /**
     * 文章作者id
     */
    private Integer authorId;
    /**
     * 点赞数
     */
    private Integer admireCount = 0;
    /**
     * 评论数
     */
    private Integer commentCount = 0;
}
