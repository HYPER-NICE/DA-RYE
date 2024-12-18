package hyper.darye.mapper;


import hyper.darye.dto.Product;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ProductMapper {

    @Insert("insert into PRODUCT (NAME, SHORT_DESCRIPTION, LONG_DESCRIPTION, PRICE, CATEGORY_ID, PRODUCT_STATUS_CODE_ID, IMPORTER, MANUFACTURER, EXPIRATION_DATE, CAPACITY, INGREDIENTS, PRECAUTIONS, SALE_DATE) " +
            "values (#{name}, #{shortDescription}, #{longDescription}, #{price}, #{categoryId}, #{productStatusCodeId}, #{importer}, #{manufacturer}, #{expirationDate}, #{capacity}, #{ingredients}, #{precautions}, #{saleDate})")
    int insertProduct(Product product);
}