package hyper.darye.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import hyper.darye.constant.RootCategory;
import hyper.darye.dto.Board;
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
public class BoardController {

    private final BoardService boardService;
    private final ObjectMapper objectMapper;

    public BoardController(BoardService boardService, ObjectMapper objectMapper) {
        this.boardService = boardService;
        this.objectMapper = objectMapper;
    }


    //공지사항 등록
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/notification-board")
    @ResponseStatus(HttpStatus.CREATED)
    public void registerBoard(@Valid @RequestBody PostBoardDTO postBoardDTO,
                              @AuthenticationPrincipal CustomUserDetails principal) {

        postBoardDTO.setRootCategory(RootCategory.NOTICE.getValue());
        postBoardDTO.setWriterId(principal.getId());
        boardService.insertBoard(postBoardDTO);
    }
}
