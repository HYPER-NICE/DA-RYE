package hyper.darye.mapper;

import hyper.darye.dto.Cart;
import hyper.darye.dto.CartSelect;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Transactional
class CartMapperTest {

    @Autowired
    private CartMapper cartMapper;

    @Test
    @DisplayName("장바구니 넣기 테스트")
    void insertCartTest() {
        //given
        Cart cart = new Cart();
        cart.setMemberId(1L);
        cart.setProductId(1L);
        cart.setQuantity(10L);
        int result = 0;

        //when
        if(cartMapper.selectCartDuplicate(cart.getMemberId(), cart.getProductId()) != null) {
            result = cartMapper.updateCart(cart.getProductId(), cart.getMemberId(), cart.getQuantity());
        }
        else
            result = cartMapper.insertCart(cart);

        //then
        assertEquals(1, result);
    }

    @Test
    @DisplayName("장바구니 중복 넣기")
    void insertDuplicateTest(){
        //given
        Cart cart = new Cart();
        cart.setMemberId(1L);
        cart.setProductId(1L);
        cart.setQuantity(10L);
        int result = 0;

        //when
        if(cartMapper.selectCartDuplicate(cart.getMemberId(), cart.getProductId()) != null)
            cartMapper.updateCart(cart.getProductId(), cart.getMemberId(), cart.getQuantity()); // update
        else
            result = cartMapper.insertCart(cart); // insert

        List<CartSelect> cartSelect = cartMapper.selectCart(1L);

        assertEquals(1, cartSelect.size());
        assertEquals(20, cartSelect.get(0).getQuantity()); // 중복 update 확인
        System.out.println(cartSelect.get(0).getQuantity());
    }

    @Test
    @DisplayName("장바구니 전체 검색")
    void selectCartTest() {

        // when
        List<CartSelect> cartSelect = cartMapper.selectCart(1L);
        assertEquals(1, cartSelect.size());
        assertEquals(10000, cartSelect.get(0).getProductPrice());
        assertEquals("11", cartSelect.get(0).getProductName());
        assertEquals(10, cartSelect.get(0).getQuantity());
        assertEquals(1, cartSelect.get(0).getProductId());
        assertEquals(1, cartSelect.get(0).getMemberId());
        assertEquals(15, cartSelect.get(0).getCartId());
    }

    @Test
    @DisplayName("장바구니 삭제")
    void deleteCartTest(){

        Cart cart = new Cart();
        cart.setMemberId(2L);
        cart.setProductId(2L);
        cart.setQuantity(10L);

        cartMapper.insertCart(cart);

        List<Long> productIdList = new ArrayList<>();
        productIdList.add(2L); // productIdList에 product_id 삽입

        List<CartSelect> cartSelect = cartMapper.selectCart(2L); // 장바구니 조회
        assertEquals(1, cartSelect.size()); // 장바구니 조회 결과 1개 검색

        cartSelect = cartMapper.selectCart(2L);


        cartMapper.deleteCart(cartSelect.get(0).getMemberId(), productIdList);

        //then
        cartSelect = cartMapper.selectCart(2L);
        assertEquals(0, cartSelect.size());
    }

    @Test
    @DisplayName("장바구니 삭제")
    void ListdeleteCartTest(){

        Cart cart = new Cart();
        cart.setMemberId(1L);
        cart.setProductId(2L);
        cart.setQuantity(10L);

        cartMapper.insertCart(cart);

        List<Long> productIdList = new ArrayList<>();
        productIdList.add(2L); // productIdList에 product_id 삽입
        productIdList.add(1L);

        List<CartSelect> cartSelect = cartMapper.selectCart(1L); // 장바구니 member = 1L 조회
        assertEquals(2, cartSelect.size()); // 장바구니 조회 결과 2개 검색

        cartSelect = cartMapper.selectCart(1L);


        cartMapper.deleteCart(cartSelect.get(0).getMemberId(), productIdList);

        //then
        cartSelect = cartMapper.selectCart(1L);
        assertEquals(0, cartSelect.size());
    }

    @Test
    @DisplayName("장바구니 수량 변경")
    void updateCartTest(){
        cartMapper.updateCart(1L, 1L, 100L);

        assertEquals(100000, cartMapper.selectCart(1L).get(0).getQuantity());
    }

}