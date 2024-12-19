package hyper.darye.service;

import hyper.darye.dto.Member;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class MemberServiceTest {

    @Autowired
    private MemberService memberService;

    @Test
    void insertMemberTest() {
        Date birthdate = new Date(1990 - 1900, 0, 1);
        int result = memberService.insertMember("john.doe@example.com", "p123",
                "p123","John Doe", 'M', birthdate, "010-1234-5678");
        assertEquals(1, result);
    }

    @Test
    void selectMemberByValidEmailTest() {
        Date birthdate = new Date(1990 - 1900, 0, 1);
        memberService.insertMember("john.doe@example.com", "p123",
                "p123","John Doe", 'M', birthdate, "010-1234-5678");

        Member foundMember = memberService.selectMemberByEmail("john.doe@example.com");

        assertNotNull(foundMember);
        assertEquals("john.doe@example.com", foundMember.getEmail());
    }

    @Test
    void selectMemberByInvalidEmailTest() {
        assertThrows(NoSuchElementException.class, () -> {
            memberService.selectMemberByEmail("null@example.com");
        });
    }

    @Test
    void selectMemberByValidIdTest() {
        Date birthdate = new Date(1990 - 1900, 0, 1);
        memberService.insertMember("john.doe@example.com", "p123",
                "p123","John Doe", 'M', birthdate, "010-1234-5678");

        Member insertedMember = memberService.selectMemberByEmail("john.doe@example.com");
        Long paramId = insertedMember.getId();
        Member foundMember = memberService.selectMemberById(paramId);

        assertNotNull(foundMember);
        assertEquals(paramId, foundMember.getId());
    }

    @Test
    void selectMemberByInvalidIdTest() {
        assertThrows(NoSuchElementException.class, () -> {
            memberService.selectMemberById(0L);
        });
    }

    @Test
    void softDeleteMemberByIdTest() {
        Date birthdate = new Date(1990 - 1900, 0, 1);
        memberService.insertMember("john.doe@example.com", "p123",
                "p123","John Doe", 'M', birthdate, "010-1234-5678");

        Member insertedMember = memberService.selectMemberByEmail("john.doe@example.com");
        Long paramId = insertedMember.getId();

        memberService.softDeleteMemberById(paramId);
        Member softDeletedMember = memberService.selectMemberById(paramId);

        assertNotNull(softDeletedMember.getDeletedDate());
    }

    @Test
    void updateMemberByIdSelectiveWithOneFieldTest() {
        Date birthdate = new Date(1990 - 1900, 0, 1);
        memberService.insertMember("john.doe@example.com", "p123",
                "p123","John Doe", 'M', birthdate, "010-1234-5678");

        Member insertedMember = memberService.selectMemberByEmail("john.doe@example.com");
        insertedMember.setEmail("john.doe2@example.com");

        memberService.updateMemberByIdSelective(insertedMember);
        Member updatedMember = memberService.selectMemberById(insertedMember.getId());

        assertEquals("john.doe2@example.com", updatedMember.getEmail());
        assertEquals("John Doe", updatedMember.getName());
        assertEquals('M', updatedMember.getSex());
//        assertEquals(birthdate, updatedMember.getBirthdate());  // Date 타입 때문에 충돌. 나중에 LocalDate로 일괄 변경?
    }

    @Test
    void updateMemberByIdSelectiveWithMultipleFieldsTest() {
        Date birthdate = new Date(1990 - 1900, 0, 1);
        memberService.insertMember("john.doe@example.com", "p123",
                "p123","John Doe", 'M', birthdate, "010-1234-5678");

        Member insertedMember = memberService.selectMemberByEmail("john.doe@example.com");
        insertedMember.setEmail("jane.smith@example.com");
        insertedMember.setName("Jane Smith");
        insertedMember.setSex('F');

        memberService.updateMemberByIdSelective(insertedMember);
        Member updatedMember = memberService.selectMemberById(insertedMember.getId());

        assertEquals("jane.smith@example.com", updatedMember.getEmail());
        assertEquals("Jane Smith", updatedMember.getName());
        assertEquals('F', updatedMember.getSex());
        assertEquals("p123", updatedMember.getPassword());
//        assertEquals(birthdate, updatedMember.getBirthdate());
    }
}