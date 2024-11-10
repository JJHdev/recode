package company.space.recode.token;

import company.space.recode.component.Utils.JwtUtil;
import company.space.recode.user.User;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Optional;

@Transactional
@Service
public class AuthService {

    @Value("${jwt.refreshExp}")
    private long REFRESH_TOKEN_TIME;

    private final AuthRepository authRepository;
    private final JwtUtil jwtUtil;

    @Autowired
    public AuthService(AuthRepository authRepository, JwtUtil jwtUtil) {
        this.authRepository = authRepository;
        this.jwtUtil = jwtUtil;
    }

    // Refresh Token 생성 및 저장
    public Token createRefreshToken(String userId) {
        Optional<Token> existingToken = authRepository.findByUserId(userId);

        Token refreshToken;
        if (existingToken.isPresent()) {
            // 기존 토큰이 있을 경우 만료 시간과 토큰 값 갱신
            refreshToken = existingToken.get();
            refreshToken.setExpiration(LocalDateTime.now().plus(Duration.ofMillis(REFRESH_TOKEN_TIME)));
            refreshToken.setToken(jwtUtil.createRefreshToken(userId));
        } else {
            // 기존 토큰이 없을 경우 새로 생성
            refreshToken = new Token();
            refreshToken.setUserId(userId);
            refreshToken.setExpiration(LocalDateTime.now().plus(Duration.ofMillis(REFRESH_TOKEN_TIME)));
            refreshToken.setToken(jwtUtil.createRefreshToken(userId));
        }

        authRepository.save(refreshToken);
        return refreshToken;
    }

    public boolean  verifyExpiration(String token) {
        return authRepository.findByToken(token)
                .filter(rt -> rt.getExpiration().isAfter(LocalDateTime.now(ZoneId.systemDefault())))
                .isPresent();
    }

    public Integer deleteByUserId(String userId) {
        return authRepository.deleteByUserId(userId);
    }

}