package hyper.darye.controller;

import hyper.darye.dto.Product;
import hyper.darye.service.ProductService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
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
    void selectProduct() throws Exception {

    }
}