package hyper.darye.service;

import hyper.darye.dto.controller.request.PostBoardDTO;

public interface BoardService {

    int insertBoard(PostBoardDTO postBoardDTO);
}
