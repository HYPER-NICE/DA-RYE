package hyper.darye.dao;

import hyper.darye.model.Member;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface MemberDao {

    @Select("SELECT * FROM member WHERE deleted_date IS NULL")
    List<Member> selectAll();

    @Insert("INSERT INTO member (email, name, password, mobile, address) " +
            "VALUES (#{email}, #{name}, #{password}, #{mobile}, #{address})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(Member member);

    @Select("SELECT * FROM member WHERE id = #{id} AND deleted_date IS NULL")
    Member selectById(Long id);

    @Select("SELECT * FROM member WHERE email = #{email} AND deleted_date IS NULL")
    Member selectByEmail(String email);

    @Update("UPDATE member SET name=#{name}, mobile=#{mobile}, address=#{address} WHERE id = #{id}")
    void update(Member member);

    @Update("UPDATE member SET deleted_date = CURRENT_TIMESTAMP(6) WHERE id = #{id}")
    void delete(Long id);
}
