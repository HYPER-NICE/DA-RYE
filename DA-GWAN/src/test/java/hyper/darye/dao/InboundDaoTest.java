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
public class InboundDaoTest {

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

    @DisplayName("입고 내역을 추가한다")
    @Test
    void insertInbound() {
        // Given
        String batchNumber = "BATCH-001";
        int quantity = 100;
        int purchasePrice = 500000;
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime expiredAt = now.plusMonths(12);  // 12개월 후 만료
        Inbound inbound = new Inbound(JJinhanMalchaProduct.getId(), batchNumber, quantity, purchasePrice, now, expiredAt);
        inboundDao.insert(inbound);

        // When
        int insertedCount = inboundDao.insert(inbound);

        // Then
        assertThat(insertedCount)
                .withFailMessage("입고 내역이 추가되면 1이 반환되어야 합니다.")
                .isEqualTo(1);
    }

    @DisplayName("입고 내역을 조회한다")
    @Test
    void selectInbound() {
        // Given
        String batchNumber = "BATCH-001";
        int quantity = 100;
        int purchasePrice = 500000;
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime expiredAt = now.plusMonths(12);  // 12개월 후 만료
        Inbound inbound = new Inbound(JJinhanMalchaProduct.getId(), batchNumber, quantity, purchasePrice, now, expiredAt);
        inboundDao.insert(inbound);

        // When
        Inbound foundInbound = inboundDao.selectByPrimaryKey(inbound.getId());

        // Then
        assertThat(foundInbound)
                .withFailMessage("입고된 데이터가 조회되어야 합니다.")
                .isNotNull();
        assertThat(foundInbound.getProductId())
                .withFailMessage("입고된 상품의 ID가 조회되어야 합니다.")
                .isEqualTo(JJinhanMalchaProduct.getId());
        assertThat(foundInbound.getBatchNumber())
                .withFailMessage("입고된 상품의 배치 번호가 조회되어야 합니다.")
                .isEqualTo(batchNumber);
        assertThat(foundInbound.getQuantity())
                .withFailMessage("입고된 상품의 수량이 조회되어야 합니다.")
                .isEqualTo(quantity);
        assertThat(foundInbound.getPurchasePrice())
                .withFailMessage("입고된 상품의 가격이 조회되어야 합니다.")
                .isEqualTo(purchasePrice);
    }

    @DisplayName("입고 내역을 삭제한다")
    @Test
    void deleteInbound() {
        // Given
        String batchNumber = "BATCH-001";
        int quantity = 100;
        int purchasePrice = 500000;
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime expiredAt = now.plusMonths(12);  // 12개월 후 만료
        Inbound inbound = new Inbound(JJinhanMalchaProduct.getId(), batchNumber, quantity, purchasePrice, now, expiredAt);
        inboundDao.insert(inbound);

        // When
        int deletedCount = inboundDao.deleteByPrimaryKey(inbound.getId());

        // Then
        assertThat(deletedCount)
                .withFailMessage("입고 내역이 삭제되면 1이 반환되어야 합니다.")
                .isEqualTo(1);
    }

    @DisplayName("입고 내역을 업데이트 한다")
    @Test
    void updateInbound() {
        // Given
        String batchNumber = "BATCH-001";
        int quantity = 100;
        int purchasePrice = 500000;
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime expiredAt = now.plusMonths(12);  // 12개월 후 만료
        Inbound inbound = new Inbound(JJinhanMalchaProduct.getId(), batchNumber, quantity, purchasePrice, now, expiredAt);
        inboundDao.insert(inbound);

        // When
        int updateQuantity = 200;
        inbound.setQuantity(updateQuantity); // 수량 변경
        int updatedCount = inboundDao.updateByPrimaryKey(inbound);

        // Then
        assertThat(updatedCount)
                .withFailMessage("입고 내역이 업데이트되면 1이 반환되어야 합니다.")
                .isEqualTo(1);

        Inbound updatedInbound = inboundDao.selectByPrimaryKey(inbound.getId());
        assertThat(updatedInbound.getQuantity())
                .withFailMessage("입고된 수량이 업데이트 되어야 합니다.")
                .isEqualTo(updateQuantity);
    }
}
