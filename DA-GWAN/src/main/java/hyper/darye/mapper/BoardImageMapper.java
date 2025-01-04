package hyper.darye.mapper;

import hyper.darye.model.entity.BoardImage;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface BoardImageMapper {
    int deleteByPrimaryKey(Long id);

    int insert(BoardImage record);

    int insertSelective(BoardImage record);

    BoardImage selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(BoardImage record);

    int updateByPrimaryKeyWithBLOBs(BoardImage record);

    int updateByPrimaryKey(BoardImage record);

    // 게시글 ID로 이미지 삭제
    int deleteByBoardId(Long boardId);

    // 게시글 ID로 이미지 조회
    @Select("SELECT * FROM BOARD_IMAGE WHERE BOARD_ID = #{boardId}")
    List<BoardImage> selectByBoardId(Long boardId);
}