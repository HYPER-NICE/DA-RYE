package hyper.darye.controller;


import hyper.darye.dto.Product;
import hyper.darye.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService productService;

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
        List<Product> products = productService.selectAllProduct();
        if (products.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(products);
    }
}
