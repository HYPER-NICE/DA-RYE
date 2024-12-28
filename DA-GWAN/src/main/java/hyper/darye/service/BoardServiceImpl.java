package hyper.darye.service;

import hyper.darye.dto.Board;
import hyper.darye.dto.BoardImage;
import hyper.darye.dto.controller.request.PostReplyDTO;
import hyper.darye.dto.controller.request.UpdateBoardDTO;
import hyper.darye.dto.controller.request.PostBoardDTO;
import hyper.darye.dto.controller.request.UpdateReplyDTO;
import hyper.darye.dto.controller.response.SearchBoardDTO;
import hyper.darye.dto.controller.response.SearchBoardDetailDTO;
import hyper.darye.dto.controller.response.SearchReplyDTO;
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

    //보드 게시판 전체 조회(목록만)
    @Override
    public List<SearchBoardDTO> selectAllBoard(Long rootCategoryId, Long subCategoryId) {

        //서브 카테고리 선택하지 않았을 시 전체 목록 조회
        if (subCategoryId == null) {
            List<Long> categoryIds = boardCategoryCodeMapper.selectAllCategoryCodeId(rootCategoryId);

            List<Board> boards = boardMapper.selectAllCategory(categoryIds);

            return boards.stream()
                    .map(board -> {
                        SearchBoardDTO dto = new SearchBoardDTO();
                        dto.setId(board.getId());
                        dto.setTitle(board.getTitle());
                        dto.setSubCategoryId(boardMapper.selectSubCategoryId(board.getId()));
                        dto.setSubCategoryName(boardMapper.selectSubCategoryName(board.getId()));
                        dto.setRegDate(board.getRegDate());

                        return dto;
                    })
                    .toList();
        }

        //서브 카테고리 선택했을 시 해당 카테고리만 조회
        Map<String, Object> param = Map.of("rootCategory", rootCategoryId, "subCategory", subCategoryId);
        Long categoryId = boardCategoryCodeMapper.selectCategoryCodeId(param);

        List<Board> boards = boardMapper.selectAll(categoryId);

        return boards.stream()
                .map(board -> {
                    SearchBoardDTO dto = new SearchBoardDTO();
                    dto.setId(board.getId());
                    dto.setTitle(board.getTitle());
                    dto.setSubCategoryId(subCategoryId);
                    dto.setSubCategoryName(boardMapper.selectSubCategoryName(board.getId()));
                    dto.setRegDate(board.getRegDate());

                    return dto;
                })
                .toList();
        }

    //보드 게시판 글 상세 조회
    @Override
    public SearchBoardDetailDTO selectBoardDetail(Long id) {
        Board board = boardMapper.selectBoard(id);

        if (board == null) {
                throw new NoSuchElementException("존재하지 않는 키입니다.");
        }

        List<BoardImage> boardImages = boardImageMapper.selectByBoardId(id);

        SearchBoardDetailDTO dto = new SearchBoardDetailDTO();
        dto.setId(board.getId());
        dto.setTitle(board.getTitle());
        dto.setContent(board.getContent());
        dto.setRegDate(board.getRegDate());
        dto.setLastModifiedDate(board.getLastModifiedDate());
        dto.setSubCategoryId(boardMapper.selectSubCategoryId(board.getId()));
        dto.setSubCategoryName(boardMapper.selectSubCategoryName(board.getId()));
        dto.setImages(boardImages);

            return dto;
        }


    //1대1 문의 자신의 질문만 조회
    @Override
    public List<SearchBoardDTO> selectOneBoard(Long rootCategoryId, Long subCategoryId, Long memberId) {

        //서브 카테고리 선택하지 않았을 시 전체 목록 조회
        if (subCategoryId == null) {
            List<Long> categoryIds = boardCategoryCodeMapper.selectAllCategoryCodeId(rootCategoryId);

            List<Board> boards = boardMapper.selectByWriterId(memberId);

            return boards.stream()
                    .map(board -> {
                        SearchBoardDTO dto = new SearchBoardDTO();
                        dto.setId(board.getId());
                        dto.setTitle(board.getTitle());
                        dto.setSubCategoryId(boardMapper.selectSubCategoryId(board.getId()));
                        dto.setSubCategoryName(boardMapper.selectSubCategoryName(board.getId()));
                        dto.setRegDate(board.getRegDate());

                        return dto;
                    })
                    .toList();
            }

        //서브 카테고리 선택했을 시 해당 카테고리만 조회
        Map<String, Object> param = Map.of("rootCategory", rootCategoryId, "subCategory", subCategoryId);
        Long categoryId = boardCategoryCodeMapper.selectCategoryCodeId(param);

        List<Board> boards = boardMapper.selectByWriterIdAndCategoryId(memberId, categoryId);

        return boards.stream()
                .map(board -> {
                    SearchBoardDTO dto = new SearchBoardDTO();
                    dto.setId(board.getId());
                    dto.setTitle(board.getTitle());
                    dto.setSubCategoryId(subCategoryId);
                    dto.setSubCategoryName(boardMapper.selectSubCategoryName(board.getId()));
                    dto.setRegDate(board.getRegDate());

                    return dto;
                })
                .toList();
        }

    //1대1 문의 댓글 작성
    @Transactional
    @Override
    public int insertReply(Long id, PostReplyDTO postReplyDTO, Long memberId) {
        Board board = new Board();

        if(id == null) {
            throw new IllegalArgumentException("존재하지 않는 키입니다.");
        }

        board.setParentId(id);
        board.setTitle("RE: ");
        board.setContent(postReplyDTO.getContent());
        board.setWriterId(memberId);
        board.setCategoryId(9L);

        int result = boardMapper.insertSelective(board);

        if(postReplyDTO.getImages() != null && !postReplyDTO.getImages().isEmpty()) {
            List<byte[]> boardImages = postReplyDTO.getImages();
            for (byte[] image : boardImages) {//배열 안의 값이 유효한 값인지 검사
                if (image != null && image.length > 0) {
                    BoardImage boardImage = new BoardImage();
                    boardImage.setImage(image);
                    boardImage.setBoardId(board.getId());
                    boardImageMapper.insertSelective(boardImage);
                }
            }
        }

        //답변이 달린 글은 카테고리 변경
        boardMapper.updateCategory(id, 9L);

        return result;
    }


    //1대1 문의 댓글 수정
    @Transactional
    @Override
    public void updateReply(Long replyId, UpdateReplyDTO updateReplyDTO, Long memberId) {

        Board board = new Board();

            //수정하면서 이미지 추가
            if(updateReplyDTO.getImages() != null && !updateReplyDTO.getImages().isEmpty() ) {
                List<byte[]> boardImages = updateReplyDTO.getImages();
                for (byte[] image : boardImages) {
                    if(image != null && image.length > 0) {
                        BoardImage boardImage = new BoardImage();
                        boardImage.setImage(image);
                        boardImage.setBoardId(replyId);
                        boardImageMapper.insertSelective(boardImage);
                    }
                }
            }

            //수정하면서 이미지 삭제
            if(updateReplyDTO.getDeleteImages() != null && updateReplyDTO.getDeleteImages().length > 0) {
                for (Long imageId : updateReplyDTO.getDeleteImages()) {
                    if(imageId != null) {
                        boardImageMapper.deleteByPrimaryKey(imageId);
                    }
                }
            }

            board.setContent(updateReplyDTO.getContent());

            board.setId(replyId);
            board.setLastModifiedDate(new Date());
            board.setLastModifiedMember(memberId);

            int result = boardMapper.updateByPrimaryKeySelective(board);

            if (result == 0) {
                throw new NoSuchElementException("존재하지 않는 키입니다.");
            }
        }

    //1대1 문의 댓글 조회
    @Transactional
    @Override
    public List<SearchReplyDTO> selectAllReply(Long id) {

        List<Board> replays = boardMapper.selectByParentId(id);

        if (replays == null) {
            throw new NoSuchElementException("존재하지 않는 키입니다.");
        }

        return replays.stream()
                .map(reply -> {
                    SearchReplyDTO dto = new SearchReplyDTO();
                    dto.setId(reply.getId());
                    dto.setContent(reply.getContent());
                    dto.setRegDate(reply.getRegDate());
                    dto.setParentId(reply.getParentId());
                    dto.setImages(boardImageMapper.selectByBoardId(reply.getId()));

                    return dto;
                })
                .toList();
    }
}
