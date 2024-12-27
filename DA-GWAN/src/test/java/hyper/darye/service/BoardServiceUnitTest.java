package hyper.darye.service;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import hyper.darye.dto.Board;
import hyper.darye.dto.BoardImage;
import hyper.darye.dto.controller.request.PostBoardDTO;
import hyper.darye.dto.controller.request.UpdateBoardDTO;
import hyper.darye.mapper.BoardCategoryCodeMapper;
import hyper.darye.mapper.BoardImageMapper;
import hyper.darye.mapper.BoardMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.*;

@SpringBootTest(classes = BoardServiceImpl.class)
class BoardServiceUnitTest {

    @MockBean
    private BoardCategoryCodeMapper boardCategoryCodeMapper;

    @MockBean
    private BoardMapper boardMapper;

    @MockBean
    private BoardImageMapper boardImageMapper;

    @MockBean
    private ObjectMapper objectMapper;

    @Autowired
    private BoardServiceImpl boardService;

    private PostBoardDTO postBoardDTO;
    private Board expectedBoard;
    private Board board;
    private UpdateBoardDTO updateBoardDTO;


    @BeforeEach
    public void setUp() {
        postBoardDTO = createPostBoardDTO();
        expectedBoard = createBoard();
    }

    private PostBoardDTO createPostBoardDTO() {
        return new PostBoardDTO("테스트 제목입니다.", "테스트 내용입니다.", false, 1L, 2L, null);
    }

    private Board createBoard() {
        Board board = new Board();
        board.setId(1L);
        board.setTitle(postBoardDTO.getTitle());
        board.setContent(postBoardDTO.getContent());
        board.setFixed(false);
        board.setCategoryId(3L);
        board.setWriterId(1L);
        board.setCreatedDate(new Date());
        return board;
    }

    @Test
    @DisplayName("게시글 작성 성공")
    public void insertBoardTest() {
        // Given
        when(boardCategoryCodeMapper.selectCategoryCodeId(anyMap())).thenReturn(3L);
        when(boardMapper.insertSelective(any(Board.class))).thenReturn(1);

        // When
        int result = boardService.insertBoard(postBoardDTO, 1L);

        // Then
        assertEquals(1, result);
        verify(boardCategoryCodeMapper, times(1)).selectCategoryCodeId(anyMap());
        verify(boardMapper, times(1)).insertSelective(any(Board.class));
        verify(boardImageMapper, times(0)).insertSelective(any(BoardImage.class));  // 이미지가 없을 경우 호출되지 않음
        assertEquals(1L, expectedBoard.getId());
        assertThat(expectedBoard.getTitle()).isEqualTo("테스트 제목입니다.");
        assertThat(expectedBoard.getContent()).isEqualTo("테스트 내용입니다.");
        assertThat(expectedBoard.getCategoryId()).isEqualTo(3L);
    }

    @Test
    @DisplayName("이미지 포함된 게시글 작성 성공")
    public void insertBoardWithImagesTest() {
        // Given
        byte[] image = new byte[] { 1, 2, 3 }; // 테스트 이미지
        postBoardDTO.setImages(Collections.singletonList(image));

        when(boardCategoryCodeMapper.selectCategoryCodeId(anyMap())).thenReturn(2L);
        when(boardMapper.insertSelective(any(Board.class))).thenReturn(1);
        when(objectMapper.convertValue(any(PostBoardDTO.class), eq(Board.class))).thenReturn(expectedBoard);  // objectMapper mock

        // When
        int result = boardService.insertBoard(postBoardDTO, 1L);

        // Then
        assertEquals(1, result);
        verify(boardCategoryCodeMapper, times(1)).selectCategoryCodeId(anyMap());
        verify(boardMapper, times(1)).insertSelective(any(Board.class));
        verify(boardImageMapper, times(1)).insertSelective(any(BoardImage.class));  // 이미지가 있을 경우 호출됨
    }

    @Test
    @DisplayName("존재하지 않는 카테고리로 게시글 작성 시 예외 발생")
    public void insertBoardWithInvalidCategoryTest() {
        // Given
        when(boardCategoryCodeMapper.selectCategoryCodeId(anyMap())).thenReturn(null); // 유효하지 않은 카테고리 ID

        // When & Then
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
            boardService.insertBoard(postBoardDTO, 1L);
        });
        assertEquals("존재하지 않는 카테고리입니다.", thrown.getMessage());
    }

    @Test
    @DisplayName("게시글 수정 테스트")
    public void updateBoardTest() {
        // Given
        Long boardId = 1L;
        Long memberId = 1L;

        updateBoardDTO = new UpdateBoardDTO();
        updateBoardDTO.setTitle("수정된 제목");
        updateBoardDTO.setContent("수정된 내용");
        updateBoardDTO.setSubCategory(2L);
        updateBoardDTO.setImages(List.of(new byte[]{1, 2, 3}));
        updateBoardDTO.setDeleteImages(new Long[]{1L});

        board = new Board();
        board.setId(1L);
        board.setTitle("수정된 제목");
        board.setContent("수정된 내용");
        board.setCategoryId(4L);
        board.setWriterId(1L);
        board.setCreatedDate(new Date());


        when(boardMapper.selectRootCategoryId(anyLong())).thenReturn(2L); // 가상의 rootCategoryId
        when(boardCategoryCodeMapper.selectCategoryCodeId(anyMap())).thenReturn(4L); // 카테고리 코드
        when(objectMapper.convertValue(any(), eq(Board.class))).thenReturn(board);
        when(boardMapper.updateByPrimaryKeySelective(any(Board.class))).thenReturn(1); // 업데이트 성공

        // When
        boardService.updateBoard(boardId, updateBoardDTO, memberId);

        // Then
        verify(boardMapper, times(1)).selectRootCategoryId(boardId);
        verify(boardCategoryCodeMapper, times(1)).selectCategoryCodeId(anyMap());
        verify(boardMapper, times(1)).updateByPrimaryKeySelective(any(Board.class));
        verify(boardImageMapper, times(1)).insertSelective(any(BoardImage.class));
        verify(boardImageMapper, times(1)).deleteByPrimaryKey(1L);
    }


    @Test
    @DisplayName("작성자가 맞는지 확인")
    public void isWriterTest() {
        // Given
        Long boardId = 1L;
        Long memberId = 2L;

        when(boardMapper.selectWriterId(boardId)).thenReturn(memberId);

        // When
        boolean result = boardService.isWriter(boardId, memberId);

        // Then
        assertTrue(result);
        verify(boardMapper, times(1)).selectWriterId(boardId);
    }

    @Test
    @DisplayName("작성자가 아닌 경우")
    public void isWriterTest_notWriter() {
        // Given
        Long boardId = 1L;
        Long memberId = 3L;

        when(boardMapper.selectWriterId(boardId)).thenReturn(1L);

        // When
        boolean result = boardService.isWriter(boardId, memberId);

        // Then
        assertFalse(result);
        verify(boardMapper, times(1)).selectWriterId(boardId);
    }


    @Test
    @DisplayName("답변된 게시글 수정 가능 여부 확인")
    public void isUpdatableTest() {
        // Given
        Long boardId = 1L;

        Board board = new Board();
        board.setCategoryId(9L);

        when(boardMapper.selectByPrimaryKey(boardId)).thenReturn(board);

        // When
        boolean result = boardService.isUpdatable(boardId);

        // Then
        assertTrue(result);
        verify(boardMapper, times(1)).selectByPrimaryKey(boardId);
    }

    @Test
    @DisplayName("답변 되지 않은 경우")
    public void isUpdatableTest_notUpdatable() {
        // Given
        Long boardId = 1L;

        Board boardWithCategoryOtherThan9 = new Board();
        boardWithCategoryOtherThan9.setCategoryId(8L);

        when(boardMapper.selectByPrimaryKey(boardId)).thenReturn(boardWithCategoryOtherThan9);

        // When
        boolean result = boardService.isUpdatable(boardId);

        // Then
        assertFalse(result);
        verify(boardMapper, times(1)).selectByPrimaryKey(boardId);
    }

    @Test
    @DisplayName("게시글 소프트 삭제 테스트")
    public void softDeleteBoardTest() {
        // Given
        Long boardId = 1L;
        Long memberId = 1L;

        when(boardMapper.softDeleteByPrimaryKey(boardId, memberId)).thenReturn(1);

        // When
        boardService.softDeleteBoard(boardId, memberId);

        // Then
        verify(boardMapper, times(1)).softDeleteByPrimaryKey(boardId, memberId);
        verify(boardImageMapper, times(1)).deleteByBoardId(boardId);
    }
}


