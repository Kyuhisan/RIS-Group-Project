package si.um.feri.Backend.controller;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import si.um.feri.Backend.model.Task;
import si.um.feri.Backend.model.TaskStatus;
import si.um.feri.Backend.repository.TaskRepository;

import java.util.Arrays;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(TaskController.class)
class TaskControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TaskRepository taskRepository;

    @Test
    void getAllTasks_ShouldReturnTasks() throws Exception {
        // Arrange
        Task task1 = new Task("Task 1", "Description 1", null, TaskStatus.IN_PROGRESS);
        Task task2 = new Task("Task 2", "Description 2", null, TaskStatus.NOT_STARTED);

        Mockito.when(taskRepository.findAll()).thenReturn(Arrays.asList(task1, task2));

        // Act & Assert
        mockMvc.perform(get("/tasks"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].taskName").value("Task 1"))
                .andExpect(jsonPath("$[1].taskName").value("Task 2"));
    }
}
