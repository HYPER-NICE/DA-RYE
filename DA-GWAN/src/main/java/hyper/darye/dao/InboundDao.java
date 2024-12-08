package hyper.darye.dao;

import hyper.darye.model.Inbound;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface InboundDao {

    // ====================
    // 삽입 (INSERT)
    // ====================
    @Insert("""
        INSERT INTO inbound (
            product_id, batch_number, quantity, purchase_price, expiry_date, received_date
        ) VALUES (
            #{productId}, #{batchNumber}, #{quantity}, #{purchasePrice}, #{expiryDate}, #{receivedDate}
        )
    """)
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(Inbound record);

    // ====================
    // 단일 조회 (SELECT)
    // ====================
    @Select("""
        SELECT * 
        FROM inbound 
        WHERE id = #{id} 
        AND deleted_date IS NULL
    """)
    Inbound selectByPrimaryKey(@Param("id") Long id);

    // ====================
    // 전체 목록 조회 (SELECT ALL)
    // ====================
    @Select("""
        SELECT * 
        FROM inbound 
        WHERE deleted_date IS NULL
        ORDER BY received_date DESC
    """)
    List<Inbound> selectAll();

    // ====================
    // 업데이트 (UPDATE)
    // ====================
    @Update("""
        UPDATE inbound 
        SET 
            product_id = #{productId}, 
            batch_number = #{batchNumber}, 
            quantity = #{quantity}, 
            purchase_price = #{purchasePrice}, 
            expiry_date = #{expiryDate}, 
            received_date = #{receivedDate}
        WHERE id = #{id} 
        AND deleted_date IS NULL
    """)
    int updateByPrimaryKey(Inbound record);

    // ====================
    // 소프트 삭제 (DELETE)
    // ====================
    @Update("""
        UPDATE inbound 
        SET deleted_date = NOW() 
        WHERE id = #{id} 
        AND deleted_date IS NULL
    """)
    int softDelete(@Param("id") Long id);
}
