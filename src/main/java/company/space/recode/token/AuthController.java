package company.space.recode.token;

import company.space.recode.component.Utils.JwtUtil;
import company.space.recode.component.security.UserDetailsServiceImpl;
import company.space.recode.user.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Cookie;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import java.util.Optional;
import java.io.IOException;
import org.springframework.security.core.Authentication;

@Controller
@Validated
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final AuthService tokenService;
    private final UserDetailsServiceImpl userDetailsServiceImpl;
    private final AuthService authService;

    public AuthController(AuthenticationManager authenticationManager, JwtUtil jwtUtil, AuthService tokenService, UserDetailsServiceImpl userDetailsServiceImpl, AuthService authService){
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.tokenService = tokenService;
        this.userDetailsServiceImpl = userDetailsServiceImpl;
        this.authService = authService;
    }

    // 로그인 엔드포인트
    public ResponseEntity<?> authenticateUser(String userId, String password) {

        try {
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userId, password));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            String accessToken = jwtUtil.createAccessToken(userDetails.getUsername());
            Token refreshToken = authService.createRefreshToken(userId);
            return ResponseEntity.ok(new JwtResponse(accessToken, refreshToken.getToken()));
        } catch (AuthenticationException ex) {
            // 인증 실패 시 401 Unauthorized 응답을 반환
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("인증에 실패했습니다. 아이디와 비밀번호를 확인해주세요.");
        }
    }
    // 토큰 갱신 엔드포인트
    @PostMapping("/refresh")
    public ResponseEntity<?> refreshToken(String requestRefreshToken) {

        if (authService.verifyExpiration(requestRefreshToken)) {
            String username = jwtUtil.getUsername(requestRefreshToken);
            String newAccessToken = jwtUtil.createAccessToken(username);

            return ResponseEntity.ok(new JwtResponse(newAccessToken,""));
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("유효하지 않은 Refresh Token입니다.");
        }
    }

    // 로그아웃 엔드포인트
    @PostMapping("/logout")
    public ResponseEntity<?> logoutUser(String refreshToken) {
        UserDetails userDetails = userDetailsServiceImpl.loadUserByUsername(jwtUtil.getUsername(refreshToken));
        authService.deleteByUserId(userDetails.getUsername());
        return ResponseEntity.ok("로그아웃되었습니다.");
    }

}
