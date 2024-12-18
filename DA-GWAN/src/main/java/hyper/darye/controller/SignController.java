package hyper.darye.controller;

import hyper.darye.dto.SignUp;
import hyper.darye.dto.controller.request.SignIn;
import hyper.darye.service.MemberService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
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
     * 로그인 엔드포인트
     */
    @PreAuthorize("isAnonymous()") // 인증되지 않은 사용자만 접근 가능
    @PostMapping("/sign-in") // POST 방식으로 로그인
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void login(@RequestBody SignIn signInRequest) {
        String email = signInRequest.getEmail();
        String password = signInRequest.getPassword();

        // 인증 시도
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(email, password)
        );

        // SecurityContext 업데이트
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // 로그인 성공 후 마지막 업데이트 시간 기록
        memberService.latestLoginDate(email);
    }

    /**
     * 회원 가입 엔드포인트
     */
    @PostMapping("/sign-up") // POST 방식으로 회원 가입
    public ResponseEntity<?> signUp(@Valid @RequestBody SignUp signUpRequest, BindingResult bindingResult) {
        // 유효성 검증 오류가 있을 경우
        if (bindingResult.hasErrors()) {

            // 오류가 있을 경우, 직접 오류 메시지를 바디에 담아 리턴
            return ResponseEntity.badRequest().body(bindingResult.getFieldErrors());
        }

        // 비밀번호 확인 로직
        if (!signUpRequest.getPassword().equals(signUpRequest.getConfirmPassword())) {
            bindingResult.rejectValue("confirmPassword", "비밀번호가 일치하지 않습니다.");

            // 오류가 있을 경우, 직접 오류 메시지를 바디에 담아 리턴
            return ResponseEntity.badRequest().body(bindingResult.getFieldErrors());
        }

        // 정상 회원 가입 처리
        memberService.insertSelective(signUpRequest);
        return ResponseEntity.status(HttpStatus.CREATED).build();
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
