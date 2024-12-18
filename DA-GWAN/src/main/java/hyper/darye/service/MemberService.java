package hyper.darye.service;

import hyper.darye.dto.Member;
import hyper.darye.dto.SignUp;
import hyper.darye.dto.controller.request.CreateMemberRequest;
import hyper.darye.mapper.MemberMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;

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

    /**
     * 회원 가입 로직 (SignUp DTO)
     */
    public int insertSelective(SignUp signUp) {
        Member member = new Member();
        member.setEmail(signUp.getEmail());
        member.setName(signUp.getName());
        member.setMobile(signUp.getContact());
        member.setRole(signUp.getRole());

        // 비밀번호 암호화 처리
        member.setPassword(passwordEncoder.encode(signUp.getPassword()));

        return memberMapper.insertSelective(member);
    }
}
