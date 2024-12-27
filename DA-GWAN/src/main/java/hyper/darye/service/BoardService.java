package hyper.darye.service;

import hyper.darye.dto.controller.request.UpdateBoardDTO;
import hyper.darye.dto.controller.request.PostBoardDTO;

public interface BoardService {

    int insertBoard(PostBoardDTO postBoardDTO, Long memberID);

    void updateBoard(Long id, UpdateBoardDTO updateBoardDTO, Long memberID);

    boolean isWriter(Long id, Long memberID);

    boolean isUpdatable(Long id);

    void softDeleteBoard(Long id, Long memberID);
}
