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
    @PostMapping
    public String addCart(Long id, Long productId, Long quantity) {
        int result = cartService.insertCart(id, productId, quantity);
        if (result == 1)
            return "성공";
        else
            throw new IllegalStateException("입력 실패"); // 적절한 런타임 예외
    }

    // 장바구니 수량 변경
    @PatchMapping
    public String updateCart(Long id, Long productId, @RequestParam Long quantity) {
        int result = cartService.updateCart(id, productId, quantity);
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
    @GetMapping
    public int deleteCart(@PathVariable Long id, @RequestParam List<Long> productIdList) {
        return cartService.deleteCart(id, productIdList);
    }
}
