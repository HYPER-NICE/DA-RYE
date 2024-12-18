package hyper.darye.service;


import hyper.darye.dto.Product;
import hyper.darye.mapper.ProductMapper;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

    private final ProductMapper productMapper;

    public ProductService(ProductMapper productMapper) {
        this.productMapper = productMapper;
    }

    public int insertProduct(Product product) {
        return this.productMapper.insertProduct(product);
    }

}
