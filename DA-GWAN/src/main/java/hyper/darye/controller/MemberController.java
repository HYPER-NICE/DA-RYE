package hyper.darye.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import hyper.darye.dto.Member;
import hyper.darye.dto.MemberUpdateRequest;
import hyper.darye.dto.controller.request.UpdatePasswordRequest;
import hyper.darye.security.CustomUserDetails;
import hyper.darye.service.MemberService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

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
    public ResponseEntity<Member> selectMemberByPrimaryKey(@PathVariable Long id, @AuthenticationPrincipal CustomUserDetails principal) {
        try {
            Member memeber = memberService.selectByPrimaryKey(id);
            return ResponseEntity.ok(memeber);
        } catch (NoSuchElementException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @PreAuthorize("#email == principal.username or hasRole('ADMIN')")
    @GetMapping("/members")
    public ResponseEntity<Member> selectMemberByEmail(@RequestParam String email) {
        try {
            Member member = memberService.selectByEmail(email);
            return ResponseEntity.ok(member);
        } catch (NoSuchElementException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/members/me")
    public Member selectMemberByPrincipal(@AuthenticationPrincipal CustomUserDetails principal) {
        return memberService.selectByPrimaryKey(principal.getId());
    }

    @PreAuthorize("#id == principal.id or hasRole('ADMIN')")
    @PutMapping("/members/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<?> updateMemberByPrimaryKeySelective(@PathVariable Long id, @RequestBody MemberUpdateRequest updateRequest) {
        // ObjectMapper를 사용하여 DTO를 엔티티로 변환
        Member member = objectMapper.convertValue(updateRequest, Member.class);

        try {
            // 필요한 서비스 로직 수행
            memberService.updateByPrimaryKeySelective(member);
            return ResponseEntity.noContent().build();
        } catch (NoSuchElementException ex) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("message", ex.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        }
    }

    @PreAuthorize("#id == principal.id or hasRole('ADMIN')")
    @DeleteMapping("/members/{id}")
    public ResponseEntity<?> softDeleteMemberById(@PathVariable Long id) {
        try {
            memberService.softDeleteByPrimaryKey(id);
            return ResponseEntity.noContent().build();
        } catch (NoSuchElementException ex) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("message", ex.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        }
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