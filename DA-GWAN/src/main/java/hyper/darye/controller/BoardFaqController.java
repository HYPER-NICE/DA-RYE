package hyper.darye.controller;

import hyper.darye.constant.RootCategory;
import hyper.darye.model.dto.controller.request.PostBoardDTO;
import hyper.darye.model.dto.controller.request.UpdateBoardDTO;
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
public class BoardFaqController {

    private final BoardService boardService;

    public BoardFaqController(BoardService boardService) {
        this.boardService = boardService;
    }


    //FAQ 등록
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/faq-board")
    @ResponseStatus(HttpStatus.CREATED)
    public void registerBoard(@Valid @RequestBody PostBoardDTO postBoardDTO,
                              @AuthenticationPrincipal CustomUserDetails principal) {

        postBoardDTO.setRootCategory(RootCategory.FAQ.getValue());
        boardService.insertBoard(postBoardDTO, principal.getId());
    }

    //FAQ 수정
    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping("/faq-board/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateBoard(@PathVariable Long id, @RequestBody UpdateBoardDTO updateBoardDTO,
                            @AuthenticationPrincipal CustomUserDetails principal) {

        boardService.updateBoard(id, updateBoardDTO, principal.getId());
    }

    //FAQ 삭제
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/faq-board/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void softDeleteBoard(@PathVariable Long id,
                                @AuthenticationPrincipal CustomUserDetails principal) {

        boardService.softDeleteBoard(id, principal.getId());
    }

    //FAQ 조회
    @PreAuthorize("permitAll()")
    @GetMapping("/faq-board")
    public List<SearchBoardDTO> selectAllBoard(@RequestParam(required = false) Long subCategoryId) {
        return boardService.selectAllBoard(RootCategory.FAQ.getValue(), subCategoryId);
    }

    //FAQ 상세 조회
    @PreAuthorize("permitAll()")
    @GetMapping("/faq-board/{id}")
    public SearchBoardDetailDTO selectBoard(@PathVariable Long id) {
        return boardService.selectBoardDetail(id);
    }
}
