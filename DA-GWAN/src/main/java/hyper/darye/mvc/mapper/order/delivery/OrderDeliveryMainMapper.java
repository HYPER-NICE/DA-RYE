package hyper.darye.mvc.mapper.order.delivery;

import hyper.darye.mvc.model.entity.OrderDeliveryMain;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OrderDeliveryMainMapper {
    int deleteByPrimaryKey(Long id);

    int insert(OrderDeliveryMain record);

    int insertSelective(OrderDeliveryMain record);

    OrderDeliveryMain selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(OrderDeliveryMain record);

    int updateByPrimaryKey(OrderDeliveryMain record);
}