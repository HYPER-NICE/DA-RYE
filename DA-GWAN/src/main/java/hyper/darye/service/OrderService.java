package hyper.darye.service;

import hyper.darye.dto.OrderDetail;
import hyper.darye.dto.OrderMain;
import hyper.darye.mapper.OrderMapper;
import org.springframework.stereotype.Service;

@Service
public class OrderService {
    private final OrderMapper orderMapper;

    public OrderService(OrderMapper orderMapper) {
        this.orderMapper = orderMapper;
    }

    // orderMain orderDetail 생성
    public void insertOrderDetail(OrderDetail orderDetail) {
//        orderMapper.insertOrderMain(orderMain);
        orderMapper.insertOrderDetail(orderDetail);
    }
}
