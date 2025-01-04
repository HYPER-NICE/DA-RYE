package hyper.darye.service;

import hyper.darye.model.entity.Cart;
import hyper.darye.dto.controller.request.SelectCartRequest;
import hyper.darye.mapper.CartMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartService {

    @Autowired
    private CartMapper cartMapper;

    //  장바구니 담기
    public int insertCart(Long memberId, Long productId, Long quantity) {
        Cart cart = new Cart();
        cart.setMemberId(memberId);
        cart.setProductId(productId);
        cart.setQuantity(quantity);

        if(cartMapper.selectCartDuplicate(cart.getMemberId(), cart.getProductId()) != null)
            return this.cartMapper.updateCart(cart.getProductId(), cart.getMemberId(), cart.getQuantity());
        else
            return this.cartMapper.insertCart(cart);
    }

    // 장바구니 조회
    public List<SelectCartRequest> selectCart(Long memberId){
        return this.cartMapper.selectCart(memberId);
    }



    // 장바구니 삭제
    public int deleteCart(Long memberId, List<Long> productIdList){
        return cartMapper.deleteCart(memberId, productIdList);
    }

    // 중복 장바구니 수량 변경
    public int updateCart(Long memberId, Long productId, Long quantity){
        return this.cartMapper.updateCart(memberId, productId, quantity);
    }

    // 장바구니 수량 변경
    public int updateCartQuantity(Long memberId, Long productId, Long quantity){
        return this.cartMapper.updateCartQuantity(memberId, productId, quantity);
    }


    // cart_id로 삭제하기
    public int deleteCart(Long cartId){
        return cartMapper.deleteByPrimaryKey(cartId);
    }

    // 선택 삽입하기
    public int insertSelective(Cart record){
        return cartMapper.insertSelective(record);
    }

    // 선택 조회하기
    public Cart selectByPrimaryKey(Long id){
        return cartMapper.selectByPrimaryKey(id);
    }

    // cart_id로 선택 변경
    public int updateByPrimaryKeySelective(Cart record){
        return cartMapper.updateByPrimaryKeySelective(record);
    }

    // cart_id로 변경
    public int updateByPrimaryKey(Cart record){
        return cartMapper.updateByPrimaryKey(record);
    }
}