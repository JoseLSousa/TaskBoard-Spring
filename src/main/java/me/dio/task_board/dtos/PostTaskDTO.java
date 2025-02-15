package me.dio.task_board.dtos;

import me.dio.task_board.models.Priority;

import java.time.LocalDateTime;

public record PostTaskDTO(String name, String description, Priority priority, LocalDateTime deadLine, Integer taskListId) {
}
