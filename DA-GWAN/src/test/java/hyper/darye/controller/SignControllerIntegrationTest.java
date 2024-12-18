package hyper.darye.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import hyper.darye.dto.Member;
import hyper.darye.dto.SignUp;
import hyper.darye.mapper.MemberMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class SignControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MemberMapper memberMapper;

    /**
     * 랜덤 이메일 생성 메서드
     */
    private String generateRandomEmail() {
        return "testuser_" + UUID.randomUUID() + "@example.com";
    }


    @Test
    @DisplayName("회원 가입 성공 - DB 저장 확인")
    void signUpSuccessAndDBCheck() throws Exception {
        // 회원가입 요청 데이터 생성
        // 랜덤 이메일 생성
        String randomEmail = generateRandomEmail();

        SignUp signUpRequest = new SignUp();
        signUpRequest.setEmail(randomEmail);
        signUpRequest.setPassword("Password123!");
        signUpRequest.setConfirmPassword("Password123!");
        signUpRequest.setName("홍길동");
        signUpRequest.setContact("010-5678-1234");

        // API 요청 실행
        mockMvc.perform(post("/api/sign-up")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(signUpRequest)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.message").value("회원 가입 성공"));

        // DB 확인
        Member savedMember = memberMapper.selectByEmail("testuser@example.com");

        assertThat(savedMember).isNotNull();
        assertThat(savedMember.getEmail()).isEqualTo("testuser@example.com");
        assertThat(savedMember.getName()).isEqualTo("홍길동");
        assertThat(savedMember.getMobile()).isEqualTo("010-5678-1234");
    }

    @Test
    @DisplayName("회원 가입 실패 - 유효성 검증 실패 확인")
    void signUpValidationFail() throws Exception {
        // 잘못된 회원가입 요청 데이터 생성 (비밀번호 불일치)
        SignUp invalidRequest = new SignUp();
        invalidRequest.setEmail("invaliduser@example.com");
        invalidRequest.setPassword("Password123!");
        invalidRequest.setConfirmPassword("WrongPassword!");
        invalidRequest.setName("이상한사용자");
        invalidRequest.setContact("010-0000-0000");

        // API 요청 실행 및 검증
        mockMvc.perform(post("/api/sign-up")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidRequest)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.confirmPassword").value("비밀번호가 일치하지 않습니다."));

        // DB 확인 (저장되지 않아야 함)
        Member nonExistentMember = memberMapper.selectByEmail("invaliduser@example.com");
        assertThat(nonExistentMember).isNull();
    }
}
