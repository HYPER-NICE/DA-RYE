package hyper.darye.mapper;

import hyper.darye.dto.Member;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.transaction.annotation.Transactional;

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
        member.setContact("010-1234-5679");

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
    void selectByPrimaryKeyTest() {
        Member foundMember = memberMapper.selectByPrimaryKey(1L);

        assertThat(foundMember.getId()).isEqualTo(1L);
    }

    @Test
    void softDeleteByPrimaryKeyTest() {
        memberMapper.softDeleteByPrimaryKey(1L);

        Member insertedMember = memberMapper.selectByPrimaryKey(1L);
        assertThat(insertedMember.getDeletedDate()).isNotNull();
    }

    @Test
    void updateByPrimaryKeySelectiveTest() {
        Member member = memberMapper.selectByPrimaryKey(1L);

        member.setEmail("john.doe2@example.com");
        Member updatedMember = memberMapper.selectByPrimaryKey(1L);

        assertThat(updatedMember)
                .usingRecursiveComparison()
                .ignoringFields("email", "lastModifiedDate")
                .isEqualTo(member);
    }

    @Test
    void updatePasswordTest() {
        Member member = memberMapper.selectByPrimaryKey(2L);

        memberMapper.updatePassword(2L, "p321");
        Member updatedMember = memberMapper.selectByEmail(member.getEmail());

        assertThat(updatedMember.getPassword()).isEqualTo("p321");
        assertThat(updatedMember.getEmail()).isEqualTo("john.doe@example.com");
        assertThat(updatedMember.getContact()).isEqualTo("010-1234-5678");
    }

    @Test
    void findEmailByContactTest() {
        Member member = memberMapper.selectByPrimaryKey(2L);

        String foundEmail = memberMapper.findEmailByContact(member.getContact());
        assertThat(foundEmail).isEqualTo("john.doe@example.com");
    }
}