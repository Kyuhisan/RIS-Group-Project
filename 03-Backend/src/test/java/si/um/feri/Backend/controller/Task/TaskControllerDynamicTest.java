package si.um.feri.Backend.controller.Task;

import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.TestFactory;
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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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
                            .andExpect(status().isBadRequest());
                }))
                .toList();
    }
}