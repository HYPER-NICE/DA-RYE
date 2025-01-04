package hyper.darye.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import hyper.darye.model.dto.controller.request.PostReplyDTO;
import hyper.darye.model.dto.controller.request.UpdateReplyDTO;
import hyper.darye.config.security.SecurityConfig;
import hyper.darye.service.BoardService;
import hyper.darye.testConfig.mockUser.WithMockCustomUser;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BoardReplyController.class)
@Import(SecurityConfig.class)
class BoardReplyControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean(name = "boardService")
    private BoardService boardService;

    @DisplayName("댓글 등록")
    @WithMockCustomUser(id = 1L, username = "admin@darye.dev", role = "ADMIN")
    @Test
    void registerReplyTest() throws Exception {
        // given
        Long boardId = 1L;
        PostReplyDTO postReplyDTO = new PostReplyDTO();
        postReplyDTO.setContent("댓글 내용");

        // when
        mockMvc.perform(post("/api/customer-board/1/reply")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(postReplyDTO)))
                .andExpect(status().isCreated());

        // then
        verify(boardService).insertReply(1L, postReplyDTO, 1L);

    }

    @DisplayName("댓글 등록 - 사용자일때")
    @WithMockCustomUser(id = 1L, username = "user@darye.dev", role = "USER")
    @Test
    void registerReplyTest_USER() throws Exception {
        // given
        Long boardId = 1L;
        PostReplyDTO postReplyDTO = new PostReplyDTO();
        postReplyDTO.setContent("댓글 내용");

        // when & then
        mockMvc.perform(post("/api/customer-board/1/reply")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(postReplyDTO)))
                .andExpect(status().isForbidden());

    }

    @DisplayName("댓글 수정")
    @WithMockCustomUser(id = 1L, username = "admin@darye.dev", role = "ADMIN")
    @Test
    void updateReplyTest() throws Exception {
        // given
        Long replyId = 3L;
        UpdateReplyDTO updateReplyDTO = new UpdateReplyDTO();
        updateReplyDTO.setContent("수정된 댓글 내용");

        // when
        mockMvc.perform(patch("/api/customer-board/1/reply/3")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(updateReplyDTO)))
                .andExpect(status().isNoContent());

        // then
        verify(boardService).updateReply(3L, updateReplyDTO, 1L);
    }

    @DisplayName("댓글 수정 - 사용자일때")
    @WithMockCustomUser(id = 1L, username = "user@darye.dev", role = "USER")
    @Test
    void updateReplyTest_USER() throws Exception {
        // given
        Long replyId = 3L;
        UpdateReplyDTO updateReplyDTO = new UpdateReplyDTO();
        updateReplyDTO.setContent("수정된 댓글 내용");

        // when & then
        mockMvc.perform(patch("/api/customer-board/1/reply/3")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(updateReplyDTO)))
                .andExpect(status().isForbidden());
    }

    @DisplayName("댓글 삭제")
    @WithMockCustomUser(id = 1L, username = "admin@darye.dev", role = "ADMIN")
    @Test
    void deleteReplyTest() throws Exception {
        // given
        Long replyId = 3L;

        // when
        mockMvc.perform(delete("/api/customer-board/1/reply/3"))
                .andExpect(status().isNoContent());

        // then
        verify(boardService).softDeleteBoard(3L, 1L);
    }


    @DisplayName("댓글 삭제 - 사용자일때")
    @WithMockCustomUser(id = 1L, username = "user@darye.dev", role = "USER")
    @Test
    void deleteReplyTest_USER() throws Exception {
        // given
        Long replyId = 3L;

        // when & then
        mockMvc.perform(delete("/api/customer-board/1/reply/3"))
                .andExpect(status().isForbidden());
    }

    @DisplayName("댓글 조회 - 작성자일때")
    @WithMockCustomUser(id = 1L, username = "user@darye.dev", role = "USER")
    @Test
    void selectReplyTest() throws Exception {
        // given
        Long boardId = 1L;
        Long memberId = 1L;

        when(boardService.isWriter(boardId, memberId)).thenReturn(true);

        // when & then
        mockMvc.perform(get("/api/customer-board/1/reply"))
                .andExpect(status().isOk());
    }

    @DisplayName("댓글 조회 - 작성자가 아닐때")
    @WithMockCustomUser(id = 2L, username = "user@darye.dev", role = "USER")
    @Test
    void selectReplyTest_NotWriter() throws Exception {
        // given
        Long boardId = 1L;
        Long memberId = 1L;

        when(boardService.isWriter(boardId, memberId)).thenReturn(true);

        // when & then
        mockMvc.perform(get("/api/customer-board/1/reply"))
                .andExpect(status().isForbidden());
    }

    @DisplayName("댓글 조회 - 관리자일때")
    @WithMockCustomUser(id = 3L, username = "admin@darye.dev", role = "ADMIN")
    @Test
    void selectReplyTest_ADMIN() throws Exception {
        // given
        Long boardId = 1L;
        Long memberId = 1L;

        when(boardService.isWriter(boardId, memberId)).thenReturn(true);

        // when & then
        mockMvc.perform(get("/api/customer-board/1/reply"))
                .andExpect(status().isOk());
    }



}