package com.example.todo.controller;

import com.example.todo.dto.TodoDto;
import com.example.todo.service.TodoService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/todos")
@AllArgsConstructor
public class TodoController {
    private TodoService todoService;

    @PostMapping
    public ResponseEntity<TodoDto>addTodo(@RequestBody TodoDto todoDto)
    {
        TodoDto savedTodo = todoService.addTodo(todoDto);
        return new ResponseEntity<>(savedTodo, HttpStatus.CREATED);
    }

    @GetMapping("{id}")
    public ResponseEntity<TodoDto> getTodo(@PathVariable("id") Long todoId)
    {
        TodoDto todoDto = todoService.getTodo(todoId);

        return new ResponseEntity<>(todoDto,HttpStatus.OK);
    }

    @GetMapping()
    public ResponseEntity<List<TodoDto>> getAllTodo()
    {
        List<TodoDto> todoDtos = todoService.getAllTodo();

        return new ResponseEntity<>(todoDtos,HttpStatus.OK);
    }
    @PutMapping("{id}")
    public ResponseEntity<TodoDto> updateTodoDto(@PathVariable("id")Long todoId,@RequestBody TodoDto todoDto)
    {
        TodoDto todoDto1 = todoService.updateTodo(todoDto,todoId);
        return new ResponseEntity<>(todoDto1,HttpStatus.OK);
    }
    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteTodo(@PathVariable("id") Long todoId)
    {
      String message = todoService.deleteTodo(todoId);
      return new ResponseEntity<>(message,HttpStatus.OK);
    }
}
