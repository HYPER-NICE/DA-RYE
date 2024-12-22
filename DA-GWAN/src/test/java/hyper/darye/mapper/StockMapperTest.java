package hyper.darye.mapper;

import hyper.darye.dto.Stock;
import hyper.darye.service.StockService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Transactional
class StockMapperTest {

    @Autowired
    private StockMapper stockMapper;
    @Autowired
    private StockService stockService;

    @Test
    @DisplayName("Stock 삽입및 조회 테스트")
    void insertStock() {
        Long productId = (Long)10L;
        Long stockInoutQuantity = (Long)10L;
        String stockChangeNote = "OUT_ORDER";

        // 이전 재고
        Long lastQuantity = stockMapper.selectRecentQuantity(productId);

        stockService.insertStock(productId, stockInoutQuantity, stockChangeNote);

        // 변경된 재고 정보 확인
        List<Stock> stockList = stockService.selectByProductId(productId);

        assertEquals(productId, stockList.get(0).getProductId());
        assertEquals(stockInoutQuantity, stockList.get(0).getStockInoutQuantity());
        assertEquals(stockChangeNote, stockList.get(0).getStockChangeNote());
        assertEquals(lastQuantity + stockInoutQuantity, stockList.get(0).getCurrentStock());


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
        Long insertQuantity = stockService.selectCurrentStock(productId);

        assertEquals(currentStock, insertQuantity);
    }
}