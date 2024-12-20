package hyper.darye.service;

import hyper.darye.dto.Stock;
import hyper.darye.mapper.StockMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class StockService {

    @Autowired
    private StockMapper stockMapper;

    public int insertStock(Long productId, Long inOutQuantity, String stockChangeNote) {
        Stock stock = new Stock();
        stock.setProductId(productId);
        stock.setStockInoutQuantity(inOutQuantity);
        stock.setStockChangeNote(stockChangeNote);

        List<Stock> recentStock = stockMapper.selectByProductId(productId);
        if(recentStock.size() != 0) {
            Long currentStock = recentStock.get(0).getCurrentStock();
            stock.setCurrentStock(currentStock + inOutQuantity);
        }
        else {
            if(inOutQuantity < 0)
                throw new IllegalArgumentException("수량이 부족합니다");
            else
                stock.setCurrentStock(inOutQuantity);
        }
        stock.setStockInoutDate(new Date());

        return stockMapper.insertSelective(stock);
    }

    // 상품 전체 이력 조회
    public List<Stock> selectByProductId(Long productId){
        return stockMapper.selectByProductId(productId);
    }

    //최신 상품재고 현황 검색
    public Long selectCurrentStock(Long productId){
        return stockMapper.selectRecentQuantity(productId);
    }
}
