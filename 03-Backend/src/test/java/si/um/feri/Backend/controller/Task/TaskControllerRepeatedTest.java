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
        Mockito.doThrow(new IllegalArgumentException("Task not found")).when(taskRepository).deleteById(999);

        mockMvc.perform(delete("/task/999"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Task not found"));
    }
}
