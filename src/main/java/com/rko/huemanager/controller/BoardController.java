package com.rko.huemanager.controller;

import com.rko.huemanager.aop.RequireAdminRole;
import com.rko.huemanager.domain.Employee;
import com.rko.huemanager.dto.request.BoardRequest;
import com.rko.huemanager.dto.response.BoardResponse;
import com.rko.huemanager.dto.response.Response;
import com.rko.huemanager.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/board")
public class BoardController {
    private final BoardService boardService;

    @RequireAdminRole
    @PostMapping("/create")
    public Response<Void> createBoard(@AuthenticationPrincipal Employee employee, @RequestBody BoardRequest request) {
        boardService.createBoard(employee.getId(), request);
        return Response.success();
    }

    @RequireAdminRole
    @PostMapping("/update/{boardId}")
    public Response<Void> updateBoard(@AuthenticationPrincipal Employee employee, @PathVariable Long boardId, @RequestBody BoardRequest request) {
        boardService.updateBoard(employee.getId(), boardId, request);
        return Response.success();
    }

    @RequireAdminRole
    @DeleteMapping("/delete/{boardId}")
    public Response<Void> deleteBoard(@AuthenticationPrincipal Employee employee, @PathVariable Long boardId) {
        boardService.deleteBoard(employee.getId(), boardId);
        return Response.success();
    }

    @GetMapping("/{boardId}")
    public Response<BoardResponse> getBoard(@PathVariable Long boardId) {
        return Response.success(boardService.getBoard(boardId));
    }

    @GetMapping("/all")
    public Response<Page<BoardResponse>> getAllBoard(Pageable pageable) {
        return Response.success(boardService.getAllBoard(pageable));
    }

}
