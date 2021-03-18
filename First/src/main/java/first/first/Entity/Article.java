package first.first.Entity;

import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

/**
 * @创建人 weizc
 * @创建时间 2018/8/12 20:55
 */
@Data
@Entity
@DynamicUpdate
@DynamicInsert
public class Article {
    @Id
    @GeneratedValue
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
     * 发布时间
     */
    private Date createTime;
    /**
     * 修改时间
     */
    private Date updateTime;
    /**
     * 点赞数
     */
    private Integer admireCount = 0;
    /**
     * 评论数
     */
    private Integer commentCount = 0;
}
