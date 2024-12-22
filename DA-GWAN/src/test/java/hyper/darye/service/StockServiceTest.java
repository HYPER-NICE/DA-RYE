package hyper.darye.service;

import hyper.darye.dto.Stock;
import hyper.darye.mapper.StockMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
class StockServiceTest {

    @Autowired
    private StockService stockService;  // StockService 주입

    @Autowired
    private StockMapper stockMapper;  // StockMapper 주입

    @Test
    @DisplayName("STOCK 삽입 테스트")
    void insertStockTest() {
        List<Stock> stockList = stockMapper.selectAll();
        Long productId = stockList.get(0).getProductId();
        Long stockInoutQuantity = (Long)3L;
        String stockChangeNote = "In";

        // stockService를 호출하여 재고 변경
        stockService.insertStock(productId, stockInoutQuantity, stockChangeNote);

        // 변경된 재고 정보 확인
        stockList = stockMapper.selectByProductId(productId);

        Long quantity = stockList.get(1).getCurrentStock(); // 이전 재고
        Long currentQuantity = stockList.get(0).getCurrentStock(); // 현재 재고

        // 단위 테스트 조건 확인
        assertEquals(quantity + stockInoutQuantity, currentQuantity);
        assertEquals(stockChangeNote, stockList.get(0).getStockChangeNote());
        assertEquals(productId, stockList.get(0).getProductId());
    }

    @Test
    @DisplayName("전체 조회 테스트")
    public void selectByProductId(){
        Long productId = (Long)5L;

        List<Stock> stockList = stockMapper.selectByProductId(productId);

        Integer size = (Integer) stockList.size();
        stockList.clear();

        Long stockInoutQuantity = (Long)10L;
        String stockChangeNote = "OUT_ORDER";

        // stockService를 호출하여 재고 변경
        stockService.insertStock(productId, stockInoutQuantity, stockChangeNote);

        stockList = stockMapper.selectByProductId(productId);

        assertEquals(size+1, stockList.size());

    }

    @Test
    @DisplayName("최신 재고 조회")
    public void selectCurrentStockTest(){
        List<Stock> stockList = stockMapper.selectAll();
        Long productId = stockList.get(0).getProductId();
        Long stockInoutQuantity = (Long)3L;
        String stockChangeNote = "In";

        // stockService를 호출하여 재고 변경
        stockService.insertStock(productId, stockInoutQuantity, stockChangeNote);

        // 변경된 재고 정보 확인
        stockList = stockMapper.selectByProductId(productId);

        Long currentQuantity = stockList.get(0).getCurrentStock(); // 현재 재고
        Long currentStock = stockService.selectCurrentStock(productId);

        assertEquals(currentQuantity, currentStock);
    }

}