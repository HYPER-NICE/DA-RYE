package hyper.darye.controller;

import hyper.darye.dto.SignUp;
import hyper.darye.service.MemberService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

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
    @PostMapping("/sign-in")
    public ResponseEntity<?> login(@RequestBody Map<String, String> credentials) {
        String email = credentials.get("email");
        String password = credentials.get("password");

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
    @PostMapping("/sign-up")
    public ResponseEntity<?> signUp(@Valid @RequestBody SignUp signUpRequest, BindingResult bindingResult) {
        Map<String, String> errors = new HashMap<>();

        // 유효성 검증 오류 처리
        if (bindingResult.hasErrors()) {
            for (FieldError error : bindingResult.getFieldErrors()) {
                errors.put(error.getField(), error.getDefaultMessage());
            }
        }

        // 비밀번호 확인 로직
        if (!signUpRequest.getPassword().equals(signUpRequest.getConfirmPassword())) {
            errors.put("confirmPassword", "비밀번호가 일치하지 않습니다.");
        }

        if (!errors.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
        }

        memberService.insertSelective(signUpRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(Map.of("message", "회원 가입 성공"));
    }

    /**
     * 로그아웃 엔드포인트 (현재는 임시 응답)
     */
    @PostMapping("/sign-out")
    public ResponseEntity<String> signOut() {
        SecurityContextHolder.clearContext();
        return ResponseEntity.ok("로그아웃 성공");
    }
}
