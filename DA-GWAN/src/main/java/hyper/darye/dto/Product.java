package hyper.darye.dto;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 판매하는 상품의 정보 테이블
 * PRODUCT
 */
@Data
public class Product implements Serializable {
    /**
     * 상품 ID (기본 키)
     */
    private Long id;

    /**
     * 카테고리 ID (외래 키)
     */
    private Long categoryId;

    /**
     * 공통 코드 ID (외래 키)
     */
    private Long commonCodeId;

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
     * 생성 날짜
     */
    private Date createdDate;

    /**
     * 수정 날짜
     */
    private Date lastModifiedDate;

    /**
     * 레코드를 마지막으로 수정한 회원 ID
     */
    private Long lastModifiedMember;

    /**
     * 삭제 날짜
     */
    private Date deletedDate;

    private static final long serialVersionUID = 1L;
}
