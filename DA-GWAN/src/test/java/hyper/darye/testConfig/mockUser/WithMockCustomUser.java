package hyper.darye.testConfig.mockUser;

import org.springframework.security.test.context.support.WithSecurityContext;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Mock Security Context를 제공하기 위한 커스텀 애너테이션
 */
@Retention(RetentionPolicy.RUNTIME)
@WithSecurityContext(factory = CustomUserDetailsSecurityContextFactory.class)
public @interface WithMockCustomUser {
    long id() default 0L;                       // 사용자 ID
    String username() default "hello@darye.dev";  // 이메일 또는 사용자명
    String role() default "USER";               // 권한
    String password() default "password";       // 비밀번호
    boolean locked() default false;             // 잠금 상태
    boolean enabled() default true;             // 활성화 상태
}
