package hyper.darye.mapper;

import hyper.darye.dto.Board;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface BoardMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Board board);

    // insertSelective 메서드는 null인 필드를 제외한 나머지 필드만 insert하는 메서드
    int insertSelective(Board board);

    Board selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Board record);

    int updateByPrimaryKey(Board record);
}