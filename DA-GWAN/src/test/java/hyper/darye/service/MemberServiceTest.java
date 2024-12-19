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
    void insertMember() {
        Date birthdate = new Date(1990 - 1900, 0, 1);
        int result = memberService.insertMember("john.doe@example.com", "p123",
                "p123","John Doe", 'M', birthdate, "010-1234-5678");
        assertEquals(1, result);
    }

    @Test
    void selectMemberByValidEmail() {
        Date birthdate = new Date(1990 - 1900, 0, 1);
        memberService.insertMember("john.doe@example.com", "p123",
                "p123","John Doe", 'M', birthdate, "010-1234-5678");

        Member foundMember = memberService.selectMemberByEmail("john.doe@example.com");

        assertNotNull(foundMember);
        assertEquals("john.doe@example.com", foundMember.getEmail());
    }

    @Test
    void selectMemberByInvalidEmail() {
        assertThrows(NoSuchElementException.class, () -> {
            memberService.selectMemberByEmail("null@example.com");
        });
    }

    @Test
    void selectMemberByValidId() {
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
    void selectMemberByInvalidId() {
        assertThrows(NoSuchElementException.class, () -> {
            memberService.selectMemberById(0L);
        });
    }

    @Test
    void softDeleteMemberById() {
        Date birthdate = new Date(1990 - 1900, 0, 1);
        memberService.insertMember("john.doe@example.com", "p123",
                "p123","John Doe", 'M', birthdate, "010-1234-5678");

        Member insertedMember = memberService.selectMemberByEmail("john.doe@example.com");
        Long paramId = insertedMember.getId();

        memberService.softDeleteMemberById(paramId);
        Member softDeletedMember = memberService.selectMemberById(paramId);

        assertNotNull(softDeletedMember.getDeletedDate());
    }
}