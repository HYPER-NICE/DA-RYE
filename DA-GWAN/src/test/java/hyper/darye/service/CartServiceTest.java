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

import java.util.ArrayList;
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
        // 데이터 세팅
        Long memberId1 = (Long)3L;
        int size = cartMapper.selectCart(memberId1).size();

        // 외래키 때문에 겹치지 않는 새로운 productId 삽입
        List<Long> productIdList = new ArrayList<>();
        List<Long> productId = new ArrayList<>();
        for (int i = 0; i < cartMapper.selectCart(memberId1).size(); i++)
            productIdList.add(cartMapper.selectCart(memberId1).get(i).getProductId());

        for(Long i = (Long)1L; i < 10; i++){
            if(!productIdList.contains(i))
                productId.add(i);
        }

        Long productId1 = productId.get(0);
        Long Quantity = (Long)10L;

        int result = cartService.insertCart(memberId1, productId1, Quantity);
        List<CartSelect> cartSelect = cartMapper.selectCart(memberId1);

        assertEquals(size+1, cartSelect.size());
        assertEquals(1, result);
    }

    @Test
    @DisplayName("장바구니 중복 담기 테스트(update)")
    void insertDuplicateTest(){
        // 데이터 세팅
        Long memberId = (Long)3L;
        Long productId = cartMapper.selectCart(memberId).get(0).getProductId();
        Long quantity = cartMapper.selectCart(memberId).get(0).getQuantity();

        // 이전 사이즈
        int lastSize = cartMapper.selectCart(memberId).size();

        cartService.insertCart(memberId, productId, quantity);

        //삽입 후 사이즈
        List<CartSelect> cartSelect = cartMapper.selectCart(memberId);

        assertEquals(lastSize, cartSelect.size());
        assertEquals(quantity*2, cartSelect.get(0).getQuantity());
    }

    @Test
    @DisplayName("장바구니 조회 테스트")
    void selectCartTest(){
        // 데이터 세팅
        Long memberId1 = (Long)3L;
        int size = cartMapper.selectCart(memberId1).size();

        // 외래키 때문에 겹치지 않는 새로운 productId 삽입
        List<Long> productIdList = new ArrayList<>();
        List<Long> productId = new ArrayList<>();
        for (int i = 0; i < cartMapper.selectCart(memberId1).size(); i++)
            productIdList.add(cartMapper.selectCart(memberId1).get(i).getProductId());

        for(Long i = (Long)1L; i < 10; i++){
            if(!productIdList.contains(i))
                productId.add(i);
        }

        Long productId1 = productId.get(0);
        Long Quantity = (Long)10L;

        // when
        int result = cartService.insertCart(memberId1, productId1, Quantity);
        List<CartSelect> cartSelect = cartService.selectCart(memberId1);

        // then

        assertNotNull(cartSelect); // null이 아닌지 확인
        assertFalse(cartSelect.isEmpty()); // 비어있지 않은지 확인
        assertEquals(memberId1, cartSelect.get(0).getMemberId()); // memberId가 100L인지 확인

        CartSelect firstCart = cartSelect.get(0);
        assertEquals(productId1, firstCart.getProductId()); // productId가 100L인지 확인
        assertEquals(Quantity, firstCart.getQuantity()); // quantity가 100L인지 확인

    }

    @Test
    @DisplayName("장바구니 수량 변경")
    void updateCartTest(){
        // 데이터 세팅
        Long memberId1 = (Long)3L;
        int size = cartMapper.selectCart(memberId1).size();

        // 외래키 때문에 겹치지 않는 새로운 productId 삽입
        List<Long> productIdList = new ArrayList<>();
        List<Long> productId = new ArrayList<>();
        for (int i = 0; i < cartMapper.selectCart(memberId1).size(); i++)
            productIdList.add(cartMapper.selectCart(memberId1).get(i).getProductId());

        for(Long i = (Long)1L; i < 10; i++){
            if(!productIdList.contains(i))
                productId.add(i);
        }

        Long productId1 = productId.get(0);
        Long Quantity = (Long)10L;
        Long changeQuantity = (Long)100L;

        // when
        int result = cartService.insertCart(memberId1, productId1, Quantity);
        cartService.updateCartQuantity(memberId1, productId1, changeQuantity);

        //then
        assertEquals(changeQuantity, cartMapper.selectCart(memberId1).get(0).getQuantity());
    }
}