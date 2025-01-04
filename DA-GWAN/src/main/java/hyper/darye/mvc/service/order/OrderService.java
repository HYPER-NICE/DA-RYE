package hyper.darye.mvc.service.order;

import hyper.darye.mvc.model.entity.OrderDetail;
import hyper.darye.mvc.model.entity.OrderMain;
import hyper.darye.mvc.model.entity.Product;
import hyper.darye.mvc.mapper.order.OrderDetailMapper;
import hyper.darye.mvc.mapper.order.OrderMainMapper;
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

//     TODO 프로덕트 키 검증이 필요한가? 필요하다면 무슨 에러를 던질것인가?

    /**
     * 주문 생성합니다. 메인 테이블, 디테일 테이블에 데이터가 모두 생성됩니다.
     * @param memberId
     * @param products
     * @return
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

            // TODO 주문된 물건의 수량을 차감하는 기능
            // TODO 배송 상태 메서드 추가
            // TODO 결제 상태 메서드 추가
            orderDetails.add(orderDetail);
        }
        return orderDetailMapper.insertBulk(orderDetails);
    }
}
