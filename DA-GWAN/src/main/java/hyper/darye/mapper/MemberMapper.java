package hyper.darye.mapper;

import hyper.darye.dto.Member;
import hyper.darye.dto.Member;
import hyper.darye.dto.controller.request.CreateMemberRequest;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

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

    int deleteByPrimaryKey(Long id);

    int insert(Member record);

    int insertSelective(Member record);


    int updateByPrimaryKeySelective(Member record);

    int updateByPrimaryKey(Member record);

}