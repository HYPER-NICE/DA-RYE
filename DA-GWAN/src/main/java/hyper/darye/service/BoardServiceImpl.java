package hyper.darye.service;

import hyper.darye.dto.Board;
import hyper.darye.dto.controller.request.CreateBoardRequestDTO;
import hyper.darye.mapper.BoardCategoryCodeMapper;
import hyper.darye.mapper.BoardMapper;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Map;

@Service
public class BoardServiceImpl implements BoardService{

    private final BoardCategoryCodeMapper boardCategoryCodeMapper;
    private final BoardMapper boardMapper;

    public BoardServiceImpl(BoardCategoryCodeMapper boardCategoryCodeMapper, BoardMapper boardMapper) {
        this.boardCategoryCodeMapper = boardCategoryCodeMapper;
        this.boardMapper = boardMapper;
    }


    @Override
    public int insertBoard(CreateBoardRequestDTO requestBoard) {

        //공지사항에서 서브 카테고리가 어디인지 확인
        String rootName = "공지사항";
        String subName = requestBoard.getSubName();

        Map<String, Object> param = Map.of("rootName", rootName, "subName", subName);

        //카테고리 코드 조회
        Long categoryId = boardCategoryCodeMapper.selectCategoryCodeId(param);

        //카테고리 코드가 없을시
        if (categoryId == null) {
            throw new IllegalArgumentException("존재하지 않는 카테고리입니다.");
        } //예외처리 이게 맞나요....? 모르겠슴니다....ㅠㅠ

        Board board = new Board();
        board.setCategoryId(categoryId);
        board.setWriterId(requestBoard.getWriterId());
        board.setTitle(requestBoard.getTitle());
        board.setContent(requestBoard.getContent());
        board.setFixed(requestBoard.getFixed());
        board.setRegDate(new Date());

        return boardMapper.insert(board);
    }
}
