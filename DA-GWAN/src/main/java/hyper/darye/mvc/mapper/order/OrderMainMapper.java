package hyper.darye.mvc.mapper.order;

import hyper.darye.mvc.model.entity.OrderMain;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OrderMainMapper {
    int deleteByPrimaryKey(Long id);

    int insert(OrderMain record);

    int insertSelective(OrderMain record);

    OrderMain selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(OrderMain record);

    int updateByPrimaryKey(OrderMain record);
}