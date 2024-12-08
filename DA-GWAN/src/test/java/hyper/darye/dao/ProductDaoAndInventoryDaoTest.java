package hyper.darye.dao;

import hyper.darye.dto.CategoryDto;
import hyper.darye.model.Inventory;
import hyper.darye.model.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@MybatisTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)  // 실제 DB 사용
public class ProductDaoAndInventoryDaoTest {

    @Autowired
    private CategoryDao categoryDao;

    @Autowired
    private ProductDao productDao;

    @Autowired
    private InventoryDao inventoryDao;

    private Set<CategoryDto> categories;

    private CategoryDto malcha;
    private Product malchaProduct;

    @BeforeEach
    void setUp() {
        categories = new HashSet<>();

        // 최상위 카테고리 삽입
        CategoryDto teaRoot = new CategoryDto("차", null);
        categoryDao.insertCategory(teaRoot);
        categories.add(teaRoot);

        CategoryDto teawareRoot = new CategoryDto("다기", null);
        categoryDao.insertCategory(teawareRoot);
        categories.add(teawareRoot);

        // 2단계 카테고리 삽입 (차 -> 녹차, 홍차, 백차 등)
        CategoryDto teaGreen = new CategoryDto("녹차", teaRoot.getId());
        categoryDao.insertCategory(teaGreen);
        categories.add(teaGreen);

        CategoryDto teaBlack = new CategoryDto("홍차", teaRoot.getId());
        categoryDao.insertCategory(teaBlack);
        categories.add(teaBlack);

        CategoryDto teaWhite = new CategoryDto("백차", teaRoot.getId());
        categoryDao.insertCategory(teaWhite);
        categories.add(teaWhite);

        CategoryDto teaOolong = new CategoryDto("우롱차", teaRoot.getId());
        categoryDao.insertCategory(teaOolong);
        categories.add(teaOolong);

        CategoryDto teaHerbal = new CategoryDto("허브차", teaRoot.getId());
        categoryDao.insertCategory(teaHerbal);
        categories.add(teaHerbal);

        // 3단계 - 녹차 세분화
        CategoryDto teaGreenMatcha = new CategoryDto("말차", teaGreen.getId());
        categoryDao.insertCategory(teaGreenMatcha);
        categories.add(teaGreenMatcha);
        malcha = this.categoryDao.selectCategoryById(teaGreenMatcha.getId());

        CategoryDto teaGreenPowder = new CategoryDto("가루녹차", teaGreen.getId());
        categoryDao.insertCategory(teaGreenPowder);
        categories.add(teaGreenPowder);

        CategoryDto teaGreenLeaf = new CategoryDto("잎녹차", teaGreen.getId());
        categoryDao.insertCategory(teaGreenLeaf);
        categories.add(teaGreenLeaf);

        // 3단계 - 홍차 세분화
        CategoryDto teaBlackEnglish = new CategoryDto("잉글리시 브렉퍼스트", teaBlack.getId());
        categoryDao.insertCategory(teaBlackEnglish);
        categories.add(teaBlackEnglish);

        CategoryDto teaBlackEarlGrey = new CategoryDto("얼그레이", teaBlack.getId());
        categoryDao.insertCategory(teaBlackEarlGrey);
        categories.add(teaBlackEarlGrey);

        CategoryDto teaBlackDarjeeling = new CategoryDto("다즐링", teaBlack.getId());
        categoryDao.insertCategory(teaBlackDarjeeling);
        categories.add(teaBlackDarjeeling);

        // 3단계 - 백차 세분화
        CategoryDto teaWhiteSilver = new CategoryDto("봉우리 백차", teaWhite.getId());
        categoryDao.insertCategory(teaWhiteSilver);
        categories.add(teaWhiteSilver);

        CategoryDto teaWhiteNeedle = new CategoryDto("은침 백차", teaWhite.getId());
        categoryDao.insertCategory(teaWhiteNeedle);
        categories.add(teaWhiteNeedle);

        // 3단계 - 우롱차 세분화
        CategoryDto teaOolongBigRed = new CategoryDto("대홍포", teaOolong.getId());
        categoryDao.insertCategory(teaOolongBigRed);
        categories.add(teaOolongBigRed);

        CategoryDto teaOolongIronGoddess = new CategoryDto("철관음", teaOolong.getId());
        categoryDao.insertCategory(teaOolongIronGoddess);
        categories.add(teaOolongIronGoddess);

        CategoryDto teaOolongDongDing = new CategoryDto("동정 우롱", teaOolong.getId());
        categoryDao.insertCategory(teaOolongDongDing);
        categories.add(teaOolongDongDing);

        // 3단계 - 허브차 세분화
        CategoryDto teaHerbalLavender = new CategoryDto("라벤더 허브차", teaHerbal.getId());
        categoryDao.insertCategory(teaHerbalLavender);
        categories.add(teaHerbalLavender);

        CategoryDto teaHerbalChamomile = new CategoryDto("캐모마일 허브차", teaHerbal.getId());
        categoryDao.insertCategory(teaHerbalChamomile);
        categories.add(teaHerbalChamomile);

        CategoryDto teaHerbalPeppermint = new CategoryDto("페퍼민트 허브차", teaHerbal.getId());
        categoryDao.insertCategory(teaHerbalPeppermint);
        categories.add(teaHerbalPeppermint);

        // 2단계 카테고리 삽입 (다기 -> 찻잔, 다관, 다기 세트 등)
        CategoryDto teawareCup = new CategoryDto("찻잔", teawareRoot.getId());
        categoryDao.insertCategory(teawareCup);
        categories.add(teawareCup);

        CategoryDto teawareTeapot = new CategoryDto("다관", teawareRoot.getId());
        categoryDao.insertCategory(teawareTeapot);
        categories.add(teawareTeapot);

        CategoryDto teawareSet = new CategoryDto("다기 세트", teawareRoot.getId());
        categoryDao.insertCategory(teawareSet);
        categories.add(teawareSet);

        CategoryDto teawareTable = new CategoryDto("찻상", teawareRoot.getId());
        categoryDao.insertCategory(teawareTable);
        categories.add(teawareTable);

        CategoryDto teawareAccessory = new CategoryDto("차 소품", teawareRoot.getId());
        categoryDao.insertCategory(teawareAccessory);
        categories.add(teawareAccessory);

        // 3단계 - 찻잔 세분화
        CategoryDto teawareCupGlass = new CategoryDto("유리찻잔", teawareCup.getId());
        categoryDao.insertCategory(teawareCupGlass);
        categories.add(teawareCupGlass);

        CategoryDto teawareCupCeramic = new CategoryDto("도자기찻잔", teawareCup.getId());
        categoryDao.insertCategory(teawareCupCeramic);
        categories.add(teawareCupCeramic);

        CategoryDto teawareCupIron = new CategoryDto("철제찻잔", teawareCup.getId());
        categoryDao.insertCategory(teawareCupIron);
        categories.add(teawareCupIron);

        CategoryDto teawareCupWood = new CategoryDto("목제찻잔", teawareCup.getId());
        categoryDao.insertCategory(teawareCupWood);
        categories.add(teawareCupWood);

        CategoryDto teawareCupJapan = new CategoryDto("일본식찻잔", teawareCup.getId());
        categoryDao.insertCategory(teawareCupJapan);
        categories.add(teawareCupJapan);

        CategoryDto teawareCupChina = new CategoryDto("중국식찻잔", teawareCup.getId());
        categoryDao.insertCategory(teawareCupChina);
        categories.add(teawareCupChina);

        CategoryDto teawareCupPremiumGlass = new CategoryDto("고급 유리찻잔", teawareCup.getId());
        categoryDao.insertCategory(teawareCupPremiumGlass);
        categories.add(teawareCupPremiumGlass);

        CategoryDto teawareCupPremiumCeramic = new CategoryDto("고급 도자기찻잔", teawareCup.getId());
        categoryDao.insertCategory(teawareCupPremiumCeramic);
        categories.add(teawareCupPremiumCeramic);

        CategoryDto teawareCupHandmade = new CategoryDto("수제찻잔", teawareCup.getId());
        categoryDao.insertCategory(teawareCupHandmade);
        categories.add(teawareCupHandmade);

        CategoryDto teawareCupSmall = new CategoryDto("소형찻잔", teawareCup.getId());
        categoryDao.insertCategory(teawareCupSmall);
        categories.add(teawareCupSmall);

        // 3단계 - 다관 세분화
        CategoryDto teawareTeapotTrad = new CategoryDto("전통 다관", teawareTeapot.getId());
        categoryDao.insertCategory(teawareTeapotTrad);
        categories.add(teawareTeapotTrad);

        CategoryDto teawareTeapotModern = new CategoryDto("현대 다관", teawareTeapot.getId());
        categoryDao.insertCategory(teawareTeapotModern);
        categories.add(teawareTeapotModern);

        CategoryDto teawareTeapotCeramic = new CategoryDto("도자기 다관", teawareTeapot.getId());
        categoryDao.insertCategory(teawareTeapotCeramic);
        categories.add(teawareTeapotCeramic);

        CategoryDto teawareTeapotGlass = new CategoryDto("유리 다관", teawareTeapot.getId());
        categoryDao.insertCategory(teawareTeapotGlass);
        categories.add(teawareTeapotGlass);

        CategoryDto teawareTeapotMini = new CategoryDto("미니 다관", teawareTeapot.getId());
        categoryDao.insertCategory(teawareTeapotMini);
        categories.add(teawareTeapotMini);

        CategoryDto teawareTeapotLarge = new CategoryDto("대형 다관", teawareTeapot.getId());
        categoryDao.insertCategory(teawareTeapotLarge);
        categories.add(teawareTeapotLarge);

        CategoryDto teawareTeapotJapan = new CategoryDto("일본식 다관", teawareTeapot.getId());
        categoryDao.insertCategory(teawareTeapotJapan);
        categories.add(teawareTeapotJapan);

        CategoryDto teawareTeapotChina = new CategoryDto("중국식 다관", teawareTeapot.getId());
        categoryDao.insertCategory(teawareTeapotChina);
        categories.add(teawareTeapotChina);

        CategoryDto teawareTeapotPremium = new CategoryDto("고급 다관", teawareTeapot.getId());
        categoryDao.insertCategory(teawareTeapotPremium);
        categories.add(teawareTeapotPremium);

        CategoryDto teawareTeapotHandmade = new CategoryDto("수제 다관", teawareTeapot.getId());
        categoryDao.insertCategory(teawareTeapotHandmade);
        categories.add(teawareTeapotHandmade);

        // 새로 삽입된 카테고리 확인
        Set<CategoryDto> newCategories = new HashSet<>();
        for (CategoryDto category : categories) {
            CategoryDto dbCategory = categoryDao.selectCategoryById(category.getId());
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
        int insertedCount = productDao.insert(product);

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
        int insertedCount = productDao.insert(product);

        // When
        int deletedCount = productDao.deleteByPrimaryKey(product.getId());

        // Then
        assertThat(deletedCount)
                .withFailMessage("상품이 삭제되면 1이 반환되어야 합니다.")
                .isEqualTo(1);
    }

    // ============================
    // 테스트 케이스: InventoryDao
    // ============================
    @DisplayName("입고 내역을 추가한다")
    @Test
    void insertInventory() {
        // Given
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime expiredAt = now.plusMonths(12);  // 12개월 후 만료
        Inventory inventory = new Inventory(malchaProduct.getId(), "BATCH-001", 100, 500000, now, expiredAt);

        // When
        int insertedCount = inventoryDao.insert(inventory);

        // Then
        assertThat(insertedCount)
                .withFailMessage("입고 내역이 추가되면 1이 반환되어야 합니다.")
                .isEqualTo(1);
    }

    @DisplayName("입고 내역을 조회한다")
    @Test
    void selectInventory() {
        // Given
        String batchNumber = "BATCH-001"; // 상품 생산 번호
        int quantity = 100;  // 입고 수량
        int purchasePrice = 500000;  // 입고 가격
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime expiredAt = now.plusMonths(12);  // 12개월 후 만료
        Inventory inventory = new Inventory(malchaProduct.getId(), batchNumber, quantity, purchasePrice, now, expiredAt);
        inventoryDao.insert(inventory);

        // When
        Inventory foundInventory = inventoryDao.selectByPrimaryKey(inventory.getId());

        // Then
        assertThat(foundInventory)
                .withFailMessage("입고된 데이터가 조회되어야 합니다.")
                .isNotNull();

        assertThat(foundInventory.getProductId())
                .withFailMessage("입고된 상품의 ID가 조회되어야 합니다.")
                .isEqualTo(malchaProduct.getId());

        assertThat(foundInventory.getBatchNumber())
                .withFailMessage("입고된 상품의 배치 번호가 조회되어야 합니다.")
                .isEqualTo("BATCH-001");

        assertThat(foundInventory.getQuantity())
                .withFailMessage("입고된 상품의 수량이 조회되어야 합니다.")
                .isEqualTo(quantity);

        assertThat(foundInventory.getPurchasePrice())
                .withFailMessage("입고된 상품의 가격이 조회되어야 합니다.")
                .isEqualTo(purchasePrice);
    }

    @DisplayName("입고 내역을 삭제한다")
    @Test
    void deleteInventory() {
        // Given
        String batchNumber = "BATCH-001"; // 상품 생산 번호
        int quantity = 100;  // 입고 수량
        int purchasePrice = 500000;  // 입고 가격
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime expiredAt = now.plusMonths(12);  // 12개월 후 만료
        Inventory inventory = new Inventory(malchaProduct.getId(), batchNumber, quantity, purchasePrice, now, expiredAt);
        inventoryDao.insert(inventory);

        // When
        int deletedCount = inventoryDao.deleteByPrimaryKey(inventory.getId());

        // Then
        assertThat(deletedCount).isEqualTo(1);
        assertThat(inventoryDao.selectByPrimaryKey(inventory.getId())).isNull();
    }
}
