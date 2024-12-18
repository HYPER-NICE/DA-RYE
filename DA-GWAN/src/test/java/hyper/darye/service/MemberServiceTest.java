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
        Date birthdate = new Date(2001 - 1900, 0, 1);
        int result = memberService.insertMember("test@example.com", "password123", "password123", "username", 'M', birthdate, "010-1234-5678");

        assertEquals(1, result);
    }

    @Test
    void selectMemberByValidEmail() {
        Date birthdate = new Date(2001 - 1900, 0, 1);
        memberService.insertMember("test@example.com", "password123", "password123", "username", 'M', birthdate, "010-1234-5678");

        Member foundMember = memberService.selectMemberByEmail("test@example.com");

        assertNotNull(foundMember);
        assertEquals("test@example.com", foundMember.getEmail());
    }

    @Test
    void selectMemberByInvalidEmail() {
        assertThrows(NoSuchElementException.class, () -> {
            memberService.selectMemberByEmail("null@example.com");
        });
    }
}