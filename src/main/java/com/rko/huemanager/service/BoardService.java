package com.rko.huemanager.service;

import com.rko.huemanager.domain.Board;
import com.rko.huemanager.domain.Employee;
import com.rko.huemanager.dto.request.BoardRequest;
import com.rko.huemanager.dto.response.BoardResponse;
import com.rko.huemanager.exception.ErrorCode;
import com.rko.huemanager.exception.HueManagerException;
import com.rko.huemanager.repository.BoardRepository;
import com.rko.huemanager.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class BoardService {
    private final BoardRepository boardRepository;

    private final EmployeeRepository employeeRepository;


    @Transactional
    public void createBoard(Long adminId, BoardRequest request) {
        Employee admin = employeeRepository.findById(adminId)
                .orElseThrow(() -> new HueManagerException(ErrorCode.EMPLOYEE_NOT_FOUND, String.format("employeeId is %s", adminId)));
        boardRepository.save(Board.of(admin, request.title(), request.content()));
    }

    @Transactional
    public void updateBoard(Long adminId, Long boardId, BoardRequest request) {
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new HueManagerException(ErrorCode.BOARD_NOT_FOUND, String.format("boardId is %s", boardId)));


        board.setTitle(request.title());
        board.setContent(request.content());
    }

    @Transactional
    public void deleteBoard(Long adminId, Long boardId) {
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new HueManagerException(ErrorCode.BOARD_NOT_FOUND, String.format("boardId is %s", boardId)));

        boardRepository.delete(board);
    }

    @Transactional(readOnly = true)
    public BoardResponse getBoard(Long boardId) {
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new HueManagerException(ErrorCode.BOARD_NOT_FOUND, String.format("boardId is %s", boardId)));

        return BoardResponse.from(board);
    }

    @Transactional(readOnly = true)
    public Page<BoardResponse> getAllBoard(Pageable pageable) {
        Page<Board> boards = boardRepository.findAll(pageable);
        return boards.map(BoardResponse::from);
    }
}
