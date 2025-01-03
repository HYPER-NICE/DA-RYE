package hyper.darye.mapper.order;

import hyper.darye.dto.OrderDetail;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface OrderDetailMapper {
    int deleteByPrimaryKey(Long id);

    int insert(OrderDetail record);

    int insertSelective(OrderDetail record);

    OrderDetail selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(OrderDetail record);

    int updateByPrimaryKey(OrderDetail record);

    /**
     * 다중 주문 상세 벌크 삽입
     *
     * @param orderDetails 주문 상세 리스트
     */
    @Insert({
            "<script>",
            "INSERT INTO ORDER_DETAIL (ORDER_ID, PRODUCT_ID, QUANTITY, UNIT_PRICE) VALUES",
            "<foreach collection='orderDetails' item='detail' separator=','>",
            "(#{detail.orderId}, #{detail.productId}, #{detail.quantity}, #{detail.unitPrice})",
            "</foreach>",
            "</script>"
    })
    int insertBulk(List<OrderDetail> orderDetails);
}