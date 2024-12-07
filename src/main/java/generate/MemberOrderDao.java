package generate;

import generate.MemberOrder;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberOrderDao {
    int deleteByPrimaryKey(Long id);

    int insert(MemberOrder record);

    int insertSelective(MemberOrder record);

    MemberOrder selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(MemberOrder record);

    int updateByPrimaryKey(MemberOrder record);
}