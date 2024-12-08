package hyper.darye.dao;

import hyper.darye.dto.InsertMemberDto;
import hyper.darye.model.Member;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@MybatisTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class MemberDaoTest {

    @Autowired
    private MemberDao memberDao;

    @DisplayName("멤버 추가 후 조회 테스트")
    @Test
    void selectAll() {
        // Given: 데이터 삽입
        String email1 = "a@email.com";
        String name1 = "홍길동";
        String password1 = "1234";
        String mobile1 = "010-1234-5678";
        String address1 = "서울 어딘가";

        String email2 = "b@email.com";
        String name2 = "홍길순";
        String password2 = "1234";
        String mobile2 = "010-1234-5678";
        String address2 = "서울 어딘가";

        InsertMemberDto member1 = new InsertMemberDto(email1, name1, password1, mobile1, address1);
        memberDao.insert(member1);
        InsertMemberDto member2 = new InsertMemberDto(email2, name2, password2, mobile2, address2);
        memberDao.insert(member2);

        // When: 데이터 조회
        List<Member> members = memberDao.selectAll();

        // Then: 결과 검증
        // 사이즈가 2이상이여야한다.
        assertThat(members).hasSizeGreaterThanOrEqualTo(2);
    }

    @DisplayName("마이바티스 타임스탬프 매핑 테스트")
    @Test
    void timeStampMappingTest() {
        // Given: 데이터 삽입
        InsertMemberDto member1 = new InsertMemberDto(
                "a@email.com", "홍길동", "1234", "010-1234-5678", "서울 어딘가");
        memberDao.insert(member1);

        // When: 데이터 조회
        Member member = memberDao.selectByEmail(member1.getEmail());

        // Then: 결과 검증
        assertThat(member.getCreatedDate()).isNotNull();
        assertThat(member.getLastModifiedDate()).isNotNull();
    }
}
