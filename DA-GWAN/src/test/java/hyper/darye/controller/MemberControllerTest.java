package hyper.darye.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import hyper.darye.dto.Member;
import hyper.darye.security.SecurityConfig;
import hyper.darye.service.MemberService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Date;

import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doReturn;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(MemberController.class)
@Import(SecurityConfig.class)
class MemberControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private MemberService memberService;

    @Test
    @WithMockUser
    void selectMemberByEmailTest() throws Exception {
        Date birthdate = new Date(1990 - 1900, 0, 1);
        Member testMember = new Member(0L, "john.doe@example.com", "p123",
                "John Doe", 'M', birthdate, "010-1234-5678");

        given(memberService.selectByEmail("john.doe@example.com")).willReturn(testMember);

        mockMvc.perform(get("/api/members?email=john.doe@example.com")
                        .with(csrf())
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email", is("john.doe@example.com")))
                .andExpect(jsonPath("$.name", is("John Doe")));
    }

    @Test
    void selectMemberByIdTest() throws Exception {
        Date birthdate = new Date(1990 - 1900, 0, 1);
        Member testMember = new Member(0L, "john.doe@example.com", "p123",
                "John Doe", 'M', birthdate, "010-1234-5678");

        given(memberService.selectMemberById(0L)).willReturn(testMember);

        mockMvc.perform(get("/api/members/0")
                        .with(csrf())
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(0)))
                .andExpect(jsonPath("$.name", is("John Doe")));
    }

    @Test
    void softDeleteMemberByIdTest() throws Exception {
        Date birthdate = new Date(1990 - 1900, 0, 1);
        Member testMember = new Member(0L, "john.doe@example.com", "p123",
                "John Doe", 'M', birthdate, "010-1234-5678");

        mockMvc.perform(delete("/api/members/")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testMember)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.confirmPassword").value("비밀번호가 일치하지 않습니다."));
    }

    @Test
    void updateMemberByIdSelectiveTest() throws Exception {
        Date birthdate = new Date(1990 - 1900, 0, 1);
        Member testMember = new Member(0L, "john.doe@example.com", "p123",
                "John Doe", 'M', birthdate, "010-1234-5678");

        String jsonRequest = objectMapper.writeValueAsString(testMember);

        mockMvc.perform(put("/api/members/0")
                        .with(csrf())
                        .contentType("application/json")
                        .content(jsonRequest))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("john.doe@example.com"))
                .andExpect(jsonPath("$.name").value("John Doe"))
                .andExpect(jsonPath("$.sex").value("M"));
    }
}