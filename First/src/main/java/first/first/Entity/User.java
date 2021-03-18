package first.first.Entity;

import first.first.Enum.SexEnum;
import first.first.Tools.RoleTool;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;


import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 * @创建人 weizc
 * @创建时间 2018/8/12 15:45
 */
@Entity
@Data
public class User implements UserDetails {

    private static final long serialVersionUID = 6357851732544538539L;
    @Id
    @GeneratedValue
    private Integer userId;
    /**
     * 用户名
     */
    private String userName;
    /**
     * 用户密码
     */
    private String userPwd;
    /**
     * 用户昵称
     */
    private String nickName;
    /**
     * 个人说明
     */
    private String statement = "";
    /**
     * 用户等级
     */
    private Integer userLevel = 1;
    /**
     * 用户总经验
     */
    private Long experience = 0L;
    /**
     * 用户余额
     */
    private BigDecimal userBalance = new BigDecimal(0);
    /**
     * 关注数
     */
    private Integer focusCount = 0;
    /**
     * 粉丝数
     */
    private Integer fansCount = 0;
    /**
     * 性别
     */
    private String userSex = SexEnum.MAN.getSex();
    /**
     * 出生日期
     */
    private Date birthday;
    /**
     * 户籍
     */
    private String province;
    private String city;
    /**
     * 发表文章数
     */
    private Integer articleCount = 0;
    /**
     * 头像
     */
    private String icon;
    /**
     * 角色
     */
    @ManyToMany(cascade = {CascadeType.REFRESH}, fetch = FetchType.EAGER)
    private List<Role> roleName = RoleTool.setRole("用户");

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> auths = new ArrayList<>();
        List<Role> roles = this.getRoleName();
        for (Role role : roles) {
            auths.add(new SimpleGrantedAuthority(role.getRoleName()));
        }
        return auths;
    }

    @Override
    public String getPassword() {
        return this.userPwd;
    }

    @Override
    public String getUsername() {
        return this.userName;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
