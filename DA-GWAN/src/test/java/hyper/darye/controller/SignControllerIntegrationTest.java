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
    
    private final String domain = "@darye.dev";

    /**
     * 랜덤 문자열 생성 메서드
     */
    private String generateRandomString(String prefix) {
        return prefix + "_" + UUID.randomUUID();
    }
    
    private Integer generateRandomInt() {
        return generateRandomInt();
    }

    @Test
    @DisplayName("회원 가입 성공 - DB 저장 확인")
    void signUpSuccessAndDBCheck() throws Exception {
        String randomEmail = generateRandomString("user") + domain;
        String randomName = generateRandomString("홍길동");
        String randomContact = "010-" + generateRandomInt() + "-" + generateRandomInt();

        SignUp signUpRequest = new SignUp();
        signUpRequest.setEmail(randomEmail);
        signUpRequest.setPassword("Password123!");
        signUpRequest.setConfirmPassword("Password123!");
        signUpRequest.setName(randomName);
        signUpRequest.setContact(randomContact);

        mockMvc.perform(post("/api/sign-up")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(signUpRequest)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.message").value("회원 가입 성공"));

        Member savedMember = memberMapper.selectByEmail(randomEmail);

        assertThat(savedMember).isNotNull();
        assertThat(savedMember.getEmail()).isEqualTo(randomEmail);
        assertThat(savedMember.getName()).isEqualTo(randomName);
        assertThat(savedMember.getMobile()).isEqualTo(randomContact);
    }

    @Test
    @DisplayName("관리자 회원 가입 성공 - DB 저장 확인")
    void adminSignUpSuccessAndDBCheck() throws Exception {
        String randomEmail = generateRandomString("admin") + domain;
        String randomName = generateRandomString("관리자");
        String randomContact = "010-" + generateRandomInt() + "-" + generateRandomInt();

        SignUp signUpRequest = new SignUp();
        signUpRequest.setEmail(randomEmail);
        signUpRequest.setPassword("Password123!");
        signUpRequest.setConfirmPassword("Password123!");
        signUpRequest.setName(randomName);
        signUpRequest.setContact(randomContact);
        signUpRequest.setRole("ADMIN");

        mockMvc.perform(post("/api/sign-up")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(signUpRequest)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.message").value("회원 가입 성공"));

        Member savedMember = memberMapper.selectByEmail(randomEmail);

        assertThat(savedMember).isNotNull();
        assertThat(savedMember.getEmail()).isEqualTo(randomEmail);
        assertThat(savedMember.getName()).isEqualTo(randomName);
        assertThat(savedMember.getMobile()).isEqualTo(randomContact);
    }

    @Test
    @DisplayName("회원 가입 실패 - 유효성 검증 실패 확인")
    void signUpValidationFail() throws Exception {
        String randomEmail = generateRandomString("fail") + domain;

        SignUp invalidRequest = new SignUp();
        invalidRequest.setEmail(randomEmail);
        invalidRequest.setPassword("Password123!");
        invalidRequest.setConfirmPassword("WrongPassword!");
        invalidRequest.setName("잘못된사용자");
        invalidRequest.setContact("010-0000-0000");

        mockMvc.perform(post("/api/sign-up")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidRequest)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.confirmPassword").value("비밀번호가 일치하지 않습니다."));

        Member nonExistentMember = memberMapper.selectByEmail(randomEmail);
        assertThat(nonExistentMember).isNull();
    }
}
