package com.example.todo.service;

import com.example.todo.dto.TodoDto;

import java.util.List;

public interface TodoService {

    TodoDto addTodo(TodoDto todoDto);

    TodoDto getTodo(Long todoId);

    List<TodoDto> getAllTodo();

    TodoDto updateTodo(TodoDto todoDto,Long todoId);

    String deleteTodo(Long todoId);
}
