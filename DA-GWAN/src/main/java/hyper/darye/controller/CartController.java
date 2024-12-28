package hyper.darye.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import hyper.darye.dto.controller.request.SelectCartRequest;
import hyper.darye.security.CustomUserDetails;
import hyper.darye.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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

    // 내 장바구니 넣기
    @PreAuthorize("isAuthenticated()")
    @PostMapping("/my/cart")
    @ResponseStatus(HttpStatus.CREATED)
    public void insertMyCart(@AuthenticationPrincipal CustomUserDetails userDetails,
                               @RequestParam Long productId,
                               @RequestParam Long quantity) {
        this.insertCart(userDetails.getId(), productId, quantity);
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

    // 내 장바구니 수량 변경
    @PreAuthorize("isAuthenticated()")
    @PatchMapping("/my/cart/{productId}")
    public void updateMyCart(@AuthenticationPrincipal CustomUserDetails userDetails,
                               @PathVariable Long productId,
                               @RequestParam Long quantity) {
        this.updateCart(userDetails.getId(), productId, quantity);
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

    // 내 장바구니 조회
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/my/cart")
    public List<SelectCartRequest> selectMyCartByMemberId(@AuthenticationPrincipal CustomUserDetails userDetails)
    {
        return this.selectCartByMemberId(userDetails.getId());
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

    // 내 장바구니 선택 삭제
    @PreAuthorize("isAuthenticated()")
    @DeleteMapping("/my/cart")
    public void deleteMyCart(@AuthenticationPrincipal CustomUserDetails userDetails, @RequestParam List<Long> productIdList) {
        this.deleteCart(userDetails.getId(), productIdList);
    }
}
