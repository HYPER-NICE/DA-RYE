package hyper.darye.service;

import hyper.darye.mvc.model.entity.Stock;
import hyper.darye.mvc.mapper.StockMapper;
import hyper.darye.mvc.service.StockService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class StockServiceTest {

    @Mock
    private StockMapper stockMapper;

    @InjectMocks
    private StockService stockService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("재고 삽입 테스트")
    void InsertStocktest() {
        // Arrange
        Long productId = 1L;
        Long inOutQuantity = 10L;
        String stockChangeNote = "In";
        when(stockMapper.selectByProductId(productId)).thenReturn(new ArrayList<>());
        when(stockMapper.insertSelective(any(Stock.class))).thenReturn(1);

        // Act
        int result = stockService.InsertStock(productId, inOutQuantity, stockChangeNote);

        // Assert
        assertEquals(1, result);
        verify(stockMapper, times(1)).insertSelective(any(Stock.class));
    }

    @Test
    @DisplayName("이미있는 재고 업데이트 테스트")
    void InsertStockUpdateQuantitytest() {
        // Arrange
        Long productId = 2L;
        Long inOutQuantity = 5L;
        String stockChangeNote = "재고 업데이트";
        List<Stock> recentStocks = new ArrayList<>();
        Stock recentStock = new Stock();
        recentStock.setCurrentStock(15L);
        recentStocks.add(recentStock);
        when(stockMapper.selectByProductId(productId)).thenReturn(recentStocks);
        when(stockMapper.insertSelective(any(Stock.class))).thenReturn(1);

        // Act
        int result = stockService.InsertStock(productId, inOutQuantity, stockChangeNote);

        // Assert
        assertEquals(1, result);
        verify(stockMapper, times(1)).insertSelective(any(Stock.class));
    }

    @Test
    @DisplayName("재고 조회 테스트")
    void SelectByProductIdTest() {
        // Arrange
        Long productId = 3L;
        List<Stock> mockStocks = new ArrayList<>();
        Stock stock1 = new Stock();
        stock1.setProductId(productId);
        stock1.setStockInoutQuantity(10L);
        mockStocks.add(stock1);
        when(stockMapper.selectByProductId(productId)).thenReturn(mockStocks);

        // Act
        List<Stock> result = stockService.selectByProductId(productId);

        // Assert
        assertEquals(1, result.size());
        assertEquals(10L, result.get(0).getStockInoutQuantity());
        verify(stockMapper, times(1)).selectByProductId(eq(productId));
    }

    @Test
    @DisplayName("최신 재고 조회 테스트")
    void testSelectCurrentStock() {
        // Arrange
        Long productId = 4L;
        Long currentStock = 20L;
        when(stockMapper.selectRecentQuantity(productId)).thenReturn(currentStock);

        // Act
        Long result = stockService.selectCurrentStock(productId);

        // Assert
        assertEquals(currentStock, result);
        verify(stockMapper, times(1)).selectRecentQuantity(eq(productId));
    }
}
