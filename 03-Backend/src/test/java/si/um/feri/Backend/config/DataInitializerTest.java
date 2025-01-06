package si.um.feri.Backend.config;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import si.um.feri.Backend.model.Task;
import si.um.feri.Backend.model.TaskGroup;
import si.um.feri.Backend.model.TaskStatus;
import si.um.feri.Backend.repository.TaskGroupRepository;
import si.um.feri.Backend.repository.TaskRepository;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@DisplayName("DataInitializer Tests")
class DataInitializerTest {

    @Mock
    private TaskRepository taskRepository;

    @Mock
    private TaskGroupRepository taskGroupRepository;

    @InjectMocks
    private DataInitializer dataInitializer;

    @BeforeAll
    static void initAll() {
        System.out.println("Starting DataInitializer tests...");
    }

    @AfterAll
    static void tearDownAll() {
        System.out.println("Completed DataInitializer tests.");
    }

    @BeforeEach
    void setup() {
        System.out.println("Setting up for a test...");
    }

    @AfterEach
    void cleanup() {
        System.out.println("Cleaning up after a test...");
        Mockito.reset(taskRepository, taskGroupRepository);
    }

    @Nested
    @DisplayName("Positive Tests")
    class PositiveTests {
        @Test
        @DisplayName("Verify TaskGroup Initialization")
        void testTaskGroupInitialization() throws Exception {
            dataInitializer.run();

            verify(taskGroupRepository, times(1)).saveAll(anyList());
        }

        @Test
        @DisplayName("Verify Task Initialization")
        void testTaskInitialization() throws Exception {
            dataInitializer.run();

            verify(taskRepository, times(1)).saveAll(anyList());
        }
    }

    @Nested
    @DisplayName("Negative Tests")
    class NegativeTests {
        @Test
        @DisplayName("Run with Empty Repositories")
        void testEmptyRepositories() {
            doThrow(new RuntimeException("Repository is empty")).when(taskGroupRepository).saveAll(anyList());
            doThrow(new RuntimeException("Repository is empty")).when(taskRepository).saveAll(anyList());

            Assertions.assertThrows(RuntimeException.class, () -> dataInitializer.run());
        }
    }
}