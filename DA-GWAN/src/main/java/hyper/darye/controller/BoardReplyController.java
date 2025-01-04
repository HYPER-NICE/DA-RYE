package hyper.darye.controller;

import hyper.darye.model.dto.controller.request.PostReplyDTO;
import hyper.darye.model.dto.controller.request.UpdateReplyDTO;
import hyper.darye.model.dto.controller.response.SearchReplyDTO;
import hyper.darye.config.security.CustomUserDetails;
import hyper.darye.service.BoardService;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class BoardReplyController {

    private final BoardService boardService;

    public BoardReplyController(BoardService boardService) {
        this.boardService = boardService;
    }

    //댓글 등록
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/customer-board/{id}/reply")
    @ResponseStatus(HttpStatus.CREATED)
    public void registerReply(@PathVariable Long id, @RequestBody PostReplyDTO postReplyDTO,
                              @AuthenticationPrincipal CustomUserDetails principal) {

        boardService.insertReply(id, postReplyDTO, principal.getId());
    }

    //댓글 수정
    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping("/customer-board/{id}/reply/{replyId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateReply(@PathVariable Long replyId, @RequestBody UpdateReplyDTO updateReplyDTO,
                            @AuthenticationPrincipal CustomUserDetails principal) {

        boardService.updateReply(replyId, updateReplyDTO, principal.getId());
    }

    //댓글 삭제
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/customer-board/{id}/reply/{replyId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteReply(@PathVariable Long replyId,
                            @AuthenticationPrincipal CustomUserDetails principal) {

        boardService.softDeleteBoard(replyId, principal.getId());
    }

    //댓글 조회
    @PreAuthorize("@boardService.isWriter(#id, principal.getId()) or hasRole('ADMIN')")
    @GetMapping("/customer-board/{id}/reply")
    public List<SearchReplyDTO> selectReply(@PathVariable Long id,
                                            @AuthenticationPrincipal CustomUserDetails principal) {
        return boardService.selectAllReply(id);
    }


}
