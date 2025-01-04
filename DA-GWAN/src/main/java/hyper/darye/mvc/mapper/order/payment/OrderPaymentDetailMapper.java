package hyper.darye.mvc.mapper.order.payment;

import hyper.darye.mvc.model.entity.OrderPaymentDetail;

public interface OrderPaymentDetailMapper {
    int deleteByPrimaryKey(Long id);

    int insert(OrderPaymentDetail record);

    int insertSelective(OrderPaymentDetail record);

    OrderPaymentDetail selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(OrderPaymentDetail record);

    int updateByPrimaryKey(OrderPaymentDetail record);
}