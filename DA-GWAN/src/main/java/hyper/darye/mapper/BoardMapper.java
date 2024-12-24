package hyper.darye.mapper;

import hyper.darye.dto.Board;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface BoardMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Board board);

    // insertSelective 메서드는 null인 필드를 제외한 나머지 필드만 insert하는 메서드
    int insertSelective(Board board);

    Board selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Board record);

    int updateByPrimaryKey(Board record);


    //전체 조회
    @Select("SELECT * FROM BOARD WHERE CATEGORY_ID = #{CategoryId}")
    Board selectAll(Long CategoryId);

    //게시글 ID로 루트카테고리ID 조회
    @Select("SELECT bcc.ROOT_CATEGORY FROM BOARD b JOIN BOARD_CATEGORY_CODE bcc ON b.CATEGORY_ID = bcc.ID WHERE b.ID = #{id}")
    Long selectRootCategoryId(Long id);

    //게시글 ID로 작성자ID 조회
    @Select("SELECT WRITER_ID FROM BOARD WHERE ID = #{id}")
    Long selectWriterId(Long id);

    //게시글 소프트 삭제
    int softDeleteByPrimaryKey(Long id, Long memberId);

}