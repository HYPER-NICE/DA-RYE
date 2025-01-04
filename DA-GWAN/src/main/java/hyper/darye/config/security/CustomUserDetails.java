package hyper.darye.config.security;

import hyper.darye.model.entity.Member;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

/**
 * 사용자 인증 및 권한 관리를 위한 CustomUserDetails
 */
public class CustomUserDetails implements UserDetails {

    private final Long id;
    private final String username;           // 사인인 식별자, 사실 이메일을 여기 넣는다. 이유는 시큐리티가 그러한 필드를 만들었는데 그걸 따라야한다...
    private final String password;        // 암호화된 비밀번호
    private final Collection<SimpleGrantedAuthority> role;  // 권한
    private final boolean isLocked;       // 계정 잠금 여부
    private final boolean isDeleted;      // 삭제 여부

    /**
     * 생성자: Member 엔티티 기반으로 초기화
     */
    public CustomUserDetails(Member member) {
        this.id = member.getId();
        this.username = member.getEmail();
        this.password = member.getPassword();
        this.isLocked = member.getLocked();
        this.isDeleted = member.getDeletedDate() != null;

        // 권한 설정
        this.role = new ArrayList<SimpleGrantedAuthority>();

        // 기본 ROLE_USER 권한 추가
        this.role.add(new SimpleGrantedAuthority("ROLE_USER"));

        // 사용자가 관리자인 경우 ROLE_ADMIN 권한 추가
        if ("ADMIN".equalsIgnoreCase(member.getRole())) {
            this.role.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
        }
    }

    public Long getId() {
        return id;
    }

    /**
     * 권한 반환: ROLE_ 접두사를 권장
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return role;
    }

    @Override
    public String getPassword() {
        return password;  // 암호화된 비밀번호 반환
    }

    @Override
    public String getUsername() {
        return username;  // 사인인 식별자 반환
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;  // 계정 만료 관리 시 수정 가능
    }

    @Override
    public boolean isAccountNonLocked() {
        return !isLocked;  // 계정 잠금 여부 확인
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;  // 자격 증명 만료 관리 시 수정 가능
    }

    @Override
    public boolean isEnabled() {
        return !isDeleted;  // 논리 삭제 확인
    }
}
