package me.dio.task_board.repositories;

import me.dio.task_board.models.Whiteboard;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WhiteboardRepository extends JpaRepository<Whiteboard, Integer> {
}
