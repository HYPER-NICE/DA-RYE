package hyper.darye.mvc.service;

import hyper.darye.mvc.model.entity.ProductWithBLOBs;
import hyper.darye.mvc.mapper.ProductMapper;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class ProductService {

    private final ProductMapper productMapper;

    public ProductService(ProductMapper productMapper) {
        this.productMapper = productMapper;
    }

    public int insertProduct(ProductWithBLOBs productWithBLOBs) {
        return this.productMapper.insertProduct(productWithBLOBs);
    }

    public List<ProductWithBLOBs> selectAllProduct() {
        return this.productMapper.selectAllProduct();
    }

    public ProductWithBLOBs selectByPrimaryKey(Long id) {
        ProductWithBLOBs productWithBLOBs = productMapper.selectByPrimaryKey(id);
        if (productWithBLOBs == null) {
            throw new RuntimeException("조회할 상품이 없습니다.");
        }
        return productWithBLOBs;
    }

    public int updateByPrimaryKey(ProductWithBLOBs productWithBLOBs) {
        ProductWithBLOBs productWithBLOBs1 = this.productMapper.selectByPrimaryKey(productWithBLOBs.getId());
        if (productWithBLOBs1 == null) {
            throw new RuntimeException("상품이 없습니다.");
        }
        return this.productMapper.updateByPrimaryKey(productWithBLOBs1);
    }

    public int deleteByPrimaryKey(Long id, Long statusCode) {
        ProductWithBLOBs productWithBLOBs = productMapper.selectByPrimaryKey(id);

        // 상품 존재 여부 확인
        if (productWithBLOBs == null) {
            throw new RuntimeException("해당 ID의 상품이 존재하지 않습니다.");
        }
        // 상태 업데이트
        int isUpdated = productMapper.updateProductStatus(id, 4L);
        if (isUpdated != 1) {
            throw new RuntimeException("상품 삭제에 실패했습니다.");
        }
        return productMapper.updateProductStatus(id, 4L);
    }

    public List<ProductWithBLOBs> searchByKeyword(String keyword, Integer minPrice, Integer maxPrice, Integer orderBy) {
        if(minPrice == null) {
            minPrice = 0;
        }

        if(maxPrice == null) {
            maxPrice = 10000000;
        }

        List<String> wordList = Arrays.stream(keyword.split(" "))
                .filter(word -> !word.isEmpty()) // 빈 문자열 제거
                .toList();

        return this.productMapper.searchByKeyword(wordList, minPrice, maxPrice, orderBy);
    }
}