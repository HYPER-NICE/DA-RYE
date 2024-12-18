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

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
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
        mockMvc.perform(get("/api/members/{id}/cart", 1L))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.size()").value(1)) // 결과 리스트 크기
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].cartId").value(1L))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].productId").value(1L))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].quantity").value(10))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].productPrice").value(10000));

        System.out.println(searchCart.toString());
    }

    @Test
    @DisplayName("장바구니 선택 삭제 테스트")
    void deleteCartTest() throws Exception {
        // given
        List<Long> productIdList = new ArrayList<>();
        productIdList.add(6L); // 삭제할 productId 추가

        // CartService의 deleteCart 메서드를 Mock 처리 (가정: 반환 값은 1)
        when(cartService.deleteCart(3L, productIdList)).thenReturn(1);

        // when & then
        mockMvc.perform(get("/api/members")
                        .param("memberId", "3")
                        .param("productIdList", "6"))
                .andExpect(status().isOk()) // HTTP 상태 코드가 200 OK여야 함
                .andExpect(content().string("1")) // 성공하면 반환 값이 1이어야 함
                .andDo(print()); // 응답 출력 (디버깅 용도)
    }

    @Test
    @DisplayName("장바구니 수량 변경 성공 테스트")
    void updateCartTest() throws Exception {
        // given
        Long memberId = 1L;
        Long productId = 2L;
        Long quantity = 5L;

        // CartService의 updateCartQuantity 메서드를 Mock 처리 (성공 시 1 반환)
        when(cartService.updateCartQuantity(memberId, productId, quantity)).thenReturn(1);  // 성공 시 1을 반환하도록 설정

        // when & then
        mockMvc.perform(MockMvcRequestBuilders.patch("/api/members") // PATCH 요청
                        .param("id", String.valueOf(memberId))
                        .param("productId", String.valueOf(productId))
                        .param("quantity", String.valueOf(quantity)))
                .andExpect(status().isOk())  // 상태 코드가 200이어야 함
                .andExpect(content().string("성공"))  // 응답 본문이 "성공"이어야 함
                .andDo(print());  // 디버깅을 위한 응답 출력
    }


}
