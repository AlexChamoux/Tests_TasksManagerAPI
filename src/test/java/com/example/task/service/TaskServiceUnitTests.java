package com.example.task.service;

import com.example.task.model.Task;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class TaskServiceUnitTests {
    private TaskService taskService;

    @BeforeEach
    void setUp() {
        taskService = new TaskService();
    }

    @Test
    void addTask_should_add_task_to_list() {
        String description = "Test Task";
        taskService.addTask(description);
        assertEquals("Test Task", description);
    }

    @Test
    void addTask_should_id_different(){
        String description4 = "Task 4";
        String description5 = "Task 5";
        taskService.addTask(description4);
        taskService.addTask(description5);

        List<Task> tasks = taskService.getTasksList();
        UUID idTask4 = tasks.getFirst().getId();
        UUID idTask5 = tasks.getLast().getId();

        assertNotEquals(idTask4, idTask5);
    }

    @Test
    void deleteTask_should_remove_task_by_id() {
        taskService.addTask("Task to delete");
        UUID id = taskService.getTasksList().getFirst().getId();

        taskService.deleteTask(id);

        assertEquals(0, taskService.getTasksList().size());
    }

    @Test
    void getTasksList_should_return_all_tasks() {
        List<Task> tasksInit = taskService.getTasksList();
        int nbTask = tasksInit.size();
        taskService.addTask("Task 1");

        List<Task> tasksEnd = taskService.getTasksList();
        assertEquals(nbTask+1, tasksEnd.size());
    }

    @Test
    void changeStateToOver_should_update_task_state() {
        taskService.addTask("Task to complete");
        UUID id = taskService.getTasksList().getFirst().getId();

        taskService.changeState(id);

        List<Task> tasks = taskService.getTasksList();
        assertEquals("terminé", tasks.getFirst().getState());
    }


}
