package hyper.darye.controller;


import hyper.darye.dto.Product;
import hyper.darye.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
    @ResponseStatus(HttpStatus.CREATED)
    public List<Product> selectAllProduct() {
        List<Product> product = productService.selectAllProduct();
        if (product.size() == 10) {
            return product;
        }
        return null;
    }
}
