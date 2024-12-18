package hyper.darye.mapper;

import hyper.darye.dto.PointTransactionType;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PointTransactionTypeMapper {
    int deleteByPrimaryKey(Long id);

    int insert(PointTransactionType record);

    int insertSelective(PointTransactionType record);

    PointTransactionType selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(PointTransactionType record);

    int updateByPrimaryKey(PointTransactionType record);
}