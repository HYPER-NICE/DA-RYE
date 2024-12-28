package hyper.darye.service;

import hyper.darye.dto.ProductWithBLOBs;
import hyper.darye.mapper.ProductMapper;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class ProductService {

    private final ProductMapper productMapper;

    public ProductService(ProductMapper productMapper) {
        this.productMapper = productMapper;
    }

    // 상품 등록
    public int insertProduct(ProductWithBLOBs productWithBLOBs) {
        return this.productMapper.insertProduct(productWithBLOBs);
    }

    // 상품 전체 조회
    public List<ProductWithBLOBs> selectAllProduct() {
        List<ProductWithBLOBs> products = this.productMapper.selectAllProduct();
        if (products == null || products.isEmpty()) {
            throw new RuntimeException("상품이 없습니다.");
        }
        return products;
    }

    // 상품 선택 조회
    public ProductWithBLOBs selectByPrimaryKey(Long id) {
        ProductWithBLOBs existingProduct = productMapper.selectByPrimaryKey(id);
        if (existingProduct == null) {
            throw new RuntimeException("조회할 상품이 없습니다.");
        }
        return existingProduct;
    }

    // 상품 내용 수정
    public int updateByPrimaryKey(ProductWithBLOBs productWithBLOBs) {
        ProductWithBLOBs existingProduct = this.productMapper.selectByPrimaryKey(productWithBLOBs.getId());
        if (existingProduct == null) {
            throw new RuntimeException("상품이 없습니다.");
        }
        return this.productMapper.updateByPrimaryKey(productWithBLOBs);
    }

    // 상품 삭제 ( 상태 업데이트 )
    public int deleteByPrimaryKey(Long id, Long statusCode) {
        ProductWithBLOBs productWithBLOBs = productMapper.selectByPrimaryKey(id);

        // 상품 존재 여부 확인
        if (productWithBLOBs == null) {
            throw new RuntimeException("해당 ID의 상품이 존재하지 않습니다.");
        }
        // 상태 업데이트
        int isUpdated = productMapper.updateProductStatus(id, statusCode);
        if (isUpdated != 1) {
            throw new RuntimeException("상품 삭제에 실패했습니다.");
        }
        return isUpdated;
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