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
import org.springframework.web.bind.annotation.*;

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

        return ResponseEntity.ok(Map.of("message", "로그인 성공"));
    }

    /**
     * 회원 가입 엔드포인트
     */
    @PostMapping("/sign-up")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<String> signUp(@Valid @RequestBody SignUp signUpRequest) {
        // 회원 가입 실행
        memberService.insert(signUpRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body("회원 가입 성공");
    }

    /**
     * 로그아웃 엔드포인트 (현재는 임시 응답)
     */
    @PostMapping("/sign-out")
    public ResponseEntity<String> signOut() {
        // 로그아웃 처리 로직 필요 시 추가
        SecurityContextHolder.clearContext();
        return ResponseEntity.ok("로그아웃 성공");
    }
}
