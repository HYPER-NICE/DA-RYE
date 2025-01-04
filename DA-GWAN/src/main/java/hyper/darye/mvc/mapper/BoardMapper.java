package hyper.darye.mvc.mapper;

import hyper.darye.mvc.model.entity.Board;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface BoardMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Board board);

    // insertSelective 메서드는 null인 필드를 제외한 나머지 필드만 insert하는 메서드
    int insertSelective(Board board);

    Board selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Board record);

    int updateByPrimaryKey(Board record);


    //카테고리ID로 조회
    @Select("SELECT * FROM BOARD WHERE CATEGORY_ID = #{categoryId} ORDER BY CREATED_DATE DESC")
    List<Board> selectAll(Long categoryId);

    //카테고리ID로 전체 카테고리 조회
    @Select("<script>" +
            "SELECT * FROM BOARD WHERE CATEGORY_ID IN " +
            "<foreach item='id' collection='categoryId' open='(' close=')' separator=','> " +
            "#{id} " +
            "</foreach>" +
            "ORDER BY CATEGORY_ID ASC, CREATED_DATE DESC" +
            "</script>")
    List<Board> selectAllCategory(List<Long> categoryId);

    //게시글 ID로 루트카테고리ID 조회
    @Select("SELECT bcc.ROOT_CATEGORY FROM BOARD b JOIN BOARD_CATEGORY_CODE bcc ON b.CATEGORY_ID = bcc.ID WHERE b.ID = #{id}")
    Long selectRootCategoryId(Long id);

    //게시글 ID로 서브카테고리ID 조회
    @Select("SELECT bcc.SUB_CATEGORY FROM BOARD b JOIN BOARD_CATEGORY_CODE bcc ON b.CATEGORY_ID = bcc.ID WHERE b.ID = #{id}")
    Long selectSubCategoryId(Long id);

    //게시글 ID로 서브 카테고리 이름 조회
    @Select("SELECT bcc.SUB_NAME FROM BOARD b JOIN BOARD_CATEGORY_CODE bcc ON b.CATEGORY_ID = bcc.ID WHERE b.ID = #{id}")
    String selectSubCategoryName(Long id);


    //게시글 ID로 작성자ID 조회
    @Select("SELECT WRITER_ID FROM BOARD WHERE ID = #{id}")
    Long selectWriterId(Long id);

    //게시글 ID로 게시글 조회
    @Select("SELECT * FROM BOARD WHERE ID = #{id} AND DELETED_DATE IS NULL")
    Board selectBoard(Long id);

    //작성자ID로 게시글 조회
    @Select("SELECT * FROM BOARD WHERE WRITER_ID = #{writerId} AND DELETED_DATE IS NULL ORDER BY CREATED_DATE DESC")
    List<Board> selectByWriterId(Long writerId);

    //작성자ID로 카테고리별 게시글 조회
    @Select("SELECT * FROM BOARD WHERE WRITER_ID = #{writerId} AND CATEGORY_ID = #{categoryId} AND DELETED_DATE IS NULL ORDER BY CREATED_DATE DESC")
    List<Board> selectByWriterIdAndCategoryId(Long writerID, Long categoryId);

    //게시글 소프트 삭제
    int softDeleteByPrimaryKey(Long id, Long lastModifiedMember);

    //게시글ID로 게시글 상태 업데이트(카테고리 변화)
    @Update("UPDATE BOARD SET CATEGORY_ID = #{categoryId} WHERE ID = #{id}")
    int updateCategory(Long id, Long categoryId);

    //부모게시글ID로 답글 조회
    @Select("SELECT * FROM BOARD WHERE PARENT_ID = #{parentId} AND DELETED_DATE IS NULL ORDER BY CREATED_DATE DESC")
    List<Board> selectByParentId(Long parentId);

//    //부모게시글ID로 답글ID 조회
//    @Select("SELECT ID FROM BOARD WHERE PARENT_ID = #{parentId} AND DELETED_DATE IS NULL ORDER BY CREATED_DATE DESC")
//    List<Long> selectIdByParentId(Long parentId);
}