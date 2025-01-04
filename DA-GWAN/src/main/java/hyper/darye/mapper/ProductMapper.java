package hyper.darye.mapper;

import hyper.darye.model.entity.Product;
import hyper.darye.model.entity.ProductWithBLOBs;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ProductMapper {
    @Insert("insert into PRODUCT (NAME, SHORT_DESCRIPTION, LONG_DESCRIPTION, PRICE, CATEGORY_ID, PRODUCT_STATUS_CODE_ID, IMPORTER, MANUFACTURER, EXPIRATION_DATE, CAPACITY, INGREDIENTS, PRECAUTIONS, SALE_DATE) " +
            "values (#{name}, #{shortDescription}, #{longDescription}, #{price}, #{categoryId}, #{productStatusCodeId}, #{importer}, #{manufacturer}, #{expirationDate}, #{capacity}, #{ingredients}, #{precautions}, #{saleDate})")
    int insertProduct(ProductWithBLOBs productWithBLOBs);

    List<ProductWithBLOBs> selectAllProduct();

    ProductWithBLOBs selectByPrimaryKey(Long id);

    int updateByPrimaryKey(ProductWithBLOBs productWithBLOBs);

    int updateProductStatus(Long id, Long statusCode);

    // 상품 검색용
    List<ProductWithBLOBs> searchByKeyword(List<String> keyword, Integer minPrice, Integer maxPrice, Integer orderBy);

    @Select("SELECT * FROM PRODUCT")
    List<Product> selectAll();
}