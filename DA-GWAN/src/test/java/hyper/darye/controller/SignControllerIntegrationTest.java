package hyper.darye.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import hyper.darye.dto.SignUp;
import hyper.darye.dto.controller.request.SignIn;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@Transactional
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

    @Nested
    @DisplayName("사인업 (Sign Up) 통합 테스트")
    class SignUpTests {

        @Test
        @DisplayName("사인업 성공 - 유효한 요청")
        @WithAnonymousUser
        void signUpSuccess() throws Exception {
            SignUp signUpRequest = createSignUpDto("테스트사용자", "test@example.com", "Password123!", "Password123!", "010-1234-5678");

            mockMvc.perform(post("/api/sign-up")
                            .with(csrf())
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(signUpRequest)))
                    .andExpect(status().isCreated());
        }

        @Test
        @DisplayName("사인업 실패 - 필수 필드 누락")
        @WithAnonymousUser
        void signUpFailMissingRequiredField() throws Exception {
            SignUp signUpRequest = createSignUpDto(null, "test@example.com", "Password123!", "Password123!", null);

            mockMvc.perform(post("/api/sign-up")
                            .with(csrf())
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(signUpRequest)))
                    .andExpect(status().isBadRequest());
        }

        @Test
        @DisplayName("사인업 실패 - 비밀번호 불일치")
        @WithAnonymousUser
        void signUpFailPasswordMismatch() throws Exception {
            SignUp signUpRequest = createSignUpDto("테스트사용자", "test@example.com", "Password123!", "WrongPassword!", "010-1234-5678");

            mockMvc.perform(post("/api/sign-up")
                            .with(csrf())
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(signUpRequest)))
                    .andExpect(status().isBadRequest());
        }
    }

    @Nested
    @DisplayName("사인인 (Sign In) 통합 테스트")
    class SignInTests {

        @Test
        @DisplayName("사인인 성공 - 유효한 사용자")
        @WithAnonymousUser
        void signInSuccess() throws Exception {
            SignUp signUpRequest = createSignUpDto("테스트사용자", "test@example.com", "Password123!", "Password123!", "010-1234-5678");
            mockMvc.perform(post("/api/sign-up")
                            .with(csrf())
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(signUpRequest)))
                    .andExpect(status().isCreated());

            SignIn signInRequest = createSignInDto("test@example.com", "Password123!");
            mockMvc.perform(post("/api/sign-in")
                            .with(csrf())
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(signInRequest)))
                    .andExpect(status().isNoContent());
        }

        @Test
        @DisplayName("사인인 실패 - 잘못된 자격 증명")
        @WithAnonymousUser
        void signInFailInvalidCredentials() throws Exception {
            SignIn signInRequest = createSignInDto("invalid@example.com", "WrongPassword!");
            mockMvc.perform(post("/api/sign-in")
                            .with(csrf())
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(signInRequest)))
                    .andExpect(status().isUnauthorized());
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
