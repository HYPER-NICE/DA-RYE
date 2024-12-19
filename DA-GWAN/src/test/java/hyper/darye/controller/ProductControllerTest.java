package hyper.darye.controller;

import hyper.darye.dto.Product;
import hyper.darye.service.ProductService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
        product.setId(45L);
        product.setCategoryId(1L);
        product.setProductStatusCodeId(1L);
        product.setName("test");
        product.setPrice(100);
        product.setExpirationDate(new Date());
        product.setSaleDate(new Date());
        product.setQuantity(2);

        // Mock: 서비스 계층의 동작을 정의
        List<Product> products = List.of(product);
        when(productService.selectAllProduct()).thenReturn(products);

        // When & Then: MockMvc를 사용해 테스트
        mockMvc.perform(get("/products")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()) // 상태 코드 200 확인
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id", is(45)));
    }
}