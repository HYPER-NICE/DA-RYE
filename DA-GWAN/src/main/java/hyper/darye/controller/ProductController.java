package hyper.darye.controller;


import hyper.darye.dto.Product;
import hyper.darye.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public String insertProduct(Product product) {
        int result = productService.insertProduct(product);
        if (result == 1) {
            return "success";
        }
        return "fail";
    }
}
