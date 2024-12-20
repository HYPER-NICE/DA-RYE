package hyper.darye.controller;

import hyper.darye.dto.CartSelect;
import hyper.darye.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/members")
public class CartController {

    @Autowired
    private CartService cartService;

    // 장바구니 넣기
    @PostMapping("/{id}/cart")
    public String addCart(@PathVariable Long id, Long productId, Long quantity) {
        int result = cartService.insertCart(id, productId, quantity);
        if (result == 1)
            return "성공";
        else
            throw new IllegalStateException("입력 실패"); // 적절한 런타임 예외
    }

    // 장바구니 수량 변경
    @PatchMapping("/{id}/cart/{productId}")
    public String updateCart(@PathVariable Long id, Long productId, @RequestParam Long quantity) {
        int result = cartService.updateCartQuantity(id, productId, quantity);
        if (result == 1)
            return "성공";
        else
            throw new IllegalStateException("입력 실패"); // 적절한 런타임 예외
    }


    // 장바구니 조회
    @GetMapping("/{id}/cart")
    public List<CartSelect> searchCart(@PathVariable Long id) {
        return cartService.selectCart(id);
    }


    // 장바구니 선택 삭제
    @DeleteMapping("/{id}/cart")
    public int deleteCart(Long memberId, @RequestParam List<Long> productIdList) {
        return cartService.deleteCart(memberId, productIdList);
    }
}
