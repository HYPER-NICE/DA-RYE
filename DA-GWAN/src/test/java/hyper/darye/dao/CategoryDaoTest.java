package hyper.darye.dao;

import hyper.darye.model.Category;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;


@MybatisTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE) // 실제 DB 사용
class CategoryDaoTest {

    @Autowired
    private CategoryDao categoryDao;

    private Set<Category> categories;

    private Category dummyCategory;

    @BeforeEach
    void setUp() {
        categories = new HashSet<>();

        // 최상위 카테고리 삽입
        Category teaRoot = new Category("차", null);
        categoryDao.insertCategory(teaRoot);
        categories.add(teaRoot);
        dummyCategory = this.categoryDao.selectCategoryById(teaRoot.getId());

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
    }

    @Test
    @DisplayName("카테고리 추가 테스트")
    void testInsertCategories() {
        Category category1 = new Category("테스트", null);
        Category category2 = new Category("테스트2", category1.getId());

        categoryDao.insertCategory(category1);
        categoryDao.insertCategory(category2);

        // 첫번째 카테고리 검증
        Category dbCategory1 = categoryDao.selectCategoryById(category1.getId());
        assertThat(dbCategory1)
                .withFailMessage("첫번째 카테고리(%s)는 null이 아니어야 합니다.", category1.getName())
                .isNotNull();

        assertThat(dbCategory1.getName())
                .withFailMessage("첫번째 카테고리의 이름은 삽입된 이름(%s)과 일치해야 합니다.", category1.getName())
                .isEqualTo(category1.getName());

        assertThat(dbCategory1.getParentId())
                .withFailMessage("첫번째 카테고리의 부모 ID는 null이어야 합니다.")
                .isNull();

        // 두번째 카테고리 검증
        Category dbCategory2 = categoryDao.selectCategoryById(category2.getId());
        assertThat(dbCategory2)
                .withFailMessage("두번째 카테고리(%s)는 null이 아니어야 합니다.", category2.getName())
                .isNotNull();

        assertThat(dbCategory2.getName())
                .withFailMessage("두번째 카테고리의 이름은 삽입된 이름(%s)과 일치해야 합니다.", category2.getName())
                .isEqualTo(category2.getName());

        assertThat(dbCategory2.getParentId())
                .withFailMessage("두번째 카테고리의 부모 ID는 첫번째 카테고리 ID(%d)와 일치해야 합니다.", category1.getId())
                .isEqualTo(category1.getParentId());
    }

    @Test
    @DisplayName("모든 카테고리 조회 테스트")
    void testSelectAllCategories() {
        List<Category> dbCategories = categoryDao.selectAll();

        // DB 카테고리 수가 테스트 카테고리 수보다 크거나 같아야 함
        assertThat(dbCategories)

                .withFailMessage("카테고리의 수(%d)가 비어 있으면 안됩니다.", dbCategories.size()).isNotEmpty()

                .withFailMessage("결과로 나온 카테고리 수(%d)가 테스트 카테고리 수(%d)보다 많아야 합니다.", dbCategories.size(), categories.size()).hasSizeGreaterThanOrEqualTo(categories.size())

                .withFailMessage("결과로 나온 카테고리 ID 목록이 테스트 카테고리 ID 목록을 포함해야 합니다.").extracting(Category::getId).containsAll(categories.stream().map(Category::getId).toList());
    }


    @Test
    @DisplayName("ID로 특정 카테고리 조회 테스트")
    void testSelectCategoryById() {
        // 위에서 인서트를 했으니 특정 ID로 조회하면 반드시 데이터가 있어야합니다.
        for (Category testCategory : categories) {
            Category dbCategory = categoryDao.selectCategoryById(testCategory.getId());

            assertThat(dbCategory).withFailMessage("카테고리 ID(%d)로 조회 시 null이 반환되지 않아야 합니다.", testCategory.getId()).isNotNull();
        }
    }

    @Test
    @DisplayName("이름으로 특정 카테고리 조회 테스트")
    void testSelectCategoryByName() {
        for (Category testCategory : categories) {
            Category dbCategory = categoryDao.selectCategoryByName(testCategory.getName());

            assertThat(dbCategory).as("카테고리 이름(%s)으로 조회 시 null이 반환되지 않아야 합니다.", testCategory.getName()).isNotNull();
        }
    }


    @Test
    @DisplayName("특정 카테고리의 직계 하위 카테고리 조회 테스트 (ID 기준)")
    void testSelectDirectSubCategoriesById() {
        Category thisCategory = dummyCategory;
        List<Category> subCategories = categoryDao.selectDirectSubCategoriesById(thisCategory.getId());
        assertThat(subCategories).as("%s 카테고리(%d)의 직계 자손이 비어있지 않아야 합니다.", thisCategory.getName(), thisCategory.getId()).isNotEmpty().as("%s 직계 자손이 1개 이상이어야 합니다.", thisCategory.getName()).hasSizeGreaterThanOrEqualTo(1);
    }

    @Test
    @DisplayName("특정 카테고리의 전체 하위 카테고리 조회 테스트 (ID 기준)")
    void testSelectAllSubCategories() {
        Category thisCategory = dummyCategory;
        List<Category> allSubCategories = categoryDao.selectAllSubCategories(thisCategory.getId());
        assertThat(allSubCategories).as("%s 카테고리의 전체 자손이 비어있지 않아야 합니다.", thisCategory.getName()).isNotEmpty().as("%s 카테고리의 전체 자손이 2개 이상이어야 합니다.", thisCategory.getName()).hasSizeGreaterThanOrEqualTo(2);

        // TODO, 특정 부모의 자손인지도 검사 해야하는데 복잡해서 생략 너낌 오시면 해주세여...
    }

    @Test
    @DisplayName("특정 카테고리의 직계 하위 카테고리 조회 테스트 (이름 기준)")
    void testSelectDirectSubCategoriesByIdByName() {
        Category thisCategory = dummyCategory;
        List<Category> subCategories = categoryDao.selectDirectSubCategoriesByName(thisCategory.getName());
        assertThat(subCategories).as("%s 카테고리의 직계 자손이 비어있지 않아야 합니다.", thisCategory.getName()).isNotEmpty().as("%s 직계 자손이 1개 이상이어야 합니다.", thisCategory.getName()).hasSizeGreaterThanOrEqualTo(1);
    }

    @Test
    @DisplayName("특정 카테고리의 전체 하위 카테고리 조회 테스트 (이름 기준)")
    void testSelectAllSubCategoriesByName() {
        Category thisCategory = dummyCategory;
        List<Category> allSubCategories = categoryDao.selectAllSubCategoriesByName(thisCategory.getName());
        assertThat(allSubCategories).as("%s 카테고리의 직계 자손이 비어있지 않아야 합니다.", thisCategory.getName()).isNotEmpty().as("%s 직계 자손이 1개 이상이어야 합니다.", thisCategory.getName()).hasSizeGreaterThanOrEqualTo(1);
    }

    @Test
    @DisplayName("자식이 있는 카테고리 삭제 시 예외가 발생해야 한다.")
    void testDeleteCategory() {
        Category thisCategory = dummyCategory;

        try {
            // 자식이 있는 카테고리 삭제 시도
            categoryDao.deleteCategory(thisCategory.getId());
            fail("자식이 있는 카테고리 삭제 시 예외가 발생해야 합니다."); // 예외가 발생하지 않으면 실패
        } catch (Exception e) {
            assertThat(e).withFailMessage("자식이 있는 카테고리 삭제 시 예외가 발생해야 합니다.").isNotNull();
        }
    }

    @Test
    @DisplayName("자식을 옮기고 카테고리 삭제 시 성공해야 한다.")
    void testDeleteCategory2() {
        Category parentCategory = dummyCategory;

        // 자식 카테고리의 부모를 다른 카테고리로 변경
        List<Category> subCategories = categoryDao.selectDirectSubCategoriesById(parentCategory.getId());

        for (Category subCategory : subCategories) {
            subCategory.setParentId(null); // 부모 제거 (최상위로 이동)
            categoryDao.updateCategory(subCategory);
        }

        // 자식을 옮긴 후 카테고리 삭제
        categoryDao.deleteCategory(parentCategory.getId());

        // 삭제된 카테고리 확인
        Category deletedCategory = categoryDao.selectCategoryById(parentCategory.getId());
        assertThat(deletedCategory).withFailMessage("삭제된 카테고리(%s)는 더 이상 DB에 존재하지 않아야 합니다.", deletedCategory).isNull();
    }

    @Test
    @DisplayName("카테고리 업데이트 테스트")
    void testUpdateCategory() {
        String newName = "수정된 찻잔";
        Category thisCategory = dummyCategory;
        thisCategory.setName(newName);

        categoryDao.updateCategory(thisCategory);
        Category updatedCategory = categoryDao.selectCategoryById(thisCategory.getId());

        assertThat(updatedCategory)
                .withFailMessage("업데이트된 카테고리(%s)는 null이 아니어야 합니다.", thisCategory.getName())
                .isNotNull();

        assertThat(updatedCategory.getName())
                .withFailMessage("업데이트된 카테고리의 이름은 수정된 이름(%s)과 일치해야 합니다.", thisCategory.getName())
                .isEqualTo(newName);

        assertThat(updatedCategory.getParentId())
                .withFailMessage("업데이트된 카테고리의 부모 ID는 변경되지 않아야 합니다.")
                .isEqualTo(thisCategory.getParentId());

        assertThat(updatedCategory.getCreatedDate())
                .withFailMessage("업데이트된 카테고리의 생성일은 변경되지 않아야 합니다.")
                .isEqualTo(thisCategory.getCreatedDate());

        assertThat(updatedCategory.getLastModifiedDate())
                .withFailMessage("업데이트된 카테고리의 수정일은 같거나 이후여야 합니다.")
                .isAfterOrEqualTo(thisCategory.getLastModifiedDate());

    }

}