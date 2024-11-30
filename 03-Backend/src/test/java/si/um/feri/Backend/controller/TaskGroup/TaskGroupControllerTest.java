package si.um.feri.Backend.controller.TaskGroup;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.io.TempDir;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import si.um.feri.Backend.controller.TaskGroupController;
import si.um.feri.Backend.model.Task;
import si.um.feri.Backend.model.TaskGroup;
import si.um.feri.Backend.repository.TaskGroupRepository;

import java.nio.file.Path;
import java.util.Arrays;
import java.util.Optional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(TaskGroupController.class)
@TestMethodOrder(OrderAnnotation.class)
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@ExtendWith(SpringExtension.class)
@Tag("Group Controller Tests")
class TaskGroupControllerUnitTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TaskGroupRepository taskGroupRepository;

    @TempDir
    Path tempDir;

    @BeforeAll
    static void initAll() {
        System.out.println("Starting TaskGroupController tests...");
    }

    @AfterAll
    static void tearDownAll() {
        System.out.println("Completed TaskGroupController tests.");
    }

    @BeforeEach
    void setup() {
        System.out.println("Setting up mock data for a test...");
    }

    @AfterEach
    void cleanup() {
        System.out.println("Cleaning up after a test...");
    }

    @Nested
    @DisplayName("Positive Tests")
    class PositiveTests {

        @Test
        @DisplayName("Get All Groups - Success")
        @Order(1)
        void getAllGroups() throws Exception {
            TaskGroup group1 = new TaskGroup("Group A", 75.5, null);
            TaskGroup group2 = new TaskGroup("Group B", 50.0, null);

            Mockito.when(taskGroupRepository.findAll()).thenReturn(Arrays.asList(group1, group2));

            mockMvc.perform(get("/groups"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$[0].groupName").value("Group A"))
                    .andExpect(jsonPath("$[1].groupName").value("Group B"));
        }

        @Test
        @DisplayName("Get Group by ID - Success")
        @Order(2)
        void getGroupByID() throws Exception {
            TaskGroup group = new TaskGroup("Group A", 75.5, null);

            Mockito.when(taskGroupRepository.findById(1)).thenReturn(Optional.of(group));

            mockMvc.perform(get("/group/1"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.groupName").value("Group A"));
        }

        @Test
        @DisplayName("Get All Tasks in a Group - Success")
        @Order(3)
        void getGroupTasks() throws Exception {
            TaskGroup group = new TaskGroup("Group A", 75.5, null);
            Task task1 = new Task("Task 1", "Description 1", group, null);
            Task task2 = new Task("Task 2", "Description 2", group, null);
            group.setListOfTasks(Arrays.asList(task1, task2));

            Mockito.when(taskGroupRepository.findById(1)).thenReturn(Optional.of(group));

            mockMvc.perform(get("/group-tsk/1"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$[0].taskName").value("Task 1"))
                    .andExpect(jsonPath("$[1].taskName").value("Task 2"));
        }

        @Test
        @DisplayName("Add New Group - Success")
        @Order(4)
        void createGroup() throws Exception {
            TaskGroup group = new TaskGroup("Group A", 75.5, null);

            Mockito.when(taskGroupRepository.save(Mockito.any(TaskGroup.class))).thenReturn(group);

            mockMvc.perform(post("/group")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content("{\"groupName\":\"Group A\",\"groupProgress\":75.5}"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.groupName").value("Group A"))
                    .andExpect(jsonPath("$.groupProgress").value(75.5));
        }

        @Test
        @DisplayName("Update Group - Success")
        @Order(5)
        void updateGroup() throws Exception {
            TaskGroup existingGroup = new TaskGroup("Old Group", 50.0, null);
            TaskGroup updatedGroup = new TaskGroup("Updated Group", 80.0, null);

            Mockito.when(taskGroupRepository.findById(1)).thenReturn(Optional.of(existingGroup));
            Mockito.when(taskGroupRepository.save(Mockito.any(TaskGroup.class))).thenReturn(updatedGroup);

            mockMvc.perform(put("/group/1")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content("{\"groupName\":\"Updated Group\",\"groupProgress\":80.0}"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.groupName").value("Updated Group"))
                    .andExpect(jsonPath("$.groupProgress").value(80.0));
        }

        @Test
        @DisplayName("Delete Group - Success")
        @Order(6)
        void deleteGroup() throws Exception {
            Mockito.doNothing().when(taskGroupRepository).deleteById(1);

            mockMvc.perform(delete("/group/1"))
                    .andExpect(status().isOk())
                    .andExpect(content().string("Group with id: 1 has been deleted."));
        }
    }

    @Nested
    @DisplayName("Negative Tests")
    class NegativeTests {

        @Test
        @DisplayName("Get Group by ID - Not Found")
        @Order(1)
        void getGroupByID() throws Exception {
            Mockito.when(taskGroupRepository.findById(999)).thenReturn(Optional.empty());

            mockMvc.perform(get("/group/999"))
                    .andExpect(status().isNotFound())
                    .andExpect(content().string("TaskGroup not found with id:999"));
        }

        @Test
        @DisplayName("Add New Group - Invalid Input")
        @Order(2)
        void createGroup() throws Exception {
            mockMvc.perform(post("/group")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content("{\"groupProgress\":75.5}")) // Missing groupName
                    .andExpect(status().isBadRequest());
        }

        @Test
        @DisplayName("Update Group - Not Found")
        @Order(3)
        void updateGroup() throws Exception {
            Mockito.when(taskGroupRepository.findById(999)).thenReturn(Optional.empty());

            mockMvc.perform(put("/group/999")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content("{\"groupName\":\"Updated Group\",\"groupProgress\":80.0}"))
                    .andExpect(status().isNotFound())
                    .andExpect(content().string("TaskGroup not found with id:999"));
        }

        @Test
        @DisplayName("Delete Group - Not Found")
        @Order(4)
        void deleteGroup() throws Exception {
            Mockito.doThrow(new RuntimeException("TaskGroup not found")).when(taskGroupRepository).deleteById(999);

            mockMvc.perform(delete("/group/999"))
                    .andExpect(status().is4xxClientError());
        }
    }
}
