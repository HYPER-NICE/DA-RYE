package hyper.darye.mapper;

import hyper.darye.dto.Product;
import hyper.darye.dto.ProductWithBLOBs;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Transactional
class ProductMapperTest {
    @Autowired
    private ProductMapper productMapper;


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
        int result = productMapper.insertProduct(product);

        // then
        assertThat(result).isEqualTo(1);
    }

    @Test
    @DisplayName("전체 상품 조회")
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

        productMapper.insertProduct(product);
        productMapper.insertProduct(product1);

        // when
        List<Product> p = productMapper.selectAllProduct();

        // then
        assertNotNull(p);
        assertEquals(12, p.size());
    }

    @Test
    @DisplayName("특정 ID의 상품을 조회")
    void selectByPrimaryKeyTest() {
        // given
        Long id = 27L;

        // when
        ProductWithBLOBs product = productMapper.selectByPrimaryKey(id);

        // then
        assertNotNull(product);
        assertEquals("세작", product.getName());
        assertEquals("신선하고 향긋한 어린 잎으로 만든 녹차", product.getShortDescription());
    }

    @Test
    @DisplayName("특정 ID에 등록된 상품이 없는 경우")
    void selectByPrimaryKey_WhenProductDoesNotExistTest() {
        // Given
        Long id = 999L;

        // When
        ProductWithBLOBs product = productMapper.selectByPrimaryKey(id);

        // Then
        assertNull(product);
    }
}