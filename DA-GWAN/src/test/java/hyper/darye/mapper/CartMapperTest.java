package hyper.darye.mapper;

import hyper.darye.dto.Cart;
import hyper.darye.dto.controller.request.SelectCartRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@MybatisTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Transactional
class CartMapperTest {

    @Autowired
    private CartMapper cartMapper;

    @Autowired
    private ForeignKeyMapper fkMapper;


    @Test
    @DisplayName("장바구니 넣기 테스트")
    void insertCartTest() {
        // 데이터 세팅
        Long memberId1 = (Long)3L;

        // 외래키 때문에 겹치지 않는 productId 삽입
        List<Long> productIdList = new ArrayList<>();
        List<Long> productId = new ArrayList<>();

        // 기존 장바구니에서 productId 목록 가져오기
        for (int i = 0; i < cartMapper.selectCart(memberId1).size(); i++) {
            productIdList.add(cartMapper.selectCart(memberId1).get(i).getProductId());
        }

        // 1부터 9까지의 ID 중에서 기존 productId와 겹치지 않는 것만 추가
        for (Long i = (Long)1L; i < 10; i++) {
            if (!productIdList.contains(i)) {
                productId.add(i);
            }
        }

        Long productId1 = productId.get(0); // 새로 추가할 productId
        Long quantity = (Long)10L;

        // 장바구니에 추가 전 사이즈
        int sizeBefore = cartMapper.selectCart(memberId1).size();

        Cart cart = new Cart();
        cart.setMemberId(memberId1);
        cart.setProductId(productId1);
        cart.setQuantity(quantity);

        // 장바구니에 상품 삽입
        int result = cartMapper.insertCart(cart);

        // then
        assertEquals(1, result);  // 삽입이 성공하면 1이 반환되어야 함

    }

    @Test
    @DisplayName("장바구니 중복 처리 테스트")
    void insertDuplicateTest() {
        Long memberId = (Long)3L;
        Long productId = cartMapper.selectCart(memberId).get(0).getProductId();
        Long quantity = cartMapper.selectCart(memberId).get(0).getQuantity();
        Long addQuantity = (Long)10L;

        Cart cart = new Cart();
        cart.setMemberId(memberId);
        cart.setProductId(productId);
        cart.setQuantity(addQuantity);

        // 이전 사이즈
        int lastSize = cartMapper.selectCart(memberId).size();

        Cart duplicateCart = cartMapper.selectCartDuplicate(memberId, productId);

        if (duplicateCart != null) {
            cartMapper.updateCart(cart.getProductId(), cart.getMemberId(), cart.getQuantity());
        } else {
            cartMapper.insertCart(cart);
        }

        // 이후 사이즈
        List<SelectCartRequest> selectCartRequest = cartMapper.selectCart(memberId);
        assertEquals(lastSize, selectCartRequest.size());
        assertEquals(quantity + addQuantity, selectCartRequest.get(0).getQuantity()); // 기존 2 + 추가 10
    }


    @Test
    @DisplayName("장바구니 데이터 조회 테스트")
    void selectCartTest() {
        // 데이터 세팅
        Long memberId1 = (Long)3L;

        // 외래키 때문에 겹치지 않는 productId 삽입
        List<Long> productIdList = new ArrayList<>();
        List<Long> productId = new ArrayList<>();
        for (int i = 0; i < cartMapper.selectCart(memberId1).size(); i++)
            productIdList.add(cartMapper.selectCart(memberId1).get(i).getProductId());

        for(Long i = (Long)1L; i < 10; i++){
            if(!productIdList.contains(i))
                productId.add(i);
        }

        Long productId1 = productId.get(0);
        Long quantity = (Long)10L;
        int size = cartMapper.selectCart(memberId1).size(); // 이전 사이즈

        Cart cart1 = new Cart();
        cart1.setMemberId(memberId1);
        cart1.setProductId(productId1);
        cart1.setQuantity(quantity);
        cartMapper.insertCart(cart1);

        // when
        List<SelectCartRequest> cartList = cartMapper.selectCart(memberId1); // member_id가 7인 데이터 조회

        // then
        assertEquals(size+1, cartList.size()); // 추가 했으니 이전 사이즈 + 1
    }


    @Test
    @DisplayName("장바구니 삭제")
    void deleteCartTest(){

        // 데이터 세팅
        Long memberId1 = (Long)3L;

        // 외래키 때문에 겹치지 않는 productId 삽입
        List<Long> productIdList = new ArrayList<>();
        List<Long> productId = new ArrayList<>();
        for (int i = 0; i < cartMapper.selectCart(memberId1).size(); i++)
            productIdList.add(cartMapper.selectCart(memberId1).get(i).getProductId());

        for(Long i = (Long)1L; i < 10; i++){
            if(!productIdList.contains(i))
                productId.add(i);
        }

        Long productId1 = productId.get(0);

        Cart cart1 = new Cart();
        cart1.setMemberId(memberId1);
        cart1.setProductId(productId1);
        cart1.setQuantity((Long)2L);
        cartMapper.insertCart(cart1);

        // 삭제 리스트에 deleteList에 넣는다
        List<Long> productIdDeleteList = new ArrayList<>();
        productIdDeleteList.add(productId1); // productIdList에 product_id 삽입
        productIdDeleteList.add(productIdList.get(0)); // 2개를 삭제

        List<SelectCartRequest> selectCartRequest = cartMapper.selectCart(memberId1);
        int size = cartMapper.selectCart(memberId1).size(); // 이전 사이즈

        //when
        // 삭제 리스트에 있는 것을 삭제
        cartMapper.deleteCart(memberId1, productIdDeleteList);

        //then
        int afterSize = cartMapper.selectCart(memberId1).size();
        assertEquals(size-productIdDeleteList.size(), afterSize);
    }

    @Test
    @DisplayName("장바구니 수량 변경")
    void updateCartTest(){
        // 데이터 세팅
        Long memberId1 = (Long)3L;

        // 외래키 때문에 겹치지 않는 productId 삽입
        List<Long> productIdList = new ArrayList<>();
        List<Long> productId = new ArrayList<>();
        for (int i = 0; i < cartMapper.selectCart(memberId1).size(); i++)
            productIdList.add(cartMapper.selectCart(memberId1).get(i).getProductId());

        for(Long i = (Long)1L; i < 10; i++){
            if(!productIdList.contains(i))
                productId.add(i);
        }

        Long productId1 = productId.get(0);
        Long quantity = (Long)10L;

        Cart cart1 = new Cart();
        cart1.setMemberId(memberId1);
        cart1.setProductId(productId1);
        cart1.setQuantity(quantity);
        cartMapper.insertCart(cart1);

        Long changeQuantity = (Long)100L;
        cartMapper.updateCartQuantity(memberId1, productId1, changeQuantity);

        assertEquals(changeQuantity, cartMapper.selectCart(memberId1).get(0).getQuantity());
    }

}