package si.um.feri.Backend.controller;

import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import si.um.feri.Backend.model.Task;
import si.um.feri.Backend.model.TaskStatus;
import si.um.feri.Backend.repository.TaskRepository;

import java.util.Arrays;
import java.util.Collections;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TaskController.class)
class TaskControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TaskRepository taskRepository;

    @RepeatedTest(3)
    void deleteTask_ShouldReturnSuccessMessage() throws Exception {
        Mockito.doNothing().when(taskRepository).deleteById(1);

        mockMvc.perform(delete("/task/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("Task with id: 1 has been deleted."));
    }

    @RepeatedTest(3)
    void deleteTask_ShouldReturnNotFoundForInvalidId() throws Exception {
        Mockito.doThrow(new org.springframework.web.server.ResponseStatusException(
                org.springframework.http.HttpStatus.NOT_FOUND, "Task not found with id:999")).when(taskRepository).deleteById(999);

        mockMvc.perform(delete("/task/999"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Task not found with id:999"));
    }

    @ParameterizedTest
    @ValueSource(strings = {"Task 1", "Task 2", "Task 3"})
    void getAllTasks_ShouldContainTaskNames(String taskName) throws Exception {
        Task task = new Task(taskName, "Description", null, TaskStatus.IN_PROGRESS);
        Mockito.when(taskRepository.findAll()).thenReturn(Arrays.asList(task));

        mockMvc.perform(get("/tasks"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].taskName").value(taskName));
    }

    @ParameterizedTest
    @ValueSource(strings = {"Invalid Task 1", "Invalid Task 2"})
    void getAllTasks_ShouldReturnEmptyListForInvalidTasks(String taskName) throws Exception {
        Mockito.when(taskRepository.findAll()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/tasks"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isEmpty());
    }
}
