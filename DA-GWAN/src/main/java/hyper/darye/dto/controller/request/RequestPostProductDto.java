package hyper.darye.dto.controller.request;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class RequestPostProductDto implements Serializable {
    /**
     * 카테고리 ID (외래 키)
     */
    private Long categoryId;

    /**
     * 공통 코드 ID (외래 키)
     */
    private Long productStatusCodeId;

    /**
     * 상품 이름
     */
    private String name;

    /**
     * 상품 간단 설명
     */
    private String shortDescription;

    /**
     * 상품 상세 설명
     */
    private String longDescription;

    /**
     * 판매 가격
     */
    private Integer price;

    /**
     * 제조원
     */
    private String manufacturer;

    /**
     * 소비기한
     */
    private Date expirationDate;

    /**
     * 원재료명 및 성분
     */
    private String ingredients;

    /**
     * 주의사항
     */
    private String precautions;

    /**
     * 수입원
     */
    private String importer;

    /**
     * 판매시작일
     */
    private Date saleDate;

    /**
     * 용량
     */
    private Integer capacity;

    /**
     * 단위 (kg, g, ml, ...)
     */
    private String unit;

    /**
     * 수량
     */
    private Integer quantity;

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
