package hyper.darye.controller;


import hyper.darye.dto.Product;
import hyper.darye.dto.ProductWithBLOBs;
import hyper.darye.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public String insertProduct(@RequestBody Product product) {
        int result = productService.insertProduct(product);
        if (result == 1) {
            return "success";
        }
        return "fail";
    }

    @GetMapping
    public ResponseEntity<List<Product>> selectAllProduct() {
        List<Product> product = productService.selectAllProduct();
        if (product.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(product);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductWithBLOBs> selectByPrimaryKey(@PathVariable Long id) {
        ProductWithBLOBs product = productService.selectByPrimaryKey(id);
        if (product == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(product);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateByPrimaryKey(
            @PathVariable Long id,
            @RequestBody Product request) {
        // 요청 본문의 ID와 경로의 ID가 일치하는지 확인
        if (!id.equals(request.getId())) {
            return ResponseEntity.badRequest().body("요청한 ID와 일치하는 상품이 없습니다.");
        }

        // 상품 정보 업데이트
        int isUpdated = productService.updateByPrimaryKey(request);

        if (isUpdated == 1) {
            return ResponseEntity.ok("상품이 업데이트 되었습니다.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("상품을 찾지 못하였습니다.");
        }
    }
}
