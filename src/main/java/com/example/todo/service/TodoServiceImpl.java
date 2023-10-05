package com.example.todo.service;

import com.example.todo.dto.TodoDto;
import com.example.todo.entity.Todo;
import com.example.todo.exception.ResourceNotFoundException;
import com.example.todo.repository.TodoRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class TodoServiceImpl implements TodoService{
    private TodoRepository todoRepository;
    private ModelMapper modelMapper;
    @Override
    public TodoDto addTodo(TodoDto todoDto) {

        //convert dto to jpa
        Todo todo = modelMapper.map(todoDto,Todo.class);
        //Todo to Jpa
        Todo savedTodo = todoRepository.save(todo);
        TodoDto savedTodoDto = modelMapper.map(savedTodo,TodoDto.class);
        return savedTodoDto;
    }

    @Override
    public TodoDto getTodo(Long todoId) {
        Todo todo = todoRepository.findById(todoId).
                orElseThrow(()->new ResourceNotFoundException("Todo not found with id: "+todoId));

        TodoDto existingUser = modelMapper.map(todo,TodoDto.class);
        return existingUser;
    }

    @Override
    public List<TodoDto> getAllTodo() {
        List<Todo> todos = todoRepository.findAll();
        return todos.stream().map((todo)->modelMapper.map(todo,TodoDto.class)).collect(Collectors.toList());
    }

    @Override
    public TodoDto updateTodo(TodoDto todoDto,Long todoId) {
        Todo todo = todoRepository.findById(todoId).
                orElseThrow(()-> new ResourceNotFoundException("Todo not found with id"+todoId));

        todo.setTitle(todoDto.getTitle());
        todo.setDescription(todoDto.getDescription());
        todo.setCompleted(todoDto.isCompleted());
        Todo updatedTodo = todoRepository.save(todo);
        TodoDto updatedTodoDto = modelMapper.map(updatedTodo,TodoDto.class);
        return updatedTodoDto;
    }

    @Override
    public String deleteTodo(Long todoId) {
        Todo todo = todoRepository.findById(todoId).
                orElseThrow(()-> new ResourceNotFoundException("Todo not found with id"+todoId));
        todoRepository.deleteById(todoId);
        return todoId+"has been removed successfully";
    }

    @Override
    public TodoDto completeTodo(Long id) {
        Todo todo = todoRepository.findById(id).
                orElseThrow(()-> new ResourceNotFoundException("Todo not found with id"+id));
        todo.setCompleted(Boolean.TRUE);
        Todo updatedTodo = todoRepository.save(todo);

        return modelMapper.map(updatedTodo,TodoDto.class);
    }

    @Override
    public TodoDto inCompleteTodo(Long id) {
        Todo todo = todoRepository.findById(id).
                orElseThrow(()-> new ResourceNotFoundException("Not found with id"+id));
        todo.setCompleted(Boolean.FALSE);
        Todo updatedTodo = todoRepository.save(todo);

        return modelMapper.map(updatedTodo,TodoDto.class);
    }


}
