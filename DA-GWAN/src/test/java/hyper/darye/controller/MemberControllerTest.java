package hyper.darye.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import hyper.darye.dto.SignUp;
import hyper.darye.service.MemberService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class MemberControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private MemberService memberService;

    @Test
    @DisplayName("회원 가입 성공 테스트")
    void signUpSuccess() throws Exception {
        // 회원 가입 요청 데이터 준비
        SignUp signUpRequest = new SignUp();
        signUpRequest.setEmail("test@example.com");
        signUpRequest.setPassword("Password123!");
        signUpRequest.setConfirmPassword("Password123!");
        signUpRequest.setName("홍길동");
        signUpRequest.setContact("010-1234-5678");

        // Mock 서비스 동작 정의 (반환값 지정)
        Mockito.when(memberService.insertSelective(signUpRequest)).thenReturn(1);

        // 테스트 실행
        mockMvc.perform(post("/api/sign-up")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(signUpRequest)))
                .andExpect(status().isCreated());
    }

    @Test
    @DisplayName("회원 가입 실패 테스트 - 잘못된 비밀번호 확인")
    void signUpFail_WrongPasswordConfirmation() throws Exception {
        SignUp signUpRequest = new SignUp();
        signUpRequest.setEmail("test@example.com");
        signUpRequest.setPassword("Password123!");
        signUpRequest.setConfirmPassword("WrongPassword!");
        signUpRequest.setName("홍길동");
        signUpRequest.setContact("010-1234-5678");

        mockMvc.perform(post("/api/sign-up")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(signUpRequest)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.confirmPassword").value("비밀번호가 일치하지 않습니다."));
    }

}
