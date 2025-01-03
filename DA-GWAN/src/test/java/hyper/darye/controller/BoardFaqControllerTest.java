package hyper.darye.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import hyper.darye.mvc.controller.BoardFaqController;
import hyper.darye.mvc.model.dto.controller.request.PostBoardDTO;
import hyper.darye.mvc.model.dto.controller.request.UpdateBoardDTO;
import hyper.darye.config.security.SecurityConfig;
import hyper.darye.mvc.service.BoardService;
import hyper.darye.testConfig.mockUser.WithMockCustomUser;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BoardFaqController.class)
@Import(SecurityConfig.class)
class BoardFaqControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BoardService boardService;

    @DisplayName("faq 등록 - 관리자")
    @WithMockCustomUser(id = 1L, username = "admin@darye.dev", role = "ADMIN")
    @Test
    void registerBoard() throws Exception {
        // given
        PostBoardDTO postBoardDTO = new PostBoardDTO();
        postBoardDTO.setTitle("공지사항 제목");
        postBoardDTO.setContent("공지사항 내용");
        postBoardDTO.setFixed(false);
        postBoardDTO.setSubCategory(2L);
        postBoardDTO.setImages(null);

        // when & then
        mockMvc.perform(post("/api/faq-board")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(postBoardDTO)))
                .andExpect(status().isCreated());

    }

    @DisplayName("faq 수정 - 관리자")
    @WithMockCustomUser(id = 1L, username = "admin@darye.dev", role = "ADMIN")
    @Test
    void updateBoard() throws Exception {
        // given
        UpdateBoardDTO updateBoardDTO = new UpdateBoardDTO();
        updateBoardDTO.setTitle("수정된 공지사항 제목");
        updateBoardDTO.setContent("수정된 공지사항 내용");

        // when & then
        mockMvc.perform(patch("/api/faq-board/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(updateBoardDTO)))
                .andExpect(status().isNoContent());

    }

    @DisplayName("faq 삭제 - 관리자")
    @WithMockCustomUser(id = 1L, username = "admin@darye.dev", role = "ADMIN")
    @Test
    void softDeleteBoard() throws Exception {
        // when & then
        mockMvc.perform(delete("/api/faq-board/1"))
                .andExpect(status().isNoContent());

    }

    @DisplayName("faq - 일반 사용자")
    @WithMockCustomUser(id = 1L, username = "test@darye.dev", role = "USER")
    @Test
    void registerBoardWithUser() throws Exception {
        // given
        PostBoardDTO postBoardDTO = new PostBoardDTO();
        postBoardDTO.setTitle("공지사항 제목");
        postBoardDTO.setContent("공지사항 내용");
        postBoardDTO.setFixed(false);
        postBoardDTO.setSubCategory(2L);
        postBoardDTO.setImages(null);

        // when & then
        mockMvc.perform(post("/api/faq-board")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(postBoardDTO)))
                .andExpect(status().isForbidden());

    }

    @DisplayName("faq 수정 - 일반 사용자")
    @WithMockCustomUser(id = 1L, username = "test@darye.dev", role = "USER")
    @Test
    void updateBoardWithUser() throws Exception {
        // given
        UpdateBoardDTO updateBoardDTO = new UpdateBoardDTO();
        updateBoardDTO.setTitle("수정된 공지사항 제목");
        updateBoardDTO.setContent("수정된 공지사항 내용");

        // when & then
        mockMvc.perform(patch("/api/faq-board/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(updateBoardDTO)))
                .andExpect(status().isForbidden());

    }

    @DisplayName("faq 삭제 - 일반 사용자")
    @WithMockCustomUser(id = 1L, username = "test@darye.dev", role = "USER")
    @Test
    void softDeleteBoardWithUser() throws Exception {
        // when & then
        mockMvc.perform(delete("/api/faq-board/1"))
                .andExpect(status().isForbidden());

    }

    @DisplayName("faq 조회 - 서브카테고리지정")
    @WithMockCustomUser(id = 1L, username = "test@darye.dev", role = "USER")
    @Test
    void selectAllBoard() throws Exception {
        // when & then
        mockMvc.perform(get("/api/faq-board?subCategoryId=1"))
                .andExpect(status().isOk());

    }

    @DisplayName("faq 조회 - 서브카테고리 미지정")
    @WithMockCustomUser(id = 1L, username = "test@darye.dev", role = "USER")
    @Test
    void selectAllBoardWithoutSubCategory() throws Exception {
        // when & then
        mockMvc.perform(get("/api/faq-board"))
                .andExpect(status().isOk());

    }

    @DisplayName("faq 조회 - 비로그인")
    @WithAnonymousUser
    @Test
    void selectAllBoardWithoutLogin() throws Exception {
        // when & then
        mockMvc.perform(get("/api/faq-board"))
                .andExpect(status().isOk());

    }

    @DisplayName("faq 상세 조회")
    @WithAnonymousUser
    @Test
    void selectBoard() throws Exception {
        // when & then
        mockMvc.perform(get("/api/faq-board/1"))
                .andExpect(status().isOk());

    }

}