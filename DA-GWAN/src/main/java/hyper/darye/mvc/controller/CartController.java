package hyper.darye.mvc.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import hyper.darye.mvc.model.dto.controller.request.SelectCartRequest;
import hyper.darye.mvc.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/members")
public class CartController {

    @Autowired
    private CartService cartService;

    @Autowired
    private ObjectMapper objectMapper;

    // 장바구니 넣기
    @PreAuthorize("#memberId == authentication.principal.id")
    @PostMapping("/{memberId}/cart")
    public String insertCart(@PathVariable Long memberId,
                             @RequestParam Long productId,
                             @RequestParam Long quantity) {
        int result = cartService.insertCart(memberId, productId, quantity);
        if (result == 1)
            return "성공";
        else
            throw new IllegalStateException("입력 실패했습니다."); // 적절한 런타임 예외
    }

    // 장바구니 수량 변경
    @PreAuthorize("#memberId == authentication.principal.id")
    @PatchMapping("/{memberId}/cart/{productId}")
    public String updateCart(@PathVariable Long memberId,
                             @PathVariable  Long productId,
                             @RequestParam Long quantity) {
        int result = cartService.updateCartQuantity(memberId, productId, quantity);
        if (result == 1)
            return "성공";
        else
            throw new IllegalStateException("입력 실패"); // 적절한 런타임 예외
    }


    // 장바구니 조회
    @PreAuthorize("#memberId == authentication.principal.id")
    @GetMapping("/{memberId}/cart")
    public List<SelectCartRequest> selectCartByMemberId(@PathVariable Long memberId)
    {
        List<SelectCartRequest> searchCart = cartService.selectCart(memberId);
        if(searchCart.isEmpty()) return new ArrayList<>();

        return cartService.selectCart(memberId);
    }


    // 장바구니 선택 삭제
    @PreAuthorize("#memberId == authentication.principal.id")
    @DeleteMapping("/{memberId}/cart")
    public int deleteCart(@PathVariable Long memberId, @RequestParam List<Long> productIdList) {
        if (productIdList == null || productIdList.isEmpty()) {
            return 0; // 삭제할 항목이 없음을 나타냄
        }
        return cartService.deleteCart(memberId, productIdList);
    }
}
