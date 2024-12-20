package hyper.darye.mapper;

import hyper.darye.dto.Member;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface MemberMapper {
    int insertSelective(Member record);

    @Select("SELECT * FROM MEMBER WHERE email = #{email}")
    Member selectByEmail(String email);

    @Update("UPDATE MEMBER SET LATEST_LOGIN_DATE = CURRENT_TIMESTAMP WHERE ID = #{id}")
    int updateLatestSignInDate(Long id);

    int softDeleteByPrimaryKey(Long id);

    Member selectByPrimaryKey(Long id);

    int deleteByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Member record);

    int updateByPrimaryKey(Member record);

    @Update("UPDATE MEMBER SET point = point + #{point} WHERE id = #{id}")
    int updatePoint(Long id, int point);

    @Update("UPDATE MEMBER SET point = point - #{point} WHERE id = #{id}")
    int usePoint(Long id, int point);
}