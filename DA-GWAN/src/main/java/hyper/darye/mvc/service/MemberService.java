package hyper.darye.mvc.service;

import hyper.darye.system.constant.Role;
import hyper.darye.mvc.model.entity.Member;
import hyper.darye.mvc.model.entity.SignUp;
import hyper.darye.mvc.mapper.MemberMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

@Service
public class MemberService {

    private final MemberMapper memberMapper;
    private final PasswordEncoder passwordEncoder;

    public MemberService(MemberMapper memberMapper, PasswordEncoder passwordEncoder) {
        this.memberMapper = memberMapper;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * 회원 데이터 생성
     * @param signUp
     * @return 생성된 데이터의 수, 정상 생성 1, 실패 예외 발생
     */
    public int insertSelective(SignUp signUp) {
        Member member = new Member();

        // 유니크 키
        member.setEmail(signUp.getEmail());

        // 데이터
        // 보안 데이터, 비밀번호 암호화 처리
        member.setPassword(passwordEncoder.encode(signUp.getPassword()));
        // 일반 데이터
        member.setName(signUp.getName());
        member.setContact(signUp.getContact());
        member.setRole(Role.USER.name());

        return memberMapper.insertSelective(member);
    }

    public boolean isEmailTaken(String email) {
        return memberMapper.selectByEmail(email) != null;
    }

    public boolean isContactTaken(String contact) {
        return memberMapper.findEmailByContact(contact) != null;
    }

    /**
     * 회원 데이터 조회
     * @param id
     * @return 조회된 회원 데이터
     * @throws NoSuchElementException
     */
    public Member selectByPrimaryKey(Long id) throws NoSuchElementException {
        Member result = memberMapper.selectByPrimaryKey(id);

        if (result == null)
            throw new NoSuchElementException("존재하지 않는 키입니다.");

        return result;
    }

    /**
     * 이메일로 회원 데이터 조회
     * @param email
     * @return 조회된 회원 데이터
     * @throws NoSuchElementException
     */
    public Member selectByEmail(String email) throws NoSuchElementException {
        Member result = memberMapper.selectByEmail(email);

        if (result == null)
            throw new NoSuchElementException("등록되지 않은 이메일입니다.");

        return result;
    }

    /**
     * 회원 데이터 삭제
     * 소프트 삭제를 하기 때문에 로우는 남아있습니다.
     * @param id
     * @return 삭제된 데이터의 수, 정상 삭제 1, 실패 예외 발생
     * @throws NoSuchElementException
     */
    public void softDeleteByPrimaryKey(Long id) throws NoSuchElementException {
        int result = memberMapper.softDeleteByPrimaryKey(id);

        if (result == 0)
            throw new NoSuchElementException("존재하지 않는 키입니다.");
    }

    /**
     * 회원 데이터 수정
     * @param member
     */
    public void updateByPrimaryKeySelective(Member member) {
        int result = memberMapper.updateByPrimaryKeySelective(member);

        if (result == 0)
            throw new NoSuchElementException("존재하지 않는 키입니다.");
    }

    /**
     * 마지막 사인인 날짜 업데이트
     */
    @Transactional
    public void updateLatestSignInDate(Long id) {
        int result = memberMapper.updateLatestSignInDate(id);
        if (result == 0) {
            throw new NoSuchElementException("사용자를 찾을 수 없습니다. ID: " + id);
        }
    }

    public void updatePassword(Long id, String oldPassword, String newPassword, String confirmPassword) {
        Member member = memberMapper.selectByPrimaryKey(id);
        String encodedOldPassword = member.getPassword();

        if(!passwordEncoder.matches(oldPassword, encodedOldPassword))
            throw new IllegalArgumentException("기존 비밀번호를 확인해주세요.");
        if(!newPassword.equals(confirmPassword))
            throw new IllegalArgumentException("비밀번호를 다시 확인해주세요.");

        String encodedNewPassword = passwordEncoder.encode(newPassword);

        memberMapper.updatePassword(id, encodedNewPassword);
    }

    public String findEmailByContact(String contact) {
        String result = memberMapper.findEmailByContact(contact);

        if (result == null)
            throw new NoSuchElementException("등록되지 않은 휴대폰 번호입니다.");

        return result;
    }
}
