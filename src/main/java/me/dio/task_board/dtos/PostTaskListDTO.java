package me.dio.task_board.dtos;

import jakarta.validation.constraints.NotNull;

public record PostTaskListDTO(String name, String description, @NotNull Integer whiteboardId) {
}
