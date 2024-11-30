package si.um.feri.Backend.controller.Task;

import org.junit.jupiter.api.RepeatedTest;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import si.um.feri.Backend.controller.TaskController;
import si.um.feri.Backend.repository.TaskRepository;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TaskController.class)
class TaskControllerRepeatedTest {

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
}