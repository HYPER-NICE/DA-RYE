package hyper.darye.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 회원의 주문 정보를 저장하는 메인 테이블로, 주문의 기본 정보(총 금액, 주문 날짜 등)를 관리합니다.
 * ORDER_MAIN
 */
@Getter
@NoArgsConstructor // 세터 없이 할경우, 마이바티스는 리플렉션으로 접근, 성능 저하
public class OrderMain implements Serializable {
    /**
     * 주문 고유 ID (기본 키)
     */
    private Long id;

    /**
     * 주문을 생성한 회원의 고유 ID (외래 키)
     */
    private Long memberId;

    /**
     * 주문이 발생한 날짜 및 시간
     */
    private LocalDateTime orderDate;

    /**
     * 레코드 생성 날짜 및 시간
     */
    private LocalDateTime createdDate;

    /**
     * 마지막 수정된 날짜 및 시간
     */
    private LocalDateTime lastModifiedDate;

    /**
     * 레코드를 마지막으로 수정한 회원 ID
     */
    private Long lastModifiedMember;

    /**
     * 레코드가 삭제된 날짜 (논리 삭제)
     */
    private LocalDateTime deletedDate;

    private static final long serialVersionUID = 1L;

    /**
     * Add용 객체 생성 메서드
     * - 시스템 컬럼은 빌더에서 초기화하지 않음
     */
    public static OrderMain createForAdd(Long memberId) {
        OrderMain orderMain = new OrderMain();
        orderMain.memberId = memberId;
        orderMain.orderDate = LocalDateTime.now();
        return orderMain;
    }
}