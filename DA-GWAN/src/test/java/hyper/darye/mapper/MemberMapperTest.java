package hyper.darye.mapper;

import hyper.darye.dto.Member;
import hyper.darye.dto.controller.request.CreateMemberRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

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
        Date birthdate = new Date(1990 - 1900, 0, 1);
        CreateMemberRequest member = new CreateMemberRequest(0L, "john.doe@example.com", "p123",
                "p123","John Doe", 'M', birthdate, "010-1234-5678");

        memberMapper.insertMember(member);
        Member foundMember = memberMapper.selectByEmail(member.getEmail());

        assertThat(foundMember).isNotNull();
        assertThat(foundMember.getEmail()).isEqualTo("john.doe@example.com");
    }

    @Test
    void selectMemberByIdTest() {
        Date birthdate = new Date(1990 - 1900, 0, 1);
        CreateMemberRequest member = new CreateMemberRequest(0L, "john.doe@example.com", "p123",
                "p123","John Doe", 'M', birthdate, "010-1234-5678");

        memberMapper.insertMember(member);
        Long paramId = member.getId();
        Member foundMember = memberMapper.selectMemberById(paramId);

        assertThat(foundMember.getId()).isEqualTo(paramId);
    }

    @Test
    void softDeleteMemberByIdTest() {
        Date birthdate = new Date(1990 - 1900, 0, 1);
        CreateMemberRequest member = new CreateMemberRequest(0L, "john.doe@example.com", "p123",
                "p123","John Doe", 'M', birthdate, "010-1234-5678");

        memberMapper.insertMember(member);
        Long paramId = member.getId();

        memberMapper.softDeleteMemberById(paramId);

        Member insertedMember = memberMapper.selectMemberById(paramId);
        assertThat(insertedMember.getDeletedDate()).isNotNull();
    }
}