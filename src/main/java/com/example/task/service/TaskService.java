package com.example.task.service;

import com.example.task.model.Task;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class TaskService {
    private final List<Task> tasks = new ArrayList<>();

    public TaskService() {
//        tasks.add(new Task("Task 1"));
//        tasks.add(new Task("Task 2"));
//        tasks.add(new Task("Task 3"));
    }

    public void addTask(String description) {
        tasks.add(new Task(description));
    }

    public void deleteTask(UUID id) {
        tasks.removeIf(task -> task.getId().equals(id));
    }

    public List<Task> getTasksList() {
        return tasks;
    }

    public void changeState(UUID id){
        for(int i=0; i<tasks.size(); i++){
            if(tasks.get(i).getId().equals(id)){
                tasks.get(i).setStateOver();
            }
        }
    }
}
