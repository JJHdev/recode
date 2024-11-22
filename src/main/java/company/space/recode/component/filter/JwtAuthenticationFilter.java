package company.space.recode.component.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import company.space.recode.component.Utils.JwtUtil;
import company.space.recode.component.security.UserDetailsServiceImpl;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
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

        String token = resolveToken(request);

        if (token != null && jwtUtil.validateToken(token)) {
            String username = jwtUtil.getUsername(token);
            UserDetails userDetails = userDetailsServiceImpl.loadUserByUsername(username);
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        filterChain.doFilter(request, response);
    }

    // 헤더에서 토큰 추출
    private String resolveToken(HttpServletRequest request) {
        // 1. 헤더에서 토큰 확인
        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }

        // 2. 요청 파라미터에서 토큰 확인
        String tokenParam = request.getParameter("accessToken");
        if (StringUtils.hasText(tokenParam)) {
            return tokenParam;
        }

        // 3. POST 요청의 폼 데이터에서 토큰 확인
        try {
            String body = request.getReader().lines().collect(Collectors.joining());
            if (StringUtils.hasText(body)) {
                ObjectMapper mapper = new ObjectMapper();
                Map<String, String> bodyMap = mapper.readValue(body, Map.class);
                if (bodyMap.containsKey("accessToken")) {
                    return bodyMap.get("accessToken");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
