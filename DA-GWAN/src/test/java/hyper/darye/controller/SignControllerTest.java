package hyper.darye.controller;  // 패키지 선언

// 필요한 라이브러리 및 클래스 임포트
import com.fasterxml.jackson.databind.ObjectMapper;  // JSON 직렬화/역직렬화를 위한 라이브러리
import hyper.darye.dto.Member;  // 사용자 정보를 담는 DTO 클래스
import hyper.darye.security.CustomUserDetails;  // 사용자 세부 정보를 확장하는 클래스
import hyper.darye.security.SecurityConfig;  // Spring Security 설정 클래스
import hyper.darye.service.MemberService;  // 사용자 관련 서비스 인터페이스

import org.junit.jupiter.api.Test;  // JUnit5 테스트 애너테이션
import org.springframework.beans.factory.annotation.Autowired;  // Spring 의존성 주입 애너테이션
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;  // MockMvc 자동 구성
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;  // MVC 테스트용 애너테이션
import org.springframework.boot.test.mock.mockito.MockBean;  // 목 객체 생성
import org.springframework.context.annotation.Import;  // 설정 클래스 가져오기
import org.springframework.http.MediaType;  // HTTP 미디어 타입 설정
import org.springframework.test.web.servlet.MockMvc;  // Spring MVC 테스트 도구

import java.util.StringJoiner;  // 문자열 연결을 위한 유틸리티
import java.util.UUID;  // 유니크 식별자 생성기

// 정적 임포트: 보안 및 테스트 관련 도구
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

// Spring MVC 테스트용 설정
@WebMvcTest(SignController.class)  // SignController 클래스만 로딩
@Import(SecurityConfig.class)  // 보안 설정 등록
class SignControllerTest {

    @Autowired
    private MockMvc mockMvc;  // MockMvc 주입 (HTTP 요청 시뮬레이션)

    @MockBean
    private MemberService memberService;  // MemberService 목 객체 생성

    @Autowired
    private ObjectMapper objectMapper;  // JSON 직렬화/역직렬화 객체

    // 상수 선언 (테스트용 기본 값)
    private final String prefix = "test_";  // 이메일 접두사
    private final String domain = "@darye.dev";  // 이메일 도메인
    private final String name = "테스트사용자";  // 테스트 사용자 이름
    private final String password = "Password123!";  // 테스트 비밀번호

    // 유니크 문자열 생성 메서드 (테스트 데이터 생성)
    private String generateRandomString() {
        return this.prefix + UUID.randomUUID();  // 고유 식별자 생성
    }

    // 랜덤 전화번호 생성기 (테스트 데이터 생성)
    private String generateRandomPhoneNumber() {
        StringJoiner randomContact = new StringJoiner("-");
        randomContact.add("010")
                .add(String.format("%04d", (int) (Math.random() * 10000)))
                .add(String.format("%04d", (int) (Math.random() * 10000)));
        return randomContact.toString();  // "010-XXXX-YYYY" 형식 반환
    }

}
