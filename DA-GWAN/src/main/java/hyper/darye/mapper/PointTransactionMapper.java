package hyper.darye.mapper;

import hyper.darye.dto.PointTransaction;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

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
            "#{memberId}, #{PointTranscationTypeId}, #{OrderMainId}, #{Amount}, #{Description})")
    int insertPointTransaction(PointTransaction record);
}