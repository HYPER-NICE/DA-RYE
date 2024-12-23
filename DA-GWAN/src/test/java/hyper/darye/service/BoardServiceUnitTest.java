package hyper.darye.service;

import hyper.darye.dto.Board;
import hyper.darye.dto.controller.request.PostBoardDTO;
import hyper.darye.mapper.BoardCategoryCodeMapper;
import hyper.darye.mapper.BoardMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyMap;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BoardServiceUnitTest {

    @Mock
    private BoardMapper boardMapper;

    @Mock
    private BoardCategoryCodeMapper boardCategoryCodeMapper;

    @InjectMocks
    private BoardServiceImpl boardService;

    private PostBoardDTO BoardRequest;
    private Board expectedBoard;

    @BeforeEach
    void setUp() {
        BoardRequest = createBoardRequest();
        expectedBoard = createExpectedBoard();
    }

    private PostBoardDTO createBoardRequest() {
        PostBoardDTO BoardRequest = new PostBoardDTO("제목", "내용", false, 2L, 1L, 1L);
        return BoardRequest;
    }

    private Board createExpectedBoard() {
        Board board = new Board();
        board.setCategoryId(2L);
        board.setTitle(BoardRequest.getTitle());
        board.setContent(BoardRequest.getContent());
        board.setFixed(BoardRequest.getFixed());
        board.setRegDate(new Date());
        board.setCreatedDate(new Date());
        return board;
    }

    @Nested
    @DisplayName("공지사항 등록 테스트")
    class InsertSelectiveBoardTest {

        @Test
        @DisplayName("공지사항 등록 성공")
        void insertBoard() {
            // given
            when(boardCategoryCodeMapper.selectCategoryCodeId(anyMap())).thenReturn(2L);
            when(boardMapper.insertSelective(any(Board.class))).thenReturn(1);

            // when
            int result = boardService.insertBoard(BoardRequest);

            // then
            assertEquals(1, result);
        }

        @Test
        @DisplayName("존재하지 않는 카테고리 코드")
        void insertBoardWithInvalidCategoryCode() {
            // given
            when(boardCategoryCodeMapper.selectCategoryCodeId(anyMap())).thenReturn(null);

            // when, then
            assertThrows(IllegalArgumentException.class, () -> boardService.insertBoard(BoardRequest));
        }

        @Test
        @DisplayName("공지사항 등록 실패")
        void insertBoardFail() {
            // given
            when(boardCategoryCodeMapper.selectCategoryCodeId(anyMap())).thenReturn(2L);
            when(boardMapper.insertSelective(any(Board.class))).thenReturn(0);

            // when
            int result = boardService.insertBoard(BoardRequest);

            // then
            assertEquals(0, result);
        }
    }
}