package me.dio.task_board.repositories;

import me.dio.task_board.models.TaskList;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskListRepository extends JpaRepository<TaskList, Integer> {
}
