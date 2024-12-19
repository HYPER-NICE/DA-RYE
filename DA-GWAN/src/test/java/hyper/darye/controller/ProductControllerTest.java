package hyper.darye.controller;

import hyper.darye.dto.Product;
import hyper.darye.dto.ProductWithBLOBs;
import hyper.darye.service.ProductService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ProductController.class)
class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService productService;

    @Test
    @DisplayName("상품 등록")
    void insertProduct() throws Exception {
        // given
        given(productService.insertProduct(any(Product.class))).willReturn(1);

        String expirationDate = LocalDate.now().format(DateTimeFormatter.ISO_LOCAL_DATE);
        String saleDate = LocalDate.now().format(DateTimeFormatter.ISO_LOCAL_DATE);

        String jsonContent = """
        {
            "id": 1,
            "categoryId": 1,
            "productStatusCodeId": 1,
            "name": "title",
            "price": 100,
            "expirationDate": "%s",
            "saleDate": "%s",
            "quantity": 2
        }
        """.formatted(expirationDate, saleDate);

        // when
        mockMvc.perform(post("/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonContent))
                // then
                .andExpect(status().isCreated())
                .andExpect(content().string("success"));
    }

    @Test
    @DisplayName("상품 전체 조회")
    void selectAllProduct() throws Exception {
        // given
        Product product = new Product();
        product.setId(11L);
        product.setCategoryId(1L);
        product.setProductStatusCodeId(1L);
        product.setName("test");
        product.setPrice(100);
        product.setExpirationDate(new Date());
        product.setSaleDate(new Date());
        product.setQuantity(20);

        Product product1 = new Product();
        product1.setId(22L);
        product1.setCategoryId(2L);
        product1.setProductStatusCodeId(1L);
        product1.setName("test2");
        product1.setPrice(500);
        product1.setExpirationDate(new Date());
        product1.setSaleDate(new Date());
        product1.setQuantity(100);

        Product product2 = new Product();
        product2.setId(33L);
        product2.setCategoryId(3L);
        product2.setProductStatusCodeId(1L);
        product2.setName("test3");
        product2.setPrice(5000);
        product2.setExpirationDate(new Date());
        product2.setSaleDate(new Date());
        product2.setQuantity(500);

        // Mock: 서비스 계층의 동작을 정의
        List<Product> products = List.of(product, product1, product2);
        when(productService.selectAllProduct()).thenReturn(products);

        // When & Then: MockMvc를 사용해 테스트
        mockMvc.perform(get("/products")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()) // 상태 코드 200 확인
                .andExpect(jsonPath("$.length()").value(3))
                .andExpect(jsonPath("$[0].id", is(11)))
                .andExpect(jsonPath("$[2].id", is(33)));
    }

    @Test
    @DisplayName("특정 ID의 상품 조회")
    void selectProductById() throws Exception {
        // given
        ProductWithBLOBs product = new ProductWithBLOBs();
        product.setId(11L);
        product.setCategoryId(1L);
        product.setProductStatusCodeId(1L);
        product.setName("test");
        product.setPrice(100);
        product.setExpirationDate(new Date());
        product.setSaleDate(new Date());
        product.setQuantity(20);

        ProductWithBLOBs product1 = new ProductWithBLOBs();
        product1.setId(22L);
        product1.setCategoryId(2L);
        product1.setProductStatusCodeId(1L);
        product1.setName("test2");
        product1.setPrice(500);
        product1.setExpirationDate(new Date());
        product1.setSaleDate(new Date());
        product1.setQuantity(100);

        ProductWithBLOBs product2 = new ProductWithBLOBs();
        product2.setId(33L);
        product2.setCategoryId(3L);
        product2.setProductStatusCodeId(1L);
        product2.setName("test3");
        product2.setPrice(5000);
        product2.setExpirationDate(new Date());
        product2.setSaleDate(new Date());
        product2.setQuantity(500);

        // Mock: 서비스 계층의 동작을 정의
        List<Product> products = List.of(product, product1, product2);
        when(productService.selectByPrimaryKey(33L))
                .thenReturn((ProductWithBLOBs) products.stream().filter(p -> p.getId().equals(33L)).findFirst().orElse(null));

        // When & Then: MockMvc를 사용해 테스트
        mockMvc.perform(get("/products/{id}", 33L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(33L)) // 반환된 상품의 ID 확인
                .andExpect(jsonPath("$.name").value("test3")) // 반환된 상품의 이름 확인
                .andExpect(jsonPath("$.price").value(5000)) // 반환된 상품의 가격 확인
                .andExpect(jsonPath("$.quantity").value(500)); // 반환된 상품의 수량 확인
    }
}