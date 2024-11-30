package si.um.feri.Backend.controller.Task;

import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.TestFactory;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import si.um.feri.Backend.controller.TaskController;
import si.um.feri.Backend.model.Task;
import si.um.feri.Backend.model.TaskStatus;
import si.um.feri.Backend.repository.TaskRepository;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TaskController.class)
class TaskControllerDynamicTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TaskRepository taskRepository;

    @TestFactory
    Collection<DynamicTest> dynamicTaskCreationTests() {
        List<Task> tasks = Arrays.asList(
                new Task("Dynamic Task 1", "Description 1", null, TaskStatus.IN_PROGRESS),
                new Task("Dynamic Task 2", "Description 2", null, TaskStatus.NOT_STARTED)
        );

        return tasks.stream()
                .map(task -> DynamicTest.dynamicTest("Create Task: " + task.getTaskName(), () -> {
                    Mockito.when(taskRepository.save(Mockito.any(Task.class))).thenReturn(task);

                    mockMvc.perform(post("/tasks")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(String.format("{\"taskName\":\"%s\",\"taskDescription\":\"%s\",\"status\":\"%s\"}",
                                    task.getTaskName(), task.getTaskDescription(), task.getStatus().name())))
                            .andExpect(status().isOk())
                            .andExpect(jsonPath("$.taskName").value(task.getTaskName()));
                }))
                .toList();
    }

    @TestFactory
    Collection<DynamicTest> dynamicTaskCreationNegativeTests() {
        List<Task> tasks = Arrays.asList(
                new Task("", "Description 1", null, TaskStatus.IN_PROGRESS), // Empty task name
                new Task("Dynamic Task 2", "", null, TaskStatus.NOT_STARTED) // Empty task description
        );

        return tasks.stream()
                .map(task -> DynamicTest.dynamicTest("Create Task with invalid data: " + task.getTaskName(), () -> {
                    Mockito.when(taskRepository.save(Mockito.any(Task.class))).thenReturn(task);

                    mockMvc.perform(post("/tasks")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(String.format("{\"taskName\":\"%s\",\"taskDescription\":\"%s\",\"status\":\"%s\"}",
                                    task.getTaskName(), task.getTaskDescription(), task.getStatus().name())))
                            .andExpect(status().isBadRequest())
                            .andExpect(jsonPath("$.message").value("Task name is required"));
                }))
                .toList();
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

    @RepeatedTest(3)
    void deleteTask_ShouldReturnSuccessMessage() throws Exception {
        Mockito.doNothing().when(taskRepository).deleteById(1);

        mockMvc.perform(delete("/task/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("Task with id: 1 has been deleted."));
    }

    @TestFactory
    Collection<DynamicTest> dynamicTaskCreationEmptyJsonTests() {
        return Arrays.asList(
                DynamicTest.dynamicTest("Create Task with empty JSON", () -> {
                    mockMvc.perform(post("/tasks")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content("{}"))
                            .andExpect(status().isBadRequest())
                            .andExpect(jsonPath("$.message").value("Task name is required"));
                })
        );
    }
}