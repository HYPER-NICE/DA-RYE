package hyper.darye.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableMethodSecurity // 메서드에서 접근제어 메서드 사용 가능하도록 설정
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
//                .csrf(csrf -> csrf.disable())  // CSRF 비활성화 (REST API)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))  // 세션 사용 안함 (JWT/토큰 기반)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/sign-in", "/api/sign-up").permitAll()  // 인증 없이 접근 가능
                        .anyRequest().authenticated()  // 나머지 요청은 인증 필요
                )
                .formLogin(form -> form.disable())  // 폼 로그인 비활성화
                .httpBasic(httpBasic -> httpBasic.disable());  // HTTP 기본 인증 비활성화

        return http.build();
    }

    // AuthenticationManager Bean 등록
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    // 비밀번호 인코더 등록
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
