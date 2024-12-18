package hyper.darye.service;

import hyper.darye.dto.Product;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Transactional
class ProductServiceTest {
    @Autowired
    private ProductService productService;

    @Test
    void insertProduct() {
        // given
        Product product = new Product();
        product.setName("test");
        product.setShortDescription("test");
        product.setLongDescription("test");
        product.setPrice(100);
        product.setCategoryId(1);
        product.setProductStatusCodeId(1);
        product.setManufacturer("test");
        product.setExpirationDate(LocalDateTime.now());
        product.setIngredients("test");
        product.setPrecautions("test");
        product.setImporter("test");
        product.setSaleDate(LocalDateTime.now());
        product.setCapacity(1);
        product.setUnit("test");
        product.setQuantity(2);

        // when
        int result = productService.insertProduct(product);

        // then
        assertEquals(1, result);
    }

}