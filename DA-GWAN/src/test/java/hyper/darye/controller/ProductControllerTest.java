package hyper.darye.controller;

import hyper.darye.dto.Product;
import hyper.darye.security.SecurityConfig;
import hyper.darye.service.ProductService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(SignController.class)
@Import(SecurityConfig.class)
class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService productService;

    @Test
    void insertProduct() throws Exception {
        // given
        given(productService.insertProduct(any(Product.class))).willReturn(1);
        String expirationDate = LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        String saleDate = LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);

        // when
        mockMvc.perform(post("/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("id", "1")
                        .param("categoryId", "1")
                        .param("productStatusCodeId", "1")
                        .param("name", "title")
                        .param("price", "100")
                        .param("expirationDate", expirationDate)
                        .param("SaleDate", saleDate)
                        .param("quantity", "2"))
                // then
                .andExpect(status().isCreated())
                .andExpect(content().string("success"));
    }
}