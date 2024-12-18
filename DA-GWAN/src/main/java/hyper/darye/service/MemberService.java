package hyper.darye.service;

import hyper.darye.dto.Member;
import hyper.darye.dto.SignUp;
import hyper.darye.dto.controller.request.CreateMemberRequest;
import hyper.darye.mapper.MemberMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class MemberService {

    @Autowired
    MemberMapper memberMapper;

    public int insertMember(String email, String password, String rePassword, String name, Character sex, Date birthdate, String mobile) {
        CreateMemberRequest member = new CreateMemberRequest();
        member.setEmail(email);
        member.setPassword(password);
        member.setName(name);
        member.setSex(sex);
        member.setBirthdate(birthdate);
        member.setMobile(mobile);
        return memberMapper.insertMember(member);
    }

    public int insert(SignUp signUp) {
        Member member = new Member();
        member.setEmail(signUp.getEmail());
        member.setPassword(signUp.getPassword());
        member.setName(signUp.getName());
        member.setMobile(signUp.getContact());

        return memberMapper.insertSelective(member);
    }
}
