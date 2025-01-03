package hyper.darye.mapper;

import hyper.darye.mvc.mapper.BoardCategoryCodeMapper;
import hyper.darye.mvc.mapper.BoardImageMapper;
import hyper.darye.mvc.mapper.BoardMapper;
import hyper.darye.mvc.model.entity.Board;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

@MybatisTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Transactional
class BoardMapperTest {

    @Autowired
    private BoardMapper boardMapper;

    @Autowired
    private BoardCategoryCodeMapper boardCategoryCodeMapper;

    @Autowired
    private BoardImageMapper boardImageMapper;

    @Test
    @DisplayName("게시판 insert 테스트")
    void insertSelectiveBoardTest() {
        //given
        Board board = new Board();
        board.setCategoryId(1L);
        board.setTitle("테스트 제목1");
        board.setContent("테스트 내용입니다.");
        board.setWriterId(1L);
        board.setFixed(true);

        //when
        int result = boardMapper.insert(board);

        //then
        assertThat(result).isEqualTo(1);

        Board insertedBoard = boardMapper.selectByPrimaryKey(board.getId());
        assertThat(insertedBoard).isNotNull();
        assertThat(insertedBoard.getTitle()).isEqualTo("테스트 제목1");

    }

    @Test
    @DisplayName("게시판 update 테스트")
    void updateBoardTest() {
        //given
        Board board = boardMapper.selectByPrimaryKey(1L);

        board.setTitle("수정된 제목입니다.");
        board.setContent("수정된 내용입니다.");

        //when
        int result = boardMapper.updateByPrimaryKeySelective(board);

        //then
        assertThat(result).isEqualTo(1);

        Board updatedBoard = boardMapper.selectByPrimaryKey(board.getId());
        assertThat(updatedBoard).isNotNull();
        assertThat(updatedBoard.getTitle()).isEqualTo("수정된 제목입니다.");
        assertThat(updatedBoard.getContent()).isEqualTo("수정된 내용입니다.");
        assertThat(updatedBoard.getCategoryId()).isEqualTo(1L);
    }

    @Test
    @DisplayName("게시판 softDelete 테스트")
    void softDeleteBoardTest() {
        //given
        Board board = boardMapper.selectByPrimaryKey(1L);

        //when
        boardMapper.softDeleteByPrimaryKey(1L, 5L);
        Board deletedBoard = boardMapper.selectByPrimaryKey(1L);

        //then
        assertThat(deletedBoard).isNotNull();
        assertThat(deletedBoard.getDeletedDate()).isNotNull();
        assertThat(deletedBoard.getLastModifiedMember()).isEqualTo(5L);
    }

    @Test
    @DisplayName("게시판 전체조회 테스트 - 카테고리당")
    void selectBoardTest() {
        //given & when
        List<Board> board = boardMapper.selectAllCategory(boardCategoryCodeMapper.selectAllCategoryCodeId(1L));

        //then
        assertThat(board).isNotNull();
        assertThat(board.size()).isEqualTo(2);
    }

    @Test
    @DisplayName("게시판 전체조회 테스트 - 서브 카테고리 지정")
    void selectBoardBySubCategoryTest() {
        //given & when
        List<Board> board = boardMapper.selectAll(1L);

        //then
        assertThat(board).isNotNull();
        assertThat(board.size()).isEqualTo(1);
    }

    @Test
    @DisplayName("게시판 상세 조회 테스트")
    void selectBoardDetailTest() {
        //given & when
        Board board = boardMapper.selectBoard(1L);

        //then
        assertThat(board).isNotNull();
        assertThat(board.getId()).isEqualTo(1L);
        assertThat(board.getTitle()).isEqualTo("배송 지연 안내입니다.");
    }

    @Test
    @DisplayName("댓글이 달리면 카테고리 변경 테스트")
    void updateBoardCategoryTest() {
        //given
        Long id = 1L;  // 테스트할 Board ID
        Long categoryId = 9L;  // 변경할 Category ID

        // 실행
        int result = boardMapper.updateCategory(id, categoryId);

        // 검증
        assertThat(result).isEqualTo(1);
        assertThat(boardMapper.selectByPrimaryKey(id).getCategoryId()).isEqualTo(categoryId);
    }
}