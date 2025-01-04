package hyper.darye.model.dto.controller.request;

import lombok.Data;

@Data
public class RequestDeleteProductDto {
    /**
     * 상품 ID (기본 키)
     */
    private Long id;

    /**
     * 공통 코드 ID (외래 키)
     */
    private Long productStatusCodeId;
}
