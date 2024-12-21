package hyper.darye.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import hyper.darye.dto.Member;
import hyper.darye.dto.MemberUpdateRequest;
import hyper.darye.security.SecurityConfig;
import hyper.darye.service.MemberService;
import hyper.darye.testConfig.mockUser.WithMockCustomUser;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithAnonymousUser;
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


    @Nested
    @DisplayName("회원 조회 테스트")
    class SelectMemberTests {

        @Nested
        @DisplayName("비인증 사용자 상태에서 회원 정보 조회")
        @WithAnonymousUser // 공통 적용 기능, 인증되지 않은 사용자
        class UnauthorizedTests {
            @Test
            @DisplayName("회원 정보 조회 실패 - ID로 조회")
            void selectMemberByPrimaryKeyTest() throws Exception {
                // When & Then
                mockMvc.perform(get("/api/members/20")
                                .with(csrf()))
                        .andExpect(status().isForbidden());
            }

            @Test
            @DisplayName("회원 정보 조회 실패 - 이메일로 조회")
            void selectMemberByEmailTest() throws Exception {
                // When & Then
                mockMvc.perform(get("/api/members")
                                .param("email", "hello@darye.dev")
                                .with(csrf()))
                        .andExpect(status().isForbidden());
            }
        }

        @Nested
        @DisplayName("일반 사용자 상태에서 회원 정보 조회")
        @WithMockCustomUser(id = 20L, username = "test@darye.dev") // 사용자 ID 20L로 인증된 사용자
        class UserRoleTests {

            /**
             * 이 테스트 케이스의 목적은 내가 내 정보를 조회할 수 있는지 확인하는 것입니다.
             * 데이터의 확인은 필요하지 않습니다.
             */
            @Test
            @DisplayName("자신의 정보를 조회 - ID로 조회")
            void selectOwnMemberByPrimaryKeyTest() throws Exception {
                // When & Then
                mockMvc.perform(get("/api/members/20")
                                .with(csrf()))
                        .andExpect(status().isOk());
            }

            /**
             * 이 테스트 케이스의 목적은 내가 다른 사람의 정보를 조회할 수 없음을 확인하는 것입니다.
             * 데이터의 확인은 필요하지 않습니다.
             */
            @Test
            @DisplayName("다른 회원의 정보를 조회하면 실패 - ID로 조회")
            void selectOtherMemberByPrimaryKeyTest() throws Exception {
                // When & Then
                mockMvc.perform(get("/api/members/30")
                                .with(csrf()))
                        .andExpect(status().isForbidden());
            }

            @Test
            @DisplayName("자신의 정보를 조회 - 이메일로 조회")
            void selectOwnMemberByEmailTest() throws Exception {
                // When & Then
                mockMvc.perform(get("/api/members")
                                .param("email", "test@darye.dev")
                                .with(csrf()))
                        .andExpect(status().isOk());
            }

            @Test
            @DisplayName("다른 회원의 정보를 조회하면 실패 - 이메일로 조회")
            void selectOtherMemberByEmailTest() throws Exception {
                // When & Then
                mockMvc.perform(get("/api/members")
                                .param("email", "other@darye.dev")
                                .with(csrf()))
                        .andExpect(status().isForbidden());
            }
        }

        @Nested
        @DisplayName("관지라 사용자 상태에서 회원 정보 조회")
        @WithMockCustomUser(role = "ADMIN", username = "test@darye.dev") // 관리자 권한을 가진 사용자
        class AdminRoleTests {
            @Test
            @DisplayName("자신의 정보를 조회 - ID로 조회")
            void selectOwnMemberByPrimaryKeyTest() throws Exception {
                // When & Then
                mockMvc.perform(get("/api/members/20")
                                .with(csrf()))
                        .andExpect(status().isOk());
            }

            @Test
            @DisplayName("다른 사용자의 정보를 조회 - ID로 조회")
            void selectOtherMemberByPrimaryKeyTest() throws Exception {
                // When & Then
                mockMvc.perform(get("/api/members/20")
                                .with(csrf()))
                        .andExpect(status().isOk());
            }

            @Test
            @DisplayName("자신의 정보를 조회 - 이메일로 조회")
            void selectOwnMemberByEmailTest() throws Exception {
                // When & Then
                mockMvc.perform(get("/api/members")
                                .param("email", "test@darye.dev")
                                .with(csrf()))
                        .andExpect(status().isOk());
            }

            @Test
            @DisplayName("다른 사용자의 정보를 조회 - 이메일로 조회")
            void selectOtherMemberByEmailTest() throws Exception {
                // When & Then
                mockMvc.perform(get("/api/members")
                                .param("email", "other@darye.dev")
                                .with(csrf()))
                        .andExpect(status().isOk());
            }
        }
    }

    @Nested
    @DisplayName("회원 정보 수정 테스트")
    class UpdateMemberTests {

        @Nested
        @DisplayName("비인증 사용자 상태에서 회원 정보 조회")
        @WithAnonymousUser // 공통 적용 기능, 인증되지 않은 사용자
        class UnauthorizedTests {
            @Test
            @DisplayName("회원 정보 수정")
            void selectMemberByPrimaryKeyTest() throws Exception {
                MemberUpdateRequest testMember = new MemberUpdateRequest();

                String jsonRequest = objectMapper.writeValueAsString(testMember);

                mockMvc.perform(put("/api/members/20")
                                .with(csrf())
                                .contentType("application/json")
                                .content(jsonRequest))
                        .andExpect(status().isForbidden());
            }

            @Test
            @DisplayName("회원 정보 수정")
            void selectMemberByEmailTest() throws Exception {
                MemberUpdateRequest testMember = new MemberUpdateRequest();

                String jsonRequest = objectMapper.writeValueAsString(testMember);

                mockMvc.perform(put("/api/members/2")
                                .with(csrf())
                                .contentType("application/json")
                                .content(jsonRequest))
                        .andExpect(status().isForbidden());
            }
        }

        @Nested
        @DisplayName("일반 사용자 상태에서 회원 정보 수정")
        @WithMockCustomUser(id = 20L, username = "test@darye.dev") // 일반 사용자
        class UserRoleTests {

            @Test
            @DisplayName("자신의 정보 수정")
            void updateOwnMemberByPrimaryKeySelectiveTest() throws Exception {
                MemberUpdateRequest testMember = new MemberUpdateRequest();

                String jsonRequest = objectMapper.writeValueAsString(testMember);

                mockMvc.perform(put("/api/members/20")
                                .with(csrf())
                                .contentType("application/json")
                                .content(jsonRequest))
                        .andExpect(status().isNoContent());
            }

            @Test
            @DisplayName("타인의 정보 수정")
            void updateOtherMemberByPrimaryKeySelectiveTest() throws Exception {
                MemberUpdateRequest testMember = new MemberUpdateRequest();

                String jsonRequest = objectMapper.writeValueAsString(testMember);

                mockMvc.perform(put("/api/members/2")
                                .with(csrf())
                                .contentType("application/json")
                                .content(jsonRequest))
                        .andExpect(status().isForbidden());
            }
        }

        @Nested
        @DisplayName("관리자 사용자 상태에서 회원 정보 수정")
        @WithMockCustomUser(id = 20L, username = "test@darye.dev", role = "ADMIN") // 관리자
        class AdminRoleTests {
            @Test
            @DisplayName("자신의 정보 수정")
            void updateOwnMemberByPrimaryKeySelectiveTest() throws Exception {
                MemberUpdateRequest testMember = new MemberUpdateRequest();

                String jsonRequest = objectMapper.writeValueAsString(testMember);

                mockMvc.perform(put("/api/members/20")
                                .with(csrf())
                                .contentType("application/json")
                                .content(jsonRequest))
                        .andExpect(status().isNoContent());
            }

            @Test
            @DisplayName("타인의 정보 수정")
            void updateOtherMemberByPrimaryKeySelectiveTest() throws Exception {
                MemberUpdateRequest testMember = new MemberUpdateRequest();

                String jsonRequest = objectMapper.writeValueAsString(testMember);

                mockMvc.perform(put("/api/members/2")
                                .with(csrf())
                                .contentType("application/json")
                                .content(jsonRequest))
                        .andExpect(status().isNoContent());
            }
        }
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
}