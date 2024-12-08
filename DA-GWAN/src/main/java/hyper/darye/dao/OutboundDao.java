package hyper.darye.dao;

import hyper.darye.model.Outbound;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OutboundDao {
    int deleteByPrimaryKey(Long id);

    int insert(Outbound record);

    int insertSelective(Outbound record);

    Outbound selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Outbound record);

    int updateByPrimaryKey(Outbound record);
}