package hyper.darye.service;

import hyper.darye.dto.Member;
import hyper.darye.dto.SignUp;
import hyper.darye.mapper.MemberMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Date;
import java.util.NoSuchElementException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MemberServiceUnitTest {

    @Mock
    private MemberMapper memberMapper;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private MemberService memberService;

    private SignUp signUp;
    private Member expectedMember;

    @BeforeEach
    void setUp() {
        signUp = createSignUp();
        expectedMember = createExpectedMember();
    }

    private SignUp createSignUp() {
        SignUp signUp = new SignUp();
        signUp.setName("John Doe");
        signUp.setEmail("john.doe@example.com");
        signUp.setPassword("P@ssword123");
        signUp.setConfirmPassword("P@ssword123");
        signUp.setContact("010-1234-5678");
        return signUp;
    }

    private Member createExpectedMember() {
        Member member = new Member();
        member.setId(1L);
        member.setName(signUp.getName());
        member.setEmail(signUp.getEmail());
        member.setPassword("EncodedP@ssword123");
        member.setContact(signUp.getContact());
        member.setCreatedDate(new Date());
        return member;
    }

    @Nested
    @DisplayName("회원 생성 테스트")
    class InsertSelectiveMemberTest {

        @Test
        @DisplayName("회원 생성 - 성공")
        void insertSelectiveMemberTest_Success() {
            // Given
            when(passwordEncoder.encode(signUp.getPassword())).thenReturn("EncodedP@ssword123");
            when(memberMapper.insertSelective(any(Member.class))).thenReturn(1);

            // When
            int result = memberService.insertSelective(signUp);

            // Then
            assertEquals(1, result);
            ArgumentCaptor<Member> captor = ArgumentCaptor.forClass(Member.class);
            verify(memberMapper).insertSelective(captor.capture());
            assertThat(captor.getValue()).usingRecursiveComparison()
                    .ignoringFields("id", "createdDate")
                    .isEqualTo(expectedMember);
        }

        @Test
        @DisplayName("회원 생성 - 실패 (매퍼가 0을 반환)")
        void insertSelectiveMemberTest_Failure() {
            // Given
            when(passwordEncoder.encode(signUp.getPassword())).thenReturn("EncodedP@ssword123");
            when(memberMapper.insertSelective(any(Member.class))).thenReturn(0);

            // When
            int result = memberService.insertSelective(signUp);

            // Then
            assertEquals(0, result);
            verify(memberMapper).insertSelective(any(Member.class));
        }
    }

    @Nested
    @DisplayName("회원 조회 테스트")
    class MemberQueryTest {
        @Test
        @DisplayName("유효한 회원 ID로 회원 조회")
        void selectMemberByValidIdTest() {
            when(memberMapper.selectByPrimaryKey(1L)).thenReturn(expectedMember);
            Member foundMember = memberService.selectByPrimaryKey(1L);
            assertNotNull(foundMember);
            assertEquals(1L, foundMember.getId());
            verify(memberMapper).selectByPrimaryKey(1L);
        }

        @Test
        @DisplayName("존재하지 않는 회원 ID로 회원 조회")
        void selectMemberByInvalidIdTest() {
            when(memberMapper.selectByPrimaryKey(0L)).thenReturn(null);
            NoSuchElementException exception = assertThrows(NoSuchElementException.class, () -> {
                memberService.selectByPrimaryKey(0L);
            });
            assertEquals("존재하지 않는 키입니다.", exception.getMessage());
            verify(memberMapper).selectByPrimaryKey(0L);
        }

        @Test
        @DisplayName("유효한 이메일로 회원 조회")
        void selectMemberByValidEmailTest() {
            when(memberMapper.selectByEmail(signUp.getEmail())).thenReturn(expectedMember);
            Member foundMember = memberService.selectByEmail(signUp.getEmail());
            assertNotNull(foundMember);
            assertEquals(signUp.getEmail(), foundMember.getEmail());
            verify(memberMapper).selectByEmail(signUp.getEmail());
        }

        @Test
        @DisplayName("존재하지 않는 이메일로 회원 조회")
        void selectMemberByInvalidEmailTest() {
            when(memberMapper.selectByEmail("invalid@example.com")).thenReturn(null);
            NoSuchElementException exception = assertThrows(NoSuchElementException.class, () -> {
                memberService.selectByEmail("invalid@example.com");
            });
            assertEquals("존재하지 않는 이메일입니다.", exception.getMessage());
            verify(memberMapper).selectByEmail("invalid@example.com");
        }
    }

    @Nested
    @DisplayName("회원 사인인 업데이트 테스트")
    class UpdateLatestSignInDateTest {

        @Test
        @DisplayName("회원 최신 사인인 날짜 업데이트")
        void updateLatestSignInDateSuccessTest() {
            when(memberMapper.updateLatestSignInDate(1L)).thenReturn(1);
            memberService.updateLatestSignInDate(1L);
            verify(memberMapper).updateLatestSignInDate(1L);
        }

        @Test
        @DisplayName("회원 최신 사인인 날짜 업데이트 실패")
        void updateLatestSignInDateFailureTest() {
            when(memberMapper.updateLatestSignInDate(1L)).thenReturn(0);
            assertThrows(NoSuchElementException.class, () -> {
                memberService.updateLatestSignInDate(1L);
            });
            verify(memberMapper).updateLatestSignInDate(1L);
        }
    }
}
