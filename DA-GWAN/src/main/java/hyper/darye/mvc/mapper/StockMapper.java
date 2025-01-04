package hyper.darye.mvc.mapper;

import hyper.darye.mvc.model.entity.Stock;
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

    // ProductID로 전체조회
    @Select("SELECT * FROM STOCK WHERE product_id = #{productId}" +
            " order by CREATED_DATE DESC")
    List<Stock> selectByProductId(Long productId);

    @Insert("insert into STOCK " +
            "(PRODUCT_ID, STOCK_INOUT_QUANTITY, STOCK_CHANGE_NOTE, CURRENT_STOCK, STOCK_INOUT_DATE) " +
            "VALUES " +
            "(#{productId}, #{stockInoutQuantity}, #{stockChangeNote}, #{currentStock}, #{stockInoutDate})")
    int insertStock(Stock stock);

    // ProductId로 최신 현황 조회
    @Select("select CURRENT_STOCK from STOCK " +
            "where PRODUCT_ID = #{productId} " +
            "order by CREATED_DATE DESC " +
            "LIMIT 1")
    Long selectRecentQuantity(Long productId);

    @Select("select * from STOCK")
    List<Stock> selectAll();

}