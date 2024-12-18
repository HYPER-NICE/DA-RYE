package hyper.darye.service;

import hyper.darye.dto.controller.request.CreateMemberRequest;
import hyper.darye.mapper.MemberMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;
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
}