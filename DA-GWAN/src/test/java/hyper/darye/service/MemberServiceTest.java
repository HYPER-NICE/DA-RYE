package hyper.darye.service;

import hyper.darye.dto.Member;
import hyper.darye.dto.SignUp;
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
        SignUp signUp = new SignUp("John Doe", "john.doe@example.com",
                "P@ssword123", "P@ssword123", "010-1234-5678", "USER");

        int result = memberService.insert(signUp);
        assertEquals(1, result);
    }

    @Test
    void selectMemberByValidEmailTest() {
        SignUp signUp = new SignUp("John Doe", "john.doe@example.com",
                "P@ssword123", "P@ssword123", "010-1234-5678", "USER");
        memberService.insert(signUp);

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
        SignUp signUp = new SignUp("John Doe", "john.doe@example.com",
                "P@ssword123", "P@ssword123", "010-1234-5678", "USER");
        memberService.insert(signUp);

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
        SignUp signUp = new SignUp("John Doe", "john.doe@example.com",
                "P@ssword123", "P@ssword123", "010-1234-5678", "USER");
        memberService.insert(signUp);

        Member insertedMember = memberService.selectMemberByEmail("john.doe@example.com");
        Long paramId = insertedMember.getId();

        memberService.softDeleteMemberById(paramId);
        Member softDeletedMember = memberService.selectMemberById(paramId);

        assertNotNull(softDeletedMember.getDeletedDate());
    }

    @Test
    void updateMemberByIdSelectiveWithOneFieldTest() {
        SignUp signUp = new SignUp("John Doe", "john.doe@example.com",
                "P@ssword123", "P@ssword123", "010-1234-5678", "USER");
        memberService.insert(signUp);

        Member insertedMember = memberService.selectMemberByEmail("john.doe@example.com");
        insertedMember.setEmail("john.doe2@example.com");

        memberService.updateMemberByIdSelective(insertedMember);
        Member softDeletedMember = memberService.selectMemberById(insertedMember.getId());

        assertEquals("john.doe2@example.com", softDeletedMember.getEmail());
        assertEquals("John Doe", softDeletedMember.getName());
//        assertEquals(birthdate, updatedMember.getBirthdate());  // Date 타입 때문에 충돌. 나중에 LocalDate로 일괄 변경?
    }

    @Test
    void updateMemberByIdSelectiveWithMultipleFieldsTest() {
        SignUp signUp = new SignUp("John Doe", "john.doe@example.com",
                "P@ssword123", "P@ssword123", "010-1234-5678", "USER");
        memberService.insert(signUp);

        Member insertedMember = memberService.selectMemberByEmail("john.doe@example.com");
        insertedMember.setEmail("jane.smith@example.com");
        insertedMember.setName("Jane Smith");
        insertedMember.setSex('F');

        memberService.updateMemberByIdSelective(insertedMember);
        Member updatedMember = memberService.selectMemberById(insertedMember.getId());

        assertEquals("jane.smith@example.com", updatedMember.getEmail());
        assertEquals("Jane Smith", updatedMember.getName());
        assertEquals('F', updatedMember.getSex());
//        assertEquals("p123", updatedMember.getPassword());    // 비밀번호 암호화로 비교 불가
//        assertEquals(birthdate, updatedMember.getBirthdate());
    }
}