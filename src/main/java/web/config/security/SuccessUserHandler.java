package web.config.security;

import com.mysql.cj.x.protobuf.MysqlxCrud;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import web.model.Role;
import web.model.User;
import web.services.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;
import java.util.Set;
@Component
public class SuccessUserHandler implements AuthenticationSuccessHandler {
    @Autowired
    UserService userService;
    // Spring Security использует объект Authentication, пользователя авторизованной сессии.
    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException {
       User user = (User) authentication.getPrincipal();
       Collection<Role> roles = user.getRoles();
        if (roles.contains(Role.USER)) {
            httpServletResponse.sendRedirect("/");
            System.out.println("вошел как пользователь");
        } if (roles.contains(Role.ADMIN)) {
            System.out.println("Вошел как админ");
            httpServletResponse.sendRedirect("/users");
       }
      //  else {
//            System.out.println("Что то еще");
//            httpServletResponse.sendRedirect("/");
//        }
    }
}
