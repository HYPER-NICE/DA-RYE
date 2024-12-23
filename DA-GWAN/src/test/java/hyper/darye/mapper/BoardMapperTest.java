package hyper.darye.mapper;

import hyper.darye.dto.Board;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@MybatisTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Transactional
class BoardMapperTest {

    @Autowired
    private BoardMapper boardMapper;

    @Autowired
    private BoardCategoryCodeMapper boardCategoryCodeMapper;

    @Test
    @DisplayName("게시판 insert 테스트")
    void insertBoardNoticeTest() {
        //given
        Board board = new Board();
        board.setCategoryId(1L);
        board.setTitle("테스트 제목1");
        board.setContent("테스트 내용입니다.");
        board.setWriterId(5L);
        board.setFixed(true);

        //when
        int result = boardMapper.insert(board);

        //then
        assertThat(result).isEqualTo(1);

        Board insertedBoard = boardMapper.selectByPrimaryKey(board.getId());
        assertThat(insertedBoard).isNotNull();
        assertThat(insertedBoard.getTitle()).isEqualTo("테스트 제목1");

    }
}