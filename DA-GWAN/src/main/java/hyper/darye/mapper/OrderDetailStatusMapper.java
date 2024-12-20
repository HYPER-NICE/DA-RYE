package hyper.darye.mapper;

import hyper.darye.dto.OrderDetailStatus;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OrderDetailStatusMapper {
    int deleteByPrimaryKey(Long id);

    int insert(OrderDetailStatus record);

    int insertSelective(OrderDetailStatus record);

    OrderDetailStatus selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(OrderDetailStatus record);

    int updateByPrimaryKey(OrderDetailStatus record);
}