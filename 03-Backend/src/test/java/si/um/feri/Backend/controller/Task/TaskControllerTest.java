package si.um.feri.Backend.controller.Task;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.server.ResponseStatusException;
import si.um.feri.Backend.controller.TaskController;
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

    @BeforeAll
    static void initAll() {
        System.out.println("Starting TaskController tests...");
    }

    @AfterAll
    static void tearDownAll() {
        System.out.println("Completed TaskController tests.");
    }

    @BeforeEach
    void setup() {
        System.out.println("Setting up mock data for a test...");
    }

    @AfterEach
    void cleanup() {
        System.out.println("Cleaning up after a test...");
    }

    // Testira, ali metoda getAllTasks vrne seznam nalog, ki vsebujejo določena imena nalog
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

    // Testira, ali metoda getAllTasks vrne prazen seznam, če ni najdenih nalog
    @ParameterizedTest
    @ValueSource(strings = {"Invalid Task 1", "Invalid Task 2"})
    @DisplayName("Get All Tasks - Return Empty List for Invalid Tasks")
    void getAllTasks_ShouldReturnEmptyListForInvalidTasks(String taskName) throws Exception {
        Mockito.when(taskRepository.findAll()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/tasks"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isEmpty());
    }

    // Testira, ali metoda getTaskById vrne nalogo, če je najdena po ID-ju
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

    // Testira, ali metoda getTaskById vrne status 404, če naloga ni najdena po ID-ju
    @Test
    @DisplayName("Get Task by ID - Not Found")
    void getTaskById_ShouldReturnNotFound() throws Exception {
        Mockito.when(taskRepository.findById(999)).thenReturn(Optional.empty());

        mockMvc.perform(get("/task/999"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Task not found with id:999"));
    }

    // Testira, ali metoda createTask uspešno ustvari novo nalogo
    @Test
    @DisplayName("Create Task - Success")
    void createTask_ShouldReturnCreatedTask() throws Exception {
        Task task = new Task("Task 1", "Description", null, TaskStatus.IN_PROGRESS);
        Mockito.when(taskRepository.save(Mockito.any(Task.class))).thenReturn(task);

        mockMvc.perform(post("/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"taskName\":\"Task 1\",\"taskDescription\":\"Description\",\"status\":\"IN_PROGRESS\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.taskName").value("Task 1"))
                .andExpect(jsonPath("$.taskDescription").value("Description"));
    }
    @Test
    @DisplayName("Create Task - Invalid Input")
    void createTask_ShouldReturnBadRequestForInvalidInput() throws Exception {
        // Poskusimo ustvariti nalogo z neveljavnim JSON vhodom (manjka taskName)
        String invalidTaskJson = "{\"taskDescription\":\"Description\",\"status\":\"IN_PROGRESS\"}";

        mockMvc.perform(post("/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(invalidTaskJson))
                .andExpect(status().isBadRequest());
    }
}
