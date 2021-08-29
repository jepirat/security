package web.config.security;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import web.model.Role;
import web.model.User;
import web.services.UserService;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;

@Component
public class SuccessUserHandler implements AuthenticationSuccessHandler {
    @Autowired
    UserService userService;
    // Spring Security использует объект Authentication, пользователя авторизованной сессии.
    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException {
       User user = (User) authentication.getPrincipal();
       Collection<Role> roles = user.getRoles();
        if (roles.contains(Role.ADMIN) && !roles.contains(Role.USER)) {
            System.out.println("Вошел как админ");
            httpServletResponse.sendRedirect("/admin");
        } else if  (roles.contains(Role.USER) && !roles.contains(Role.ADMIN)) {
            httpServletResponse.sendRedirect("/");
            System.out.println("вошел как пользователь");
        } else if  (roles.contains(Role.USER) && roles.contains(Role.ADMIN)) {
            httpServletResponse.sendRedirect("/admin");
            System.out.println("вошел как admin");
        }
    }
}
