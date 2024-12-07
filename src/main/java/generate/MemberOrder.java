package generate;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * member_order
 */
@Data
public class MemberOrder implements Serializable {
    private Long id;

    private Long memberId;

    private Integer totalPrice;

    private Date orderDate;

    private String deliveryAddress;

    private String paymentMethod;

    private Object deliveryStatus;

    private Object status;

    private Date createdDate;

    private Date lastModifiedDate;

    private static final long serialVersionUID = 1L;
}