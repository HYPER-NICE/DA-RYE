package hyper.darye.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import hyper.darye.dto.Member;
import hyper.darye.dto.SignUp;
import hyper.darye.mapper.MemberMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.util.StringJoiner;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@AutoConfigureRestDocs // REST Docs 자동 구성
@Transactional
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

    private String generateRandomString() {
        return this.prefix + UUID.randomUUID();
    }

    private String generateRandomPhoneNumber() {
        StringJoiner randomContact = new StringJoiner("-");
        randomContact.add("010").add(String.format("%04d", (int) (Math.random() * 10000))).add(String.format("%04d", (int) (Math.random() * 10000)));
        return randomContact.toString();
    }

    @Test
    @DisplayName("회원 가입 성공 - DB 저장 확인")
    void signUpSuccessAndDBCheck() throws Exception {
        // given
        String randomEmail = generateRandomString() + domain;
        String randomContact = generateRandomPhoneNumber();

        SignUp signUpRequest = new SignUp();
        signUpRequest.setName(name);
        signUpRequest.setEmail(randomEmail);
        signUpRequest.setPassword(password);
        signUpRequest.setConfirmPassword(password);
        signUpRequest.setContact(randomContact);

        // when
        mockMvc.perform(post("/api/sign-up").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(signUpRequest)))
                // then
                .andExpect(status().isCreated())
                .andDo(document("api-signs"));

        // then
        Member member = memberMapper.selectByEmail(randomEmail);
        assertThat(member).isNotNull();
        assertThat(member.getName()).isEqualTo(name);
        assertThat(member.getEmail()).isEqualTo(randomEmail);
        assertThat(member.getMobile()).isEqualTo(randomContact);
    }

    @Test
    @DisplayName("회원 가입 실패 - 필수 필드 누락")
    void signUpMissingRequiredField() throws Exception {
        // given
        SignUp invalidRequest = new SignUp();
        invalidRequest.setEmail("missing@example.com");
        invalidRequest.setPassword(password);
        invalidRequest.setConfirmPassword(password);

        // when
        mockMvc.perform(post("/api/sign-up").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(invalidRequest)))
                // then
                .andExpect(status().isBadRequest())
                .andDo(print());
    }

    @Test
    @DisplayName("회원 가입 실패 - 전체 필드 누락")
    void signUpMissingAllFields() throws Exception {
        // given
        SignUp invalidRequest = new SignUp();

        // when
        mockMvc.perform(post("/api/sign-up").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(invalidRequest)))
                // then
                .andExpect(status().isBadRequest())
                .andDo(print());
    }

    @Test
    @DisplayName("회원 가입 실패 - 비밀번호 불일치")
    void signUpPasswordMismatch() throws Exception {
        // given
        SignUp invalidRequest = new SignUp();
        invalidRequest.setName("테스트사용자");
        invalidRequest.setEmail(generateRandomString() + domain);
        invalidRequest.setPassword(password);
        invalidRequest.setConfirmPassword("WrongPassword!");
        invalidRequest.setContact(generateRandomPhoneNumber());

        // when
        mockMvc.perform(post("/api/sign-up").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(invalidRequest)))
                // then
                .andExpect(status().isBadRequest())
                .andDo(print());
    }

    @Test
    @DisplayName("사인인 성공 - 유효한 사용자")
    void signInSuccess() throws Exception {
        // given
        SignUp signUpRequest = new SignUp();
        signUpRequest.setName(name);
        signUpRequest.setEmail(generateRandomString() + domain);
        signUpRequest.setPassword(password);
        signUpRequest.setConfirmPassword(password);
        signUpRequest.setContact(generateRandomPhoneNumber());

        // given, 테스트 용 유저를 즉석에서 생성
        mockMvc.perform(post("/api/sign-up").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(signUpRequest)))
                // then, 테스트용 유저의 생성을 확인합니다.
                .andExpect(status().isCreated());

        // when, 로그인 요청을 수행합니다.
        mockMvc.perform(post("/api/sign-in").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(signUpRequest)))
                // then, 로그인이 성공적으로 수행되었는지 확인합니다.
                .andExpect(status().isNoContent());

        // then, 로그인 이후에 최근 로그인 날짜가 업데이트 되었는지 확인합니다.
        Member member = this.memberMapper.selectByEmail(signUpRequest.getEmail());
        assertThat(member).isNotNull();
        assertThat(member.getLatestLoginDate()).isNotNull();
    }

    @Test
    @DisplayName("사인인 실패 - 없는 사용자")
    void signInFailWithNonExistentUser() throws Exception {
        // given
        SignUp signUpRequest = new SignUp();

        // when, 사인인 요청을 수행합니다.
        mockMvc.perform(post("/api/sign-in").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(signUpRequest)))
                // then, 사인인이 실패했는지 확인합니다.
                .andExpect(status().isForbidden());
    }
}
