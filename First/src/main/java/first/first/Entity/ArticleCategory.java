package first.first.Entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * @创建人 weizc
 * @创建时间 2018/9/14 19:52
 */
@Entity
@Data
public class ArticleCategory {
    @Id
    @GeneratedValue
    private Integer categoryId;

    private String categoryName;
}
