package hyper.darye.mapper;

import hyper.darye.dto.ProductWithBLOBs;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ProductMapper {
   int insertProduct(ProductWithBLOBs productWithBLOBs);

    List<ProductWithBLOBs> selectAllProduct();

    ProductWithBLOBs selectByPrimaryKey(Long id);

    int updateByPrimaryKey(ProductWithBLOBs productWithBLOBs);

    int updateProductStatus(Long id, Long statusCode);

    // 상품 검색용
    List<ProductWithBLOBs> searchByKeyword(List<String> keyword, Integer minPrice, Integer maxPrice, Integer orderBy);

}