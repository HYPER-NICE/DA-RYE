package hyper.darye.mapper;

import hyper.darye.dto.PointHistory;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PointHistoryMapper {
    int deleteByPrimaryKey(Long id);

    int insert(PointHistory record);

    int insertSelective(PointHistory record);

    PointHistory selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(PointHistory record);

    int updateByPrimaryKey(PointHistory record);
}