package hyper.darye.controller;

import hyper.darye.dto.Member;
import hyper.darye.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/members")
public class PointController {
    @Autowired
    MemberService memberService;

    @GetMapping("/{id}/point")
    public Member getPoint(@PathVariable Long id) {
        return memberService.selectByPrimaryKey(id);
    }

}
