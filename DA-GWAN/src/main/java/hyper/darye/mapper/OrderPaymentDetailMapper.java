package hyper.darye.mapper;

import hyper.darye.dto.OrderPaymentDetail;

public interface OrderPaymentDetailMapper {
    int deleteByPrimaryKey(Long id);

    int insert(OrderPaymentDetail record);

    int insertSelective(OrderPaymentDetail record);

    OrderPaymentDetail selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(OrderPaymentDetail record);

    int updateByPrimaryKey(OrderPaymentDetail record);
}