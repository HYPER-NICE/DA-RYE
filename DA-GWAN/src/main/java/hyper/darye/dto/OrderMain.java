package hyper.darye.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class OrderMain {
    private int id;
    private int memberId;                   // FK
    private LocalDateTime orderDate;
    private LocalDateTime createdDate;
    private LocalDateTime lastModifiedDate;
    private int lastModifiedMemberId;       // FK
    private LocalDateTime deletedDate;
}
