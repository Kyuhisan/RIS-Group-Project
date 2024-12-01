import org.springframework.test.web.servlet.MockMvc;
import si.um.feri.Backend.model.Task;
import si.um.feri.Backend.model.TaskStatus;
import si.um.feri.Backend.repository.TaskRepository;
import java.util.Arrays;
import java.util.Collections;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@WebMvcTest(TaskController.class)
class TaskControllerParameterizedTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TaskRepository taskRepository;

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