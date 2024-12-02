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
@DisplayName("Task Group Model Tests")
class TaskGroupUnitTest {

    @BeforeAll
    static void initAll() {
        System.out.println("Starting TaskGroup model tests...");
    }

    @AfterAll
    static void tearDownAll() {
        System.out.println("Completed TaskGroup model tests.");
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
    class PositiveTests{
        @Test
        @DisplayName("Create TaskGroup with valid data")
        @Order(1)
        void testCreateTaskGroup() {
            TaskGroup taskGroup = new TaskGroup("Group A", 75.5, Collections.emptyList());

            assertNotNull(taskGroup);
            assertEquals("Group A", taskGroup.getGroupName());
            assertEquals(75.5, taskGroup.getGroupProgress());
            assertNotNull(taskGroup.getListOfTasks());
            assertTrue(taskGroup.getListOfTasks().isEmpty());
        }

        @ParameterizedTest
        @MethodSource("TaskGroupData")
        @DisplayName("TaskGroup Properties")
        @Order(2)
        void testTaskGroupProperties(String groupName, double progress, List<Task> tasks, int expectedSize) {
            TaskGroup taskGroup = new TaskGroup(groupName, progress, tasks);

            assertEquals(groupName, taskGroup.getGroupName());
            assertEquals(progress, taskGroup.getGroupProgress());
            assertNotNull(taskGroup.getListOfTasks());
            assertEquals(expectedSize, taskGroup.getListOfTasks().size());
        }

        static Stream<Arguments> TaskGroupData() {
            return Stream.of(
                    Arguments.of("Group 1", 50.0, Collections.emptyList(), 0),
                    Arguments.of("Group 2", 75.5, null, 0),
                    Arguments.of("Group 3", 100.0,
                            Arrays.asList(
                                    new Task("Task 1", "Description 1", null, TaskStatus.NOT_STARTED),
                                    new Task("Task 2", "Description 2", null, TaskStatus.IN_PROGRESS)
                            ), 2)
            );
        }

        @TestFactory
        @DisplayName("TaskGroup Progress Edge Cases")
        @Order(3)
        Collection<DynamicTest> dynamicTestForTaskGroupProgress() {
            List<TaskGroup> groups = Arrays.asList(
                    new TaskGroup("Low Progress", 0.0, Collections.emptyList()),
                    new TaskGroup("Medium Progress", 50.0, Collections.emptyList()),
                    new TaskGroup("High Progress", 100.0, Collections.emptyList())
            );

            return Arrays.asList(
                    DynamicTest.dynamicTest("Test progress = 0.0", () -> assertEquals(0.0, groups.get(0).getGroupProgress())),
                    DynamicTest.dynamicTest("Test progress = 50.0", () -> assertEquals(50.0, groups.get(1).getGroupProgress())),
                    DynamicTest.dynamicTest("Test progress = 100.0", () -> assertEquals(100.0, groups.get(2).getGroupProgress()))
            );
        }
    }

    @Nested
    @DisplayName("Negative Tests")
    class NegativeTests{
        @Test
        @DisplayName("TaskGroup with null list of tasks")
        @Order(1)
        void testNullListOfTasks() {
            TaskGroup taskGroup = new TaskGroup("Group C", 60.0, null);

            assertNotNull(taskGroup.getListOfTasks(), "List of tasks should not be null");
            assertTrue(taskGroup.getListOfTasks().isEmpty(), "List of tasks should default to empty");
        }

        @Test
        @DisplayName("TaskGroup empty group name")
        @Order(2)
        void testEmptyGroupName() {
            TaskGroup taskGroup = new TaskGroup("", 25.0, Collections.emptyList());

            assertEquals("", taskGroup.getGroupName(), "Group name should be an empty string");
        }
    }
}
