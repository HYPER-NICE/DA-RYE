package hyper.darye.controller;

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

import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(MemberController.class)
class MemberControllerTest {

    @Autowired
    private MockMvc mockMvc;

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
}