package hyper.darye.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import hyper.darye.dto.controller.request.PostBoardDTO;
import hyper.darye.dto.controller.request.UpdateBoardDTO;
import hyper.darye.security.SecurityConfig;
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

import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.patch;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BoardOneOnOneController.class)
@Import(SecurityConfig.class)
class BoardOneOnOneControllerTest {

    @Autowired
    private MockMvc mockMvc;

    // 빈 이름을 명시하지 않으면 hyper.darye.service.BoardService#0 로 빈이 등록됨
    // 그러나 `@PreAuthorize("@boardService.isWriter(#id, principal.getId())")`에서 @로 찾는건 정확한 빈 이름이라
    // 빈을 찾지 못해서 에러 발생 명시적으로 빈이름 지정
    @MockBean(name = "boardService")
    private BoardService boardService;

    @DisplayName("1대1 문의 등록")
    @Test
    @WithMockCustomUser(id = 1L, username = "user@darye.dev")
    void registerOneOnOneBoard() throws Exception {

        PostBoardDTO postBoardDTO = new PostBoardDTO();
        postBoardDTO.setTitle("1대1 문의 제목");
        postBoardDTO.setContent("1대1 문의 내용");

        mockMvc.perform(post("/api/customer-board")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(postBoardDTO)))
                .andExpect(status().isCreated());

    }

    @DisplayName("1대1 문의 수정 - 작성자 일때")
    @Test
    @WithMockCustomUser(id = 1L, username = "user@darye.dev")
    void updateOneOnOneBoard() throws Exception {

        Long boardId = 1L;
        Long memberId = 1L;
        UpdateBoardDTO updateBoardDTO = new UpdateBoardDTO();
        updateBoardDTO.setTitle("수정된 1대1 문의 제목");
        updateBoardDTO.setContent("수정된 1대1 문의 내용");

        when(boardService.isWriter(boardId, memberId)).thenReturn(true);

        // why new ObjectMapper??
        //위에서 뉴 안해서...?
        mockMvc.perform(patch("/api/customer-board/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(updateBoardDTO)))
                .andExpect(status().isNoContent());
    }

    @DisplayName("1대1 문의 수정 - 답변완료된 글일때")
    @Test
    @WithMockCustomUser(id = 1L, username = "user@darye.dev")
    void updateOneOnOneBoard_isUpdatable() throws Exception {

        Long boardId = 1L;
        Long memberId = 1L;
        UpdateBoardDTO updateBoardDTO = new UpdateBoardDTO();
        updateBoardDTO.setTitle("수정된 1대1 문의 제목");
        updateBoardDTO.setContent("수정된 1대1 문의 내용");

        when(boardService.isWriter(boardId, memberId)).thenReturn(true);
        when(boardService.isUpdatable(boardId)).thenReturn(true);

        mockMvc.perform(patch("/api/customer-board/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(updateBoardDTO)))
                .andExpect(status().isBadRequest());
    }

    @DisplayName("1대1 문의 수정 - 작성자가 아닐때")
    @Test
    @WithMockCustomUser(id = 1L, username = "user@darye.dev")
    void updateOneOnOneBoard_NotWriter() throws Exception {

        Long boardId = 1L;
        Long memberId = 2L;
        UpdateBoardDTO updateBoardDTO = new UpdateBoardDTO();
        updateBoardDTO.setTitle("수정된 1대1 문의 제목");
        updateBoardDTO.setContent("수정된 1대1 문의 내용");

        when(boardService.isWriter(boardId, memberId)).thenReturn(false);

        mockMvc.perform(patch("/api/customer-board/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(updateBoardDTO)))
                .andExpect(status().isForbidden());
    }

    @DisplayName("1대1 문의 삭제 - 작성자 일때")
    @Test
    @WithMockCustomUser(id = 1L, username = "user@darye.dev")
    void softDeleteOneOnOneBoard() throws Exception {

        Long boardId = 1L;
        Long memberId = 1L;

        when(boardService.isWriter(boardId, memberId)).thenReturn(true);

        mockMvc.perform(delete("/api/customer-board/1"))
                .andExpect(status().isNoContent());
    }

    @DisplayName("1대1 문의 삭제 - 작성자가 아닐때")
    @Test
    @WithMockCustomUser(id = 2L, username = "user@darye.dev")
    void softDeleteOneOnOneBoard_NotWriter() throws Exception {

        Long boardId = 1L;
        Long memberId = 2L;

        when(boardService.isWriter(boardId, memberId)).thenReturn(false);

        mockMvc.perform(delete("/api/customer-board/1"))
                .andExpect(status().isForbidden());
    }


}
