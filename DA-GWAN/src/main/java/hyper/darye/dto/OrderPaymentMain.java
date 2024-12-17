package hyper.darye.dto;

import java.time.LocalDateTime;

public class OrderPaymentMain {
    private int id;
    private int orderId;                        // PK
    private int totalAmount;
    private LocalDateTime createdDate;
    private LocalDateTime lastModifiedDate;
    private int lastModifiedMember;             // PK
    private LocalDateTime deletedDate;
}
