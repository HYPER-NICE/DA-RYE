package hyper.darye.mapper;

import hyper.darye.dto.Member;
import hyper.darye.dto.controller.request.CreateMemberRequest;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@MybatisTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Transactional
class MemberMapperUnitTest {

    @Autowired
    private MemberMapper memberMapper;

    @Test
    void insertSelectiveTest() {
        Member member = new Member();
        member.setEmail("jane.doe@example.com");
        member.setPassword("p123");
        member.setName("Jane Doe");
        member.setMobile("010-1234-5679");

        int result = memberMapper.insertSelective(member);

        assertEquals(1, result);

        Member insertedMember = memberMapper.selectByEmail("jane.doe@example.com");
        assertNotNull(insertedMember);
        assertEquals("jane.doe@example.com", insertedMember.getEmail());
    }

    @Test
    void selectMemberByEmailTest() {
        Member foundMember = memberMapper.selectByEmail("john.doe@example.com");

        assertThat(foundMember).isNotNull();
        assertThat(foundMember.getEmail()).isEqualTo("john.doe@example.com");
    }

    @Test
    void selectMemberByIdTest() {
        Member foundMember = memberMapper.selectByPrimaryKey(1L);

        assertThat(foundMember.getId()).isEqualTo(1L);
    }

    @Test
    void softDeleteMemberByIdTest() {
        memberMapper.softDeleteMemberById(1L);

        Member insertedMember = memberMapper.selectByPrimaryKey(1L);
        assertThat(insertedMember.getDeletedDate()).isNotNull();
    }

    @Test
    void updateByPrimaryKeySelectiveTest() {
        Member member = memberMapper.selectByPrimaryKey(1L);

        member.setEmail("john.doe2@example.com");
        Member updatedMember = memberMapper.selectByPrimaryKey(1L);

        assertThat(updatedMember.getEmail()).isEqualTo("john.doe2@example.com");
        assertThat(updatedMember.getName()).isEqualTo("John Doe");
    }
}