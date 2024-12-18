package hyper.darye.mapper;

import hyper.darye.dto.PaymentStatusCode;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PaymentStatusCodeMapper {
    int deleteByPrimaryKey(Long id);

    int insert(PaymentStatusCode record);

    int insertSelective(PaymentStatusCode record);

    PaymentStatusCode selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(PaymentStatusCode record);

    int updateByPrimaryKey(PaymentStatusCode record);
}