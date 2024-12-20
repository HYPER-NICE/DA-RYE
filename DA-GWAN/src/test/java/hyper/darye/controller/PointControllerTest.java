package hyper.darye.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class PointControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Test
    public void testGetPoint() throws Exception {
        // Given: 테스트를 위한 데이터 설정
        Long testMemberId = 1L;

        // When & Then: API 호출 및 응답 검증
        mockMvc.perform(get("/api/members/" + testMemberId + "/point")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(testMemberId)) // id 값 검증
                .andExpect(jsonPath("$.point").exists()); // point 필드가 존재하는지 확인
    }
}