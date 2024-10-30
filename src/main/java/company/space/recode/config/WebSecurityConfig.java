package company.space.recode.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@RequiredArgsConstructor
@EnableWebSecurity
@Configuration
public class WebSecurityConfig {
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf
                    .ignoringRequestMatchers("/user/checkUser","/user/sysCodes" ,"/email/send", "/email/verify") // 해당 경로에 대해 CSRF 비활성화
            )
            .authorizeHttpRequests(authorize -> authorize
                    .requestMatchers("/user/login", "/user/regiUser", "/user/checkUser","/user/sysCodes" ,"/email/send", "/email/verify", "/js/**", "/css/**", "/images/**", "/assets/**").permitAll()
                    .anyRequest().authenticated() // 나머지 요청은 인증 필요
            )
            .formLogin(form -> form
                    .loginPage("/user/login") // 사용자 정의 로그인 페이지
                    .loginProcessingUrl("/user/login")  // 로그인 처리 URL
                    .usernameParameter("userId")      // 사용자 ID 필드 이름 설정
                    .passwordParameter("password")    // 비밀번호 필드 이름 설정
                    .defaultSuccessUrl("/home", true) // 로그인 성공 시 이동할 페이지
                    .permitAll()
            )
            .logout(logout -> logout
                    .logoutUrl("/logout")
                    .logoutSuccessUrl("/login")
                    .permitAll()
            );
        return http.build();
    }
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
