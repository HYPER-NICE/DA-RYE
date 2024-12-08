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
import java.util.HashSet;
import java.util.Set;

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

    private Set<Category> categories;

    private Category malcha;
    private Product malchaProduct;

    @BeforeEach
    void setUp() {
        categories = new HashSet<>();

        // 최상위 카테고리 삽입
        Category teaRoot = new Category("차", null);
        categoryDao.insertCategory(teaRoot);
        categories.add(teaRoot);

        Category teawareRoot = new Category("다기", null);
        categoryDao.insertCategory(teawareRoot);
        categories.add(teawareRoot);

        // 2단계 카테고리 삽입 (차 -> 녹차, 홍차, 백차 등)
        Category teaGreen = new Category("녹차", teaRoot.getId());
        categoryDao.insertCategory(teaGreen);
        categories.add(teaGreen);

        Category teaBlack = new Category("홍차", teaRoot.getId());
        categoryDao.insertCategory(teaBlack);
        categories.add(teaBlack);

        Category teaWhite = new Category("백차", teaRoot.getId());
        categoryDao.insertCategory(teaWhite);
        categories.add(teaWhite);

        Category teaOolong = new Category("우롱차", teaRoot.getId());
        categoryDao.insertCategory(teaOolong);
        categories.add(teaOolong);

        Category teaHerbal = new Category("허브차", teaRoot.getId());
        categoryDao.insertCategory(teaHerbal);
        categories.add(teaHerbal);

        // 3단계 - 녹차 세분화
        Category teaGreenMatcha = new Category("말차", teaGreen.getId());
        categoryDao.insertCategory(teaGreenMatcha);
        categories.add(teaGreenMatcha);
        malcha = this.categoryDao.selectCategoryById(teaGreenMatcha.getId());

        Category teaGreenPowder = new Category("가루녹차", teaGreen.getId());
        categoryDao.insertCategory(teaGreenPowder);
        categories.add(teaGreenPowder);

        Category teaGreenLeaf = new Category("잎녹차", teaGreen.getId());
        categoryDao.insertCategory(teaGreenLeaf);
        categories.add(teaGreenLeaf);

        // 3단계 - 홍차 세분화
        Category teaBlackEnglish = new Category("잉글리시 브렉퍼스트", teaBlack.getId());
        categoryDao.insertCategory(teaBlackEnglish);
        categories.add(teaBlackEnglish);

        Category teaBlackEarlGrey = new Category("얼그레이", teaBlack.getId());
        categoryDao.insertCategory(teaBlackEarlGrey);
        categories.add(teaBlackEarlGrey);

        Category teaBlackDarjeeling = new Category("다즐링", teaBlack.getId());
        categoryDao.insertCategory(teaBlackDarjeeling);
        categories.add(teaBlackDarjeeling);

        // 3단계 - 백차 세분화
        Category teaWhiteSilver = new Category("봉우리 백차", teaWhite.getId());
        categoryDao.insertCategory(teaWhiteSilver);
        categories.add(teaWhiteSilver);

        Category teaWhiteNeedle = new Category("은침 백차", teaWhite.getId());
        categoryDao.insertCategory(teaWhiteNeedle);
        categories.add(teaWhiteNeedle);

        // 3단계 - 우롱차 세분화
        Category teaOolongBigRed = new Category("대홍포", teaOolong.getId());
        categoryDao.insertCategory(teaOolongBigRed);
        categories.add(teaOolongBigRed);

        Category teaOolongIronGoddess = new Category("철관음", teaOolong.getId());
        categoryDao.insertCategory(teaOolongIronGoddess);
        categories.add(teaOolongIronGoddess);

        Category teaOolongDongDing = new Category("동정 우롱", teaOolong.getId());
        categoryDao.insertCategory(teaOolongDongDing);
        categories.add(teaOolongDongDing);

        // 3단계 - 허브차 세분화
        Category teaHerbalLavender = new Category("라벤더 허브차", teaHerbal.getId());
        categoryDao.insertCategory(teaHerbalLavender);
        categories.add(teaHerbalLavender);

        Category teaHerbalChamomile = new Category("캐모마일 허브차", teaHerbal.getId());
        categoryDao.insertCategory(teaHerbalChamomile);
        categories.add(teaHerbalChamomile);

        Category teaHerbalPeppermint = new Category("페퍼민트 허브차", teaHerbal.getId());
        categoryDao.insertCategory(teaHerbalPeppermint);
        categories.add(teaHerbalPeppermint);

        // 2단계 카테고리 삽입 (다기 -> 찻잔, 다관, 다기 세트 등)
        Category teawareCup = new Category("찻잔", teawareRoot.getId());
        categoryDao.insertCategory(teawareCup);
        categories.add(teawareCup);

        Category teawareTeapot = new Category("다관", teawareRoot.getId());
        categoryDao.insertCategory(teawareTeapot);
        categories.add(teawareTeapot);

        Category teawareSet = new Category("다기 세트", teawareRoot.getId());
        categoryDao.insertCategory(teawareSet);
        categories.add(teawareSet);

        Category teawareTable = new Category("찻상", teawareRoot.getId());
        categoryDao.insertCategory(teawareTable);
        categories.add(teawareTable);

        Category teawareAccessory = new Category("차 소품", teawareRoot.getId());
        categoryDao.insertCategory(teawareAccessory);
        categories.add(teawareAccessory);

        // 3단계 - 찻잔 세분화
        Category teawareCupGlass = new Category("유리찻잔", teawareCup.getId());
        categoryDao.insertCategory(teawareCupGlass);
        categories.add(teawareCupGlass);

        Category teawareCupCeramic = new Category("도자기찻잔", teawareCup.getId());
        categoryDao.insertCategory(teawareCupCeramic);
        categories.add(teawareCupCeramic);

        Category teawareCupIron = new Category("철제찻잔", teawareCup.getId());
        categoryDao.insertCategory(teawareCupIron);
        categories.add(teawareCupIron);

        Category teawareCupWood = new Category("목제찻잔", teawareCup.getId());
        categoryDao.insertCategory(teawareCupWood);
        categories.add(teawareCupWood);

        Category teawareCupJapan = new Category("일본식찻잔", teawareCup.getId());
        categoryDao.insertCategory(teawareCupJapan);
        categories.add(teawareCupJapan);

        Category teawareCupChina = new Category("중국식찻잔", teawareCup.getId());
        categoryDao.insertCategory(teawareCupChina);
        categories.add(teawareCupChina);

        Category teawareCupPremiumGlass = new Category("고급 유리찻잔", teawareCup.getId());
        categoryDao.insertCategory(teawareCupPremiumGlass);
        categories.add(teawareCupPremiumGlass);

        Category teawareCupPremiumCeramic = new Category("고급 도자기찻잔", teawareCup.getId());
        categoryDao.insertCategory(teawareCupPremiumCeramic);
        categories.add(teawareCupPremiumCeramic);

        Category teawareCupHandmade = new Category("수제찻잔", teawareCup.getId());
        categoryDao.insertCategory(teawareCupHandmade);
        categories.add(teawareCupHandmade);

        Category teawareCupSmall = new Category("소형찻잔", teawareCup.getId());
        categoryDao.insertCategory(teawareCupSmall);
        categories.add(teawareCupSmall);

        // 3단계 - 다관 세분화
        Category teawareTeapotTrad = new Category("전통 다관", teawareTeapot.getId());
        categoryDao.insertCategory(teawareTeapotTrad);
        categories.add(teawareTeapotTrad);

        Category teawareTeapotModern = new Category("현대 다관", teawareTeapot.getId());
        categoryDao.insertCategory(teawareTeapotModern);
        categories.add(teawareTeapotModern);

        Category teawareTeapotCeramic = new Category("도자기 다관", teawareTeapot.getId());
        categoryDao.insertCategory(teawareTeapotCeramic);
        categories.add(teawareTeapotCeramic);

        Category teawareTeapotGlass = new Category("유리 다관", teawareTeapot.getId());
        categoryDao.insertCategory(teawareTeapotGlass);
        categories.add(teawareTeapotGlass);

        Category teawareTeapotMini = new Category("미니 다관", teawareTeapot.getId());
        categoryDao.insertCategory(teawareTeapotMini);
        categories.add(teawareTeapotMini);

        Category teawareTeapotLarge = new Category("대형 다관", teawareTeapot.getId());
        categoryDao.insertCategory(teawareTeapotLarge);
        categories.add(teawareTeapotLarge);

        Category teawareTeapotJapan = new Category("일본식 다관", teawareTeapot.getId());
        categoryDao.insertCategory(teawareTeapotJapan);
        categories.add(teawareTeapotJapan);

        Category teawareTeapotChina = new Category("중국식 다관", teawareTeapot.getId());
        categoryDao.insertCategory(teawareTeapotChina);
        categories.add(teawareTeapotChina);

        Category teawareTeapotPremium = new Category("고급 다관", teawareTeapot.getId());
        categoryDao.insertCategory(teawareTeapotPremium);
        categories.add(teawareTeapotPremium);

        Category teawareTeapotHandmade = new Category("수제 다관", teawareTeapot.getId());
        categoryDao.insertCategory(teawareTeapotHandmade);
        categories.add(teawareTeapotHandmade);

        // 새로 삽입된 카테고리 확인
        Set<Category> newCategories = new HashSet<>();
        for (Category category : categories) {
            Category dbCategory = categoryDao.selectCategoryById(category.getId());
            newCategories.add(dbCategory);
        }

        categories = newCategories;


        // 프로덕트 등록
        Product product = new Product("진한 말차", "농축된 성분만 담았습니다.", 15000, 1, malcha.getId());
        productDao.insert(product);
        malchaProduct = productDao.selectByPrimaryKey(product.getId());
    }

    @DisplayName("상품을 추가한다")
    @Test
    void insertProduct() {
        // Given, 말차 상품 등록
        Product product = new Product("건강한 말차", "건강한 성분만 담았습니다.", 15000, 10, malcha.getId());

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
        Product product = new Product("건강한 말차", "건강한 성분만 담았습니다.", 15000, 10, malcha.getId());
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
        Product product = new Product("건강한 말차", "건강한 성분만 담았습니다.", 15000, 10, malcha.getId());
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
        Inbound inbound = new Inbound(malchaProduct.getId(), "BATCH-001", 100, 500000, now, expiredAt);

        // When
        int insertedCount = inboundDao.insert(inbound);

        // Then
        assertThat(insertedCount)
                .withFailMessage("입고 내역이 추가되면 1이 반환되어야 합니다.")
                .isEqualTo(1);
    }

    @DisplayName("입고되면 재고가 증가한다")
    @Test
    void insertInboundThenStockQuantityIncrease() {
        // Given
        String batchNumber = "BATCH-001"; // 상품 생산 번호
        int quantity = 100;  // 입고 수량
        int purchasePrice = 500000;  // 입고 가격
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime expiredAt = now.plusMonths(12);  // 12개월 후 만료
        Inbound inbound = new Inbound(malchaProduct.getId(), batchNumber, quantity, purchasePrice, now, expiredAt);

        // When
        int insertedCount = inboundDao.insert(inbound);

        // Then
        assertThat(insertedCount)
                .withFailMessage("입고 내역이 추가되면 1이 반환되어야 합니다.")
                .isEqualTo(1);

        Product foundProduct = productDao.selectByPrimaryKey(malchaProduct.getId());
        assertThat(foundProduct.getStockQuantity())
                .withFailMessage("입고된 상품의 재고가 증가되어야 합니다.")
                .isGreaterThanOrEqualTo(quantity);
    }

    @DisplayName("입고 내역을 조회한다")
    @Test
    void selectInbound() {
        // Given
        String batchNumber = "BATCH-001"; // 상품 생산 번호
        int quantity = 100;  // 입고 수량
        int purchasePrice = 500000;  // 입고 가격
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime expiredAt = now.plusMonths(12);  // 12개월 후 만료
        Inbound inbound = new Inbound(malchaProduct.getId(), batchNumber, quantity, purchasePrice, now, expiredAt);
        inboundDao.insert(inbound);

        // When
        Inbound foundInbound = inboundDao.selectByPrimaryKey(inbound.getId());

        // Then
        assertThat(foundInbound)
                .withFailMessage("입고된 데이터가 조회되어야 합니다.")
                .isNotNull();

        assertThat(foundInbound.getProductId())
                .withFailMessage("입고된 상품의 ID가 조회되어야 합니다.")
                .isEqualTo(malchaProduct.getId());

        assertThat(foundInbound.getBatchNumber())
                .withFailMessage("입고된 상품의 배치 번호가 조회되어야 합니다.")
                .isEqualTo("BATCH-001");

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
        String batchNumber = "BATCH-001"; // 상품 생산 번호
        int quantity = 100;  // 입고 수량
        int purchasePrice = 500000;  // 입고 가격
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime expiredAt = now.plusMonths(12);  // 12개월 후 만료
        Inbound inbound = new Inbound(malchaProduct.getId(), batchNumber, quantity, purchasePrice, now, expiredAt);
        inboundDao.insert(inbound);

        // When
        int deletedCount = inboundDao.deleteByPrimaryKey(inbound.getId());

        // Then
        assertThat(deletedCount).isEqualTo(1);
        assertThat(inboundDao.selectByPrimaryKey(inbound.getId())).isNull();
    }
}
