package hyper.darye.service;

import hyper.darye.dto.Stock;
import hyper.darye.mapper.StockMapper;
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
    void insertStockTest() {
        Long productId = 10L;
        Long stockInoutQuantity = -10L;
        String stockChangeNote = "OUT_ORDER";

        // stockService를 호출하여 재고 변경
        stockService.insertStock(productId, stockInoutQuantity, stockChangeNote);

        // 변경된 재고 정보 확인
        List<Stock> stockList = stockMapper.selectByProductId(productId);

        Long quantity = stockList.get(stockList.size() - 2).getCurrentStock();
        Long currentQuantity = stockList.get(stockList.size() - 1).getCurrentStock();

        // 단위 테스트 조건 확인
        assertEquals(quantity + stockInoutQuantity, currentQuantity);
        assertEquals(stockChangeNote, stockList.get(stockList.size() - 1).getStockChangeNote());
        assertEquals(productId, stockList.get(stockList.size() - 1).getProductId());
    }
}