package hyper.darye.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Map;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class SignControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    @DisplayName("로그인 성공 테스트")
    void loginSuccess() throws Exception {
        // 가상 사용자 생성 (테스트 전 DB에 가상 유저 생성 필요)
        Map<String, String> credentials = Map.of(
                "email", "root@darye.dev",
                "password", "1234"
        );

        // 요청 실행 및 검증
        mockMvc.perform(post("/api/sign-in")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(credentials))
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("로그인 성공"));
    }

    @Test
    @DisplayName("로그인 실패 테스트 - 잘못된 비밀번호")
    void loginFail_WrongPassword() throws Exception {
        Map<String, String> credentials = Map.of(
                "email", "test@example.com",
                "password", "wrongpassword"
        );

        mockMvc.perform(post("/api/sign-in")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(credentials))
                )
                .andExpect(status().isUnauthorized());
    }

    @Test
    @DisplayName("로그인 실패 테스트 - 잘못된 이메일")
    void loginFail_WrongEmail() throws Exception {
        Map<String, String> credentials = Map.of(
                "email", "wrong@example.com",
                "password", "password123"
        );

        mockMvc.perform(post("/api/sign-in")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(credentials))
                )
                .andExpect(status().isUnauthorized());
    }
}
