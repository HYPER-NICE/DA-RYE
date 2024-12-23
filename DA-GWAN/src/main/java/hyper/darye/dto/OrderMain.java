package hyper.darye.dto;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;
import lombok.Data;

/**
 * 회원의 주문 정보를 저장하는 메인 테이블로, 주문의 기본 정보(총 금액, 주문 날짜 등)를 관리합니다.
 * ORDER_MAIN
 */
@Data
public class OrderMain implements Serializable {

    private Long id;
    private Long memberId;                              // FK
    private LocalDateTime orderDate;
    private LocalDateTime createdDate;
    private LocalDateTime lastModifiedDate;
    private Long lastModifiedMember;
    private LocalDateTime deletedDate;
    private static final long serialVersionUID = 1L;
}