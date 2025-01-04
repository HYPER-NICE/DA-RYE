package hyper.darye.mapper;

import hyper.darye.model.entity.Stock;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@MybatisTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Transactional
class StockMapperTest {

    @Autowired
    private StockMapper stockMapper;

    @Test
    @DisplayName("Stock 삽입및 조회 테스트")
    void insertStock() {
        Long productId = (Long)10L;
        Long stockInoutQuantity = (Long)10L;
        String stockChangeNote = "OUT_ORDER";
        Long currentQuantity = 10L;
        Stock stock = new Stock();
        stock.setProductId(productId);
        stock.setStockInoutQuantity(stockInoutQuantity);
        stock.setStockChangeNote(stockChangeNote);
        stock.setCurrentStock(currentQuantity);
        stock.setStockInoutDate(new Date());

        // 이전 재고
        Long lastQuantity = stockMapper.selectRecentQuantity(productId);

        stockMapper.insertSelective(stock);

        // 변경된 재고 정보 확인
        List<Stock> stockList = stockMapper.selectByProductId(productId);

        assertEquals(productId, stockList.get(0).getProductId());
        assertEquals(stockInoutQuantity, stockList.get(0).getStockInoutQuantity());
        assertEquals(stockChangeNote, stockList.get(0).getStockChangeNote());
        assertEquals(stockInoutQuantity, stockList.get(0).getCurrentStock());


    }

    @Test
    @DisplayName("상품 최신 재고 조회 테스트")
    void selectRecentStock() {
        Long productId = (Long) 10L;
        Long stockInoutQuantity = (Long) 10L;
        Long currentStock = (Long)100L;
        String stockChangeNote = "OUT_ORDER";

        Stock stock = new Stock();
        stock.setProductId(productId);
        stock.setStockInoutQuantity(stockInoutQuantity);
        stock.setStockChangeNote(stockChangeNote);
        stock.setStockInoutDate(new Date());
        stock.setCurrentStock(currentStock);

        stockMapper.insertStock(stock);

        // 변경된 재고 정보 확인
        Long insertQuantity = stockMapper.selectRecentQuantity(productId);

        assertEquals(currentStock, insertQuantity);
    }
}