package hyper.darye.service;

import hyper.darye.dto.CartSelect;
import hyper.darye.mapper.CartMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class CartServiceTest {

    @Autowired
    CartService cartService;

    @Autowired
    CartMapper cartMapper;

    @Test
    @DisplayName("장바구니 담기 테스트")
    void insertCartTest() {
        int result = cartService.insertCart(1L, 1L, 10L);
        List<CartSelect> cartSelect = cartMapper.selectCart(1L);

        assertEquals(1, cartSelect.size());
        assertEquals(1, result);
    }

    @Test
    @DisplayName("장바구니 중복 담기 테스트(update)")
    void insertDuplicateTest(){
        cartService.insertCart(1L, 1L, 10L);
        cartService.insertCart(1L, 1L, 10L);
        List<CartSelect> cartSelect = cartMapper.selectCart(1L);

        assertEquals(1, cartSelect.size());
        assertEquals(30, cartSelect.get(0).getQuantity());
    }

    @Test
    @DisplayName("장바구니 조회 테스트")
    void selectCartTest(){
        int result = cartService.insertCart(2L, 2L, 10L);
        List<CartSelect> cartSelect = cartService.selectCart(2L);


    }

    @Test
    @DisplayName("장바구니 조회 테스트")
    void deleteCartTest(){
        List<CartSelect> cartSelect = cartService.selectCart(1L);
        assertEquals(10000, cartSelect.get(0).getProductPrice());
        assertEquals("11", cartSelect.get(0).getProductName());
        assertEquals(10, cartSelect.get(0).getQuantity());
        assertEquals(1, cartSelect.get(0).getProductId());
        assertEquals(1, cartSelect.get(0).getMemberId());
        assertEquals(15, cartSelect.get(0).getCartId());

    }

    @Test
    @DisplayName("장바구니 수량 변경")
    void updateCartTest(){
        cartService.updateCart(1L, 1L, 100L);
        assertEquals(100, cartMapper.selectCart(1L).get(0).getQuantity());
    }
}