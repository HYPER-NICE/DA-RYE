package hyper.darye.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import hyper.darye.dto.Member;
import hyper.darye.dto.MemberUpdateRequest;
import hyper.darye.dto.controller.request.UpdatePasswordRequest;
import hyper.darye.security.CustomUserDetails;
import hyper.darye.service.MemberService;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class MemberController {

    private final MemberService memberService;
    private final ObjectMapper objectMapper;

    public MemberController(MemberService memberService, ObjectMapper objectMapper) {
        this.memberService = memberService;
        this.objectMapper = objectMapper;
    }

    @PreAuthorize("#id == principal.id or hasRole('ADMIN')")
    @GetMapping("/members/{id}")
    public Member selectMemberByPrimaryKey(@PathVariable Long id, @AuthenticationPrincipal CustomUserDetails principal) {
        return memberService.selectByPrimaryKey(id);
    }

    @PreAuthorize("#email == principal.username or hasRole('ADMIN')")
    @GetMapping("/members")
    public Member selectMemberByEmail(@RequestParam String email) {
        return memberService.selectByEmail(email);
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/members/me")
    public Member selectMemberByPrincipal(@AuthenticationPrincipal CustomUserDetails principal) {
        return memberService.selectByPrimaryKey(principal.getId());
    }

    @PreAuthorize("#id == principal.id or hasRole('ADMIN')")
    @PutMapping("/members/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateMemberByPrimaryKeySelective(@PathVariable Long id, @RequestBody MemberUpdateRequest updateRequest) {
        // ObjectMapper를 사용하여 DTO를 엔티티로 변환
        Member member = objectMapper.convertValue(updateRequest, Member.class);

        // 필요한 서비스 로직 수행
        memberService.updateByPrimaryKeySelective(member);
    }

    @PreAuthorize("#id == principal.id or hasRole('ADMIN')")
    @DeleteMapping("/members/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void softDeleteMemberById(@PathVariable Long id) {
        memberService.softDeleteByPrimaryKey(id);
    }

    @PreAuthorize("hasRole('USER') and (#id == authentication.principal.id or hasRole('ADMIN'))")
    @PatchMapping("/members/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updatePassword(@PathVariable Long id, @RequestBody UpdatePasswordRequest updatePasswordRequest) {
        memberService.updatePassword(id, updatePasswordRequest.getOldPassword(),
                                         updatePasswordRequest.getNewPassword(),
                                         updatePasswordRequest.getConfirmPassword());
    }
}
