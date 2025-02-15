package me.dio.task_board.controllers;

import lombok.AllArgsConstructor;
import me.dio.task_board.dtos.PostWhiteboardDTO;
import me.dio.task_board.models.Whiteboard;
import me.dio.task_board.repositories.WhiteboardRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/whiteboard")
@AllArgsConstructor
public class WhiteboardController {
    private final WhiteboardRepository repository;

    @GetMapping
    public List<Whiteboard> getWhiteboards() {
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getWhiteboardById(@PathVariable Integer id) {
        Whiteboard whiteboard = repository.findById(id).orElse(null);
        if (whiteboard == null)
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Whiteboard não encontrado.");

        return ResponseEntity.status(HttpStatus.OK).body(whiteboard);
    }

    @PostMapping
    public ResponseEntity<Object> postWhiteboard(@RequestBody PostWhiteboardDTO body) {
        if (body.name().isEmpty())
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Verifique os campos e tente novamente.");

        Whiteboard newWhiteboard = new Whiteboard();
        newWhiteboard.setName(body.name());
        repository.save(newWhiteboard);
        return ResponseEntity.status(HttpStatus.OK).body("Whiteboard criado com sucesso!");
    }

    @PutMapping("{id}")
    public ResponseEntity<Object> putWhiteboard(@PathVariable Integer id, @RequestBody PostWhiteboardDTO body) {
        Whiteboard existingWhiteboard = repository.findById(id).orElse(null);
        if (existingWhiteboard == null)
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Whiteboard Id: " + id + " não encontrado.");
        if (body.name().isEmpty())
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Verifique os campos e tente novamente.");

        existingWhiteboard.setName(body.name());

        repository.save(existingWhiteboard);

        return ResponseEntity.status(HttpStatus.OK).body("Whiteboard atualizado com sucesso!");
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Object> deleteWhiteboard(@PathVariable Integer id) {
        Whiteboard whiteboardExists = repository.findById(id).orElse(null);
        if (whiteboardExists == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Whiteboard Id: " + id + " não encontrado.");
        }

        repository.delete(whiteboardExists);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
