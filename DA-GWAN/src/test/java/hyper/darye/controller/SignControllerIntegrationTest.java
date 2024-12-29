package hyper.darye.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import hyper.darye.dto.SignUp;
import hyper.darye.dto.controller.request.SignIn;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@Transactional
@ExtendWith(SpringExtension.class)
class SignControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private SignUp createSignUpDto(String name, String email, String password, String confirmPassword, String contact) {
        SignUp signUp = new SignUp();
        signUp.setName(name);
        signUp.setEmail(email);
        signUp.setPassword(password);
        signUp.setConfirmPassword(confirmPassword);
        signUp.setContact(contact);
        return signUp;
    }

    private SignIn createSignInDto(String email, String password) {
        SignIn signIn = new SignIn();
        signIn.setEmail(email);
        signIn.setPassword(password);
        return signIn;
    }

    private void performPostRequest(String url, Object requestBody, ResultMatcher expectedStatus) throws Exception {
        mockMvc.perform(post(url)
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestBody)))
                .andExpect(expectedStatus);
    }

    @Nested
    @DisplayName("사인업 (Sign Up) 통합 테스트")
    class SignUpTests {

        @Test
        @DisplayName("사인업 성공 - 유효한 요청")
        @WithAnonymousUser
        void signUpSuccess() throws Exception {
            SignUp signUpRequest = createSignUpDto("테스트사용자", "test@example.com", "Password123!", "Password123!", "010-9999-9999");
            performPostRequest("/api/sign-up", signUpRequest, status().isCreated());
        }

        @Test
        @DisplayName("사인업 실패 - 필수 필드 누락")
        @WithAnonymousUser
        void signUpFailMissingRequiredField() throws Exception {
            SignUp signUpRequest = createSignUpDto(null, "test@example.com", "Password123!", "Password123!", null);
            performPostRequest("/api/sign-up", signUpRequest, status().isBadRequest());
        }

        @Test
        @DisplayName("사인업 실패 - 비밀번호 불일치")
        @WithAnonymousUser
        void signUpFailPasswordMismatch() throws Exception {
            SignUp signUpRequest = createSignUpDto("테스트사용자", "test@example.com", "Password123!", "WrongPassword!", "010-1234-5678");
            performPostRequest("/api/sign-up", signUpRequest, status().isBadRequest());
        }

        @Test
        @DisplayName("사인업 실패 - 이미 등록된 이메일")
        @WithAnonymousUser
        void signUpFailEmailAlreadyTaken() throws Exception {
            SignUp firstSignUpRequest = createSignUpDto("테스트사용자", "test@example.com", "Password123!", "Password123!", "010-9999-9999");
            performPostRequest("/api/sign-up", firstSignUpRequest, status().isCreated());

            SignUp secondSignUpRequest = createSignUpDto("테스트사용자2", "test@example.com", "Password123!", "Password123!", "010-8888-8888");
            performPostRequest("/api/sign-up", secondSignUpRequest, status().isBadRequest());
        }
    }

    @Nested
    @DisplayName("사인인 (Sign In) 통합 테스트")
    class SignInTests {

        @Test
        @DisplayName("사인인 성공 - 유효한 사용자")
        @WithAnonymousUser
        void signInSuccess() throws Exception {
            // 회원가입 먼저 수행
            SignUp signUpRequest = createSignUpDto("테스트사용자", "test@example.com", "Password123!", "Password123!", "010-9999-9999");
            performPostRequest("/api/sign-up", signUpRequest, status().isCreated());

            // 사인인 수행
            SignIn signInRequest = createSignInDto("test@example.com", "Password123!");
            performPostRequest("/api/sign-in", signInRequest, status().isNoContent());
        }

        @Test
        @DisplayName("사인인 실패 - 잘못된 자격 증명")
        @WithAnonymousUser
        void signInFailInvalidCredentials() throws Exception {
            SignIn signInRequest = createSignInDto("invalid@example.com", "WrongPassword!");
            performPostRequest("/api/sign-in", signInRequest, status().isUnauthorized());
        }
    }

    @Nested
    @DisplayName("사인아웃 (Sign Out) 통합 테스트")
    class SignOutTests {

        @Test
        @DisplayName("사인아웃 성공 - 인증된 사용자")
        @WithMockUser(username = "authenticatedUser", roles = {"USER"})
        void signOutSuccess() throws Exception {
            mockMvc.perform(post("/api/sign-out")
                            .with(csrf())
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isNoContent());
        }

        @Test
        @DisplayName("사인아웃 실패 - 인증되지 않은 사용자")
        @WithAnonymousUser
        void signOutFailUnauthenticated() throws Exception {
            mockMvc.perform(post("/api/sign-out")
                            .with(csrf())
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isForbidden());
        }
    }
}
