package hyper.darye.service;

import hyper.darye.dto.OrderDetail;
import hyper.darye.dto.OrderMain;
import hyper.darye.mapper.OrderDetailMapper;
import hyper.darye.mapper.OrderMainMapper;
import hyper.darye.mapper.OrderMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {
    private final OrderMapper orderMapper;
    private final OrderMainMapper orderMainMapper;
    private final OrderDetailMapper orderDetailMapper;

    public OrderService(OrderMapper orderMapper, OrderMainMapper orderMainMapper, OrderDetailMapper orderDetailMapper) {
        this.orderMapper = orderMapper;
        this.orderMainMapper = orderMainMapper;
        this.orderDetailMapper = orderDetailMapper;
    }

    // [클라이언트] order 생성 - orderMain orderDetail 생성
    public void insertOrder(OrderDetail orderDetail) {
//        orderMapper.insertOrderMain(orderMain);
        orderMapper.insertOrderDetail(orderDetail);
    }

    // [관리자] order 주문 전체 조회
    public List<OrderMain> selectAllOrderMain(){
//        List<OrderMain> orderMains
        return orderMainMapper.selectAllOrderMain();
    }
}
