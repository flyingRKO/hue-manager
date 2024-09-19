package com.rko.huemanager.dto.response;

import com.rko.huemanager.domain.Board;

import java.sql.Timestamp;

public record BoardResponse(
        Long id,
        String title,
        String content,
        String author,
        Timestamp createdDate,
        Timestamp modifiedDate
) {
    public static BoardResponse from(Board board) {
        return new BoardResponse(
                board.getId(),
                board.getTitle(),
                board.getContent(),
                board.getEmployee().getRole().getDescription(),
                board.getRegisteredAt(),
                board.getUpdatedAt());
    }
}
