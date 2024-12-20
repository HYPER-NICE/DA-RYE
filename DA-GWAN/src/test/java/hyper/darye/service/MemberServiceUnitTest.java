package hyper.darye.service;

import hyper.darye.dto.Member;
import hyper.darye.dto.SignUp;
import hyper.darye.mapper.MemberMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Date;
import java.util.NoSuchElementException;

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
    private Member mockMember;

    @BeforeEach
    void setUp() {
        signUp = new SignUp();
        signUp.setName("John Doe");
        signUp.setEmail("john.doe@example.com");
        signUp.setPassword("P@ssword123");
        signUp.setConfirmPassword("P@ssword123");
        signUp.setContact("010-1234-5678");
        signUp.setRole("USER");

        mockMember = new Member();
        mockMember.setId(1L);
        mockMember.setName(signUp.getName());
        mockMember.setEmail(signUp.getEmail());
        mockMember.setPassword("EncodedP@ssword123");
        mockMember.setMobile(signUp.getContact());
        mockMember.setRole(signUp.getRole());
        mockMember.setCreatedDate(new Date());
    }

    @Test
    void insertMemberTest() {
        // 비밀번호 암호화 시뮬레이션
        when(passwordEncoder.encode(signUp.getPassword())).thenReturn("EncodedP@ssword123");

        // 매퍼의 insertSelective 메서드가 호출되었을 때 1을 반환하도록 설정
        when(memberMapper.insertSelective(any(Member.class))).thenReturn(1);

        // 서비스 메서드 호출
        int result = memberService.insert(signUp);

        // 반환값 검증
        assertEquals(1, result);

        // ArgumentCaptor를 사용하여 매퍼에 전달된 Member 객체 캡처
        ArgumentCaptor<Member> memberCaptor = ArgumentCaptor.forClass(Member.class);
        verify(memberMapper, times(1)).insertSelective(memberCaptor.capture());
        Member capturedMember = memberCaptor.getValue();

        // 캡처된 Member 객체의 필드 검증
        assertEquals("John Doe", capturedMember.getName());
        assertEquals("john.doe@example.com", capturedMember.getEmail());
        assertEquals("EncodedP@ssword123", capturedMember.getPassword());
        assertEquals("010-1234-5678", capturedMember.getMobile());
        assertEquals("USER", capturedMember.getRole());
    }

    @Test
    void selectMemberByValidEmailTest() {
        // 매퍼의 selectByEmail 메서드가 호출되었을 때 mockMember를 반환하도록 설정
        when(memberMapper.selectByEmail("john.doe@example.com")).thenReturn(mockMember);

        // 서비스 메서드 호출
        Member foundMember = memberService.selectMemberByEmail("john.doe@example.com");

        // 반환된 Member 객체 검증
        assertNotNull(foundMember);
        assertEquals("john.doe@example.com", foundMember.getEmail());
        assertEquals("John Doe", foundMember.getName());
        assertEquals("USER", foundMember.getRole());

        // 매퍼의 selectByEmail 메서드가 정확히 한 번 호출되었는지 검증
        verify(memberMapper, times(1)).selectByEmail("john.doe@example.com");
    }

    @Test
    void selectMemberByInvalidEmailTest() {
        // 매퍼의 selectByEmail 메서드가 호출되었을 때 null을 반환하도록 설정
        when(memberMapper.selectByEmail("null@example.com")).thenReturn(null);

        // 서비스 메서드 호출 시 예외 발생 여부 검증
        NoSuchElementException exception = assertThrows(NoSuchElementException.class, () -> {
            memberService.selectMemberByEmail("null@example.com");
        });

        // 예외 메시지 검증
        assertEquals("존재하지 않는 이메일입니다.", exception.getMessage());

        // 매퍼의 selectByEmail 메서드가 정확히 한 번 호출되었는지 검증
        verify(memberMapper, times(1)).selectByEmail("null@example.com");
    }

    @Test
    void selectMemberByValidIdTest() {
        // 매퍼의 selectMemberById 메서드가 호출되었을 때 mockMember를 반환하도록 설정
        when(memberMapper.selectByPrimaryKey(1L)).thenReturn(mockMember);

        // 서비스 메서드 호출
        Member foundMember = memberService.selectMemberById(1L);

        // 반환된 Member 객체 검증
        assertNotNull(foundMember);
        assertEquals(1L, foundMember.getId());
        assertEquals("john.doe@example.com", foundMember.getEmail());

        // 매퍼의 selectMemberById 메서드가 정확히 한 번 호출되었는지 검증
        verify(memberMapper, times(1)).selectByPrimaryKey(1L);
    }

    @Test
    void selectMemberByInvalidIdTest() {
        // 매퍼의 selectMemberById 메서드가 호출되었을 때 null을 반환하도록 설정
        when(memberMapper.selectByPrimaryKey(0L)).thenReturn(null);

        // 서비스 메서드 호출 시 예외 발생 여부 검증
        NoSuchElementException exception = assertThrows(NoSuchElementException.class, () -> {
            memberService.selectMemberById(0L);
        });

        // 예외 메시지 검증
        assertEquals("존재하지 않는 키입니다.", exception.getMessage());

        // 매퍼의 selectMemberById 메서드가 정확히 한 번 호출되었는지 검증
        verify(memberMapper, times(1)).selectByPrimaryKey(0L);
    }

    @Test
    void softDeleteMemberByIdTest() {
        // 매퍼의 softDeleteMemberById 메서드가 호출되었을 때 1을 반환하도록 설정
        when(memberMapper.softDeleteMemberById(1L)).thenReturn(1);

        // 서비스 메서드 호출
        int result = memberService.softDeleteMemberById(1L);

        // 반환값 검증
        assertEquals(1, result);

        // 매퍼의 softDeleteMemberById 메서드가 정확히 한 번 호출되었는지 검증
        verify(memberMapper, times(1)).softDeleteMemberById(1L);
    }

    @Test
    void softDeleteMemberByInvalidIdTest() {
        // 매퍼의 softDeleteMemberById 메서드가 호출되었을 때 0을 반환하도록 설정
        when(memberMapper.softDeleteMemberById(0L)).thenReturn(0);

        // 서비스 메서드 호출 시 예외 발생 여부 검증
        NoSuchElementException exception = assertThrows(NoSuchElementException.class, () -> {
            memberService.softDeleteMemberById(0L);
        });

        // 예외 메시지 검증
        assertEquals("존재하지 않는 키입니다.", exception.getMessage());

        // 매퍼의 softDeleteMemberById 메서드가 정확히 한 번 호출되었는지 검증
        verify(memberMapper, times(1)).softDeleteMemberById(0L);
    }

    @Test
    void updateMemberByIdSelectiveTest() {
        // 기존 회원 정보
        Member existingMember = new Member();
        existingMember.setId(1L);
        existingMember.setName("John Doe");
        existingMember.setEmail("john.doe@example.com");
        existingMember.setSex('M');
        existingMember.setBirthdate(new Date());
        existingMember.setMobile("010-1234-5678");

        // 업데이트할 회원 정보
        Member updatedMember = new Member();
        updatedMember.setId(1L);
        updatedMember.setName("Jane Doe");
        updatedMember.setEmail("jane.doe@example.com");
        updatedMember.setSex('F');
        updatedMember.setBirthdate(new Date());
        updatedMember.setMobile("010-8765-4321");

        // 매퍼의 selectMemberById 메서드가 호출되었을 때 existingMember를 반환하도록 설정
        when(memberMapper.selectByPrimaryKey(1L)).thenReturn(existingMember);

        // 서비스 메서드 호출
        memberService.updateMemberByIdSelective(updatedMember);

        // 기존 회원 정보가 업데이트되었는지 검증
        assertEquals("Jane Doe", existingMember.getName());
        assertEquals("jane.doe@example.com", existingMember.getEmail());
        assertEquals('F', existingMember.getSex());
        assertEquals("010-8765-4321", existingMember.getMobile());

        // 매퍼의 selectMemberById 메서드가 정확히 한 번 호출되었는지 검증
        verify(memberMapper, times(1)).selectByPrimaryKey(1L);
    }

    @Test
    void insertMemberTest_Success() {
        // 비밀번호 암호화 시뮬레이션
        when(passwordEncoder.encode(signUp.getPassword())).thenReturn("EncodedP@ssword123");

        // 매퍼의 insertSelective 메서드가 호출되었을 때 1을 반환하도록 설정
        when(memberMapper.insertSelective(any(Member.class))).thenReturn(1);

        // 서비스 메서드 호출
        int result = memberService.insert(signUp);

        // 반환값 검증
        assertEquals(1, result);

        // ArgumentCaptor를 사용하여 매퍼에 전달된 Member 객체 캡처
        ArgumentCaptor<Member> memberCaptor = ArgumentCaptor.forClass(Member.class);
        verify(memberMapper, times(1)).insertSelective(memberCaptor.capture());
        Member capturedMember = memberCaptor.getValue();

        // 캡처된 Member 객체의 필드 검증
        assertEquals("John Doe", capturedMember.getName());
        assertEquals("john.doe@example.com", capturedMember.getEmail());
        assertEquals("EncodedP@ssword123", capturedMember.getPassword());
        assertEquals("010-1234-5678", capturedMember.getMobile());
        assertEquals("USER", capturedMember.getRole());
    }

    @Test
    void latestLoginDateTest() {
        // 서비스 메서드 호출
        memberService.latestLoginDate("john.doe@example.com");

        // 매퍼의 updateLatestLoginDate 메서드가 정확히 한 번 호출되었는지 검증
        verify(memberMapper, times(1)).updateLatestLoginDate("john.doe@example.com");
    }
}
