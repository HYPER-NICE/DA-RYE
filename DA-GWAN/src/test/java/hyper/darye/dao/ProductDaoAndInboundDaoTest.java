package hyper.darye.dao;

import hyper.darye.model.Category;
import hyper.darye.model.Inbound;
import hyper.darye.model.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@MybatisTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)  // 실제 DB 사용
public class ProductDaoAndInboundDaoTest {

    @Autowired
    private CategoryDao categoryDao;

    @Autowired
    private ProductDao productDao;

    @Autowired
    private InboundDao inboundDao;

    private Category malchaCategory;
    private Product JJinhanMalchaProduct;

    @BeforeEach
    void setUp() {
        // 최상위 카테고리 삽입
        Category teaRoot = new Category("차", null);
        categoryDao.insertCategory(teaRoot);

        // 2단계 카테고리 삽입 (차 -> 녹차, 홍차, 백차 등)
        Category teaGreen = new Category("녹차", teaRoot.getId());
        categoryDao.insertCategory(teaGreen);

        // 3단계 - 녹차 세분화
        Category teaGreenMatcha = new Category("말차", teaGreen.getId());
        categoryDao.insertCategory(teaGreenMatcha);
        malchaCategory = this.categoryDao.selectCategoryById(teaGreenMatcha.getId());

        // 프로덕트 등록
        Product product = new Product("진한 말차", "농축된 성분만 담았습니다.", 15000, malchaCategory.getId());
        productDao.insert(product);
        JJinhanMalchaProduct = productDao.selectByPrimaryKey(product.getId());
    }

    @DisplayName("상품을 추가한다")
    @Test
    void insertProduct() {
        // Given, 말차 상품 등록
        Product product = new Product("건강한 말차", "건강한 성분만 담았습니다.", 15000, malchaCategory.getId());

        // When
        int insertedCount = productDao.insert(product);

        // Then
        assertThat(insertedCount)
                .withFailMessage("상품이 추가되면 1이 반환되어야 합니다.")
                .isEqualTo(1);
    }

    @DisplayName("상품을 조회한다")
    @Test
    void selectProduct() {
        // Given, 말차 상품 등록
        Product product = new Product("건강한 말차", "건강한 성분만 담았습니다.", 15000, malchaCategory.getId());
        productDao.insert(product);

        // When
        Product foundProduct = productDao.selectByPrimaryKey(product.getId());

        // Then
        assertThat(foundProduct)
                .withFailMessage("추가한 상품(id: %d, name: %s)가 조회 되어야 합니다.", product.getId(), product.getName())
                .isNotNull();
    }

    @DisplayName("상품을 삭제한다")
    @Test
    void deleteProduct() {
        // Given, 말차 상품 등록
        Product product = new Product("건강한 말차", "건강한 성분만 담았습니다.", 15000, malchaCategory.getId());
        productDao.insert(product);

        // When
        int deletedCount = productDao.deleteByPrimaryKey(product.getId());

        // Then
        assertThat(deletedCount)
                .withFailMessage("상품이 삭제되면 1이 반환되어야 합니다.")
                .isEqualTo(1);
    }

    // ============================
    // 테스트 케이스: InboundDao
    // ============================
    @DisplayName("입고 내역을 추가한다")
    @Test
    void insertInbound() {
        // Given
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime expiredAt = now.plusMonths(12);  // 12개월 후 만료
        Inbound inbound = new Inbound(JJinhanMalchaProduct.getId(), "BATCH-001", 100, 500000, now, expiredAt);

        // When
        int insertedCount = inboundDao.insert(inbound);

        // Then
        assertThat(insertedCount)
                .withFailMessage("입고 내역이 추가되면 1이 반환되어야 합니다.")
                .isEqualTo(1);
    }

//    @DisplayName("입고되면 재고가 증가한다")
//    @Test
//    void insertInboundThenStockQuantityIncrease() {
//        // Given
//        String batchNumber = "BATCH-001"; // 상품 생산 번호
//        int quantity = 100;  // 입고 수량
//        int purchasePrice = 500000;  // 입고 가격
//        LocalDateTime now = LocalDateTime.now();
//        LocalDateTime expiredAt = now.plusMonths(12);  // 12개월 후 만료
//        Inbound inbound = new Inbound(malchaProduct.getId(), batchNumber, quantity, purchasePrice, now, expiredAt);
//
//        // When
//        int insertedCount = inboundDao.insert(inbound);
//
//        // Then
//        assertThat(insertedCount)
//                .withFailMessage("입고 내역이 추가되면 1이 반환되어야 합니다.")
//                .isEqualTo(1);
//
//        Product foundProduct = productDao.selectByPrimaryKey(malchaProduct.getId());
//        assertThat(foundProduct.getStockQuantity())
//                .withFailMessage("입고된 상품의 재고가 증가되어야 합니다.")
//                .isGreaterThanOrEqualTo(quantity);
//    }
//
//    @DisplayName("입고 내역을 조회한다")
//    @Test
//    void selectInbound() {
//        // Given
//        String batchNumber = "BATCH-001"; // 상품 생산 번호
//        int quantity = 100;  // 입고 수량
//        int purchasePrice = 500000;  // 입고 가격
//        LocalDateTime now = LocalDateTime.now();
//        LocalDateTime expiredAt = now.plusMonths(12);  // 12개월 후 만료
//        Inbound inbound = new Inbound(malchaProduct.getId(), batchNumber, quantity, purchasePrice, now, expiredAt);
//        inboundDao.insert(inbound);
//
//        // When
//        Inbound foundInbound = inboundDao.selectByPrimaryKey(inbound.getId());
//
//        // Then
//        assertThat(foundInbound)
//                .withFailMessage("입고된 데이터가 조회되어야 합니다.")
//                .isNotNull();
//
//        assertThat(foundInbound.getProductId())
//                .withFailMessage("입고된 상품의 ID가 조회되어야 합니다.")
//                .isEqualTo(malchaProduct.getId());
//
//        assertThat(foundInbound.getBatchNumber())
//                .withFailMessage("입고된 상품의 배치 번호가 조회되어야 합니다.")
//                .isEqualTo("BATCH-001");
//
//        assertThat(foundInbound.getQuantity())
//                .withFailMessage("입고된 상품의 수량이 조회되어야 합니다.")
//                .isEqualTo(quantity);
//
//        assertThat(foundInbound.getPurchasePrice())
//                .withFailMessage("입고된 상품의 가격이 조회되어야 합니다.")
//                .isEqualTo(purchasePrice);
//    }
//
//    @DisplayName("입고 내역을 삭제한다")
//    @Test
//    void deleteInbound() {
//        // Given
//        String batchNumber = "BATCH-001"; // 상품 생산 번호
//        int quantity = 100;  // 입고 수량
//        int purchasePrice = 500000;  // 입고 가격
//        LocalDateTime now = LocalDateTime.now();
//        LocalDateTime expiredAt = now.plusMonths(12);  // 12개월 후 만료
//        Inbound inbound = new Inbound(malchaProduct.getId(), batchNumber, quantity, purchasePrice, now, expiredAt);
//        inboundDao.insert(inbound);
//
//        // When
//        int deletedCount = inboundDao.deleteByPrimaryKey(inbound.getId());
//
//        // Then
//        assertThat(deletedCount).isEqualTo(1);
//        assertThat(inboundDao.selectByPrimaryKey(inbound.getId())).isNull();
//    }
}
