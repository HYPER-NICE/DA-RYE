package hyper.darye.mapper.order;

import hyper.darye.dto.OrderDetail;
import hyper.darye.dto.OrderMain;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;

import java.util.List;

@Mapper
public interface OrderMapper {
    // CREATE
    @Insert("INSERT INTO ORDER_MAIN (MEMBER_ID) VALUES (#{memberId})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insertOrderMain(OrderMain orderMain);

    @Insert("INSERT INTO ORDER_DETAIL (ORDER_ID, PRODUCT_ID, QUANTITY, UNIT_PRICE) VALUES (#{orderId}, #{productId}, #{quantity}, #{unitPrice})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insertOrderDetail(OrderDetail orderDetail);

    void insert(List<OrderDetail> products);
}
