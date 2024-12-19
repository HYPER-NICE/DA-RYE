package hyper.darye.service;

import hyper.darye.dto.Product;
import hyper.darye.dto.ProductWithBLOBs;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@SpringBootTest
@Transactional
class ProductServiceTest {
    @Autowired
    private ProductService productService;

    @Test
    @DisplayName("상품 등록")
    void insertProduct() {
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
    void selectAllProduct() {
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
    void selectProductById() {
        ProductWithBLOBs pwb = productService.selectByPrimaryKey(28L);
        assertEquals("우전", pwb.getName());
    }

    @Test
    @DisplayName("특정 ID에 등록된 상품이 없는 경우")
    void selectProductById2() {
        ProductWithBLOBs pwb = productService.selectByPrimaryKey(999L);
        assertNull(pwb);
    }
}