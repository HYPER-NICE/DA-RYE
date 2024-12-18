package hyper.darye.mapper;

import hyper.darye.dto.OrderPaymentMain;
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