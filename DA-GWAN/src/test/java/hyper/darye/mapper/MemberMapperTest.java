package hyper.darye.mapper;

import hyper.darye.dto.Member;
import hyper.darye.dto.controller.request.CreateMemberRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

import static org.assertj.core.api.Assertions.as;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class MemberMapperTest {

    @Autowired
    private MemberMapper memberMapper;

    @Test
    void insertMemberTest() {
        Date birthdate = new Date(2001 - 1900, 0, 1);
        CreateMemberRequest member = new CreateMemberRequest(0L, "test@example.com", "password123",
                "password123", "username", 'M', birthdate, "010-1234-5678");

        memberMapper.insertMember(member);

        assertThat(member.getId()).isNotNull();
        assertEquals("test@example.com", member.getEmail());
        assertEquals("password123", member.getPassword());
        assertEquals("password123", member.getRePassword());
        assertEquals("username", member.getName());
        assertEquals(birthdate, member.getBirthdate());
        assertEquals("010-1234-5678", member.getMobile());
    }

    @Test
    void selectMemberByEmailTest() {
        Date birthdate = new Date(2001 - 1900, 0, 1);
        CreateMemberRequest member = new CreateMemberRequest(0L, "test@example.com", "password123",
                "password123", "username", 'M', birthdate, "010-1234-5678");
        memberMapper.insertMember(member);
        Member foundMember = memberMapper.selectMemberByEmail(member.getEmail());
        assertThat(foundMember).isNotNull();
        assertThat(foundMember.getEmail()).isEqualTo("test@example.com");
    }
}