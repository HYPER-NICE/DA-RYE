package hyper.darye.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableMethodSecurity // 메서드에서 접근제어 메서드 사용 가능하도록 설정
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // CORS 설정
                .cors(cors -> cors.disable())
                // CSRF 비활성화 (REST API)
                .csrf(AbstractHttpConfigurer::disable)
                // 세션 관리 활성화
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED))
                // 인가 설정
                .authorizeHttpRequests(auth -> auth
                        // 기본 설정
                        .requestMatchers(HttpMethod.GET, "/").permitAll()
                        // Swagger UI 접근 허용
                        .requestMatchers("/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui.html").permitAll()

                        // 인증 없이 접근 가능
                        .requestMatchers(HttpMethod.POST, "/api/sign-in").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/sign-up").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/check/**").authenticated()
                        .requestMatchers(HttpMethod.GET, "/api/notification-board/**").permitAll()

                        // `/api/members`에 대한 POST 요청은 인증 없이 접근 허용
                        .requestMatchers(HttpMethod.POST, "/api/members").permitAll()
                        // `/api/members/**`에 대한 나머지 모든 요청은 인증 필요
                        .requestMatchers("/api/members", "/api/members/**").authenticated()

                        // 나머지 모든 요청은 인증 필요
                        .anyRequest().authenticated()
                )
                .formLogin(AbstractHttpConfigurer::disable);

        // JWT 필터 추가 (필요 시)
        // .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

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
