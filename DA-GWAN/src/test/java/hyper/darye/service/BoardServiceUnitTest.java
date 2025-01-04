package hyper.darye.service;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import hyper.darye.model.entity.Board;
import hyper.darye.model.entity.BoardImage;
import hyper.darye.model.dto.controller.request.PostBoardDTO;
import hyper.darye.model.dto.controller.request.PostReplyDTO;
import hyper.darye.model.dto.controller.request.UpdateBoardDTO;
import hyper.darye.model.dto.controller.request.UpdateReplyDTO;
import hyper.darye.model.dto.controller.response.SearchBoardDTO;
import hyper.darye.model.dto.controller.response.SearchBoardDetailDTO;
import hyper.darye.model.dto.controller.response.SearchReplyDTO;
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

    @Test
    @DisplayName("게시글 조회 테스트 - 전체 카테고리")
    public void selectAllBoardTest() {
        // Given
        Long rootCategoryId = 1L;
        Long subCategoryId = null;

        List<Long> categoryIds = List.of(1L, 2L);
        List<Board> boards = List.of(
                new Board(1L, null, 1L, 1L, "제목1", "내용1", false, new Date(), new Date(), new Date(), null, null),
                new Board(2L, null, 2L, 1L, "제목2", "내용2", false, new Date(), new Date(), new Date(), null, null)
        );

        when(boardCategoryCodeMapper.selectAllCategoryCodeId(rootCategoryId)).thenReturn(categoryIds);
        when(boardMapper.selectAllCategory(categoryIds)).thenReturn(boards);

        // When
        List<SearchBoardDTO> result = boardService.selectAllBoard(rootCategoryId, subCategoryId);

        // Then
        verify(boardCategoryCodeMapper, times(1)).selectAllCategoryCodeId(rootCategoryId);
        verify(boardMapper, times(1)).selectAllCategory(categoryIds);
        assertThat(result).hasSize(2);
        assertThat(result).extracting("title").contains("제목1", "제목2");
    }

    @Test
    @DisplayName("게시글 조회 테스트 - 서브 카테고리")
    public void selectAllBoardTest_withSubCategory() {
        // Given
        Long rootCategoryId = 1L;
        Long subCategoryId = 2L;
        List<Board> boards = List.of(
                new Board(2L, null, 2L, 1L, "제목2", "내용2", false, new Date(), new Date(), new Date(), null, null)
        );

        // When
        Map<String, Object> param = Map.of("rootCategory", rootCategoryId, "subCategory", subCategoryId);
        Long categoryId = boardCategoryCodeMapper.selectCategoryCodeId(param);

        when(boardCategoryCodeMapper.selectCategoryCodeId(param)).thenReturn(categoryId);
        when(boardMapper.selectAll(categoryId)).thenReturn(boards);
        when(boardMapper.selectSubCategoryName(anyLong())).thenReturn("일반");
        List<SearchBoardDTO> result = boardService.selectAllBoard(rootCategoryId, subCategoryId);

        // Then
        assertThat(result).hasSize(1);
        assertThat(result).extracting("title").contains("제목2");

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("제목2", result.get(0).getTitle());
        assertEquals("일반", result.get(0).getSubCategoryName());
        assertEquals(subCategoryId, result.get(0).getSubCategoryId());
    }

    @Test
    @DisplayName("게시글 상세 조회 테스트")
    public void selectBoardDetailTest() {
        // Given
        Long boardId = 1L;
        Board board = new Board(1L, null, 1L, 1L, "제목1", "내용1", false, new Date(), new Date(), new Date(), null, null);
        BoardImage boardImage = new BoardImage(1L, 1L, new Date(), new Date(), null, new byte[]{1, 2, 3});
        BoardImage boardImage2 = new BoardImage(2L, 1L, new Date(), new Date(), null, new byte[]{1, 2});

        when(boardMapper.selectBoard(boardId)).thenReturn(board);
        when(boardImageMapper.selectByBoardId(boardId)).thenReturn(List.of(boardImage, boardImage2));

        // When
        SearchBoardDetailDTO result = boardService.selectBoardDetail(boardId);

        // Then
        verify(boardMapper, times(1)).selectBoard(boardId);
        assertNotNull(result);
        assertEquals(boardId, result.getId());
        assertEquals("제목1", result.getTitle());
        assertEquals("내용1", result.getContent());
        assertThat(result.getImages()).hasSize(2);

        System.out.println(result.getRegDate());
    }

    @Test
    @DisplayName("1대1 문의 조회 - 작성자 일때")
    public void selectOneOnOneBoard() {
        // Given
        Long memberId = 1L;
        List<Board> boards = List.of(
                new Board(1L, null, 8L, 1L, "제목1", "내용1", false, new Date(), new Date(), new Date(), null, null),
                new Board(2L, null, 9L, 1L, "제목2", "내용2", false, new Date(), new Date(), new Date(), null, null)
        );

            when(boardMapper.selectByWriterId(memberId)).thenReturn(boards);

        // When
        List<SearchBoardDTO> result = boardService.selectOneBoard(3L, null, memberId);

        // Then
        assertThat(result).hasSize(2);
        assertThat(result).extracting("title").contains("제목1", "제목2");
    }

    @Test
    @DisplayName("1대1 문의 조회 - 작성자가 아닐때")
    public void selectOneOnOneBoard_NotWriter() {
        // Given
        Long memberId = 2L;
        List<Board> boards = List.of(
                new Board(1L, null, 8L, 1L, "제목1", "내용1", false, new Date(), new Date(), new Date(), null, null),
                new Board(2L, null, 9L, 1L, "제목2", "내용2", false, new Date(), new Date(), new Date(), null, null)
        );

        List<Board> boards2 = List.of(
                new Board(3L, null, 8L, 2L, "제목3", "내용3", false, new Date(), new Date(), new Date(), null, null)
        );

        when(boardMapper.selectByWriterId(memberId)).thenReturn(boards2);

        // When
        List<SearchBoardDTO> result = boardService.selectOneBoard(3L, null, memberId);

        // Then
        assertThat(result).hasSize(1);
        assertThat(result).extracting("title").contains("제목3");
    }

    @Test
    @DisplayName("1대1 문의 전체 조회 - 없을때")
    public void selectOneOnOneBoard_NotExist() {
        // Given
        Long memberId = 1L;
        List<Board> boards = new ArrayList<>();

        when(boardMapper.selectByWriterId(memberId)).thenReturn(boards);

        // When
        List<SearchBoardDTO> result = boardService.selectOneBoard(3L, null, memberId);

        // Then
        assertThat(result).isEmpty();
    }

    @Test
    @DisplayName("1대1 문의 상세 조회 - 작성자일때")
    public void selectOneOnOneBoardDetail() {
        // Given
        Long boardId = 1L;
        Long memberId = 1L;

        Board board = new Board(1L, null, 8L, 1L, "제목1", "내용1", false, new Date(), new Date(), new Date(), null, null);
        BoardImage boardImage = new BoardImage(1L, 1L, new Date(), new Date(), null, new byte[]{1, 2, 3});
        BoardImage boardImage2 = new BoardImage(2L, 1L, new Date(), new Date(), null, new byte[]{1, 2});

        when(boardMapper.selectBoard(boardId)).thenReturn(board);
        when(boardImageMapper.selectByBoardId(boardId)).thenReturn(List.of(boardImage, boardImage2));

        // When
        SearchBoardDetailDTO result = boardService.selectBoardDetail(boardId);

        // Then
        assertNotNull(result);
        assertEquals(boardId, result.getId());
        assertEquals("제목1", result.getTitle());
        assertEquals("내용1", result.getContent());
        assertThat(result.getImages()).hasSize(2);
    }

    //1대1 문의 댓글 작성
    @Test
    @DisplayName("1대1 문의 댓글 작성 성공")
    public void insertReplyTest() {
        // Given
        PostReplyDTO postReplyDTO = new PostReplyDTO("내용", 1L, null);
        when(boardMapper.insertSelective(any(Board.class))).thenReturn(1);
        Board expectedBoard2 = new Board();
        expectedBoard2.setId(2L);
        expectedBoard2.setTitle("RE: ");
        expectedBoard2.setContent(postReplyDTO.getContent());
        expectedBoard2.setCategoryId(9L);
        expectedBoard2.setParentId(postReplyDTO.getParentId());

        // When
        int result = boardService.insertReply(1L, postReplyDTO, 1L);

        // Then
        assertEquals(1, result);
        verify(boardMapper, times(1)).insertSelective(any(Board.class));
        verify(boardImageMapper, times(0)).insertSelective(any(BoardImage.class));  // 이미지가 없을 경우 호출되지 않음
        assertEquals(1L, expectedBoard.getId());
        assertThat(expectedBoard2.getTitle()).isEqualTo("RE: ");
        assertThat(expectedBoard2.getContent()).isEqualTo("내용");
        assertThat(expectedBoard2.getCategoryId()).isEqualTo(9L);
        assertThat(expectedBoard2.getParentId()).isEqualTo(1L);
    }


    //1대1 문의 댓글 수정
    @Test
    @DisplayName("1대1 문의 댓글 수정 성공")
    public void updateReplyTest() {
        // Given
        Long replyId = 3L;
        UpdateReplyDTO updateReplyDTO = new UpdateReplyDTO("수정된 내용", null, null);

        Board board = new Board();
        board.setId(3L);
        board.setTitle("RE: ");
        board.setContent("수정된 내용");
        board.setCategoryId(9L);
        board.setParentId(1L);
        board.setWriterId(1L);

        when(boardMapper.selectByPrimaryKey(replyId)).thenReturn(board);
        when(boardMapper.updateByPrimaryKeySelective(any(Board.class))).thenReturn(1);

        // When
        boardService.updateReply(3L, updateReplyDTO, 1L);

        // Then
        verify(boardMapper, times(1)).updateByPrimaryKeySelective(any(Board.class));
    }

    //1대1 문의 댓글 조회
    @Test
    @DisplayName("1대1 문의 댓글 조회")
    public void selectAllReplyTest() {

        Board board1 = new Board();
        board1.setId(2L);
        board1.setContent("댓글 1");
        board1.setRegDate(new Date());
        board1.setParentId(1L);

        Board board2 = new Board();
        board2.setId(3L);
        board2.setContent("댓글 2");
        board2.setRegDate(new Date());
        board2.setParentId(1L);

        List<Board> replays = Arrays.asList(board1, board2);

        // 댓글에 대한 이미지 리스트
        BoardImage image1 = new BoardImage();
        image1.setBoardId(2L);
        image1.setImage(new byte[]{1, 2, 3});

        BoardImage image2 = new BoardImage();
        image2.setBoardId(3L);
        image2.setImage(new byte[]{4, 5, 6});

        List<BoardImage> images = Arrays.asList(image1, image2);

        when(boardMapper.selectByParentId(1L)).thenReturn(replays);
        when(boardImageMapper.selectByBoardId(2L)).thenReturn(Collections.singletonList(image1));
        when(boardImageMapper.selectByBoardId(3L)).thenReturn(Collections.singletonList(image2));

        // When
        List<SearchReplyDTO> result = boardService.selectAllReply(1L);

        // Then
        assertNotNull(result);
        assertEquals(2, result.size());

        SearchReplyDTO dto1 = result.get(0);
        assertEquals(2L, dto1.getId());
        assertEquals("댓글 1", dto1.getContent());
        assertEquals(1, dto1.getImages().size());

        SearchReplyDTO dto2 = result.get(1);
        assertEquals(3L, dto2.getId());
        assertEquals("댓글 2", dto2.getContent());
        assertEquals(1, dto2.getImages().size());

        verify(boardMapper, times(1)).selectByParentId(1L);
        verify(boardImageMapper, times(1)).selectByBoardId(2L);
        verify(boardImageMapper, times(1)).selectByBoardId(3L);
    }

    @Test
    @DisplayName("1대1 문의 댓글 조회 - 댓글이 없을때")
    void testSelectAllReplyWhenNoRepliesFound() {
        //given
        when(boardMapper.selectByParentId(1L)).thenReturn(Collections.emptyList());

        //when
        List<SearchReplyDTO> result = boardService.selectAllReply(1L);

        //then
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }
}




