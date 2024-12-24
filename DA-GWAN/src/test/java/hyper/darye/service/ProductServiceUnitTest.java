package hyper.darye.service;

import hyper.darye.dto.Product;
import hyper.darye.dto.ProductWithBLOBs;
import hyper.darye.mapper.ProductMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.transaction.annotation.Transactional;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
@Transactional
class ProductServiceUnitTest {
    @Autowired
    private ProductService productService;

    @InjectMocks
    private ProductService productService1;

    @Mock
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
        int result = productService.insertProduct(productWithBLOBs);

        // then
        assertEquals(1, result);
    }

    @Test
    @DisplayName("상품 전체 조회")
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

        productService.insertProduct(productWithBLOBs);
        productService.insertProduct(productWithBLOBs1);

        List<ProductWithBLOBs> result = productService.selectAllProduct();
        assertEquals(17, result.size());
    }

    @Test
    @DisplayName("특정 ID의 상품 조회")
    void selectByPrimaryKeyTest() {
        ProductWithBLOBs pwb = productService.selectByPrimaryKey(1L);
        assertEquals("세작", pwb.getName());
    }

    @Test
    @DisplayName("특정 ID에 등록된 상품이 없는 경우 예외 발생")
    void selectByPrimaryKeyTest2() {
        Long id = 999L;

        // When & Then
        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> productService.selectByPrimaryKey(id)
        );

        // 예외 메시지가 예상 메시지와 일치하는지 검증
        assertEquals("조회할 상품이 없습니다.", exception.getMessage());
    }

    @Test
    @DisplayName("상품 정보 업데이트")
    void updateByPrimaryKeyTest() {
        ProductWithBLOBs pwb = productService.selectByPrimaryKey(13L);
        assertNotNull(pwb);

        pwb.setName("강남 순수 녹차");
        pwb.setPrice(80000);

        int result = productService.updateByPrimaryKey(pwb);
        assertEquals(1, result);

        ProductWithBLOBs pwb2 = productService.selectByPrimaryKey(13L);
        assertEquals("강남 순수 녹차", pwb2.getName());
        assertEquals(80000, pwb2.getPrice());
    }

    @Test
    @DisplayName("상품 정보 삭제 성공 - 선택 ID")
    void deleteByPrimaryKeyTest() {
        Long id = 1L;

        // Given
        ProductWithBLOBs product = new ProductWithBLOBs();
        product.setId(id);
        product.setProductStatusCodeId(1L); // 초기 상태 설정

        // When & Then
        productService.deleteByPrimaryKey(id, 4L);
        ProductWithBLOBs product1 = productService.selectByPrimaryKey(id);
        long result = product1.getProductStatusCodeId();
        assertEquals(4L, result);
    }

    @Test
    @DisplayName("상품 정보 삭제 실패 - 선택 ID가 없는 경우")
    void deleteByPrimaryKeyTest2() {
        Long id = 999L;

        // Given
        ProductWithBLOBs product = new ProductWithBLOBs();
        product.setId(1L);
        product.setProductStatusCodeId(1L); // 초기 상태 설정

        // When & Then
        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> productService.deleteByPrimaryKey(id, 4L) // 삭제 로직 실행
        );

        // 예외 메시지가 예상 메시지와 일치하는지 검증
        assertEquals("해당 ID의 상품이 존재하지 않습니다.", exception.getMessage());
    }

    @Test
    @DisplayName("상품 검색 테스트")
    void searchByKeywordTest() {
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

        productService.insertProduct(productWithBLOBs);
        productService.insertProduct(productWithBLOBs1);


        String keyword = "   es     ";
        Integer minPrice = 0;
        Integer maxPrice = 20000000;

        // when
        int result = productService.insertProduct(productWithBLOBs);
        assertEquals(1, result);

        List<ProductWithBLOBs> productList = productService.searchByKeyword(keyword, minPrice, maxPrice, 1);
        assertEquals(3, productList.size());
        assertEquals("test1", productList.get(0).getName());

    }

    @Test
    @DisplayName("상품 검색 (가격 설정X) 테스트")
    void searchByKeywordEmptyPriceTest() {
        // given
        ProductWithBLOBs product = new ProductWithBLOBs();
        product.setName("test");
        product.setShortDescription("test");
        product.setLongDescription("test");
        product.setPrice(10000);
        product.setCategoryId(1L);
        product.setProductStatusCodeId(1L);
        product.setManufacturer("test");
        product.setExpirationDate(new Date());
        product.setIngredients("test");
        product.setPrecautions("test");
        product.setImporter("test");
        product.setSaleDate(new Date());
        product.setCapacity(1);
        product.setUnit("test");
        product.setQuantity(2);

        String keyword = "   es     ";
        Integer minPrice = 100;
        Integer maxPrice = 20000;

        // when
        int result = productService.insertProduct(product);
        System.out.println("삽입된 결과: " + result);

        List<ProductWithBLOBs> productList = productService.searchByKeyword(keyword, null, null, 2);

        // 검색된 결과 확인
        System.out.println("검색된 상품 개수: " + productList.size());

        assertEquals(1, productList.size());
        assertEquals("test", productList.get(0).getName());
    }

}