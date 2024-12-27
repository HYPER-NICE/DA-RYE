package hyper.darye.service;

import hyper.darye.dto.controller.request.UpdateBoardDTO;
import hyper.darye.dto.controller.request.PostBoardDTO;
import hyper.darye.dto.controller.response.SearchBoardDTO;

import java.util.List;

public interface BoardService {

    int insertBoard(PostBoardDTO postBoardDTO, Long memberID);

    void updateBoard(Long id, UpdateBoardDTO updateBoardDTO, Long memberID);

    boolean isWriter(Long id, Long memberID);

    boolean isUpdatable(Long id);

    void softDeleteBoard(Long id, Long memberID);

    List<SearchBoardDTO> selectAllBoard(Long rootCategoryId, Long subCategoryId);
}
