package hyper.darye.testConfig.mockUser;

import hyper.darye.model.entity.Member;
import hyper.darye.security.CustomUserDetails;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithSecurityContextFactory;

/**
 * CustomUserDetails 기반 Security Context 생성
 */
public class CustomUserDetailsSecurityContextFactory implements WithSecurityContextFactory<WithMockCustomUser> {

    @Override
    public SecurityContext createSecurityContext(WithMockCustomUser customUser) {
        SecurityContext context = SecurityContextHolder.createEmptyContext();

        // Member 객체 생성 및 초기화
        Member member = new Member();
        member.setId(customUser.id());
        member.setEmail(customUser.username());
        member.setRole(customUser.role());
        member.setPassword(customUser.password());
        member.setLocked(customUser.locked());
        member.setDeletedDate(customUser.enabled() ? null : new java.util.Date());

        // CustomUserDetails 생성
        CustomUserDetails customUserDetails = new CustomUserDetails(member);

        // Authentication 설정
        Authentication auth = new UsernamePasswordAuthenticationToken(customUserDetails, null, customUserDetails.getAuthorities());
        context.setAuthentication(auth);

        return context;
    }
}
