package hyper.darye.service;

import hyper.darye.dto.controller.request.CreateBoardRequestDTO;

public interface BoardService {

    int insertBoard(CreateBoardRequestDTO requestBoard);
}
