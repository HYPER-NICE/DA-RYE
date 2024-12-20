package hyper.darye.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import hyper.darye.dto.SignUp;
import hyper.darye.dto.controller.request.SignIn;
import hyper.darye.security.SecurityConfig;
import hyper.darye.service.MemberService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(SignController.class)
@Import(SecurityConfig.class)
class SignControllerUnitTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private AuthenticationManager authenticationManager;

    @MockBean
    private MemberService memberService;

    /**
     * Helper method to create SignUp DTO
     */
    private SignUp createSignUpDto(String name, String email, String password, String confirmPassword, String contact) {
        SignUp signUp = new SignUp();
        signUp.setName(name);
        signUp.setEmail(email);
        signUp.setPassword(password);
        signUp.setConfirmPassword(confirmPassword);
        signUp.setContact(contact);
        return signUp;
    }

    /**
     * Helper method to create SignIn DTO
     */
    private SignIn createSignInDto(String email, String password) {
        SignIn signIn = new SignIn();
        signIn.setEmail(email);
        signIn.setPassword(password);
        return signIn;
    }

    @Nested
    @DisplayName("사인업 (Sign Up) 테스트")
    class SignUpTests {

        @Test
        @DisplayName("사인업 성공 - 유효한 요청")
        @WithAnonymousUser
        void signUpSuccess() throws Exception {
            // Given
            SignUp signUpRequest = createSignUpDto("테스트사용자", "test@example.com", "Password123!", "Password123!", "010-1234-5678");

            // Mocking
            when(memberService.insertSelective(ArgumentMatchers.any(SignUp.class))).thenReturn(1);

            // When & Then
            mockMvc.perform(post("/api/sign-up")
                            .with(csrf())
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(signUpRequest)))
                    .andExpect(status().isCreated())
                    .andExpect(content().string("")); // No content expected
        }

        @Test
        @DisplayName("사인업 실패 - 필수 필드 누락")
        @WithAnonymousUser
        void signUpFailMissingRequiredField() throws Exception {
            // Given: Missing 'name' and 'contact'
            SignUp signUpRequest = createSignUpDto(null, "test@example.com", "Password123!", "Password123!", null);

            // When & Then
            mockMvc.perform(post("/api/sign-up")
                            .with(csrf())
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(signUpRequest)))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$[?(@.field=='name')]").exists())
                    .andExpect(jsonPath("$[?(@.field=='contact')]").exists());
        }

        @Test
        @DisplayName("사인업 실패 - 비밀번호 불일치")
        @WithAnonymousUser
        void signUpFailPasswordMismatch() throws Exception {
            // Given
            SignUp signUpRequest = createSignUpDto("테스트사용자", "test@example.com", "Password123!", "WrongPassword!", "010-1234-5678");

            // When & Then
            mockMvc.perform(post("/api/sign-up")
                            .with(csrf())
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(signUpRequest)))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$[?(@.field=='confirmPassword')]").exists());
        }

        @Test
        @DisplayName("사인업 실패 - 이미 인증된 사용자")
        @WithMockUser(username = "authenticatedUser", roles = {"USER"})
        void signUpFailAlreadyAuthenticated() throws Exception {
            // Given
            SignUp signUpRequest = createSignUpDto("테스트사용자", "test@example.com", "Password123!", "Password123!", "010-1234-5678");

            // When & Then
            mockMvc.perform(post("/api/sign-up")
                            .with(csrf())
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(signUpRequest)))
                    .andExpect(status().isForbidden());
        }
    }

    @Nested
    @DisplayName("사인인 (Sign In) 테스트")
    class SignInTests {

        @Test
        @DisplayName("사인인 성공 - 유효한 사용자")
        @WithAnonymousUser
        void signInSuccess() throws Exception {
            // Given
            SignIn signInRequest = createSignInDto("test@example.com", "Password123!");

            // Mocking AuthenticationManager
            Authentication authentication = Mockito.mock(Authentication.class);
            when(authenticationManager.authenticate(ArgumentMatchers.any(UsernamePasswordAuthenticationToken.class)))
                    .thenReturn(authentication);

            // Mocking MemberService
            doNothing().when(memberService).latestLoginDate("test@example.com");

            // When & Then
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
            // Given
            SignIn signInRequest = createSignInDto("invalid@example.com", "WrongPassword!");

            // Mocking AuthenticationManager to throw an exception
            when(authenticationManager.authenticate(ArgumentMatchers.any(UsernamePasswordAuthenticationToken.class)))
                    .thenThrow(new BadCredentialsException("Invalid credentials"));

            // When & Then
            mockMvc.perform(post("/api/sign-in")
                            .with(csrf())  // CSRF 보호 추가
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(signInRequest)))
                    .andExpect(status().isUnauthorized());  // 401 Unauthorized 기대
        }

        @Test
        @DisplayName("사인인 실패 - 이미 인증된 사용자")
        @WithMockUser(username = "authenticatedUser", roles = {"USER"})
        void signInFailAlreadyAuthenticated() throws Exception {
            // Given
            SignIn signInRequest = createSignInDto("test@example.com", "Password123!");

            // When & Then
            mockMvc.perform(post("/api/sign-in")
                            .with(csrf())
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(signInRequest)))
                    .andExpect(status().isForbidden());
        }
    }

    @Nested
    @DisplayName("사인아웃 (Sign Out) 테스트")
    class SignOutTests {

        @Test
        @DisplayName("사인아웃 성공 - 인증된 사용자")
        @WithMockUser(username = "authenticatedUser")
        void signOutSuccess() throws Exception {
            // When & Then
            mockMvc.perform(post("/api/sign-out")
                            .with(csrf())
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isNoContent());
        }

        @Test
        @DisplayName("사인아웃 실패 - 인증되지 않은 사용자")
        @WithAnonymousUser
        void signOutFailUnauthenticated() throws Exception {
            // When & Then
            mockMvc.perform(post("/api/sign-out")
                            .with(csrf())
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isForbidden());
        }
    }
}
