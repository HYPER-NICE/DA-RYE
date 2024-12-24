package hyper.darye.service;

import hyper.darye.dto.Product;
import hyper.darye.dto.ProductWithBLOBs;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
@Transactional
class ProductServiceTest {
    @Autowired
    private ProductService productService;

    @Test
    @DisplayName("상품 등록")
    void insertProductTest() {
        // given
        Product product = new Product();
        product.setName("test");
        product.setShortDescription("test");
        product.setLongDescription("test");
        product.setPrice(100);
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

        // when
        int result = productService.insertProduct(product);

        // then
        assertEquals(1, result);
    }

    @Test
    @DisplayName("상품 전체 조회")
    void selectAllProductTest() {
        // given
        Product product = new Product();
        product.setId(1L);
        product.setCategoryId(1L);
        product.setProductStatusCodeId(1L);
        product.setName("test1");
        product.setPrice(100);
        product.setExpirationDate(new Date());
        product.setSaleDate(new Date());
        product.setQuantity(2);

        Product product1 = new Product();
        product1.setId(2L);
        product1.setCategoryId(1L);
        product1.setProductStatusCodeId(1L);
        product1.setName("test2");
        product1.setPrice(200);
        product1.setExpirationDate(new Date());
        product1.setSaleDate(new Date());
        product1.setQuantity(10);

        productService.insertProduct(product);
        productService.insertProduct(product1);

        List<Product> result = productService.selectAllProduct();
        assertEquals(12, result.size());
    }

    @Test
    @DisplayName("특정 ID의 상품 조회")
    void selectByPrimaryKeyTest() {
        ProductWithBLOBs pwb = productService.selectByPrimaryKey(12L);
        assertEquals("우전", pwb.getName());
    }

    @Test
    @DisplayName("특정 ID에 등록된 상품이 없는 경우")
    void selectByPrimaryKey2Test() {
        ProductWithBLOBs pwb = productService.selectByPrimaryKey(999L);
        assertNull(pwb);
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
    @DisplayName("상품 검색 테스트")
    void searchByKeywordTest() {
        // given
        Product product = new Product();
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

        List<Product> productList = productService.searchByKeyword(keyword, minPrice, maxPrice, 2);
        assertEquals(1, productList.size());
        assertEquals("test", productList.get(0).getName());

    }

    @Test
    @DisplayName("상품 검색 (가격 설정X) 테스트")
    void searchByKeywordEmptyPriceTest() {
        // given
        Product product = new Product();
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

        List<Product> productList = productService.searchByKeyword(keyword, null, null, 2);

        // 검색된 결과 확인
        System.out.println("검색된 상품 개수: " + productList.size());

        assertEquals(1, productList.size());
        assertEquals("test", productList.get(0).getName());
    }

}