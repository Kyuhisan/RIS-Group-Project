package si.um.feri.Backend.model;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.*;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.*;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@DisplayName("Task Model Tests")
class TaskUnitTest {

    @BeforeAll
    static void initAll() {
        System.out.println("Starting Task model tests...");
    }

    @AfterAll
    static void tearDownAll() {
        System.out.println("Completed Task model tests.");
    }

    @BeforeEach
    void setup() {
        System.out.println("Setting up data for a test...");
    }

    @AfterEach
    void cleanup() {
        System.out.println("Cleaning up after a test...");
    }

    @Nested
    @DisplayName("Positive Tests")
    class PositiveTests {
        @Test
        @DisplayName("Create Task with valid data")
        @Order(1)
        void testCreateTask() {
            Task task = new Task("Task", "Description", null, TaskStatus.NOT_STARTED);

            assertNotNull(task);
            assertEquals("Task", task.getTaskName());
            assertEquals("Description", task.getTaskDescription());
            assertNull(task.getTaskGroup());
            assertEquals(TaskStatus.NOT_STARTED, task.getStatus());
        }

        @ParameterizedTest
        @MethodSource("TaskData")
        @DisplayName("Task Properties Test")
        @Order(2)
        void testTaskProperties(String taskName, String description, TaskGroup group, TaskStatus status) {
            Task task = new Task(taskName, description, group, status);

            assertEquals(taskName, task.getTaskName());
            assertEquals(description, task.getTaskDescription());
            assertEquals(group, task.getTaskGroup());
            assertEquals(status, task.getStatus());
        }

        static Stream<Arguments> TaskData() {
            TaskGroup group = new TaskGroup("Group", 50.0, Collections.emptyList());
            return Stream.of(
                    Arguments.of("Task 1", "Desc 1", null, TaskStatus.NOT_STARTED),
                    Arguments.of("Task 2", "Desc 2", group, TaskStatus.IN_PROGRESS),
                    Arguments.of("Task 3", "Desc 3", group, TaskStatus.FINISHED)
            );
        }

        @RepeatedTest(3)
        @DisplayName("Repeated Test for Task Status")
        @Order(3)
        void repeatedTestForTaskStatus() {
            Task task = new Task("Task", "Description", null, TaskStatus.NOT_STARTED);
            assertEquals(TaskStatus.NOT_STARTED, task.getStatus());
        }
    }

    @Nested
    @DisplayName("Negative Tests")
    class NegativeTests {
        @Test
        @DisplayName("Task with null taskName")
        @Order(1)
        void testTaskWithNullTaskName() {
            Exception exception = assertThrows(IllegalArgumentException.class, () ->
                    new Task(null, "Description", null, TaskStatus.NOT_STARTED)
            );
            assertEquals("Task name cannot be null or empty", exception.getMessage());
        }

        @Test
        @DisplayName("Task with empty taskDescription")
        @Order(2)
        void testTaskWithEmptyTaskDescription() {
            Task task = new Task("Task", "", null, TaskStatus.IN_PROGRESS);

            assertEquals("", task.getTaskDescription(), "Task description should be an empty string");
        }

        @Test
        @DisplayName("Task with invalid TaskStatus")
        @Order(3)
        void testTaskWithInvalidTaskStatus() {
            Exception exception = assertThrows(IllegalArgumentException.class, () ->
                    new Task("Task", "Description", null, null)
            );
            assertEquals("Task status cannot be null", exception.getMessage());
        }

        @Test
        @DisplayName("Task associated with null TaskGroup")
        @Order(4)
        void testTaskWithNullTaskGroup() {
            Task task = new Task("Task", "Description", null, TaskStatus.NOT_STARTED);

            assertNull(task.getTaskGroup(), "Task group should be null if not set");
        }
    }
}
