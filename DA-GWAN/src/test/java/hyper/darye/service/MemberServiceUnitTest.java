package hyper.darye.service;

import hyper.darye.mvc.model.entity.Member;
import hyper.darye.mvc.model.entity.SignUp;
import hyper.darye.mvc.mapper.MemberMapper;
import hyper.darye.mvc.service.MemberService;
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
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.NoSuchElementException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@Transactional
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
        member.setRole("USER");
        member.setCreatedDate(new Date());
        return member;
    }

    @Nested
    @DisplayName("회원 생성 테스트")
    class InsertSelectiveMemberTests {

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
    class MemberQueryTests {
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
            assertEquals("등록되지 않은 이메일입니다.", exception.getMessage());
            verify(memberMapper).selectByEmail("invalid@example.com");
        }
    }

    @Nested
    @DisplayName("회원 사인인 업데이트 테스트")
    class UpdateLatestSignInDateTests {

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

    @Nested
    @DisplayName("회원 암호 수정 테스트")
    class UpdatePasswordTests {

        @Test
        @DisplayName("회원 암호 수정")
        void updatePasswordTest() {
            Member member = createExpectedMember();
            when(memberMapper.selectByPrimaryKey(1L)).thenReturn(member);

            String oldPassword = "P@ssword123";
            String encodedOldPassword = member.getPassword();
            String newPassword = "P@ssword321";
            String encodedNewPassword = "EncodedP@ssword321";

            when(passwordEncoder.matches(eq("P@ssword123"), eq("EncodedP@ssword123"))).thenReturn(true);
            when(passwordEncoder.encode(newPassword)).thenReturn(encodedNewPassword);

            doAnswer(invocation -> {
                member.setPassword(encodedNewPassword);
                return null;
            }).when(memberMapper).updatePassword(eq(1L), eq(encodedNewPassword));

            memberService.updatePassword(1L, oldPassword, newPassword, newPassword);

            assertEquals(encodedNewPassword, member.getPassword());
            verify(memberMapper).updatePassword(1L, encodedNewPassword);

            Member updatedMember = memberMapper.selectByPrimaryKey(1L);
            assertNotEquals(encodedOldPassword, updatedMember.getPassword());
            assertEquals(encodedNewPassword, updatedMember.getPassword());
        }

        @Test
        @DisplayName("기존 비밀번호 불일치")
        void updatePasswordByInvalidOldPasswordTest() {
            Member member = createExpectedMember();
            when(memberMapper.selectByPrimaryKey(1L)).thenReturn(member);

            String wrongOldPassword = "P@ssword1234";
            String newPassword = "P@ssword321";
            String encodedNewPassword = "EncodedP@ssword321";

            when(passwordEncoder.matches(eq(wrongOldPassword), eq("EncodedP@ssword123"))).thenReturn(false);

            assertThrows(IllegalArgumentException.class, () -> {
                memberService.updatePassword(1L, wrongOldPassword, newPassword, newPassword);
            });
            verify(memberMapper, never()).updatePassword(1L, encodedNewPassword);

            Member updatedMember = memberMapper.selectByPrimaryKey(1L);
            assertEquals(member.getPassword(), updatedMember.getPassword());
        }

        @Test
        @DisplayName("새 비밀번호 재입력 불일치")
        void updatePasswordByInvalidNewRePasswordTest() {
            Member member = createExpectedMember();
            when(memberMapper.selectByPrimaryKey(1L)).thenReturn(member);

            String oldPassword = "P@ssword123";
            String newPassword = "P@ssword321";
            String wrongNewConfirmPassword = "P@ssword3210";
            String encodedNewPassword = "EncodedP@ssword321";

            when(passwordEncoder.matches(eq(oldPassword), eq("EncodedP@ssword123"))).thenReturn(true);

            assertThrows(IllegalArgumentException.class, () -> {
                memberService.updatePassword(1L, oldPassword, newPassword, wrongNewConfirmPassword);
            });
            verify(memberMapper, never()).updatePassword(1L, encodedNewPassword);

            Member updatedMember = memberMapper.selectByPrimaryKey(1L);
            assertEquals(member.getPassword(), updatedMember.getPassword());
        }
    }

    @Nested
    @DisplayName("휴대폰 번호로 이메일 찾기")
    class findEmailByContactTests {

        @Test
        @DisplayName("휴대폰 번호로 이메일 찾기 - 성공")
        void findEmailByContactTest() {
            Member member = createExpectedMember();
            String contact = member.getContact();
            String expectedEmail = member.getEmail();

            when(memberMapper.findEmailByContact(contact)).thenReturn(expectedEmail);

            String foundEmail = memberService.findEmailByContact(member.getContact());
            assertEquals(member.getEmail(), foundEmail);
        }

        @Test
        @DisplayName("휴대폰 번호로 이메일 찾기 - 실패")
        void findEmailByContactFailTest() {
            String contact = "010-0000-0000";

            when(memberMapper.findEmailByContact(contact)).thenReturn(null);

            assertThrows(NoSuchElementException.class, () -> {
                memberService.findEmailByContact(contact);
            });
        }
    }

    @Nested
    @DisplayName("중복 검사 테스트")
    class DuplicateCheckTests {
        @Test
        @DisplayName("이메일이 이미 존재하는지 확인 - 이메일 존재")
        void isEmailTakenTestExisting() {
            // Given: 이미 존재하는 이메일을 반환하도록 모킹
            String email = "test@darye.dev";
            when(memberMapper.selectByEmail(email)).thenReturn(new Member()); // 이메일이 존재한다고 가정

            // When & Then
            assertTrue(memberService.isEmailTaken(email)); // 이메일이 존재하면 true 반환
        }

        @Test
        @DisplayName("이메일이 이미 존재하는지 확인 - 이메일 미존재")
        void isEmailTakenTestNotExisting() {
            // Given: 존재하지 않는 이메일을 반환하도록 모킹
            String email = "nonexistent@darye.dev";
            when(memberMapper.selectByEmail(email)).thenReturn(null); // 이메일이 존재하지 않음

            // When & Then
            assertFalse(memberService.isEmailTaken(email)); // 이메일이 존재하지 않으면 false 반환
        }

        @Test
        @DisplayName("연락처가 이미 존재하는지 확인 - 연락처 존재")
        void isContactTakenTest_ExistingContact() {
            // Given: 이미 존재하는 연락처를 반환하도록 모킹
            String contact = "010-1234-5678";
            when(memberMapper.findEmailByContact(contact)).thenReturn("test@darye.dev"); // 연락처가 이미 존재

            // When & Then
            assertTrue(memberService.isContactTaken(contact)); // 연락처가 존재하면 true 반환
        }

        @Test
        @DisplayName("연락처가 이미 존재하는지 확인 - 연락처 미존재")
        void isContactTakenTest_NotExistingContact() {
            // Given: 존재하지 않는 연락처를 반환하도록 모킹
            String contact = "010-0000-0000";
            when(memberMapper.findEmailByContact(contact)).thenReturn(null); // 연락처가 존재하지 않음

            // When & Then
            assertFalse(memberService.isContactTaken(contact)); // 연락처가 존재하지 않으면 false 반환
        }
    }
}