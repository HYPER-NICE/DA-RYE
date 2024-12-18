package hyper.darye.controller;

import hyper.darye.dto.CartSelect;
import hyper.darye.service.CartService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(CartController.class)
class CartControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CartService cartService;

    @Test
    @DisplayName("장바구니 물품 담기 테스트")
    void addCartTest() throws Exception {
        // CartService Mock 동작 정의
        doReturn(1).when(cartService).insertCart( 1L, 1L, 6L);

        // MockMvc를 사용한 컨트롤러 테스트
        mockMvc.perform(post("/api/members") // 정확한 URL
                        .param("id", "1")
                        .param("productId", "1")
                        .param("quantity", "6"))
                .andExpect(status().isOk()) // HTTP 상태 200 확인
                .andExpect(content().string("성공")); // 응답 본문 확인
    }



    @Test
    @DisplayName("장바구니 조회 테스트")
    void searchCartTest() throws Exception {
        // Mock 데이터 준비
        List<CartSelect> searchCart = new ArrayList<>();
        searchCart.add(new CartSelect(1L, 1L, 1L, 10L,"과자", 10000, null)); // 필드 추가

        // Mock 설정
        doReturn(searchCart).when(cartService).selectCart(1L);

        // MockMvc 요청 및 검증
        mockMvc.perform(MockMvcRequestBuilders.get("/api/members/{id}/cart", 1L))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.size()").value(1)) // 결과 리스트 크기
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].cartId").value(1L))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].productId").value(1L))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].quantity").value(10))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].productPrice").value(10000));

        System.out.println(searchCart.toString());
    }
}
