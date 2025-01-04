package hyper.darye.controller;

import hyper.darye.constant.RootCategory;
import hyper.darye.model.dto.controller.request.UpdateBoardDTO;
import hyper.darye.model.dto.controller.request.PostBoardDTO;
import hyper.darye.model.dto.controller.response.SearchBoardDTO;
import hyper.darye.model.dto.controller.response.SearchBoardDetailDTO;
import hyper.darye.config.security.CustomUserDetails;
import hyper.darye.service.BoardService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    //공지사항 조회
    @PreAuthorize("permitAll()")
    @GetMapping("/notification-board")
    public List<SearchBoardDTO> selectAllBoard(@RequestParam(required = false) Long subCategoryId) {
        return boardService.selectAllBoard(RootCategory.NOTICE.getValue(), subCategoryId);
    }

    //공지사항 상세 조회
    @PreAuthorize("permitAll()")
    @GetMapping("/notification-board/{id}")
    public SearchBoardDetailDTO selectBoard(@PathVariable Long id) {
        return boardService.selectBoardDetail(id);
    }
}
