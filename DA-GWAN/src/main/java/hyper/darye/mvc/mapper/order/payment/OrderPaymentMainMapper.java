package hyper.darye.mvc.mapper.order.payment;

import hyper.darye.mvc.model.entity.OrderPaymentMain;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OrderPaymentMainMapper {
    int deleteByPrimaryKey(Long id);

    int insert(OrderPaymentMain record);

    int insertSelective(OrderPaymentMain record);

    OrderPaymentMain selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(OrderPaymentMain record);

    int updateByPrimaryKey(OrderPaymentMain record);
}