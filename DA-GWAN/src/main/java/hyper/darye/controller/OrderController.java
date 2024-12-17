package hyper.darye.controller;

import lombok.Data;
import org.springframework.cglib.core.Local;

import java.time.LocalDateTime;

@Data
public class OrderController {
    private int id;
    private int orderId;                        // FK
    private int productId;                      // FK
    private int quantity;
    private int unitPrice;
    private LocalDateTime createdDate;
    private LocalDateTime LastModifiedDate;
    private int LastModifiedMember;             // FK
    private LocalDateTime deletedDate;
}
