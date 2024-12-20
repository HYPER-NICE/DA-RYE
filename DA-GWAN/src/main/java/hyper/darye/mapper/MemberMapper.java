package hyper.darye.mapper;

import hyper.darye.dto.Member;
import hyper.darye.dto.controller.request.CreateMemberRequest;
import org.apache.ibatis.annotations.*;

@Mapper
public interface MemberMapper {
    int insertSelective(Member record);

    @Select("SELECT * FROM MEMBER WHERE email = #{email}")
    Member selectByEmail(String email);

    @Update("UPDATE MEMBER SET LATEST_LOGIN_DATE = NOW() WHERE EMAIL = #{email}")
    int updateLatestLoginDate(String email);

    int softDeleteMemberById(Long id);

    Member selectByPrimaryKey(Long id);

    int deleteByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Member record);

    int updateByPrimaryKey(Member record);

    @Update("UPDATE MEMBER SET point = point + #{point} WHERE id = #{id}")
    int updatePoint(Long id, int point);

    @Update("UPDATE MEMBER SET point = point - #{point} WHERE id = #{id}")
    int usePoint(Long id, int point);
}