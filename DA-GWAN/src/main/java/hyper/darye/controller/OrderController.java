package hyper.darye.controller;

import hyper.darye.dto.OrderDetail;
import hyper.darye.dto.OrderMain;
import hyper.darye.service.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

// /api/members/{id}/orders
@RestController
@RequestMapping("/orders")
public class OrderController {
    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    // 주문생성
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public String insertOrder(@RequestBody OrderDetail orderDetail){
        orderService.insertOrderDetail(orderDetail);
        return "ORDER 성공";
    }
}
