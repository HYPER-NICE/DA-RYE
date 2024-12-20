package hyper.darye.service;

import hyper.darye.dto.CartSelect;
import hyper.darye.mapper.CartMapper;
import hyper.darye.mapper.ForeignKeyMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
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

    @Autowired
    private ForeignKeyMapper fkMapper;

    @BeforeEach
    void disableForeignKeyChecks() {
        fkMapper.disableForeignKeyChecks();
    }

    @AfterEach
    void enableForeignKeyChecks() {
        fkMapper.enableForeignKeyChecks();
    }

    @Test
    @DisplayName("장바구니 담기 테스트")
    void insertCartTest() {
        int result = cartService.insertCart(100L, 100L, 10L);
        List<CartSelect> cartSelect = cartMapper.selectCart(100L);

        assertEquals(1, cartSelect.size());
        assertEquals(1, result);
    }

    @Test
    @DisplayName("장바구니 중복 담기 테스트(update)")
    void insertDuplicateTest(){
        cartService.insertCart(100L, 100L, 10L);
        cartService.insertCart(100L, 100L, 10L);
        List<CartSelect> cartSelect = cartMapper.selectCart(100L);

        assertEquals(1, cartSelect.size());
        assertEquals(20, cartSelect.get(1).getQuantity());
    }

    @Test
    @DisplayName("장바구니 조회 테스트")
    void selectCartTest(){
        int result = cartService.insertCart(100L, 100L, 10L);
        List<CartSelect> cartSelect = cartService.selectCart(100L);
        // then

        assertNotNull(cartSelect); // null이 아닌지 확인
        assertFalse(cartSelect.isEmpty()); // 비어있지 않은지 확인
        assertEquals(100L, cartSelect.get(0).getMemberId()); // memberId가 100L인지 확인

        CartSelect firstCart = cartSelect.get(1);
        assertEquals(100L, firstCart.getProductId()); // productId가 100L인지 확인
        assertEquals(100L, firstCart.getQuantity()); // quantity가 100L인지 확인

    }

    @Test
    @DisplayName("장바구니 수량 변경")
    void updateCartTest(){
        cartService.insertCart(100L, 100L, 10L);
        cartService.updateCartQuantity(100L, 100L, 100L);
        assertEquals(100, cartMapper.selectCart(100L).get(0).getQuantity());
    }
}