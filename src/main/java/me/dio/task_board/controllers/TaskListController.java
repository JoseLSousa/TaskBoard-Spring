package me.dio.task_board.controllers;

import lombok.AllArgsConstructor;
import me.dio.task_board.dtos.PostTaskListDTO;

import me.dio.task_board.models.TaskList;
import me.dio.task_board.models.Whiteboard;
import me.dio.task_board.repositories.TaskListRepository;
import me.dio.task_board.repositories.WhiteboardRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RestController
@AllArgsConstructor
@RequestMapping("/tasklist")
public class TaskListController {
    private final TaskListRepository repository;
    private final WhiteboardRepository whiteboardRepository;

    @GetMapping
    public List<TaskList> getTaskLists() {
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getTaskListById(@PathVariable Integer id) {
        TaskList taskListExists = repository.findById(id).orElse(null);
        if (taskListExists == null)
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("TaskList Id: " + id + " não encontrado.");
        return ResponseEntity.status(HttpStatus.OK).body(taskListExists);
    }

    @PostMapping
    public ResponseEntity<Object> postTaskList(@RequestBody PostTaskListDTO body) {
        Whiteboard whiteboard = whiteboardRepository.findById(body.whiteboardId())
                .orElse(null);
        if (whiteboard == null)
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Whiteboard Id: " + body.whiteboardId() + " não encontrado!");

        TaskList newTaskList = new TaskList();
        newTaskList.setName(body.name());
        newTaskList.setDescription(body.description());
        newTaskList.setWhiteboard(whiteboard);

        TaskList savedTaskList = repository.save(newTaskList);


        return ResponseEntity.status(HttpStatus.CREATED).body(savedTaskList);
    }


    @PutMapping("{id}")
    public ResponseEntity<Object> putTaskList(@PathVariable Integer id, @RequestBody PostTaskListDTO body) {
        TaskList taskListExists = repository.findById(id).orElse(null);
        if (taskListExists == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        Optional<Whiteboard> whiteboardExists = whiteboardRepository.findById(body.whiteboardId());

        if (whiteboardExists.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Erro: Whiteboard" + body.whiteboardId() + "não existe");
        }

        taskListExists.setName(body.name());
        taskListExists.setDescription(body.description());
        taskListExists.setWhiteboard(whiteboardExists.get());

        repository.save(taskListExists);

        return ResponseEntity.ok(taskListExists);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Object> deleteTaskList(@PathVariable Integer id) {
        TaskList taskListExists = repository.findById(id).orElse(null);
        if (taskListExists == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("TaskList Id:" + id + "não encontrado!");
        }
        repository.delete(taskListExists);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("TaskList Id:" + id + "excluído!");
    }
}
