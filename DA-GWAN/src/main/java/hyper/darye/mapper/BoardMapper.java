package hyper.darye.mapper;

import hyper.darye.dto.Board;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface BoardMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Board record);

    int insertSelective(Board record);

    Board selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Board record);

    int updateByPrimaryKey(Board record);
}