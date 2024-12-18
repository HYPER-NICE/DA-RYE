package hyper.darye.mapper;

import hyper.darye.dto.Cart;
import hyper.darye.dto.CartSelect;
import hyper.darye.dto.Product;
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
        cart.setMemberId(5L);
        cart.setProductId(5L);
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
    @DisplayName("장바구니 중복 처리 테스트")
    void insertDuplicateTest() {
        Cart cart = new Cart();
        cart.setMemberId(7L);
        cart.setProductId(7L);
        cart.setQuantity(10L);

        int result = 0;
        Cart duplicateCart = cartMapper.selectCartDuplicate(cart.getMemberId(), cart.getProductId());

        if (duplicateCart != null) {
            result = cartMapper.updateCart(cart.getProductId(), cart.getMemberId(), cart.getQuantity());
        } else {
            result = cartMapper.insertCart(cart);
        }

        List<CartSelect> cartSelect = cartMapper.selectCart(7L);
        assertEquals(1, cartSelect.size());
        assertEquals(12, cartSelect.get(0).getQuantity()); // 기존 2 + 추가 10
    }


    @Test
    @DisplayName("장바구니 데이터 조회 테스트")
    void selectCartTest() {
        // given
        Cart cart1 = new Cart();
        cart1.setMemberId(7L);
        cart1.setProductId(9L);
        cart1.setQuantity(2L);
        cartMapper.insertCart(cart1);

        Cart cart2 = new Cart();
        cart2.setMemberId(7L);
        cart2.setProductId(8L);
        cart2.setQuantity(3L);
        cartMapper.insertCart(cart2);

        // when
        List<CartSelect> cartList = cartMapper.selectCart(7L); // member_id가 3인 데이터 조회

        // then
        assertEquals(3, cartList.size()); // 두 개의 데이터가 존재해야 함

        // 첫 번째 데이터 검증
        CartSelect firstCart = cartList.get(1);
        assertEquals(9L, firstCart.getProductId());
        assertEquals(7L, firstCart.getMemberId());
        assertEquals(2L, firstCart.getQuantity());
        System.out.println("첫 번째 데이터: " + firstCart);

        // 두 번째 데이터 검증
        CartSelect secondCart = cartList.get(2);
        assertEquals(8L, secondCart.getProductId());
        assertEquals(7L, secondCart.getMemberId());
        assertEquals(3L, secondCart.getQuantity());
        System.out.println("두 번째 데이터: " + secondCart);
    }


    @Test
    @DisplayName("장바구니 삭제")
    void deleteCartTest(){

        Cart cart = new Cart();
        cart.setMemberId(7L);
        cart.setProductId(8L);
        cart.setQuantity(10L);

        cartMapper.insertCart(cart);

        List<Long> productIdList = new ArrayList<>();
        productIdList.add(8L); // productIdList에 product_id 삽입

        List<CartSelect> cartSelect = cartMapper.selectCart(7L); // 장바구니 조회
        assertEquals(2, cartSelect.size()); // 장바구니 조회 결과 1개 검색

        cartSelect = cartMapper.selectCart(7L);


        cartMapper.deleteCart(cartSelect.get(0).getMemberId(), productIdList);

        //then
        cartSelect = cartMapper.selectCart(7L);
        assertEquals(1, cartSelect.size());
    }

    @Test
    @DisplayName("장바구니 삭제")
    void ListdeleteCartTest(){

        Cart cart1 = new Cart();
        cart1.setMemberId(7L);
        cart1.setProductId(8L);
        cart1.setQuantity(10L);
        cartMapper.insertCart(cart1);

        Cart cart2 = new Cart();
        cart2.setMemberId(7L);
        cart2.setProductId(9L);
        cart2.setQuantity(10L);
        cartMapper.insertCart(cart2);

        List<Long> productIdList = new ArrayList<>();
        productIdList.add(8L); // productIdList에 product_id 삽입
        productIdList.add(9L);

        List<CartSelect> cartSelect = cartMapper.selectCart(7L); // 장바구니 member = 7L 조회
        assertEquals(3, cartSelect.size()); // 장바구니 조회 결과 3개 검색

        cartSelect = cartMapper.selectCart(7L);


        cartMapper.deleteCart(cartSelect.get(0).getMemberId(), productIdList); // 장바구니 비우기

        //then
        cartSelect = cartMapper.selectCart(7L);
        assertEquals(1, cartSelect.size());
    }

    @Test
    @DisplayName("장바구니 수량 변경")
    void updateCartTest(){
        cartMapper.updateCartQuantity(7L, 7L, 100L);

        assertEquals(100, cartMapper.selectCart(7L).get(0).getQuantity());
    }

}