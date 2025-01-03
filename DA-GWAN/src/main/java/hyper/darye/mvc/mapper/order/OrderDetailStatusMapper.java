package hyper.darye.mvc.mapper.order;

import hyper.darye.mvc.model.entity.OrderDetailStatus;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OrderDetailStatusMapper {
    int deleteByPrimaryKey(Long id);

    int insert(OrderDetailStatus record);

    int insertSelective(OrderDetailStatus record);

    OrderDetailStatus selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(OrderDetailStatus record);

    int updateByPrimaryKey(OrderDetailStatus record);
}