package hyper.darye.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import hyper.darye.dto.Product;
import hyper.darye.dto.ProductWithBLOBs;
import hyper.darye.dto.controller.request.RequestDeleteProductDto;
import hyper.darye.dto.controller.request.RequestPostProductDto;
import hyper.darye.dto.controller.request.RequestPutProductDto;
import hyper.darye.security.CustomUserDetails;
import hyper.darye.service.ProductService;
import lombok.AllArgsConstructor;
import org.apache.ibatis.javassist.NotFoundException;
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
    public void selectAllProduct() {
        productService.selectAllProduct();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void selectByPrimaryKey(@PathVariable Long id) {
        productService.selectByPrimaryKey(id);
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
        productWithBLOBs.setLastModifiedMember(userDetails.getId());
        productService.updateByPrimaryKey(productWithBLOBs);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteByPrimaryKey(
            @PathVariable Long id,
            @RequestBody RequestDeleteProductDto requestDeleteProductDto) throws NotFoundException {
        productService.deleteByPrimaryKey(requestDeleteProductDto.getId(), 4L); // 상태 코드 4로 업데이트
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