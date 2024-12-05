package company.space.recode.component.Utils;

import company.space.recode.component.security.UserDetailsServiceImpl;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;

import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Base64;
import java.util.Date;

@Component
@Log4j2
public class JwtUtil {
    
    private final Key secretKey;
    private final long ACCESS_TOKEN_TIME;
    private final long REFRESH_TOKEN_TIME;

    public JwtUtil(@Value("${jwt.secret}") String secretKey, @Value("${jwt.accessExp}") long ACCESS_TOKEN_TIME, @Value("${jwt.refreshExp}") long REFRESH_TOKEN_TIME) {
        this.secretKey = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
        this.ACCESS_TOKEN_TIME = ACCESS_TOKEN_TIME;
        this.REFRESH_TOKEN_TIME = REFRESH_TOKEN_TIME;
    }

    // Access Token 생성
    public String createAccessToken(String username) {
        return createToken(username, ACCESS_TOKEN_TIME);
    }

    // Refresh Token 생성
    public String createRefreshToken(String username) {
        return createToken(username, REFRESH_TOKEN_TIME);
    }

    private String createToken(String username, long validity) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + validity))
                .signWith(secretKey , SignatureAlgorithm.HS256)
                .compact();
    }
    //토큰에서 유효성검사
    public boolean validateToken(String token) {
        try {
            parseClaims(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            //에러처리
        }
        return false;
    }
    // 토큰에서 사용자명 추출
    public String getUsername(String token) {
        return parseClaims(token).getSubject();
    }

    private Claims parseClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
    public long getRefreshTokenTime() {
        return this.REFRESH_TOKEN_TIME;
    }

    public long getAccessTokenTime() {
        return this.REFRESH_TOKEN_TIME;
    }

    public boolean isTokenExpired(String token) {
        try {
            Date expiration = parseClaims(token).getExpiration();
            return expiration.before(new Date());
        } catch (Exception e) {
            return true; // 파싱 실패 시 만료로 간주
        }
    }
}