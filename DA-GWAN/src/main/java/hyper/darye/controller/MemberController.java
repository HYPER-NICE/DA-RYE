package hyper.darye.controller;

import hyper.darye.dto.Member;
import hyper.darye.dto.MemberUpdateRequest;
import hyper.darye.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class MemberController {

    @Autowired
    private MemberService memberService;

    @PreAuthorize("isAuthenticated()") // 인증된 사용자만 접근 가능
    @GetMapping("/members")
    public Member selectMemberByEmail(@RequestParam String email) {
        return memberService.selectByEmail(email);
    }

    @PreAuthorize("#id == principal.id or hasRole('USER')")
    @GetMapping("/members/{id}")
    public Member selectMemberById(@PathVariable Long id) {
        return memberService.selectMemberById(id);
    }

    @PreAuthorize("#id == principal.id or hasRole('USER')")
    @DeleteMapping("/members/{id}")
    public int softDeleteMemberById(@PathVariable Long id) {
        return memberService.softDeleteByPrimaryKey(id);
    }

    /**
     * 회원 수정: 회원 자신만 접근 가능 또는 ADMIN 권한 보유자
     */
    @PreAuthorize("#id == principal.id or hasRole('USER')")
    @PutMapping("/members/{id}")
    public Member updateMemberByIdSelective(@PathVariable Long id, @RequestBody MemberUpdateRequest updateRequest) {
        // DTO를 사용하여 필요한 필드만을 설정
        Member member = new Member();
        member.setId(id); // 경로 변수 id를 설정
        member.setEmail(updateRequest.getEmail());
        member.setName(updateRequest.getName());
        member.setSex(updateRequest.getSex());
        member.setBirthdate(updateRequest.getBirthdate());
        member.setMobile(updateRequest.getMobile());
        member.setLastModifiedMember(id); // 예시 설정

        memberService.updateMemberByIdSelective(member);
        return memberService.selectMemberById(id);
    }
}
