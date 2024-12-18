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

import java.util.StringJoiner;
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

    private final String prefix = "test_";
    private final String domain = "@darye.dev";
    private final String name = "테스트사용자";
    private final String password = "Password123!";

    // 랜덤 문자열 생성 메서드
    private String generateRandomString() {
        return this.prefix + UUID.randomUUID();
    }

    // 랜덤 전화번호 생성기
    private String generateRandomPhoneNumber() {
        StringJoiner randomContact = new StringJoiner("-");
        randomContact.add("010")
                .add(String.format("%04d", (int) (Math.random() * 10000)))
                .add(String.format("%04d", (int) (Math.random() * 10000)));
        return randomContact.toString();
    }

    @Test
    @DisplayName("회원 가입 성공 - DB 저장 확인")
    void signUpSuccessAndDBCheck() throws Exception {
        String randomEmail = this.generateRandomString() + domain;
        String randomName = this.name;
        String randomContact = generateRandomPhoneNumber();

        SignUp signUpRequest = new SignUp();
        signUpRequest.setName(randomName);
        signUpRequest.setEmail(randomEmail);
        signUpRequest.setPassword(this.password);
        signUpRequest.setConfirmPassword(this.password);
        signUpRequest.setContact(randomContact);

        mockMvc.perform(post("/api/sign-up")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(signUpRequest)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.message").value("회원 가입 성공"));

        Member savedMember = memberMapper.selectByEmail(randomEmail);

        assertThat(savedMember).isNotNull();
        assertThat(savedMember.getName()).isEqualTo(randomName);
        assertThat(savedMember.getEmail()).isEqualTo(randomEmail);
        assertThat(savedMember.getMobile()).isEqualTo(randomContact);
    }

    @Test
    @DisplayName("관리자 회원 가입 성공 - DB 저장 확인")
    void adminSignUpSuccessAndDBCheck() throws Exception {
        String randomEmail = generateRandomString() + domain;
        String randomName = "관리자" + UUID.randomUUID();
        String randomContact = generateRandomPhoneNumber();

        SignUp signUpRequest = new SignUp();
        signUpRequest.setName(randomName);
        signUpRequest.setEmail(randomEmail);
        signUpRequest.setPassword(password);
        signUpRequest.setConfirmPassword(password);
        signUpRequest.setContact(randomContact);
        signUpRequest.setRole("ADMIN");

        mockMvc.perform(post("/api/sign-up")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(signUpRequest)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.message").value("회원 가입 성공"));

        Member savedMember = memberMapper.selectByEmail(randomEmail);

        assertThat(savedMember).isNotNull();
        assertThat(savedMember.getName()).isEqualTo(randomName);
        assertThat(savedMember.getEmail()).isEqualTo(randomEmail);
        assertThat(savedMember.getMobile()).isEqualTo(randomContact);
    }

    @Test
    @DisplayName("회원 가입 실패 - 비밀번호 불일치 확인")
    void signUpValidationFail() throws Exception {
        String randomEmail = generateRandomString() + domain;
        String randomContact = generateRandomPhoneNumber();

        SignUp invalidRequest = new SignUp();
        invalidRequest.setName("잘못된사용자");
        invalidRequest.setEmail(randomEmail);
        invalidRequest.setPassword(password);
        invalidRequest.setConfirmPassword("WrongPassword!");
        invalidRequest.setContact(randomContact);

        mockMvc.perform(post("/api/sign-up")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidRequest)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.confirmPassword").value("비밀번호가 일치하지 않습니다."));

        Member nonExistentMember = memberMapper.selectByEmail(randomEmail);
        assertThat(nonExistentMember).isNull();
    }
}
