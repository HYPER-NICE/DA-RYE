package hyper.darye.service;

import hyper.darye.dto.Board;
import hyper.darye.dto.controller.request.PostBoardDTO;
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


    //보드 게시판 글 작성
    @Override
    public int insertBoard(PostBoardDTO postBoardDTO) {

        Board board = new Board();

        //카테고리 코드 조희
        Map<String, Object> param = Map.of("rootCategory", postBoardDTO.getRootCategory(), "subCategory", postBoardDTO.getSubCategory());
        Long categoryId = boardCategoryCodeMapper.selectCategoryCodeId(param);

        //카테고리 코드가 없을시
        if (categoryId == null) {
            throw new IllegalArgumentException("존재하지 않는 카테고리입니다.");
        } //예외처리 이게 맞나요....? 모르겠슴니다....ㅠㅠ

        board.setCategoryId(categoryId);
        board.setTitle(postBoardDTO.getTitle());
        board.setContent(postBoardDTO.getContent());
        board.setFixed(postBoardDTO.getFixed());
        board.setWriterId(postBoardDTO.getWriterId());

        return boardMapper.insertSelective(board);
    }
}
