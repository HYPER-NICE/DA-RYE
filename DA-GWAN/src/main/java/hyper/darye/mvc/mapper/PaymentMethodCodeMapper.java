package hyper.darye.mvc.mapper;

import hyper.darye.mvc.model.entity.PaymentMethodCode;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PaymentMethodCodeMapper {
    int deleteByPrimaryKey(Long id);

    int insert(PaymentMethodCode record);

    int insertSelective(PaymentMethodCode record);

    PaymentMethodCode selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(PaymentMethodCode record);

    int updateByPrimaryKey(PaymentMethodCode record);
}