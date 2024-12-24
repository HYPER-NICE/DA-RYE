package hyper.darye.service;


import hyper.darye.dto.Product;
import hyper.darye.dto.ProductWithBLOBs;
import hyper.darye.mapper.ProductMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    private final ProductMapper productMapper;

    public ProductService(ProductMapper productMapper) {
        this.productMapper = productMapper;
    }

    public int insertProduct(Product product) {
        return this.productMapper.insertProduct(product);
    }

    public List<Product> selectAllProduct() {
        return this.productMapper.selectAllProduct();
    }

    public ProductWithBLOBs selectByPrimaryKey(Long id) {
        return this.productMapper.selectByPrimaryKey(id);
    }

    public int updateByPrimaryKey(Product product) {
        return this.productMapper.updateByPrimaryKey(product);
    }

    public List<Product> searchByKeyword(String keyword, Integer minPrice, Integer maxPrice, Integer orderBy) {
        if(minPrice == null) {
            minPrice = 0;
        }

        if(maxPrice == null) {
            maxPrice = 10000000;
        }
         keyword = keyword.trim();
        return this.productMapper.searchByKeyword(keyword, minPrice, maxPrice, orderBy      );
    }
}
