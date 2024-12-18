package hyper.darye.mapper;

import hyper.darye.dto.OrderDeliveryStatus;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OrderDeliveryStatusMapper {
    int deleteByPrimaryKey(Long id);

    int insert(OrderDeliveryStatus record);

    int insertSelective(OrderDeliveryStatus record);

    OrderDeliveryStatus selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(OrderDeliveryStatus record);

    int updateByPrimaryKey(OrderDeliveryStatus record);
}