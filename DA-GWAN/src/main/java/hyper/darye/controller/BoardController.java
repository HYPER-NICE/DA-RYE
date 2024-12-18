package hyper.darye.controller;

import hyper.darye.dto.controller.request.CreateBoardRequestDTO;
import hyper.darye.service.BoardService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/notification-board")
public class BoardController {

    private final BoardService boardService;

    public BoardController(BoardService boardService) {
        this.boardService = boardService;
    }

    //공지사항 등록
    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public String registerBoard(@RequestBody CreateBoardRequestDTO boardRequestDTO) {
        int result = boardService.insertBoard(boardRequestDTO);
        if (result == 1) {
            return "공지사항 등록 성공";
        }
        return "공지사항 등록 실패";
    }
}
