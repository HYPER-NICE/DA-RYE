package hyper.darye.dao;

import hyper.darye.model.Member;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;

import static org.assertj.core.api.Assertions.assertThat;

@MybatisTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class MemberDaoTest {

    @Autowired
    private MemberDao memberDao;

    String email = "test@email.com";
    String name = "테스터";
    String password = "1234";
    String mobile = "010-1234-5678";
    String address = "서울 어딘가";

    @DisplayName("회원가입 테스트")
    @Test
    void testInsertMember() {
        // Given
        Member member = new Member(email, name, password, mobile, address);

        // When
        int count = memberDao.insert(member);

        // Then
        assertThat(count)
                .withFailMessage("회원가입이 성공했을 경우 1이 반환되어야 합니다. 반환된 값: %s", count)
                .isEqualTo(1);

        assertThat(member.getId())
                .withFailMessage("회원가입 후에는 자동 생성된 ID가 있어야 합니다. 현재 값: %s", member.getId())
                .isNotNull();
    }

    @DisplayName("회원 조회 테스트 - 기본키로 조회")
    @Test
    void testSelectMemberById() {
        // Given
        Member member = new Member(email, name, password, mobile, address);
        memberDao.insert(member);

        // When
        Member foundMember = memberDao.selectById(member.getId());

        // Then
        assertThat(foundMember)
                .withFailMessage("회원 삽입 후 ID로 조회 시 회원이 NULL이 아닙니다.")
                .isNotNull();

        assertThat(foundMember.getName())
                .withFailMessage("조회된 회원 이름이 일치하지 않습니다. 실제 값: %s, 예상 값: %s", foundMember.getName(), name)
                .isEqualTo(name);

        assertThat(foundMember.getEmail())
                .withFailMessage("조회된 이메일이 일치하지 않습니다. 실제 값: %s, 예상 값: %s", foundMember.getEmail(), email)
                .isEqualTo(email);
    }

    @DisplayName("회원 조회 테스트 - 이메일로 조회")
    @Test
    void testSelectMemberByEmail() {
        // Given
        Member member = new Member(email, name, password, mobile, address);
        memberDao.insert(member);

        // When
        Member foundMember = memberDao.selectByEmail(member.getEmail());

        // Then
        assertThat(foundMember)
                .withFailMessage("이메일로 조회 시 회원이 NULL이 아닙니다.")
                .isNotNull();

        assertThat(foundMember.getName())
                .withFailMessage("조회된 회원 이름이 일치하지 않습니다. 실제 값: %s, 예상 값: %s", foundMember.getName(), name)
                .isEqualTo(name);
    }

    @DisplayName("회원정보 수정 테스트")
    @Test
    void testUpdateMember() {
        // Given
        Member member = new Member(email, name, password, mobile, address);
        memberDao.insert(member);
        Member savedMember = memberDao.selectByEmail(member.getEmail());

        // When
        savedMember.setName("변경된 이름");
        savedMember.setMobile("010-3333-4444");
        savedMember.setAddress("부산 어딘가");
        memberDao.update(savedMember);

        // Then
        Member updatedMember = memberDao.selectByEmail(member.getEmail());

        assertThat(updatedMember)
                .withFailMessage("수정된 회원이 NULL이 아닙니다.")
                .isNotNull();

        assertThat(updatedMember.getName())
                .withFailMessage("회원 이름이 일치하지 않습니다. 실제 값: %s, 예상 값: %s", updatedMember.getName(), "변경된 이름")
                .isEqualTo("변경된 이름");

        assertThat(updatedMember.getMobile())
                .withFailMessage("회원 전화번호가 일치하지 않습니다. 실제 값: %s, 예상 값: %s", updatedMember.getMobile(), "010-3333-4444")
                .isEqualTo("010-3333-4444");

        assertThat(updatedMember.getAddress())
                .withFailMessage("회원 주소가 일치하지 않습니다. 실제 값: %s, 예상 값: %s", updatedMember.getAddress(), "부산 어딘가")
                .isEqualTo("부산 어딘가");
    }

    @DisplayName("회원 탈퇴 테스트 (소프트 삭제)")
    @Test
    void testDeleteMember() {
        // Given
        Member member = new Member(email, name, password, mobile, address);
        memberDao.insert(member);
        Member savedMember = memberDao.selectByEmail(member.getEmail());

        assertThat(savedMember)
                .withFailMessage("회원 삽입 후 삭제 전에 NULL이 아니어야 합니다.")
                .isNotNull();

        // When
        memberDao.delete(savedMember.getId());

        // Then
        Member deletedMember = memberDao.selectByEmail(member.getEmail());

        assertThat(deletedMember)
                .withFailMessage("회원 탈퇴(소프트 삭제) 후 회원은 NULL이어야 합니다.")
                .isNull();
    }
}
