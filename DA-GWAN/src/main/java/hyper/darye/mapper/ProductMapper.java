package hyper.darye.mapper;

import hyper.darye.dto.Product;
import hyper.darye.dto.ProductWithBLOBs;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ProductMapper {
    @Insert("insert into PRODUCT (NAME, SHORT_DESCRIPTION, LONG_DESCRIPTION, PRICE, CATEGORY_ID, PRODUCT_STATUS_CODE_ID, IMPORTER, MANUFACTURER, EXPIRATION_DATE, CAPACITY, INGREDIENTS, PRECAUTIONS, SALE_DATE) " +
            "values (#{name}, #{shortDescription}, #{longDescription}, #{price}, #{categoryId}, #{productStatusCodeId}, #{importer}, #{manufacturer}, #{expirationDate}, #{capacity}, #{ingredients}, #{precautions}, #{saleDate})")
    int insertProduct(Product product);

    List<Product> selectAllProduct();

    ProductWithBLOBs selectByPrimaryKey(Long id);

    int updateByPrimaryKey(Product record);

    int deleteByPrimaryKey(Long id);

    int insert(ProductWithBLOBs record);

    int insertSelective(ProductWithBLOBs record);

    int updateByPrimaryKeySelective(ProductWithBLOBs record);

    int updateByPrimaryKeyWithBLOBs(ProductWithBLOBs record);

}