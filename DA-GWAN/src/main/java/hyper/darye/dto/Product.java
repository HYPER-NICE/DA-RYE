package hyper.darye.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
public class Product {
    private Integer id; // 상품 ID
    private Integer categoryId; // 카테고리 ID
    private Integer productStatusCodeId; // 상품 상태 코드

    // 데이터
    private String name; // 상품 이름
    private byte[] thumbnailImage; // 썸네일 이미지
    private String shortDescription; // 간단 설명
    private byte[] descriptionImage; // 상세 설명 이미지
    private String longDescription; // 상세 설명
    private Integer price; // 판매 가격

    private String manufacturer; // 제조원
    private LocalDateTime expirationDate; // 소비기한
    private String ingredients; // 원재료명 및 성분
    private String precautions; // 주의사항
    private String importer; // 수입원

    private LocalDateTime saleDate; // 판매 시작일
    private Integer capacity; // 용량
    private String unit; // 단위 (kg, g, ml 등)
    private Integer quantity; // 수량

    // 시스템 값
    private LocalDateTime createdDate;  // 생성 날짜
    private LocalDateTime lastModifiedDate; // 수정 날짜
    private Long lastModifiedMember; // 레코드를 마지막으로 수정한 회원 ID
    private LocalDateTime deletedDate; //삭제 날짜
}
