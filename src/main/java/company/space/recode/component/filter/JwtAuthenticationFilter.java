package company.space.recode.component.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import company.space.recode.component.Utils.JwtUtil;
import company.space.recode.component.security.UserDetailsServiceImpl;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final UserDetailsServiceImpl userDetailsServiceImpl;

    public JwtAuthenticationFilter(JwtUtil jwtUtil, UserDetailsServiceImpl userDetailsServiceImpl) {
        this.jwtUtil = jwtUtil;
        this.userDetailsServiceImpl = userDetailsServiceImpl;
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String path = request.getRequestURI();
        return path.startsWith("/js/") || path.startsWith("/css/") || path.startsWith("/images/") || path.startsWith("/assets/");
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String path = request.getRequestURI();
        if (path.startsWith("/js/") || path.startsWith("/css/") || path.startsWith("/images/")) {
            filterChain.doFilter(request, response);
            return;
        }

        String accessToken = resolveToken(request, "accessToken");

          if (accessToken != null && jwtUtil.validateToken(accessToken)) {

            String username = jwtUtil.getUsername(accessToken);
            UserDetails userDetails = userDetailsServiceImpl.loadUserByUsername(username);
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);
              System.out.println("Principal1: " + authentication.getPrincipal());
              System.out.println("Authorities1: " + authentication.getAuthorities());
        }else if(accessToken == null || jwtUtil.isTokenExpired(accessToken)){

              String refreshToken = resolveToken(request, "refreshToken");
              if (refreshToken != null && jwtUtil.validateToken(refreshToken)) {

                  String username = jwtUtil.getUsername(refreshToken);
                  UserDetails userDetails = userDetailsServiceImpl.loadUserByUsername(username);

                  String newAccessToken = jwtUtil.createAccessToken(username);

                  Cookie cookie = new Cookie("accessToken", newAccessToken);
                  cookie.setHttpOnly(false);
                  cookie.setSecure(false);
                  cookie.setPath("/");
                  cookie.setMaxAge((int) jwtUtil.getAccessTokenTime());
                  response.addCookie(cookie);

                  UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                  SecurityContextHolder.getContext().setAuthentication(authentication);
                  System.out.println("Principal2: " + authentication.getPrincipal());
                  System.out.println("Authorities2: " + authentication.getAuthorities());
              }
        }

        filterChain.doFilter(request, response);
    }

    // 헤더에서 토큰 추출
    private String resolveToken(HttpServletRequest request, String tokenName) {
        // 1. 헤더에서 토큰 확인
        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }

        // 2. 요청 파라미터에서 토큰 확인
        String tokenParam = request.getParameter(tokenName);
        if (StringUtils.hasText(tokenParam)) {
            return tokenParam;
        }

        // 3. 쿠키에서 토큰 확인
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (tokenName.equals(cookie.getName())) {
                    return cookie.getValue();
                }
            }
        }

        // 4. POST 요청의 폼 데이터에서 토큰 확인
        try {
            String body = request.getReader().lines().collect(Collectors.joining());
            if (StringUtils.hasText(body)) {
                ObjectMapper mapper = new ObjectMapper();
                Map<String, String> bodyMap = mapper.readValue(body, Map.class);
                if (bodyMap.containsKey(tokenName)) {
                    return bodyMap.get(tokenName);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    private void createCookie (String cookieName , String jwtToken , Boolean httpOnly, Boolean secure, int cookieTime, HttpServletResponse response){
        Cookie cookie = new Cookie(cookieName, jwtToken);
        cookie.setHttpOnly(httpOnly); // 클라이언트 접근 가능
        cookie.setSecure(secure);   // HTTPS에서만 전송
        cookie.setPath("/");
        cookie.setMaxAge(cookieTime); // 15분
        response.addCookie(cookie);
    }
}
