package hyper.darye.controller;

import hyper.darye.constant.RootCategory;
import hyper.darye.dto.controller.request.UpdateBoardDTO;
import hyper.darye.dto.controller.request.PostBoardDTO;
import hyper.darye.security.CustomUserDetails;
import hyper.darye.service.BoardService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class BoardNotificationController {

    private final BoardService boardService;

    public BoardNotificationController(BoardService boardService) {
        this.boardService = boardService;
    }


    //공지사항 등록
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/notification-board")
    @ResponseStatus(HttpStatus.CREATED)
    public void registerBoard(@Valid @RequestBody PostBoardDTO postBoardDTO,
                              @AuthenticationPrincipal CustomUserDetails principal) {

        postBoardDTO.setRootCategory(RootCategory.NOTICE.getValue());
        boardService.insertBoard(postBoardDTO, principal.getId());
    }


    //공지사항 수정
    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping("/notification-board/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateBoard(@PathVariable Long id, @RequestBody UpdateBoardDTO updateBoardDTO,
                            @AuthenticationPrincipal CustomUserDetails principal) {

        boardService.updateBoard(id, updateBoardDTO, principal.getId());
    }

    //공지사항 삭제
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/notification-board/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void softDeleteBoard(@PathVariable Long id,
                            @AuthenticationPrincipal CustomUserDetails principal) {

        boardService.softDeleteBoard(id, principal.getId());
    }
}
