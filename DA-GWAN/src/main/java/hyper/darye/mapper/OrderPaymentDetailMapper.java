package hyper.darye.mapper;

import hyper.darye.dto.OrderPaymentDetail;
import hyper.darye.dto.OrderPaymentDetailKey;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OrderPaymentDetailMapper {
    int deleteByPrimaryKey(OrderPaymentDetailKey key);

    int insert(OrderPaymentDetail record);

    int insertSelective(OrderPaymentDetail record);

    OrderPaymentDetail selectByPrimaryKey(OrderPaymentDetailKey key);

    int updateByPrimaryKeySelective(OrderPaymentDetail record);

    int updateByPrimaryKey(OrderPaymentDetail record);
}