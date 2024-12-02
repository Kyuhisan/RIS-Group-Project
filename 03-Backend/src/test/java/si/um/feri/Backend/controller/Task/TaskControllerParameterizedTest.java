package si.um.feri.Backend.controller;

import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import si.um.feri.Backend.model.Task;
import si.um.feri.Backend.model.TaskStatus;
import si.um.feri.Backend.repository.TaskRepository;

import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TaskController.class)
@ExtendWith(SpringExtension.class)
@TestMethodOrder(OrderAnnotation.class)
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class TaskControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TaskRepository taskRepository;

    @RepeatedTest(3)
    @DisplayName("Delete Task - Success")
    void deleteTask_ShouldReturnSuccessMessage() throws Exception {
        Mockito.doNothing().when(taskRepository).deleteById(1);

        mockMvc.perform(delete("/task/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("Task with id: 1 has been deleted."));
    }

    @RepeatedTest(3)
    @DisplayName("Delete Task - Not Found")
    void deleteTask_ShouldReturnNotFoundForInvalidId() throws Exception {
        Mockito.doThrow(new ResponseStatusException(HttpStatus.NOT_FOUND, "Task not found with id:999"))
                .when(taskRepository).deleteById(999);

        mockMvc.perform(delete("/task/999"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Task not found with id:999"));
    }

    @ParameterizedTest
    @ValueSource(strings = {"Task 1", "Task 2", "Task 3"})
    @DisplayName("Get All Tasks - Contains Task Names")
    void getAllTasks_ShouldContainTaskNames(String taskName) throws Exception {
        Task task = new Task(taskName, "Description", null, TaskStatus.IN_PROGRESS);
        Mockito.when(taskRepository.findAll()).thenReturn(Arrays.asList(task));

        mockMvc.perform(get("/tasks"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].taskName").value(taskName));
    }

    @ParameterizedTest
    @ValueSource(strings = {"Invalid Task 1", "Invalid Task 2"})
    @DisplayName("Get All Tasks - Return Empty List for Invalid Tasks")
    void getAllTasks_ShouldReturnEmptyListForInvalidTasks(String taskName) throws Exception {
        Mockito.when(taskRepository.findAll()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/tasks"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isEmpty());
    }

    @Test
    @DisplayName("Get Task by ID - Success")
    void getTaskById_ShouldReturnTask() throws Exception {
        Task task = new Task("Task 1", "Description", null, TaskStatus.IN_PROGRESS);
        Mockito.when(taskRepository.findById(1)).thenReturn(Optional.of(task));

        mockMvc.perform(get("/task/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.taskName").value("Task 1"))
                .andExpect(jsonPath("$.taskDescription").value("Description"));
    }

    @Test
    @DisplayName("Get Task by ID - Not Found")
    void getTaskById_ShouldReturnNotFound() throws Exception {
        Mockito.when(taskRepository.findById(999)).thenReturn(Optional.empty());

        mockMvc.perform(get("/task/999"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Task not found with id:999"));
    }

    @Test
    @DisplayName("Create Task - Success")
    void createTask_ShouldReturnCreatedTask() throws Exception {
        Task task = new Task("Task 1", "Description", null, TaskStatus.IN_PROGRESS);
        Mockito.when(taskRepository.save(Mockito.any(Task.class))).thenReturn(task);

        mockMvc.perform(post("/task")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"taskName\":\"Task 1\",\"taskDescription\":\"Description\",\"status\":\"IN_PROGRESS\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.taskName").value("Task 1"))
                .andExpect(jsonPath("$.taskDescription").value("Description"));
    }

    @Test
    @DisplayName("Update Task - Success")
    void updateTask_ShouldReturnUpdatedTask() throws Exception {
        Task existingTask = new Task("Old Task", "Old Description", null, TaskStatus.IN_PROGRESS);
        Task updatedTask = new Task("Updated Task", "Updated Description", null, TaskStatus.COMPLETED);

        Mockito.when(taskRepository.findById(1)).thenReturn(Optional.of(existingTask));
        Mockito.when(taskRepository.save(Mockito.any(Task.class))).thenReturn(updatedTask);

        mockMvc.perform(put("/task/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"taskName\":\"Updated Task\",\"taskDescription\":\"Updated Description\",\"status\":\"COMPLETED\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.taskName").value("Updated Task"))
                .andExpect(jsonPath("$.taskDescription").value("Updated Description"))
                .andExpect(jsonPath("$.status").value("COMPLETED"));
    }

    @Test
    @DisplayName("Update Task - Not Found")
    void updateTask_ShouldReturnNotFound() throws Exception {
        Mockito.when(taskRepository.findById(999)).thenReturn(Optional.empty());

        mockMvc.perform(put("/task/999")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"taskName\":\"Updated Task\",\"taskDescription\":\"Updated Description\",\"status\":\"COMPLETED\"}"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Task not found with id:999"));
    }
}
