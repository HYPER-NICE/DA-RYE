package hyper.darye.mapper;

import hyper.darye.dto.OrderMain;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface OrderMainMapper {
    int deleteByPrimaryKey(Long id);

    int insert(OrderMain record);

    int insertSelective(OrderMain record);

    OrderMain selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(OrderMain record);

    int updateByPrimaryKey(OrderMain record);

    @Select("select * from ORDER_MAIN where MEMBER_ID = #{memberId}")
    OrderMain selectByMemberId(Long memberId);

}