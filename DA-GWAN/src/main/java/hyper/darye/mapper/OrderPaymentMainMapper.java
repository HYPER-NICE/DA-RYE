package hyper.darye.mapper;

import hyper.darye.dto.OrderPaymentMain;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface OrderPaymentMainMapper {
    int deleteByPrimaryKey(Long id);

    int insert(OrderPaymentMain record);

    int insertSelective(OrderPaymentMain record);

    OrderPaymentMain selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(OrderPaymentMain record);

    int updateByPrimaryKey(OrderPaymentMain record);

    @Select("SELECT * FROM order_payment_main WHERE ORDER_ID = #{orderNo}")
    OrderPaymentMain selectByOrderNo(Long orderNo);
}