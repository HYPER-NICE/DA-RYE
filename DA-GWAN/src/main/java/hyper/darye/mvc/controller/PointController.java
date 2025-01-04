package hyper.darye.mvc.controller;

import hyper.darye.mvc.model.entity.Member;
import hyper.darye.mvc.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/members")
public class PointController {

    @Autowired
    MemberService memberService;

    // @PreAuthorize에서 authentication.principal을 사용하도록 수정
    @PreAuthorize("#id == authentication.principal.id or hasRole('ADMIN')")
    @GetMapping("/{id}/point")
    public Member selectPointByMemberId(@PathVariable Long id) {
        return memberService.selectByPrimaryKey(id);
    }
}

