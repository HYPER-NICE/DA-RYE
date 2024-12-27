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

    @Update("UPDATE MEMBER SET LATEST_SIGN_IN_DATE = CURRENT_TIMESTAMP WHERE ID = #{id}")
    int updateLatestSignInDate(Long id);

    int softDeleteByPrimaryKey(Long id);

    Member selectByPrimaryKey(Long id);

    int deleteByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Member record);

    int updateByPrimaryKey(Member record);

    @Update("update MEMBER " +
            "set PASSWORD = #{newPassword}, " +
            "LAST_MODIFIED_DATE = CURRENT_TIMESTAMP, " +
            "LAST_MODIFIED_MEMBER = #{id} " +
            "where ID = #{id}")
    void updatePassword(Long id, String newPassword);

    @Update("UPDATE MEMBER SET point = point + #{point} WHERE id = #{id}")
    int addPoint(Long id, int point);

    @Update("UPDATE MEMBER SET point = point - #{point} WHERE id = #{id}")
    int usePoint(Long id, int point);
}