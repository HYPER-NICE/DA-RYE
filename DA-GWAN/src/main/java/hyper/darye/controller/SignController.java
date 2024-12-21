package hyper.darye.controller;

import hyper.darye.dto.Member;
import hyper.darye.dto.SignUp;
import hyper.darye.dto.controller.request.SignIn;
import hyper.darye.security.CustomUserDetails;
import hyper.darye.service.MemberService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.web.bind.annotation.*;

@Tag(name = "사인 컨트롤러", description = "인증 관련 컨트롤러 입니다")
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
    @PostMapping("/sign-in")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void signIn(@RequestBody SignIn signInRequest, HttpServletRequest request) {
        String email = signInRequest.getEmail();
        String password = signInRequest.getPassword();

        // 인증 시도
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(email, password)
        );

        // SecurityContext 업데이트
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // 세션에 SecurityContext 저장
        HttpSession session = request.getSession(true);
        session.setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY,
                SecurityContextHolder.getContext());

        // 인증된 사용자 정보 추출
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        Long userId = userDetails.getId();

        // 사인인 성공 후 작업
        memberService.updateLatestSignInDate(userId);
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
    public void signOut(HttpServletRequest request, HttpServletResponse response) {
        // 세션 무효화
        request.getSession().invalidate();

        // SecurityContext 비우기
        SecurityContextHolder.clearContext();

        // 쿠키 삭제
        Cookie cookie = new Cookie("JSESSIONID", null);
        cookie.setPath("/"); // 쿠키의 경로 설정
        cookie.setHttpOnly(true); // HTTP 전송만 허용
        cookie.setMaxAge(0); // 쿠키 만료 시간 설정 (즉시 삭제)
        response.addCookie(cookie);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/check/admin")
    public Member checkAdmin(@AuthenticationPrincipal CustomUserDetails principal) {
        return memberService.selectByPrimaryKey(principal.getId());
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/check/user")
    public Member checkUser(@AuthenticationPrincipal CustomUserDetails principal) {
        return memberService.selectByPrimaryKey(principal.getId());
    }
}
