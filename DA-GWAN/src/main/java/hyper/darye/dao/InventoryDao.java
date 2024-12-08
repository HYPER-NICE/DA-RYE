package hyper.darye.dao;

import hyper.darye.model.Inventory;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface InventoryDao {
    int deleteByPrimaryKey(Long id);

    int insert(Inventory record);

    int insertSelective(Inventory record);

    Inventory selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Inventory record);

    int updateByPrimaryKey(Inventory record);
}