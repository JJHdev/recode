package company.space.recode.component;

import company.space.recode.user.User;
import company.space.recode.user.UserDtoDetails;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

@Component
public class AuthInterceptor implements HandlerInterceptor {

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            Object principal = authentication.getPrincipal();

            if (principal instanceof UserDetails) {
                UserDetails userObject = (UserDetails) principal;
                if (modelAndView != null) {
                    modelAndView.addObject("userObject", userObject);
                }
            } else {
                User user = new User();
                UserDtoDetails defaultUser = new UserDtoDetails(user);
                if (modelAndView != null) {
                    modelAndView.addObject("userObject", defaultUser);
                }
            }
        }
    }
}
