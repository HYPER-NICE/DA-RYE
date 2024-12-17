package hyper.darye.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class OrderDetail {
    private int id;
    private int orderId;                    // FK
    private int productId;                  // FK
    private int quantity;
    private int unitPrice;
    private LocalDateTime createdDate;
    private LocalDateTime LastModifiedDate;
    private int lastModifiedMember;         // FK
    private LocalDateTime deletedDate;
}
