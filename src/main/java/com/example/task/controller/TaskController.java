package com.example.task.controller;

import com.example.task.service.TaskService;
import com.example.task.model.Task;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/tasks")
public class TaskController {
    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping("/hello")
    public ResponseEntity<String> hello() {
        return ResponseEntity.ok("Welcome to the Task Manager API!");
    }

    @GetMapping ("/list")
    public ResponseEntity<List<Task>> getTasksList() {
        return ResponseEntity.ok(taskService.getTasksList());
    }

    @PutMapping("/completeStatus/{id}")
    public ResponseEntity<Void> changeState(@PathVariable String id) {
        taskService.changeState(UUID.fromString(id));
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        taskService.deleteTask(UUID.fromString(id));
        return ResponseEntity.ok().build();
    }

    @PostMapping("/add/{description}")
    public ResponseEntity<Void> addTask(@PathVariable String description) {
        taskService.addTask(description);
        return ResponseEntity.ok().build();
    }

}
