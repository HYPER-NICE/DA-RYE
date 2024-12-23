package hyper.darye.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import hyper.darye.dto.controller.request.SelectCartRequest;
import hyper.darye.security.SecurityConfig;
import hyper.darye.service.CartService;
import hyper.darye.testConfig.mockUser.WithMockCustomUser;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(CartController.class)
@Import(SecurityConfig.class)
class CartControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private CartService cartService;


    @Nested
    @DisplayName("장바구니 담기 테스트")
    class insertCartTest {

        @Test
        @DisplayName("비인증 사용자 상태에서 장바구니 담기 테스트")
        @WithAnonymousUser
        public void UnauthorizedTest() throws Exception {
            mockMvc.perform(post("/api/members/1/cart")
                            .param("productId", "1")
                            .param("quantity", "10")
                            .with(csrf()))
                    .andExpect(status().isForbidden());
        }

        @Test
        @DisplayName("사용자 상태에서 장바구니 담기 성공 테스트")
        @WithMockCustomUser(id = 1L, username = "user@darye.dev")
        public void AuthorizedTest() throws Exception {
            // Mock 설정
            when(cartService.insertCart(1L, 1L, 10L)).thenReturn(1);

            mockMvc.perform(post("/api/members/1/cart")
                            .param("productId", "1")
                            .param("quantity", "10")
                            .with(csrf()))
                    .andExpect(status().isOk())
                    .andExpect(content().string("성공"));
        }

        @Test
        @DisplayName("사용자 상태에서 장바구니 담기 실패 테스트")
        @WithMockCustomUser(id = 1L, username = "user@darye.dev")
        public void AuthorizedFailedTest() throws Exception {
            // Mock 설정
            when(cartService.insertCart(1L, 1L, 10L)).thenReturn(0); // 실패 상황 모의

            mockMvc.perform(post("/api/members/1/cart")
                            .param("productId", "1")
                            .param("quantity", "10") // 누락된 필드 추가
                            .with(csrf()))
                    .andExpect(status().isBadRequest()) // 400 Bad Request 확인
                    .andExpect(result -> assertTrue(result.getResolvedException() instanceof IllegalStateException)) // 예외 타입 확인
                    .andExpect(result -> assertEquals("입력 실패했습니다.", result.getResolvedException().getMessage())); // 메시지 확인
        }
    }

    @Nested
    @DisplayName("장바구니 조회 테스트")
    class SelectCartTest {

        @Test
        @DisplayName("비인증 사용자 상태에서 장바구니 조회 테스트")
        @WithAnonymousUser
        public void UnauthorizedSelectTest() throws Exception {
            // Mock 데이터 준비
            List<SelectCartRequest> searchCart = new ArrayList<>();
            searchCart.add(new SelectCartRequest(1L, 1L, 1L, 10L, "과자", 10000)); // 필드 추가

            when(cartService.selectCart(1L)).thenReturn(searchCart);
            mockMvc.perform(post("/api/members/1/cart")
                            .param("productId", "1")
                            .param("quantity", "10")
                            .with(csrf()))
                    .andExpect(status().isForbidden());
        }

        @Test
        @DisplayName("사용자 상태에서 장바구니 조회 테스트")
        @WithMockCustomUser(id = 1L, username = "user@darye.dev")
        void selectCartByMemberIdTest() throws Exception {
            // Mock 데이터 준비
            List<SelectCartRequest> searchCart = new ArrayList<>();
            searchCart.add(new SelectCartRequest(1L, 1L, 1L, 10L, "과자", 10000)); // 필드 추가

            // Mock 설정
            doReturn(searchCart).when(cartService).selectCart(1L);
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
        @DisplayName("사용자 상태에서 비어있는 장바구니 조회 테스트")
        @WithMockCustomUser(id = 1L, username = "user@darye.dev")
        void selectCartByMemberIdEmptyTest() throws Exception {
            // Mock 데이터 준비
            List<SelectCartRequest> searchCart = new ArrayList<>();
            // Mock 설정
            doReturn(searchCart).when(cartService).selectCart(1L);

            // MockMvc 요청 및 검증
            mockMvc.perform(get("/api/members/1/cart", 1L))
                    .andExpect(status().isOk())
                    .andExpect(content().json("[]"));// 빈 리스트([])가 반환되어야 함
        }

    }

    @Nested
    @DisplayName("장바구니 선택 삭제 테스트")
    class DeleteCartTest {
        @Test
        @DisplayName("비인증 사용자 상태에서 장바구니 삭제 테스트")
        @WithAnonymousUser
        public void UnauthorizedDeleteTest() throws Exception {
            // given
            List<Long> productIdList = new ArrayList<>();
            productIdList.add(1L); // 삭제할 productId 추가
            productIdList.add(2L); // 삭제할 productId 추가

            // when
            when(cartService.deleteCart(1L, productIdList)).thenReturn(3);

            //then
            mockMvc.perform(delete("/api/members/1/cart", 1L)
                            .param("productIdList", "1", "2")
                            .with(csrf()))
                    .andExpect(status().isForbidden());
        }

        @Test
        @WithMockCustomUser(id = 1L, username = "user@darye.dev")
        @DisplayName("사용자 상태에서 장바구니 선택 삭제 테스트")
        void deleteCartTest() throws Exception {
            // given
            List<Long> productIdList = new ArrayList<>();
            productIdList.add(1L); // 삭제할 productId 추가
            productIdList.add(2L); // 삭제할 productId 추가
            // Mock 설정
            when(cartService.insertCart(1L, 1L, 10L)).thenReturn(1);
            when(cartService.insertCart(1L, 2L, 10L)).thenReturn(2);
            when(cartService.deleteCart(1L, productIdList)).thenReturn(3);

            // when & then
                mockMvc.perform(delete("/api/members/1/cart", 1L)
                                .param("productIdList", "1", "2")
                                .with(csrf()))
                    .andExpect(status().isOk()) // HTTP 상태 코드가 200 OK여야 함
                    .andExpect(content().string("3")) // 성공하면 반환 값이 3이어야 함
                    .andDo(print()); // 응답 출력 (디버깅 용도)
        }

        @Test
        @WithMockCustomUser(id = 1L, username = "user@darye.dev")
        @DisplayName("사용자 상태에서 비어있는 장바구니 선택 삭제 테스트")
        void deleteCartEmptyTest() throws Exception {
            // given
            List<Long> productIdList = new ArrayList<>();

            when(cartService.deleteCart(1L, productIdList)).thenReturn(0);

            // when & then
            mockMvc.perform(delete("/api/members/1/cart", 1L)
                            .param("productIdList", "")
                            .with(csrf()))
                    .andExpect(status().isOk()) // HTTP 상태 코드가 200 OK여야 함
                    .andExpect(content().string("0")) // 성공하면 반환 값이 3이어야 함
                    .andDo(print()); // 응답 출력 (디버깅 용도)
        }
    }

    @Nested
    @DisplayName("장바구니 수량 변경 테스트")
    class UpdateCartTest {

        @Test
        @DisplayName("비인증 사용자 상태에서 장바구니 수량 변경 성공 테스트")
        @WithAnonymousUser
        void UnauthorizedUpdateCartTest() throws Exception {
            // given
            Long memberId = 1L;
            Long productId = 2L;
            Long quantity = 5L;

            // Mock 설정 (성공 시 1 반환)
            when(cartService.updateCartQuantity(memberId, productId, quantity)).thenReturn(1);

            // when & then
            mockMvc.perform(patch("/api/members/1/cart/2", memberId, productId)
                            .param("quantity", String.valueOf(quantity))
                            .with(csrf()))  // PATCH 요청
                    .andExpect(status().isForbidden());
        }

        @Test
        @DisplayName("사용자 상태에서 장바구니 수량 변경 성공 테스트")
        @WithMockCustomUser(id = 1L, username = "user@darye.dev")
        void updateCartTest() throws Exception {
            // given
            Long memberId = 1L;
            Long productId = 2L;
            Long quantity = 5L;

            // CartService의 updateCartQuantity 메서드를 Mock 처리 (성공 시 1 반환)
            when(cartService.updateCartQuantity(memberId, productId, quantity)).thenReturn(1);  // 성공 시 1을 반환하도록 설정

            // when & then
            mockMvc.perform(patch("/api/members/1/cart/2", memberId, productId)
                            .param("quantity", String.valueOf(quantity))
                            .with(csrf()))  // PATCH 요청
                    .andExpect(status().isOk())  // 상태 코드가 200이어야 함
                    .andExpect(content().string("성공"))  // 응답 본문이 "1"이어야 함
                    .andDo(print());  // 디버깅을 위한 응답 출력
        }
    }


}

