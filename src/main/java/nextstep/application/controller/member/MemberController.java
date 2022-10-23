package nextstep.application.controller.member;

import java.net.URI;
import nextstep.application.controller.auth.Auth;
import nextstep.application.controller.auth.LoginMember;
import nextstep.application.dto.member.MemberRequest;
import nextstep.application.dto.member.MemberResponse;
import nextstep.application.service.member.MemberCommandService;
import nextstep.application.service.member.MemberQueryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/members")
public class MemberController {

    private final MemberCommandService memberCommandService;
    private final MemberQueryService memberQueryService;

    public MemberController(
        MemberCommandService memberCommandService,
        MemberQueryService memberQueryService
    ) {
        this.memberCommandService = memberCommandService;
        this.memberQueryService = memberQueryService;
    }

    @PostMapping
    public ResponseEntity<Void> create(@RequestBody MemberRequest request) {
        Long memberId = memberCommandService.create(request);
        return ResponseEntity.created(URI.create("/members/" + memberId)).build();
    }

    @GetMapping("/me")
    public ResponseEntity<MemberResponse> me(@Auth LoginMember loginMember) {
        MemberResponse response = memberQueryService.findBy(memberId(loginMember));
        return ResponseEntity.ok(response);
    }

    private Long memberId(LoginMember loginMember) {
        return Long.valueOf(loginMember.getPrincipal());
    }
}
