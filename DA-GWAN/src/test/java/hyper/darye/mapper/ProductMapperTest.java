package hyper.darye.mapper;

import hyper.darye.dto.Product;
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
    void selectProductTest() {
        // 더미데이터 넣고 돌려야 통과됨
        // given
        Product product = new Product();
        List<Product> products = productMapper.selectAllProduct();
        assertEquals(products.size(), 10);

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

        productMapper.insertProduct(product);

        // when
        List<Product> p = productMapper.selectAllProduct();

        // then
        assertNotNull(p);
        assertEquals(p.size(), 11);
        Product pro1 = p.get(10);
        assertEquals(100, pro1.getPrice());
        assertEquals("test", pro1.getName());


    }
}