package hyper.darye.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class OrderPaymentDetail {
    // composite primary key
    private int orderPaymentMainId;         // FK
    private int paymentMethodCodeId;
    private int orderPaymentStatusCodeId;

    private String approvalNumber;
    private int paymentAmount;
    private LocalDateTime paymentDate;
    private String paymentComment;
    private LocalDateTime createdDate;
    private LocalDateTime LastModifiedDate;
    private int LastModifiedMember;         // FK
    private LocalDateTime deletedDate;
}
