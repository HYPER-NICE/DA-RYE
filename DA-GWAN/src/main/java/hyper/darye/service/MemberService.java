package hyper.darye.service;

import hyper.darye.dto.Member;
import hyper.darye.dto.controller.request.CreateMemberRequest;
import hyper.darye.mapper.MemberMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.NoSuchElementException;

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

    public Member selectMemberByEmail(String email) throws NoSuchElementException {
        Member result = memberMapper.selectMemberByEmail(email);

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
}
