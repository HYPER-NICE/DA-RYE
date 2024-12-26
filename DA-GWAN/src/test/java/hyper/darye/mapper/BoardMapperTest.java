package hyper.darye.mapper;

import hyper.darye.dto.Board;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.transaction.annotation.Transactional;

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
}