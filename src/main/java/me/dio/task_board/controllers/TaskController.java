package me.dio.task_board.controllers;

import lombok.AllArgsConstructor;
import me.dio.task_board.dtos.PostTaskDTO;
import me.dio.task_board.models.Task;
import me.dio.task_board.models.TaskList;
import me.dio.task_board.repositories.TaskListRepository;
import me.dio.task_board.repositories.TaskRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
@AllArgsConstructor
@RequestMapping("/task")
public class TaskController {
    private final TaskRepository repository;
    private final TaskListRepository taskListRepository;

    @GetMapping
    public List<Task> getTasks() {
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getTaskById(@PathVariable Integer id) {
        Task taskExists = repository.findById(id).orElse(null);
        if (taskExists == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Erro: Id: " + id + " não encontrado.");
        }
        return ResponseEntity.ok().body(taskExists);
    }

    @PostMapping
    public ResponseEntity<Object> postTask(@RequestBody PostTaskDTO body) {
        if (body.name().isEmpty() || body.deadLine() == null)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Verifique os campos e tente novamente.");

        Optional<TaskList> taskListExists = taskListRepository.findById(body.taskListId());
        if (taskListExists.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("TaskList Id: " + body.taskListId() + " não encontrada!");

        Task newTask = new Task();
        newTask.setName(body.name());
        newTask.setDescription(body.description());
        newTask.setDeadLine(body.deadLine());
        newTask.setTaskList(taskListExists.get());
        repository.save(newTask);

        return ResponseEntity.status(HttpStatus.CREATED).body("Task Criada com sucesso!");
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> putTask(@RequestBody PostTaskDTO body, @PathVariable Integer id) {
        if (body.name().isEmpty() || body.deadLine() == null)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Verifique os campos e tente novamente.");

        Task taskExists = repository.findById(id).orElse(null);
        if (taskExists == null)
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Task Id: " + id + " não encontrada.");

        Optional<TaskList> taskListExists = taskListRepository.findById(body.taskListId());
        if (taskListExists.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("TaskList Id: " + id + " não encontrada.");
        taskExists.setName(body.name());
        taskExists.setDescription(body.description());
        taskExists.setDeadLine(body.deadLine());
        taskExists.setTaskList(taskListExists.get());
        repository.save(taskExists);
        return ResponseEntity.status(HttpStatus.OK).body("Task atualizada com sucesso!");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteTask(@PathVariable Integer id) {
        Task taskExists = repository.findById(id).orElse(null);
        if (taskExists == null)
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Task Id: " + id + " não encontrada.");
        repository.delete(taskExists);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
