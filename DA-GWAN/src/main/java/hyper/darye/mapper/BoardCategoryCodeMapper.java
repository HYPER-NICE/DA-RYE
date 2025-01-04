package hyper.darye.mapper;

import hyper.darye.model.entity.BoardCategoryCode;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

@Mapper
public interface BoardCategoryCodeMapper {

    //카테고리 ID로 카테고리 코드 조회
    Long selectCategoryCodeId(Map<String, Object> param);

    //루트 카테고리 ID로 카테고리 전체 조회
    @Select("SELECT ID FROM BOARD_CATEGORY_CODE WHERE ROOT_CATEGORY = #{rootCategory}")
    List<Long> selectAllCategoryCodeId(Long rootCategory);

    int deleteByPrimaryKey(Long id);

    int insert(BoardCategoryCode record);

    int insertSelective(BoardCategoryCode record);

    BoardCategoryCode selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(BoardCategoryCode record);

    int updateByPrimaryKey(BoardCategoryCode record);
}