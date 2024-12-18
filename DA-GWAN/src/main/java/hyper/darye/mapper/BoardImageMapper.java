package hyper.darye.mapper;

import hyper.darye.dto.BoardImage;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface BoardImageMapper {
    int deleteByPrimaryKey(Long id);

    int insert(BoardImage record);

    int insertSelective(BoardImage record);

    BoardImage selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(BoardImage record);

    int updateByPrimaryKeyWithBLOBs(BoardImage record);

    int updateByPrimaryKey(BoardImage record);
}