package hyper.darye.mvc.controller;

import hyper.darye.system.constant.RootCategory;
import hyper.darye.mvc.model.dto.controller.request.PostBoardDTO;
import hyper.darye.mvc.model.dto.controller.request.UpdateBoardDTO;
import hyper.darye.mvc.model.dto.controller.response.SearchBoardDTO;
import hyper.darye.mvc.model.dto.controller.response.SearchBoardDetailDTO;
import hyper.darye.config.security.CustomUserDetails;
import hyper.darye.mvc.service.BoardService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class BoardOneOnOneController {

    private final BoardService boardService;

    public BoardOneOnOneController(BoardService boardService) {
        this.boardService = boardService;
    }


    //1대1 문의 등록
    @PreAuthorize("isAuthenticated()")
    @PostMapping("/customer-board")
    @ResponseStatus(HttpStatus.CREATED)
    public void registerBoard(@Valid @RequestBody PostBoardDTO postBoardDTO,
                              @AuthenticationPrincipal CustomUserDetails principal) {

        postBoardDTO.setRootCategory(RootCategory.FAQ.getValue());
        postBoardDTO.setSubCategory(1L);
        boardService.insertBoard(postBoardDTO, principal.getId());
    }

    //1대1 문의 수정
    @PreAuthorize("@boardService.isWriter(#id, principal.getId())")
    @PatchMapping("/customer-board/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateBoard(@PathVariable Long id, @RequestBody UpdateBoardDTO updateBoardDTO,
                            @AuthenticationPrincipal CustomUserDetails principal) {

//        //작성자가 본인인지 확인
//        if (!boardService.isWriter(id, principal.getId())) {
//            throw new IllegalArgumentException("작성자만 수정할 수 있습니다.");
//        }
        //답변 여부 확인
        if (boardService.isUpdatable(id)) {
            throw new IllegalArgumentException("답변된 글은 수정할 수 없습니다.");
        }

        boardService.updateBoard(id, updateBoardDTO, principal.getId());
    }

    //1대1 문의 삭제
    @PreAuthorize("@boardService.isWriter(#id, principal.getId())")
    @DeleteMapping("/customer-board/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void softDeleteBoard(@PathVariable Long id,
                                @AuthenticationPrincipal CustomUserDetails principal) {

//        //작성자가 본인인지 확인
//        if (!boardService.isWriter(id, principal.getId())) {
//            throw new IllegalArgumentException("작성자만 삭제할 수 있습니다.");
//        }

        boardService.softDeleteBoard(id, principal.getId());
    }

    //1대1 문의 전체 조회
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/customer-board")
    public List<SearchBoardDTO> selectOneBoard(@RequestParam(required = false) Long subCategoryId,
                                               @AuthenticationPrincipal CustomUserDetails principal) {
        return boardService.selectOneBoard(RootCategory.ONE_ON_ONE.getValue(), subCategoryId, principal.getId());
    }

    //1대1 문의 상세 조회
    @PreAuthorize("@boardService.isWriter(#id, principal.getId())")
    @GetMapping("/customer-board/{id}")
    public SearchBoardDetailDTO selectBoard(@PathVariable Long id,
                                            @AuthenticationPrincipal CustomUserDetails principal) {
        return boardService.selectBoardDetail(id);
    }

}
