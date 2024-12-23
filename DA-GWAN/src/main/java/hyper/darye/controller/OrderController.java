package hyper.darye.controller;

import hyper.darye.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

// /api/members/{id}/orders
@RestController
@RequestMapping("/api/members/{id}")
public class OrderController {

    @Autowired
    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }


    // 주문 생성
    @PostMapping("orders")
    public String insertOrder(@PathVariable Long id, Long order){

        return "";
    }

    // 주문 전체 조회

    // 주문 상세 조회

    // 주문 숨김

    // 환불 요청

}
