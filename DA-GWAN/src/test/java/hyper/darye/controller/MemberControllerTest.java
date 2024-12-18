package hyper.darye.controller;

import hyper.darye.dto.Member;
import hyper.darye.dto.controller.request.CreateMemberRequest;
import hyper.darye.service.MemberService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Date;

import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(MemberController.class)
class MemberControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MemberService memberService;

    Date birthdate = new Date(2001 - 1900, 0, 1);
    private CreateMemberRequest testMember = new CreateMemberRequest(0L, "test@example.com",
            "password123", "password123", "username", 'M', birthdate, "010-1234-5678");

    @BeforeEach
    void setUp() {
        doReturn(1).when(memberService).insertMember(Mockito.anyString(), Mockito.anyString(), Mockito.anyString(),
                Mockito.anyString(), Mockito.anyChar(), Mockito.any(Date.class), Mockito.anyString());
    }

    @Test
    void insertMemberTest() throws Exception {
        String jsonContent = "{" +
                "\"email\": \"test@example.com\"," +
                "\"password\": \"password123\"," +
                "\"rePassword\": \"password123\"," +
                "\"name\": \"username\"," +
                "\"sex\": \"M\"," +
                "\"birthdate\": \"2001-01-01\"," +
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
    void insertMemberByEmailTest() throws Exception {
        Member member = new Member();
        member.setEmail("test@example.com");
        member.setPassword("password123");
        member.setName("username");
        given(memberService.selectMemberByEmail("test@example.com")).willReturn(member);

        mockMvc.perform(get("/members?email=test@example.com"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email", is("test@example.com")))
                .andExpect(jsonPath("$.name", is("username")));
    }
}