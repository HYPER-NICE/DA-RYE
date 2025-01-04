package hyper.darye.mvc.service;

import hyper.darye.mvc.model.dto.controller.request.PostReplyDTO;
import hyper.darye.mvc.model.dto.controller.request.UpdateBoardDTO;
import hyper.darye.mvc.model.dto.controller.request.PostBoardDTO;
import hyper.darye.mvc.model.dto.controller.request.UpdateReplyDTO;
import hyper.darye.mvc.model.dto.controller.response.SearchBoardDTO;
import hyper.darye.mvc.model.dto.controller.response.SearchBoardDetailDTO;
import hyper.darye.mvc.model.dto.controller.response.SearchReplyDTO;

import java.util.List;

public interface BoardService {

    int insertBoard(PostBoardDTO postBoardDTO, Long memberID);

    void updateBoard(Long id, UpdateBoardDTO updateBoardDTO, Long memberID);

    boolean isWriter(Long id, Long memberID);

    boolean isUpdatable(Long id);

    void softDeleteBoard(Long id, Long memberID);

    //보드 게시판 글 전체 조회
    List<SearchBoardDTO> selectAllBoard(Long rootCategoryId, Long subCategoryId);

    //보드 게시판 글 상세 조회
    SearchBoardDetailDTO selectBoardDetail(Long id);

    //1대1 문의 자신의 질문만 조회
    List<SearchBoardDTO> selectOneBoard(Long rootCategoryId, Long subCategoryId, Long memberId);

    //1대1 문의 댓글 작성
    int insertReply(Long id, PostReplyDTO postReplyDTO, Long memberId);

    //1대1 문의 댓글 수정
    void updateReply(Long replyId, UpdateReplyDTO updateReplyDTO, Long memberId);

    //1대1 문의 댓글 조회
    List<SearchReplyDTO> selectAllReply(Long id);
}
