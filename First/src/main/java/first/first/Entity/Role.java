package first.first.Entity;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * @创建人 weizc
 * @创建时间 2018/8/21 14:54
 */
@Data
@Entity
public class Role implements GrantedAuthority {

    private static final long serialVersionUID = 7784457233477573519L;

    @Id
    @GeneratedValue
    private Integer id;
    private String roleName;


    @Override
    public String getAuthority() {
        return roleName;
    }
}
