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
}
