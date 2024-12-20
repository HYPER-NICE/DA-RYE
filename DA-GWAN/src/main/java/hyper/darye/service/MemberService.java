package hyper.darye.service;

import hyper.darye.dto.Member;
import hyper.darye.dto.SignUp;
import hyper.darye.mapper.MemberMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
public class MemberService {

    private final MemberMapper memberMapper;

    private final PasswordEncoder passwordEncoder;

    public MemberService(MemberMapper memberMapper, PasswordEncoder passwordEncoder) {
        this.memberMapper = memberMapper;
        this.passwordEncoder = passwordEncoder;
    }

    public Member selectMemberByEmail(String email) throws NoSuchElementException {
        Member result = memberMapper.selectByEmail(email);

        if (result == null)
            throw new NoSuchElementException("존재하지 않는 이메일입니다.");

        return result;
    }

    public Member selectMemberById(Long id) throws NoSuchElementException {
        Member result = memberMapper.selectByPrimaryKey(id);

        if (result == null)
            throw new NoSuchElementException("존재하지 않는 키입니다.");

        return result;
    }

    public int softDeleteMemberById(Long id) throws NoSuchElementException {
        int result = memberMapper.softDeleteMemberById(id);

        if (result == 0)
            throw new NoSuchElementException("존재하지 않는 키입니다.");

        return result;
    }

    public void updateMemberByIdSelective(Member member) {
        Member foundmember = memberMapper.selectByPrimaryKey(member.getId());
        foundmember.setEmail(member.getEmail());
        foundmember.setName(member.getName());
        foundmember.setSex(member.getSex());
        foundmember.setBirthdate(member.getBirthdate());
        foundmember.setMobile(member.getMobile());
        foundmember.setLastModifiedMember(member.getId());
    }

    public int insert(SignUp signUp) {
        Member member = new Member();
        
        // 유니크 키
        member.setEmail(signUp.getEmail());


        // 데이터
        // 보안 데이터, 비밀번호 암호화 처리
        member.setPassword(passwordEncoder.encode(signUp.getPassword()));
        // 일반 데이터
        member.setName(signUp.getName());
        member.setMobile(signUp.getContact());
        member.setRole(signUp.getRole());

        return memberMapper.insertSelective(member);
    }

    /**
     * 마지막 사인인 날짜 업데이트
     */
    public void latestLoginDate(String email) {
        memberMapper.updateLatestLoginDate(email);
    }
}
