package hyper.darye.service;

import hyper.darye.dto.Member;
import hyper.darye.dto.SignUp;
import hyper.darye.dto.controller.request.CreateMemberRequest;
import hyper.darye.mapper.MemberMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.NoSuchElementException;

@Service
public class MemberService {

    @Autowired
    private MemberMapper memberMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * 회원 가입 로직 (개별 필드)
     */
    public int insertMember(String email, String password, String rePassword, String name, Character sex, Date birthdate, String mobile) {
        CreateMemberRequest member = new CreateMemberRequest();
        member.setEmail(email);

        // 비밀번호 암호화 처리
        member.setPassword(passwordEncoder.encode(password));

        member.setName(name);
        member.setSex(sex);
        member.setBirthdate(birthdate);
        member.setMobile(mobile);
        return memberMapper.insertMember(member);
    }

    public Member selectMemberByEmail(String email) throws NoSuchElementException {
        Member result = memberMapper.selectByEmail(email);

        if (result == null)
            throw new NoSuchElementException("존재하지 않는 이메일입니다.");

        return result;
    }

    public Member selectMemberById(Long id) throws NoSuchElementException {
        Member result = memberMapper.selectMemberById(id);

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
        Member foundmember = memberMapper.selectMemberById(member.getId());
        foundmember.setEmail(member.getEmail());
        foundmember.setName(member.getName());
        foundmember.setSex(member.getSex());
        foundmember.setBirthdate(member.getBirthdate());
        foundmember.setMobile(member.getMobile());
        foundmember.setLastModifiedMember(member.getId());
    }

    public int insert(SignUp signUp) {
        Member member = new Member();
        member.setEmail(signUp.getEmail());

        // 비밀번호 암호화 처리
        member.setPassword(passwordEncoder.encode(signUp.getPassword()));

        member.setName(signUp.getName());
        member.setMobile(signUp.getContact());
        member.setRole(signUp.getRole());

        // 비밀번호 암호화 처리
        member.setPassword(passwordEncoder.encode(signUp.getPassword()));

        return memberMapper.insertSelective(member);
    }

    /**
     * 마지막 사인인 날짜 업데이트
     */
    public void latestLoginDate(String email) {
        memberMapper.updateLatestLoginDate(email);
    }
}
