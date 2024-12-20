package hyper.darye.controller;

import hyper.darye.dto.Member;
import hyper.darye.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class MemberController {

    @Autowired
    private MemberService memberService;

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
