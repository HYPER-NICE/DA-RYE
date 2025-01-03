package hyper.darye.service.order;

import hyper.darye.dto.OrderDetail;
import hyper.darye.dto.OrderMain;
import hyper.darye.dto.Product;
import hyper.darye.mapper.order.OrderDetailMapper;
import hyper.darye.mapper.order.OrderMainMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * 주문 서비스
 */
@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderMainMapper orderMainMapper;
    private final OrderDetailMapper orderDetailMapper;

    /**
     * 주문 생성
     * TODO 프로덕트 키 검증이 필요한가? 필요하다면 무슨 에러를 던질것인가?
     */
    @Transactional
    public int add(Long memberId, List<Product> products) {
        // 주문 메인 생성
        OrderMain orderMain = OrderMain.createForAdd(memberId);
        orderMainMapper.insertSelective(orderMain);

        // 주문 상세 생성
        List<OrderDetail> orderDetails = new ArrayList<>();
        for(Product product : products) {
            OrderDetail orderDetail = OrderDetail.createForAdd(orderMain.getId(), product);
            orderDetails.add(orderDetail);
        }
        return orderDetailMapper.insertBulk(orderDetails);
    }
}
