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

    @Select("SELECT * FROM MEMBER " +
            "WHERE email = #{email}")
    Member selectMemberByEmail(String email);

    Member selectMemberById(Long id);

    int softDeleteMemberById(Long id);

    void updateMemberByIdSelective(Member member);

    int insertSelective(Member record);

    int updateByPrimaryKey(Member record);
}