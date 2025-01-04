package hyper.darye.controller;

import hyper.darye.mvc.controller.PointController;
import hyper.darye.mvc.model.entity.Member;
import hyper.darye.config.security.SecurityConfig;
import hyper.darye.mvc.service.MemberService;
import hyper.darye.testConfig.mockUser.WithMockCustomUser;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PointController.class)
@Import(SecurityConfig.class)
public class PointControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MemberService memberService;

    @Nested
    @DisplayName("포인트 조회 테스트")
    class selectPointByMemberIdTest {

        @Test
        @DisplayName("비인증 사용자 상태로 포인트 조회 테스트")
        @WithAnonymousUser
        public void unauthorizedSelectPointByMemberId() throws Exception {
            Long memberId = 1L;

            // 회원 조회 시, 존재하지 않는 회원 정보를 반환
            when(memberService.selectByPrimaryKey(memberId)).thenReturn(null);

            // When & Then: API 호출 및 응답 검증
            mockMvc.perform(get("/api/members/1/point")
                            .with(csrf()))
                    .andExpect(status().isForbidden());
        }

        @Test
        @DisplayName("포인트 조회 테스트")
        @WithMockCustomUser(id = 1L, username = "user@darye.dev")
        public void selectPointByMemberIdTest() throws Exception {
            // Given: 테스트를 위한 데이터 설정
            Long memberId = (Long)1L;
            Member mockMember = new Member();
            mockMember.setId(memberId);
            mockMember.setPoint(100); // 예시 포인트 값 설정

            when(memberService.selectByPrimaryKey(memberId)).thenReturn(mockMember);

            // When & Then: API 호출 및 응답 검증
            mockMvc.perform(get("/api/members/1/point")
                            .with(csrf()))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.id").value(memberId)) // id 값 검증
                    .andExpect(jsonPath("$.point").value(100)); // point 값 검증
        }

        @Test
        @DisplayName("관리자 상태로 포인트 조회 테스트")
        @WithMockCustomUser(id = 1L, username = "admin@darye.dev", role = "ADMIN")
        public void selectPointByMemberIdAdminTest() throws Exception {
            // Given: 테스트를 위한 데이터 설정
            Long memberId = (Long)2L;
            Member mockMember = new Member();
            mockMember.setId(memberId);
            mockMember.setPoint(100); // 예시 포인트 값 설정

            when(memberService.selectByPrimaryKey(memberId)).thenReturn(mockMember);

            // When & Then: API 호출 및 응답 검증
            mockMvc.perform(get("/api/members/2/point")
                            .with(csrf()))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.id").value(memberId)) // id 값 검증
                    .andExpect(jsonPath("$.point").value(100)); // point 값 검증
        }
    }
}
