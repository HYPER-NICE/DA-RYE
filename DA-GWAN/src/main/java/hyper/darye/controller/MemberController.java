package hyper.darye.controller;

import hyper.darye.dto.Member;
import hyper.darye.dto.controller.request.CreateMemberRequest;
import hyper.darye.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class MemberController {

    @Autowired
    private MemberService memberService;

    @PostMapping("/members")
    @ResponseStatus(HttpStatus.CREATED)
    public String insertMember(@RequestBody CreateMemberRequest createMemberRequest) {
        if (!createMemberRequest.getPassword().equals(createMemberRequest.getRePassword())) {
            throw new IllegalArgumentException("비밀번호를 확인해주세요.");
        }

        memberService.insertMember(
                createMemberRequest.getEmail(),
                createMemberRequest.getPassword(),
                createMemberRequest.getRePassword(),
                createMemberRequest.getName(),
                createMemberRequest.getSex(),
                createMemberRequest.getBirthdate(),
                createMemberRequest.getMobile());

        return "회원 가입 성공";
    }

    @GetMapping("/members")
    public Member selectMemberByEmail(@RequestParam String email) {
        return memberService.selectMemberByEmail(email);
    }

    @GetMapping("/members/{id}")
    public Member selectMemberById(@PathVariable Long id) {
        return memberService.selectMemberById(id);
    }

    @DeleteMapping("/members/{id}")
    public int softDeleteMemberById(@PathVariable Long id) {
        return memberService.softDeleteMemberById(id);
    }

    @PutMapping("/members/{id}")
    public Member updateMemberByIdSelective(@RequestBody Member member) {
        memberService.updateMemberByIdSelective(member);
        return member;
    }
}
