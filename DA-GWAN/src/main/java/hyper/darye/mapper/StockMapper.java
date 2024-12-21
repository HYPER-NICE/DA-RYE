package hyper.darye.mapper;

import hyper.darye.dto.Stock;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface StockMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Stock record);

    int insertSelective(Stock record);

    Stock selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Stock record);

    int updateByPrimaryKey(Stock record);

    @Select("SELECT * FROM STOCK WHERE product_id = #{productId}")
    List<Stock> selectByProductId(Long productId);

    @Insert("insert into STOCK " +
            "(PRODUCT_ID, STOCK_INOUT_QUANTITY, STOCK_CHANGE_NOTE, CURRENT_STOCK, STOCK_INOUT_DATE) " +
            "VALUES " +
            "(#{productId}, #{stockInoutQuantity}, #{stockChangeNote}, #{currentStock}, now())")
    int insertStock(Stock stock);

    @Select("select * from STOCK " +
            "group by PRODUCT_ID" +
            "")
    Stock selectRecentStock(Long productId);
}