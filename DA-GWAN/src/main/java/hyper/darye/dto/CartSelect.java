package hyper.darye.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
@AllArgsConstructor
public class CartSelect {

    // 키
    Long cartId;
    Long productId;
    Long memberId;

    // 화면 표기용
    Long quantity;
    String productName;
//    String productImg;
    Integer productPrice;

    // 삭제 확인용
    Date deletedDate;
}
