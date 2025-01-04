package hyper.darye.mapper;

import hyper.darye.mvc.mapper.ProductMapper;
import hyper.darye.mvc.model.entity.Product;
import hyper.darye.mvc.model.entity.ProductWithBLOBs;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.transaction.annotation.Transactional;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;


@MybatisTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Transactional
class ProductMapperUnitTest {
    @Autowired
    private ProductMapper productMapper;

    @Test
    @DisplayName("상품 등록")
    void insertProductTest() {

        // given
        ProductWithBLOBs productWithBLOBs = new ProductWithBLOBs();
        productWithBLOBs.setName("test");
        productWithBLOBs.setPrice(100);
        productWithBLOBs.setCategoryId(1L);
        productWithBLOBs.setProductStatusCodeId(1L);
        productWithBLOBs.setExpirationDate(new Date());
        productWithBLOBs.setSaleDate(new Date());
        productWithBLOBs.setQuantity(2);

        // when
        int result = productMapper.insertProduct(productWithBLOBs);

        // then
        assertThat(result).isEqualTo(1);
    }

    @Test
    @DisplayName("전체 상품 조회")
    void selectAllProductTest() {
        // given
        ProductWithBLOBs productWithBLOBs = new ProductWithBLOBs();
        productWithBLOBs.setId(1L);
        productWithBLOBs.setCategoryId(1L);
        productWithBLOBs.setProductStatusCodeId(1L);
        productWithBLOBs.setName("test1");
        productWithBLOBs.setPrice(100);
        productWithBLOBs.setExpirationDate(new Date());
        productWithBLOBs.setSaleDate(new Date());
        productWithBLOBs.setQuantity(2);

        ProductWithBLOBs productWithBLOBs1 = new ProductWithBLOBs();
        productWithBLOBs1.setId(2L);
        productWithBLOBs1.setCategoryId(1L);
        productWithBLOBs1.setProductStatusCodeId(1L);
        productWithBLOBs1.setName("test2");
        productWithBLOBs1.setPrice(200);
        productWithBLOBs1.setExpirationDate(new Date());
        productWithBLOBs1.setSaleDate(new Date());
        productWithBLOBs1.setQuantity(10);

        productMapper.insertProduct(productWithBLOBs);
        productMapper.insertProduct(productWithBLOBs);

        // when
        List<ProductWithBLOBs> p = productMapper.selectAllProduct();

        // then
        assertNotNull(p);
        assertEquals(17, p.size());
    }

    @Test
    @DisplayName("특정 ID의 상품을 조회")
    void selectByPrimaryKeyTest() {
        // given
        Long id = 1L;

        // when
        ProductWithBLOBs productWithBLOBs = productMapper.selectByPrimaryKey(id);

        // then
        assertNotNull(productWithBLOBs);
        assertEquals("세작", productWithBLOBs.getName());
        assertEquals("신선하고 향긋한 어린 잎으로 만든 녹차", productWithBLOBs.getShortDescription());
    }

    @Test
    @DisplayName("특정 ID에 등록된 상품이 없는 경우")
    void selectByPrimaryKey_WhenProductDoesNotExistTest() {
        // Given
        Long id = 999L;

        // When
        ProductWithBLOBs productWithBLOBs = productMapper.selectByPrimaryKey(id);

        // Then
        assertNull(productWithBLOBs);
    }

    @Test
    @DisplayName("상품 정보 업데이트")
    void updateByPrimaryKeyTest() {
        Long id = 11L;
        ProductWithBLOBs productWithBLOBs = productMapper.selectByPrimaryKey(id);
        assertNotNull(productWithBLOBs);

        productWithBLOBs.setName("가짜세작");
        productWithBLOBs.setPrice(10000);

        int result = productMapper.updateByPrimaryKey(productWithBLOBs);

        assertEquals(1, result);
        ProductWithBLOBs updateProduct = productMapper.selectByPrimaryKey(id);
        assertEquals("가짜세작", updateProduct.getName());
        assertEquals(10000, updateProduct.getPrice());
    }

    @Test
    @DisplayName("상품 정보 삭제 - 선택 ID")
    void deleteByPrimaryKeyTest() {
        Long id = 1L;
        ProductWithBLOBs productWithBLOBs = productMapper.selectByPrimaryKey(id);
        assertNotNull(productWithBLOBs);

        int result = productMapper.updateProductStatus(id, 4L);
        assertEquals(1, result);
        Product deleteProduct = productMapper.selectByPrimaryKey(id);

        assertEquals(4, deleteProduct.getProductStatusCodeId());
    }

    @Test
    @DisplayName("상품 키워드 검색 테스트")
    void searchByKeywordTest() {
        String keyword = "제주 차";
        List<String> wordList = Arrays.asList(keyword.split(" "));
        List<ProductWithBLOBs> productList = productMapper.searchByKeyword(wordList, 0, 45000, null);

        // 검색 결과 출력
        System.out.println("검색 결과:");
        productList.forEach(product -> {
            System.out.println("상품명: " + product.getName() + ", 가격: " + product.getPrice());
        });

        assertNotNull(productList);
        assertFalse(productList.isEmpty(), "검색 결과가 비어 있습니다.");
    }

    @Test
    @DisplayName("상품 카테고리 이름 검색 테스트")
    void searchByCategoryNameTest() {
        String keyword = "잎차";
        List<String> wordList = Arrays.asList(keyword.split(" "));
        List<ProductWithBLOBs> productList = productMapper.searchByKeyword(wordList, 0, 45000, null);

        // 검색 결과 출력
        System.out.println("검색 결과:");
        productList.forEach(product -> {
            System.out.println("상품명: " + product.getName() + ", 가격: " + product.getPrice());
        });

        assertNotNull(productList);
        assertFalse(productList.isEmpty(), "검색 결과가 비어 있습니다.");
    }
}