package hyper.darye.controller;

import hyper.darye.dto.SignUp;
import hyper.darye.dto.controller.request.SignIn;
import hyper.darye.security.CustomUserDetails;
import hyper.darye.service.MemberService;
import jakarta.validation.Valid;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class SignController {

    private final AuthenticationManager authenticationManager;
    private final MemberService memberService;

    public SignController(AuthenticationManager authenticationManager, MemberService memberService) {
        this.authenticationManager = authenticationManager;
        this.memberService = memberService;
    }

    /**
     * 사인인 엔드포인트
     */
    @PreAuthorize("isAnonymous()") // 인증되지 않은 사용자만 접근 가능
    @PostMapping("/sign-in") // POST 방식으로 사인인
    public ResponseEntity<?> login(@RequestBody SignIn signInRequest) {
        String email = signInRequest.getEmail();
        String password = signInRequest.getPassword();

        try {
            // 인증 시도
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));

            // SecurityContext 업데이트
            SecurityContextHolder.getContext().setAuthentication(authentication);

            // 인증된 사용자 정보 추출
            CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
            Long userId = userDetails.getId();

            // 사인인 성공 후 작업
            memberService.updateLatestSignInDate(userId);
            return ResponseEntity.noContent().build();
        } catch (BadCredentialsException e) {
            // 자격 증명 오류: 헤더에 메시지 추가
            HttpHeaders headers = new HttpHeaders();
            headers.add("Description", "사인인 실패: 자격 증명 오류");
            return new ResponseEntity<>(headers, HttpStatus.UNAUTHORIZED);

        } catch (Exception e) {
            // 기타 서버 오류
            HttpHeaders headers = new HttpHeaders();
            headers.add("Description", "서버 오류 발생");
            return new ResponseEntity<>(headers, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * 회원 가입 엔드포인트
     */
    @PreAuthorize("isAnonymous()") // 인증되지 않은 사용자만 접근 가능
    @PostMapping("/sign-up") // POST 방식으로 회원 가입
    @ResponseStatus(HttpStatus.CREATED)
    public void signUp(@Valid @RequestBody SignUp signUpRequest) {
        // 정상 회원 가입 처리
        memberService.insertSelective(signUpRequest);
    }

    /**
     * 로그아웃 엔드포인트 (현재는 임시 응답)
     */
    @PreAuthorize("isAuthenticated()") // 인증된 사용자만 접근 가능
    @PostMapping("/sign-out") // POST 방식으로 로그아웃
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void signOut() {
        SecurityContextHolder.clearContext();
    }
}
