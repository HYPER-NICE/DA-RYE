package hyper.darye.dao;

import hyper.darye.model.Product;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ProductDao {
    int deleteByPrimaryKey(Long id);

    int insert(Product record);

    int insertSelective(Product record);

    Product selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Product record);

    int updateByPrimaryKey(Product record);
}