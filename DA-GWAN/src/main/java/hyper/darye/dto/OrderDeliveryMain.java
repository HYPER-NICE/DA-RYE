package hyper.darye.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class OrderDeliveryMain {
    private int id;
    private int orderMainId;                // FK
    private String deliveryCompanyName;
    private String trackingNumber;
    private String deliveryRequestNote;
    private LocalDateTime createdDate;
    private LocalDateTime lastModifiedDate;
    private int lastModifiedMember;         // FK
    private LocalDateTime deletedDate;
}
