package hyper.darye.service;

import hyper.darye.dto.Stock;
import hyper.darye.mapper.StockMapper;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
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

        List<Stock> recentStock = this.stockMapper.selectByProductId(productId);
        Long currentStock = recentStock.get(recentStock.size()-1).getCurrentStock();
        stock.setCurrentStock(currentStock + inOutQuantity);
        stock.setStockInoutDate(new Date());

        return stockMapper.insertSelective(stock);
    }
}
