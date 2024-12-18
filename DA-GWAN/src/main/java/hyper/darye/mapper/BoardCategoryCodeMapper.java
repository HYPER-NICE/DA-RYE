package hyper.darye.mapper;

import hyper.darye.dto.BoardCategoryCode;
import org.apache.ibatis.annotations.Mapper;

import java.util.Map;

@Mapper
public interface BoardCategoryCodeMapper {

    //카테고리 이름으로 카테고리 코드 조회
    Long selectCategoryCodeId(Map<String, Object> param);

    int deleteByPrimaryKey(Long id);

    int insert(BoardCategoryCode record);

    int insertSelective(BoardCategoryCode record);

    BoardCategoryCode selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(BoardCategoryCode record);

    int updateByPrimaryKey(BoardCategoryCode record);
}