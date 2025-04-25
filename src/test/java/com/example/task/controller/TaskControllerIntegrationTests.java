package com.example.task.controller;

import com.example.task.model.Task;
import com.example.task.service.TaskService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.UUID;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class TaskControllerIntegrationTests {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TaskService taskService; // real TaskService object automatically injected in TaskController instance

    @Test
    void should_return_allTasks() throws Exception {
        taskService.addTask("Task 1");

        mockMvc.perform(get("/tasks/list"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].description").value("Task 1"))
                .andExpect(jsonPath("$[0].id").isNotEmpty());
    }

    @Test
    void should_complete_status() throws Exception {
        taskService.addTask("Task 1");
        UUID idTask = taskService.getTasksList().getFirst().getId();

        String request = "/tasks/completeStatus/{idTask}";
        request = request.replace("{idTask}", idTask.toString());

        mockMvc.perform(put(request))
                .andExpect(status().isOk());
    }

    @Test
    void should_delete() throws Exception {
        taskService.addTask("Task 1");
        UUID idTask = taskService.getTasksList().getFirst().getId();

        String request = "/tasks/delete/{idTask}";
        request = request.replace("{idTask}", idTask.toString());

        mockMvc.perform(delete(request))
                .andExpect(status().isOk());
    }

    @Test
    void should_add() throws Exception {
        taskService.addTask("Task 1");
        String description = taskService.getTasksList().getFirst().getDescription();

        String request = "/tasks/add/{description}";
        request = request.replace("{description}", description);

        mockMvc.perform(post(request))
                .andExpect(status().isOk());
    }

}
