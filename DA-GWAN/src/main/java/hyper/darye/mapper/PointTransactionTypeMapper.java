package hyper.darye.mapper;

import hyper.darye.model.entity.PointTransactionType;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface PointTransactionTypeMapper {
    int deleteByPrimaryKey(Long id);

    int insert(PointTransactionType record);

    int insertSelective(PointTransactionType record);

    PointTransactionType selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(PointTransactionType record);

    int updateByPrimaryKey(PointTransactionType record);

    @Select("select * from POINT_TRANSACTION_TYPE")
    List<PointTransactionType> selectAll();
}