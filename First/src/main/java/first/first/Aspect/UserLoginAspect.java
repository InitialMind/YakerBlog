package first.first.Aspect;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;


/**
 * @创建人 weizc
 * @创建时间 2018/8/17 18:26
 */
@Aspect
@Component
public class UserLoginAspect {
    @Pointcut("execution(public * first.first.CustomerController.*.*(..))" + "&& !execution(public * first.first.CustomerController.CustomerLoginController.*(..))")
    public void verify() {
    }

    @Before("verify()")
    public void doverify() {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        HttpServletResponse response = attributes.getResponse();
        HttpSession session = request.getSession();
        if (ObjectUtils.isEmpty(session.getAttribute("username"))) {
            try {
                response.sendRedirect("/customer/login");
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {

        }

    }
}
