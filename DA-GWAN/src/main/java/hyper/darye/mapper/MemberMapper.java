package hyper.darye.mapper;

import hyper.darye.dto.controller.request.CreateMemberRequest;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;

@Mapper
public interface MemberMapper {
    @Insert("INSERT INTO MEMBER (email, password, name, sex, birthdate, mobile) " +
            "VALUES (#{email}, #{password}, #{name}, #{sex}, #{birthdate}, #{mobile})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insertMember(CreateMemberRequest member);
}
