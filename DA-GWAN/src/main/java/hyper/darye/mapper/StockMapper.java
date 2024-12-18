package hyper.darye.mapper;

import hyper.darye.dto.Stock;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface StockMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Stock record);

    int insertSelective(Stock record);

    Stock selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Stock record);

    int updateByPrimaryKey(Stock record);
}