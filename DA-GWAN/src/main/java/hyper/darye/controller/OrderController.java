package hyper.darye.controller;

import hyper.darye.service.order.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

// /api/members/{id}/orders
@RestController
@RequestMapping("/api/members/{memberId}")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    // 주문 생성
    @PostMapping("orders")
    @ResponseStatus(HttpStatus.CREATED)
    public String insertOrder(@PathVariable Long id, Long order){
//        int resultOrderMain = OrderService.insertOrderMain();
        return "";
    }

    // 주문 전체 조회
    @GetMapping("orders")
    public String selectAllOrderMain(@PathVariable Long id){
//        List<OrderMain> orders =  OrderService.selectAllOrderMain();

        return "";
    }

    // 주문 상세 조회
    @GetMapping("orders/{orderId}")
    public String selectOrder(@PathVariable Long orderId){

        return "";
    }

    // 주문 숨김
    @DeleteMapping("orders/{orderId}")
    public String deleteOrder(@PathVariable Long orderId){
        return "";
    }

    // 환불 요청


}
