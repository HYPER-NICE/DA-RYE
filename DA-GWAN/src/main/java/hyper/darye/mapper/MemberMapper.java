package hyper.darye.mapper;

import hyper.darye.dto.Member;
import hyper.darye.dto.controller.request.CreateMemberRequest;
import org.apache.ibatis.annotations.*;

@Mapper
public interface MemberMapper {
    @Insert("INSERT INTO MEMBER (email, password, name, sex, birthdate, mobile) " +
            "VALUES (#{email}, #{password}, #{name}, #{sex}, #{birthdate}, #{mobile})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insertMember(CreateMemberRequest member);

    @Select("SELECT * FROM MEMBER WHERE email = #{email}")
    Member selectByEmail(String email);

    @Update("UPDATE MEMBER SET LATEST_LOGIN_DATE = NOW() WHERE EMAIL = #{email}")
    int updateLatestLoginDate(String email);
    
    int deleteByPrimaryKey(Long id);

    int insert(Member record);

    int insertSelective(Member record);

    Member selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Member record);

    int updateByPrimaryKey(Member record);
}