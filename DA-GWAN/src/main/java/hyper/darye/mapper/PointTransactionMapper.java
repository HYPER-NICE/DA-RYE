package hyper.darye.mapper;

import hyper.darye.dto.PointTransaction;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface PointTransactionMapper {
    int deleteByPrimaryKey(Long id);

    int insert(PointTransaction record);

    int insertSelective(PointTransaction record);

    PointTransaction selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(PointTransaction record);

    int updateByPrimaryKey(PointTransaction record);

    @Insert("insert into POINT_TRANSACTION " +
            "(MEMBER_ID, POINT_TRANSACTION_TYPE_ID, ORDER_MAIN_ID, AMOUNT, DESCRIPTION) " +
            "VALUES (" +
            "#{memberId}, #{pointTransactionTypeId}, #{orderMainId}, #{amount}, #{description})")
    int insertPointTransaction(PointTransaction record);

    @Select("select * from POINT_TRANSACTION where MEMBER_ID = #{memberId}")
    PointTransaction selectByMemberId(Long memberId);

    @Select("select * from POINT_TRANSACTION where MEMBER_ID = #{memberId} and ORDER_MAIN_ID = #{orderId}")
    PointTransaction selectByMemberIdAndOrderId(Long memberId, Long orderId);
}