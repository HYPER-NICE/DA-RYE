package hyper.darye.mapper;

import hyper.darye.dto.BoardCategoryCode;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface BoardCategoryCodeMapper {
    int deleteByPrimaryKey(Long id);

    int insert(BoardCategoryCode record);

    int insertSelective(BoardCategoryCode record);

    BoardCategoryCode selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(BoardCategoryCode record);

    int updateByPrimaryKey(BoardCategoryCode record);
}