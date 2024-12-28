package hyper.darye.dto.controller.request;

import lombok.Data;

@Data
public class RequestDeleteProductDto {
    /**
     * 공통 코드 ID (외래 키)
     */
    private Long productStatusCodeId;
}
