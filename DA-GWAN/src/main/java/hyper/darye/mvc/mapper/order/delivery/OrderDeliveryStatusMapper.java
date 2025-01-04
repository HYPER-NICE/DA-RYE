package hyper.darye.mvc.mapper.order.delivery;

import hyper.darye.mvc.model.entity.OrderDeliveryStatus;
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