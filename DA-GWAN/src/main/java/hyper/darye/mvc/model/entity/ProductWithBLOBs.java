package hyper.darye.mvc.model.entity;

import java.io.Serializable;

import lombok.*;

/**
 * 판매하는 상품의 정보 테이블
 * PRODUCT
 */
@Data
@Getter
@Setter
public class ProductWithBLOBs extends Product implements Serializable {
    /**
     * 썸네일 이미지
     */
    private byte[] thumbnailImage;

    /**
     * 상세 설명 이미지
     */
    private byte[] descriptionImage;

    private static final long serialVersionUID = 1L;
}