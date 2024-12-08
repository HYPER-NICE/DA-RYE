package hyper.darye.dao;

import hyper.darye.model.Inbound;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface InboundDao {
    int deleteByPrimaryKey(Long id);

    int insert(Inbound record);

    int insertSelective(Inbound record);

    Inbound selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Inbound record);

    int updateByPrimaryKey(Inbound record);
}