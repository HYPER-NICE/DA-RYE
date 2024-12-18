package hyper.darye.mapper;

import hyper.darye.dto.OrderDeliveryMain;
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