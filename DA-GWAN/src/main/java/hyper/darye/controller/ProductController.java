package hyper.darye.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import hyper.darye.dto.Product;
import hyper.darye.dto.ProductWithBLOBs;
import hyper.darye.dto.controller.request.RequestPostProductDto;
import hyper.darye.dto.controller.request.RequestPutProductDto;
import hyper.darye.service.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
        Product product = objectMapper.convertValue(insertPostProductRequest, Product.class);
        productService.insertProduct(product);
    }

    @GetMapping
    public List<Product> selectAllProduct() {
        return productService.selectAllProduct();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductWithBLOBs> selectByPrimaryKey(@PathVariable Long id) {
        ProductWithBLOBs product = productService.selectByPrimaryKey(id);
        if (product == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(product);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<String> updateByPrimaryKey(
            @PathVariable Long id,
            @RequestBody RequestPutProductDto requestPutProductDto) {
        // 요청 본문의 ID와 경로의 ID가 일치하는지 확인
        if (!id.equals(requestPutProductDto.getId())) {
            return ResponseEntity.badRequest().body("요청한 ID와 일치하는 상품이 없습니다.");
        }

        // 상품 정보 업데이트
        Product product = objectMapper.convertValue(requestPutProductDto, Product.class);
        int isUpdated = productService.updateByPrimaryKey(product);

        if (isUpdated == 1) {
            return ResponseEntity.ok("상품이 업데이트 되었습니다.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("수정된 내용이 없습니다.");
        }
    }
}
