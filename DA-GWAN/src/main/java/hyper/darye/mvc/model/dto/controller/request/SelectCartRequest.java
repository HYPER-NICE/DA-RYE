package hyper.darye.mvc.model.dto.controller.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
@AllArgsConstructor
public class SelectCartRequest {

    @Schema(description = "장바구니 아이디", required = true)
    private Long cartId;

    @Schema(description = "상품 아이디", required = true)
    private Long productId;

    @Schema(description = "회원 아이디", required = true)
    private Long memberId;

    @Schema(description = "상품 수량", example = "1", required = true)
    private Long quantity;

    @Schema(description = "상품명", example = "과자", required = true)
    private String productName;

    @Schema(description = "상품 가격", example = "10000", required = true)
    private Integer productPrice;

    @Schema(description = "상품 사진", example = "바이트 코드", required = true)
    private Byte productImage;


}
