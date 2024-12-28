package hyper.darye.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import hyper.darye.dto.ProductWithBLOBs;
import hyper.darye.dto.controller.request.RequestDeleteProductDto;
import hyper.darye.dto.controller.request.RequestPostProductDto;
import hyper.darye.dto.controller.request.RequestPutProductDto;
import hyper.darye.security.CustomUserDetails;
import hyper.darye.service.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;
    private final ObjectMapper objectMapper;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void insertProduct(@RequestBody RequestPostProductDto insertPostProductRequest) {
        ProductWithBLOBs productWithBLOBs = objectMapper.convertValue(insertPostProductRequest, ProductWithBLOBs.class);
        productService.insertProduct(productWithBLOBs);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<ProductWithBLOBs> selectAllProduct() {
        return productService.selectAllProduct();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ProductWithBLOBs selectByPrimaryKey(@PathVariable Long id) {
        return productService.selectByPrimaryKey(id);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void updateByPrimaryKey(
            @PathVariable Long id,
            @RequestBody RequestPutProductDto requestPutProductDto,
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        // 상품 정보 업데이트
        ProductWithBLOBs productWithBLOBs = objectMapper.convertValue(requestPutProductDto, ProductWithBLOBs.class);

        // 요청된 id를 설정하고, 수정한 상품 정보 업데이트
        productWithBLOBs.setId(id);
        productWithBLOBs.setLastModifiedMember(userDetails.getId());

        // 서비스에서 검증 후 업데이트
        productService.updateByPrimaryKey(productWithBLOBs);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteByPrimaryKey(
            @PathVariable Long id,
            @RequestBody RequestDeleteProductDto requestDeleteProductDto) {
        // 상태 코드가 4일 경우에만 삭제 상태로 변경
        if (requestDeleteProductDto.getProductStatusCodeId() == 4L) {
            productService.deleteByPrimaryKey(id, requestDeleteProductDto.getProductStatusCodeId());
        } else {
            throw new IllegalArgumentException("잘못된 상태 코드입니다. 삭제 상태는 4이어야 합니다.");
        }
    }

    @GetMapping("/test")
    public List<ProductWithBLOBs> SearchByKeyword(@RequestParam String keyword,
                                         @RequestParam(required = false) Integer minPrice,
                                         @RequestParam(required = false) Integer maxPrice,
                                         @RequestParam(required = false) Integer orderBy) {
        if(minPrice > maxPrice || !(minPrice instanceof Integer) || !(maxPrice instanceof Integer)) {
            throw new IllegalStateException("가격 설정이 잘못 됐습니다.");
        } else if (keyword == null || keyword.isEmpty())
            return productService.selectAllProduct();
        else
            return productService.searchByKeyword(keyword, minPrice, maxPrice, orderBy);
    }
}