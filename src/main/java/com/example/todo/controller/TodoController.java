package com.example.todo.controller;

import com.example.todo.dto.TodoDto;
import com.example.todo.service.TodoService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/todos")
@AllArgsConstructor
public class TodoController {
    private TodoService todoService;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<TodoDto>addTodo(@RequestBody TodoDto todoDto)
    {
        TodoDto savedTodo = todoService.addTodo(todoDto);
        return new ResponseEntity<>(savedTodo, HttpStatus.CREATED);
    }

    @GetMapping("{id}")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public ResponseEntity<TodoDto> getTodo(@PathVariable("id") Long todoId)
    {
        TodoDto todoDto = todoService.getTodo(todoId);

        return new ResponseEntity<>(todoDto,HttpStatus.OK);
    }

    @GetMapping()
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public ResponseEntity<List<TodoDto>> getAllTodo()
    {
        List<TodoDto> todoDtos = todoService.getAllTodo();

        return new ResponseEntity<>(todoDtos,HttpStatus.OK);
    }
    @PutMapping("{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<TodoDto> updateTodoDto(@PathVariable("id")Long todoId,@RequestBody TodoDto todoDto)
    {
        TodoDto todoDto1 = todoService.updateTodo(todoDto,todoId);
        return new ResponseEntity<>(todoDto1,HttpStatus.OK);
    }
    @DeleteMapping("{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> deleteTodo(@PathVariable("id") Long todoId)
    {
      String message = todoService.deleteTodo(todoId);
      return new ResponseEntity<>(message,HttpStatus.OK);
    }
    @PatchMapping("{id}/complete")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public ResponseEntity<TodoDto> completeTodo(@PathVariable("id")Long id)
    {
        TodoDto todoDto = todoService.completeTodo(id);
        return  new ResponseEntity<>(todoDto,HttpStatus.OK);
    }

    @PatchMapping("{id}/incomplete")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public ResponseEntity<TodoDto> incompleteTodo(@PathVariable("id")Long id)
    {
        TodoDto todoDto = todoService.inCompleteTodo(id);
        return new ResponseEntity<>(todoDto,HttpStatus.OK);
    }
}
