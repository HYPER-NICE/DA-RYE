package hyper.darye.mvc.service;

import hyper.darye.system.constant.StockType;
import hyper.darye.mvc.model.entity.Stock;
import hyper.darye.mvc.mapper.StockMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class StockService {

    @Autowired
    private StockMapper stockMapper;


    // 입고시 재고 현황 삽입
    public int InsertStock(Long productId, Long inOutQuantity, String stockChangeNote) {
        Stock stock = new Stock();
        stock.setProductId(productId);
        stock.setStockInoutQuantity(inOutQuantity);
        stock.setStockChangeNote(stockChangeNote + " : " + StockType.INCOMING.getDescription());

        List<Stock> recentStock = stockMapper.selectByProductId(productId);
        if(!recentStock.isEmpty()) {
            Long currentStock = recentStock.get(0).getCurrentStock();
            stock.setCurrentStock((Long)(currentStock +  inOutQuantity));
        }
        else {
            stock.setCurrentStock(inOutQuantity);
        }
        stock.setStockInoutDate(new Date());

        return stockMapper.insertSelective(stock);
    }

    // 출고시 재고 현황 삽입
    public int orderInsertStock(Long productId, Long inOutQuantity, String stockChangeNote) {
        Stock stock = new Stock();
        stock.setProductId(productId);
        stock.setStockInoutQuantity(inOutQuantity);
        stock.setStockChangeNote(stockChangeNote + " : " + StockType.OUTCOMING.getDescription());

        List<Stock> recentStock = stockMapper.selectByProductId(productId);
        if(!recentStock.isEmpty()) {
            Long currentStock = recentStock.get(0).getCurrentStock();
            stock.setCurrentStock((Long)(currentStock +  inOutQuantity));
        }
        else {
            stock.setCurrentStock(inOutQuantity);
        }
        stock.setStockInoutDate(new Date());

        return stockMapper.insertSelective(stock);
    }

    // 환불시 재고 현황 삽입
    public int refundInsertStock(Long productId, Long inOutQuantity, String stockChangeNote) {
        Stock stock = new Stock();
        stock.setProductId(productId);
        stock.setStockInoutQuantity(inOutQuantity);
        stock.setStockChangeNote(stockChangeNote + " : " + StockType.REFUND.getDescription());

        List<Stock> recentStock = stockMapper.selectByProductId(productId);
        if(!recentStock.isEmpty()) {
            Long currentStock = recentStock.get(0).getCurrentStock();
            stock.setCurrentStock((Long)(currentStock +  inOutQuantity));
        }
        else {
            stock.setCurrentStock(inOutQuantity);
        }
        stock.setStockInoutDate(new Date());

        return stockMapper.insertSelective(stock);
    }
    // 환불시 재고 현황 삽입
    public int demagedInsertStock(Long productId, Long inOutQuantity, String stockChangeNote) {
        Stock stock = new Stock();
        stock.setProductId(productId);
        stock.setStockInoutQuantity(inOutQuantity);
        stock.setStockChangeNote(stockChangeNote + " : " + StockType.DEMAGED.getDescription());

        List<Stock> recentStock = stockMapper.selectByProductId(productId);
        if(!recentStock.isEmpty()) {
            Long currentStock = recentStock.get(0).getCurrentStock();
            stock.setCurrentStock((Long)(currentStock +  inOutQuantity));
        }
        else {
            stock.setCurrentStock(inOutQuantity);
        }
        stock.setStockInoutDate(new Date());

        return stockMapper.insertSelective(stock);
    }
    // 환불시 재고 현황 삽입
    public int checkedInsertStock(Long productId, Long inOutQuantity, String stockChangeNote) {
        Stock stock = new Stock();
        stock.setProductId(productId);
        stock.setStockInoutQuantity(inOutQuantity);
        stock.setStockChangeNote(stockChangeNote + " : " + StockType.CHECKED.getDescription());

        List<Stock> recentStock = stockMapper.selectByProductId(productId);
        if(!recentStock.isEmpty()) {
            Long currentStock = recentStock.get(0).getCurrentStock();
            stock.setCurrentStock((Long)(currentStock +  inOutQuantity));
        }
        else {
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
