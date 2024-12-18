package hyper.darye.mapper;

import hyper.darye.dto.OrderStatusCode;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OrderStatusCodeMapper {
    int deleteByPrimaryKey(Long id);

    int insert(OrderStatusCode record);

    int insertSelective(OrderStatusCode record);

    OrderStatusCode selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(OrderStatusCode record);

    int updateByPrimaryKey(OrderStatusCode record);
}