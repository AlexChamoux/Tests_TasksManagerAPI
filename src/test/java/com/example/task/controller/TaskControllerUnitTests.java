package com.example.task.controller;

import com.example.task.model.Task;
import com.example.task.service.TaskService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.UUID;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(TaskController.class)
public class TaskControllerUnitTests {
    @Autowired
    private MockMvc mockMvc; //Object used to make HTTP request on our API

    @MockitoBean
    private TaskService taskService; //Mock object TaskService automatically injected in TaskController instance

    @Test
    void hello_should_return_message() throws Exception {
        mockMvc.perform(get("/tasks/hello"))
                .andExpect(status().isOk())
                .andExpect(content().string("Welcome to the Task Manager API!"));
    }

    @Test
    void should_return_allTasks() throws Exception {
        ArrayList<Task> tasks = new ArrayList<>();
        tasks.add(new Task("Task 1"));
        tasks.add(new Task("Task 2"));

        when(taskService.getTasksList()).thenReturn(tasks);


        mockMvc.perform(get("/tasks/list"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].description").value("Task 1"))
                .andExpect(jsonPath("$[0].id").isNotEmpty())
                .andExpect(jsonPath("$[1].description").value("Task 2"))
                .andExpect(jsonPath("$[1].id").isNotEmpty());

        verify(taskService, times(1)).getTasksList();
    }

    @Test
    void should_complete_status() throws Exception {
        Task task = new Task("Task 1");
        UUID idTask = task.getId();
        doNothing().when(taskService).changeState(idTask);

        String request = "/tasks/completeStatus/{idTask}";
        request = request.replace("{idTask}", idTask.toString());

        mockMvc.perform(put(request))
                .andExpect(status().isOk());

        verify(taskService, times(1)).changeState(idTask);
    }

    @Test
    void should_delete() throws Exception {
        Task task1 = new Task("Task 1");
        UUID idTask1 = task1.getId();
        doNothing().when(taskService).deleteTask(idTask1);

        String request = "/tasks/delete/{idTask}";
        request = request.replace("{idTask}", idTask1.toString());

        mockMvc.perform(delete(request))
                .andExpect(status().isOk());

        verify(taskService, times(1)).deleteTask(idTask1);

    }

    @Test
    void should_add() throws Exception {
        Task task = new Task("Task 1");
        String description = task.getDescription();
        doNothing().when(taskService).addTask(description);

        String request = "/tasks/add/{description}";
        request = request.replace("{description}", description);

        mockMvc.perform(post(request))
                .andExpect(status().isOk());

        verify(taskService, times(1)).addTask(description);
    }

}
