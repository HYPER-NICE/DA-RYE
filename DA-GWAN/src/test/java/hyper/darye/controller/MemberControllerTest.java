package hyper.darye.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import hyper.darye.mvc.controller.MemberController;
import hyper.darye.mvc.model.entity.Member;
import hyper.darye.mvc.model.dto.controller.request.MemberUpdateRequest;
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
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.NoSuchElementException;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
                mockMvc.perform(get("/api/members/1")
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
        @WithMockCustomUser(id = 1L, username = "user@darye.dev")
        class UserRoleTests {

            @Test
            @DisplayName("자신의 정보를 조회 - ID로 조회")
            void selectOwnMemberByPrimaryKeyTest() throws Exception {
                // When & Then
                mockMvc.perform(get("/api/members/1")
                                .with(csrf()))
                        .andExpect(status().isOk());
            }

            @Test
            @DisplayName("다른 회원의 정보를 조회하면 실패 - ID로 조회")
            void selectOtherMemberByPrimaryKeyTest() throws Exception {
                // When & Then
                mockMvc.perform(get("/api/members/2")
                                .with(csrf()))
                        .andExpect(status().isForbidden());
            }

            @Test
            @DisplayName("자신의 정보를 조회 - 이메일로 조회")
            void selectOwnMemberByEmailTest() throws Exception {
                // When & Then
                mockMvc.perform(get("/api/members")
                                .param("email", "user@darye.dev")
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
        @DisplayName("관리자 사용자 상태에서 회원 정보 조회")
        @WithMockCustomUser(id = 1L, username = "admin@darye.dev", role = "ADMIN") // 관리자 권한을 가진 사용자
        class AdminRoleTests {
            @Test
            @DisplayName("자신의 정보를 조회 - ID로 조회")
            void selectOwnMemberByPrimaryKeyTest() throws Exception {
                // When & Then
                mockMvc.perform(get("/api/members/1")
                                .with(csrf()))
                        .andExpect(status().isOk());
            }

            @Test
            @DisplayName("다른 사용자의 정보를 조회 - ID로 조회")
            void selectOtherMemberByPrimaryKeyTest() throws Exception {
                // When & Then
                mockMvc.perform(get("/api/members/2")
                                .with(csrf()))
                        .andExpect(status().isOk());
            }

            @Test
            @DisplayName("자신의 정보를 조회 - 이메일로 조회")
            void selectOwnMemberByEmailTest() throws Exception {
                // When & Then
                mockMvc.perform(get("/api/members")
                                .param("email", "admin@darye.dev")
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

            @Test
            @DisplayName("회원 정보 조회 실패 - 기본 키로 조회 (없는 기본 키)")
            void selectByPrimaryKeyNotFoundTest() throws Exception {
                when(memberService.selectByPrimaryKey(0L)).thenThrow(new NoSuchElementException("존재하지 않는 키입니다."));
                // When & Then
                mockMvc.perform(get("/api/members/0")
                                .with(csrf()))
                        .andExpect(status().isNotFound());  // 404
            }
            
            @Test
            @DisplayName("회원 정보 조회 실패 - 이메일로 조회 (없는 이메일)")
            void selectMemberByEmailNotFoundTest() throws Exception {
                // Given: 이메일이 존재하지 않도록 설정
                when(memberService.selectByEmail("nonexistent@darye.dev")).thenThrow(new NoSuchElementException("등록되지 않은 이메일입니다."));
                // When & Then
                mockMvc.perform(get("/api/members")
                                .param("email", "nonexistent@darye.dev")
                                .with(csrf()))
                        .andExpect(status().isNotFound());  // 404
            }
        }
    }

    @Nested
    @DisplayName("회원 정보 수정 테스트")
    class UpdateMemberTests {

        @Nested
        @DisplayName("비인증 사용자 상태에서 회원 정보 수정")
        @WithAnonymousUser // 공통 적용 기능, 인증되지 않은 사용자
        class UnauthorizedTests {
            @Test
            @DisplayName("회원 정보 수정")
            void updateMemberByPrimaryKeyTest() throws Exception {
                MemberUpdateRequest testMember = new MemberUpdateRequest();
                String jsonRequest = objectMapper.writeValueAsString(testMember);

                mockMvc.perform(put("/api/members/1")
                                .with(csrf())
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(jsonRequest))
                        .andExpect(status().isForbidden());
            }

            @Test
            @DisplayName("회원 정보 수정")
            void updateMemberByEmailTest() throws Exception {
                MemberUpdateRequest testMember = new MemberUpdateRequest();
                String jsonRequest = objectMapper.writeValueAsString(testMember);

                mockMvc.perform(put("/api/members/2")
                                .with(csrf())
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(jsonRequest))
                        .andExpect(status().isForbidden());
            }
        }

        @Nested
        @DisplayName("일반 사용자 상태에서 회원 정보 수정")
        @WithMockCustomUser(id = 1L, username = "test@darye.dev", role = "USER") // 일반 사용자
        class UserRoleTests {

            @Test
            @DisplayName("자신의 정보 수정")
            void updateOwnMemberByPrimaryKeySelectiveTest() throws Exception {
                MemberUpdateRequest testMember = new MemberUpdateRequest();
                String jsonRequest = objectMapper.writeValueAsString(testMember);

                mockMvc.perform(put("/api/members/1")
                                .with(csrf())
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(jsonRequest))
                        .andExpect(status().isNoContent());
            }

            @Test
            @DisplayName("타인의 정보 수정")
            void updateOtherMemberByPrimaryKeySelectiveTest() throws Exception {
                MemberUpdateRequest testMember = new MemberUpdateRequest();
                String jsonRequest = objectMapper.writeValueAsString(testMember);

                // When & Then
                mockMvc.perform(put("/api/members/2")
                                .with(csrf())
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(jsonRequest))
                        .andExpect(status().isForbidden());
            }
        }

        @Nested
        @DisplayName("관리자 사용자 상태에서 회원 정보 수정")
        @WithMockCustomUser(id = 1L, username = "admin@darye.dev", role = "ADMIN") // 관리자
        class AdminRoleTests {
            @Test
            @DisplayName("자신의 정보 수정")
            void updateOwnMemberByPrimaryKeySelectiveTest() throws Exception {
                MemberUpdateRequest testMember = new MemberUpdateRequest();
                String jsonRequest = objectMapper.writeValueAsString(testMember);

                mockMvc.perform(put("/api/members/1")
                                .with(csrf())
                                .contentType(MediaType.APPLICATION_JSON)
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
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(jsonRequest))
                        .andExpect(status().isNoContent());
            }

            @Test
            @DisplayName("타인의 정보 수정 실패 - 없는 ID")
            void updateOtherMemberByPrimaryKeyNotFoundTest() throws Exception {
                MemberUpdateRequest testRequest = new MemberUpdateRequest();
                String jsonRequest = objectMapper.writeValueAsString(testRequest);

                doThrow(new NoSuchElementException("존재하지 않는 키입니다.")).when(memberService).updateByPrimaryKeySelective(any(Member.class));

                mockMvc.perform(put("/api/members/0")
                                .with(csrf())
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(jsonRequest))
                        .andExpect(status().isNotFound());
            }
        }
    }

    /**
     * 회원 삭제 테스트 추가
     */
    @Nested
    @DisplayName("회원 삭제 테스트")
    class DeleteMemberTests {

        @Nested
        @DisplayName("비인증 사용자 상태에서 회원 정보 삭제")
        @WithAnonymousUser // 인증되지 않은 사용자
        class UnauthorizedTests {
            @Test
            @DisplayName("회원 정보 삭제")
            void deleteMemberAsAnonymousTest() throws Exception {
                // When & Then
                mockMvc.perform(delete("/api/members/1")
                                .with(csrf()))
                        .andExpect(status().isForbidden());
            }

            @Test
            @DisplayName("회원 정보 삭제")
            void deleteOtherMemberAsAnonymousTest() throws Exception {
                // When & Then
                mockMvc.perform(delete("/api/members/2")
                                .with(csrf()))
                        .andExpect(status().isForbidden());
            }
        }

        @Nested
        @DisplayName("일반 사용자 상태에서 회원 정보 삭제")
        @WithMockCustomUser(id = 1L, username = "test@darye.dev", role = "USER") // 일반 사용자
        class UserRoleTests {
            @Test
            @DisplayName("자신의 정보 삭제")
            void deleteOwnMemberTest() throws Exception {
                // When & Then
                mockMvc.perform(delete("/api/members/1")
                                .with(csrf()))
                        .andExpect(status().isNoContent());
            }

            @Test
            @DisplayName("타인의 정보 삭제")
            void deleteOtherMemberTest() throws Exception {
                // When & Then
                mockMvc.perform(delete("/api/members/2")
                                .with(csrf()))
                        .andExpect(status().isForbidden());
            }
        }

        @Nested
        @DisplayName("관리자 사용자 상태에서 회원 정보 삭제")
        @WithMockCustomUser(id = 1L, username = "admin@darye.dev", role = "ADMIN") // 관리자
        class AdminRoleTests {
            @Test
            @DisplayName("자신의 정보 삭제")
            void deleteOwnMemberAsAdminTest() throws Exception {
                // When & Then
                mockMvc.perform(delete("/api/members/1")
                                .with(csrf()))
                        .andExpect(status().isNoContent());
            }

            @Test
            @DisplayName("타인의 정보 삭제")
            void deleteOtherMemberAsAdminTest() throws Exception {
                // When & Then
                mockMvc.perform(delete("/api/members/2")
                                .with(csrf()))
                        .andExpect(status().isNoContent());
            }
        }
    }

    @Nested
    @DisplayName("회원 암호 수정 테스트")
    class UpdatePasswordTests {

        @Nested
        @DisplayName("비인증 사용자 상태에서 회원 암호 수정")
        @WithAnonymousUser // 인증되지 않은 사용자
        class UnauthorizedTests {

            @Test
            @DisplayName("회원 암호 수정 실패 - 비인증 사용자")
            void updatePasswordAsAnonymousTest() throws Exception {
                // When & Then
                mockMvc.perform(patch("/api/members/1")
                                .with(csrf())
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("{\"oldPassword\":\"oldPass123\", \"newPassword\":\"newPass123\", \"confirmPassword\":\"newPass123\"}"))
                        .andExpect(status().isForbidden()); // 비인증 사용자에게는 접근 불가
            }
        }

        @Nested
        @DisplayName("일반 사용자 상태에서 회원 암호 수정")
        @WithMockCustomUser(id = 1L, username = "user@darye.dev", role = "USER") // 일반 사용자
        class UserRoleTests {

            @Test
            @DisplayName("자신의 암호 수정 성공")
            void updateOwnPasswordTest() throws Exception {
                // When & Then
                mockMvc.perform(patch("/api/members/1")
                                .with(csrf())
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("{\"oldPassword\":\"oldPass123\", \"newPassword\":\"newPass123\", \"confirmPassword\":\"newPass123\"}"))
                        .andExpect(status().isNoContent()); // 암호 수정 후 204 반환
            }

            @Test
            @DisplayName("다른 사용자의 암호 수정 실패")
            void updateOtherPasswordTest() throws Exception {
                // When & Then
                mockMvc.perform(patch("/api/members/2")
                                .with(csrf())
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("{\"oldPassword\":\"oldPass123\", \"newPassword\":\"newPass123\", \"confirmPassword\":\"newPass123\"}"))
                        .andExpect(status().isForbidden()); // 타인의 암호는 수정할 수 없음
            }
        }

        @Nested
        @DisplayName("관리자 사용자 상태에서 회원 암호 수정")
        @WithMockCustomUser(id = 1L, username = "admin@darye.dev", role = "ADMIN") // 관리자
        class AdminRoleTests {

            @Test
            @DisplayName("자신의 암호 수정 성공")
            void updateOwnPasswordAsAdminTest() throws Exception {
                // When & Then
                mockMvc.perform(patch("/api/members/1")
                                .with(csrf())
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("{\"oldPassword\":\"oldPass123\", \"newPassword\":\"newPass123\", \"confirmPassword\":\"newPass123\"}"))
                        .andExpect(status().isNoContent()); // 암호 수정 후 204 반환
            }

            @Test
            @DisplayName("타인의 암호 수정 성공 - 관리자")
            void updateOtherPasswordAsAdminTest() throws Exception {
                // When & Then
                mockMvc.perform(patch("/api/members/2")
                                .with(csrf())
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("{\"oldPassword\":\"oldPass123\", \"newPassword\":\"newPass123\", \"confirmPassword\":\"newPass123\"}"))
                        .andExpect(status().isNoContent()); // 관리자는 다른 사용자의 암호도 수정 가능
            }

            @Test
            @DisplayName("타인의 암호 수정 실패 - 없는 ID")
            void updateOtherPasswordAsAdminNotFoundTest() throws Exception {
                doThrow(new NoSuchElementException("존재하지 않는 키입니다.")).when(memberService).softDeleteByPrimaryKey(0L);
                // When & Then
                mockMvc.perform(delete("/api/members/0")
                                .with(csrf()))
                        .andExpect(status().isNotFound());
            }
        }

        @Nested
        @DisplayName("휴대폰 번호로 이메일 찾기")
        @WithAnonymousUser
        class findEmailByContactTests {

            @Test
            @DisplayName("휴대폰 번호로 이메일 찾기 - 성공")
            void findEmailByContactTest() throws Exception {
                mockMvc.perform(get("/api/members/findEmail")
                                .param("contact", "test@darye.dev")
                                .with(csrf()))
                        .andExpect(status().isOk());
            }

            @Test
            @DisplayName("휴대폰 번호로 이메일 찾기 - 실패")
            void findEmailByContactNotFoundTest() throws Exception {
                when(memberService.findEmailByContact("010-0000-0000")).thenThrow(new NoSuchElementException("등록되지 않은 휴대폰 번호입니다."));

                mockMvc.perform(get("/api/members/findEmail")
                                .param("contact", "010-0000-0000")
                                .with(csrf()))
                        .andExpect(status().isNotFound());
            }
        }
    }
}