import hyper.darye.model.entity.Cart;
import hyper.darye.mapper.CartMapper;
import hyper.darye.service.CartService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class CartServiceTest {

    @InjectMocks
    private CartService cartService;  // CartService를 자동으로 주입

    @Mock
    private CartMapper cartMapper;  // CartMapper를 모킹

    @Test
    @DisplayName("장바구니 담기 테스트")
    void insertCartTest() {
        Long memberId = 1L;
        Long productId = 2L;
        Long quantity = 5L;

        // Cart 객체 생성 및 설정
        Cart cart = new Cart();
        cart.setMemberId(memberId);
        cart.setProductId(productId);
        cart.setQuantity(quantity);

        // selectCartDuplicate() 메서드가 null을 반환한다고 가정
        when(cartMapper.selectCartDuplicate(memberId, productId)).thenReturn(null);

        // insertCart 메서드가 호출되면 1을 반환한다고 가정
        when(cartMapper.insertCart(cart)).thenReturn(1);

        // 서비스 메서드 호출
        int result = cartService.insertCart(memberId, productId, quantity);

        // 결과 검증
        assertEquals(1, result);

        // selectCartDuplicate와 insertCart가 각각 한 번씩 호출되었는지 검증
        verify(cartMapper, times(1)).selectCartDuplicate(memberId, productId);
        verify(cartMapper, times(1)).insertCart(cart);
    }



    @Test
    @DisplayName("장바구니 삭제 테스트")
    void deleteCartTest() {
        Long memberId = 1L;
        List<Long> productIdList = List.of(2L, 3L);

        // deleteCart 메서드가 2를 반환하도록 설정
        when(cartMapper.deleteCart(memberId, productIdList)).thenReturn(2);

        // 서비스 메서드 호출
        int result = cartService.deleteCart(memberId, productIdList);

        // 결과 검증
        assertEquals(2, result);

        // deleteCart 메서드가 한 번 호출되었는지 검증
        verify(cartMapper, times(1)).deleteCart(memberId, productIdList);
    }

    @Test
    @DisplayName("장바구니 수량 변경 테스트")
    void updateCartTest() {
        Long memberId = 1L;
        Long productId = 2L;
        Long quantity = 10L;

        // updateCart 메서드가 1을 반환하도록 설정
        when(cartMapper.updateCart(memberId, productId, quantity)).thenReturn(1);

        // 서비스 메서드 호출
        int result = cartService.updateCart(memberId, productId, quantity);

        // 결과 검증
        assertEquals(1, result);

        // updateCart 메서드가 한 번 호출되었는지 검증
        verify(cartMapper, times(1)).updateCart(memberId, productId, quantity);
    }

    @Test
    @DisplayName("중복 장바구니 수량 변경 테스트")
    void updateCartQuantityTest() {
        Long memberId = 1L;
        Long productId = 2L;
        Long quantity = 3L;

        // updateCartQuantity 메서드가 1을 반환하도록 설정
        when(cartMapper.updateCartQuantity(memberId, productId, quantity)).thenReturn(1);

        // 서비스 메서드 호출
        int result = cartService.updateCartQuantity(memberId, productId, quantity);

        // 결과 검증
        assertEquals(1, result);

        // updateCartQuantity 메서드가 한 번 호출되었는지 검증
        verify(cartMapper, times(1)).updateCartQuantity(memberId, productId, quantity);
    }
}
