package hyper.darye.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import hyper.darye.dto.Member;
import hyper.darye.security.SecurityConfig;
import hyper.darye.service.MemberService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Date;

import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(SignController.class)
@Import(SecurityConfig.class)
class MemberControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private MemberService memberService;

    @BeforeEach
    void setUp() {
        doReturn(1).when(memberService).insertMember(Mockito.anyString(), Mockito.anyString(), Mockito.anyString(),
                Mockito.anyString(), Mockito.anyChar(), Mockito.any(Date.class), Mockito.anyString());
    }

    @Test
    void insertMemberTest() throws Exception {
        String jsonContent = "{" +
                "\"email\": \"john.doe@example.com\"," +
                "\"password\": \"p123\"," +
                "\"rePassword\": \"p123\"," +
                "\"name\": \"John Doe\"," +
                "\"sex\": \"M\"," +
                "\"birthdate\": \"1990-01-01\"," +
                "\"mobile\": \"010-1234-5678\"" +
                "}";

        System.out.println("jsonContent = " + jsonContent);

        mockMvc.perform(post("/members")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonContent))
                .andExpect(status().isCreated())
                .andExpect(content().string("회원 가입 성공"));
    }

    @Test
    void selectMemberByEmailTest() throws Exception {
        Date birthdate = new Date(1990 - 1900, 0, 1);
        Member testMember = new Member(0L, "john.doe@example.com", "p123",
                "p123", "John Doe", 'M', birthdate, "010-1234-5678");

        given(memberService.selectMemberByEmail("john.doe@example.com")).willReturn(testMember);

        mockMvc.perform(get("/members?email=john.doe@example.com"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email", is("john.doe@example.com")))
                .andExpect(jsonPath("$.name", is("John Doe")));
    }

    @Test
    void selectMemberByIdTest() throws Exception {
        Date birthdate = new Date(1990 - 1900, 0, 1);
        Member testMember = new Member(0L, "john.doe@example.com", "p123",
                "p123","John Doe", 'M', birthdate, "010-1234-5678");

        given(memberService.selectMemberById(0L)).willReturn(testMember);

        mockMvc.perform(get("/members/0"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(0)))
                .andExpect(jsonPath("$.name", is("John Doe")));
    }

    @Test
    void softDeleteMemberByIdTest() throws Exception {
        Date birthdate = new Date(1990 - 1900, 0, 1);
        Member testMember = new Member(0L, "john.doe@example.com", "p123",
                "p123","John Doe", 'M', birthdate, "010-1234-5678");

        mockMvc.perform(post("/api/sign-up")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testMember)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.confirmPassword").value("비밀번호가 일치하지 않습니다."));
    }

    @Test
    void updateMemberByIdSelectiveTest() throws Exception {
        Date birthdate = new Date(1990 - 1900, 0, 1);
        Member testMember = new Member(0L, "john.doe@example.com", "p123",
                "p123","John Doe", 'M', birthdate, "010-1234-5678");

        String jsonRequest = objectMapper.writeValueAsString(testMember);

        mockMvc.perform(put("/members/0")
                        .contentType("application/json")
                        .content(jsonRequest))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("john.doe@example.com"))
                .andExpect(jsonPath("$.name").value("John Doe"))
                .andExpect(jsonPath("$.sex").value("M"));
    }
}