package hyper.darye.mapper;

import hyper.darye.model.entity.DeliveryStatusCode;

public interface DeliveryStatusCodeMapper {
    int deleteByPrimaryKey(Long id);

    int insert(DeliveryStatusCode record);

    int insertSelective(DeliveryStatusCode record);

    DeliveryStatusCode selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(DeliveryStatusCode record);

    int updateByPrimaryKey(DeliveryStatusCode record);
}