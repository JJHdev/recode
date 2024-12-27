package company.space.recode.component.filter;

import company.space.recode.component.Utils.JwtUtil;
import company.space.recode.token.AuthService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Component;

@Component
public class UserLogoutHandler implements LogoutHandler {
    private final AuthService authService;
    private final JwtUtil jwtUtil;

    public UserLogoutHandler(AuthService authService, JwtUtil jwtUtil) {
        this.authService = authService;
        this.jwtUtil = jwtUtil;
    }

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        String refreshToken = null;
        if(request.getCookies() != null){
            for(Cookie cookie : request.getCookies()){
                if(cookie.getName().equals("refreshToken")){
                    refreshToken = cookie.getValue();
                    break;
                }
            }
        }
        if(refreshToken != null){
            String userId = jwtUtil.getUsername(refreshToken);
            authService.deleteByUserId(userId);
        }
    }
}
