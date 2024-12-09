package hyper.darye.dao;

import hyper.darye.model.OutboundReason;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface OutboundReasonDao {
    int deleteByPrimaryKey(Long id);

    int insert(OutboundReason record);

    int insertSelective(OutboundReason record);

    OutboundReason selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(OutboundReason record);

    int updateByPrimaryKey(OutboundReason record);

    // 추가 코드

    @Select("SELECT * FROM outbound_reason where deleted_date is null")
    List<OutboundReason> selectAll();
}