package hyper.darye.service;

import hyper.darye.dto.Board;
import hyper.darye.dto.BoardImage;
import hyper.darye.dto.controller.request.UpdateBoardDTO;
import hyper.darye.dto.controller.request.PostBoardDTO;
import hyper.darye.mapper.BoardCategoryCodeMapper;
import hyper.darye.mapper.BoardImageMapper;
import hyper.darye.mapper.BoardMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

@Service("boardService")
public class BoardServiceImpl implements BoardService{

    private final BoardCategoryCodeMapper boardCategoryCodeMapper;
    private final BoardMapper boardMapper;
    private final BoardImageMapper boardImageMapper;

    @Autowired
    public BoardServiceImpl(BoardCategoryCodeMapper boardCategoryCodeMapper, BoardMapper boardMapper, BoardImageMapper boardImageMapper) {
        this.boardCategoryCodeMapper = boardCategoryCodeMapper;
        this.boardMapper = boardMapper;
        this.boardImageMapper = boardImageMapper;
    }


    //보드 게시판 글 작성
    @Override
    @Transactional
    public int insertBoard(PostBoardDTO postBoardDTO, Long memberId) {

        Board board = new Board();

        //카테고리 코드 조희
        Map<String, Object> param = Map.of("rootCategory", postBoardDTO.getRootCategory(), "subCategory", postBoardDTO.getSubCategory());
        Long categoryId = boardCategoryCodeMapper.selectCategoryCodeId(param);

        //카테고리 코드가 없을시
        if (categoryId == null) {
            throw new IllegalArgumentException("존재하지 않는 카테고리입니다.");
        } //예외처리 이게 맞나요....? 모르겠슴니다....ㅠㅠ

        board.setTitle(postBoardDTO.getTitle());
        board.setContent(postBoardDTO.getContent());
        board.setFixed(postBoardDTO.getFixed());
        board.setCategoryId(categoryId);
        board.setWriterId(memberId);

        int result = boardMapper.insertSelective(board);

        if(postBoardDTO.getImages() != null && !postBoardDTO.getImages().isEmpty()) {
            List<byte[]> boardImages = postBoardDTO.getImages();
            for (byte[] image : boardImages) {//배열 안의 값이 유효한 값인지 검사
                if (image != null && image.length > 0) {
                    BoardImage boardImage = new BoardImage();
                    boardImage.setImage(image);
                    boardImage.setBoardId(board.getId());
                    boardImageMapper.insertSelective(boardImage);
                }
            }
        }

        return result;
    }

    //보드 게시판 글 수정
    @Override
    @Transactional
    public void updateBoard(Long id, UpdateBoardDTO updateBoardDTO, Long memberId) {

        Board board = new Board();

        //수정하면서 이미지 추가
        if(updateBoardDTO.getImages() != null && !updateBoardDTO.getImages().isEmpty() ) {
            List<byte[]> boardImages = updateBoardDTO.getImages();
            for (byte[] image : boardImages) {
                if(image != null && image.length > 0) {
                    BoardImage boardImage = new BoardImage();
                    boardImage.setImage(image);
                    boardImage.setBoardId(id);
                    boardImageMapper.insertSelective(boardImage);
                }
            }
        }

        //수정하면서 이미지 삭제
        if(updateBoardDTO.getDeleteImages() != null && updateBoardDTO.getDeleteImages().length > 0) {
            for (Long imageId : updateBoardDTO.getDeleteImages()) {
                if(imageId != null) {
                    boardImageMapper.deleteByPrimaryKey(imageId);
                }
            }
        }

        board.setTitle(updateBoardDTO.getTitle());
        board.setContent(updateBoardDTO.getContent());

        //서브 카테고리 변경시
        if(updateBoardDTO.getSubCategory() != null) {

            Long rootCategory = boardMapper.selectRootCategoryId(id);

            Map<String, Object> param = Map.of("rootCategory", rootCategory, "subCategory", updateBoardDTO.getSubCategory());

            Long categoryId = boardCategoryCodeMapper.selectCategoryCodeId(param);

            if (categoryId == null) {
                throw new IllegalArgumentException("존재하지 않는 카테고리입니다.");
            }

            board.setCategoryId(categoryId);
        }


        board.setId(id);
        board.setLastModifiedDate(new Date());
        board.setLastModifiedMember(memberId);

        int result = boardMapper.updateByPrimaryKeySelective(board);

        if (result == 0) {
            throw new NoSuchElementException("존재하지 않는 키입니다.");
        }
    }

    //작성자가 본인인지를 판단하는 메서드
    @Override
    public boolean isWriter(Long id, Long memberId) {
        Long writerId = boardMapper.selectWriterId(id);
        if(writerId == null) {
            throw new NoSuchElementException("존재하지 않는 키입니다.");
        }
        return writerId.equals(memberId);
    }

    //답변이 완료된 게시글인지 판단하는 메서드
    @Override
    public boolean isUpdatable(Long id) {
        return boardMapper.selectByPrimaryKey(id).getCategoryId().equals(9L);
    }


    //보드 게시판 글 소프트삭제
    @Override
    @Transactional
    public void softDeleteBoard(Long id, Long memberId) {
        int result = boardMapper.softDeleteByPrimaryKey(id, memberId);


        if (result == 0) {
            throw new NoSuchElementException("존재하지 않는 키입니다.");
        }

        //게시글에 있는 이미지 삭제
        boardImageMapper.deleteByBoardId(id);

    }
}
