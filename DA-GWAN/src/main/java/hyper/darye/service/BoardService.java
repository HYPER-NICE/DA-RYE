package hyper.darye.service;

import hyper.darye.dto.controller.request.UpdateBoardDTO;
import hyper.darye.dto.controller.request.PostBoardDTO;
import hyper.darye.dto.controller.response.SearchBoardDTO;
import hyper.darye.dto.controller.response.SearchBoardDetailDTO;

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
}
