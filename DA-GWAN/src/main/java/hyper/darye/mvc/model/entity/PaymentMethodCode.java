package hyper.darye.mvc.model.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

import lombok.Data;

/**
 * PAYMENT_METHOD_CODE
 */
@Data
public class PaymentMethodCode implements Serializable {
    private Long id;
    private String name;
    private String description;
    private LocalDateTime createdDate;
    private LocalDateTime lastModifiedDate;
    private Long lastModifiedMember;
    private LocalDateTime deletedDate;
    private static final long serialVersionUID = 1L;
}