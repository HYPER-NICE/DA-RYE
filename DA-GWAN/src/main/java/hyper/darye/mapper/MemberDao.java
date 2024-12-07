package hyper.darye.mapper;

import hyper.darye.dto.InsertMemberDto;
import hyper.darye.model.Member;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface MemberDao {

    @Select("SELECT * FROM member")
    List<Member> selectAll();

    @Insert("INSERT INTO member (email, name, password, mobile, address) VALUES (#{email}, #{name}, #{password}, #{mobile}, #{address})")
    void insert(InsertMemberDto member);

    @Select("SELECT * FROM member WHERE id = #{id}")
    Member selectById(Long id);

    @Select("SELECT * FROM member WHERE email = #{email}")
    Member selectByEmail(String email);
}
