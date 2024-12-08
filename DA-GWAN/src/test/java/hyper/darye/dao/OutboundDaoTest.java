package hyper.darye.dao;

import hyper.darye.model.*;
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
public class OutboundDaoTest {

    @Autowired
    private CategoryDao categoryDao;

    @Autowired
    private ProductDao productDao;

    @Autowired
    private InboundDao inboundDao;

    @Autowired
    private OutboundDao outboundDao;

    @Autowired
    private OutboundReasonDao outboundReasonDao;

    private Category malchaCategory;
    private Product JJinhanMalchaProduct;
    private Inbound inboundData;
    private OutboundReason outboundReasonData;

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

        // 입고 등록
        String batchNumber = "BATCH-001";
        int quantity = 100;
        int purchasePrice = 500000;
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime expiredAt = now.plusMonths(12);  // 12개월 후 만료
        inboundData = new Inbound(JJinhanMalchaProduct.getId(), batchNumber, quantity, purchasePrice, now, expiredAt);
        inboundDao.insert(inboundData);

        // 출고 사유 등록
        outboundReasonData = this.outboundReasonDao.selectAll().get(0);
    }

    @DisplayName("출고 내역을 추가한다")
    @Test
    void insertOutbound() {
        // Given
        int outBoundQuantity = 5;
        LocalDateTime outBoundAt = LocalDateTime.now();
        Outbound outbound = new Outbound(inboundData.getId(), outBoundQuantity, outBoundAt, outboundReasonData.getId());  // 이유 ID는 1로 설정

        // When
        int insertedCount = outboundDao.insert(outbound);

        // Then
        assertThat(insertedCount)
                .withFailMessage("출고 내역이 추가되면 1이 반환되어야 합니다.")
                .isEqualTo(1);
    }

    @DisplayName("출고 내역을 조회한다")
    @Test
    void selectOutbound() {
        // Given
        int outBoundQuantity = 5;
        LocalDateTime outBoundAt = LocalDateTime.now();
        Outbound outbound = new Outbound(inboundData.getId(), outBoundQuantity, outBoundAt, outboundReasonData.getId());  // 이유 ID는 1로 설정
        outboundDao.insert(outbound);

        // When
        Outbound foundOutbound = outboundDao.selectByPrimaryKey(outbound.getId());

        // Then
        assertThat(foundOutbound)
                .withFailMessage("출고된 데이터가 조회되어야 합니다.")
                .isNotNull();
        assertThat(foundOutbound.getInboundId())
                .withFailMessage("출고된 상품의 입고 ID가 조회되어야 합니다")
                .isEqualTo(inboundData.getId());
        assertThat(foundOutbound.getReasonId())
                .withFailMessage("출고된 상품의 이유 ID가 조회되어야 합니다.")
                .isEqualTo(outboundReasonData.getId());
        assertThat(foundOutbound.getQuantity())
                .withFailMessage("출고된 상품의 수량이 조회되어야 합니다.")
                .isEqualTo(outBoundQuantity);
    }

    @DisplayName("출고 내역을 삭제한다")
    @Test
    void deleteOutbound() {
        // Given
        int outBoundQuantity = 5;
        LocalDateTime outBoundAt = LocalDateTime.now();
        Outbound outbound = new Outbound(inboundData.getId(), outBoundQuantity, outBoundAt, outboundReasonData.getId());  // 이유 ID는 1로 설정
        outboundDao.insert(outbound);

        // When
        int deletedCount = outboundDao.deleteByPrimaryKey(outbound.getId());

        // Then
        assertThat(deletedCount)
                .withFailMessage("출고 내역이 삭제되면 1이 반환되어야 합니다.")
                .isEqualTo(1);
    }

    @DisplayName("출고 내역을 업데이트 한다")
    @Test
    void updateOutbound() {
        // Given
        int outBoundQuantity = 5;
        LocalDateTime outBoundAt = LocalDateTime.now();
        Outbound outbound = new Outbound(inboundData.getId(), outBoundQuantity, outBoundAt, outboundReasonData.getId());  // 이유 ID는 1로 설정
        outboundDao.insert(outbound);

        // When
        int updateQuantity = 80;
        outbound.setQuantity(updateQuantity);  // 수량 변경
        int updatedCount = outboundDao.updateByPrimaryKey(outbound);

        // Then
        assertThat(updatedCount)
                .withFailMessage("출고 내역이 업데이트되면 1이 반환되어야 합니다.")
                .isEqualTo(1);

        Outbound updatedOutbound = outboundDao.selectByPrimaryKey(outbound.getId());
        assertThat(updatedOutbound.getQuantity())
                .withFailMessage("출고된 수량이 업데이트 되어야 합니다.")
                .isEqualTo(updateQuantity);
    }
}
