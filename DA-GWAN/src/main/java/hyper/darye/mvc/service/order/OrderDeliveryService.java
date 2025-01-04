package hyper.darye.mvc.service.order;

import hyper.darye.mvc.model.entity.OrderDeliveryMain;
import hyper.darye.mvc.model.entity.OrderDeliveryStatus;
import hyper.darye.mvc.mapper.order.delivery.OrderDeliveryMainMapper;
import hyper.darye.mvc.mapper.order.delivery.OrderDeliveryStatusMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class OrderDeliveryService {

    private final OrderDeliveryMainMapper orderDeliveryMainMapper;
    private final OrderDeliveryStatusMapper orderDeliveryStatusMapper;

    /**
     * 배송 생성합니다. 메인 테이블, 디테일 테이블에 데이터가 모두 생성됩니다.
     * @param orderId 주문 ID
     * @param deliveryRequestNote 배송 요청 사항
     * @return
     */
    @Transactional
    public int add(Long orderId, String deliveryRequestNote) {
        // 배송 메인 엔트리 생성
        // 최초 데이터 생성시에는 데이터가 없으므로 null로 처리
        OrderDeliveryMain orderDeliveryMain = OrderDeliveryMain.createForAdd(orderId, null, null, deliveryRequestNote);
        orderDeliveryMainMapper.insertSelective(orderDeliveryMain);

        // 배송 상태 엔트리 생성
        // 최초 데이터 생성시에는 데이터가 없으므로 null로 처리
        OrderDeliveryStatus orderDeliveryStatus = OrderDeliveryStatus.createForAdd(orderDeliveryMain.getId(),  null, null);
        return orderDeliveryStatusMapper.insertSelective(orderDeliveryStatus);
    }
}
