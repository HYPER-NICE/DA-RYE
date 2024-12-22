package hyper.darye.mapper;

import hyper.darye.dto.Cart;
import hyper.darye.dto.CartSelect;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CartMapper {

    // 장바구니 넣기
    @Insert("insert into CART (MEMBER_ID, PRODUCT_ID, QUANTITY) " +
            "values (#{memberId}, #{productId}, #{quantity})")
    int insertCart(Cart cart);

    // 장바구니 조회
    @Select("select c.ID as CART_ID, p.ID as PRODUCT_ID, m.ID as MEMBER_ID, " +
            "c.QUANTITY as QUANTITY , p.NAME as PRODUCT_NAME, p.PRICE as PRODUCT_PRICE, c.DELETED_DATE " +
            "from MEMBER as m inner join CART as c on m.ID = c.MEMBER_ID " +
            "inner join PRODUCT as p on c.PRODUCT_ID = p.ID " +
            "where m.ID=#{memberId} and c.DELETED_DATE IS NULL")
    List<CartSelect> selectCart(Long memberId);

    // 장바구니 수량 증가

    @Update("update CART " +
            "set QUANTITY = QUANTITY + #{quantity} " +
            "where PRODUCT_ID = #{productID} and MEMBER_ID = #{memberID}")
    int updateCart(Long productID,
                   Long memberID,
                   Long quantity);

    // 장바구니 중복 찾기
    @Select("SELECT * FROM CART " +
            "WHERE MEMBER_ID = #{memberId} AND PRODUCT_ID = #{productId}")
    Cart selectCartDuplicate(Long memberId, Long productId);

    // 장바구니 수량 변경
    @Update("update CART " +
            "set QUANTITY = #{quantity} " +
            "where MEMBER_ID = #{memberId} and PRODUCT_ID = #{productId}")
    int updateCartQuantity(Long memberId, Long productId, Long quantity);



    // 장바구니 선택 삭제
    @Delete("<script>" +
            "DELETE FROM CART " +
            "WHERE MEMBER_ID = #{memberId} AND PRODUCT_ID IN " +
            "<foreach item='productId' collection='productIds' open='(' separator=',' close=')'>" +
            "#{productId}" +
            "</foreach>" +
            "</script>")
    int deleteCart(@Param("memberId") Long memberId, @Param("productIds") List<Long> productIdList);
//
//    int deleteByPrimaryKey(Long id);
//
//    int insert(Cart record);
//
//    int insertSelective(Cart record);
//
//    Cart selectByPrimaryKey(Long id);
//
//    int updateByPrimaryKeySelective(Cart record);
//
//    int updateByPrimaryKey(Cart record);
}